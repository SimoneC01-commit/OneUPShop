<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout Ordine</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; background-color: #f8f9fa; color: #333; }
        .checkout-container { max-width: 900px; margin: 0 auto; display: flex; gap: 30px; }
        .panel { background: white; padding: 25px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); flex: 1; }
        .summary-panel { max-width: 350px; }
        h2, h3 { margin-top: 0; color: #2c3e50; border-bottom: 2px solid #ecf0f1; padding-bottom: 10px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; font-size: 0.9em; }
        input[type="text"] { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        input[type="text"]:focus { border-color: #3498db; outline: none; }
        .error-message { background-color: #f8d7da; color: #721c24; padding: 12px; border-radius: 4px; margin-bottom: 20px; font-weight: bold; border: 1px solid #f5c6cb; }
        .prodotto-item { display: flex; justify-content: space-between; padding: 10px 0; border-bottom: 1px dashed #eee; font-size: 0.95em; }
        .totale-box { font-size: 1.3em; font-weight: bold; color: #27ae60; text-align: right; margin-top: 20px; }
        .btn-paga { background-color: #27ae60; color: white; border: none; padding: 12px; width: 100%; font-size: 1.1em; font-weight: bold; border-radius: 4px; cursor: pointer; margin-top: 20px; transition: background 0.2s; }
        .btn-paga:hover { background-color: #219653; }
    </style>
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

            <form action="${pageContext.request.contextPath}/Checkout" method="post">
                
                <div class="form-group">
                    <label for="via">Via / Piazza e Numero Civico</label>
                    <input type="text" id="via" name="via" value="${param.via}" required placeholder="Es. Via Roma, 15">
                </div>

                <div style="display: flex; gap: 15px;">
                    <div class="form-group" style="flex: 1;">
                        <label for="cap">CAP</label>
                        <input type="text" id="cap" name="cap" value="${param.cap}" required placeholder="Es. 84100" maxlength="5">
                    </div>
                    
                    <div class="form-group" style="flex: 2;">
                        <label for="citta">Città</label>
                        <input type="text" id="citta" name="citta" value="${param.citta}" required placeholder="Es. Salerno">
                    </div>
                </div>
                <div class="form-group">
				    <label for="telefono">Numero di Telefono</label>
				    <input type="text" id="telefono" name="telefono" value="${param.telefono}" required placeholder="Es. 3331234567">
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
                        <strong>${prodotto.prezzo} &euro;</strong>
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