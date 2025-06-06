package acme.transaction.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.Test;


public class ExchangeRateTest {

    @Test
    public void shouldCreateExchangeRate() {
        Date date = new Date();
        ExchangeRate exchangeRate = new ExchangeRate(date, "Real", "Brazil", "Real (Brazil)", 0.01);
        assertNotNull(exchangeRate);
        assertEquals(date, exchangeRate.date());
        assertEquals("Real", exchangeRate.currencyName());
        assertEquals("Brazil", exchangeRate.currencyCountry());
        assertEquals("Real (Brazil)", exchangeRate.currencyDescription());
        assertEquals(0.01, exchangeRate.rate());
    }

    @Test
    public void shouldRequireTheDate() {
        assertThrows(IllegalArgumentException.class, 
            () -> new ExchangeRate(null, "Real", "Brazil", "Real (Brazil)", 1.0), "Invalid exchange rate date: null");

    }

    @Test
    public void shouldRequireTheCurrencyName() {
        assertThrows(IllegalArgumentException.class, 
            () -> new ExchangeRate(new Date(), null, "Brazil", "Real (Brazil)", 1.0), "Invalid exchange rate currency: null");
        assertThrows(IllegalArgumentException.class, 
            () -> new ExchangeRate(new Date(), "", "Brazil", "Real (Brazil)", 1.0), "Invalid exchange rate currency: null");
    }

    @Test
    public void shouldRequireTheCurrencyCountry() {
        assertThrows(IllegalArgumentException.class, 
            () -> new ExchangeRate(new Date(), "Real", null, "Real (Brazil)", 1.0), "Invalid exchange rate currency country: null");
        assertThrows(IllegalArgumentException.class, 
            () -> new ExchangeRate(new Date(), "Real", "", "Real (Brazil)", 1.0), "Invalid exchange rate currency country: null");
    }

    @Test
    public void shouldRequireTheCurrencyDescription() {
        assertThrows(IllegalArgumentException.class, 
            () -> new ExchangeRate(new Date(), "Real", "Brazil", null, 1.0), "Invalid exchange rate currency description: null");
    }
    
    @Test
    public void shouldRequireTheRate() {
        assertThrows(IllegalArgumentException.class, 
            () -> new ExchangeRate(new Date(), "Real", "Brazil", "Real (Brazil)", null), "Invalid exchange rate coeficient: null or <= 0");
    }

    @Test
    public void shouldRequireTheRateGreaterThanZero() {
        assertThrows(IllegalArgumentException.class, 
            () -> new ExchangeRate(new Date(), "Real", "Brazil", "Real (Brazil)", 0.0), "Invalid exchange rate coeficient: null or <= 0");
    }
}
