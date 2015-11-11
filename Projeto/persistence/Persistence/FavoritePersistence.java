package Persistence;

import Controllers.AdController;
import Controllers.FavoriteController;
import DataBase.DbInstance;
import Models.UserModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FavoritePersistence {
    
    public static int insert(int idAnuncio, int idUsuario) throws SQLException {

        DbInstance db = new DbInstance();
        try {
            //TODO check if email already exists
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String sql = "SELECT * from Favoritos where idUsuario = "+idUsuario;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                if(rs.getInt("idAnuncio") == idAnuncio)
                    return 0;
            }
            String insertTableSQL = "INSERT INTO Favoritos"
                    + "(idAnuncio, idUsuario) VALUES ("+idAnuncio+","+idUsuario+")";
            Statement stmt2 = conn.createStatement();
            stmt2.executeUpdate(insertTableSQL);
            stmt2.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FavoriteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }
    
    public static JsonArray getAllFavorites(int idUsuario){
        JsonArray result = new JsonArray();
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String sql = "SELECT * FROM favoritos WHERE idUsuario = "+idUsuario;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                JsonObject ad = AdPersistence.getAdById(rs.getInt("idAnuncio"));
                UserModel user = UserPersistence.getUserById(rs.getInt("idUsuario"));
                ad.addProperty("usuario", user.getNome());
                ad.addProperty("idUsuario", user.getIdUsuario());
                ad.addProperty("idFavorito", rs.getInt("idFavorito"));
                result.add(ad);
            }
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public static void delete(int id) {
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String delete = "DELETE FROM favoritos WHERE idFavorito = " +id;
            Statement stmt = conn.createStatement();
            stmt.execute(delete);
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(FavoriteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
