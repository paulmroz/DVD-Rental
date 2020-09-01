package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCustomerController {

    public Parent root;

    @FXML private Button showDvdBtn;
    @FXML private Button showRentalAddBtn;
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField villageField;
    @FXML private TextField postCodeField;
    @FXML private TextField apartamentNumberField;
    @FXML private TextField cityField;
    @FXML private TextField emailField;
    @FXML private TextField phoneNumberField;
    @FXML private Button clearCustomerBtn;
    @FXML private Button addCustomerBtn;
    @FXML private Button showManageCustomer;



    public void addCustomer() {
        sample.Main.printWriter.println("addCustomer,"+
                nameField.getText().trim()+
                ","+surnameField.getText().trim()+
                ","+villageField.getText().trim()+
                ","+postCodeField.getText().trim()+
                ","+apartamentNumberField.getText().trim()+
                ","+cityField.getText().trim()+
                ","+emailField.getText().trim()+
                ","+phoneNumberField.getText().trim()
        );
        sample.Main.printWriter.flush();
    }

    public void clearFields()
    {
        nameField.setText("");
        surnameField.setText("");
        villageField.setText("");
        postCodeField.setText("");
        apartamentNumberField.setText("");
        cityField.setText("");
        emailField.setText("");
        phoneNumberField.setText("");
    }

    public void showAddRental(ActionEvent event) {
        try {
            Stage stage = (Stage) showRentalAddBtn.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/rentalAdd.fxml"));
            Parent root = loader.load();
            stage.setTitle("Dodawanie zamówienia");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showAddDvd(ActionEvent event) {
        try {
            Stage stage = (Stage) showDvdBtn.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/dvdAdd.fxml"));
            Parent root = loader.load();
            stage.setTitle("Usuwanie DVD");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showManageCustomer(ActionEvent event) {
        try {
            Stage stage = (Stage) showManageCustomer.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/customerManage.fxml"));
            Parent root = loader.load();
            stage.setTitle("Zarządzanie Klientami");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
