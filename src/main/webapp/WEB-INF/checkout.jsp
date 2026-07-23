<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout Ordine</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/checkout/styleCheckout.css">
    <script src="${pageContext.request.contextPath}/resources/checkout/scriptCheckout.js"></script>
</head>
<body>

    <div class="checkout-container">
        
        <div class="panel">
            <h2>Indirizzo di Spedizione</h2>
            
            <c:if test="${not empty requestScope.errorMessage}">
                <div class="error-message">
                    ${requestScope.errorMessage}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/Checkout" method="post" 
            	onsubmit="event.preventDefault(); validate(this)" data-context-path="${pageContext.request.contextPath}">
                
                <div class="form-group">
                    <label for="via">Via / Piazza e Numero Civico</label>
                    <input type="text" id="via" name="via" value="${param.via}" required max="100" 
                		oninput="checkVia(this)" placeholder="es. Via Esempio, 10/A">
                </div>

                <div style="display: flex; gap: 15px;">
                    <div class="form-group" style="flex: 1;">
                        <label for="cap">CAP</label>
                        <input type="text" id="cap" name="cap" value="${param.cap}" required max="5" 
                			oninput="checkCAP(this)" placeholder="es. 84100" maxlength="5">
                    </div>
                    
                    <div class="form-group" style="flex: 2;">
                        <label for="citta">Città</label>
                        <input type="text" id="citta" name="citta" value="${param.citta}" required min="2" max="50" 
                			oninput="checkCitta(this)" placeholder="Es. Salerno">
                    </div>
                </div>
                <div class="form-group">
				    <label for="telefono">Numero di Telefono</label>
				    <input type="text" id="telefono" name="telefono" value="${param.telefono}" required min="14" max="16" 
                			oninput="checkTel(this)" placeholder="Es. +39 123 456 7890 OR +39 1234567890">
				</div>
				
				<div class="form-group">
				    <label for="metodoPagamento">Metodo di Pagamento</label>
				    <select id="metodoPagamento" name="metodoPagamento" required style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px;">
				        <option value="" disabled selected>Seleziona un metodo</option>
				        <option value="Pagamento Alla Consegna">Pagamento Alla Consegna</option>
				    </select>
				</div>

                <button type="submit" class="btn-paga">Conferma e Invia Ordine</button>
            </form>
        </div>

        <div class="panel summary-panel">
            <h3>Riepilogo Ordine</h3>
            
            <div class="prodotti-list">
                <c:forEach var="prodotto" items="${sessionScope.carrello.lista}">
                    <div class="prodotto-item">
                        <span>${prodotto.titolo}</span>
                        <strong>${prodotto.prezzoAttuale} &euro;</strong>
                    </div>
                </c:forEach>
            </div>

            <div class="totale-box">
                Totale: ${sessionScope.carrello.totale} &euro;
            </div>
        </div>

    </div>

</body>
</html>