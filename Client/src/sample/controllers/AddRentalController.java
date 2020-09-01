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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Customer;
import sample.Dvd;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddRentalController implements Initializable {

    public Parent root;

    @FXML private Button showDvdBtn;
    @FXML private Button showManageBtn;
    @FXML private Button showCustomerBtn;
    @FXML private TextField searchField;
    @FXML private TextField searchFieldDvd;
    @FXML private TableView<Customer> table_info;
    @FXML private TableView<Customer> table_info_customer_two;

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
    @FXML private TableView<Dvd> table_info_dvd;
    public static TableView<Dvd> table_info_dvd_two;
    @FXML private TableColumn<Dvd, String> col_id;
    @FXML private TableColumn<Dvd, String> col_name;
    @FXML private TableColumn<Dvd, String> col_price;
    @FXML private TableColumn<Dvd, String> col_genre;
    @FXML private TableColumn<Dvd, String> col_director;
    @FXML private TableColumn<Dvd, String> col_realase;
    @FXML private TableColumn<Dvd, String> col_countity;

    @FXML
    private Label selectedCustomer;
    @FXML
    private Label selectedDvd;

    @FXML private Button pickButton;
    @FXML private Button pickButton_dvd;

    @FXML private ListView pickedDvd;

    @FXML
    private Button deleteFromChoosenDvd;

    @FXML
    private Label msgFromServer;

    @FXML
    private Label kwota;


    public static ObservableList<Customer> data_table;
    public static ObservableList<Dvd> data_table_dvd;

    public void chooseCustomer(ActionEvent event){
        Customer customer = table_info.getSelectionModel().getSelectedItem();
        selectedCustomer.setText(customer.getId()+"," + customer.getName());
    }


    public void chooseDvd(ActionEvent event){
        Dvd dvd = table_info_dvd.getSelectionModel().getSelectedItem();
        pickedDvd.getItems().add(dvd.getId()+","+dvd.getName()+","+dvd.getPrice());
        kwota.setText(Integer.toString(Integer.parseInt(kwota.getText()) + Integer.parseInt(dvd.getPrice())));
    }

    public void deleteFromSelectedDvd(ActionEvent event){
        //pickedDvd.getItems().remove(pickedDvd.getSelectionModel().getSelectedItems().toString());

        String price = (String) pickedDvd.getSelectionModel().getSelectedItem();
        String[] arr =  price.split(",");
        kwota.setText(Integer.toString(Integer.parseInt(kwota.getText()) - Integer.parseInt(arr[2])));

        pickedDvd.getItems().remove(pickedDvd.getSelectionModel().getSelectedIndex());
    }

    public void rentDvds(ActionEvent event) throws IOException {
        String retrivedCustomerId = selectedCustomer.getText();
        String[] customerValues = retrivedCustomerId.split(",");

        String[] valuesSenttoSever = new String[100];

        valuesSenttoSever[0] = "rentDvd";
        valuesSenttoSever[1] = customerValues[0];

        String[] dvdValues = new String[100];
        ObservableList<String> allSelectedDvd = pickedDvd.getItems();


        for (String item : allSelectedDvd) {

            //System.out.println(item);
            String[] retrivedDvdId = item.split(",");
            Main.printWriter.println("rentalAdd," +
                    customerValues[0] + "," +
                    retrivedDvdId[0]
            );
            Main.printWriter.flush();


            String messageFromServer = "";
            messageFromServer = Main.bufferedReader.readLine();
            if (messageFromServer.equals("success")) {
                msgFromServer.setText("Wypozyczono!");
            } else {
                msgFromServer.setText("Dana plyta jest niedostepna");
            }
        }
    }



    public void searchForDvd(KeyEvent event){
        System.out.println(searchFieldDvd.getText());
        FilteredList<Dvd> filteredDvd = new FilteredList<Dvd>(data_table_dvd, b->true);

        //String lowerCaseFilter = searchField.getText().toLowerCase();
        filteredDvd.setPredicate(dvd->{
            String lowerCaseFilter = searchFieldDvd.getText().toLowerCase();

            if(dvd.getName().toLowerCase().contains(lowerCaseFilter)){
                return true;
            } else if(dvd.getDirector().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }else {
                return false;
            }
        });

        SortedList<Dvd> sortedData = new SortedList<>(filteredDvd);
        table_info_dvd.setItems(sortedData);

    }


    public void searchForCustomer(KeyEvent event){
        System.out.println(searchField.getText());
        FilteredList<Customer> filteredDvd = new FilteredList<Customer>(data_table, b->true);

        //String lowerCaseFilter = searchField.getText().toLowerCase();
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
        table_info_dvd_two = table_info_dvd;
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

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        col_realase.setCellValueFactory(new PropertyValueFactory<>("realase"));
        col_director.setCellValueFactory(new PropertyValueFactory<>("director"));
        col_countity.setCellValueFactory(new PropertyValueFactory<>("countity"));


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

        col_id.setCellFactory(TextFieldTableCell.forTableColumn());

        col_id.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setId(e.getNewValue());
        });

        col_name.setCellFactory(TextFieldTableCell.forTableColumn());

        col_name.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
        });

        col_price.setCellFactory(TextFieldTableCell.forTableColumn());

        col_price.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPrice(e.getNewValue());
        });

        col_genre.setCellFactory(TextFieldTableCell.forTableColumn());

        col_genre.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setGenre(e.getNewValue());
        });

        col_realase.setCellFactory(TextFieldTableCell.forTableColumn());

        col_realase.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setRealase(e.getNewValue());
        });

        col_director.setCellFactory(TextFieldTableCell.forTableColumn());

        col_director.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setDirector(e.getNewValue());
        });

        col_countity.setCellFactory(TextFieldTableCell.forTableColumn());

        col_countity.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCountity(e.getNewValue());
        });

        table_info_dvd.setEditable(true);


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




        data_table_dvd = FXCollections.observableArrayList();

        Main.printWriter.println("dvdShow");
        Main.printWriter.flush();
        String msgin = "";
        while (!msgin.equals("close")) {
            msgin = Main.bufferedReader.readLine();
            String[] values = msgin.split(",");

            if(values[0].equals("close")) break;
            data_table_dvd.add(new Dvd(
                    values[1],
                    values[2],
                    values[3],
                    values[4],
                    values[5],
                    values[6],
                    values[7],
                    new Button("update"),
                    new Button("delete")
            ));
        }

        table_info_dvd.setItems(data_table_dvd);
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

    public void showMangaeRental(ActionEvent event) {
        try {
            Stage stage = (Stage) showManageBtn.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/manageRental.fxml"));
            Parent root = loader.load();
            /*
            root = FXMLLoader.load(getClass().getResource("manageRental.fxml"));
            */

            stage.setTitle("Zarządzanie Wypożyczenami");
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
}
