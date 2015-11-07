
package Persistence;

import Controllers.BookController;
import Controllers.UserController;
import DataBase.DbInstance;
import Models.BookModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookPersistence {
    
    public static void insert(BookModel book, int idUsuario) throws SQLException {

        DbInstance db = new DbInstance();
        try {
            //TODO check if email already exists
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String insertTableSQL = "INSERT INTO LIVRO"
                    + "(nome, autor, editora, edicao, ano, condicao, wishlist, idUsuario, imagem )"
                    + " VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, book.getNome());
            preparedStatement.setString(2, book.getAutor());
            preparedStatement.setString(3, book.getEditora());
            preparedStatement.setString(4, book.getEdicao());
            preparedStatement.setInt(5, book.getAno());
            preparedStatement.setString(6, book.getCondicao());
            preparedStatement.setInt(7, book.isWishlist());
            preparedStatement.setInt(8, idUsuario);
            preparedStatement.setString(9, book.getImagem());
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static JsonArray getBooks(int idUsuario){
        JsonArray result = new JsonArray();
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String sql = "SELECT * FROM livro WHERE idUsuario = "+idUsuario;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                JsonObject book = new JsonObject();
                book.addProperty("idLivro", rs.getInt("idLivro"));
                book.addProperty("nome",rs.getString("nome"));
                book.addProperty("autor",rs.getString("autor"));
                book.addProperty("editora",rs.getString("editora"));
                book.addProperty("edicao",rs.getString("edicao"));
                book.addProperty("ano",rs.getInt("ano"));
                book.addProperty("condicao",rs.getString("condicao"));
                book.addProperty("wishlist",rs.getInt("wishlist"));
                book.addProperty("imagem",rs.getString("imagem"));
                result.add(book);
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
    
    public static void deleteBook(int id) {
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String delete = "DELETE FROM livro WHERE idLivro = " +id;
            Statement stmt = conn.createStatement();
            stmt.execute(delete);
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static JsonObject getBookById(int idLivro){
       JsonObject book = new JsonObject();
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String sql = "SELECT * FROM livro WHERE idLivro = "+idLivro;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                book.addProperty("idLivro", rs.getInt("idLivro"));
                book.addProperty("nome",rs.getString("nome"));
                book.addProperty("autor",rs.getString("autor"));
                book.addProperty("editora",rs.getString("editora"));
                book.addProperty("edicao",rs.getString("edicao"));
                book.addProperty("ano",rs.getInt("ano"));
                book.addProperty("condicao",rs.getString("condicao"));
                book.addProperty("wishlist",rs.getInt("wishlist"));
                book.addProperty("imagem",rs.getString("imagem"));
            }
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return book; 
    }
}
