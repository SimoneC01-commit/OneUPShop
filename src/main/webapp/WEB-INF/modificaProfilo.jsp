<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifica Profilo</title>
    <style>
        body { font-family: sans-serif; margin: 30px; line-height: 1.6; }
        .form-container { border: 1px solid #ccc; padding: 20px; max-width: 400px; border-radius: 8px; background-color: #f9f9f9; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
        .form-group input { width: 100%; padding: 8px; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px; }
        .btn-submit { background-color: #007BFF; color: white; border: none; padding: 10px 15px; border-radius: 4px; cursor: pointer; width: 100%; font-size: 1em; }
        .btn-submit:hover { background-color: #0056b3; }
        .error-box { color: red; font-weight: bold; background-color: #f8d7da; padding: 10px; border-radius: 4px; margin-bottom: 15px; border: 1px solid #f5c6cb; }
    </style>
</head>
<body>

    <h2>Modifica i dati del tuo profilo</h2>

    <div class="form-container">
        <!-- Mostra il messaggio di errore se presente nella richiesta -->
        <c:if test="${not empty errorMessage}">
            <div class="error-box">
                ⚠️ ${errorMessage}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/ModificaProfilo" method="post">
            
            <div class="form-group">
                <label for="nuovoNome">Nuovo Nome:</label>
                <!-- Precompila con il vecchio valore inserito in caso di errore, altrimenti usa il nome attuale in sessione -->
                <input type="text" id="nuovoNome" name="nuovoNome" 
                       value="${not empty param.nuovoNome ? param.nuovoNome : sessionScope.utente.nome}" required>
            </div>

            <div class="form-group">
                <label for="nuovoCognome">Nuovo Cognome:</label>
                <input type="text" id="nuovoCognome" name="nuovoCognome" 
                       value="${not empty param.nuovoCognome ? param.nuovoCognome : sessionScope.utente.cognome}" required>
            </div>

            <div class="form-group">
                <label for="nuovaPassword">Nuova Password:</label>
                <input type="password" id="nuovaPassword" name="nuovaPassword" required>
            </div>

            <button type="submit" class="btn-submit">Salva Modifiche</button>
        </form>
    </div>

    <br>
    <a href="${pageContext.request.contextPath}/Home">Torna alla Home</a>

</body>
</html>