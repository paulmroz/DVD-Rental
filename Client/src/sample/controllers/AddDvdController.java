package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Main;


import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class AddDvdController implements Initializable {

    @FXML private Button deleteBtn;
    @FXML private Button showCustomerBtn;
    @FXML private Button showRentalAddBtn;
    @FXML private TextField dvdName;
    @FXML private TextField releaseDate;
    @FXML private ComboBox<String> genreName;
    @FXML private TextField directorName;
    @FXML private TextField dvdPrice;
    @FXML private TextField countity;
    @FXML private ImageView posterImage;

    public Parent root;

    ObservableList<String> genreList= FXCollections.observableArrayList("Komedia", "Dramat", "Fantasy");


    public void addDvd() {

        Main.printWriter.println("addDvd,"+
                dvdName.getText().trim()+
                ","+genreName.getSelectionModel().getSelectedItem()+
                ","+releaseDate.getText().trim()+
                ","+directorName.getText().trim()+
                ","+dvdPrice.getText().trim()+
                ","+countity.getText().trim()

        );
        Main.printWriter.flush();
    }

    public void clearFields()
    {
        dvdName.setText("");
        dvdPrice.setText("");
        releaseDate.setText("");
        directorName.setText("");
        countity.setText("");
    }


    public void addImage(){

        FileChooser.ExtensionFilter imageFilter =  new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");


        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(imageFilter);

        File selectedFile = fileChooser.showOpenDialog(null);

        Image image = new Image(selectedFile.toURI().toString());

        if(selectedFile != null){
                posterImage.setImage(image);
        } else {

        }
    }

    public void showManageDvd(ActionEvent event) {
        try {
            Stage stage = (Stage) deleteBtn.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/dvdManage.fxml"));
            Parent root = loader.load();
            /*
            root = FXMLLoader.load(getClass().getResource("dvdManage.fxml"));
            */
            stage.setTitle("Zarządzanie DVD");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddCustomer(ActionEvent event) {
        try {
            Stage stage = (Stage) showCustomerBtn.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/customerAdd.fxml"));
            Parent root = loader.load();
            /*
            root = FXMLLoader.load(getClass().getResource("customerAdd.fxml"));

             */
            stage.setTitle("Dodawanie Klienta");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddRental(ActionEvent event) {
        try {
            Stage stage = (Stage) showRentalAddBtn.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/rentalAdd.fxml"));
            Parent root = loader.load();
            /*
            root = FXMLLoader.load(getClass().getResource("rentalAdd.fxml"));

             */
            stage.setTitle("Dodawanie zamówienia");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genreName.setItems(genreList);
    }
}
