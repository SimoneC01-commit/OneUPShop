<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Risultati ricerca per "${param.q}" - RetroGaming Shop</title>
    <script src="${pageContext.request.contextPath}/resources/generalScript.js"></script>
    <script src="${pageContext.request.contextPath}/resources/catalogo/scriptCatalogo.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/catalogo/styleCatalogo.css">
</head>
<body>

<jsp:include page="common/header.jsp" />

    <h1>Risultati per: "${param.q}"</h1>

    <!-- Impostato grid ad una singola colonna per occupare tutto lo spazio libero -->
    <main class="container ricerca-prodotto">
    
        <!-- AREA RISULTATI -->
        <article class="catalog">
            
            <c:if test="${not empty errorMessage}">
                <div class="error-msg">${errorMessage}</div>
            </c:if>

            <c:choose>
                <c:when test="${not empty risultati}">
                    
                    <div class="stats">
                        Prodotti trovati: <strong>${risultati.size()}</strong>
                    </div>

                    <!-- GRIGLIA PRODOTTI -->
                    <div class="product-grid">
                        <c:forEach var="p" items="${risultati}">
                            <div class="product-card">

                                <img src="${pageContext.request.contextPath}/GetPicture?idProdotto=${p.idProdotto}" class="img-placeholder" alt="${p.titolo}">
                              
                                <div class="card-details">
                                  
                                    <span class="category">${p.tipo}</span>
                                    <h3 class="title">
                                        <a href="DettagliProdotto?idProdotto=${p.idProdotto}">${p.titolo}</a>
                                    </h3>
                                    
                                    <p class="price">€ ${p.prezzoAttuale} EUR</p>
                                    
                                    <button type="button" class="bottone" onclick="aggiungiAlCarrello(this)" data-id-prodotto="${p.idProdotto}" data-context-path="${pageContext.request.contextPath}">
                                        Aggiungi al carrello
                                    </button>
                                </div>
                                
                            </div>
                        </c:forEach>
                    </div>

                </c:when>
                <c:otherwise>
                    <p class="p-no-risultati">Nessun risultato trovato per la ricerca "<strong>${param.q}</strong>".</p>
                </c:otherwise>
            </c:choose>
            
        </article>
    </main>

<jsp:include page="common/footer.jsp" />

</body>
</html>