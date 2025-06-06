package acme.transaction.infrastructure.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import acme.transaction.core.ExchangeRate;

public class FiscalDataCurrencyRatesTest {

    @Mock
    private CurrencyExchangeRates currencyExchangeRates;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(currencyExchangeRates);
    }

    @Test
    public void shouldGetPreviousRate() {
        FiscalDataCurrencyRates fiscalDataCurrencyRates = new FiscalDataCurrencyRates(currencyExchangeRates);
        Date firstDate = this.getDate(-10);
        Date secondDate = this.getDate(-200);
        when(currencyExchangeRates.getRates()).thenReturn(Arrays.asList(
            new ExchangeRate(secondDate, "Cfa Franc", "Burkina Faso", "Burkina Faso (Cfa Franc)", 623.13),
            new ExchangeRate(firstDate, "Cfa Franc", "Burkina Faso", "Burkina Faso (Cfa Franc)", 605.95)
        ));
        ExchangeRate exchangeRate = fiscalDataCurrencyRates.getPreviousRate(this.getDate(0), "Cfa Franc", "Burkina Faso");
        assertEquals(exchangeRate.date(), firstDate);
        assertEquals(exchangeRate.currencyName(), "Cfa Franc");
        assertEquals(exchangeRate.currencyCountry(), "Burkina Faso");
        assertEquals(exchangeRate.rate(), 605.95);
    }

    @Test
    public void shouldGetPreviousRateWhenRateIsOlderThanSixMonths() {
        FiscalDataCurrencyRates fiscalDataCurrencyRates = new FiscalDataCurrencyRates(currencyExchangeRates);
        Date firstDate = this.getDate(-10);
        Date secondDate = this.getDate(-200);
        when(currencyExchangeRates.getRates()).thenReturn(Arrays.asList(
            new ExchangeRate(secondDate, "Cfa Franc", "Burkina Faso", "Burkina Faso (Cfa Franc)", 623.13),
            new ExchangeRate(firstDate, "Cfa Franc", "Burkina Faso", "Burkina Faso (Cfa Franc)", 605.95)
        ));
        ExchangeRate exchangeRate = fiscalDataCurrencyRates.getPreviousRate(this.getDate(-15), "Cfa Franc", "Burkina Faso");
        assertEquals(exchangeRate.date(), secondDate);
        assertEquals(exchangeRate.currencyName(), "Cfa Franc");
        assertEquals(exchangeRate.currencyCountry(), "Burkina Faso");
        assertEquals(exchangeRate.rate(), 623.13);
    }

    @Test
    public void shouldNotReturnAnythingWhenNoRateIsFound() {
        FiscalDataCurrencyRates fiscalDataCurrencyRates = new FiscalDataCurrencyRates(currencyExchangeRates);
        when(currencyExchangeRates.getRates()).thenReturn(null);
        ExchangeRate exchangeRate = fiscalDataCurrencyRates.getPreviousRate(this.getDate(-15), "Cfa Franc", "Burkina Faso");
        assertNull(exchangeRate);
    }

    @Test
    public void shouldNotReturnAnythingWhenThereIsNoRateForTheCurrency() {  
        FiscalDataCurrencyRates fiscalDataCurrencyRates = new FiscalDataCurrencyRates(currencyExchangeRates);
        when(currencyExchangeRates.getRates()).thenReturn(Arrays.asList(
            new ExchangeRate(this.getDate(-10), "Cfa Franc", "Burkina Faso", "Burkina Faso (Cfa Franc)", 605.95),
            new ExchangeRate(this.getDate(-12), "Cfa Franc", "Burkina Faso", "Burkina Faso (Cfa Franc)", 623.13)
        ));
        ExchangeRate exchangeRate = fiscalDataCurrencyRates.getPreviousRate(this.getDate(-15), "Cfa Franc", "Burkina Faso");
        assertNull(exchangeRate);
    }

    private Date getDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }
}
