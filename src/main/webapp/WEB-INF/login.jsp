<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.utente.UtenteBean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accedi - 1UPShop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/login/styleLogin.css">
</head>

<body>

    <main class="login-container">
    
    	<header class="header-mini">
		    <div class="only-logo">
		        <a href="${pageContext.request.contextPath}/Home">
		            <img src="${pageContext.request.contextPath}/resources/img/logoW.png" alt="1-UPShop Logo" class="logo-img">
		        </a>
		    </div>
		</header>

    	<div class="login-card">
	        <h2>Accedi al tuo Account!</h2>
	
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
	                <input type="email" id="email" name="email" required>
	            </div>
	
	            <div class="form-group">
	                <label for="password">Password</label>
	                <input type="password" id="password" name="password" required>
	            </div>
	
	            <button type="submit" class="btn-submit">Accedi</button>
	
	        </form>
	
	        <div class="register-link">
	            Non hai ancora un account? <a href="${pageContext.request.contextPath}/Registrazione">Registrati qui!</a>
	        </div>
        
        </div>
    </main>
</body>
</html>