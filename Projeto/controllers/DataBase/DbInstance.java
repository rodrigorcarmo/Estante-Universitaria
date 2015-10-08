package DataBase;

import com.mysql.jdbc.*;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbInstance {
    private final static String DBURL = "jdbc:mysql://localhost:3306/estanteUniversitaria"; 
    public Connection getConnection() throws SQLException{
    Connection conn = (Connection) DriverManager.getConnection(DBURL, "usuario", "senha");
    return conn;
   }
}
