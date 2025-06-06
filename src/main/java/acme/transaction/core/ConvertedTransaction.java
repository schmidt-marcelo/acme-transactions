package acme.transaction.core;

import java.util.Date;

public class ConvertedTransaction {

    private final Transaction transaction;
    private final ExchangeRate currencyRate;
    private final Double convertedAmount;

    public ConvertedTransaction(Transaction purchase, ExchangeRate currencyRate, Double convertedAmount) {
        if (purchase == null) {
            throw new IllegalArgumentException("Purchase cannot be null");
        }
        if (currencyRate == null) {
            throw new IllegalArgumentException("Currency rate cannot be null");
        }
        this.transaction = purchase;
        this.currencyRate = currencyRate;
        this.convertedAmount = convertedAmount;
    }

    public String transactionId() {
        return this.transaction.id();
    }

    public String transactionDescription() {
        return this.transaction.description();
    }

    public Date transactionDate() {
        return this.transaction.date();
    }

    public Double transactionTotalAmount() {
        return this.transaction.totalAmount();
    }

    public Transaction transaction() {
        return this.transaction;
    }

    public ExchangeRate currencyRate() {
        return this.currencyRate;
    }

    public Double rate() {
        return this.currencyRate.rate();
    }

    public String currencyId() {
        return this.currencyRate.currencyName();
    }

    public String currencyCountry() {
        return this.currencyRate.currencyCountry();
    }

    public String currencyDescription() {
        return this.currencyRate.currencyDescription();
    }

    public Double convertedAmount() {
        return this.convertedAmount;
    }

    public String toString() {
        return "ConvertedTransaction [transaction=" + transaction + ", currencyRate=" + currencyRate + ", convertedAmount=" + convertedAmount + "]";
    }
}
