package zm.ac.layaway.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface PaymentPlan {

    BigDecimal calculateInstalment(
            BigDecimal total,
            int numberOfInstalments
    );

    LocalDate nextDueDate(LocalDate lastPaymentDate);
}
