<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
  <title>Cadastrar Usuário</title>
</head>
<body>
<div class="container">
  <h1>Cadastro de Usuário</h1>

  <form action="${pageContext.request.contextPath}/user" method="POST">
    <input type="hidden" name="action" value="cadastrar" />

    <div class="form_input">
      <label for="nome">Nome:</label>
      <input type="text" id="nome" name="nome" required />
    </div>

    <div class="form_input">
      <label for="email">Email:</label>
      <input type="email" id="email" name="email" required />
    </div>

    <div class="form_input">
      <label for="password">Senha:</label>
      <input type="password" id="password" name="password" required />
    </div>

    <div class="form_input">
    <button type="submit">Cadastrar</button>
    </div>

  </form>
</div>
</body>
</html>
