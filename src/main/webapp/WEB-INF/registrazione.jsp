<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrazione - RetroGaming</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/registrazione/styleRegistrazione.css">
    <script src="${pageContext.request.contextPath}/resources/registrazione/scriptRegistrazione.js"></script>
</head>
<body>

    <div class="register-container">
        <h2>Crea un Account</h2>

        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                ${errorMessage}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/Registrazione" method="POST" onsubmit="event.preventDefault(); validate(this)" data-context-path="${pageContext.request.contextPath}">
            
            <div class="form-group">
                <label for="nome">Nome</label>
                <input type="text" id="nome" name="nome" required 
                	oninput="checkName(this)" placeholder="Mario">
            </div>

            <div class="form-group">
                <label for="cognome">Cognome</label>
                <input type="text" id="cognome" name="cognome" required 
                	oninput="checkSurname(this)" placeholder="Rossi">
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required 
                	oninput="checkEmail(this)" placeholder="emai.prova@dominio.prova.it">
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required min="6" 
                	oninput="checkPassword(this); checkConfermaPassword(this, document.getElementById('confermaPassword'))">
            </div>
            
            <div class="form-group">
                <label for="confermaPassword">Conferma Password</label>
                <input type="password" id="confermaPassword" name="confermaPassword" required min="6" 
                	oninput="checkConfermaPassword(document.getElementById('password'), this)">
            </div>

            <button type="submit" class="btn-submit">Registrati</button>

        </form>

        <div class="login-link">
            Hai già un account? <a href="${pageContext.request.contextPath}/Login">Accedi qui</a>
        </div>
    </div>

</body>
</html>