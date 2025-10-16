package org.Pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * This class represents a single transaction in the accounting ledger.
 * It stores the date, time, description, vendor, and amount of the transaction.
 */
public class Transaction {
    // Date of the transaction
    private LocalDate date;
    // Time of the transaction
    private LocalTime time;
    // Description of the transaction
    private String description;
    // Vendor involved in the transaction
    private String vendor;
    // Amount of the transaction (positive for deposit, negative for payment)
    private double amount;

    /**
     * Constructor to create a new Transaction.
     * @param date The date of the transaction.
     * @param time The time of the transaction.
     * @param description The description of the transaction.
     * @param vendor The vendor of the transaction.
     * @param amount The amount of the transaction.
     */
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    /**
     * Gets the date of the transaction.
     * @return The date.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the time of the transaction.
     * @return The time.
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Gets the description of the transaction.
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the vendor of the transaction.
     * @return The vendor.
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * Gets the amount of the transaction.
     * @return The amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Converts the transaction to a CSV-formatted string.
     * @return The CSV string representation.
     */
    public String toCsvString() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "|" +
                time.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "|" +
                description + "|" +
                vendor + "|" +
                amount;
    }
}