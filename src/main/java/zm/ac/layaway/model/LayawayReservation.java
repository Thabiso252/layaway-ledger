package zm.ac.layaway.model;

import java.math.BigDecimal;

public class LayawayReservation extends Reservation {

    public LayawayReservation(
            Customer customer,
            Item item,
            BigDecimal totalPrice,
            PaymentPlan paymentPlan) {

        super(customer, item, totalPrice, paymentPlan);
    }
}
