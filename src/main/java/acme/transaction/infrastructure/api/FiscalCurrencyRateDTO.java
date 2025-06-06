package acme.transaction.infrastructure.api;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FiscalCurrencyRateDTO(
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("record_date")
    Date recordDate,
    @JsonProperty("country")
    String country,
    @JsonProperty("currency")
    String currency,
    @JsonProperty("country_currency_desc")
    String countryCurrencyDesc,
    @JsonProperty("exchange_rate")
    Double exchangeRate,
    @JsonProperty("effective_date")
    Date effectiveDate
    
) {
    
}
