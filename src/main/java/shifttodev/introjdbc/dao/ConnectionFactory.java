package shifttodev.introjdbc.dao;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class ConnectionFactory {
    public static Connection getConnection(){
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String passwd = dotenv.get("DB_PASSWORD");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            return DriverManager.getConnection(url, user, passwd);
        } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
