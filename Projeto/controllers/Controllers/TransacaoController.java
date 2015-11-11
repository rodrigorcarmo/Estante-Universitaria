package Controllers;

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

@WebServlet(name = "TransacaoController", urlPatterns = {"/TransacaoController"})
public class TransacaoController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String action = request.getParameter("action");
        if (action.equalsIgnoreCase("GETTRANSACOES")) {
            int idUsuario = Integer.valueOf(request.getParameter("idUsuario"));
            JsonArray data = TransacaoPersistence.getTransacoesByUser(idUsuario);
            PrintWriter pw = response.getWriter();
            pw.println(data);
        } else if (action.equalsIgnoreCase("UPDATETRANSACAO")) {
            int idTransacao = Integer.valueOf(request.getParameter("idTransacao"));
            int status = Integer.valueOf(request.getParameter("status"));
            TransacaoPersistence.updateTransacao(idTransacao, status);
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
