package com.layaway.model;

import java.time.LocalDate;

public interface PaymentPlan {
    LocalDate nextDueDate(LocalDate lastPayment);
    double calculateInstalment(double total, int instalments);
}