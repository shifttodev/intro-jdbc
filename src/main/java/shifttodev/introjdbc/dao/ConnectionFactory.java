package shifttodev.introjdbc.dao;

import java.sql.*;

public class ConnectionFactory {
    public static Connection getConnection(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            return DriverManager.getConnection("jdbc:mysql://192.168.0.110/livraria",
                    "root", "senha");
        } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
