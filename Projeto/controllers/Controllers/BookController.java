package Controllers;

import Models.BookModel;
import Persistence.BookPersistence;
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
@WebServlet(name = "BookController", urlPatterns = {"/BookController"})
public class BookController extends HttpServlet {

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
            BookModel book = gson.fromJson(teste, BookModel.class);
            try {
                BookPersistence.insert(book, Integer.valueOf(request.getParameter("idUsuario")));
            } catch (SQLException ex) {
                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (action.equalsIgnoreCase("GETBOOKS")) {
            int idUsuario = Integer.valueOf(request.getParameter("idUsuario"));
            JsonArray data = BookPersistence.getBooks(idUsuario);
            PrintWriter pw = response.getWriter();
            pw.println(data);
        } else if (action.equalsIgnoreCase("UPDATE")) {
            JsonObject teste = requestParamsToJSON(request);
            BookPersistence.updateBook(teste, request.getParameter("idLivro"));
        }else if(action.equalsIgnoreCase("DELETE")){
            int idLivro = Integer.valueOf(request.getParameter("idLivro"));
            BookPersistence.deleteBook(idLivro);
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
