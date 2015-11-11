
package Persistence;

import Controllers.AdController;
import Controllers.BookController;
import Controllers.UserController;
import DataBase.DbInstance;
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

public class ChatPersistence {
    
    public static void createChat(int idCliente, int idAnunciante, int idAnuncio) throws SQLException {

        DbInstance db = new DbInstance();
        try {
            //TODO check if email already exists
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String insertTableSQL = "INSERT INTO Chat"
                    + "(idCliente, idAnunciante, idChat) VALUES (?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
            preparedStatement.setInt(1, idCliente);
            preparedStatement.setInt(2, idAnunciante);
            preparedStatement.setInt(3, idAnuncio);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static JsonArray getAllChats(int idUsuario) {
       JsonArray result = new JsonArray();
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String sql = "SELECT * FROM chat WHERE idCliente = "+idUsuario;
            String sql2 = "SELECT * FROM chat WHERE idAnunciante = "+idUsuario;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                JsonObject chat = new JsonObject();
                UserModel um = UserPersistence.getUserById(rs.getInt("idAnunciante"));
                JsonObject ad = AdPersistence.getAdById(rs.getInt("idChat"));
                JsonElement adAux = ad.get("nome");
                String anuncio = adAux.getAsString();
                chat.addProperty("anunciante", um.getNome());
                chat.addProperty("cliente","Voce");
                chat.addProperty("anuncio",anuncio);
                chat.addProperty("idChat",rs.getInt("idChat"));
                chat.addProperty("idAnunciante",rs.getInt("idAnunciante"));
                chat.addProperty("idCliente",rs.getInt("idCliente"));
                chat.addProperty("ultimamensagem",MessagePersistence.getLatestMessage(rs.getInt("idChat")));
                chat.addProperty("usuarioEnvia",MessagePersistence.getLatestMessageUsuarioEnvia(rs.getInt("idChat")));
                chat.addProperty("usuarioRecebe",MessagePersistence.getLatestMessageUsuarioRecebe(rs.getInt("idChat")));
                chat.addProperty("date",MessagePersistence.getLatestMessageTimestamp(rs.getInt("idChat")));
                result.add(chat);
            }
            ResultSet rs2 = stmt.executeQuery(sql2);
            while(rs2.next()){
                JsonObject chat = new JsonObject();
                UserModel um = UserPersistence.getUserById(rs2.getInt("idCliente"));
                JsonObject ad = AdPersistence.getAdById(rs2.getInt("idChat"));
                JsonElement adAux = ad.get("nome");
                String anuncio = adAux.getAsString();
                chat.addProperty("anunciante","Voce");
                chat.addProperty("cliente",um.getNome());
                chat.addProperty("anuncio",anuncio);
                chat.addProperty("idChat",rs2.getInt("idChat"));
                chat.addProperty("idAnunciante",rs2.getInt("idAnunciante"));
                chat.addProperty("idCliente",rs2.getInt("idCliente"));
                chat.addProperty("ultimamensagem",MessagePersistence.getLatestMessage(rs2.getInt("idChat")));
                chat.addProperty("ultimamensagem",MessagePersistence.getLatestMessage(rs2.getInt("idChat")));
                chat.addProperty("usuarioEnvia",MessagePersistence.getLatestMessageUsuarioEnvia(rs2.getInt("idChat")));
                chat.addProperty("usuarioRecebe",MessagePersistence.getLatestMessageUsuarioRecebe(rs2.getInt("idChat")));
                chat.addProperty("date",MessagePersistence.getLatestMessageTimestamp(rs2.getInt("idChat")));
                result.add(chat);
            }
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public static void updateBook(JsonObject book, String id) {
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String updateTableSQL = "UPDATE livro SET ";
            int i = 0;
            for (Map.Entry<String, JsonElement> entry : book.entrySet()) {
                if (!entry.getKey().equalsIgnoreCase("idLivro") && !entry.getKey().equalsIgnoreCase("action")) {
                    if (i > 0) {
                        updateTableSQL += ", ";
                    }
                    updateTableSQL += "" + entry.getKey() + " = " + entry.getValue() + " ";
                    i++;
                }
            }
            updateTableSQL += " WHERE idLivro = " + id;
            Statement stmt = conn.createStatement();
            stmt.execute(updateTableSQL);
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
