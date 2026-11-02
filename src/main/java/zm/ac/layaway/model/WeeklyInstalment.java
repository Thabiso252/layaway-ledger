package zm.ac.layaway.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class WeeklyInstalment implements PaymentPlan {

    @Override
    public BigDecimal calculateInstalment(
            BigDecimal total,
            int numberOfInstalments) {

        if (total == null || total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Total must be greater than zero.");
        }

        if (numberOfInstalments <= 0) {
            throw new IllegalArgumentException(
                    "Number of instalments must be greater than zero."
            );
        }

        return total.divide(
                BigDecimal.valueOf(numberOfInstalments),
                2,
                RoundingMode.HALF_UP
        );
    }

    @Override
    public LocalDate nextDueDate(LocalDate lastPaymentDate) {

        if (lastPaymentDate == null) {
            throw new IllegalArgumentException("Payment date cannot be null.");
        }

        return lastPaymentDate.plusDays(7);
    }
}
