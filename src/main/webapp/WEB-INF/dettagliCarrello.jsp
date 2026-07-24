<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Base64" %>
<%@ page import="model.prodotto.ProdottoBean" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Il tuo Carrello</title>
   	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/carrello/styleCarrello.css">
</head>
<body>

<jsp:include page="common/header.jsp" />

<!-- MAIN -->
<main class="container">

    <section class="header-utente">
        <c:choose>
            <c:when test="${not empty sessionScope.utente}">
               <!-- Corretto l'inserimento delle virgolette nella classe -->
               <div class="titolo-carrello"> 
                   <h2>Benvenuto nel tuo carrello, ${sessionScope.utente.nome}!</h2> 
                   <img src="${pageContext.request.contextPath}/resources/img/coin.gif" alt="coin" class="coin-img">
               </div>
            </c:when>
            <c:otherwise>
            	<div class="title-notlogin">
                    <h2>Stai navigando come Ospite. <a href="Login" style="color: #3498db;">Accedi</a> per salvare i tuoi acquisti!</h2>
                </div>
            </c:otherwise>
        </c:choose>
    </section>

    <c:choose>
        <c:when test="${empty sessionScope.carrello || empty sessionScope.carrello.lista}">
            <div class="carrello-vuoto">
            	<img src="${pageContext.request.contextPath}/resources/img/sonicPensa.gif" alt="Sonic sta aspettando i tuoi prodotti" class="empty-cart-img">
                <p>Il tuo carrello è attualmente vuoto.</p>
                <a href="Catalogo">Torna allo shopping</a>
            </div>
        </c:when>
        
        <c:otherwise>
            
            <div class="cart-layout">
                <section class="cart-items-section">
                    <div class="cart-header">
                        <h2>Il tuo carrello</h2>
                        <p>Non sei pronto all'acquisto? <a href="Catalogo">Continua lo shopping</a></p>
                    </div>
                    
                    <!-- Lista prodotti -->
                    <div class="items-list">
                        <c:forEach var="prodotto" items="${sessionScope.carrello.lista}">
                            
                            <!-- Prodotto -->
                            <article class="prodotto-item">
                                <img src="${pageContext.request.contextPath}/GetPicture?idProdotto=${prodotto.idProdotto}" 
                                     alt="${prodotto.titolo}" class="prodotto-img" />
                                
                                <div class="prodotto-details">
                                    <h4>${prodotto.titolo}</h4>
                                    <span class="prezzo">${prodotto.prezzoAttuale} &euro;</span>
                                </div>
                                
                                <div class="prodotto-actions">
                                    <form action="${pageContext.request.contextPath}/RimuoviDalCarrello" method="post">
                                        <input type="hidden" name="idProdotto" value="${prodotto.idProdotto}">
                                        <button type="submit" class="link-rimuovi">Rimuovi</button>
                                    </form>
                                </div>
                            </article>
                            <hr class="item-divider">
                            
                        </c:forEach>
                    </div>
                    
                    <div class="cart-footer">
                        <form action="${pageContext.request.contextPath}/PulisciCarrello" method="get">
                            <button type="submit" class="btn-pulisci">Pulisci Carrello</button>
                        </form>
                    </div>
                </section>

                <!-- Sommario Carrello -->
                <aside class="cart-summary-section">
                    <h3>Sommario Ordine</h3>
                   
                    <div class="summary-details">
                        
                        <!-- Stampa una riga per ogni prodotto nel carrello -->
                        <c:forEach var="item" items="${sessionScope.carrello.lista}">
                            <div class="summary-row" style="font-size: 0.95rem; color: #555;">
                                <!-- Nome del prodotto tagliato se troppo lungo -->
                                <span style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis; max-width: 70%;">${item.titolo}</span>
                                <span>${item.prezzoAttuale} &euro;</span>
                            </div>
                        </c:forEach>
                        <!-- FINE CICLO -->
                        
                        <hr class="summary-divider">
                        
                        <div class="summary-row totale">
                            <span>Totale</span>
                            <span>${sessionScope.carrello.totale} &euro;</span>
                        </div>   
                    </div> 
                    
                    <form action="${pageContext.request.contextPath}/Checkout" method="get">
                        <button type="submit" class="btn-checkout">Procedi al checkout</button>
                    </form>
                </aside>
                
            </div>
        </c:otherwise>
    </c:choose>

</main>

<jsp:include page="common/footer.jsp"/>

</body>
</html>