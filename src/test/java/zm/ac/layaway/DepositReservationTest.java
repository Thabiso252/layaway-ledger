package zm.ac.layaway;

import org.junit.jupiter.api.Test;
import zm.ac.layaway.model.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DepositReservationTest {

    @Test
    void shouldAcceptDepositOfExactlyTwentyPercent() {
        Customer customer = new Customer("John", "0977000000");
        Item item = new Item("Laptop", new BigDecimal("1000.00"));

        DepositReservation reservation = new DepositReservation(
                customer,
                item,
                new BigDecimal("1000.00"),
                new WeeklyInstalment(),
                new BigDecimal("200.00")
        );

        assertEquals(new BigDecimal("200.00"), reservation.getAmountPaid());
        assertEquals(new BigDecimal("800.00"), reservation.getRemainingBalance());
    }

    @Test
    void shouldRejectDepositBelowTwentyPercent() {
        Customer customer = new Customer("John", "0977000000");
        Item item = new Item("Laptop", new BigDecimal("1000.00"));

        assertThrows(IllegalArgumentException.class, () ->
                new DepositReservation(
                        customer,
                        item,
                        new BigDecimal("1000.00"),
                        new WeeklyInstalment(),
                        new BigDecimal("150.00")
                )
        );
    }
}
