package zm.ac.layaway.model;
import java.math.BigDecimal;
import java.time.LocalDate;
public class Payment {
    private final BigDecimal amount;
    private final LocalDate paymentDate;
    public Payment(BigDecimal amount, LocalDate paymentDate) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be positive.");
        if (paymentDate == null) throw new IllegalArgumentException("Date cannot be null.");
        this.amount = amount;
        this.paymentDate = paymentDate;
    }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getPaymentDate() { return paymentDate; }
}
