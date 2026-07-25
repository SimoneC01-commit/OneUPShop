<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Aggiunta della libreria JSTL Core -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="UTF-8">
    <title>Pannello Admin - Elenco Prodotti</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/elencoProdotti/styleElencoProdotti.css">
    <script src="${pageContext.request.contextPath}/resources/elencoProdotti/scriptElencoProdotti.js" defer></script>
</head>
<body>

    <jsp:include page="common/header.jsp" />

    <div class="header-container">
        <h1>Elenco Prodotti</h1>
        
        <div class="admin-filter-wrapper">
            <input type="text" id="adminFiltroTesto" class="admin-table-filter" placeholder="Cerca per titolo...">
        </div>
        
        <div class="btn-group">
            <a href="${pageContext.request.contextPath}/AggiungiProdotto" class="btn-add">+ Aggiungi Prodotto</a>
            <a href="${pageContext.request.contextPath}/Profilo" class="btn-back">← Torna al Profilo</a>
        </div>
    </div>
    
    <c:if test="${not empty errorMessage}">
        <div class="error">${errorMessage}</div>
    </c:if>
    
    <div id="response" style="display: none;"></div>
    
    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Foto</th>
                    <th>Titolo</th>
                    <th>Azienda</th>
                    <th>Tipo</th>
                    <th>Stato</th>
                    <th>Prezzo</th>
                    <th>Disponibile</th>
                    <th>IVA</th>
                    <th>Azioni</th> 
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <%-- Se ci sono prodotti --%>
                    <c:when test="${not empty prodotti}">
                        <c:forEach var="p" items="${prodotti}">
                            <tr id="prodotto-${p.idProdotto}" class="riga-prodotto">
                                
                                <td>${p.idProdotto}</td>
                                <td>
                                	<img src="${pageContext.request.contextPath}/GetPicture?idProdotto=${p.idProdotto}" alt="${p.titolo}" class="img-preview" />
                                </td>
                                <td class="titolo-prodotto"><strong>${p.titolo}</strong></td>
                                <td>${p.azienda}</td>
                                <td>${p.tipo}</td>
                                
                                <td>
                                    <c:choose>
                                        <c:when test="${p.stato == 'Nuovo'}">
                                            <span class="badge badge-nuovo">Nuovo</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-usato" title="Difetti: ${not empty p.noteDifetti ? p.noteDifetti : 'Nessuno'}">Usato</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                
                                <td>&euro; ${p.prezzoAttuale}</td>
                                
                                <td>
                                    <c:choose>
                                        <c:when test="${p.disponibile}">
                                            <span class="badge badge-disp">Sì</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-not-disp">No</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                
                                <td>${p.iva}%</td>
                                
                                <td>
                                    <div class="action-group">
                                        <button type="button" class="btn-edit" onclick="modificaProdotto(${p.idProdotto})" 
                                            <c:if test="${!p.disponibile}">disabled</c:if>>
                                            Modifica
                                        </button>
                                        
                                        <button type="button" class="btn-delete" onclick="confermaEliminazione(${p.idProdotto})" 
                                            <c:if test="${!p.disponibile}">disabled</c:if>>
                                            Cancella
                                        </button>
                                    </div>
                                </td>
                                
                            </tr>
                        </c:forEach>
                    </c:when>
                    
                    <c:otherwise>
                        <tr>
                            <td colspan="10" class="empty-catalog">Nessun prodotto presente nel catalogo.</td>
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