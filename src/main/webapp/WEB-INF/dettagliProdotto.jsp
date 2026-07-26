<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Base64" %>
<%@ page import="model.prodotto.ProdottoBean" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>1-Up Shop - Prodotto</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/home/style.css">
    <script src="${pageContext.request.contextPath}/resources/generalScript.js"></script>
    <script src="${pageContext.request.contextPath}/resources/dettagliProdotto/scriptDettagliProdotto.js"></script>
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/favicon.ico">
</head>

<jsp:include page="common/header.jsp" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/dettagliProdotto/styleDettagliProdotto.css">

<main class="product-page">
    
    <!-- SEZIONE SUPERIORE: Dettagli Prodotto -->
    <section class="product-top">
        
     
        <!-- Sinistra: Immagine Singola -->
        <div class="product-gallery">
            <div class="main-image-container">
				<img src="${pageContext.request.contextPath}/GetPicture?idProdotto=${prodotto.idProdotto}" id="mainImage" class="main-img" alt="${prodotto.titolo}">
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
			<div class="product-desc">
            <p class="desc">${prodotto.descrizione}</p>
			</div>
			
            <!-- Form Acquisto -->
            <form action="${pageContext.request.contextPath}/AggiungiCarrello" method="POST" class="purchase-form">
                <input type="hidden" name="idProdotto" value="${prodotto.idProdotto}">
                

                <div class="action-buttons">
                    <c:choose>
                        <c:when test="${prodotto.disponibile}">
                            <button type="button" class="btn-cart" onclick="aggiungiAlCarrello(this)" 
                            	data-id-prodotto="${prodotto.idProdotto}" data-context-path="${pageContext.request.contextPath}">Aggiungi al carrello</button>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="btn-cart disabled" disabled>Esaurito</button>
                        </c:otherwise>
                    </c:choose>  <!--  MOMENTANEO DA CAMBIARE -->
                    	 <button type="button" class="btn-wishlist" onclick="aggiungiAllaWishlist(this)" 
                    	 	data-id-prodotto="${prodotto.idProdotto}" data-context-path="${pageContext.request.contextPath}">Aggiungi alla Wishlist </button>
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
                        	<img class="img-placeholder" src="${pageContext.request.contextPath}/GetPicture?idProdotto=${prodotto.idProdotto}" alt="${prodotto.titolo}">
						</div>
                        
                        <div class="card-details">
                            <span class="category">${prodotto.tipo}</span>
	                           <h3 class="title"><a href="${pageContext.request.contextPath}/DettagliProdotto?idProdotto=${prodotto.idProdotto}">
                            	${prodotto.titolo} </a></h3>
                            <p class="price"><b>€ ${prodotto.prezzoAttuale} EUR </b></p> 
                            
							<button type="button" class="bottone" onclick="aggiungiAlCarrello(this)" 
								data-id-prodotto="${prodotto.idProdotto}" data-context-path="${pageContext.request.contextPath}">Aggiungi al carrello</button>
                        </div>
                    </article>
                </c:forEach>
            </div>
        </section>
</main>

<jsp:include page="common/footer.jsp" />