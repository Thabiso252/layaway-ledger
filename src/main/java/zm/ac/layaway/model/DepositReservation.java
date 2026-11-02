package zm.ac.layaway.model;

import java.math.BigDecimal;

public class DepositReservation extends Reservation {

    private static final BigDecimal MINIMUM_DEPOSIT_RATE =
            new BigDecimal("0.20");

    public DepositReservation(
            Customer customer,
            Item item,
            BigDecimal totalPrice,
            PaymentPlan paymentPlan,
            BigDecimal initialDeposit) {

        super(customer, item, totalPrice, paymentPlan);

        if (initialDeposit == null || initialDeposit.compareTo(
                totalPrice.multiply(MINIMUM_DEPOSIT_RATE)) < 0) {
            throw new IllegalArgumentException(
                    "Initial deposit must be at least 20% of the total price."
            );
        }

        addPayment(new Payment(initialDeposit));
    }
}
