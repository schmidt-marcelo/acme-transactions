package acme.transaction.infrastructure.persistence;

import java.util.Date;

import acme.transaction.core.Transaction;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class TransactionRecord {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    public String id;
    public String description;
    public Date date;
    public Double totalAmount;
    
    TransactionRecord() {}

    public TransactionRecord(String id, String description, Date date, Double totalAmount) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public TransactionRecord(Transaction transaction) {
        this(transaction.id(), transaction.description(), transaction.date(), transaction.totalAmount());
    }

    public Transaction toTransaction() {
        return new Transaction(description, date, totalAmount, id);
    }
}
