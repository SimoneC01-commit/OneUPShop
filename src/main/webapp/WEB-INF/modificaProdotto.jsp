<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifica Prodotto #${prodotto.idProdotto}</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/modificaProdotto/styleModificaProdotto.css">
    <script src="${pageContext.request.contextPath}/resources/modificaProdotto/scriptModificaProdotto.js" defer></script>
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/favicon.ico">
</head>
<body>

    <div class="form-container">
        <h1>✏️ Modifica Prodotto</h1>

        <!-- Box Errore Server -->
        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                ${errorMessage}
            </div>
        </c:if>

        <!-- Informazioni Congelate (Non Modificabili) -->
        <div class="readonly-info">
            <p><strong>ID Prodotto:</strong> ${prodotto.idProdotto}</p>
            <p><strong>Tipo Componente:</strong> ${prodotto.tipo}</p>
            <p><strong>Data Aggiunta:</strong> ${prodotto.dataAggiunta}</p>
            <p><strong>Stato Disponibilità:</strong> ${prodotto.disponibile ? 'Sì (In Catalogo)' : 'No (Venduto)'}</p>
        </div>

        <form action="${pageContext.request.contextPath}/ModificaProdotto" method="POST" enctype="multipart/form-data"
              onsubmit="event.preventDefault(); validate(this)" data-context-path="${pageContext.request.contextPath}">
            
            <!-- Campi Nascosti -->
            <input type="hidden" name="idProdotto" id="idProdotto" value="${prodotto.idProdotto}">
            <input type="hidden" name="tipo" id="tipo" value="${prodotto.tipo}">

            <!-- Titolo -->
            <div class="form-group">
                <label for="nuovoTitolo">Titolo Prodotto *</label>
                <input type="text" id="nuovoTitolo" name="nuovoTitolo" required maxlength="100"
                       value="${not empty param.nuovoTitolo ? param.nuovoTitolo : prodotto.titolo}"
                       oninput="checkTitolo(this)">
                <span class="error-text" id="err-nuovoTitolo"></span>
            </div>

            <!-- Foto Copertina -->
            <div class="form-group">
                <label for="nuovaFoto">Nuova Foto Copertina (lascia vuoto per mantenere l'attuale):</label>
                <input type="file" id="nuovaFoto" name="nuovaFoto" accept="image/*" onchange="checkFoto(this)">
                <span class="error-text" id="err-nuovaFoto"></span>
            </div>

            <!-- Azienda e Anno Rilascio -->
            <div class="form-row">
                <div class="form-group">
                    <label for="nuovaAzienda">Azienda / Produttore *</label>
                    <input type="text" id="nuovaAzienda" name="nuovaAzienda" required maxlength="100"
                           value="${not empty param.nuovaAzienda ? param.nuovaAzienda : prodotto.azienda}"
                           oninput="checkAzienda(this)">
                    <span class="error-text" id="err-nuovaAzienda"></span>
                </div>

                <div class="form-group">
                    <label for="nuovoAnnoRilascio">Anno di Rilascio *</label>
                    <input type="number" id="nuovoAnnoRilascio" name="nuovoAnnoRilascio" required min="1950" max="2026"
                           value="${not empty param.nuovoAnnoRilascio ? param.nuovoAnnoRilascio : prodotto.annoRilascio}"
                           oninput="checkAnnoRilascio(this)">
                    <span class="error-text" id="err-nuovoAnnoRilascio"></span>
                </div>
            </div>

            <!-- Stato Conservazione -->
            <c:set var="statoAttuale" value="${not empty param.nuovoStato ? param.nuovoStato : prodotto.stato}" />
            <div class="form-group">
                <label for="nuovoStato">Stato del Prodotto *</label>
                <select name="nuovoStato" id="nuovoStato" onchange="gestisciStato(); checkStato(this)" required>
                    <option value="Nuovo" ${statoAttuale == 'Nuovo' ? 'selected' : ''}>Nuovo</option>
                    <option value="Usato" ${statoAttuale == 'Usato' ? 'selected' : ''}>Usato</option>
                </select>
                <span class="error-text" id="err-nuovoStato"></span>
            </div>

            <!-- Note Difetti (Visibile solo se Usato) -->
            <div class="form-group" id="containerNoteDifetti" style="${statoAttuale == 'Usato' ? '' : 'display: none;'}">
                <label for="nuoveNoteDifetti">Note / Eventuali Difetti *</label>
                <textarea id="nuoveNoteDifetti" name="nuoveNoteDifetti" rows="3" maxlength="500"
                          oninput="checkNoteDifetti(this)" placeholder="Descrivi eventuali graffi o difetti">${not empty param.nuoveNoteDifetti ? param.nuoveNoteDifetti : prodotto.noteDifetti}</textarea>
                <span class="error-text" id="err-nuoveNoteDifetti"></span>
            </div>

            <!-- Prezzi e IVA -->
            <div class="form-row">
                <div class="form-group">
                    <label for="nuovoPrezzoAcquisto">Prezzo d'Acquisto (€)</label>
                    <input type="number" step="0.01" id="nuovoPrezzoAcquisto" name="nuovoPrezzoAcquisto"
                           value="${not empty param.nuovoPrezzoAcquisto ? param.nuovoPrezzoAcquisto : prodotto.prezzoAcquisto}"
                           oninput="checkPrezzoAcquisto(this)">
                    <span class="error-text" id="err-nuovoPrezzoAcquisto"></span>
                </div>

                <div class="form-group">
                    <label for="nuovoPrezzoAttuale">Prezzo di Vendita (€) *</label>
                    <input type="number" step="0.01" id="nuovoPrezzoAttuale" name="nuovoPrezzoAttuale" required
                           value="${not empty param.nuovoPrezzoAttuale ? param.nuovoPrezzoAttuale : prodotto.prezzoAttuale}"
                           oninput="checkPrezzoAttuale(this)">
                    <span class="error-text" id="err-nuovoPrezzoAttuale"></span>
                </div>

                <div class="form-group">
                    <label for="nuovaIva">IVA (%) *</label>
                    <input type="number" id="nuovaIva" name="nuovaIva" required min="0" max="100"
                           value="${not empty param.nuovaIva ? param.nuovaIva : prodotto.iva}"
                           oninput="checkIva(this)">
                    <span class="error-text" id="err-nuovaIva"></span>
                </div>
            </div>

            <!-- Descrizione -->
            <div class="form-group">
                <label for="nuovaDescrizione">Descrizione *</label>
                <textarea id="nuovaDescrizione" name="nuovaDescrizione" rows="4" required maxlength="1000"
                          oninput="checkDescrizione(this)" placeholder="Inserisci una descrizione dettagliata">${not empty param.nuovaDescrizione ? param.nuovaDescrizione : prodotto.descrizione}</textarea>
                <span class="error-text" id="err-nuovaDescrizione"></span>
            </div>

            <!-- SEZIONI SPECIFICHE IN BASE AL TIPO DI PRODOTTO -->
            
            <!-- Specifiche Gioco -->
            <c:if test="${prodotto.tipo == 'Gioco'}">
                <div id="sezioneGioco" class="dynamic-section" style="display: block;">
                    <h3>Specifiche Gioco</h3>
                    <div class="form-group">
                        <label for="nuovoSviluppatore">Sviluppatore *</label>
                        <input type="text" name="nuovoSviluppatore" id="nuovoSviluppatore" required maxlength="100"
                               value="${not empty param.nuovoSviluppatore ? param.nuovoSviluppatore : gioco.sviluppatore}"
                               oninput="checkSviluppatore(this)">
                        <span class="error-text" id="err-nuovoSviluppatore"></span>
                    </div>
                </div>
            </c:if>

            <!-- Specifiche Console -->
            <c:if test="${prodotto.tipo == 'Console'}">
                <div id="sezioneConsole" class="dynamic-section" style="display: block;">
                    <h3>Specifiche Console</h3>
                    <div class="form-group">
                        <label for="nuovoModelloSpecifico">Modello Specifico *</label>
                        <input type="text" name="nuovoModelloSpecifico" id="nuovoModelloSpecifico" required maxlength="100"
                               value="${not empty param.nuovoModelloSpecifico ? param.nuovoModelloSpecifico : console.modelloSpecifico}"
                               oninput="checkModelloSpecifico(this)">
                        <span class="error-text" id="err-nuovoModelloSpecifico"></span>
                    </div>
                </div>
            </c:if>

            <!-- Specifiche Gadget -->
            <c:if test="${prodotto.tipo == 'Gadget'}">
                <div id="sezioneGadget" class="dynamic-section" style="display: block;">
                    <h3>Specifiche Gadget</h3>
                    <div class="form-group">
                        <label for="nuovoTipoMateriale">Tipo Materiale *</label>
                        <input type="text" name="nuovoTipoMateriale" id="nuovoTipoMateriale" required maxlength="100"
                               value="${not empty param.nuovoTipoMateriale ? param.nuovoTipoMateriale : gadget.tipoMateriale}"
                               oninput="checkTipoMateriale(this)">
                        <span class="error-text" id="err-nuovoTipoMateriale"></span>
                    </div>
                    <div class="form-group">
                        <label for="nuovoTipoGadget">Tipo Gadget *</label>
                        <input type="text" name="nuovoTipoGadget" id="nuovoTipoGadget" required maxlength="100"
                               value="${not empty param.nuovoTipoGadget ? param.nuovoTipoGadget : gadget.tipoGadget}"
                               oninput="checkTipoGadget(this)">
                        <span class="error-text" id="err-nuovoTipoGadget"></span>
                    </div>
                </div>
            </c:if>

            <!-- Specifiche Cabinato -->
            <c:if test="${prodotto.tipo == 'Cabinato'}">
                <div id="sezioneCabinato" class="dynamic-section" style="display: block;">
                    <h3>Specifiche Cabinato</h3>
                    <div class="form-group">
                        <label for="nuovoTipoSistemaArcade">Tipo Sistema Arcade *</label>
                        <input type="text" name="nuovoTipoSistemaArcade" id="nuovoTipoSistemaArcade" required maxlength="100"
                               value="${not empty param.nuovoTipoSistemaArcade ? param.nuovoTipoSistemaArcade : cabinato.tipoSistemaArcade}"
                               oninput="checkTipoSistemaArcade(this)">
                        <span class="error-text" id="err-nuovoTipoSistemaArcade"></span>
                    </div>
                    <div class="form-group">
                        <label for="nuoveDimensioniCm">Dimensioni (LxPxA in cm) *</label>
                        <input type="text" name="nuoveDimensioniCm" id="nuoveDimensioniCm" required maxlength="50"
                               value="${not empty param.nuoveDimensioniCm ? param.nuoveDimensioniCm : cabinato.dimensioniCm}"
                               oninput="checkDimensioniCm(this)">
                        <span class="error-text" id="err-nuoveDimensioniCm"></span>
                    </div>
                </div>
            </c:if>

            <button type="submit" class="btn-submit">💾 Salva Modifiche</button>
        </form>

        <a href="${pageContext.request.contextPath}/ElencoProdotti" class="back-link">← Annulla e Torna all'Elenco</a>
    </div>

</body>
</html>