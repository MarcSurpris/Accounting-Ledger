Accounting Ledger ApplicationOverviewThis is a console-based Java application designed to manage a simple accounting ledger. It allows users to record financial transactions (deposits and payments), view the ledger with various filters, and generate reports based on time periods, vendors, or custom criteria. Transactions are stored in a CSV file for persistence, making it easy to track financial activities over time.The application is built as a capstone project for Java development fundamentals, demonstrating concepts such as file I/O, object-oriented programming, date/time handling, user input validation, and data filtering/sorting.FeaturesHome Screen: Main menu to add deposits, make payments, access the ledger, or exit.Add Deposit: Record incoming funds with description, vendor, and positive amount.
Make Payment (Debit): Record outgoing funds with description, vendor, and negative amount.

Ledger Screen: View all transactions, deposits only, payments only, or access reports.
Reports Screen: Generate filtered reports including:Month to Date
Previous Month
Year to Date
Previous Year
Search by Vendor
Custom Search (filter by date range, description, vendor, and/or amount)

Transactions are automatically timestamped with the current date and time.
All transactions are displayed in descending order (most recent first) in a pipe-delimited format.
Data persistence via transactions.csv file.

Requirements Java 8 or higher (uses java.time package for date/time operations).
No external libraries required; built with standard Java SDK.

SetupClone the Repository:

git clone https://github.com/MarcSurpris/Accounting-Ledger
cd accounting-ledger-app

Compile the Code:
Compile the Java files using javac:

javac AccountingLedgerApp.java Transaction.java

Run the Application:
Run the main class:

java AccountingLedgerApp

The application will create a transactions.csv file in the current directory if it doesn't exist.

UsageLaunch the app and follow the on-screen menus.
Example Workflow:From Home: Select D to add a deposit (e.g., Description: "Salary", Vendor: "Employer", Amount: 1000.00).
Select P to add a payment (e.g., Description: "Groceries", Vendor: "Amazon", Amount: 89.50 → stored as -89.50).
Select L to enter Ledger: Choose A to view all, or R for reports.
In Reports: Select 6 for Custom Search to filter by multiple criteria (leave fields blank to ignore).

Exit with X from Home.

Sample Transaction in CSV

2025-10-16|14:30:00|Invoice Payment|Amazon|-89.50
2025-10-15|10:15:00|Salary Deposit|Employer|1000.00

Challenge Feature: Custom SearchAllows flexible filtering without values for unused fields.
Example: Search for transactions between 2025-01-01 and 2025-12-31 with "Amazon" in vendor and amount exactly -89.50.

Project StructureAccountingLedgerApp.java: Main application logic, menus, filtering, and file handling.
Transaction.java: Data model for individual transactions with getters and CSV formatting.
transactions.csv: Auto-generated file for storing transactions (do not edit manually).

Class DemonstrationDuration: 10 minutes per student.
Steps:Run the application and demonstrate key screens/scenarios (e.g., adding transactions, viewing reports).
Describe/show one interesting piece of code (e.g., custom search logic or date filtering).
Answer questions from the audience.

GitHub Repository RequirementsPublic repository.
Appropriate commit history (commit for each meaningful piece of work).
Includes this README.md with project description, screen details, and code snippets/examples.

LimitationsNo editing or deleting of existing transactions.
Assumes all inputs are in the current timezone; no manual date entry.
Error handling is basic (e.g., invalid amounts revert the operation).

ContributingFeel free to fork and submit pull requests for improvements, such as adding balance calculations or export features.LicenseThis project is licensed under the MIT License. See LICENSE for details.

