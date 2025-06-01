<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
  <title>Menu de Tarefas</title>
</head>
<body>
<div class="container">
  <div class="navegation_container">
    <div class="navegation_user">
      <h3>Bem-vindo, ${sessionScope.user.name}</h3>
      <p>ID de Usuário: ${sessionScope.user.id}</p>
    </div>

    <div class="navegation_user_update">
      <form action="${pageContext.request.contextPath}/user" method="get">
        <input type="hidden" name="action" value="atualizar" />
        <button type="submit">Meus Dados</button>
      </form>
    </div>
  </div>

  <div class="menu">

  <ul class="menu_buttons">

    <li>
      <form action="${pageContext.request.contextPath}/tasks" method="get">
        <input type="hidden" name="action" value="criar" />
        <button type="submit" class="button_menu">
          <img
                  class="icon_button"
                  src="assets/add_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg"
                  alt="Ícone Criar Tarefa"
          /><br/>
          Criar Tarefa
        </button>
      </form>
    </li>


    <li>
      <form action="${pageContext.request.contextPath}/tasks" method="get">
        <input type="hidden" name="action" value="listar-pendentes">
        <button type="submit" class="button_menu"> <img
                class="icon_button"
                src="assets/menu_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg"
                alt="Ícone Tarefas Pendentes"
        />
          Tarefas Pendentes
        </button>
      </form>
    </li>


  <li>
    <form action="${pageContext.request.contextPath}/tasks" method="get">
      <input type="hidden" name="action" value="listar-concluidas">
      <button type="submit"class="button_menu">
        <img
                class="icon_button"
                src="assets/fact_check_24dp_1F1F1F_FILL0_wght400_GRAD0_opsz24.svg"
                alt="Ícone Tarefas Concluidos"
        />
        Tarefas Concluídas
      </button>
    </form>
  </li>



</ul>

  </div>

<c:if test="${not empty msg}">
  <p>${msg}</p>
</c:if>

</body>
</html>
