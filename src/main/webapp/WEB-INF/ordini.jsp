<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.utente.UtenteBean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> <!-- Usata per alcune istruzioni ftm (formattazione) -->

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>I miei Ordini</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/utenteOrdini/styleOrdiniUtente.css">
</head>
<body>

<jsp:include page="common/header.jsp" />

<main class="container">
    <h1>I Miei Ordini</h1>
    
      <!-- TABELLA  -->
        <!-- Controllo se esistono ordini -->
    <c:choose>
        <c:when test="${empty ordini}">
            <div class="no-orders">
                <h2>Non hai ancora effettuato nessun ordine :( </h2>
                <p>Esplora il nostro catalogo per trovare fantastici prodotti arcade!</p>
                <a href="${pageContext.request.contextPath}/Home" class="btn-home">Torna allo Shop</a>
            </div>
        </c:when>
        <c:otherwise>
             <!-- Se esistono mostro tabella  -->
            <table>
                <thead>
                    <tr>
                        <th>ID Ordine</th>
                        <th>Data e Ora</th>
                        <th>Stato</th>
                        <th>Totale</th>
                        <th>Dettagli Spedizione</th>
                        <th>Pagamento</th>
                    </tr>
                </thead>
                <tbody>
                   <!-- Ciclo sull'ArrayList di OrdineBean  -->
                    <c:forEach var="ordine" items="${ordini}">
					    <!-- Generiamo l'URL per ogni riga -->
					    <c:url var="dettagliUrl" value="/DettagliOrdine">
					        <c:param name="idOrdine" value="${ordine.idOrdine}" />
					    </c:url>
					    
					    <!-- L'attributo onclick reindirizza alla servlet  -->
					    <tr onclick="window.location='${dettagliUrl}';" style="cursor: pointer;">
					        <td><strong>#${ordine.idOrdine}</strong></td>
					        <td>
					            <fmt:formatDate value="${ordine.dataOrdine}" pattern="dd/MM/yyyy HH:mm" />   <!-- Fomatta la data -->
					        </td>
					        <td>
					            <c:choose>
					                <c:when test="${ordine.statoOrdine == 'In elaborazione'}">
					                    <span class="badge badge-elaborazione">In elaborazione</span>
					                </c:when>
					                <c:when test="${ordine.statoOrdine == 'Spedito'}">
					                    <span class="badge badge-spedito">Spedito</span>
					                </c:when>
					                <c:when test="${ordine.statoOrdine == 'Consegnato'}">
					                    <span class="badge badge-consegnato">Consegnato</span>
					                </c:when>
					                <c:otherwise>
					                    <span class="badge badge-annullato">${ordine.statoOrdine}</span>
					                </c:otherwise>
					            </c:choose>
					        </td> 
					        <td>
					            <fmt:formatNumber value="${ordine.totaleOrdine}" type="currency" currencySymbol="€"/> <!-- Fomatta il prezzo -->
					        </td>
					        <td>
					            <div class="Dettagli-storici">
					                <strong>Indirizzo:</strong> ${ordine.indirizzoSpedizione}<br>
					                <strong>Tel:</strong> ${ordine.telefono}
					            </div>
					        </td>
					        <td>${ordine.metodoPagamento}</td>
					    </tr>
					</c:forEach>
                </tbody>
            </table>
            
            <div class= "btn-back-home">
                <a href="${pageContext.request.contextPath}/Home" class="btn-home">Continua lo Shopping</a>
            </div>
        </c:otherwise>
    </c:choose>
</main>

<jsp:include page="common/footer.jsp" />

</body>
</html>