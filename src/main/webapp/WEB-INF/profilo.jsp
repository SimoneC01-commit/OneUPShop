<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.utente.UtenteBean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profilo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/profilo/styleProfilo.css">
</head>

<body>

<jsp:include page="common/header.jsp" />

	<main class="container-profile">

		<div class="titolo-profilo">
	    <h1>Il tuo Profilo</h1>
	    </div>
	
	    <div class="profilo-card">
	        <p><strong>Nome:</strong> ${sessionScope.utente.nome}</p>
	        <p><strong>Cognome:</strong> ${sessionScope.utente.cognome}</p>
	        <p><strong>Email:</strong> ${utente.email}</p>
	    </div>
	
	    <!-- Pulsanti di azione per l'utente loggato -->
	    <div class="user-actions">
	        <a href="${pageContext.request.contextPath}/ModificaProfilo" class="btn-modifica"> Modifica Profilo </a>
	        <a href="${pageContext.request.contextPath}/Ordini" class="btn-orders"> I miei Ordini </a>
	        <!--  <a href="${pageContext.request.contextPath}/#" class="btn-logout"> Logout </a>-->
	    </div>
	
	    <!-- Controlli con JSLT  -->
	   	<c:if test="${not empty sessionScope.utente and sessionScope.utente.ruolo == 'Admin'}">
            
            <section class="admin-panel">
                <h3>Pannello di Amministrazione</h3>
                
                <div class="admin-actions">
                    <a href="${pageContext.request.contextPath}/ElencoProdotti" class="btn-admin">Elenco prodotti</a>
                    <a href="${pageContext.request.contextPath}/ElencoOrdini" class="btn-admin">Elenco ordini</a>
                </div>
            </section>
            
        </c:if>
    
    </main>
    

 <jsp:include page="common/footer.jsp" />
</body>
</html>