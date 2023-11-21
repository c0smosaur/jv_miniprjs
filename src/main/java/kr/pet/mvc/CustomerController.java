package kr.pet.mvc;

import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    private List<Customer> customers;
    private MedicalRecordController recordController;

    public CustomerController(){}

    public CustomerController(MedicalRecordController recordController){
        this.customers = new ArrayList<>();
        this.recordController = recordController;
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void removeCustomer(String phoneNumber){
        for (int i=0;i<customers.size();i++){
            if (customers.get(i).getPhoneNumber().equals(phoneNumber)){
                customers.remove(i);
                recordController.removeMedicalRecord(phoneNumber);
                break;
            }
        }
    }

    public Customer findCustomer(String phoneNumber){
        for (Customer customer: customers){
            if (customer.getPhoneNumber().equals(phoneNumber)){
                return customer;
            }
        }
        return null; // no match
    }

    public boolean isRegistered(String phoneNumber){
        for (Customer customer: customers){
            if (customer.getPhoneNumber().equals(phoneNumber)){
                return true; // the phone number is registered
            }
        }
        return false; // not registered
    }
}
