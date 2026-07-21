<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Base64" %>
<%@ page import="model.prodotto.ProdottoBean" %>

<%
    // Conversione dell'immagine da byte[] a Base64 per la visualizzazione inline
    ProdottoBean p = (ProdottoBean) request.getAttribute("prodotto");
    String base64Image = "";
    if(p != null && p.getFotoBlob() != null) {
        base64Image = Base64.getEncoder().encodeToString(p.getFotoBlob());
    }
    request.setAttribute("base64Image", base64Image);
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>1-Up Shop - Prodotto</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/home/style.css">
</head>

<jsp:include page="common/header.jsp" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/dettagliProdotto/styleDettagliProdotto.css">

<main class="product-page">
    
    <!-- SEZIONE SUPERIORE: Dettagli Prodotto -->
    <section class="product-top">
        
     
        <!-- Sinistra: Immagine Singola -->
        <div class="product-gallery">
            <div class="main-image-container">
                <c:choose>
                    <c:when test="${not empty base64Image}">
                        <img src="data:image/jpeg;base64,${base64Image}" id="mainImage" class="main-img" alt="${prodotto.titolo}">
                    </c:when>
                    <c:otherwise>
                        <img src="${pageContext.request.contextPath}/resources/img/placeholder.jpg" id="mainImage" class="main-img" alt="Nessuna immagine">
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        
        <!-- Destra: Informazioni e Acquisto -->
        <div class="product-info">
            <h1 class="product-title">${prodotto.titolo}</h1>
            
            <!-- Metadata: Azienda e Anno di Rilascio sotto il titolo -->
            <p class="product-meta">
                <span class="meta-brand">${prodotto.azienda}</span> • <span class="meta-year">${prodotto.annoRilascio}</span>
            </p>

            <p class="product-price">€ ${prodotto.prezzoAttuale}</p>
            
            <!-- Badge Condizioni del prodotto (Es: Usato, Nuovo) -->
            <div class="product-condition">
                <span class="condition-badge">${prodotto.stato}</span>
                <c:if test="${not empty prodotto.noteDifetti}">
                    <p class="condition-notes"><strong>Note:</strong> ${prodotto.noteDifetti}</p>
                </c:if>
            </div>

            <p class="product-desc">${prodotto.descrizione}</p>

            <!-- Form Acquisto -->
            <form action="${pageContext.request.contextPath}/AggiungiCarrello" method="POST" class="purchase-form">
                <input type="hidden" name="idProdotto" value="${prodotto.idProdotto}">
                
                <div class="quantity-selector">
                    <label>Quantità</label>
                    <div class="qty-controls">
                        <button type="button" class="qty-btn" onclick="modificaQuantita(-1)">−</button>
                        <input type="number" id="qtyInput" name="quantita" value="1" min="1" readonly>
                        <button type="button" class="qty-btn" onclick="modificaQuantita(1)">+</button>
                    </div>
                </div>

                <div class="action-buttons">
                    <c:choose>
                        <c:when test="${prodotto.disponibile}">
                            <button type="submit" class="btn-cart">Aggiungi al carrello</button>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="btn-cart disabled" disabled>Esaurito</button>
                        </c:otherwise>
                    </c:choose>
                    <button type="button" class="btn-wishlist">Aggiungi alla Wishlist</button>
                </div>
            </form>
        </div>
    </section>

    <!-- Linea di separazione -->
    <hr class="section-divider">

    <!-- SEZIONE INFERIORE: Prodotti Consigliati -->
 <section class="product-section">
            <h2>Prodotti Consigliati</h2>
            <div class="product-grid">
                <c:forEach var="prodotto" items="${consigliati}">
                    <article class="product-card">
                        
                        <div class="img-placeholder">
                            </div>
                        
                        <div class="card-details">
                            <span class="category">${prodotto.tipo}</span>
	                           <h3 class="title"><a href="${pageContext.request.contextPath}/DettagliProdotto?idProdotto=${prodotto.idProdotto}">
                            	${prodotto.titolo} </a></h3>
                            <p class="price"><b>€ ${prodotto.prezzoAttuale} EUR </b></p> 
                            
                            <form action="${pageContext.request.contextPath}/AggiungiAlCarrello" method="POST">
                                <input type="hidden" name="idProdotto" value="${prodotto.idProdotto}">
                                <button type="submit" class="bottone">Aggiungi al carrello</button>
                            </form>
                        </div>
                    </article>
                </c:forEach>
            </div>
        </section>
</main>

<jsp:include page="common/footer.jsp" />
<script src="${pageContext.request.contextPath}/resources/js/dettaglio.js"></script>