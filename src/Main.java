package com.layaway;
import java.io.*;
import java.util.List;
import com.layaway.model.Customer;
import com.layaway.model.Item;
import com.layaway.model.LayawayAgreement;

public class Main {
    public static void main(String[] args) {
        List<LayawayAgreement> agreements = loadData();
        // 1. Create a Customer
        Customer customer = new Customer("Alice Smith", "555-1234");

        // 2. Create an Item
        Item item = new Item("Laptop", 600.00);

        // 3. Create a Layaway Agreement
        LayawayAgreement agreement = new LayawayAgreement(customer, item, 600.00);

        System.out.println("--- Layaway Agreement Created ---");
        System.out.println("Customer: " + agreement.getCustomer().getName());
        System.out.println("Phone: " + agreement.getCustomer().getPhone());
        System.out.println("Item: " + agreement.getItem().getItemName());
        System.out.println("Total Amount: $" + agreement.getTotalAmount());
        System.out.println("Initial Remaining Balance: $" + agreement.getRemainingBalance());

        // 4. Make Payments
        System.out.println("\nMaking a payment of $150.00...");
        agreement.makePayment(150.00);
        System.out.println("Remaining Balance: $" + agreement.getRemainingBalance());

        System.out.println("\nMaking another payment of $200.00...");
        agreement.makePayment(200.00);
        System.out.println("Final Remaining Balance: $" + agreement.getRemainingBalance());
        saveData(agreements);
    }
    private static final String DATA_FILE = "layaway_data.ser";

    public static void saveData(List<LayawayAgreement> agreements) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(agreements);
            System.out.println("Saved successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<LayawayAgreement> loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return new java.util.ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<LayawayAgreement>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
}
