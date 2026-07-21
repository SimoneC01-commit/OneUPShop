<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifica Prodotto #${prodotto.idProdotto}</title>
    <style>
        body { font-family: sans-serif; margin: 30px; line-height: 1.6; background-color: #f4f6f9; }
        .form-container { border: 1px solid #ddd; padding: 25px; max-width: 650px; border-radius: 8px; background-color: #ffffff; box-shadow: 0 2px 4px rgba(0,0,0,0.1); margin: auto; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; color: #333; }
        .form-group input, .form-group textarea, .form-group select { width: 100%; padding: 8px 10px; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px; font-size: 0.95em; }
        .form-group textarea { resize: vertical; min-height: 80px; }
        .form-row { display: flex; gap: 15px; }
        .form-row .form-group { flex: 1; }
        .readonly-info { background-color: #e9ecef; border: 1px solid #ced4da; padding: 12px; border-radius: 6px; margin-bottom: 20px; }
        .readonly-info p { margin: 5px 0; color: #495057; font-size: 0.9em; }
        .btn-submit { background-color: #28a745; color: white; border: none; padding: 12px 20px; border-radius: 4px; cursor: pointer; width: 100%; font-size: 1em; font-weight: bold; margin-top: 10px; }
        .btn-submit:hover { background-color: #218838; }
        .error-box { color: #721c24; background-color: #f8d7da; border: 1px solid #f5c6cb; padding: 12px; border-radius: 4px; margin-bottom: 20px; font-weight: bold; }
        .back-link { display: inline-block; margin-top: 15px; color: #007bff; text-decoration: none; }
        .back-link:hover { text-decoration: underline; }
    </style>
</head>
<body>

    <div class="form-container">
        <h2>✏️ Modifica Prodotto</h2>

        <!-- Box Errore -->
        <c:if test="${not empty errorMessage}">
            <div class="error-box">
                ⚠️ ${errorMessage}
            </div>
        </c:if>

        <!-- Informazioni Non Modificabili (Congelate) -->
        <div class="readonly-info">
            <p><strong>ID Prodotto:</strong> ${prodotto.idProdotto}</p>
            <p><strong>Tipo Componente:</strong> ${prodotto.tipo}</p>
            <p><strong>Data Aggiunta:</strong> ${prodotto.dataAggiunta}</p>
            <p><strong>Disponibile nel catalogo:</strong> ${prodotto.disponibile ? 'Sì' : 'No (Già Acquistato)'}</p>
        </div>

        <form action="${pageContext.request.contextPath}/ModificaProdotto" method="post" enctype="multipart/form-data">
            
            <!-- Campo Nascosto ID Prodotto -->
            <input type="hidden" name="idProdotto" value="${prodotto.idProdotto}">

            <!-- Titolo -->
            <div class="form-group">
                <label for="nuovoTitolo">Titolo Prodotto *</label>
                <input type="text" id="nuovoTitolo" name="nuovoTitolo" 
                       value="${not empty param.nuovoTitolo ? param.nuovoTitolo : prodotto.titolo}" required>
            </div>
            
            <div class="form-group">
			    <label for="nuovaFoto">Nuova Foto Copertina (lascia vuoto per non cambiarla):</label>
			    <input type="file" id="nuovaFoto" name="nuovaFoto" accept="image/*">
			</div>

            <!-- Azienda e Anno Rilascio -->
            <div class="form-row">
                <div class="form-group">
                    <label for="nuovaAzienda">Azienda / Produttore</label>
                    <input type="text" id="nuovaAzienda" name="nuovaAzienda" 
                           value="${not empty param.nuovaAzienda ? param.nuovaAzienda : prodotto.azienda}">
                </div>

                <div class="form-group">
                    <label for="nuovoAnnoRilascio">Anno di Rilascio</label>
                    <input type="number" id="nuovoAnnoRilascio" name="nuovoAnnoRilascio" min="1900" max="2099" 
                           value="${not empty param.nuovoAnnoRilascio ? param.nuovoAnnoRilascio : prodotto.annoRilascio}">
                </div>
            </div>

            <!-- Prezzo Acquisto, Prezzo Attuale, IVA -->
            <div class="form-row">
                <div class="form-group">
                    <label for="nuovoPrezzoAcquisto">Prezzo d'Acquisto (€)</label>
                    <input type="number" step="0.01" id="nuovoPrezzoAcquisto" name="nuovoPrezzoAcquisto" 
                           value="${not empty param.nuovoPrezzoAcquisto ? param.nuovoPrezzoAcquisto : prodotto.prezzoAcquisto}">
                </div>

                <div class="form-group">
                    <label for="nuovoPrezzoAttuale">Prezzo di Vendita (€) *</label>
                    <input type="number" step="0.01" id="nuovoPrezzoAttuale" name="nuovoPrezzoAttuale" 
                           value="${not empty param.nuovoPrezzoAttuale ? param.nuovoPrezzoAttuale : prodotto.prezzoAttuale}" required>
                </div>

                <div class="form-group">
                    <label for="nuovaIva">IVA (%)</label>
                    <input type="number" id="nuovaIva" name="nuovaIva" 
                           value="${not empty param.nuovaIva ? param.nuovaIva : prodotto.iva}">
                </div>
            </div>

            <!-- Stato Conservazione -->
            <div class="form-group">
                <label for="nuovoStato">Stato del Prodotto</label>
                <input type="text" id="nuovoStato" name="nuovoStato" 
                       value="${not empty param.nuovoStato ? param.nuovoStato : prodotto.stato}">
            </div>

            <!-- Descrizione -->
            <div class="form-group">
                <label for="nuovaDescrizione">Descrizione</label>
                <textarea id="nuovaDescrizione" name="nuovaDescrizione">${not empty param.nuovaDescrizione ? param.nuovaDescrizione : prodotto.descrizione}</textarea>
            </div>

            <!-- Note Difetti -->
            <div class="form-group">
                <label for="nuoveNoteDifetti">Note / Eventuali Difetti</label>
                <textarea id="nuoveNoteDifetti" name="nuoveNoteDifetti">${not empty param.nuoveNoteDifetti ? param.nuoveNoteDifetti : prodotto.noteDifetti}</textarea>
            </div>

            <button type="submit" class="btn-submit">💾 Salva Modifiche</button>
        </form>

        <a href="${pageContext.request.contextPath}/ElencoProdotti" class="back-link">← Annulla e Torna all'Elenco</a>
    </div>

</body>
</html>