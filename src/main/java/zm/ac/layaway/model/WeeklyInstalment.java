package zm.ac.layaway.model;
import java.time.LocalDate;
public class WeeklyInstalment implements PaymentPlan {
    @Override public LocalDate nextDueDate(LocalDate lastPayment) { return lastPayment.plusDays(7); }
    @Override public double calculateInstalment(double total, int instalments) { return total / instalments; }
}
