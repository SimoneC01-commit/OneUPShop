<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>1-Up Shop - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/home/style.css">
    <script src="${pageContext.request.contextPath}/resources/generalScript.js"></script>
</head>
<body>

  <jsp:include page="common/header.jsp" />

   
        <section class="banner"> 
	        <div class="banner-container"> <img src="${pageContext.request.contextPath}/resources/img/banner2.png" alt="Banner" class="banner-img"> 
	        </div>
        </section>
        
 <main class="container">
        <section class="product-section">
            <h2>Nuovi Prodotti</h2>
            <div class="product-grid">
                <c:forEach var="prodotto" items="${prodottiNuovi}">
                    <article class="product-card">
                        
                        <div class="img-placeholder">
                        	<img class = "img-placeholder" src="${pageContext.request.contextPath}/GetPicture?idProdotto=${prodotto.idProdotto}" alt="${prodotto.titolo}">
                        </div>
                        
                        <div class="card-details">
                            <span class="category">${prodotto.tipo}</span>
                            <h3 class="title"><a href="${pageContext.request.contextPath}/DettagliProdotto?idProdotto=${prodotto.idProdotto}">
                            	${prodotto.titolo} </a></h3>
                            <p class="price"><b>€ ${prodotto.prezzoAttuale} EUR </b></p> 
                            
						    <button type="button" class="bottone" onclick="aggiungiAlCarrello(this)" data-id-prodotto="${prodotto.idProdotto}" data-context-path="${pageContext.request.contextPath}">Aggiungi al carrello</button>
                        </div>
                    </article>
                </c:forEach>
            </div>
        </section>

        <section class="product-section">
            <h2>Prodotti Consigliati</h2>
            <div class="product-grid">
                <c:forEach var="prodotto" items="${prodottiConsigliati}">
                    <article class="product-card">
                        
                        <div class="img-placeholder">
                        <img class="img-placeholder" src="${pageContext.request.contextPath}/GetPicture?idProdotto=${prodotto.idProdotto}" alt="${prodotto.titolo}">
                            </div>
                        
                        <div class="card-details">
                            <span class="category">${prodotto.tipo}</span>
	                           <h3 class="title"><a href="${pageContext.request.contextPath}/DettagliProdotto?idProdotto=${prodotto.idProdotto}">
                            	${prodotto.titolo} </a></h3>
                            <p class="price"><b>€ ${prodotto.prezzoAttuale} EUR </b></p> 
                            
							<button type="button" class="bottone" onclick="aggiungiAlCarrello(this)" data-id-prodotto="${prodotto.idProdotto}" data-context-path="${pageContext.request.contextPath}">Aggiungi al carrello</button>
                       
                       	</div>
                    </article>
                </c:forEach>
            </div>
        </section>
    </main>

    <jsp:include page="common/footer.jsp" />
</body>
</html>