package Controllers;

import com.google.gson.*;
import Models.UserModel;
import java.io.IOException;
import java.io.PrintWriter;
import Persistence.*;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action.equalsIgnoreCase("REGISTER")){
            JsonObject teste = requestParamsToJSON(request);
            Gson gson = new Gson();
            UserModel user = gson.fromJson(teste, UserModel.class);
            UserPersistence.insert(user);
        }
        else if(action.equalsIgnoreCase("LOGIN")){
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            PrintWriter pw = response.getWriter();
            int id = UserPersistence.autenticateUser(email, password);
            pw.append(Integer.toString(id));
        }
        else if(action.equalsIgnoreCase("UPDATE")){
            JsonObject teste = requestParamsToJSON(request);
            UserPersistence.updateUser(teste, request.getParameter("id"));
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
