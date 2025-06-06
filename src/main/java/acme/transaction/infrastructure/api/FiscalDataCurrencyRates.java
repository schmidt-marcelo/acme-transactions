package acme.transaction.infrastructure.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

import org.springframework.stereotype.Component;

import acme.transaction.core.ExchangeRate;
import acme.transaction.core.ExchangeRates;

@Component
public class FiscalDataCurrencyRates implements ExchangeRates {

    private final CurrencyExchangeRates currencyExchangeRates;
    
    private final Logger log = LoggerFactory.getLogger(FiscalDataCurrencyRates.class);

    public FiscalDataCurrencyRates(CurrencyExchangeRates currencyExchangeRates) {
        this.currencyExchangeRates = currencyExchangeRates;
    }
    @Override
    public ExchangeRate getPreviousRate(Date date, String currencyName, String currencyCountry) {
        log.info("Getting currency rates");
        Collection<ExchangeRate> exchangeRates = currencyExchangeRates.getRates();        
        log.info("Getting previous rate for {} on {}", currencyName, date);
        if (exchangeRates == null) {
            return null;
        }
        return exchangeRates.stream()
            .sorted(Comparator.comparing(ExchangeRate::date, Comparator.reverseOrder()))
            .filter(currencyRate -> currencyRate.currencyName().equals(currencyName) && currencyRate.currencyCountry().equals(currencyCountry) && currencyRate.date().before(date))
            .findFirst()
            .orElse(null);
    }

    
}
    
