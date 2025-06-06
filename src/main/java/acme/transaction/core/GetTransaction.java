package acme.transaction.core;

import java.time.Duration;

public class GetTransaction {

    private final ExchangeRates currencyRates;
    private final Transactions transactions;

    public GetTransaction(ExchangeRates currencyRates, Transactions purchases) {
        this.currencyRates = currencyRates;
        this.transactions = purchases;
    }

    public ConvertedTransaction get(String id, String currency, String country) {
        Transaction transaction = this.transactions.get(id);
        if (transaction == null) {
            throw new RecordNotFoundException("Transaction not found");
        }
        ExchangeRate currencyRate = this.currencyRates.getPreviousRate(transaction.date(), currency, country);
        if (currencyRate == null) {
            throw new RecordNotFoundException("Currency rate not found");
        }
        long diffInDays = Duration.between(currencyRate.date().toInstant(), transaction.date().toInstant()).toDays();
        if (diffInDays > 180) {
            throw new IllegalArgumentException("Currency rate is too old");
        }
        Double convertedAmount = (double) Math.round(((transaction.totalAmount() * 100) * currencyRate.rate())) / 100.0;
        return new ConvertedTransaction(transaction, currencyRate, convertedAmount);
    }

}
