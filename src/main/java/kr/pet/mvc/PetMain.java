package kr.pet.mvc;

import java.util.List;
import java.util.Scanner;

public class PetMain {
    public static void main(String[] args) {
        MedicalRecordController recordController = new MedicalRecordController();
        CustomerController customerController = new CustomerController(recordController);
        ConsoleView view = new ConsoleView();

        while (true){
            System.out.println("---Vet Record Management System---");
            System.out.println("1. Registration for new patient");
            System.out.println("2. Save diagnosis");
            System.out.println("3. Access records");
            System.out.println("4. Delete records");
            System.out.println("5. Delete account");
            System.out.println("6. Exit");
            System.out.println("---Choose a number---");

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    Customer newCustomer = view.getCustomerInfo();
                    String phoneNumber = newCustomer.getPhoneNumber();
                    if (customerController.isRegistered(phoneNumber)){
                        view.printMessage("You're already registered.");
                        continue;
                    }
                    customerController.addCustomer(newCustomer);
                    view.printMessage("You are now registered.");
                    break;

                case 2:
                    phoneNumber = view.getPhoneNumber();
                    if (customerController.findCustomer(phoneNumber)==null){
                        view.printMessage("Number not registered.");
                        break;
                    }
                    Customer customer = customerController.findCustomer(phoneNumber);
                    MedicalRecord newRecord = view.getMedicalRecordInfo();
                    newRecord.setPhoneNumber(phoneNumber);
                    recordController.addMedicalRecord(newRecord);
                    customer.addMedicalRecords(newRecord);
                    break;

                case 3:
                    phoneNumber = view.getPhoneNumber();
                    List<MedicalRecord> records = recordController.findMedicalRecord(phoneNumber);
                    if (records.isEmpty()){
                        view.printMessage("No record exists.");
                        break;
                    }
                    customer = customerController.findCustomer(phoneNumber);
                    view.printMedicalRecordInfo(customer);
                    break;

                case 4:
                    phoneNumber = view.getPhoneNumber();
                    if (customerController.findCustomer(phoneNumber)==null){
                        view.printMessage("Number not registered.");
                        break;
                    }

                    recordController.removeMedicalRecord(phoneNumber);
                    view.printMessage("Your records have been deleted.");
                    break;

                case 5:
                    phoneNumber = view.getPhoneNumber();
                    if (customerController.isRegistered(phoneNumber)){
                        view.printMessage("Delete account? (Y/N)");
                        String ans = scanner.nextLine();
                        if (ans.equals("Y")){
                            customerController.removeCustomer(phoneNumber);
                            view.printMessage("Account deleted");
                            break;
                        } else if (ans.equals("N")){
                            break;
                        } else {
                            view.printMessage("Wrong input, account was not deleted. ");
                            break;
                        }

                    } else {
                        view.printMessage("Number not registered.");
                        break;
                    }

                case 6:
                    System.out.println("Shutting off.");
                    return;
                default:
                    System.out.println("Wrong input.");
                    break;
            }
        }
    }
}
