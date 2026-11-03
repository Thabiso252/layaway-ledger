package zm.ac.layaway.model;
import java.math.BigDecimal;
public class LayawayReservation extends Reservation {
    public LayawayReservation(String id, Customer customer, Item item, BigDecimal totalPrice, PaymentPlan plan) { super(id, customer, item, totalPrice, plan); }
}
