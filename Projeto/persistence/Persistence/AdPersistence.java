
package Persistence;

import Controllers.AdController;
import Controllers.UserController;
import DataBase.DbInstance;
import Models.AdModel;
import Models.UserModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdPersistence {
    
    public static void insert(AdModel ad) throws SQLException {

        DbInstance db = new DbInstance();
        try {
            //TODO check if email already exists
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String insertTableSQL = "INSERT INTO Anuncio"
                    + "(idLivro, tipo, valor, idUsuario ) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
            preparedStatement.setInt(1, ad.getIdLivro());
            preparedStatement.setString(2, ad.getTipo());
            preparedStatement.setDouble(3, ad.getValor());
            preparedStatement.setInt(4, ad.getIdUsuario());
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static JsonArray getAds(int idUsuario){
        JsonArray result = new JsonArray();
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String sql = "SELECT * FROM anuncio WHERE idUsuario = "+idUsuario;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                JsonObject ad = new JsonObject();
                JsonObject book = BookPersistence.getBookById(rs.getInt("idLivro"));
                String nome = book.get("nome").toString();
                String idLivro = book.get("idLivro").toString();
                idLivro = idLivro.replace("\""," ");
                nome = nome.replace("\""," ");
                ad.addProperty("nome", nome);
                ad.addProperty("tipo",rs.getString("tipo"));
                ad.addProperty("valor",rs.getDouble("valor"));
                ad.addProperty("idAnuncio", rs.getInt("idAnuncio"));
                ad.addProperty("idLivro", idLivro);
                result.add(ad);
            }
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
}
    
    public static JsonObject getAdById(int idAnuncio){
        JsonObject ad = new JsonObject();
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String sql = "SELECT * FROM anuncio WHERE idAnuncio = "+idAnuncio;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                ad = BookPersistence.getBookById(rs.getInt("idLivro"));
                UserModel user = UserPersistence.getUserById(rs.getInt("idUsuario"));
                ad.addProperty("usuario", user.getNome());
                ad.addProperty("tipo",rs.getString("tipo"));
                ad.addProperty("valor",rs.getDouble("valor"));
                ad.addProperty("idAnuncio",rs.getDouble("idAnuncio"));
            }
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ad; 
    }
    
    public static void deleteAd(int id) {
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String delete = "DELETE FROM anuncio WHERE idAnuncio = " +id;
            Statement stmt = conn.createStatement();
            stmt.execute(delete);
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static void updateAd(JsonObject ad, String id) {
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String updateTableSQL = "UPDATE anuncio SET ";
            int i = 0;
            for (Map.Entry<String, JsonElement> entry : ad.entrySet()) {
                if (!entry.getKey().equalsIgnoreCase("idAnuncio") && !entry.getKey().equalsIgnoreCase("action")) {
                    if (i > 0) {
                        updateTableSQL += ", ";
                    }
                    updateTableSQL += "" + entry.getKey() + " = " + entry.getValue() + " ";
                    i++;
                }
            }
            updateTableSQL += " WHERE idAnuncio = " + id;
            Statement stmt = conn.createStatement();
            stmt.execute(updateTableSQL);
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static JsonArray getAllAds(){
        JsonArray result = new JsonArray();
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String sql = "SELECT * FROM anuncio";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                JsonObject ad = BookPersistence.getBookById(rs.getInt("idLivro"));
                UserModel user = UserPersistence.getUserById(rs.getInt("idUsuario"));
                ad.addProperty("tipo",rs.getString("tipo"));
                ad.addProperty("valor",rs.getDouble("valor"));
                ad.addProperty("idAnuncio", rs.getInt("idAnuncio"));
                ad.addProperty("usuario", user.getNome());
                result.add(ad);
            }
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
}
}