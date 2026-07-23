<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifica Profilo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/modificaProfilo/styleModificaProfilo.css">
    <script src="${pageContext.request.contextPath}/resources/modificaProfilo/scriptModificaProfilo.js"></script>
</head>
<body>

    <h2>Modifica i dati del tuo profilo</h2>

    <div class="form-container">
        <c:if test="${not empty errorMessage}">
            <div class="error-box">
              	${errorMessage}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/ModificaProfilo" method="post" onsubmit="event.preventDefault(); validate(this)">
            
            <!-- SEZIONE NOME -->
            <div class="form-group">
                <label class="checkbox-label">
                    <input type="checkbox" onchange="toggleField(this, ['nuovoNome'])"> Modifica Nome
                </label>
                <input type="text" id="nuovoNome" name="nuovoNome" 
                       value="${not empty param.nuovoNome ? param.nuovoNome : sessionScope.utente.nome}" 
                       maxlength="100" oninput="checkName(this)" disabled>
            </div>

            <!-- SEZIONE COGNOME -->
            <div class="form-group">
                <label class="checkbox-label">
                    <input type="checkbox" onchange="toggleField(this, ['nuovoCognome'])"> Modifica Cognome
                </label>
                <input type="text" id="nuovoCognome" name="nuovoCognome" 
                       value="${not empty param.nuovoCognome ? param.nuovoCognome : sessionScope.utente.cognome}" 
                       maxlength="100" oninput="checkSurname(this)" disabled>
            </div>

            <!-- SEZIONE PASSWORD (Una sola checkbox sblocca entrambi i campi password) -->
            <div class="form-group">
                <label class="checkbox-label">
                    <input type="checkbox" onchange="toggleField(this, ['nuovaPassword', 'confermaNuovaPassword'])"> Modifica Password
                </label>
                
                <div class="password-block">
                    <label for="nuovaPassword">Nuova Password:</label>
                    <input type="password" id="nuovaPassword" name="nuovaPassword" 
                           minlength="8" maxlength="100" oninput="checkPassword(this)" disabled>

                    <label for="confermaNuovaPassword">Conferma Nuova Password:</label>
                    <input type="password" id="confermaNuovaPassword" name="confermaNuovaPassword" 
                           minlength="8" maxlength="100" oninput="checkConfermaPassword(document.getElementById('nuovaPassword'), this)" disabled>
                </div>
            </div>

            <button type="submit" class="btn-submit">Salva Modifiche</button>
        </form>
    </div>

    <br>
    <a href="${pageContext.request.contextPath}/Home">Torna alla Home</a>

</body>
</html>