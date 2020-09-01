package com.company.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class customerController {
    static PreparedStatement pstmt;

    public void insertCustomer(String[] values,  Connection con) throws SQLException {
        pstmt = con.prepareStatement("INSERT INTO customer VALUES (cust_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?)");
        pstmt.setString(1,values[1]);
        pstmt.setString(2,values[2]);
        pstmt.setString(3,values[3]);
        pstmt.setString(4,values[4]);
        pstmt.setString(5,values[5]);
        pstmt.setString(6,values[6]);
        pstmt.setString(7,values[7]);
        pstmt.setString(8,values[8]);
        pstmt.executeUpdate();
    }

    public ResultSet getAllCustomersFromDatabse(Connection con) throws SQLException {

        String query = "SELECT * FROM customer";
        ResultSet rs = con.createStatement().executeQuery(query);
        return rs;
    }

    public void updateCustomer(String[] values,  Connection con) throws SQLException {

        pstmt = con.prepareStatement("UPDATE customer SET name=?, surname=?, village=?, postcode=?, apartment_number=?, city=? , email=? , phone_number=?  WHERE id_customer=?");
        pstmt.setString(1, values[2]);
        pstmt.setString(2, values[3]);
        pstmt.setString(3, values[4]);
        pstmt.setString(4, values[5]);
        pstmt.setString(5, values[6]);
        pstmt.setString(6, values[7]);
        pstmt.setString(7, values[8]);
        pstmt.setString(8, values[9]);
        pstmt.setString(9, values[1]);

        pstmt.executeUpdate();
    }

    public void deleteCustomer(String[] values,  Connection con) throws SQLException {

        pstmt = con.prepareStatement("DELETE FROM customer WHERE id_customer=?");
        pstmt.setString(1, values[1]);
        pstmt.executeUpdate();
    }
}
