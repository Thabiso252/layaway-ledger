package zm.ac.layaway.model;

public class Customer {
    private final String customerId;
    private final String name;
    private final String phone;

    public Customer(String customerId, String name, String phone) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be empty.");
        if (phone == null || phone.isBlank()) throw new IllegalArgumentException("Phone cannot be empty.");
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
}
