package zm.ac.layaway.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import zm.ac.layaway.exception.ReservationException;
import zm.ac.layaway.model.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservationServer {
    private static List<Customer> customers = new ArrayList<>();
    private static List<Item> items = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();
    private static int nextCustomerId = 1001;
    private static int nextItemId = 2001;
    private static int nextReservationId = 3001;

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.showJavalinBanner = false;
            config.jsonMapper(new JavalinJackson(mapper, false));
        }).start(7000);

        app.get("/", ctx -> ctx.redirect("/index.html"));

        app.post("/customers", ctx -> {
            var req = ctx.bodyAsClass(Map.class);
            Customer c = new Customer(String.valueOf(nextCustomerId++), (String) req.get("name"), (String) req.get("phone"));
            customers.add(c);
            ctx.status(201).json(Map.of("id", c.getCustomerId()));
        });

        app.post("/items", ctx -> {
            var req = ctx.bodyAsClass(Map.class);
            BigDecimal price = new BigDecimal(req.get("price").toString());
            Item i = new Item(String.valueOf(nextItemId++), (String) req.get("description"), price);
            items.add(i);
            ctx.status(201).json(Map.of("id", i.getItemId()));
        });

        app.post("/reservations", ctx -> {
            try {
                var req = ctx.bodyAsClass(Map.class);
                String customerId = req.get("customerId").toString().trim();
                String itemId = req.get("itemId").toString().trim();
                String planType = (String) req.get("plan");
                double deposit = req.containsKey("depositAmount") ? ((Number) req.get("depositAmount")).doubleValue() : 0.0;

                Customer c = customers.stream().filter(x -> x.getCustomerId().equals(customerId)).findFirst()
                        .orElseThrow(() -> new ReservationException("Customer not found"));
                Item i = items.stream().filter(x -> x.getItemId().equals(itemId)).findFirst()
                        .orElseThrow(() -> new ReservationException("Item not found"));

                if (i.getStatus() != ItemStatus.AVAILABLE) throw new ReservationException("Item not available");

                PaymentPlan plan = planType.equalsIgnoreCase("weekly") ? new WeeklyInstalment() : new MonthlyInstalment();
                String resId = String.valueOf(nextReservationId++);
                Reservation res = (deposit > 0)
                    ? new DepositReservation(resId, c, i, i.getPrice(), plan, new BigDecimal(deposit))
                    : new LayawayReservation(resId, c, i, i.getPrice(), plan);

                i.setStatus(ItemStatus.RESERVED);
                reservations.add(res);
                ctx.status(201).json(Map.of("id", res.getReservationId()));
            } catch (Exception e) {
                ctx.status(400).json(Map.of("error", e.getMessage()));
            }
        });

        app.post("/payments", ctx -> {
            try {
                var req = ctx.bodyAsClass(Map.class);
                String resId = req.get("reservationId").toString().trim();
                BigDecimal amount = new BigDecimal(req.get("amount").toString());
                Reservation res = reservations.stream().filter(x -> x.getReservationId().equals(resId)).findFirst()
                        .orElseThrow(() -> new ReservationException("Reservation not found"));
                res.addPayment(new Payment(amount, LocalDate.now()));
                ctx.json(Map.of("message", "Payment recorded"));
            } catch (Exception e) {
                ctx.status(400).json(Map.of("error", e.getMessage()));
            }
        });

        app.get("/overdue", ctx -> ctx.json(
            reservations.stream()
                .filter(r -> !r.isComplete() && r.getStatus() == ReservationStatus.ACTIVE)
                .collect(Collectors.toList())
        ));

        app.get("/ready", ctx -> ctx.json(
            reservations.stream()
                .filter(Reservation::isComplete)
                .collect(Collectors.toList())
        ));

        app.get("/reservations", ctx -> ctx.json(reservations));

        app.get("/schedule/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id").trim();
                Reservation res = reservations.stream().filter(x -> x.getReservationId().equals(id)).findFirst()
                        .orElseThrow(() -> new ReservationException("Reservation not found"));
                ctx.json(Map.of(
                    "reservationId", id,
                    "planType", res.getPaymentPlan().getClass().getSimpleName(),
                    "nextDueDate", res.getNextDueDate().toString(),
                    "instalmentAmount", res.getInstalmentAmount(4),
                    "totalPrice", res.getTotalPrice()
                ));
            } catch (Exception e) {
                ctx.status(400).json(Map.of("error", e.getMessage()));
            }
        });

        app.delete("/reservations/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id").trim();
                Reservation res = reservations.stream().filter(x -> x.getReservationId().equals(id)).findFirst()
                        .orElseThrow(() -> new ReservationException("Reservation not found"));
                res.cancel();
                res.getItem().setStatus(ItemStatus.AVAILABLE);
                ctx.json(Map.of("message", "Deleted"));
            } catch (Exception e) {
                ctx.status(400).json(Map.of("error", e.getMessage()));
            }
        });

        System.out.println("Layaway Ledger server running on http://localhost:7000");
    }
}
