package com.alberto.ticketqr.serverConnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Connection {
    public boolean connect(String IP){
        String url = "jdbc:mysql://localhost:3306/biglietto_db";
        String username = "root";
        String password = "";
        String driver = "com.mysql.jdbc.Driver";

        try (Connection conn = DriverManager.getConnection(url, username, password)){
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }
}