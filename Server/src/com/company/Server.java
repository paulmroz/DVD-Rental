package com.company;

import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

     private static Connection databaseConnection;
     static ServerSocket serverSocket;
     static Socket socket;
     static BufferedReader bufferedReader;
     static PrintWriter printWriter;

     static dvdController dvdController;
     static com.company.controllers.customerController customerController;

    private static final int PORT = 4999;
    private static ArrayList<ClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newFixedThreadPool(25);

    static Connection databaseConnection(){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            databaseConnection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","adam","mani1974");

            return databaseConnection;

        }catch(Exception e){ System.out.println(e);}
        return null;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(PORT);
        databaseConnection = databaseConnection();

        while(true) {
            System.out.println("[SERVER] Waiting for client connection...");
            Socket client = listener.accept();
            System.out.println("[SERVER] Connected to client!!!");

            ClientHandler clientThread = new ClientHandler(client,databaseConnection);
            clients.add(clientThread);

            pool.execute(clientThread);

        }

      }

}
