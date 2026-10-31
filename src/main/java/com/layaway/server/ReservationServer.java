package com.layaway.server;

import io.javalin.Javalin;
import java.util.List;
import java.util.Map;

public class ReservationServer {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.showJavalinBanner = false;
        }).start(7000);

        app.get("/", ctx -> ctx.redirect("/index.html"));

        // --- STUB ENDPOINTS (Replace logic when A pushes classes) ---

        app.post("/customers", ctx -> {
            // TODO: call ledger.addCustomer(...)
            ctx.status(201).json(Map.of("id", 1, "message", "Customer added"));
        });

        app.post("/items", ctx -> {
            // TODO: call ledger.addItem(...)
            ctx.status(201).json(Map.of("id", 1, "message", "Item added"));
        });

        app.post("/reservations", ctx -> {
            // TODO: call ledger.createReservation(...)
            ctx.status(201).json(Map.of("id", 1, "message", "Reservation created"));
        });

        app.post("/payments", ctx -> {
            // TODO: call ledger.recordPayment(...)
            ctx.status(200).json(Map.of("message", "Payment recorded", "remainingBalance", 500.0));
        });

        app.get("/overdue", ctx -> {
            // TODO: call ledger.getOverdue()
            ctx.json(List.of(
                Map.of("id", 1, "customerName", "John Doe", "itemDescription", "Sofa", "remainingBalance", 500.0, "nextDueDate", "2026-11-05")
            ));
        });

        app.get("/ready", ctx -> {
            // TODO: call ledger.getReadyForCollection()
            ctx.json(List.of(
                Map.of("id", 2, "customerName", "Jane Smith", "itemDescription", "TV", "status", "READY")
            ));
        });

        app.get("/reservations", ctx -> {
            // TODO: call ledger.getAllReservations()
            ctx.json(List.of(
                Map.of("id", 1, "customerName", "John Doe", "itemDescription", "Sofa", "totalPrice", 1000.0, "amountPaid", 500.0, "remainingBalance", 500.0, "complete", false)
            ));
        });

        app.delete("/reservations/{id}", ctx -> {
            // TODO: call ledger.deleteReservation(...)
            ctx.status(200).json(Map.of("message", "Reservation deleted"));
        });

        System.out.println("Layaway Ledger server running on http://localhost:7000");
    }
}
