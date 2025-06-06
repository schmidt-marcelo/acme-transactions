package acme.transaction.core;

import java.util.Date;

public class Transaction {

    private String id = null;
    private String description;
    private Date date;
    private Double totalAmount;
    
    public Transaction(String description, Date date, Double totalAmount) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description must be provided");
        }
        if (description.length() > 50) {
            throw new IllegalArgumentException("Description cannot be longer than 50 characters");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date must be provided");
        }
        if (totalAmount == null || totalAmount <= 0) {
            throw new IllegalArgumentException("Total amount must be provided and greater than 0");
        }
        this.description = description;
        this.date = date;
        this.totalAmount = totalAmount;
    }

    public Transaction(String description, Date date, Double totalAmount, String id) {
        this(description, date, totalAmount);
        this.id = id;
    }

    public String id() {
        return id;
    }

    public String description() {
        return description;
    }

    public Date date() {
        return date;
    }

    public Double totalAmount() {
        return totalAmount;
    }

    public String toString() {
        return "Transaction [id=" + id + ", description=" + description + ", date=" + date + ", totalAmount=" + totalAmount + "]";
    }

    public Transaction clone() {
        return new Transaction(this.description, this.date, this.totalAmount, this.id);
    }

}
