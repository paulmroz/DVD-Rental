package sample;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import sample.controllers.ManageRentalController;


public class Rental {
    public String id, id_Customer, personalData, phoneNumber, id_Movie, titleMovie, realaseData;
    public Button returnBtn;

    public Rental(String id, String id_Customer, String personalData, String phoneNumber, String id_Movie, String titleMovie, String realaseData, Button returnBtn) {
        this.id = id;
        this.id_Customer = id_Customer;
        this.personalData = personalData;
        this.phoneNumber = phoneNumber;
        this.id_Movie = id_Movie;
        this.titleMovie = titleMovie;
        this.realaseData = realaseData;
        this.returnBtn = returnBtn;


        returnBtn.setOnAction(e->{
            ObservableList<Rental> rentals = ManageRentalController.table_info_two.getSelectionModel().getSelectedItems();
            for(Rental rental: rentals){
                if(rental.getReturnBtn() == returnBtn){

                    Main.printWriter.println("returnRental,"+
                            rental.getId()
                    );
                   Main.printWriter.flush();
                    rentals.forEach(ManageRentalController.data_table::remove);

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

    public String getId_Customer() {
        return id_Customer;
    }

    public void setId_Customer(String id_Customer) {
        this.id_Customer = id_Customer;
    }

    public String getPersonalData() {
        return personalData;
    }

    public void setPersonalData(String personalData) {
        this.personalData = personalData;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId_Movie() {
        return id_Movie;
    }

    public void setId_Movie(String id_Movie) {
        this.id_Movie = id_Movie;
    }

    public String getTitleMovie() {
        return titleMovie;
    }

    public void setTitleMovie(String titleMovie) {
        this.titleMovie = titleMovie;
    }

    public String getRealaseData() {
        return realaseData;
    }

    public void setRealaseData(String realaseData) {
        this.realaseData = realaseData;
    }

    public Button getReturnBtn() {
        return returnBtn;
    }

    public void setReturnBtn(Button returnBtn) {
        this.returnBtn = returnBtn;
    }
}
