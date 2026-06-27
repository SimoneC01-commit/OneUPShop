<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>I Miei Ordini - OneUpShop</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f7f6;
            margin: 0;
            padding: 20px;
            color: #333;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        h1 {
            color: #2c3e50;
            border-bottom: 2px solid #3498db;
            padding-bottom: 10px;
            margin-bottom: 30px;
        }
        .no-orders {
            text-align: center;
            padding: 40px;
            background: #f9f9f9;
            border-radius: 4px;
            border: 1px dashed #ccc;
        }
        .btn-home {
            display: inline-block;
            background: #3498db;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 4px;
            margin-top: 15px;
            transition: background 0.3s;
        }
        .btn-home:hover {
            background: #2980b9;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #2c3e50;
            color: white;
            font-weight: bold;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .badge {
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: bold;
            text-transform: uppercase;
        }
        .badge-elaborazione { background-color: #ffeaa7; color: #d63031; }
        .badge-spedito { background-color: #74b9ff; color: #0984e3; }
        .badge-consegnato { background-color: #55efc4; color: #00b894; }
        .badge-annullato { background-color: #ff7675; color: #d63031; }
        
        .Dettagli-storici {
            font-size: 13px;
            color: #7f8c8d;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>I Miei Ordini</h1>
    
    <%-- Verifica se la lista degli ordini passata dalla Servlet è vuota o nulla --%>
    <c:choose>
        <c:when test="${empty ordini}">
            <div class="no-orders">
                <h2>Non hai ancora effettuato nessun ordine.</h2>
                <p>Esplora il nostro catalogo per trovare fantastici prodotti arcade!</p>
                <a href="${pageContext.request.contextPath}/Home" class="btn-home">Torna allo Shop</a>
            </div>
        </c:when>
        <c:otherwise>
            <%-- Se ci sono ordini, mostra la tabella --%>
            <table>
                <thead>
                    <tr>
                        <th>ID Ordine</th>
                        <th>Data e Ora</th>
                        <th>Stato</th>
                        <th>Totale</th>
                        <th>Dettagli Spedizione</th>
                        <th>Pagamento</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- Ciclo sull'ArrayList di OrdineBean --%>
                    <c:forEach var="ordine" items="${ordini}">
					    <%-- Generiamo l'URL per ogni riga --%>
					    <c:url var="dettagliUrl" value="/DettagliOrdine">
					        <c:param name="idOrdine" value="${ordine.idOrdine}" />
					    </c:url>
					    
					    <%-- L'attributo onclick reindirizza alla servlet, lo stile 'cursor:pointer' fa apparire la manina --%>
					    <tr onclick="window.location='${dettagliUrl}';" style="cursor: pointer;">
					        <td><strong>#${ordine.idOrdine}</strong></td>
					        <td>
					            <fmt:formatDate value="${ordine.dataOrdine}" pattern="dd/MM/yyyy HH:mm" />
					        </td>
					        <td>
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
					        </td>
					        <td>
					            <fmt:formatNumber value="${ordine.totaleOrdine}" type="currency" currencySymbol="€"/>
					        </td>
					        <td>
					            <div class="Dettagli-storici">
					                <strong>Indirizzo:</strong> ${ordine.indirizzoSpedizioneStorico}<br>
					                <strong>Tel:</strong> ${ordine.telefono}
					            </div>
					        </td>
					        <td>${ordine.metodoPagamento}</td>
					    </tr>
					</c:forEach>
                </tbody>
            </table>
            
            <div style="margin-top: 30px;">
                <a href="${pageContext.request.contextPath}/Home" class="btn-home" style="background-color: #7f8c8d;">Continua lo Shopping</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>