package rocks.zipcode.ATM;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Account {
	// variables
	private Double checkingBalance = 0.00;
	private Double savingsBalance = 0.00;
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
				if (accType.equals("Checking")) {
					System.out.println("\nSelect an account you wish to transfer funds to:");
					System.out.println("1. Savings");
					System.out.println("2. Exit");
					System.out.print("\nChoice: ");
					int choice = input.nextInt();
					switch (choice) {
					case 1:
						System.out.println("\nCurrent Checking Account Balance: " + moneyFormat.format(checkingBalance));
						System.out.print("\nAmount you want to deposit into your Savings Account: ");
						Double amount = input.nextDouble();
						if ((savingsBalance + amount) >= 0 && (checkingBalance - amount) >= 0 && amount >= 0) {
							calcCheckingTransfer(amount);
							System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingsBalance));
							System.out.println(
									"\nCurrent Checking Account Balance: " + moneyFormat.format(checkingBalance));
							end = true;
						} else {
							System.out.println("\nBalance Cannot Be Negative.");
						}
						break;
					case 2:
						return;
					default:
						System.out.println("\nInvalid Choice.");
						break;
					}
				} else if (accType.equals("Savings")) {
					System.out.println("\nSelect an account you wish to transfer funds to: ");
					System.out.println("1. Checking");
					System.out.println("2. Exit");
					System.out.print("\nChoice: ");
					int choice = input.nextInt();
					switch (choice) {
					case 1:
						System.out.println("\nCurrent Savings Account Balance: " + moneyFormat.format(savingsBalance));
						System.out.print("\nAmount you want to deposit into your savings account: ");
						Double amount = input.nextDouble();
						if ((checkingBalance + amount) >= 0 && (savingsBalance - amount) >= 0 && amount >= 0) {
							calcSavingsTransfer(amount);
							System.out.println("\nCurrent checking account balance: " + moneyFormat.format(checkingBalance));
							System.out.println("\nCurrent savings account balance: " + moneyFormat.format(savingsBalance));
							end = true;
						} else {
							System.out.println("\nBalance Cannot Be Negative.");
						}
						break;
					case 2:
						return;
					default:
						System.out.println("\nInvalid Choice.");
						break;
					}
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				input.next();
			}
		}
	}
}
