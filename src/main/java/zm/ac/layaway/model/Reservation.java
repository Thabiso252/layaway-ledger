
package zm.ac.layaway.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Reservation {

    private final String reservationId;
    private final Customer customer;
    private final Item item;
    private final BigDecimal totalPrice;
    private final PaymentPlan paymentPlan;

    private final List<Payment> payments;

    private ReservationStatus status;

    public Reservation(
            Customer customer,
            Item item,
            BigDecimal totalPrice,
            PaymentPlan paymentPlan) {

        if (customer == null || item == null ||
                totalPrice == null || paymentPlan == null) {
            throw new IllegalArgumentException(
                    "Reservation details cannot be null."
            );
        }

        if (totalPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Reservation price must be greater than zero."
            );
        }

        this.reservationId = UUID.randomUUID().toString();
        this.customer = customer;
        this.item = item;
        this.totalPrice = totalPrice;
        this.paymentPlan = paymentPlan;
        this.payments = new ArrayList<>();
        this.status = ReservationStatus.ACTIVE;
    }

    public String getReservationId() {
        return reservationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Item getItem() {
        return item;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public PaymentPlan getPaymentPlan() {
        return paymentPlan;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public List<Payment> getPayments() {
        return List.copyOf(payments);
    }
   
    public BigDecimal getAmountPaid() {
    BigDecimal amountPaid = BigDecimal.ZERO;

    for (Payment payment : payments) {
        amountPaid = amountPaid.add(payment.getAmount());
    }

    return amountPaid;
}

public BigDecimal getRemainingBalance() {
    return totalPrice.subtract(getAmountPaid());
}

public boolean isComplete() {
    return getRemainingBalance().compareTo(BigDecimal.ZERO) == 0;
}

public void addPayment(Payment payment) {
    if (payment == null) {
        throw new IllegalArgumentException(
                "Payment cannot be null."
        );
    }

    if (status != ReservationStatus.ACTIVE) {
        throw new IllegalStateException(
                "Payments can only be made on active reservations."
        );
    }

    if (payment.getAmount().compareTo(getRemainingBalance()) > 0) {
        throw new IllegalArgumentException(
                "Payment cannot exceed the remaining balance."
        );
    }

    payments.add(payment);

    if (isComplete()) {
        status = ReservationStatus.COMPLETED;
    }
}

public void cancel() {
    if (status != ReservationStatus.ACTIVE) {
        throw new IllegalStateException(
                "Only active reservations can be cancelled."
        );
    }

    status = ReservationStatus.CANCELLED;
}

}

