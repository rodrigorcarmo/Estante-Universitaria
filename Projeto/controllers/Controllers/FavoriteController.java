/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.AdModel;
import Persistence.AdPersistence;
import Persistence.FavoritePersistence;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pedro
 */
@WebServlet(name = "FavoriteController", urlPatterns = {"/FavoriteController"})
public class FavoriteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("Add")) {
            String idAnuncio = request.getParameter("idAnuncio");
            String idUsuario = request.getParameter("idUsuario");
            try {
               int result = FavoritePersistence.insert(Integer.valueOf(idAnuncio), Integer.valueOf(idUsuario));
               PrintWriter pw = response.getWriter();
               JsonObject jo = new JsonObject();
               jo.addProperty("resultado", result);
               pw.println(jo);
            } catch (SQLException ex) {
                Logger.getLogger(FavoriteController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (action.equalsIgnoreCase("GETFAVORITES")) {
            String idUsuario = request.getParameter("idUsuario");
            JsonArray data = FavoritePersistence.getAllFavorites(Integer.valueOf(idUsuario));
            PrintWriter pw = response.getWriter();
            pw.println(data);
        }
        else if (action.equalsIgnoreCase("DELETE")) {
            String idFavorito = request.getParameter("idFavorito");
            FavoritePersistence.delete(Integer.valueOf(idFavorito));
        }
    }
    
    public JsonObject requestParamsToJSON(HttpServletRequest req) {
        JsonObject jsonObj = new JsonObject();
        Map<String, String[]> params = req.getParameterMap();
        params.entrySet().stream().forEach((entry) -> {
            String value = entry.getValue()[0];
            jsonObj.addProperty(entry.getKey(), value);
        });
        return jsonObj;
    }

    
}
