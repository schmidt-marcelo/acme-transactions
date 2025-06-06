package acme.transaction.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class GetTransactionTest {

    @Mock
    private ExchangeRates exchangeRates;
    @Mock
    private Transactions transactions;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(exchangeRates);
        Mockito.reset(transactions);
    }

    @Test
    public void shouldConvertTransaction() {
        Date transactionDate = new Date();
        Date exchangeRateDateBurkinaFaso = this.getDate(-50);
        String transactionId = UUID.randomUUID().toString();
        Transaction transaction = new Transaction("Test Transaction", transactionDate, Double.valueOf(100.0), transactionId) ;
        ExchangeRate burkinaFasoExchangeRate = new ExchangeRate(exchangeRateDateBurkinaFaso, "Cfa Franc", "Burkina Faso", "Burkina Faso (Cfa Franc)", 605.95);
        when(transactions.get(transactionId)).thenReturn(transaction);
        when(exchangeRates.getPreviousRate(transactionDate, "Cfa Franc", "Burkina Faso")).thenReturn(burkinaFasoExchangeRate);
        GetTransaction getTransaction = new GetTransaction(exchangeRates, transactions);
        ConvertedTransaction convertedTransaction = getTransaction.get(transactionId, "Cfa Franc", "Burkina Faso");
        assertEquals(convertedTransaction.transactionTotalAmount(), Double.valueOf(100.0));
        assertEquals(convertedTransaction.currencyId(), "Cfa Franc");
        assertEquals(convertedTransaction.transactionDate(), transactionDate);
        assertEquals(convertedTransaction.rate(), 605.95);
        assertEquals(convertedTransaction.convertedAmount(), Double.valueOf(60595.0));
        
    }

    @Test
    public void shouldNotConvertTransactionWhenExchangeIsMoreThanSixMonthsOld() {
        String transactionId = UUID.randomUUID().toString();
        Date transactionDate = new Date();
        Date exchangeRateDateBurkinaFaso = this.getDate(-185);
        Transaction transaction = new Transaction("Test Transaction", transactionDate, Double.valueOf(100.0), transactionId) ;
        when(transactions.get(transactionId)).thenReturn(transaction);
        ExchangeRate burkinaFasoExchangeRate = new ExchangeRate(exchangeRateDateBurkinaFaso, "Cfa Franc", "Burkina Faso", "Burkina Faso (Cfa Franc)", 605.95);
        when(exchangeRates.getPreviousRate(transactionDate, "Cfa Franc", "Burkina Faso")).thenReturn(burkinaFasoExchangeRate);
        GetTransaction getTransaction = new GetTransaction(exchangeRates, transactions);
        assertThrows(IllegalArgumentException.class, () -> getTransaction.get(transactionId, "Cfa Franc", "Burkina Faso"), "Currency rate is too old");
    }

    @Test
    public void shouldNotConvertTransactionWhenExchangeIsNotFound() {
        String transactionId = UUID.randomUUID().toString();
        Date transactionDate = new Date();
        Transaction transaction = new Transaction("Test Transaction", transactionDate, Double.valueOf(100.0), transactionId) ;
        when(transactions.get(transactionId)).thenReturn(transaction);
        when(exchangeRates.getPreviousRate(transactionDate, "Cfa Franc", "Burkina Faso")).thenReturn(null);
        GetTransaction getTransaction = new GetTransaction(exchangeRates, transactions);
        assertThrows(RecordNotFoundException.class, () -> getTransaction.get(transactionId, "Cfa Franc", "Burkina Faso"), "Currency rate not found");
    }

    @Test
    public void shouldNotConvertTransactionWhenTransactionIsNotFound() {
        String transactionId = UUID.randomUUID().toString();
        when(transactions.get(transactionId)).thenReturn(null);
        GetTransaction getTransaction = new GetTransaction(exchangeRates, transactions);
        assertThrows(RecordNotFoundException.class, () -> getTransaction.get(transactionId, "Cfa Franc", "Burkina Faso"), "Transaction not found");
    }   

    private Date getDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }
}
