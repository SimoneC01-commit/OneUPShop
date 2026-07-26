<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifica Profilo</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/modificaProfilo/styleModificaProfilo.css">
    <script src="${pageContext.request.contextPath}/resources/modificaProfilo/scriptModificaProfilo.js"></script>
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/favicon.ico">
</head>

<body>
<jsp:include page="common/header.jsp" />

	<main class="main-container">
    <h2>Modifica i dati del tuo profilo</h2>
    
	<section class="form-container">
   
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

            <!-- PASSWOR : una checkbox per entrambi -->
            <div class="form-group">
                <label class="checkbox-label">
                    <input type="checkbox" onchange="toggleField(this, ['nuovaPassword', 'confermaNuovaPassword'])"> Modifica Password
                </label>
                
                <div class="password-block">
                    <label for="nuovaPassword">Nuova Password:</label>
                    <input type="password" id="nuovaPassword" name="nuovaPassword" 
                           min="8" maxlength="100" oninput="checkPassword(this)" disabled>

                    <label for="confermaNuovaPassword">Conferma Nuova Password:</label>
                    <input type="password" id="confermaNuovaPassword" name="confermaNuovaPassword" 
                           min="8" maxlength="100" oninput="checkConfermaPassword(document.getElementById('nuovaPassword'), this)" disabled>
                </div>
            </div>

            <button type="submit" class="btn-submit">Salva Modifiche</button>
        </form>
    </section>

    <br>
    <a href="${pageContext.request.contextPath}/Profilo">← Torna al Profilo</a>
</main>

<jsp:include page="common/footer.jsp" />
</body>
</html>