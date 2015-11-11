package Persistence;

import Controllers.AdController;
import Controllers.BookController;
import DataBase.DbInstance;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessagePersistence {

    public static void insertMessage(int idUsuarioEnvia, int idUsuarioRecebe, int idChat, String mensagem) {
        DbInstance db = new DbInstance();
        Date date = new Date();
        java.sql.Timestamp ts = new java.sql.Timestamp(date.getTime());
        try {
            //TODO check if email already exists
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String insertTableSQL = "INSERT INTO mensagem"
                    + "(idChat, idUsuarioEnvia, idUsuarioRecebe, mensagem, date) VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
            preparedStatement.setInt(1, idChat);
            preparedStatement.setInt(2, idUsuarioEnvia);
            preparedStatement.setInt(3, idUsuarioRecebe);
            preparedStatement.setString(4, mensagem);
            preparedStatement.setTimestamp(5, ts);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getLatestMessage(int idChat) {
        JsonArray result = new JsonArray();
        String mensagem = "";
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String sql = "SELECT * FROM mensagem WHERE idChat = " + idChat + " ORDER BY date desc";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            mensagem = rs.getString("mensagem");
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mensagem;
    }

    public static String getLatestMessageUsuarioEnvia(int idChat) {
        JsonArray result = new JsonArray();
        int idUsuarioEnvia = 0;
        String usuarioEnvia = "";
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String sql = "SELECT * FROM mensagem WHERE idChat = " + idChat + " ORDER BY date desc";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            idUsuarioEnvia = rs.getInt("idUsuarioEnvia");
            usuarioEnvia = UserPersistence.getUserById(idUsuarioEnvia).getNome();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarioEnvia;
    }

    public static String getLatestMessageUsuarioRecebe(int idChat) {
        JsonArray result = new JsonArray();
        int idUsuarioRecebe = 0;
        String usuarioRecebe = "";
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String sql = "SELECT * FROM mensagem WHERE idChat = " + idChat + " ORDER BY date desc";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            idUsuarioRecebe = rs.getInt("idUsuarioRecebe");
            usuarioRecebe = UserPersistence.getUserById(idUsuarioRecebe).getNome();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarioRecebe;
    }

    public static String getLatestMessageTimestamp(int idChat) {
        JsonArray result = new JsonArray();
        String timestamp = "";
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String sql = "SELECT * FROM mensagem WHERE idChat = " + idChat + " ORDER BY date desc";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            timestamp = rs.getTimestamp("date").toString();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return timestamp;
    }

    public static JsonArray getChat(int idChat) {
        JsonArray result = new JsonArray();
        DbInstance db = new DbInstance();
        try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = db.getConnection();
        String sql = "SELECT * FROM mensagem WHERE idChat = " + idChat+ " ORDER BY date desc";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql); 
        while (rs.next()) {
            JsonObject message = new JsonObject();
            message.addProperty("texto", rs.getString("mensagem"));
            message.addProperty("usuarioEnvia", UserPersistence.getUserById(rs.getInt("idUsuarioEnvia")).getNome());
            message.addProperty("usuarioRecebe", UserPersistence.getUserById(rs.getInt("idUsuarioRecebe")).getNome());
            message.addProperty("date", rs.getTimestamp("date").toString());
            result.add(message);
        }
        }
        catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
