package kr.pet.mvc;

import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private Scanner scanner = new Scanner(System.in);

    public String getPhoneNumber(){
        System.out.println("Enter phone number: ");
        return scanner.nextLine();
    }

    public Customer getCustomerInfo(){
        System.out.println("Enter infos necessary for registration");
        System.out.println("Phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.println("Owner's name: ");
        String ownerName = scanner.nextLine();
        System.out.println("Pet's name: ");
        String petName=scanner.nextLine();
        System.out.println("Address: ");
        String address = scanner.nextLine();
        System.out.println("Species: ");
        String species = scanner.nextLine();
        System.out.println("Birth year: ");
        int birthYear = scanner.nextInt();
        scanner.nextLine();

        return new Customer(phoneNumber,ownerName,petName,address,species,birthYear);
    }

    public MedicalRecord getMedicalRecordInfo(){
        System.out.println("Date visited");
        String date = scanner.nextLine();
        System.out.println("Diasnosis");
        String content = scanner.nextLine();

        return new MedicalRecord(null,date,content);
    }

    public void printMedicalRecordInfo(Customer customer){
        List<MedicalRecord> records = customer.getMedicalRecords();
        System.out.println("["+customer.getPetName()+"]'s patient records");
        for (MedicalRecord record:records){
            System.out.println("| date visited: "+record.getDate());
            System.out.println("| disnosis: "+record.getContent());
            System.out.println("| owner's name: "+customer.getOwnerName());
            System.out.println("| pet's name: "+customer.getPetName());
            System.out.println("| address: "+customer.getAddress());
            System.out.println("| species: "+customer.getSpecies());
            System.out.println("| birth year: "+customer.getBirthYear());
        }
    }

    public void printMessage(String message){
        System.out.println(message);
    }
}
