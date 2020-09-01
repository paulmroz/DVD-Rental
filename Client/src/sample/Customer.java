package sample;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import sample.controllers.ManageCustomerController;

public class Customer {
    public String id, name, surname, place, postalCode, house, city, email, phone;

    public Button update;
    public Button delete;

    public Customer(String id, String name, String surname, String place, String postalCode, String house, String city, String email, String phone, Button update, Button delete) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.place = place;
        this.postalCode = postalCode;
        this.house = house;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.update = update;
        this.delete = delete;

        update.setOnAction(e->{
            ObservableList<Customer> customers = ManageCustomerController.table_info_two.getSelectionModel().getSelectedItems();
            for(Customer customer: customers){
                if(customer.getUpdate() == update){
                    Main.printWriter.println("updateCustomer,"+
                            customer.getId()+","+
                            customer.getName()+","+
                            customer.getSurname()+","+
                            customer.getPlace()+","+
                            customer.getPostalCode()+","+
                            customer.getHouse()+","+
                            customer.getCity()+","+
                            customer.getEmail()+","+
                            customer.getPhone()
                    );
                    Main.printWriter.flush();
                }
            }
        });

        delete.setOnAction(e->{
            ObservableList<Customer> customers = ManageCustomerController.table_info_two.getSelectionModel().getSelectedItems();
            for(Customer customer: customers){
                if(customer.getDelete() == delete){
                    Main.printWriter.println("deleteCustomer,"+customer.getId());
                    Main.printWriter.flush();
                    customers.forEach(ManageCustomerController.data_table::remove);
                }
            }
        });
    }





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }

    public Button getDelete() {
        return delete;
    }

    public void setDelete(Button delete) {
        this.delete = delete;
    }
}
