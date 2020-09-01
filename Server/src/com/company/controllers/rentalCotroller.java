package com.company.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class rentalCotroller {
    static PreparedStatement pstmt;

    public boolean checkAvailability(String[] values, Connection con) throws SQLException {
        Statement stmt = null;
        int value = 0;
        String query = "SELECT ilosc FROM dvd WHERE id_dvd = " + values[2];
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        if(rs.next())
            value = rs.getInt(1);
        if(value > 0)
            return true;
        else
            return false;
    }

    public void updateDvdAmount(String[] values, Connection con) throws SQLException {
        String query = "UPDATE dvd SET ilosc = ilosc - 1 WHERE id_dvd=" + values[2];
        Statement stmt = null;
        stmt= con.createStatement();
        stmt.executeQuery(query);
    }

    public void insertRental(String[] values, Connection con) throws SQLException {
        pstmt = con.prepareStatement("INSERT INTO rental VALUES (RENT_SEQ.nextval,?,?)");
        pstmt.setString(1,values[1]);
        pstmt.setString(2,values[2]);
        pstmt.executeUpdate();
    }

    public ResultSet getAllRentalsFromDatabse(Connection con) throws SQLException {

        String query = "SELECT RENTAL.ID_RENTAL, CUSTOMER.ID_CUSTOMER, CUSTOMER.NAME, CUSTOMER.SURNAME, CUSTOMER.PHONE_NUMBER, DVD.ID_DVD, DVD.NAZWA, DVD.ROK_WYDANIA FROM RENTAL \n" +
                "INNER JOIN DVD  ON DVD.ID_DVD = RENTAL.ID_DVD\n" +
                "INNER JOIN CUSTOMER  ON CUSTOMER.ID_CUSTOMER = RENTAL.ID_KLIENT";
        ResultSet rs = con.createStatement().executeQuery(query);

        return rs;
    }

    public void deleteRental(String[] values,  Connection con) throws SQLException {
        pstmt = con.prepareStatement("DELETE FROM rental WHERE id_rental=?");
        pstmt.setString(1, values[1]);
        pstmt.executeUpdate();
    }
}
