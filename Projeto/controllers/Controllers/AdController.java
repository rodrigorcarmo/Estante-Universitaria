package Controllers;

import Models.AdModel;
import Persistence.AdPersistence;
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

@WebServlet(name = "AdController", urlPatterns = {"/AdController"})
public class AdController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("Add")) {
            JsonObject teste = requestParamsToJSON(request);
            Gson gson = new Gson();
            AdModel ad = gson.fromJson(teste, AdModel.class);
            try {
                AdPersistence.insert(ad);
            } catch (SQLException ex) {
                Logger.getLogger(AdController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (action.equalsIgnoreCase("GETADS")) {
            int idUsuario = Integer.valueOf(request.getParameter("idUsuario"));
            JsonArray data = AdPersistence.getAds(idUsuario);
            PrintWriter pw = response.getWriter();
            pw.println(data);
        }else if(action.equalsIgnoreCase("GETADBYID")){
            int idAnuncio = Integer.valueOf(request.getParameter("idAnuncio"));
            JsonObject ad = AdPersistence.getAdById(idAnuncio);
            PrintWriter pw = response.getWriter();
            pw.println(ad);
        } else if(action.equalsIgnoreCase("DELETE")){
            int idAnuncio = Integer.valueOf(request.getParameter("idAnuncio"));
            AdPersistence.deleteAd(idAnuncio);
        }else if (action.equalsIgnoreCase("UPDATE")) {
            JsonObject teste = requestParamsToJSON(request);
            AdPersistence.updateAd(teste, request.getParameter("idAnuncio"));
        }else if (action.equalsIgnoreCase("GETALLADS")) {
            JsonArray data = AdPersistence.getAllAds();
            PrintWriter pw = response.getWriter();
            pw.println(data);
        } else if (action.equalsIgnoreCase("QUESTION")){
            int idAnuncio = Integer.valueOf(request.getParameter("idAnuncio"));
            int idUsuario = Integer.valueOf(request.getParameter("idUsuario"));
            String question = request.getParameter("question");
            AdPersistence.addQuestion(idAnuncio,idUsuario,question);
        } else if (action.equalsIgnoreCase("GETCOMMENTS")){
            int idAnuncio = Integer.valueOf(request.getParameter("idAnuncio"));
            JsonArray data = AdPersistence.getComments(idAnuncio);
            PrintWriter pw = response.getWriter();
            pw.println(data);
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
