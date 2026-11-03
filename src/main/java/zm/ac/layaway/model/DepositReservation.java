package zm.ac.layaway.model;
import java.math.BigDecimal;
public class DepositReservation extends Reservation {
    private final BigDecimal depositAmount;
    public DepositReservation(String id, Customer customer, Item item, BigDecimal totalPrice, PaymentPlan plan, BigDecimal depositAmount) {
        super(id, customer, item, totalPrice, plan);
        if (depositAmount == null || depositAmount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Deposit must be positive.");
        this.depositAmount = depositAmount;
    }
    public BigDecimal getDepositAmount() { return depositAmount; }
}
