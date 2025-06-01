package csi.controller;

import csi.model.User;
import csi.service.DataBaseServiceTask;
import csi.service.DataBaseServiceUser;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    private DataBaseServiceUser db_user;
    private DataBaseServiceTask db_task;

    @Override
    public void init() throws ServletException {
        super.init();
        db_user = new DataBaseServiceUser();
        db_task = new DataBaseServiceTask();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        System.out.println("Action recebida no doGet: " + action);

        if ("cadastrar".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/cadastrar-usuario.jsp");
            dispatcher.forward(request, response);
            return;

        } else if ("atualizar".equals(action)) {

            // Obtem a sessção atual, sem criar uma nova. Usa o usuário logado
            HttpSession session = request.getSession(false);

            //se a sessão é nula (ngn logado) vai para o index
            if (session == null) {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }

            User loggedUser = (User) session.getAttribute("user");

            if (loggedUser == null) {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }

            //Apenas atualizei o user da sessão. O que foi passado no LoginServlet
            request.setAttribute("user", loggedUser);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/atualizar-usuario.jsp");
            dispatcher.forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/menu");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        System.out.println("Action recebida no doPost: " + action);

        try {
            if ("cadastrar".equals(action)) {

                String name = request.getParameter("nome");
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);

                String resultado = db_user.insertUser(user);
                request.getSession().setAttribute("message", resultado);

                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }

            //Recupero a sessão com o usuário já logado
            HttpSession session = request.getSession(false);

            if (session == null) {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }

            User sessionUser = (User) session.getAttribute("user");

            if (sessionUser == null) {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }

            // Pego os parâmetros aqui para usar no atualizar
            String name = request.getParameter("nome");
            String email = request.getParameter("email");
            String password = request.getParameter("password");


            if ("deletar".equals(action)) {

                int id = sessionUser.getId();

                db_task.deletTaskByUserId(id);
                db_user.deleteUser(id);

                //encerra a sessão. O usuário foi excluido
                session.invalidate();
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            } else if ("atualizar".equals(action)) {

                sessionUser.setName(name);
                sessionUser.setEmail(email);
                sessionUser.setPassword(password);

                db_user.updateUser(sessionUser);

                //eu só atribuo a sessão o novo usuario
                session.setAttribute("user", sessionUser);

                response.sendRedirect(request.getContextPath() + "/menu");

            } else if ("voltar".equals(action)) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/menu.jsp");
                dispatcher.forward(request, response);
            }


        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "Erro: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/atualizar-usuario.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {
        db_user.close();
        super.destroy();
    }
}
