<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.wishlist.WishlistBean" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>La tua Wishlist</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/wishlist/styleWishlist.css">
</head>
<body>

<jsp:include page="common/header.jsp" />

<!-- MAIN -->
<main class="container">
    
    <section class="wishlist-header">
        <h1>La tua Wishlist</h1>
        <img src="${pageContext.request.contextPath}/resources/img/Heart.gif" alt="cuoricino" class="heart-gif">
    </section>

    <!-- Errori page -->
    <c:if test="${not empty errorMessage}">
        <div class="error-message">
            <p>${errorMessage}</p>
        </div>
    </c:if>

   <section class="wishlist-content">
        <c:choose>
            
            <c:when test="${empty wishlist}">
                <!-- Caso Wishlist Vuota -->
                <div class="wishlist-vuota">
                    <p>La tua wishlist è attualmente vuota.</p>
                    <a href="${pageContext.request.contextPath}/Catalogo">Scopri i nostri prodotti</a>
                </div>
            </c:when>
            
            <c:otherwise>
                <!-- Caso Wishlist Piena -->
                <div class="wishlist-items">
                    
                    <c:forEach var="item" items="${wishlist}">
                        <article class="wishlist-item">
                            
                            <!-- 1. Immagine (Sinistra) -->
                            <div class="item-image">
                                <img src="${pageContext.request.contextPath}/GetPicture?idProdotto=${item.prodotto.idProdotto}" 
                                     alt="${item.prodotto.titolo}">
                            </div>
                            
                            <!-- 2. Dettagli (Centro) -->
                            <div class="item-details">
                                <h3>${item.prodotto.titolo}</h3>
                                <p class="azienda-text">${item.prodotto.azienda}</p>
                                <span class="prezzo-text">${item.prodotto.prezzoAttuale} &euro;</span>
                            </div>
                            
                            <!-- 3. Azioni (Destra) -->
                            <div class="item-actions">
                                <!-- Bottone Aggiungi al carrello -->
                                <button type="button" class="btn-add-cart" onclick="aggiungiAlCarrello(this)" data-id-prodotto="${prodotto.idProdotto}" data-context-path="${pageContext.request.contextPath}">Aggiungi al carrello</button>
                                
                                <!-- Bottone Rimuovi --> 
                            	  <form action="${pageContext.request.contextPath}/RimuoviDallaWishlist" method="post">
                                        <input type="hidden" name="idProdotto" value="${item.prodotto.idProdotto}">
                                        <button type="submit" class="link-rimuovi">Rimuovi</button>
                                    </form>
                            </div>
                            
                        </article>
                        
                        <hr class="item-divider">
                    </c:forEach>
                    
                </div>

                <div class="wishlist-footer">
                    <form action="${pageContext.request.contextPath}/SvuotaWishlist" method="get">
                            <button type="submit" class="btn-svuota">Svuota Wishlist</button>
                        </form>
                </div>
            </c:otherwise>
            
        </c:choose>
    </section>

</main>

<jsp:include page="common/footer.jsp" />

</body>
</html>