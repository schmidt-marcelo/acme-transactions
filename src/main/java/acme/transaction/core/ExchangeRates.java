package acme.transaction.core;

import java.util.Date;

public interface ExchangeRates {

    ExchangeRate getPreviousRate(Date date, String currencyName, String currencyCountry);
}
