package com.company;

import com.company.controllers.customerController;
import com.company.controllers.rentalCotroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ClientHandler implements  Runnable{

    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private dvdController dvdController;
    private com.company.controllers.customerController customerController;
    private com.company.controllers.rentalCotroller rentalCotroller;
    private static Connection databaseConnection;

    public ClientHandler(Socket clientSocker, Connection databaseConnection) throws IOException {
        this.socket = clientSocker;
        this.databaseConnection = databaseConnection;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void run() {

        Server.databaseConnection();
        String messageFromClient = "";
        dvdController = new dvdController();
        customerController = new customerController();
        rentalCotroller = new rentalCotroller();

        while(true) {
            try {

                    bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    printWriter = new PrintWriter(socket.getOutputStream());

                    while (!messageFromClient.equals("exit")) {
                        messageFromClient = bufferedReader.readLine();
                        String[] values = messageFromClient.split(",");
                        if(values[0].equals("addCustomer")){
                            System.out.println(messageFromClient);
                            customerController.insertCustomer(values,databaseConnection);
                        }

                        if(values[0].equals("customerShow")){
                            System.out.println(messageFromClient);
                            ResultSet databseResult = customerController.getAllCustomersFromDatabse(databaseConnection);


                            while(databseResult.next()) {
                                printWriter.println(","+
                                        databseResult.getString("id_customer")+","+
                                        databseResult.getString("name")+","+
                                        databseResult.getString("surname")+","+
                                        databseResult.getString("village")+","+
                                        databseResult.getString("postcode")+","+
                                        databseResult.getString("apartment_number")+","+
                                        databseResult.getString("city")+","+
                                        databseResult.getString("email")+","+
                                        databseResult.getString("phone_number")
                                );
                                printWriter.flush();
                            }
                            printWriter.println("close");
                            printWriter.flush();
                        }

                        if(values[0].equals("updateCustomer")){
                           System.out.println(messageFromClient);
                            customerController.updateCustomer(values, databaseConnection);
                        }

                        if(values[0].equals("rentalAdd")){
                            System.out.println(messageFromClient);
                            if(rentalCotroller.checkAvailability(values, databaseConnection) == true) {
                                rentalCotroller.insertRental(values, databaseConnection);
                                rentalCotroller.updateDvdAmount(values, databaseConnection);
                                printWriter.println("success");
                                printWriter.flush();
                            }
                            else {
                                printWriter.println("fail");
                                printWriter.flush();
                            }
                        }

                        if(values[0].equals("returnRental")){
                            System.out.println(messageFromClient);
                            rentalCotroller.deleteRental(values, databaseConnection);
                        }

                        if(values[0].equals("rentalShow")){
                            System.out.println(messageFromClient);
                            ResultSet databseResult = rentalCotroller.getAllRentalsFromDatabse(databaseConnection);

                            while(databseResult.next()) {
                                printWriter.println(","+
                                        databseResult.getString("ID_RENTAL")+","+
                                        databseResult.getString("ID_CUSTOMER")+","+
                                        databseResult.getString("NAME") + " " + databseResult.getString("SURNAME")+","+
                                        databseResult.getString("PHONE_NUMBER")+","+
                                        databseResult.getString("ID_DVD")+","+
                                        databseResult.getString("NAZWA")+","+
                                        databseResult.getString("ROK_WYDANIA")
                                );
                                printWriter.flush();
                            }

                            printWriter.println("close");
                            printWriter.flush();

                        }

                        if(values[0].equals("deleteCustomer")){
                            System.out.println(messageFromClient);
                            customerController.deleteCustomer(values, databaseConnection);
                        }

                        if(values[0].equals("addDvd")){
                            System.out.println(messageFromClient);
                            dvdController.insertDvd(values, databaseConnection);
                        }

                        if(values[0].equals("updateDvd")){
                            System.out.println(messageFromClient);
                            dvdController.updateDvd(values, databaseConnection);
                        }

                        if(values[0].equals("deleteDvd")){
                            System.out.println(messageFromClient);
                            dvdController.deleteDvd(values, databaseConnection);
                        }

                        if(values[0].equals("dvdShow")){
                            ResultSet databseResult = dvdController.getAllDvdFromDatabse(databaseConnection);

                            while(databseResult.next()) {
                                printWriter.println(","+databseResult.getString("id_dvd")+","+
                                        databseResult.getString("nazwa")+","+
                                        databseResult.getString("gatunek")+","+
                                        databseResult.getString("rok_wydania")+","+
                                        databseResult.getString("rezyser")+","+
                                        databseResult.getString("cena")+","+
                                        databseResult.getString("ilosc"));
                                printWriter.flush();
                            }
                            printWriter.println("close");
                            printWriter.flush();

                        }

                        if(values[0].equals("login")){
                            Statement stmt = null;
                            int check = 0;
                            String query = "select count(*) from userr where login = '" + values[1] + "' and password = '" + values[2] + "'";
                            stmt = databaseConnection.createStatement();
                            ResultSet rs = stmt.executeQuery(query);
                            if(rs.next())
                                check = rs.getInt(1);
                            if(check == 1) {
                                printWriter.println("success");
                                printWriter.flush();
                            }
                            else {
                                printWriter.println("fail");
                                printWriter.flush();
                            }
                        }

                        if(values[0].equals("createUser")){
                            String query = "select count(*) from userr where login = '" + values[1] + "'";
                            System.out.println(values[1]+values[2]);
                            Statement stmt = databaseConnection.createStatement();
                            ResultSet rs = stmt.executeQuery(query);
                            int check = 0;
                            if(rs.next())
                                check = rs.getInt(1);
                            if(check == 1) {
                                printWriter.println("fail");
                                printWriter.flush();
                            }
                            else {
                                PreparedStatement pstmt = databaseConnection.prepareStatement("INSERT INTO userr(login, password) VALUES(?,?)");
                                pstmt.setString(1,values[1]);
                                pstmt.setString(2,values[2]);
                                pstmt.executeUpdate();

                                printWriter.println("success");
                                printWriter.flush();
                            }
                        }




                    }
                    socket.close();
            } catch (Exception e) {

            }
        }

    }
}
