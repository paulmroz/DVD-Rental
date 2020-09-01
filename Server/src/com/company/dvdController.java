package com.company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class dvdController {

    static PreparedStatement pstmt;

    public void insertDvd(String[] values,  Connection con) throws SQLException {
        pstmt = con.prepareStatement("INSERT INTO dvd(id_dvd, nazwa, gatunek, rok_wydania, rezyser, cena, ilosc) VALUES (dvd_seq.nextval, ?, ?, ?, ?, ?, ?)");
        pstmt.setString(1,values[1]);
        pstmt.setString(2,values[2]);
        pstmt.setString(3,values[3]);
        pstmt.setString(4,values[4]);
        pstmt.setString(5,values[5]);
        pstmt.setString(6,values[6]);
        pstmt.executeUpdate();
    }

    public ResultSet getAllDvdFromDatabse(Connection con) throws SQLException {

        String query = "SELECT * FROM DVD";
        ResultSet rs = con.createStatement().executeQuery(query);
        return rs;
    }

    public void updateDvd(String[] values,  Connection con) throws SQLException {

        pstmt = con.prepareStatement("UPDATE dvd SET nazwa=?, gatunek=?, rok_wydania=?, rezyser=?, cena=?, ilosc=?  WHERE id_dvd=?");
        pstmt.setString(1, values[2]);
        pstmt.setString(2, values[3]);
        pstmt.setString(3, values[4]);
        pstmt.setString(4, values[5]);
        pstmt.setString(5, values[6]);
        pstmt.setString(6, values[7]);
        pstmt.setString(7, values[1]);

        pstmt.executeUpdate();
    }

    public void deleteDvd(String[] values,  Connection con) throws SQLException {

        pstmt = con.prepareStatement("DELETE FROM dvd WHERE id_dvd=?");
        pstmt.setString(1, values[1]);
        pstmt.executeUpdate();
    }


}
