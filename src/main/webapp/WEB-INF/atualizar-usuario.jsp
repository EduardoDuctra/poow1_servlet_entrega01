<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />

  <title>Atualizar Usuário</title>
</head>
<body>
<div class="container">
<h1>Atualizar Usuário</h1>

<form action="${pageContext.request.contextPath}/user" method="POST">

  <div class="form_input">
  <input type="hidden" name="id" value="${user.id}" />

  <label for="nome">Nome:</label>
  <input type="text" id="nome" name="nome" value="${user.name}" required /><br/><br/>

  <label for="email">Email:</label>
  <input type="email" id="email" name="email" value="${user.email}" required /><br/><br/>

  <label for="password">Senha:</label>
  <input type="password" id="password" name="password" value="${user.password}" required /><br/><br/>

  </div>

  <button type="submit" name="action" value="atualizar">Atualizar</button>
  <button type="submit" name="action" value="deletar">Deletar</button>

  <form action="/menu" method="get">
    <button type="submit" name="action" value="voltar">Voltar ao Menu</button>
  </form>

</form>

</div>
</body>
</html>
