package com.layaway.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PaymentPlanTest {

    @Test
    void weeklyDueDateIsSevenDaysLater() {
        PaymentPlan plan = new WeeklyInstalment();
        assertEquals(LocalDate.of(2026, 11, 9), plan.nextDueDate(LocalDate.of(2026, 11, 2)));
    }

    @Test
    void monthlyDueDateIsThirtyDaysLater() {
        PaymentPlan plan = new MonthlyInstalment();
        assertEquals(LocalDate.of(2026, 12, 2), plan.nextDueDate(LocalDate.of(2026, 11, 2)));
    }

    @Test
    void instalmentSplitsTotalEvenly() {
        assertEquals(250.0, new WeeklyInstalment().calculateInstalment(1000, 4));
    }

    @Test
    void zeroInstalmentsRejected() {
        assertThrows(IllegalArgumentException.class,
            () -> new MonthlyInstalment().calculateInstalment(1000, 0));
    }

    @Test
    void polymorphismDifferentPlansDifferentDates() {
        LocalDate last = LocalDate.of(2026, 11, 2);
        List<PaymentPlan> plans = List.of(new WeeklyInstalment(), new MonthlyInstalment());
        assertNotEquals(plans.get(0).nextDueDate(last), plans.get(1).nextDueDate(last));
    }
}