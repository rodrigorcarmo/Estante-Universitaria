package Persistence;

import Controllers.BookController;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransacaoPersistence {

    public static void setTransacao(int idUsuarioAnunciante, int idUsuarioCliente, int idAnuncio) {
        JsonArray result = new JsonArray();
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String sql = "INSERT INTO transacao (idUsuarioAnunciante, idUsuarioCliente, idAnuncio, status) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, idUsuarioAnunciante);
            preparedStatement.setInt(2, idUsuarioCliente);
            preparedStatement.setInt(3, idAnuncio);
            preparedStatement.setInt(4, 0);
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateTransacao(int idTransacao, int status) {
        JsonArray result = new JsonArray();
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            int statusUpdate=0;
            int statusAtual = TransacaoPersistence.getTransacao(idTransacao); 
            if(statusAtual == 0){
                statusUpdate = status;
            }
            else if(statusAtual == -1){
                statusUpdate = statusAtual;
            }
            else if(statusAtual == 1 && status == 1){
                statusUpdate = 1;
            }
            else if(statusAtual == 1 && status == -1){
                statusUpdate = -1;
            }
            String sql = "UPDATE transacao SET status = " + statusUpdate + " WHERE idTransacao=" + idTransacao;
            Statement stmt = conn.createStatement();
            stmt.execute(sql);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BookController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int getTransacao(int idTransacao) {
        JsonArray result = new JsonArray();
        DbInstance db = new DbInstance();
        int status = -2;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String sql = "SELECT status FROM transacao WHERE idTransacao = " + idTransacao;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            status = rs.getInt("status");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BookController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public static JsonArray getTransacoesByUser(int idUsuario) {
        JsonArray result = new JsonArray();
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String sql = "SELECT * FROM transacao WHERE idUsuarioAnunciante = " + idUsuario;
            String sql2 = "SELECT * FROM transacao WHERE idUsuarioCliente = " + idUsuario;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                JsonObject transacao = new JsonObject();
                UserModel um = UserPersistence.getUserById(rs.getInt("idUsuarioCliente"));
                transacao.addProperty("anunciante", "Voce");
                transacao.addProperty("idAnunciante", rs.getInt("idUsuarioAnunciante"));
                transacao.addProperty("idCliente", rs.getInt("idUsuarioCliente"));
                transacao.addProperty("cliente", um.getNome());
                transacao.addProperty("idTransacao", rs.getInt("idTransacao"));
                transacao.addProperty("status", rs.getInt("status"));
                result.add(transacao);
            }
            ResultSet rs2 = stmt.executeQuery(sql2);
            while (rs2.next()) {
                JsonObject transacao = new JsonObject();
                UserModel um = UserPersistence.getUserById(rs2.getInt("idUsuarioAnunciante"));
                transacao.addProperty("anunciante", um.getNome());
                transacao.addProperty("cliente", "Voce");
                transacao.addProperty("idAnunciante", rs2.getInt("idUsuarioAnunciante"));
                transacao.addProperty("idCliente", rs2.getInt("idUsuarioCliente"));
                transacao.addProperty("idTransacao", rs2.getInt("idTransacao"));
                transacao.addProperty("status", rs2.getInt("status"));
                result.add(transacao);

            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BookController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
