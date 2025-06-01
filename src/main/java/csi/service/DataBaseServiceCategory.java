package csi.service;

import csi.dao.CategoryDAO;
import csi.model.Category;
import csi.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseServiceCategory {

    private Connection connection;

    public DataBaseServiceCategory() {
        try {
            this.connection = new ConnectionFactory().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar com o Banco de Dados");
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

    public String insertCategory(Category category) {
        try {
            return new CategoryDAO(connection).insertCategory(category);
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao inserir categoria: " + e.getMessage();
        }
    }

    public Category getCategoryById(int id) {
        CategoryDAO categoryDAO = new CategoryDAO(connection);
        return categoryDAO.getCategoryById(id);
    }

}
