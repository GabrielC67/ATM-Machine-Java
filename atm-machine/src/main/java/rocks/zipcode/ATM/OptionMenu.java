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

	//Step 3: After login is complete, and/or accounts are added to the user profile -> This is step 3.//
	public void getAccountType(Account acc) {
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

	//Step 2: Profile will be created here.-> Step 2
	public void createProfile() throws IOException {
		CustomerInformation customer;

		int cst_no = 0;
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nEnter your customer number ");
				cst_no = menuInput.nextInt();
                for (Map.Entry<CustomerInformation, List<Account>> pair : data.entrySet()) {
                    CustomerInformation key = pair.getKey();
                    if (key.getCustomerNumber() == cst_no) {//Checks if the created acct no. exists.
                        end = true;
                        break;
                    }
                }
				if (!end) {
					System.out.println("\nThis customer number is already registered");
				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		System.out.println("\nEnter PIN to be registered");
		int pin = menuInput.nextInt();
		CustomerInformation newCustomer = new CustomerInformation(cst_no, pin);
		List<Account> newCustomerAccount = addBankAccount(newCustomer);
		data.put(newCustomer, newCustomerAccount); //Customer's data is now in the system
		System.out.println("\nYour new account has been successfuly registered!");
		System.out.println("\nRedirecting to login.............");
		getLogin();
	}


	//Step 1: Where everything starts from line 10 of ATM class -> Step 1
	public void mainMenu() throws IOException {
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
					createProfile();
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

	public List<Account> addBankAccount(CustomerInformation customer) throws IOException {
		List<Account> accounts = new ArrayList<>();
		boolean end = false;
		String accountType = "";
		double initialAccountBalance = 0;
		System.out.println("\nDo you want to add a bank account? Select (Y/N):");
		String custInput = menuInput.next();
		while (!end) {
			try {
				if (custInput.equalsIgnoreCase("n")) {
					end = true;
					return accounts; // breaks out the while loop - Still needs a return.
				} else if (custInput.equalsIgnoreCase("y")) {
					try {
						System.out.println("Select account type to add:");
						System.out.println("Type 1 - Checking Account");
						System.out.println("Type 2 - Savings Account");
						System.out.println("Type 3 - Exit");
						int response = menuInput.nextInt();
						System.out.println("Enter initial deposit amount:");
						initialAccountBalance = menuInput.nextDouble();

						switch (response) {
							case 1:
								accounts.add(new Account("Checking", initialAccountBalance));
								System.out.println("Checking account added");
							case 2:
								accounts.add(new Account("Savings", initialAccountBalance));
								System.out.println("Savings account added");
							case 3:
								end = true;
								break;
							default:
								System.out.println("Invalid choice. Please select from the following menu.");
						}
					} catch (InputMismatchException e){
						System.out.println("Invalid input. Please select from the following menu");
					} //End of Second catch block
				}
			} catch (InputMismatchException e){
				System.out.println("\nInvalid Choice. Please enter (Y for Yes or N for No.)");
			} //End of  First catch block
			System.out.println("\nDo you want to add another bank account? Select (Y/N):");
		} //End of While loop
		return accounts;
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

	CustomerInformation findCustomerByNumber(int customerNumber) {
		for (CustomerInformation customer : data.keySet()) {
			if (customer.getCustomerNumber() == customerNumber) {
				return customer;
			}
		}
		return null;
	}
}


