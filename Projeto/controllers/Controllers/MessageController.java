package Controllers;

import Persistence.MessagePersistence;
import com.google.gson.JsonArray;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MessageController", urlPatterns = {"/MessageController"})
public class MessageController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("INSERTMESSAGE")) {
            int idUsuarioEnvia = Integer.valueOf(request.getParameter("idUsuarioEnvia"));
            int idUsuarioRecebe = Integer.valueOf(request.getParameter("idUsuarioRecebe"));
            int idChat = Integer.valueOf(request.getParameter("idChat"));
            String mensagem = request.getParameter("mensagem");
            MessagePersistence.insertMessage(idUsuarioEnvia, idUsuarioRecebe, idChat, mensagem);
        } else if (action.equalsIgnoreCase("GETCHAT")) {
            int idChat = Integer.valueOf(request.getParameter("idChat"));
            JsonArray data = MessagePersistence.getChat(idChat);
            PrintWriter pw = response.getWriter();
            pw.println(data);
        }
    }

}
