<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Test Dettagli Prodotto - ${prodotto.titolo}</title>
    <style>
        body { font-family: sans-serif; margin: 30px; line-height: 1.6; }
        .product-container { border: 1px solid #ccc; padding: 20px; max-width: 600px; border-radius: 8px; }
        .dynamic-details { background-color: #f9f9f9; padding: 15px; border-left: 4px solid #007BFF; margin-top: 15px; }
        .error { color: red; font-weight: bold; }
        .badge { background: #333; color: #fff; padding: 3px 8px; border-radius: 4px; font-size: 0.9em; }
    </style>
</head>
<body>

    <h2>Pagina di Test: Dettagli Prodotto</h2>

    <c:choose>
        <c:when test="${not empty prodotto}">
            <div class="product-container">
                <h1>${prodotto.titolo} <span class="badge">${prodotto.tipo}</span></h1>
                <p><strong>ID Prodotto:</strong> ${prodotto.idProdotto}</p>
                <p><strong>Azienda:</strong> ${prodotto.azienda}</p>
                <p><strong>Anno di Rilascio:</strong> ${prodotto.annoRilascio}</p>
                <p><strong>Stato:</strong> ${prodotto.stato}</p>
                
                <form action="${pageContext.request.contextPath}/AggiungiAlCarrello" method="post" style="margin-top: 20px;">
    
				    <input type="hidden" name="idProdotto" value="${prodotto.idProdotto}">
				    
				    <button type="submit" class="btn-aggiungi-rapido">
				        🛒 Inserisci nel Carrello
				    </button>
				</form>
                
                <c:if test="${not empty prodotto.noteDifetti}">
                    <p class="error"><strong>Note Difetti:</strong> ${prodotto.noteDifetti}</p>
                </c:if>
                
                <p><strong>Descrizione:</strong> ${prodotto.descrizione}</p>
                <h3>Prezzo: € ${prodotto.prezzoAttuale}</h3>

                <div class="dynamic-details">
                    <h3>Specifiche del Componente (${prodotto.tipo})</h3>
                    
                    <c:choose>
                        <c:when test="${prodotto.tipo == 'Gioco'}">
                            <p><strong>Sviluppatore:</strong> ${prodotto.sviluppatore}</p>
                        </c:when>
                        
                        <c:when test="${prodotto.tipo == 'Cabinato'}">
                            <p><strong>Sistema Arcade:</strong> ${prodotto.tipoSistemaArcade}</p>
                            <p><strong>Dimensioni:</strong> ${prodotto.dimensioniCm}</p>
                        </c:when>
                        
                        <c:when test="${prodotto.tipo == 'Console'}">
                            <p><strong>Modello Specifico:</strong> ${prodotto.modelloSpecifico}</p>
                        </c:when>
                        
                        <c:when test="${prodotto.tipo == 'Gadget'}">
                            <p><strong>Tipo Gadget:</strong> ${prodotto.tipoGadget}</p>
                            <p><strong>Materiale:</strong> ${prodotto.tipoMateriale}</p>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <p class="error">Nessun prodotto trovato o errore nel passaggio dei dati.</p>
        </c:otherwise>
    </c:choose>

    <br>
    <a href="Catalogo">Torna al Catalogo</a>

</body>
</html>