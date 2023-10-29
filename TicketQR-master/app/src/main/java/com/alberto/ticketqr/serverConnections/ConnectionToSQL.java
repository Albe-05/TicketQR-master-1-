package com.alberto.ticketqr.serverConnections;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToSQL {
    public boolean connect(String IP){
        String url = "jdbc:mysql://localhost:3306/biglietto_db";
        String username = "root";
        String password = "";
        String driver = "com.mysql.jdbc.Driver";

        Log.i(String.valueOf(ConnectionToSQL.class), IP);
        try (Connection conn = DriverManager.getConnection(IP, username, password)){
            Log.i(String.valueOf(ConnectionToSQL.class), conn.toString());
            return true;
        }
        catch(SQLException e)
        {
            Log.e(String.valueOf(ConnectionToSQL.class), String.valueOf(e)); //java.sql.SQLNonTransientConnectionException: Could not create connection to database server. (con IP sbagliato)
            /*
            com.mysql.cj.jdbc.exceptions.CommunicationsException: Communications link failure
            The last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server.
            con l'ip corretto del pc su una rete comune
             */
            return false;
        }
    }
}