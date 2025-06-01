package csi.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String action = request.getParameter("action");
        

        String userIdParam = request.getParameter("id");
        HttpSession session = request.getSession();

        //recupera o id e mantem ele na sessão
        if (userIdParam != null && !userIdParam.isEmpty()) {
            session.setAttribute("userId", userIdParam);
        } else {
            userIdParam = (String) session.getAttribute("userId");
        }

        System.out.println("ID do usuário recebido no MenuServlet: " + userIdParam);

        //serve para passar o ID do usuario para os formulários
        if (userIdParam != null) {
            int userId = Integer.parseInt(userIdParam);
            request.setAttribute("userId", userId);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/menu.jsp");
        dispatcher.forward(request, response);
    }
}
