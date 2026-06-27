<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dettaglio Ordine #${ordine.idOrdine} - OneUpShop</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f7f6;
            margin: 0;
            padding: 20px;
            color: #333;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
            background: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .header-ordine {
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 2px solid #3498db;
            padding-bottom: 15px;
            margin-bottom: 20px;
        }
        .header-ordine h1 {
            color: #2c3e50;
            margin: 0;
        }
        .info-box {
            background: #f9f9f9;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            margin-bottom: 20px;
            line-height: 1.6;
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
        .totale-box {
            text-align: right;
            font-size: 20px;
            margin-top: 20px;
            padding-top: 15px;
            border-top: 2px solid #2c3e50;
        }
        .btn-back {
            display: inline-block;
            background: #7f8c8d;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 4px;
            margin-top: 20px;
            transition: background 0.3s;
        }
        .btn-back:hover {
            background: #95a5a6;
        }
        .badge {
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: bold;
            text-transform: uppercase;
        }
        .badge-elaborazione { background-color: #ffeaa7; color: #d63031; }
        .badge-spedito { background-color: #74b9ff; color: #0984e3; }
        .badge-consegnato { background-color: #55efc4; color: #00b894; }
        .badge-annullato { background-color: #ff7675; color: #d63031; }
        
        .img-prodotto {
            width: 60px;
            height: auto;
            border-radius: 4px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.2);
        }
    </style>
</head>
<body>

<div class="container">
    
    <div class="header-ordine">
        <h1>Ordine #${ordine.idOrdine}</h1>
        <div>
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
        </div>
    </div>

    <div class="info-box">
        <strong>Data Ordine:</strong> <fmt:formatDate value="${ordine.dataOrdine}" pattern="dd/MM/yyyy - HH:mm" /><br>
        <strong>Indirizzo di Spedizione:</strong> ${ordine.indirizzoSpedizioneStorico}<br>
        <strong>Telefono:</strong> ${ordine.telefono}<br>
        <strong>Metodo di Pagamento:</strong> ${ordine.metodoPagamento}
    </div>

    <h2>Prodotti Acquistati</h2>
    <table>
        <thead>
            <tr>
                <th>Immagine</th>
                <th>Prodotto</th>
                <th>Tipo</th>
                <th>Prezzo (Storico)</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="dettaglio" items="${dettagliOrdine}">
                
                <%-- Costruiamo l'URL per i dettagli del prodotto --%>
                <c:url var="prodottoUrl" value="/DettagliProdotto">
                    <%-- Puoi cambiare 'idProdotto' con il nome esatto che si aspetta la tua Servlet --%>
                    <c:param name="idProdotto" value="${dettaglio.prodotto.ID}" />
                </c:url>
                
                <%-- Rendiamo la riga cliccabile con Javascript e aggiungiamo la manina (cursor: pointer) --%>
                <tr onclick="window.location='${prodottoUrl}';" style="cursor: pointer;" title="Clicca per vedere i dettagli del prodotto">
                    <td>
                        <img src="${pageContext.request.contextPath}/ImmagineServlet?id=${dettaglio.prodotto.ID}" 
                             alt="${dettaglio.prodotto.titolo}" class="img-prodotto" 
                             onerror="this.src='https://via.placeholder.com/60?text=No+Img';">
                    </td>
                    <td><strong>${dettaglio.prodotto.titolo}</strong></td>
                    <td>${dettaglio.prodotto.tipo}</td>
                    <td>
                        <fmt:formatNumber value="${dettaglio.prezzoVenditaStorico}" type="currency" currencySymbol="€"/>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="totale-box">
        <strong>Totale Ordine: </strong> 
        <span style="color: #27ae60;">
            <fmt:formatNumber value="${ordine.totaleOrdine}" type="currency" currencySymbol="€"/>
        </span>
    </div>

    <a href="${pageContext.request.contextPath}/Ordini" class="btn-back">← Torna ai Miei Ordini</a>

</div>

</body>
</html>