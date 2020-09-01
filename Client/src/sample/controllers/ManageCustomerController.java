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
import sample.Customer;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageCustomerController implements Initializable {

    public Parent root;
    @FXML private Button showDvdBtn;
    @FXML private Button showAddCustomerBtn;
    @FXML private Button showRentalAddBtn;
    @FXML private TextField searchField;
    @FXML private TableView<Customer> table_info;
    public static TableView<Customer> table_info_two;
    @FXML private TableColumn<Customer, String> id_col;
    @FXML private TableColumn<Customer, String> name_col;
    @FXML private TableColumn<Customer, String> surname_col;
    @FXML private TableColumn<Customer, String> place_col;
    @FXML private TableColumn<Customer, String> postalCode_col;
    @FXML private TableColumn<Customer, String> house_col;
    @FXML private TableColumn<Customer, String> city_col;
    @FXML private TableColumn<Customer, String> email_col;
    @FXML private TableColumn<Customer, String> phone_col;
    @FXML private TableColumn<Customer, Button> delete_col;
    @FXML private TableColumn<Customer, Button> update_col;

    public static ObservableList<Customer> data_table;

    public void searchForCustomer(KeyEvent event){
        System.out.println(searchField.getText());
        FilteredList<Customer> filteredDvd = new FilteredList<Customer>(data_table, b->true);

        filteredDvd.setPredicate(customer->{
            String lowerCaseFilter = searchField.getText().toLowerCase();

            if(customer.getName().toLowerCase().contains(lowerCaseFilter)){
                return true;
            } else if(customer.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if(customer.getPhone().toLowerCase().contains(lowerCaseFilter)){
                return true;
            }else {
                return false;
            }
        });

        SortedList<Customer> sortedData = new SortedList<>(filteredDvd);

        table_info.setItems(sortedData);

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


        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname_col.setCellValueFactory(new PropertyValueFactory<>("surname"));
        place_col.setCellValueFactory(new PropertyValueFactory<>("place"));
        postalCode_col.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        house_col.setCellValueFactory(new PropertyValueFactory<>("house"));
        city_col.setCellValueFactory(new PropertyValueFactory<>("city"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone_col.setCellValueFactory(new PropertyValueFactory<>("phone"));
        delete_col.setCellValueFactory(new PropertyValueFactory<>("delete"));
        update_col.setCellValueFactory(new PropertyValueFactory<>("update"));


        editTableCols();
    }

    private void editTableCols(){
        id_col.setCellFactory(TextFieldTableCell.forTableColumn());
        id_col.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setId(e.getNewValue());
        });

        name_col.setCellFactory(TextFieldTableCell.forTableColumn());
        name_col.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
        });

        surname_col.setCellFactory(TextFieldTableCell.forTableColumn());
        surname_col.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setSurname(e.getNewValue());
        });

        place_col.setCellFactory(TextFieldTableCell.forTableColumn());
        place_col.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPlace(e.getNewValue());
        });

        postalCode_col.setCellFactory(TextFieldTableCell.forTableColumn());
        postalCode_col.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPostalCode(e.getNewValue());
        });

        house_col.setCellFactory(TextFieldTableCell.forTableColumn());
        house_col.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setHouse(e.getNewValue());
        });

        city_col.setCellFactory(TextFieldTableCell.forTableColumn());
        city_col.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCity(e.getNewValue());
        });

        email_col.setCellFactory(TextFieldTableCell.forTableColumn());
        email_col.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setEmail(e.getNewValue());
        });

        phone_col.setCellFactory(TextFieldTableCell.forTableColumn());
        phone_col.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPhone(e.getNewValue());
        });


        table_info.setEditable(true);
    }

    private void loadData() throws IOException {
        data_table = FXCollections.observableArrayList();

        Main.printWriter.println("customerShow");
        Main.printWriter.flush();
        String messageFromServer = "";
        while (!messageFromServer.equals("close")) {
            messageFromServer = Main.bufferedReader.readLine();
            String[] values = messageFromServer.split(",");

            System.out.println(messageFromServer);

            if(values[0].equals("close")) break;

            data_table.add(new Customer(
                    values[1],
                    values[2],
                    values[3],
                    values[4],
                    values[5],
                    values[6],
                    values[7],
                    values[8],
                    values[9],
                    new Button("update"),
                    new Button("delete")
                    ));
        }

     table_info.setItems(data_table);
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


    public void showAddCustomer(ActionEvent event) {
        try {
            Stage stage = (Stage) showAddCustomerBtn.getScene().getWindow();
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
            stage.setTitle("Dodawanie Klienta");
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
