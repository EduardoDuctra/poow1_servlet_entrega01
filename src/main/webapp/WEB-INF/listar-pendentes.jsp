<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8" />
  <title>Tarefas Pendentes</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />

</head>
<body>
<div class="container">
  <h1>Tarefas Pendentes</h1>

  <c:choose>
    <c:when test="${empty tasks}">
      <p>Não há tarefas pendentes.</p>
    </c:when>
    <c:otherwise>

      <div class="button_category">
        <form action="${pageContext.request.contextPath}/tasks" method="get">
          <input type="hidden" name="action" value="listar-pendentes" />
          <button type="submit">Listar Todas</button>
        </form>

        <form action="${pageContext.request.contextPath}/tasks" method="get">
          <input type="hidden" name="action" value="trabalho" />
          <button type="submit">Trabalho</button>
        </form>

        <form action="${pageContext.request.contextPath}/tasks" method="get">
          <input type="hidden" name="action" value="estudo" />
          <button type="submit">Estudo</button>
        </form>

        <form action="${pageContext.request.contextPath}/tasks" method="get">
          <input type="hidden" name="action" value="pessoal" />
          <button type="submit">Pessoal</button>
        </form>
      </div>

      <table>
        <thead>
        <tr>
          <th>ID</th>
          <th>Título</th>
          <th>Descrição</th>
          <th>Categoria</th>
          <th colspan="3">Ações</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="task" items="${tasks}">
          <tr>
            <td>${task.id}</td>
            <td>${task.title}</td>
            <td>${task.description}</td>
            <td>${task.category.name}</td>


            <td>
              <form action="${pageContext.request.contextPath}/tasks" method="post">
                <input type="hidden" name="action" value="concluida" />
                <input type="hidden" name="taskId" value="${task.id}" />
                <button type="submit" class="button_list">
                  <img
                          class="icon_button"
                          src="assets/check_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg"
                          alt="Ícone Concluída"
                  />
                </button>
              </form>
            </td>

            <td>
              <form action="${pageContext.request.contextPath}/tasks" method="get">
                <input type="hidden" name="action" value="editar" />
                <input type="hidden" name="id" value="${task.id}" />
                <button type="submit" class="button_list">
                  <img
                          class="icon_button"
                          src="assets/edit_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg"
                          alt="Ícone Editar"
                  />
                </button>
              </form>
            </td>

            <td>
              <form action="${pageContext.request.contextPath}/tasks" method="post">
                <input type="hidden" name="action" value="deletar" />
                <input type="hidden" name="id" value="${task.id}" />
                <button type="submit" class="button_list">
                  <img
                          class="icon_button"
                          src="assets/delete_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg"
                          alt="Ícone Deletar"
                  />
                </button>
              </form>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </c:otherwise>
  </c:choose>

  <br />

  <form action="${pageContext.request.contextPath}/menu" method="get">
    <input type="hidden" name="action" value="voltar" />
    <button type="submit">Voltar</button>
  </form>
</div>
</body>
</html>
