package dbconnection;

import java.sql.*;

//function that connects the app with the database
public class SQLLiteConnection {
    public static Connection Connector(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:books.db");
            return conn;
        }catch(Exception e){
            System.out.print(e.getMessage());
            return null;
        }
    }
}
