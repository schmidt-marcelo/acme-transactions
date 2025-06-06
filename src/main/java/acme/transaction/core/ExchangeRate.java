package acme.transaction.core;

import java.util.Date;

public class ExchangeRate {
    private final Date date;
    private final String currencyName;
    private final String currencyCountry;
    private final String currencyDescription;
    private final Double rate;

    public ExchangeRate(Date date, String currencyName, String currencyCountry, String currencyDescription, Double rate) {
        if (date == null) {
            throw new IllegalArgumentException("Invalid exchange rate date: null");
        }
        if (currencyName == null || currencyName.isEmpty()) {
            throw new IllegalArgumentException("Invalid exchange rate currency: null or empty");
        }
        if (currencyCountry == null || currencyCountry.isEmpty()) {
            throw new IllegalArgumentException("Invalid exchange rate currency country: null or empty");
        }
        if (currencyDescription == null) {
            throw new IllegalArgumentException("Invalid exchange rate currency description: null");
        }
        if (rate == null || rate <= 0) {
            throw new IllegalArgumentException("Invalid exchange rate coeficient: null or <= 0");
        }
        this.date = date;
        this.currencyName = currencyName;
        this.currencyCountry = currencyCountry;
        this.currencyDescription = currencyDescription;
        this.rate = rate;
    }

    public Date date() {
        return date;
    }

    public String currencyName() {
        return currencyName;
    }

    public String currencyCountry() {
        return currencyCountry;
    }

    public String currencyDescription() {
        return currencyDescription;
    }

    public Double rate() {
        return rate;
    }

    public String toString() {
        return "CurrencyRate [date=" + date + ", currencyName=" + currencyName + ", currencyCountry=" + currencyCountry + ", currencyDescription=" + currencyDescription + ", rate=" + rate + "]";
    }

    public ExchangeRate clone() {
        return new ExchangeRate(this.date, this.currencyName, this.currencyCountry, this.currencyDescription, this.rate);
    }
}
