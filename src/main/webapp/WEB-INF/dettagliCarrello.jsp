<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Base64" %>
<%@ page import="model.carrello.ProdottoCarrelloBean" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Il tuo Carrello</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; background-color: #f4f4f4; }
        .container { max-width: 800px; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .header-utente { background-color: #2c3e50; color: white; padding: 15px; border-radius: 5px; margin-bottom: 20px; }
        .prodotto-item { display: flex; align-items: center; justify-content: space-between; border-bottom: 1px solid #ddd; padding: 15px 0; }
        .prodotto-info { display: flex; align-items: center; gap: 20px; }
        .prodotto-img { width: 80px; height: 80px; object-fit: cover; border-radius: 4px; border: 1px solid #ccc; }
        .btn-rimuovi { background-color: #e74c3c; color: white; border: none; padding: 8px 12px; border-radius: 4px; cursor: pointer; }
        .btn-rimuovi:hover { background-color: #c0392b; }
        .totale-box { text-align: right; margin-top: 20px; font-size: 1.2em; font-weight: bold; }
        .carrello-vuoto { text-align: center; color: #7f8c8d; padding: 40px 0; }
        .btn-checkout { background-color: #2ecc71; color: white; border: none; padding: 12px 20px; font-size: 1em; border-radius: 4px; cursor: pointer; width: 100%; margin-top: 20px; }
    </style>
</head>
<body>

<div class="container">

    <div class="header-utente">
        <c:choose>
            <c:when test="${not empty sessionScope.utente}">
                <h2>Benvenuto nel tuo carrello, ${sessionScope.utente.nome}!</h2>
            </c:when>
            <c:otherwise>
                <h2>Stai navigando come Ospite. <a href="Login" style="color: #3498db;">Accedi</a> per salvare i tuoi acquisti!</h2>
            </c:otherwise>
        </c:choose>
    </div>

    <c:choose>
        <c:when test="${empty sessionScope.carrello || empty sessionScope.carrello.lista}">
            <div class="carrello-vuoto">
                <p>Il tuo carrello è attualmente vuoto.</p>
                <a href="Catalogo">Torna allo shopping</a>
            </div>
        </c:when>
        
        <c:otherwise>
            <c:forEach var="prodotto" items="${sessionScope.carrello.lista}">
                <div class="prodotto-item">
                    
                    <div class="prodotto-info">
                        <% 
                            ProdottoCarrelloBean prod = (ProdottoCarrelloBean) pageContext.getAttribute("prodotto");
                            String base64Image = "";
                            if (prod != null && prod.getFoto() != null) {
                                base64Image = Base64.getEncoder().encodeToString(prod.getFoto());
                            }
                            pageContext.setAttribute("base64Image", base64Image);
                        %>
                        <img src="data:image/jpeg;base64,${base64Image}" alt="${prodotto.titolo}" class="prodotto-img" />
                        
                        <div>
                            <h4 style="margin: 0 0 5px 0;">${prodotto.titolo}</h4>
                            <span style="color: #27ae60; font-weight: bold;">${prodotto.prezzo} &euro;</span>
                        </div>
                    </div>
                    
                    <form action="${pageContext.request.contextPath}/RimuoviDalCarrello" method="post">
                        <input type="hidden" name="idProdotto" value="${prodotto.id}">
                        <button type="submit" class="btn-rimuovi">Rimuovi</button>
                    </form>
                    
                </div>
            </c:forEach>

            <div class="totale-box">
                Totale Ordine: ${sessionScope.carrello.totale} &euro;
            </div>
            
            <form action="${pageContext.request.contextPath}/Checkout" method="post">
                <button type="submit" class="btn-checkout">Procedi all'Acquisto</button>
            </form>
            
            <form action="${pageContext.request.contextPath}/PulisciCarrello" method="post">
                <button type="submit" class="btn-rimuovi">Pulisci Carrello</button>
            </form>
            
        </c:otherwise>
    </c:choose>

</div>

</body>
</html>