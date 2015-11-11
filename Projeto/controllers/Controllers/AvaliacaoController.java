package Controllers;

import Persistence.AvaliacaoPersistence;
import Persistence.TransacaoPersistence;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AvaliacaoController", urlPatterns = {"/AvaliacaoController"})
public class AvaliacaoController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String action = request.getParameter("action");
        if (action.equalsIgnoreCase("AVALIAR")) {
            int tipoId = 0;
            int idUsuario = Integer.valueOf(request.getParameter("idUsuario"));
            String tipo = request.getParameter("tipo");
            int nota = Integer.valueOf(request.getParameter("nota"));
            if(tipo.equalsIgnoreCase("anunciante")){
                tipoId = 1;
            }
            AvaliacaoPersistence.avaliar(tipoId, idUsuario, nota);
        } else if(action.equalsIgnoreCase("GETAVALIACOES")){
            int idUsuario = Integer.valueOf(request.getParameter("idUsuario"));
            JsonObject data = AvaliacaoPersistence.getAvaliacoes(idUsuario);
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
