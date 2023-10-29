package com.alberto.ticketqr.serverConnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Check {
    public int checkTicket(String code){
        String url = "jdbc:mysql://localhost:3306/biglietto_db";
        String username = "root";
        String password = "";
        String driver = "com.mysql.jdbc.Driver";

        try (Connection conn = DriverManager.getConnection(url, username, password)){
            Statement stmt = conn.createStatement();
            String query = "SELECT numero FROM biglietti WHERE codice = " + code;
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            switch (resultSet.getInt("stato")){
                case 0: return 0;
                case 1: return 1;
                case 2: return 2;
            }
        }
        catch(SQLException e)
        {
            return 3;
        }
        return 3;
    }
}
