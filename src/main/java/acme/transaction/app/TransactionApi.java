package acme.transaction.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import acme.transaction.core.ConvertedTransaction;
import acme.transaction.core.ExchangeRates;
import acme.transaction.core.GetTransaction;
import acme.transaction.core.RecordNotFoundException;
import acme.transaction.core.Transaction;
import acme.transaction.core.Transactions;

@RestController
@RequestMapping("/transactions")
public class TransactionApi {

    private final Transactions transactions;
    private final GetTransaction getConvertedTransaction;
    private final Logger log = LoggerFactory.getLogger(TransactionApi.class);

    public TransactionApi(Transactions transactions, ExchangeRates currencyRates) {
        this.transactions = transactions;
        this.getConvertedTransaction = new GetTransaction(currencyRates, transactions);
    }
    
    @GetMapping("/")
    public ResponseEntity<List<TransactionDTO>> all() {
        log.info("Getting all transactions");
        Collection<Transaction> transactions = this.transactions.all();
        if (transactions == null || transactions.isEmpty()) {
            log.info("No transactions found");
            return ResponseEntity.ok(new ArrayList<TransactionDTO>());
        }
        log.info("Found {} transactions", transactions.size());
        return ResponseEntity.ok(transactions.stream().map(TransactionDTO::new).collect(Collectors.toList()));
    }
    
    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody TransactionDTO transactionRequest) {
        try {
            log.info("Adding transaction: {}", transactionRequest);
            Transaction transaction = transactionRequest.toTransaction();
            log.info("Transaction added");
            return ResponseEntity.ok(new TransactionDTO(this.transactions.add(transaction)));
        } catch (IllegalArgumentException e) {
            log.error("Insafisfied requirement: {}", e.getMessage());
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable String id) {
        log.info("Removing transaction: {}", id);
        Transaction transaction = this.transactions.get(id);
        if (transaction != null) {
            log.info("Transaction removed");
            this.transactions.remove(transaction);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/converted")
    public ResponseEntity<?> findConverted(@PathVariable String id, 
        @RequestParam(required = true) String currency, 
        @RequestParam(required = true) String country) {
        try{
            log.info("Getting converted transaction: {} for currency: {} and country: {}", id, currency, country);
            ConvertedTransaction transaction = this.getConvertedTransaction.get(id, currency, country); 
            if (transaction != null) {
                log.info("Transaction converted");
                return ResponseEntity.ok(new ConvertedTransactionDTO(transaction));
            }
            return ResponseEntity.notFound().build();
        } catch (RecordNotFoundException e) {
            log.error("Record not found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatusCode.valueOf(404)).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Unsatisfied requirement: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> findById(@PathVariable String id) {
        log.info("Getting transaction: {}", id);
        Transaction transaction = this.transactions.get(id);
        if (transaction != null) {
            log.info("Transaction found");
            return ResponseEntity.ok(new TransactionDTO(transaction));
        }
        log.info("Transaction not found");
        return ResponseEntity.notFound().build();
    }
     
}
