package Main;

import java.sql.Connection;

import JDBC.JDBCConnectionManager;

public class InputOutput {

    public static void main(String[] args) {
        JDBCConnectionManager connectionManager = new JDBCConnectionManager();
        Connection c = connectionManager.manager();

        if (c != null) {
            System.out.println("Database connected and tables created.");

            connectionManager.closeConnection();
        } else {
            System.out.println("Could not connect to database.");
        }
    }
}
