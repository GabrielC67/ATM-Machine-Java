package rocks.zipcode.ATM;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Account {
	// variables
	private Double checkingBalance = 0.00;
	private Double secondaryCheckingBalance = 0.00;
	private Double savingsBalance = 0.00;
	private Double secondarySavingsBalance = 0.00;
	private String accountType;



	Scanner input = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");

	public Account() {} //Nullary Constructor

	public Account(String accountType, Double balance) {
		this.accountType = accountType;
		if ("Checking".equalsIgnoreCase(accountType)) {
			this.checkingBalance = balance;
		} else if ("Savings".equalsIgnoreCase(accountType)) {
			this.savingsBalance = balance;
		} else {
			throw new IllegalArgumentException("Invalid account type: " + accountType);
		}
	}

	public String getAccountType() {
		return this.accountType;
	}

	public boolean isCheckingAccount() {
		return "Checking".equalsIgnoreCase(accountType);
	}

	public boolean isSavingsAccount() {
		return "Savings".equalsIgnoreCase(accountType);
	}

	public double getCheckingBalance() {
		return checkingBalance;
	}

	public double getSavingsBalance() {
		return savingsBalance;
	}

	public Double getSecondaryCheckingBalance() {
		return secondaryCheckingBalance;
	}

	public void setSecondaryCheckingBalance(Double secondaryCheckingBalance) {
		this.secondaryCheckingBalance = secondaryCheckingBalance;
	}

	public Double getSecondarySavingsBalance() {
		return secondarySavingsBalance;
	}

	public void setSecondarySavingsBalance(Double secondarySavingsBalance) {
		this.secondarySavingsBalance = secondarySavingsBalance;
	}

	public double calcCheckingWithdraw(Double amount) {
		checkingBalance = (checkingBalance - amount);
		return checkingBalance;
	}

	public double calcSavingsWithdraw(Double amount) {
		savingsBalance = (savingsBalance - amount);
		return savingsBalance;
	}

	public double calcCheckingDeposit(Double amount) {
		checkingBalance = (checkingBalance + amount);
		return checkingBalance;
	}

	public double calcSavingsDeposit(Double amount) {
		savingsBalance = (savingsBalance + amount);
		return savingsBalance;
	}

	public void calcCheckingTransfer(Double amount) {
		checkingBalance = checkingBalance - amount;
		savingsBalance = savingsBalance + amount;
	}

	public void calcSavingsTransfer(Double amount) {
		savingsBalance = savingsBalance - amount;
		checkingBalance = checkingBalance + amount;
	}

	public void getCheckingWithdrawInput() {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nCurrent Checking Account Balance: " + moneyFormat.format(checkingBalance));
				System.out.print("\nAmount you want to withdraw from Checking Account: ");
				Double amount = input.nextDouble();
				if ((checkingBalance - amount) >= 0 && amount >= 0) {
					calcCheckingWithdraw(amount);
					System.out.println("\nCurrent Checking Account Balance: " + moneyFormat.format(checkingBalance));
					end = true;
				} else {
					System.out.println("\nBalance Cannot be Negative.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}
		}
	}

	public void getSavingsWithdrawInput() {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingsBalance));
				System.out.print("\nAmount you want to withdraw from Savings Account: ");
				Double amount = input.nextDouble();
				if ((savingsBalance - amount) >= 0 && amount >= 0) {
					calcSavingsWithdraw(amount);
					System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingsBalance));
					end = true;
				} else {
					System.out.println("\nBalance Cannot Be Negative.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}
		}
	}

	public void getCheckingDepositInput() {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nCurrent Checking Account Balance: " + moneyFormat.format(checkingBalance));
				System.out.print("\nAmount you want to deposit from Checking Account: ");
				Double amount = input.nextDouble();
				if ((checkingBalance + amount) >= 0 && amount >= 0) {
					calcCheckingDeposit(amount);
					System.out.println("\nCurrent Checking Account Balance: " + moneyFormat.format(checkingBalance));
					end = true;
				} else {
					System.out.println("\nBalance Cannot Be Negative.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}
		}
	}

	public void getSavingsDepositInput() {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingsBalance));
				System.out.print("\nAmount you want to deposit into your Savings Account: ");
				Double amount = input.nextDouble();

				if ((savingsBalance + amount) >= 0 && amount >= 0) {
					calcSavingsDeposit(amount);
					System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingsBalance));
					end = true;
				} else {
					System.out.println("\nBalance Cannot Be Negative.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}
		}
	}

	public void getTransferInput(String accType) {
		boolean end = false;
		while (!end) {
			try {
				// Prompt for primary/secondary if applicable
				String fromAccount = accType;
				if ("Checking".equalsIgnoreCase(accType)) {
					//System needs to check if there's a secondary checking account.
					System.out.println("Transfer from: \n1. Primary Checking  \n2. Secondary Checking");
					int fromChoice = input.nextInt();
					fromAccount = (fromChoice == 2) ? "Secondary Checking" : "Checking";
				}

				else if ("Savings".equalsIgnoreCase(accType)) {
					//System needs to check if there's a secondary savings account.
					System.out.println("Transfer from: \n1. Primary Savings  \n2. Secondary Savings");
					int fromChoice = input.nextInt();
					fromAccount = (fromChoice == 2) ? "Secondary Savings" : "Savings";
				}

				// Prompt for destination
				System.out.println("Transfer to: \n1. Checking  \n2. Secondary Checking  \n3. Savings  \n4. Secondary Savings  \n5. Exit");
				int toChoice = input.nextInt();

				if (toChoice == 5){
					return;
				}

				String toAccount = switch (toChoice) {
					case 1 -> "Checking";
					case 2 -> "Secondary Checking";
					case 3 -> "Savings";
					case 4 -> "Secondary Savings";
					default -> null;
				};

				if (toAccount == null || toAccount.equals(fromAccount)) {
					System.out.println("\nInvalid or same account selected.");
					System.out.println("\nDo you want to make a transfer? (Y/N)");
					String choice = input.nextLine();

					if (choice.equalsIgnoreCase("y")){
						continue;
					} else if (choice.equalsIgnoreCase("n")){
						end = true;
						break;
					} else{
						System.out.println("Invalid Input");
						return;
					}
				}

				System.out.print("\nAmount to transfer: ");
				Double amount = input.nextDouble();

				// Get balances by reference
				Double fromBalance = getBalanceByType(fromAccount);
				Double toBalance = getBalanceByType(toAccount);

				if (fromBalance != null && toBalance != null && fromBalance - amount >= 0 && amount >= 0) {
					setBalanceByType(fromAccount, fromBalance - amount);
					setBalanceByType(toAccount, toBalance + amount);
					System.out.println("\nTransfer successful.");
					end = true;
				} else {
					System.out.println("\nInsufficient funds or invalid amount.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid input.");
				input.next();
			}
		}
	}

	// Helper methods
	private Double getBalanceByType(String type) {
		return switch (type) {
			case "Checking" -> checkingBalance;
			case "Secondary Checking" -> secondaryCheckingBalance;
			case "Savings" -> savingsBalance;
			case "Secondary Savings" -> secondarySavingsBalance;
			default -> null;
		};
	}

	private void setBalanceByType(String type, Double value) {
		switch (type) {
			case "Checking" -> checkingBalance = value;
			case "Secondary Checking" -> secondaryCheckingBalance = value;
			case "Savings" -> savingsBalance = value;
			case "Secondary Savings" -> secondarySavingsBalance = value;
		}
	}
}
