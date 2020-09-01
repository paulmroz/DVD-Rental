package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.Main;
import sample.Rental;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ManageRentalController implements Initializable {

    public Parent root;
    @FXML private Button showDvdBtn;
    @FXML private Button showCustomerBtn;
    @FXML private Button showAddRentalBtn;

    @FXML
    private TableView<Rental> table_info;

    public static TableView<Rental> table_info_two;

    @FXML private TextField searchField;


    @FXML
    private TableColumn<Rental, String> col_id;

    @FXML
    private TableColumn<Rental, String> col_idCust;

    @FXML
    private TableColumn<Rental, String> col_personalData;

    @FXML
    private TableColumn<Rental, String> col_phoneNumber;

    @FXML
    private TableColumn<Rental, String> col_idMovie;

    @FXML
    private TableColumn<Rental, String> col_title;

    @FXML
    private TableColumn<Rental, String> col_realaseDate;

    @FXML
    private TableColumn<Rental, Button> col_return;

    public static ObservableList<Rental> data_table;


    public void searchForRental(KeyEvent event){
        System.out.println(searchField.getText());
        FilteredList<Rental> filteredRental = new FilteredList<Rental>(data_table, b->true);

        filteredRental.setPredicate(rental->{
            String lowerCaseFilter = searchField.getText().toLowerCase();

            if(rental.getPersonalData().toLowerCase().contains(lowerCaseFilter)){
                return true;
            } else if(rental.getRealaseData().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if(rental.getPhoneNumber().toLowerCase().contains(lowerCaseFilter)){
                return true;
            } else if(rental.getTitleMovie().toLowerCase().contains(lowerCaseFilter)){
                return true;
            }else {
                return false;
            }
        });

        SortedList<Rental> sortedData = new SortedList<>(filteredRental);

        table_info.setItems(sortedData);

    }



    public void showAddDvd(ActionEvent event) {
        try {
            Stage stage = (Stage) showDvdBtn.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/dvdAdd.fxml"));
            Parent root = loader.load();
            /*
            root = FXMLLoader.load(getClass().getResource("dvdAdd.fxml"));

             */
            stage.setTitle("Dodawanie DVD");
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
            Stage stage = (Stage) showAddRentalBtn.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/rentalAdd.fxml"));
            Parent root = loader.load();
            /*
            root = FXMLLoader.load(getClass().getResource("rentalAdd.fxml"));

             */
            stage.setTitle("Dodawanie wypożyczenia");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table_info_two = table_info;
        initTable();
        try {
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTable(){
        initCols();
    }

    private void initCols(){

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_idCust.setCellValueFactory(new PropertyValueFactory<>("id_Customer"));
        col_personalData.setCellValueFactory(new PropertyValueFactory<>("personalData"));
        col_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        col_idMovie.setCellValueFactory(new PropertyValueFactory<>("id_Movie"));
        col_title.setCellValueFactory(new PropertyValueFactory<>("titleMovie"));
        col_realaseDate.setCellValueFactory(new PropertyValueFactory<>("realaseData"));
        col_return.setCellValueFactory(new PropertyValueFactory<>("returnBtn"));

        editTableCols();
    }

    private void editTableCols(){
        col_id.setCellFactory(TextFieldTableCell.forTableColumn());
        col_id.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setId(e.getNewValue());
        });

        col_idCust.setCellFactory(TextFieldTableCell.forTableColumn());
        col_idCust.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setId_Customer(e.getNewValue());
        });

        col_personalData.setCellFactory(TextFieldTableCell.forTableColumn());
        col_personalData.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPersonalData(e.getNewValue());
        });

        col_phoneNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        col_phoneNumber.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPhoneNumber(e.getNewValue());
        });

        col_idMovie.setCellFactory(TextFieldTableCell.forTableColumn());
        col_idMovie.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setId_Movie(e.getNewValue());
        });

        col_title.setCellFactory(TextFieldTableCell.forTableColumn());
        col_title.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setTitleMovie(e.getNewValue());
        });

        col_realaseDate.setCellFactory(TextFieldTableCell.forTableColumn());
        col_realaseDate.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setRealaseData(e.getNewValue());
        });


        table_info.setEditable(true);
    }

    private void loadData() throws IOException {
        data_table = FXCollections.observableArrayList();

        Main.printWriter.println("rentalShow");
        Main.printWriter.flush();
        String messageFromServer = "";
        while (!messageFromServer.equals("close")) {
            messageFromServer = Main.bufferedReader.readLine();
            String[] values = messageFromServer.split(",");

            System.out.println(messageFromServer);

            if(values[0].equals("close")) break;

            data_table.add(new Rental(
                    values[1],
                    values[2],
                    values[3],
                    values[4],
                    values[5],
                    values[6],
                    values[7],
                    new Button("returnBtn")
            ));
        }

        table_info.setItems(data_table);
    }
}
