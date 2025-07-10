package rocks.zipcode.ATM;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class OptionMenu {
	Scanner menuInput = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");
	HashMap<CustomerInformation, List<Account>> data = new HashMap<>(); //Modified value to represent a list instead of just one value.
	Account account = new Account();

	public void getLogin() throws IOException {
		boolean end = false;
		int customerNumber = 0;
		int pinNumber = 0;
		while (!end) {
			try {
					System.out.println("Enter your customer number: ");
					customerNumber = menuInput.nextInt();
					System.out.println("Enter your PIN number: ");
					pinNumber = menuInput.nextInt(); //Customer inputs PIN Number
					List<Account> accounts = data.get(customerNumber);

					if (accounts != null) {
					// Find accounts with matching PIN
						int finalPinNumber = pinNumber;
						List<Account> matchedAccounts = accounts.stream()
							.filter(acc -> acc.getPinNumber() == finalPinNumber)
							.toList();
						if (!matchedAccounts.isEmpty()) {
							// If multiple, let user select
							if (matchedAccounts.size() > 1) {
								System.out.println("Select account:");
								for (int i = 0; i < matchedAccounts.size(); i++) {
									System.out.println((i + 1) + " - Account #" + (i + 1)); //I'll come back to modify this later to show the choice between accounts.
								}
								int accChoice = menuInput.nextInt() - 1;
								getAccountType(matchedAccounts.get(accChoice));
							} else {
								getAccountType(matchedAccounts.get(0));
							}
							end = true;
						}

						if (!end) {
							System.out.println("\nWrong Customer Number or Pin Number");
						} //Customer will have to try again due to this being in While loop.
					}
			} catch (InputMismatchException e) {
				System.out.println("Invalid Character(s). Only Numbers.");
			} //Same thing, customer will try again.
		}
	}

	public void getAccountType(Account acc) { //After login is complete, we go here. Step 3.
		boolean end = false;
		while (!end) {
			try {
				System.out.println("Select the account you want to access: ");
				System.out.println(" Type 1 - Checking Account");
				System.out.println(" Type 2 - Savings Account");//This is where modifications will begin
				System.out.println(" Type 3 - Check Statement balances"); //Statement Balances added
				System.out.println(" Type 4 - Exit");
				System.out.println("Choice: ");

				int selection = menuInput.nextInt();

				switch (selection) {
					case 1:{
						// Filter for checking accounts
						List<Account> checkingAccounts = data.get(acc.getCustomerNumber()).stream()
								.filter(Account::isCheckingAccount) // Implement this method in Account
								.toList();
						if (checkingAccounts.size() > 1) {
							System.out.println("Select Checking Account:\n");
							for (int i = 0; i < checkingAccounts.size(); i++) {
								System.out.println((i + 1) + " - Account #" + (i + 1) +"\n");
							}
							int idx = menuInput.nextInt() - 1;
							getChecking(checkingAccounts.get(idx));
						} else if (checkingAccounts.size() == 1) {
							getChecking(checkingAccounts.get(0));
						} else {
							System.out.println("No checking accounts found.\n");
						}
						break;
					}
					case 2:
					{
						// Filter for savings accounts
						List<Account> savingsAccounts = data.get(acc.getCustomerNumber()).stream()
								.filter(Account::isSavingsAccount) // Implement this method in Account
								.toList();
						if (savingsAccounts.size() > 1) {
							System.out.println("Select Savings Account:");
							for (int i = 0; i < savingsAccounts.size(); i++) {
								System.out.println((i + 1) + " - Account #" + (i + 1));
							}
							int idx = menuInput.nextInt() - 1;
							getSaving(savingsAccounts.get(idx));
						} else if (savingsAccounts.size() == 1) {
							getSaving(savingsAccounts.get(0));
						} else {
							System.out.println("No savings accounts found.");
						}
						break;
					}
					case 3:
						System.out.printf("%s", "\nChecking Account Balance: " + moneyFormat.format(acc.getCheckingBalance()));
						System.out.printf("%s", "\nSavings Account Balance: " + moneyFormat.format(acc.getSavingBalance()));
						break;
					case 4:
						end = true;
						break;
					default:
						System.out.println("\nInvalid Choice.");
					}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}

	public void getChecking(Account acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nChecking Account: ");
				System.out.println(" Type 1 - View Balance");
				System.out.println(" Type 2 - Withdraw Funds");
				System.out.println(" Type 3 - Deposit Funds");
				System.out.println(" Type 4 - Transfer Funds");
				System.out.println(" Type 5 - Exit");
				System.out.print("\nChoice: ");

				int selection = menuInput.nextInt();

				switch (selection) {
				case 1:
					System.out.println("\nChecking Account Balance: " + moneyFormat.format(acc.getCheckingBalance()));
					break;
				case 2:
					acc.getCheckingWithdrawInput();
					break;
				case 3:
					acc.getCheckingDepositInput();
					break;

				case 4:
					acc.getTransferInput("Checking");
					break;
				case 5:
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}

	public void getSaving(Account acc) {
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nSavings Account: ");
				System.out.println(" Type 1 - View Balance");
				System.out.println(" Type 2 - Withdraw Funds");
				System.out.println(" Type 3 - Deposit Funds");
				System.out.println(" Type 4 - Transfer Funds");
				System.out.println(" Type 5 - Exit");
				System.out.print("Choice: ");
				int selection = menuInput.nextInt();
				switch (selection) {
				case 1:
					System.out.println("\nSavings Account Balance: " + moneyFormat.format(acc.getSavingBalance()));
					break;
				case 2:
					acc.getsavingWithdrawInput();
					break;
				case 3:
					acc.getSavingDepositInput();
					break;
				case 4:
					acc.getTransferInput("Savings");
					break;
				case 5:
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
	}

	public void createAccount() throws IOException {
		int cst_no = 0;
		String input = "";
		String inputNewPIN = "", inputNewPINAgain = "";
		CustomerInformation customer = new CustomerInformation();
		Iterator<Map.Entry<CustomerInformation, List<Account>>> it = data.entrySet().iterator();
		boolean validCustomer = false;
		while (!validCustomer) {
			try {
				System.out.println("\nEnter your customer number: ");
				cst_no = menuInput.nextInt();
				Map.Entry<CustomerInformation, List<Account>> pair = it.next();
				if(!data.containsKey(cst_no)) {
					System.out.println("\n Welcome to ZipTM! Enter your new PIN \n Your PIN must be at least 4 numbers: ");
					inputNewPIN = menuInput.next();

					if(inputNewPIN.matches("\\d{4,}")){
						int pinNumber = Integer.parseInt(inputNewPIN);
						System.out.println("Great!\n Please enter your PIN again to confirm.");
						inputNewPINAgain = menuInput.next();

						if (inputNewPINAgain.matches("\\d{4,}")) {
							int confirmationPIN = Integer.parseInt(inputNewPINAgain);

							if (confirmationPIN != pinNumber) {
								System.out.println("\n Your PINs do not match! Do you want to try again? (Y/N): ");
								input = menuInput.next();
								if(input.equalsIgnoreCase("N")){
									return;
								} else if(!input.equalsIgnoreCase("Y")) {
									throw new IllegalArgumentException("Invalid Input! Please try again.");
								} else {
									continue;
								}
							} else {
								data.put(customer, new ArrayList<Account>());
								validCustomer = true;

								}
							}
						}
					} else {
						System.out.println("Your PIN must be at least 4 digits and all numbers.");
					}
			if (!validCustomer) {
					System.out.println("\nThis customer number is already registered");
				}

//					if (!data.containsKey(cst_no)) {//Checks if the created acct no. exists.
//						end = true;
//					}
//				}
//
			} catch (InputMismatchException e) {
				System.out.println("Invalid Choice.");
				menuInput.next();
			}
			addMoreAccounts(customer);
		System.out.println("Redirecting to login.............");
		getLogin();
	}



	public void mainMenu() throws IOException { //Where everything starts from line 10 of ATM class
		//Modified the Map completely to remove redundancies and bottlenecks.

//		data.put((952141, 191904), new ArrayList<>(List.of(new Account(, 1000, 5000))));
//		data.put(123, new ArrayList<>(List.of(new Account(123, 123, 20000, 50000))));
		initializeSampleData();
		boolean end = false;
		while (!end) {
			try {
				System.out.println("Type 1 - Login");
				System.out.println("Type 2 - Sign Up");
				System.out.print("Choice: ");
				int choice = menuInput.nextInt();
				switch (choice) {
				case 1:
					getLogin();
					end = true;
					break;
				case 2:
					createAccount();
					end = true;
					break;
				default:
					System.out.println("\nInvalid Choice.");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		System.out.println("\nThank You for using this ATM.");
		menuInput.close();
		System.exit(0);
	}

	private void addMoreAccounts(CustomerInformation customer) {
		while (true) {
			System.out.println("\nDo you want to add another account? Select (Y/N):");
			String custInput = menuInput.next();
			if (custInput.equalsIgnoreCase("y")) {
				break; // Proceed to add accounts
			} else if (custInput.equalsIgnoreCase("n")) {
				return; // Exit the method
			} else {
				System.out.println("\nInvalid Input! Please enter Y or N.");
			}

			boolean addAccount = true;
			while (addAccount) {
				System.out.println("\nEnter your PIN to add an account:");
				int pin = menuInput.nextInt();
				if (pin != customer.getPinNumber()) {
					System.out.println("Incorrect PIN. Try again.");
					continue;
				}

				//This is to distinguish what the user will select when adding a new account into their profile.
				String accountType = "";
				while (true) {
					System.out.println("\nEnter account type (Checking/Savings):");
					accountType = menuInput.next();
					if (accountType.equalsIgnoreCase("Checking") || accountType.equalsIgnoreCase("Savings")) {
						break;
					} else {
						System.out.println("\nInvalid Input!");
					}
				}
				Account newAccount = new Account();
				data.get(customer).add(newAccount); //Customer's data is now in the system
				System.out.println("\nAccount added successfully!");

				System.out.println("\nDo you want to add another account? Select (Y/N):");
				String response = menuInput.next();
				if (response.equalsIgnoreCase("n")) {
					addAccount = false;
				} else if (!response.equalsIgnoreCase("y")) {
					System.out.println("\nInvalid Input! Please enter Y or N.");
				}
			}
		}
	}

	void initializeSampleData() {
		CustomerInformation customer1 = new CustomerInformation(952141, 191904);
		Account checking1 = new Account("Checking", 1000.00);
		Account savings1 = new Account("Saving", 5000.00);
		List<Account> accounts1 = new ArrayList<>();
		accounts1.add(checking1);
		accounts1.add(savings1);
		data.put(customer1, accounts1);

		CustomerInformation customer2 = new CustomerInformation(123, 123);
		Account checking2 = new Account("Checking", 20000.00);
		Account savings2 = new Account("Saving", 50000.00);
		List<Account> accounts2 = new ArrayList<>();
		accounts2.add(checking2);
		accounts2.add(savings2);
		data.put(customer2, accounts2);
	}
}
