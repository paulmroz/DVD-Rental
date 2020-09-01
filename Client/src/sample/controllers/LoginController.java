package sample.controllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Main;
import sample.ClientSocket;
import java.io.IOException;
import java.util.logging.Logger;


public class LoginController {
    private static final Logger log = Logger.getLogger(LoginController.class.getName());
    @FXML
    private TextField inputUserName;
    @FXML
    private TextField inputUserPass;
    @FXML
    private Button logInBtn;
    @FXML
    private Text loginErr;
    @FXML
    private Button addUser;

    /**
     * Funkcja obłsugująca logowanie użytkownika
     * @param event wydarzenie kliknięcia w przycisk
     * @throws IOException
     */
    public void Login(ActionEvent event) throws IOException {

        if(inputUserName.getText().isEmpty() || inputUserPass.getText().isEmpty()) {
            loginErr.setText("Niepoprawny login lub haslo!");
            loginErr.setVisible(true);
            return;
        }

        String login = inputUserName.getText();
        String password = inputUserPass.getText();

        Main.printWriter.println("login," + login + "," + password);
        Main.printWriter.flush();

        String messageFromServer = "";
        messageFromServer = Main.bufferedReader.readLine();

        if(messageFromServer.equals("success")) {
            Stage stage = (Stage) logInBtn.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/dvdAdd.fxml"));
            Parent root = loader.load();
            stage.setTitle("Dodawanie DVD");
            stage.setScene(new Scene(root));
            stage.show();
        }
        else {
            loginErr.setText("Niepoprawny login lub haslo!");
            loginErr.setVisible(true);
        }
    }

    public void addUser(ActionEvent actionEvent) throws IOException {
        Stage addUserWindow = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/addUser.fxml"));
        Parent root = loader.load();
        addUserWindow.setScene(new Scene(root));
        addUserWindow.show();
    }
}
