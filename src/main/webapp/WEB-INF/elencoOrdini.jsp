<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Aggiunta della libreria JSTL Core -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestione Ordini - Amministrazione</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/elencoOrdini/styleElencoOrdini.css">
    <script src="${pageContext.request.contextPath}/resources/elencoOrdini/scriptElencoOrdini.js" defer></script>
</head>
<body>
	<jsp:include page="common/header.jsp" />

    <h1>Elenco Ordini Ricevuti</h1>

    <!-- Gestione Messaggio di Errore -->
    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>
    
    <div id="response">
    	
    </div>

    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>ID Ordine</th>
                    <th>Utente</th>
                    <th>Data</th>
                    <th>Metodo Pagamento</th>
                    <th>Totale</th>
                    <th>Stato</th>
                    <th>Azioni</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <%-- Se la lista ordini NON è vuota, esegui il ciclo --%>
                    <c:when test="${not empty ordini}">
                        
                        <c:forEach var="ordine" items="${ordini}">
                            
                            <!-- Riga Principale dell'Ordine -->
                            <tr id="ordine-${ordine.idOrdine}" class="main-order-row" onclick="toggleDettagli(${ordine.idOrdine})">
                                <td>#${ordine.idOrdine}</td>
                                <td>${ordine.emailUtente}</td>
                                <td>${ordine.dataOrdine}</td>
                                <td>${ordine.metodoPagamento}</td>
                                <td>&euro; ${ordine.totaleOrdine}</td>
                                
                                <!-- Menù a tendina per lo stato -->
                                <td>
                                    <select name="statoOrdine" class="select-stato" data-id-ordine="${ordine.idOrdine}" data-stato="${ordine.statoOrdine}" data-context-path="${pageContext.request.contextPath}"
                                    	onchange="cambiaStato(this)" onclick="event.stopPropagation()">
                                        <option value="In elaborazione" <c:if test="${ordine.statoOrdine == 'In elaborazione'}">selected</c:if>>In elaborazione</option>
                                        <option value="Spedito" <c:if test="${ordine.statoOrdine == 'Spedito'}">selected</c:if>>Spedito</option>
                                        <option value="Consegnato" <c:if test="${ordine.statoOrdine == 'Consegnato'}">selected</c:if>>Consegnato</option>
                                    </select>
                                </td>
                                
                                <!-- Pulsante Cancellazione -->
                                <td>
	                                <button type="button" id="btn-delete" class="btn-delete" onclick="confermaEliminazione(${ordine.idOrdine})"
	                                	<c:if test="${ordine.statoOrdine != 'In elaborazione'}">disabled</c:if>>
	                                    Cancella
	                                </button>
                                </td>
                            </tr>

                            <!-- Riga Nascosta: Dettagli Ordine -->
                            <tr id="dettagli-${ordine.idOrdine}" class="dettagli-row" style="display: none;">
                                <td colspan="7">
                                    <div class="dettagli-container">
                                        <h4>Dettagli Ordine #${ordine.idOrdine}</h4>
                                        <p>
                                            <strong>Indirizzo di spedizione:</strong> ${not empty ordine.indirizzoSpedizione ? ordine.indirizzoSpedizione : 'N/D'} <br>
                                            <strong>Telefono:</strong> ${not empty ordine.telefono ? ordine.telefono : 'N/D'}
                                        </p>
                                        
                                        <table class="inner-table">
                                            <thead>
                                                <tr>
                                                    <th>Prodotto ID</th>
                                                    <th>Nome</th>
                                                    <th>Prezzo Storico</th>
                                                    <th>IVA</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                
                                                <c:set var="hasDetails" value="false" />
                                                
                                                <!-- Ciclo sui Dettagli Ordine -->
                                                <c:if test="${not empty dettagliOrdini}">
                                                    <c:forEach var="dettaglio" items="${dettagliOrdini}">
                                                        
                                                        <c:if test="${dettaglio.idOrdine == ordine.idOrdine}">
                                                            <tr>
                                                                <td>${dettaglio.prodotto.idProdotto}</td>
                                                                <td>${dettaglio.prodotto.titolo}</td>
                                                                <td>&euro; ${dettaglio.prezzoVenditaStorico}</td>
                                                                <td>${dettaglio.ivaStorico}%</td>
                                                            </tr>
                                                            <c:set var="hasDetails" value="true" />
                                                        </c:if>
                                                        
                                                    </c:forEach>
                                                </c:if>

                                                <!-- Se non ci sono dettagli associati a questo ordine -->
                                                <c:if test="${not hasDetails}">
                                                    <tr>
                                                        <td colspan="4">Nessun dettaglio trovato.</td>
                                                    </tr>
                                                </c:if>

                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                            
                        </c:forEach>
                        
                    </c:when>
                    <%-- ALTRIMENTI: Nessun ordine --%>
                    <c:otherwise>
                        <tr>
                            <td colspan="7">Nessun ordine presente nel sistema.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>

    <dialog id="dlg-cancellazione">
        <p>Sicuro di voler cancellare questo prodotto?</p>
        <button class="btn-annulla" onclick="annulla()">Annulla</button>
        <button class="btn-conferma" onclick="elimina(this)" data-context-path="${pageContext.request.contextPath}">Conferma</button>
    </dialog>
    <jsp:include page="common/footer.jsp" />
</body>
</html>