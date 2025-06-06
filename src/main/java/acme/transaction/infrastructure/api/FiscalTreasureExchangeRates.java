package acme.transaction.infrastructure.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import acme.transaction.core.ExchangeRate;

@Component
public class FiscalTreasureExchangeRates implements CurrencyExchangeRates {

    private final String URL;
    private final Logger log = LoggerFactory.getLogger(FiscalTreasureExchangeRates.class);

    public FiscalTreasureExchangeRates(@Value("${treasury.exchange.rates.url}") String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("Fiscal data exchange rates URL is required");
        }
        String startDate = LocalDate.now().minusMonths(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.URL = url + "&filter=record_date:gte:" + startDate;
    }

    @Override
    @Cacheable(value = "currencyRates", key = "fiscalTreasureRates")
    public Collection<ExchangeRate> getRates() {
        log.info("Fetching currency rates from {}", this.URL);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<FiscalCurrencyRatesResponseDTO> response = restTemplate.getForEntity(this.URL, FiscalCurrencyRatesResponseDTO.class);
        Collection<FiscalCurrencyRateDTO> exchangeRates = response.getBody().toCollection();
        log.info("Fetched {} currency rates", exchangeRates.size());
        return exchangeRates.stream()
            .map(fr -> new ExchangeRate(fr.recordDate(), fr.currency(),  fr.country(), fr.countryCurrencyDesc(), fr.exchangeRate()))
            .collect(Collectors.toList());
    }

}
