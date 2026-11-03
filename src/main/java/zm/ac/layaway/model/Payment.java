package zm.ac.layaway.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Payment {

    private final String paymentId;
    private final BigDecimal amount;
    private final LocalDate paymentDate;

    public Payment(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Payment amount must be greater than zero."
            );
        }

        this.paymentId = UUID.randomUUID().toString();
        this.amount = amount;
        this.paymentDate = LocalDate.now();
    }

    public String getPaymentId() {
        return paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }
}
