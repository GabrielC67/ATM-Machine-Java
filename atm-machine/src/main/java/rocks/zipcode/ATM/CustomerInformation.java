package rocks.zipcode.ATM;

import java.util.Objects;
import java.util.Scanner;

public class CustomerInformation {
    private int customerNumber;
    private int pinNumber;

    Scanner input = new Scanner(System.in);

    public CustomerInformation(){
    } //Nullary constructor


    public CustomerInformation(int customerNumber, int pinNumber) { //Constructor specifically for the Key.
        this.customerNumber = customerNumber;
        this.pinNumber = pinNumber;
    }

    public int setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
        return customerNumber;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public int setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
        return pinNumber;
    }

    public int getPinNumber() {
        return pinNumber;
    }


    //Old equals and hashcode methods
//    // Override equals and hashCode for use as HashMap key
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof CustomerInformation)) return false;
//        CustomerInformation that = (CustomerInformation) o;
//        return customerNumber == that.customerNumber && pinNumber == that.pinNumber;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(customerNumber, pinNumber);
//    }
//
//    //This will allow the user to change their PIN
//    public void getPinChangeInput() {
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            System.out.print("Enter your new PIN (digits only): ");
//            String input = scanner.nextLine();
//            if (input.matches("\\d+")) {
//                this.pinNumber = Integer.parseInt(input);
//                System.out.println("PIN changed successfully.");
//                break;
//            } else {
//                System.out.println("Invalid PIN. Please enter digits only.");
//            }
//        }
//    }


    //New Override and Hashcode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerInformation that = (CustomerInformation) o;
        return customerNumber == that.customerNumber && pinNumber == that.pinNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerNumber, pinNumber);
    }
}
