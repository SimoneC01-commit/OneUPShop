<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout Ordine</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/checkout/styleCheckout.css">
    <script src="${pageContext.request.contextPath}/resources/checkout/scriptCheckout.js"></script>
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/favicon.ico">
</head>

<body>

   
<jsp:include page="common/header.jsp" />
       
        <main class="checkout-container">
        
        <div class="titolo-checkout"> 
                   <h2>Checkout</h2> 
                   <img src="${pageContext.request.contextPath}/resources/img/money.gif" alt="coin" class="money-img">
               </div>
               
        <div class="checkout-content"> 
        <!-- SINISTRA: Modulo Indirizzo -->
        <section class="checkout-form-section">
            <h2>Indirizzo di Spedizione</h2>
            
            <!--  errori -->
            <c:if test="${not empty requestScope.errorMessage}">
                <div class="error-message">
                    ${requestScope.errorMessage}
                </div>
            </c:if>

            <!--  POST da usare per il checkout -->
            <form action="${pageContext.request.contextPath}/Checkout" method="post"
            	onsubmit="event.preventDefault(); validate(this)" data-context-path="${pageContext.request.contextPath}">  <!-- data-context-path:  -->
                <!-- INDIRIZZO -->
                <div class="form-group">
                    <label for="via">Via / Piazza e Numero Civico</label>
                    <input type="text" id="via" name="via" value="${param.via}" required maxlength="100" 
                		oninput="checkVia(this)" placeholder="es. Via Esempio, 10/A">
                </div>

                <!-- CAP -->
                <div class="form-row">
                    <div class="form-group cap-group">
                        <label for="cap">CAP</label>
                        <input type="text" id="cap" name="cap" value="${param.cap}" required max="5" 
                			oninput="checkCAP(this)" placeholder="es. 84100">
                    </div>
                    <!-- CITTà -->
                    <div class="form-group citta-group">
                        <label for="citta">Città</label>
                        <input type="text" id="citta" name="citta" value="${param.citta}" required min="2" max="50" 
                			oninput="checkCitta(this)" placeholder="Es. Salerno">
                    </div>
                </div>
                <!-- NUMERO DI TELEFONO -->
                <div class="form-group">
				    <label for="telefono">Numero di Telefono</label>
				    <input type="text" id="telefono" name="telefono" value="${param.telefono}" required min="14" max="16" 
                			oninput="checkTel(this)" placeholder="Es. +39 123 456 7890 OR +39 1234567890">
				</div>
				<!-- METODO DI PAGAMENTO -->
				<div class="form-group">
				    <label for="metodoPagamento">Metodo di Pagamento</label>
				    <select id="metodoPagamento" name="metodoPagamento" required class="select-pagamento">
				        <option value="" disabled selected>Seleziona un metodo</option>
				        <option value="Pagamento Alla Consegna">Pagamento Alla Consegna</option>
				    </select>
				</div>

                <!-- Tasto di conferma che aziona il metodo POST -->
                <button type="submit" class="btn-paga">Conferma e Invia Ordine</button>
            </form>
        </section>

        <!-- DESTRA: Riepilogo Ordine -->
        <aside class="checkout-summary-section">
            <h3>Riepilogo Ordine</h3>
            
            <div class="prodotti-list">
                <c:forEach var="prodotto" items="${sessionScope.carrello.lista}">
                    <div class="summary-row">
                  
                        <span class="prodotto-titolo">${prodotto.titolo}</span>
                        <strong>${prodotto.prezzoAttuale} &euro;</strong>
                    </div>
                </c:forEach>
            </div>
            
            <hr class="summary-divider">

            <div class="summary-row totale">
                <span>Totale:</span>
                <span>${sessionScope.carrello.totale} &euro;</span>
            </div>
        </aside>
        
		</div>
		
    </main>
    
    <jsp:include page="common/footer.jsp" />

</body>
</html>