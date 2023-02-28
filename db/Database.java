package banking.db;

import banking.entities.Card;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class Database {
    private static Connection conn;

    public static Connection getConnection(String databasePath) {
        String dbUrl = "jdbc:sqlite:" + databasePath.trim();
        try {
            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl(dbUrl);
            conn = dataSource.getConnection();
        } catch (SQLException ex) {
            System.out.println("Error trying to connect to DB");
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
