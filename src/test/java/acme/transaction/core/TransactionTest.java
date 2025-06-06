package acme.transaction.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.Test;


public class TransactionTest {

    @Test
    public void shouldCreatePurchaseWithNoIdAndDefaultCurrency() {
        Date date = new Date();
        Transaction purchase = new Transaction("Test Purchase", date, 100.0);
        assertEquals(purchase.description(), "Test Purchase");
        assertEquals(purchase.date(), date);
        assertEquals(purchase.totalAmount(), 100.0);
        assertNull(purchase.id());
    }

    @Test
    public void shouldCreatePurchaseWithId() {
        String id = UUID.randomUUID().toString();
        Date date = new Date();
        Transaction purchase = new Transaction("Test Purchase", date, 100.0,  id);
        assertEquals(purchase.description(), "Test Purchase");
        assertEquals(purchase.date(), date);
        assertEquals(purchase.totalAmount(), 100.0);
        assertEquals(purchase.id(), id);
    }

    @Test
    public void shouldNotCreatePurchaseWithNullDescription() {
        assertThrows(IllegalArgumentException.class,
            () -> new Transaction(null, new Date(), 100.0),
    "Description cannot be null or empty");
    }

    @Test
    public void shouldNotCreatePurchaseWithLongDescription() {
        assertThrows(IllegalArgumentException.class,
            () -> new Transaction("Test Purchase with a very long description that is longer than 50 characters", new Date(), 100.0, "USD"),
            "Description cannot be longer than 50 characters");
    }

    @Test
    public void shouldNotCreatePurchaseWithNullDate() {
        assertThrows(IllegalArgumentException.class, 
            () -> new Transaction("Test Purchase", null, 100.0),
            "Date cannot be null");
    }

    
    @Test
    public void shouldNotCreatePurchaseWithNegativeTotalAmount() {
        assertThrows(IllegalArgumentException.class,
            () -> new Transaction("Test Purchase", new Date(), -100.0),
            "Total amount must be greater than 0");
    }
}
