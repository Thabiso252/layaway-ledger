package zm.ac.layaway.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Item {

    private final String itemId;
    private final String name;
    private final BigDecimal price;

    private ItemStatus status;

    public Item(String name, BigDecimal price) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Item name cannot be empty."
            );
        }

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Item price must be greater than zero."
            );
        }

        this.itemId = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.status = ItemStatus.AVAILABLE;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        if (status == null) {
            throw new IllegalArgumentException(
                    "Item status cannot be null."
            );
        }

        this.status = status;
    }
}
