package com.layaway.model;
import java.io.Serializable;
public class LayawayAgreement implements Serializable {
    private Customer customer;
    private Item item;
    private double totalAmount;
    private double balancePaid;

    public LayawayAgreement(Customer customer, Item item, double totalAmount) {
        this.customer = customer;
        this.item = item;
        this.totalAmount = totalAmount;
        this.balancePaid = 0.0;
    }

    public void makePayment(double amount) {
        if (amount > 0) {
            this.balancePaid += amount;
        }
    }

    public double getRemainingBalance() {
        return this.totalAmount - this.balancePaid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Item getItem() {
        return item;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getBalancePaid() {
        return balancePaid;
    }
}