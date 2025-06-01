package csi.controller;

import csi.model.Category;
import csi.model.Task;
import csi.model.User;
import csi.service.DataBaseServiceCategory;
import csi.service.DataBaseServiceTask;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.util.List;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {

    private DataBaseServiceCategory db_category;
    private DataBaseServiceTask db_task;

    @Override
    public void init() throws ServletException {
        super.init();

        db_task = new DataBaseServiceTask();
        db_category = new DataBaseServiceCategory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        System.out.println("Action recebida no doGet: " + action);

        if ("criar".equals(action)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/criar-tarefa.jsp");
            dispatcher.forward(request, response);
            return;
        } else if ("listar-pendentes".equals(action)) {

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            System.out.println("Codigo Usuario: " + user.getId());
            List<Task> pendingTasks = db_task.listTasks(user.getId());


            for (Task task : pendingTasks) {

                System.out.println("Task ID: " + task.getId()
                        + ", Título: " + task.getTitle()
                        + ", Categoria ID: " + (task.getCategory() != null ? task.getCategory().getId() : "null"));
            }

            request.setAttribute("tasks", pendingTasks);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listar-pendentes.jsp");
            dispatcher.forward(request, response);
            return;
        } else if ("listar-concluidas".equals(action)) {

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            System.out.println("Codigo Usuario: " + user.getId());
            List<Task> concludetTasks = db_task.listConcludedTasks(user.getId());


            for (Task task : concludetTasks) {

                System.out.println("Task ID: " + task.getId()
                        + ", Título: " + task.getTitle()
                        + ", Categoria ID: " + (task.getCategory() != null ? task.getCategory().getId() : "null"));
            }

            request.setAttribute("tasks", concludetTasks);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listar-concluidas.jsp");
            dispatcher.forward(request, response);
            return;
        }else if ("trabalho".equals(action) || "pessoal".equals(action) || "estudo".equals(action)) {


            String categoryStr = action;

            Category category = new Category();
            switch (categoryStr) {
                case "trabalho":
                    category.setId(1);
                    category.setName("Trabalho");
                    break;
                case "pessoal":
                    category.setId(2);
                    category.setName("Pessoal");
                    break;
                case "estudo":
                    category.setId(3);
                    category.setName("Estudo");
                    break;
            }

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            System.out.println("Codigo Usuario: " + user.getId());

            List<Task> filterTasks = db_task.listFilterTasks(user.getId(), category);

            request.setAttribute("tasks", filterTasks);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listar-pendentes.jsp");
            dispatcher.forward(request, response);
            return;
        }


        else if ("editar".equals(action)) {

            String idStr = request.getParameter("id");
            System.out.println("Codigo da Task no GET: " + idStr);

            Integer idTask = null;
            try {
                idTask = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            System.out.println("Codigo da Task no GET: " + idTask);

            if (idTask != null) {
                Task t = db_task.getTaskById(idTask);
                request.setAttribute("task", t);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/atualizar-tarefa.jsp");
            dispatcher.forward(request, response);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("criar".equals(action)) {
            String title = request.getParameter("titulo");
            String desc = request.getParameter("descricao");
            String categoryStr = request.getParameter("categoria");

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            Category category = new Category();
            switch (categoryStr) {
                case "trabalho":
                    category.setId(1);
                    category.setName("Trabalho");
                    break;
                case "pessoal":
                    category.setId(2);
                    category.setName("Pessoal");
                    break;
                case "estudo":
                    category.setId(3);
                    category.setName("Estudo");
                    break;
            }

            Task t = new Task();

            t.setTitle(title);
            t.setDescription(desc);
            t.setStatus(true);
            t.setUser(user);
            t.setCategory(category);

            db_task.insertTask(t);

            response.sendRedirect(request.getContextPath() + "/tasks?action=listar-pendentes");

        } else if ("editar".equals(action)) {

            String newTitle = request.getParameter("titulo");
            String newDesc = request.getParameter("descricao");
            String idStr = request.getParameter("id");

            System.out.println("Id da Task no POST: "+ idStr);

            Integer idTask = null;
            try {
                idTask = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            System.out.println("ID da task passado: " + idTask);

            if (idTask != null) {
                Task t = db_task.getTaskById(idTask);

                if (t != null) {

                    t.setTitle(newTitle);
                    t.setDescription(newDesc);
                    t.setStatus(true);

                    db_task.updateTask(t);
                }
            }

            response.sendRedirect(request.getContextPath() + "/tasks?action=listar-pendentes");
        }else if ("deletar".equals(action)) {
            String idStr = request.getParameter("id");
            System.out.println("Código da Task no POST para deletar: " + idStr);

            try {
                int id = Integer.parseInt(idStr);
                db_task.deletTask(id);
            } catch (NumberFormatException e) {
                System.out.println("ID inválido para deleção: " + idStr);
            }

            response.sendRedirect(request.getContextPath() + "/tasks?action=listar-pendentes");
        } else if("concluida".equals(action)){
            String idStr = request.getParameter("taskId");
            System.out.println("Código da Task no POST para concluir: " + idStr);

            try {
                int id = Integer.parseInt(idStr);
                db_task.conCludedTask(id);
            } catch (NumberFormatException e) {
                System.out.println("ID inválido para deleção: " + idStr);
            }
            response.sendRedirect(request.getContextPath() + "/tasks?action=listar-pendentes");
        }

    }


}
