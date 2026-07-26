<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="it">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="UTF-8">
    <title>Ordine #${ordine.idOrdine} - OneUpShop</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/dettagliOrdine/styleDettagliOrdine.css">
    <script src="${pageContext.request.contextPath}/resources/dettagliOrdine/scriptDettagliOrdine.js" defer></script>
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/favicon.ico">
</head>
<body>
<jsp:include page="common/header.jsp" />

<div class="container">

    <div class="header-fattura">
        <div class="dati-azienda">
            <h2>OneUpShop S.r.l.</h2>
            <p>Via Shop 123, 20121 Atlantide (UMI)<br>
            P.IVA / C.F.: +39 123 456 7890<br>
            Email: info@1upshop.com</p>
        </div>
        <div class="dati-ricevuta">
            <h1>ORDINE N° #${ordine.idOrdine}</h1>
            <p><strong>Data:</strong> <fmt:formatDate value="${ordine.dataOrdine}" pattern="dd/MM/yyyy HH:mm" /></p>
        </div>
    </div>

    <hr class="separatore">

    <div class="info-box">
        <div class="info-cliente">
            <h3>Dati Cliente &amp; Spedizione</h3>
            <p><strong>Email Account:</strong> ${ordine.emailUtente}</p>
            <p><strong>Indirizzo:</strong> ${ordine.indirizzoSpedizione}</p>
            <p><strong>Telefono:</strong> ${ordine.telefono}</p>
        </div>
        <div class="info-pagamento">
            <h3>Dettagli Ordine</h3>
            <p><strong>Metodo di Pagamento:</strong> ${ordine.metodoPagamento}</p>
            <p class="hide-on-print">
                <strong>Stato Ordine:</strong> 
                <c:choose>
                    <c:when test="${ordine.statoOrdine == 'In elaborazione'}">
                        <span class="badge badge-elaborazione">In elaborazione</span>
                    </c:when>
                    <c:when test="${ordine.statoOrdine == 'Spedito'}">
                        <span class="badge badge-spedito">Spedito</span>
                    </c:when>
                    <c:when test="${ordine.statoOrdine == 'Consegnato'}">
                        <span class="badge badge-consegnato">Consegnato</span>
                    </c:when>
                    <c:otherwise>
                        <span class="badge badge-annullato">${ordine.statoOrdine}</span>
                    </c:otherwise>
                </c:choose>
            </p>
        </div>
    </div>

    <h2>Prodotti in Fattura</h2>
    <table class="tabella-prodotti">
        <thead>
            <tr>
                <th class="hide-on-print">Anteprima</th>
                <th>Prodotto</th>
                <th>Tipo</th>
                <th>IVA</th>
                <th>Prezzo (Storico)</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="dettaglio" items="${dettagliOrdine}">
                <c:url var="prodottoUrl" value="/DettagliProdotto">
                    <c:param name="idProdotto" value="${dettaglio.prodotto.idProdotto}" />
                </c:url>
                
                <tr onclick="window.location='${prodottoUrl}';" class="riga-prodotto">
                    <td class="hide-on-print">
                        <img src="${pageContext.request.contextPath}/GetPicture?idProdotto=${dettaglio.prodotto.idProdotto}" 
                             alt="${dettaglio.prodotto.titolo}" class="img-prodotto">
                    </td>
                    <td><strong>${dettaglio.prodotto.titolo}</strong></td>
                    <td>${dettaglio.prodotto.tipo}</td>
                    <td>${dettaglio.ivaStorico}%</td>
                    <td>
                        <fmt:formatNumber value="${dettaglio.prezzoVenditaStorico}" type="currency" currencySymbol="€"/>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- BOX TOTALE -->
    <div class="totale-box">
        <strong>Totale Complessivo (IVA Incl.): </strong> 
        <span class="prezzo-totale">
            <fmt:formatNumber value="${ordine.totaleOrdine}" type="currency" currencySymbol="€"/>
        </span>
    </div>

    <div class="actions-bar hide-on-print">
        <div class="btn-group-right">
        	 <a href="${pageContext.request.contextPath}/Ordini" class="btn-back">← Torna ai Miei Ordini</a>

            <button type="button" onclick="window.print()" class="btn-stampa">Stampa Fattura (PDF)</button>

			<button type="submit" class="btn-delete" <c:if test="${ordine.statoOrdine != 'In elaborazione'}">disabled</c:if> 
				data-stato="${ordine.statoOrdine}" onclick="confermaEliminazione(${ordine.idOrdine}, this)">
				Annulla Ordine
			</button>
        </div>
    </div>

</div>

<dialog id="dlg-cancellazione">
    <p>Sicuro di voler cancellare questo prodotto?</p>
    <button class="btn-annulla" onclick="annulla()">Annulla</button>
    <button class="btn-conferma" onclick="elimina(this)" data-context-path="${pageContext.request.contextPath}">Conferma</button>
</dialog>

<jsp:include page="common/footer.jsp" />

</body>
</html>