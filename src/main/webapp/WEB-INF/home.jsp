<%@page import="controller.servlet.Home"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>1UpShop</title>
</head>
<body>

    <section class="hero-banner">
        <div class="banner-placeholder">
            <h1>Presentazione Sito Retrogaming</h1>
        </div>
    </section>

    <section class="product-section">
        <h2>Nuovi Prodotti</h2>
        
        <div class="scrolling-wrapper">
            
            <c:forEach items="${prodottiHomeNuovi}" var="prodotto">
                
                <div class="product-card">
                    <div class="image-box"> [Immagine del Gioco] </div>
                    
                    <p class="category">${prodotto.tipo}</p>
                    <h3 class="title">${prodotto.titolo}</h3>
                    <p class="price">${prodotto.prezzo} &euro;</p>
                </div>
                
            </c:forEach>
            
            <c:if test="${empty prodottiHomeNuovi}">
                <p>Nessun nuovo prodotto trovato.</p>
            </c:if>

        </div>
    </section>

    <section class="product-section">
        <h2>Prodotti Consigliati</h2>
        
        <div class="scrolling-wrapper">
            
            <c:forEach items="${prodottiHomeConsigliati}" var="prodotto">
                
                <div class="product-card">
                    <div class="image-box"> [Immagine del Gioco] </div>
                    <p class="category">${prodotto.tipo}</p>
                    <h3 class="title">${prodotto.titolo}</h3>
                    <p class="price">${prodotto.prezzo} &euro;</p>
                </div>
                
            </c:forEach>
            
            <c:if test="${empty prodottiHomeConsigliati}">
                <p>Nessun prodotto consigliato trovato.</p>
            </c:if>

        </div>
    </section>

</body>
</html>