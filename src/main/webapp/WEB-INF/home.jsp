<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>OneUp Shop - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/home/style.css">
</head>
<body>

  <jsp:include page="common/header.jsp" />

   
        <section class="hero-banner">
        	<div class="banner-overlay">
            	<div class="banner-content">
                	<h1>Presentazione Sito Retrogaming</h1>
                	</div>
                </div>
        </section>
        
 <main class="container">
        <section class="product-section">
            <h2>New Products</h2>
            <div class="product-grid">
                <c:forEach var="prodotto" items="${prodottiHomeNuovi}">
                    <article class="product-card">
                        
                        <div class="img-placeholder">
                            </div>
                        
                        <div class="card-details">
                            <span class="category">${prodotto.tipo}</span>
                            <h3 class="title"><a href="${pageContext.request.contextPath}/DettagliProdotto?idProdotto=${prodotto.id}">
                            	${prodotto.titolo} </a></h3>
                            <p class="price"><b>€ ${prodotto.prezzo} EUR </b></p> 
                            
                            <form action="${pageContext.request.contextPath}/AggiungiAlCarrello" method="POST">
                                <input type="hidden" name="idProdotto" value="${prodotto.id}">
                                <button type="submit" class="bottone">Aggiungi al carrello</button>
                            </form>
                        </div>
                    </article>
                </c:forEach>
            </div>
        </section>

        <section class="product-section">
            <h2>Recommended Products</h2>
            <div class="product-grid">
                <c:forEach var="prodotto" items="${prodottiHomeConsigliati}">
                    <article class="product-card">
                        
                        <div class="img-placeholder">
                            </div>
                        
                        <div class="card-details">
                            <span class="category">${prodotto.tipo}</span>
	                           <h3 class="title"><a href="${pageContext.request.contextPath}/DettagliProdotto?idProdotto=${prodotto.id}">
                            	${prodotto.titolo} </a></h3>
                            <p class="price"><b>€ ${prodotto.prezzo} EUR </b></p> 
                            
                            <form action="${pageContext.request.contextPath}/AggiungiAlCarrello" method="POST">
                                <input type="hidden" name="idProdotto" value="${prodotto.id}">
                                <button type="submit" class="bottone">Aggiungi al carrello</button>
                            </form>
                        </div>
                    </article>
                </c:forEach>
            </div>
        </section>
    </main>

   <%-- <jsp:include page="footer.jsp" /> --%>

</body>
</html>