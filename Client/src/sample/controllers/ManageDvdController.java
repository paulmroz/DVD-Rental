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
import sample.Dvd;
import sample.Main;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageDvdController implements Initializable {

    public Parent root;

    @FXML private Button addBtn;
    @FXML private Button showCustomerBtn;
    @FXML private Button showRentalAddBtn;
    @FXML private TextField searchField;
    @FXML private TableView<Dvd> table_info;
    public static TableView<Dvd> table_info_two;
    @FXML private TableColumn<Dvd, String> col_id;
    @FXML private TableColumn<Dvd, String> col_name;
    @FXML private TableColumn<Dvd, String> col_price;
    @FXML private TableColumn<Dvd, String> col_genre;
    @FXML private TableColumn<Dvd, String> col_director;
    @FXML private TableColumn<Dvd, String> col_realase;
    @FXML private TableColumn<Dvd, String> col_countity;
    @FXML private TableColumn<Dvd, Button> col_delete;
    @FXML private TableColumn<Dvd, Button> col_update;

    public static ObservableList<Dvd> data_table;

    public void searchForDvd(KeyEvent event){
        System.out.println(searchField.getText());
        FilteredList<Dvd> filteredDvd = new FilteredList<Dvd>(data_table, b->true);

        filteredDvd.setPredicate(dvd->{
            String lowerCaseFilter = searchField.getText().toLowerCase();

            if(dvd.getName().toLowerCase().contains(lowerCaseFilter)){
                return true;
            } else if(dvd.getDirector().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }else {
                return false;
            }
        });

        SortedList<Dvd> sortedData = new SortedList<>(filteredDvd);

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
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        col_realase.setCellValueFactory(new PropertyValueFactory<>("realase"));
        col_director.setCellValueFactory(new PropertyValueFactory<>("director"));
        col_countity.setCellValueFactory(new PropertyValueFactory<>("countity"));
        col_delete.setCellValueFactory(new PropertyValueFactory<>("delete"));
        col_update.setCellValueFactory(new PropertyValueFactory<>("update"));

        editTableCols();
    }

    private void editTableCols(){
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

        table_info.setEditable(true);
    }

    private void loadData() throws IOException {
        data_table = FXCollections.observableArrayList();

        Main.printWriter.println("dvdShow");
        Main.printWriter.flush();
        String msgin = "";
        while (!msgin.equals("close")) {
            msgin = Main.bufferedReader.readLine();
            String[] values = msgin.split(",");

            if(values[0].equals("close")) break;
            data_table.add(new Dvd(
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

        table_info.setItems(data_table);
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

    public void showAddDvd(ActionEvent event) {
        try {
            Stage stage = (Stage) addBtn.getScene().getWindow();
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

}
