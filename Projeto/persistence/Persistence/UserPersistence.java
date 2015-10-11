package Persistence;

import Controllers.UserController;
import DataBase.DbInstance;
import Models.UserModel;
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

public class UserPersistence {

    public static void insert(UserModel user) {

        DbInstance db = new DbInstance();
        try {
            //TODO check if email already exists
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String insertTableSQL = "INSERT INTO USUARIO"
                    + "(EMAIL, `PASSWORD`, CEP, ENDERECO, ENDNUM, ENDCOMPLEMENTO,"
                    + " ESTADO, CIDADE, UNIVERSIDADE, TELEFONE, CELULAR, NOME) VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getCep());
            preparedStatement.setString(4, user.getEndereco());
            preparedStatement.setString(5, user.getEndNum());
            preparedStatement.setString(6, user.getEndComplemento());
            preparedStatement.setString(7, user.getEstado());
            preparedStatement.setString(8, user.getCidade());
            preparedStatement.setString(9, user.getUniversidade());
            preparedStatement.setString(10, user.getTelefone());
            preparedStatement.setString(11, user.getCelular());
            preparedStatement.setString(12, user.getNome());
            preparedStatement.execute();
            preparedStatement.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int autenticateUser(String email, String password) {
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String sql = "SELECT * FROM usuario WHERE email = '" + email + "' and password = '" + password + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int result = rs.getInt("idUsuario");
            conn.close();
            return result;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public static void updateUser(JsonObject user, String id) {
        DbInstance db = new DbInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = db.getConnection();
            String updateTableSQL = "UPDATE usuario SET ";
            int i = 0;
            for (Map.Entry<String, JsonElement> entry : user.entrySet()) {
                if (!entry.getKey().equalsIgnoreCase("id") && !entry.getKey().equalsIgnoreCase("action")) {
                    if (i > 0) {
                        updateTableSQL += ", ";
                    }
                    updateTableSQL += "" + entry.getKey() + " = " + entry.getValue() + " ";
                    i++;
                }
            }
            updateTableSQL += " WHERE idUsuario = " + id;
            Statement stmt = conn.createStatement();
            stmt.execute(updateTableSQL);
            conn.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
