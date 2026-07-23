<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Accedi - RetroGaming</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/login/styleLogin.css">
</head>
<body>

    <div class="login-container">
        <h2>Accedi al tuo Account</h2>

        <c:if test="${param.registrato == 'true'}">
            <div class="success-message">
                Registrazione completata! Puoi effettuare l'accesso.
            </div>
        </c:if>

        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                ${errorMessage}
            </div>
            
            <c:remove var="errorMessage" scope="session" />
        </c:if>

        <form action="${pageContext.request.contextPath}/Login" method="POST">
            
            <div class="form-group">
                <label for="email">Indirizzo Email</label>
                <input type="email" id="email" name="email" required placeholder="emai.prova@dominio.prova.it">
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>

            <button type="submit" class="btn-submit">Accedi</button>

        </form>

        <div class="register-link">
            Non hai ancora un account? <a href="${pageContext.request.contextPath}/Registrazione">Registrati qui</a>
        </div>
    </div>

</body>
</html>