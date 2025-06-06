package acme.transaction.app;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import acme.transaction.core.Transaction;

public record TransactionDTO(
    String id, 
    String description, 
    @JsonFormat(pattern = "yyyy-MM-dd") 
    Date date, 
    Double totalAmount) {

    public TransactionDTO(Transaction transaction) {
        this(transaction.id(), transaction.description(), transaction.date(), transaction.totalAmount());
    }

    public Transaction toTransaction() {
        return new Transaction(description, date, totalAmount, id);
    }

}
