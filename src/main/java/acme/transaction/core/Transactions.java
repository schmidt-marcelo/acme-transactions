package acme.transaction.core;

import java.util.Collection;

public interface Transactions {

    Transaction add(Transaction purchase);
    Collection<Transaction> all();
    void remove(Transaction purchase);
    Transaction get(String id);
}
