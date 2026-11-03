package zm.ac.layaway.model;

import java.util.UUID;

public class Customer {

    private final String customerId;
    private final String name;
    private final String phoneNumber;

    public Customer(String name, String phoneNumber) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Customer name cannot be empty."
            );
        }

        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException(
                    "Customer phone number cannot be empty."
            );
        }

        this.customerId = UUID.randomUUID().toString();
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
