<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.utente.UtenteBean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>1-UPShop - Contatti</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styleContatti.css">
</head>
<body>

    <jsp:include page="common/header.jsp" />

    <main class="contacts-container">
        
        <!-- TITOLO -->
        <div class="contacts-header">
            <h2>Hey Player! Hai bisogno di aiuto?</h2>
            <h1>Prendi queste Info!</h1>
        </div>

        <!--  GIF -->
        <div class="contacts-media">
            <img src="${pageContext.request.contextPath}/resources/img/tua-gif.gif" alt="Support GIF" class="support-gif">
        </div>

        <!-- INFO -->
       	<article class="contacts-info">
            
            <!--  Negozio -->
            <div class="info-block">
                <img src="${pageContext.request.contextPath}/resources/img/icona-negozio.png" alt="Negozio" class="info-icon">
                <h3>Vieni a trovarci in negozio!</h3>
                <p>Via Shop 123, 20121 Atlantide (UMI)</p>
            </div>

            <!-- Telefono -->
            <div class="info-block center-block">
                <img src="${pageContext.request.contextPath}/resources/img/icona-telefono.png" alt="Telefono" class="info-icon">
                <h3>Chiamaci!</h3>
                <p class="strong-text">+39 123 234 45</p>
                <p>Siamo operativi per assistenza<br>dal lunedì al venerdì<br>dalle 9:00 alle 12:00</p>
            </div>

            <!--  Email -->
            <div class="info-block">
                <img src="${pageContext.request.contextPath}/resources/img/icona-email.png" alt="Email" class="info-icon">
                <h3>Scrivici!</h3>
                <p>Email: <strong>info@1upshop.com</strong></p>
            </div>

        </article>
        
    </main>

    <jsp:include page="common/footer.jsp" />

</body>
</html>