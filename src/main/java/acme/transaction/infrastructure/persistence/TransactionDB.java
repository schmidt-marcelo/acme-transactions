package acme.transaction.infrastructure.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import acme.transaction.core.Transaction;
import acme.transaction.core.Transactions;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class TransactionDB implements Transactions {

    private final Logger log = LoggerFactory.getLogger(TransactionDB.class);
    private final TransactionRepository repository;

    public TransactionDB(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Transaction add(Transaction transaction) {
        log.info("Persisting transaction: {}", transaction);
        TransactionRecord record = this.repository.save(new TransactionRecord(transaction));
        log.info("Transaction persisted");
        return record.toTransaction();
    }

    @Override
    public Collection<Transaction> all() {
        log.info("Getting all transactions from database");
        Collection<Transaction> transactions = this.repository.findAll().stream().map(TransactionRecord::toTransaction).collect(Collectors.toList());
        log.info("Found {} transactions", transactions.size());
        return transactions;
    }

    @Override
    public void remove(Transaction transaction) { 
        log.info("Removing transaction from database: {}", transaction);
        this.repository.deleteById(transaction.id());
        log.info("Transaction removed");
    }

    @Override
    public Transaction get(String id) {
        log.info("Getting transaction from database: {}", id);  
        Transaction transaction = this.repository.findById(id).map(TransactionRecord::toTransaction).orElse(null);
        log.info("Transaction found");
        return transaction;
    }
}
