package edu.rseymour.advancedjava;

import java.sql.*;

public class SimpleDatabaseApp {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/stocks";

    private static final String USER = "monty";
    private static final String PASS = "some_pass";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;

        try {
            // Load the JDBC driver
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to the db!");
        } catch (ClassNotFoundException | SQLException e) {
            String message = e.getMessage();
            throw new SQLException("Could not connect to the database: " + message, e);
        }
        return connection;
    }

    public static void executeStatement() throws SQLException {
        // create a statement with the connection (if the connection object is in scope,
        // use ... = connection.createStatement();

        try {
            Statement statement = getConnection().createStatement();

            // create a simple query
            String sqlQuery = "select * from stocks";

            // execute statement. Accepts String
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            // iterate through resultSet. Initial row position is null, so first iteration reads first tuple
            // getString accepts String or column #
            while(resultSet.next()) {
                System.out.println(resultSet.getString("symbol") + "\t" +
                        resultSet.getString("time") + "\t" +
                        resultSet.getString("price")
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {

        try {
            SimpleDatabaseApp.getConnection();
            SimpleDatabaseApp.executeStatement();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
}
