package rocks.zipcode.ATM;

import java.io.IOException;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.*;

public class OptionMenu {
	Scanner menuInput = new Scanner(System.in);
	DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");
	HashMap<Integer, List<Account>> data = new HashMap<>(); //Modified value to represent a list instead of just one value.
	Account account = new Account();

	public void getLogin() throws IOException {
		boolean end = false;
		int customerNumber = 0;
		int pinNumber = 0;
		while (!end) {
			try {
					System.out.print("\nEnter your customer number: ");
					customerNumber = menuInput.nextInt();
					System.out.print("\nEnter your PIN number: ");
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
								System.out.println("Select account:\n");
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
				System.out.println("\nInvalid Character(s). Only Numbers.");
			} //Same thing, customer will try again.
		}
	}

	public void getAccountType(Account acc) { //After login is complete, we go here. Step 3.
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\nSelect the account you want to access: ");
				System.out.println(" Type 1 - Checking Account");
				System.out.println(" Type 2 - Savings Account");//This is where modifications will begin
				System.out.println(" Type 3 - Check Statement balances"); //Statement Balances added
				System.out.println(" Type 4 - Exit");
				System.out.print("\nChoice: ");

				int selection = menuInput.nextInt();

				switch (selection) {
					case 1:
						List <Account> checkingAccounts = data.get(acc.getCustomerNumber()).stream()
								.filter(a -> a.isCheckingAccount()).toList();
					case 2:
						getSaving(acc);
						break;
					case 3:
						System.out.printf("%s", "\nChecking Account Balance: " + moneyFormat.format(acc.getCheckingBalance()));
						System.out.printf("%s", "\nSavings Account Balance: " + account.getSavingBalance());
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
		boolean validCustomer = false;
		while (!validCustomer) {
			try {
				System.out.println("\nEnter your customer number ");
				cst_no = menuInput.nextInt();
				if(!data.containsKey(cst_no)) {
					data.put(cst_no, new ArrayList<>());
					validCustomer = true;
				} else {
					System.out.println("\nThis customer number is already registered");
				}
//				Iterator it = data.entrySet().iterator();
//				while (!) {
//					Map.Entry pair = (Map.Entry) it.next();
//					if (!data.containsKey(cst_no)) {//Checks if the created acct no. exists.
//						end = true;
//					}
//				}
//				if (!end) {
//					System.out.println("\nThis customer number is already registered");
//				}
			} catch (InputMismatchException e) {
				System.out.println("\nInvalid Choice.");
				menuInput.next();
			}
		}
		addMoreAccounts(cst_no);
		System.out.println("\nRedirecting to login.............");
		getLogin();
	}



	public void mainMenu() throws IOException { //Where everything starts from line 10 of ATM class
		data.put(952141, new ArrayList<>(List.of(new Account(952,191904, 1000, 5000))));
		data.put(123, new ArrayList<>(List.of(new Account(123, 123, 20000, 50000))));
		boolean end = false;
		while (!end) {
			try {
				System.out.println("\n Type 1 - Login");
				System.out.println(" Type 2 - Create Account");
				System.out.print("\nChoice: ");
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
		System.out.println("\nThank You for using this ATM.\n");
		menuInput.close();
		System.exit(0);
	}

	private void addMoreAccounts(int cst_no) {
		while (true) {
			System.out.println("\nDo you want to add another account? Select (Y/N):\n");
			String custInput = menuInput.next();
			if (custInput.equalsIgnoreCase("y")) {
				break; // Proceed to add accounts
			} else if (custInput.equalsIgnoreCase("n")) {
				return; // Exit the method
			} else {
				System.out.println("\nInvalid Input! Please enter Y or N.\n");
			}

			boolean addAccount = true;
			while (addAccount) {
				System.out.println("\nEnter PIN to be registered for this account:\n");
				int pin = menuInput.nextInt();

				//This is to distinguish what the user will select when adding a new account into their profile.
				String accountType = "";
				while (true) {
					System.out.println("\nEnter account type (Checking/Savings):\n");
					accountType = menuInput.next();
					if (accountType.equalsIgnoreCase("Checking") || accountType.equalsIgnoreCase("Savings")) {
						break;
					} else {
						System.out.println("\nInvalid Input!");
					}
				}
				Account newAccount = new Account(cst_no, pin, accountType);
				data.get(cst_no).add(newAccount); //Customer's data is now in the system
				System.out.println("\nAccount added successfully!");

				System.out.println("\nDo you want to add another account? Select (Y/N):");
				String response = menuInput.next();
				if (Objects.equals(response, "N")) {
					addAccount = false;
				} else if (Objects.equals(response, "Y")) { //I know this may be redundant, but this is for readability.
					continue;
				} else {
					System.out.println("\nInvalid Input! Please enter Y or N.");
				}
			}
		}
	}
}
