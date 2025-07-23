package rocks.zipcode.ATM;

import java.io.IOException;

public class ATM {

	public static void main(String[] args) throws IOException {
		OptionMenu optionMenu = new OptionMenu();
		introduction(); //--> Line 13
		optionMenu.mainMenu(); // --> Line 249 of OptionMenu class
	}

	public static void introduction() {
		System.out.println("Welcome to the ATM Project!");
	}
}
