/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Controllers.AdController;
import DataBase.DbInstance;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro
 */
public class AvaliacaoPersistence {

    public static void avaliar(int tipoId, int idUsuario, int nota) {
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String selectSQL = "SELECT * from avaliacao WHERE anunciante = " + tipoId + " AND idUsuario=" + idUsuario;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectSQL);
            int novaNotaMedia = 0;
            int novaQuantidade = 0;
            if (rs.next()) {
                int quantidade = rs.getInt("quantidade");
                int notaMedia = rs.getInt("notamedia");
                novaQuantidade = quantidade + 1;
                novaNotaMedia = Math.round(((notaMedia * quantidade) + nota) / novaQuantidade);
                String sql = "UPDATE avaliacao SET quantidade = " + novaQuantidade + ", notamedia = " + novaNotaMedia + " WHERE anunciante = " + tipoId + " AND idUsuario=" + idUsuario;
                stmt.execute(sql);
            } else {
                String insertSQL = "INSERT INTO avaliacao (notamedia, idUsuario, anunciante, quantidade) VALUES (?,?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(insertSQL);
                preparedStatement.setInt(1, nota);
                preparedStatement.setInt(2, idUsuario);
                preparedStatement.setInt(3, tipoId);
                preparedStatement.setInt(4, 1);
                preparedStatement.execute();
                preparedStatement.close();
            }
            conn.close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static JsonObject getAvaliacoes(int idUsuario) {
        DbInstance db = new DbInstance();
        JsonObject result = new JsonObject();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String selectSQL = "SELECT * from avaliacao WHERE anunciante = 0  AND idUsuario=" + idUsuario;
            String selectSQL2 = "SELECT * from avaliacao WHERE anunciante = 1  AND idUsuario=" + idUsuario;
            Statement stmt = conn.createStatement();
            ResultSet rsCliente = stmt.executeQuery(selectSQL);
            if(!rsCliente.next()){ 
                result.addProperty("cliente", 0);
            }
            else{
                result.addProperty("cliente", rsCliente.getInt("notamedia"));
            }
             ResultSet rsAnunciante = stmt.executeQuery(selectSQL2);
            if(!rsAnunciante.next()){
                result.addProperty("anunciante", 0);
            }
            else{
                result.addProperty("anunciante", rsAnunciante.getInt("notamedia"));
            }
            conn.close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AdController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
