package csi.service;

import csi.dao.TaskDAO;
import csi.dao.UserDAO;
import csi.model.Category;
import csi.model.Task;
import csi.model.User;
import csi.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DataBaseServiceTask {
    private Connection connection;

    public DataBaseServiceTask() {

        try {
            this.connection = new ConnectionFactory().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println( "Erro ao conectar com o Banco de Dados");
        }
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexão com o banco fechada.");
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }


    public String insertTask(Task task) {
        try {
            return new TaskDAO(connection).insertTask(task);
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao inserir tarefa: " + e.getMessage();
        }
    }

    public String updateTask(Task task) {
        try {
            return new TaskDAO(connection).updateTask(task);
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao atualizar tarefa: " + e.getMessage();
        }
    }

    public String deletTask(int id) {

        try {
            return new TaskDAO(connection).deletTask(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao deletar tarefa: " + e.getMessage();
        }
    }

    public String deletTaskByUserId(int id) {

        try {
            return new TaskDAO(connection).deleteTasksByUserId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao deletar tarefa: " + e.getMessage();
        }
    }

    public String conCludedTask(int id){
        try {
            return new TaskDAO(connection).concludedTask(id);
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao concluir tarefa: " + e.getMessage();
        }
    }


    public List<Task> listTasks(int id) {
        return new TaskDAO(connection).getTasks(id);
    }

    public List<Task> listFilterTasks(int id, Category category) {
        return new TaskDAO(connection).getTasksByCategory(id, category);
    }

    public List<Task> listConcludedTasks(int id) {
        return new TaskDAO(connection).getConcludedTasks(id);
    }

    public Task getTaskById(int id) {
        return new TaskDAO(connection).getTaskById(id);
    }






}
