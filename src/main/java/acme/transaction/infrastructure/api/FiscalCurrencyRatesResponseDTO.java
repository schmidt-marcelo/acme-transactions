package acme.transaction.infrastructure.api;

import java.util.Arrays;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FiscalCurrencyRatesResponseDTO(FiscalCurrencyRateDTO[] data) {

    public Collection<FiscalCurrencyRateDTO> toCollection() {
        return Arrays.asList(data);
    }

}
