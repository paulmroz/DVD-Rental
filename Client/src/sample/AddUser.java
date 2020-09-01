package sample;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.io.IOException;


public class AddUser {
    @FXML
    private Label labelUserName1;
    @FXML
    private TextField inputUserName1;
    @FXML
    private Label labelUserPass1;
    @FXML
    private TextField inputUserPass1;
    @FXML
    private Label labelUserName2;
    @FXML
    private TextField inputUserName2;
    @FXML
    private Label labelUserPass2;
    @FXML
    private TextField inputUserPass2;
    @FXML
    private Text loginErr;

    String login;
    String password;
    String newUserLogin;
    String newUserPass;
    String messageFromServer;

    public void addUser(ActionEvent event) throws IOException {
        login = inputUserName1.getText();
        password = inputUserPass1.getText();

            if(inputUserName1.getText().isEmpty() || inputUserPass1.getText().isEmpty()) {
                loginErr.setText("Zadne pole nie moze byc puste!");
                loginErr.setVisible(true);
                return;
            }
            newUserLogin = "";
            newUserPass = "";

            Main.printWriter.println("login," + login + "," + password);
            Main.printWriter.flush();

            messageFromServer = "";
            messageFromServer = Main.bufferedReader.readLine();

            if(messageFromServer.equals("fail")) {
                loginErr.setText("Niepoprawny login lub haslo!");
                loginErr.setVisible(true);
            }
            else {
                newUserLogin = inputUserName2.getText();
                newUserPass = inputUserPass2.getText();
                if(inputUserName2.getText().isEmpty() || inputUserPass2.getText().isEmpty()) {
                    loginErr.setText("Zadne pole nie moze byc puste!");
                    loginErr.setVisible(true);
                    return;
                }

                Main.printWriter.println("createUser," + newUserLogin + "," + newUserPass);
                Main.printWriter.flush();

                messageFromServer = Main.bufferedReader.readLine();
                if(messageFromServer.equals("success")) {
                    loginErr.setText("Dodawnie uzytkownika sie powiodlo");
                    loginErr.setVisible(true);
                }
                else {
                    loginErr.setText("Uzytkownik o takiej nazwie juz istnieje w bazie");
                    loginErr.setVisible(true);
                }
            }

        }

}
