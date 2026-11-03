package com.layaway.model;

import java.time.LocalDate;

public class WeeklyInstalment implements PaymentPlan {
    private static final int CYCLE_DAYS = 7;

    @Override
    public LocalDate nextDueDate(LocalDate lastPayment) {
        return lastPayment.plusDays(CYCLE_DAYS);
    }

    @Override
    public double calculateInstalment(double total, int instalments) {
        if (total < 0) {
            throw new IllegalArgumentException("Total cannot be negative");
        }
        if (instalments <= 0) {
            throw new IllegalArgumentException("Instalments must be greater than zero");
        }
        return Math.round(total / instalments * 100.0) / 100.0;
    }
}