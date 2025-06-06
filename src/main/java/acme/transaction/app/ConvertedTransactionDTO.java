package acme.transaction.app;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import acme.transaction.core.ConvertedTransaction;

public record ConvertedTransactionDTO(
    String id, 
    String description,
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date date, 
    Double totalAmount, 
    String currencyId, 
    String currencyCountry, 
    String currencyDescription, 
    Double rate,
    Double convertedAmount) {
    
    public ConvertedTransactionDTO(ConvertedTransaction convertedTransaction) {
        this(
            convertedTransaction.transactionId(), 
            convertedTransaction.transactionDescription(), 
            convertedTransaction.transactionDate(), 
            convertedTransaction.transactionTotalAmount(), 
            convertedTransaction.currencyId(), 
            convertedTransaction.currencyCountry(), 
            convertedTransaction.currencyDescription(), 
            convertedTransaction.rate(),
            convertedTransaction.convertedAmount()
        );
    }
    
}
