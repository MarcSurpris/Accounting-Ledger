
package org.Pluralsight;


import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * This class represents the main application for managing an accounting ledger.
 * It handles user interactions for adding deposits, payments, viewing the ledger,
 * and generating reports. Transactions are stored in a CSV file and loaded into memory.
 */
public class AccountingLedgerApp {

    // Constants for the CSV file name and date/time formatters
    private static final String CSV_FILE = "transactions.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    // List to hold all transactions in memory
    private static List<Transaction> transactions = new ArrayList<>();
    // Scanner for reading user input
    private static Scanner scanner = new Scanner(System.in);

    /**
     * The main entry point of the application.
     * Loads transactions from the CSV file and presents the home screen menu in a loop until the user exits.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        loadTransactions();
        boolean running = true;
        while (running) {
            System.out.println("\nHome Screen");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment (Debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim().toUpperCase();
            switch (choice) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    addPayment();
                    break;
                case "L":
                    showLedger();
                    break;
                case "X":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    /**
     * Loads transactions from the CSV file into the transactions list.
     * Skips loading if the file does not exist.
     */
    private static void loadTransactions() {
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0], DATE_FORMATTER);
                    LocalTime time = LocalTime.parse(parts[1], TIME_FORMATTER);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);
                    transactions.add(new Transaction(date, time, description, vendor, amount));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
    }

    /**
     * Saves a new transaction to the CSV file and adds it to the in-memory list.
     * @param transaction The transaction to save.
     */
    private static void saveTransaction(Transaction transaction) {
        transactions.add(transaction);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            bw.write(transaction.toCsvString());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    /**
     * Prompts the user to enter details for a deposit (positive amount) and saves it.
     */
    private static void addDeposit() {
        System.out.println("\nAdd Deposit");
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        System.out.print("Enter description: ");
        String description = scanner.nextLine().trim();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine().trim();
        System.out.print("Enter amount: ");
        double amount = 0;
        try {
            amount = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Deposit not added.");
            return;
        }
        Transaction transaction = new Transaction(date, time, description, vendor, amount);
        saveTransaction(transaction);
        System.out.println("Deposit added successfully.");
    }

    /**
     * Prompts the user to enter details for a payment (negative amount) and saves it.
     */
    private static void addPayment() {
        System.out.println("\nMake Payment (Debit)");
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        System.out.print("Enter description: ");
        String description = scanner.nextLine().trim();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine().trim();
        System.out.print("Enter amount: ");
        double amount = 0;
        try {
            amount = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Payment not added.");
            return;
        }
        Transaction transaction = new Transaction(date, time, description, vendor, -amount);
        saveTransaction(transaction);
        System.out.println("Payment added successfully.");
    }

    /**
     * Displays the ledger menu and handles user choices for viewing transactions or reports.
     */
    private static void showLedger() {
        boolean inLedger = true;
        while (inLedger) {
            System.out.println("\nLedger Screen");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim().toUpperCase();
            switch (choice) {
                case "A":
                    displayTransactions(transactions);
                    break;
                case "D":
                    List<Transaction> deposits = filterDeposits(transactions);
                    displayTransactions(deposits);
                    break;
                case "P":
                    List<Transaction> payments = filterPayments(transactions);
                    displayTransactions(payments);
                    break;
                case "R":
                    showReports();
                    break;
                case "H":
                    inLedger = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Displays the reports menu and handles user choices for generating specific reports.
     */
    private static void showReports() {
        boolean inReports = true;
        while (inReports) {
            System.out.println("\nReports Screen");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search By Vendor");
            System.out.println("6) Custom Search");  // Challenge feature
            System.out.println("0) Back");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    List<Transaction> monthToDate = filterMonthToDate(transactions);
                    displayTransactions(monthToDate);
                    break;
                case "2":
                    List<Transaction> previousMonth = filterPreviousMonth(transactions);
                    displayTransactions(previousMonth);
                    break;
                case "3":
                    List<Transaction> yearToDate = filterYearToDate(transactions);
                    displayTransactions(yearToDate);
                    break;
                case "4":
                    List<Transaction> previousYear = filterPreviousYear(transactions);
                    displayTransactions(previousYear);
                    break;
                case "5":
                    List<Transaction> byVendor = searchByVendor(transactions);
                    displayTransactions(byVendor);
                    break;
                case "6":
                    List<Transaction> custom = customSearch(transactions);
                    displayTransactions(custom);
                    break;
                case "0":
                    inReports = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Filters the list of transactions to include only deposits (positive amounts).
     * @param txns The list of transactions to filter.
     * @return A list of deposit transactions.
     */
    private static List<Transaction> filterDeposits(List<Transaction> txns) {
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction t : txns) {
            if (t.getAmount() > 0) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    /**
     * Filters the list of transactions to include only payments (negative amounts).
     * @param txns The list of transactions to filter.
     * @return A list of payment transactions.
     */
    private static List<Transaction> filterPayments(List<Transaction> txns) {
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction t : txns) {
            if (t.getAmount() < 0) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    /**
     * Filters transactions for the current month to date.
     * @param txns The list of transactions to filter.
     * @return Filtered list of transactions.
     */
    private static List<Transaction> filterMonthToDate(List<Transaction> txns) {
        LocalDate now = LocalDate.now();
        LocalDate start = now.with(TemporalAdjusters.firstDayOfMonth());
        return filterByDateRange(txns, start, now);
    }

    /**
     * Filters transactions for the previous month.
     * @param txns The list of transactions to filter.
     * @return Filtered list of transactions.
     */
    private static List<Transaction> filterPreviousMonth(List<Transaction> txns) {
        LocalDate now = LocalDate.now();
        LocalDate start = now.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate end = now.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        return filterByDateRange(txns, start, end);
    }

    /**
     * Filters transactions for the current year to date.
     * @param txns The list of transactions to filter.
     * @return Filtered list of transactions.
     */
    private static List<Transaction> filterYearToDate(List<Transaction> txns) {
        LocalDate now = LocalDate.now();
        LocalDate start = now.with(TemporalAdjusters.firstDayOfYear());
        return filterByDateRange(txns, start, now);
    }

    /**
     * Filters transactions for the previous year.
     * @param txns The list of transactions to filter.
     * @return Filtered list of transactions.
     */
    private static List<Transaction> filterPreviousYear(List<Transaction> txns) {
        LocalDate now = LocalDate.now();
        LocalDate start = now.minusYears(1).with(TemporalAdjusters.firstDayOfYear());
        LocalDate end = now.minusYears(1).with(TemporalAdjusters.lastDayOfYear());
        return filterByDateRange(txns, start, end);
    }

    /**
     * Searches for transactions by vendor name (case-insensitive partial match).
     * @param txns The list of transactions to search.
     * @return Filtered list of transactions matching the vendor.
     */
    private static List<Transaction> searchByVendor(List<Transaction> txns) {
        System.out.print("Enter vendor name: ");
        String vendor = scanner.nextLine().trim().toLowerCase();
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction t : txns) {
            if (t.getVendor().toLowerCase().contains(vendor)) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    /**
     * Performs a custom search based on user-provided filters for date range, description, vendor, and amount.
     * @param txns The list of transactions to search.
     * @return Filtered list of transactions matching the criteria.
     */
    private static List<Transaction> customSearch(List<Transaction> txns) {
        System.out.println("\nCustom Search");
        System.out.print("Enter start date (yyyy-MM-dd, leave blank for no filter): ");
        String startStr = scanner.nextLine().trim();
        LocalDate startDate = startStr.isEmpty() ? null : LocalDate.parse(startStr, DATE_FORMATTER);

        System.out.print("Enter end date (yyyy-MM-dd, leave blank for no filter): ");
        String endStr = scanner.nextLine().trim();
        LocalDate endDate = endStr.isEmpty() ? null : LocalDate.parse(endStr, DATE_FORMATTER);

        System.out.print("Enter description (leave blank for no filter): ");
        String description = scanner.nextLine().trim().toLowerCase();

        System.out.print("Enter vendor (leave blank for no filter): ");
        String vendor = scanner.nextLine().trim().toLowerCase();

        System.out.print("Enter amount (leave blank for no filter): ");
        String amountStr = scanner.nextLine().trim();
        Double amount = amountStr.isEmpty() ? null : Double.parseDouble(amountStr);

        List<Transaction> filtered = new ArrayList<>();
        for (Transaction t : txns) {
            boolean matches = true;
            if (startDate != null && t.getDate().isBefore(startDate)) {
                matches = false;
            }
            if (endDate != null && t.getDate().isAfter(endDate)) {
                matches = false;
            }
            if (!description.isEmpty() && !t.getDescription().toLowerCase().contains(description)) {
                matches = false;
            }
            if (!vendor.isEmpty() && !t.getVendor().toLowerCase().contains(vendor)) {
                matches = false;
            }
            if (amount != null && t.getAmount() != amount) {
                matches = false;
            }
            if (matches) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    /**
     * Filters transactions within a specified date range (inclusive).
     * @param txns The list of transactions to filter.
     * @param start The start date of the range.
     * @param end The end date of the range.
     * @return Filtered list of transactions.
     */
    private static List<Transaction> filterByDateRange(List<Transaction> txns, LocalDate start, LocalDate end) {
        List<Transaction> filtered = new ArrayList<>();
        for (Transaction t : txns) {
            if (!t.getDate().isBefore(start) && !t.getDate().isAfter(end)) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    /**
     * Displays the list of transactions in descending order by date and time.
     * Prints a header and each transaction in CSV format.
     * @param txns The list of transactions to display.
     */
    private static void displayTransactions(List<Transaction> txns) {
        if (txns.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        List<Transaction> sorted = new ArrayList<>(txns);
        sorted.sort(Comparator.comparing(Transaction::getDate)
                .thenComparing(Transaction::getTime)
                .reversed());
        System.out.println("\ndate|time|description|vendor|amount");
        for (Transaction t : sorted) {
            System.out.println(t.toCsvString());
        }
    }
}