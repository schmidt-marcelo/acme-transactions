package acme.transaction.infrastructure.api;

import java.util.Collection;

import acme.transaction.core.ExchangeRate;

public interface CurrencyExchangeRates {

    Collection<ExchangeRate> getRates();
}
