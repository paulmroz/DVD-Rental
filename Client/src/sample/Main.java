package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.*;
import java.io.*;

public class Main extends Application {

    public static PrintWriter printWriter;
    public static Socket socket;
    public static BufferedReader bufferedReader;
    /**
     * Funkcja łącząca klienta z serwerem
     * @return połączenie z serwerem
     * @throws Exception
     */
    static public boolean connectToServer() throws Exception {
        return ClientSocket.connect();
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("DVD Rental");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        socket = new Socket("localhost",4999);
        printWriter = new PrintWriter(socket.getOutputStream());
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

    }
    /**
     * Funkcja wysyłająca komunikat do serwera o zamknięciu połaczenia
     */
    public void exit() throws Exception {
        ClientSocket.socket.close();
        ClientSocket.printWriter.close();
        ClientSocket.bufferedReader.close();
        ClientSocket.sendMsg("exit");
    }

    public static void main(String[] args) throws Exception {
        if (connectToServer()) {
            launch(args);
        } else {
            Platform.exit();
            System.exit(0);
        }
    }
}
