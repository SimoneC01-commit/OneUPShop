<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="UTF-8">
    <title>Aggiungi Prodotto</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/aggiungiProdotto/styleAggiungiProdotto.css">
    <script src="${pageContext.request.contextPath}/resources/aggiungiProdotto/scriptAggiungiProdotto.js" defer></script>
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/favicon.ico">
</head>
<body>

    <div class="form-container">
        <h1>Aggiungi Nuovo Prodotto</h1>

        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                ${errorMessage}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/AggiungiProdotto" method="POST" enctype="multipart/form-data"
              onsubmit="event.preventDefault(); validate(this)" data-context-path="${pageContext.request.contextPath}">
            
            <div class="form-group">
                <label for="tipo">Tipo Prodotto *</label>
                <select name="tipo" id="tipo" onchange="gestisciTipo(); checkTipo(this)" required>
                    <option value="">Seleziona un tipo</option>
                    <option value="Gioco" ${param.tipo == 'Gioco' ? 'selected' : ''}>Gioco</option>
                    <option value="Console" ${param.tipo == 'Console' ? 'selected' : ''}>Console</option>
                    <option value="Gadget" ${param.tipo == 'Gadget' ? 'selected' : ''}>Gadget</option>
                    <option value="Cabinato" ${param.tipo == 'Cabinato' ? 'selected' : ''}>Cabinato</option>
                </select>
                <span class="error-text" id="err-tipo"></span>
            </div>

            <div class="form-group">
                <label for="titolo">Titolo *</label>
                <input type="text" name="titolo" id="titolo" required maxlength="100"
                       oninput="checkTitolo(this)" placeholder="es. Super Mario Bros"
                       value="<c:out value='${param.titolo}'/>">
                <span class="error-text" id="err-titolo"></span>
            </div>

            <div class="form-group">
                <label for="descrizione">Descrizione *</label>
                <textarea name="descrizione" id="descrizione" rows="4" required maxlength="1000"
                          oninput="checkDescrizione(this)" placeholder="Inserisci una descrizione dettagliata"><c:out value="${param.descrizione}"/></textarea>
                <span class="error-text" id="err-descrizione"></span>
            </div>

            <div class="form-group">
                <label for="annoRilascio">Anno Rilascio *</label>
                <input type="number" name="annoRilascio" id="annoRilascio" required min="1950" max="2026"
                       oninput="checkAnnoRilascio(this)" placeholder="es. 1985"
                       value="${param.annoRilascio}">
                <span class="error-text" id="err-annoRilascio"></span>
            </div>

            <div class="form-group">
                <label for="azienda">Azienda *</label>
                <input type="text" name="azienda" id="azienda" required maxlength="100"
                       oninput="checkAzienda(this)" placeholder="es. Nintendo"
                       value="<c:out value='${param.azienda}'/>">
                <span class="error-text" id="err-azienda"></span>
            </div>

            <div class="form-group">
                <label for="stato">Stato *</label>
                <select name="stato" id="stato" onchange="gestisciStato(); checkStato(this)" required>
                    <option value="Nuovo" ${param.stato == 'Nuovo' ? 'selected' : ''}>Nuovo</option>
                    <option value="Usato" ${param.stato == 'Usato' ? 'selected' : ''}>Usato</option>
                </select>
                <span class="error-text" id="err-stato"></span>
            </div>

            <!-- Il contenitore difetti resta visibile se in precedenza era stato selezionato "Usato" -->
            <div class="form-group" id="containerNoteDifetti" style="display: ${param.stato == 'Usato' ? 'block' : 'none'};">
                <label for="noteDifetti">Note Difetti *</label>
                <textarea name="noteDifetti" id="noteDifetti" rows="3" maxlength="500"
                          oninput="checkNoteDifetti(this)" placeholder="Descrivi eventuali graffi o difetti"><c:out value="${param.noteDifetti}"/></textarea>
                <span class="error-text" id="err-noteDifetti"></span>
            </div>

            <div class="form-group">
                <label for="iva">IVA (%) *</label>
                <input type="number" name="iva" id="iva" required min="0" max="100"
                       oninput="checkIva(this)" placeholder="es. 22"
                       value="${not empty param.iva ? param.iva : '22'}">
                <span class="error-text" id="err-iva"></span>
            </div>

            <!-- GESTIONE PREZZI TRAMITE CHECKBOX -->
            <div class="form-group checkbox-group">
                <label for="checkPrezzoCustom" class="checkbox-label">
                    <input type="checkbox" id="checkPrezzoCustom" name="checkPrezzoCustom" onchange="gestisciPrezzoCustom()"
                           ${param.checkPrezzoCustom == 'on' ? 'checked' : ''}>
                    Vuoi inserire un prezzo di vendita custom (anziché un prezzo di acquisto)?
                </label>
            </div>

            <!-- Gestione visibilità contenitori prezzi post-errore -->
            <div class="form-group" id="containerPrezzoAcquisto" style="display: ${param.checkPrezzoCustom == 'on' ? 'none' : 'block'};">
                <label for="prezzoAcquisto">Prezzo Acquisto (€) *</label>
                <input type="number" name="prezzoAcquisto" id="prezzoAcquisto" step="0.01"
                       oninput="checkPrezzoAcquisto(this)" placeholder="es. 49.99"
                       value="${param.prezzoAcquisto}">
                <span class="error-text" id="err-prezzoAcquisto"></span>
            </div>

            <div class="form-group" id="containerPrezzoAttuale" style="display: ${param.checkPrezzoCustom == 'on' ? 'block' : 'none'};">
                <label for="prezzoAttuale">Prezzo Attuale Custom (€) *</label>
                <input type="number" name="prezzoAttuale" id="prezzoAttuale" step="0.01"
                       oninput="checkPrezzoAttuale(this)" placeholder="es. 59.99"
                       value="${param.prezzoAttuale}">
                <span class="error-text" id="err-prezzoAttuale"></span>
            </div>

            <div class="form-group">
                <label for="foto">Foto Prodotto</label>
                <input type="file" name="foto" id="foto" accept="image/*" onchange="checkFoto(this)">
                <span class="error-text" id="err-foto"></span>
                <p style="font-size: 0.8em; color: #666; margin-top: 5px;">* in caso di errore di compilazione, seleziona nuovamente il file</p>
            </div>

            <!-- SEZIONI DINAMICHE: Manteniamo visibile solo quella corrispondente al tipo selezionato -->
            <div id="sezioneGioco" class="dynamic-section" style="display: ${param.tipo == 'Gioco' ? 'block' : 'none'};">
                <h3>Specifiche Gioco</h3>
                <div class="form-group">
                    <label for="sviluppatore">Sviluppatore *</label>
                    <input type="text" name="sviluppatore" id="sviluppatore" maxlength="100"
                           oninput="checkSviluppatore(this)" placeholder="es. Shigeru Miyamoto"
                           value="<c:out value='${param.sviluppatore}'/>">
                    <span class="error-text" id="err-sviluppatore"></span>
                </div>
            </div>

            <div id="sezioneConsole" class="dynamic-section" style="display: ${param.tipo == 'Console' ? 'block' : 'none'};">
                <h3>Specifiche Console</h3>
                <div class="form-group">
                    <label for="modelloSpecifico">Modello Specifico *</label>
                    <input type="text" name="modelloSpecifico" id="modelloSpecifico" maxlength="100"
                           oninput="checkModelloSpecifico(this)" placeholder="es. PAL Version - Model NES-001"
                           value="<c:out value='${param.modelloSpecifico}'/>">
                    <span class="error-text" id="err-modelloSpecifico"></span>
                </div>
            </div>

            <div id="sezioneGadget" class="dynamic-section" style="display: ${param.tipo == 'Gadget' ? 'block' : 'none'};">
                <h3>Specifiche Gadget</h3>
                <div class="form-group">
                    <label for="tipoMateriale">Tipo Materiale *</label>
                    <input type="text" name="tipoMateriale" id="tipoMateriale" maxlength="100"
                           oninput="checkTipoMateriale(this)" placeholder="es. Plastica / PVC"
                           value="<c:out value='${param.tipoMateriale}'/>">
                    <span class="error-text" id="err-tipoMateriale"></span>
                </div>
                <div class="form-group">
                    <label for="tipoGadget">Tipo Gadget *</label>
                    <input type="text" name="tipoGadget" id="tipoGadget" maxlength="100"
                           oninput="checkTipoGadget(this)" placeholder="es. Statuetta / Action Figure"
                           value="<c:out value='${param.tipoGadget}'/>">
                    <span class="error-text" id="err-tipoGadget"></span>
                </div>
            </div>

            <div id="sezioneCabinato" class="dynamic-section" style="display: ${param.tipo == 'Cabinato' ? 'block' : 'none'};">
                <h3>Specifiche Cabinato</h3>
                <div class="form-group">
                    <label for="tipoSistemaArcade">Tipo Sistema Arcade *</label>
                    <input type="text" name="tipoSistemaArcade" id="tipoSistemaArcade" maxlength="100"
                           oninput="checkTipoSistemaArcade(this)" placeholder="es. Neo Geo MVS"
                           value="<c:out value='${param.tipoSistemaArcade}'/>">
                    <span class="error-text" id="err-tipoSistemaArcade"></span>
                </div>
                <div class="form-group">
                    <label for="dimensioniCm">Dimensioni (LxPxA in cm) *</label>
                    <input type="text" name="dimensioniCm" id="dimensioniCm" maxlength="50"
                           oninput="checkDimensioniCm(this)" placeholder="es. 60x80x170"
                           value="<c:out value='${param.dimensioniCm}'/>">
                    <span class="error-text" id="err-dimensioniCm"></span>
                </div>
            </div>

            <button type="submit" class="btn-submit">Salva Prodotto</button>
        </form>
    </div>

</body>
</html>