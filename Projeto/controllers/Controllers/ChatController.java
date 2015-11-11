package Controllers;

import Persistence.AdPersistence;
import Persistence.ChatPersistence;
import Persistence.MessagePersistence;
import Persistence.TransacaoPersistence;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

@WebServlet(name = "ChatController", urlPatterns = {"/ChatController"})
public class ChatController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("CREATECHAT")) {
            int idCliente = Integer.valueOf(request.getParameter("idCliente"));
            int idAnuncio = Integer.valueOf(request.getParameter("idAnuncio"));
            String mensagem = request.getParameter("mensagem");
            JsonObject anunciante = AdPersistence.getAdById(idAnuncio);
            JsonElement aux = anunciante.get("idUsuario");
            int idAnunciante = aux.getAsInt();
            try {
                ChatPersistence.createChat(idCliente, idAnunciante, idAnuncio);
            } catch (SQLException ex) {
                Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            }
            MessagePersistence.insertMessage(idCliente,idAnunciante,idAnuncio,mensagem);
            TransacaoPersistence.setTransacao(idAnunciante, idCliente, idAnuncio);
        } else if(action.equalsIgnoreCase("GETALLCHATS")){
            int idUsuario = Integer.valueOf(request.getParameter("idUsuario"));
            JsonArray data = ChatPersistence.getAllChats(idUsuario);
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
