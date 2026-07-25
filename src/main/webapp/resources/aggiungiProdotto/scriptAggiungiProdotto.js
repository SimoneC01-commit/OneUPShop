// ==========================================
// 1. FUNZIONI DI VALIDAZIONE DEI CAMPI
// ==========================================

function checkTipo(inputtxt) {
    if (!inputtxt.value) {
        addError(inputtxt, "Seleziona un tipo di prodotto.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkTitolo(inputtxt) {
    const val = inputtxt.value.trim();
    if (val.length < 2 || val.length > 100) {
        addError(inputtxt, "Il titolo deve contenere tra 2 e 100 caratteri.");
        return false;
    }

    const titoloRegex = /^[a-zA-Z0-9\s'’:\-\.!,?()]{2,100}$/;
    if (titoloRegex.test(val)) {
        removeError(inputtxt);
        return true;
    }

    addError(inputtxt, "Titolo contenente caratteri non validi.");
    return false;
}

function checkDescrizione(inputtxt) {
    const val = inputtxt.value.trim();
    if (val.length < 10 || val.length > 1000) {
        addError(inputtxt, "La descrizione deve contenere tra 10 e 1000 caratteri.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkAnnoRilascio(inputtxt) {
    const val = parseInt(inputtxt.value, 10);
    const annoCorrente = new Date().getFullYear();

    if (isNaN(val) || val < 1950 || val > annoCorrente) {
        addError(inputtxt, `L'anno deve essere compreso tra 1950 e ${annoCorrente}.`);
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkAzienda(inputtxt) {
    const val = inputtxt.value.trim();
    if (val.length < 2 || val.length > 100) {
        addError(inputtxt, "L'azienda deve contenere tra 2 e 100 caratteri.");
        return false;
    }

    const aziendaRegex = /^[a-zA-Z0-9\s'’&:\-\.]{2,100}$/;
    if (aziendaRegex.test(val)) {
        removeError(inputtxt);
        return true;
    }

    addError(inputtxt, "Nome azienda non valido.");
    return false;
}

function checkStato(inputtxt) {
    if (!inputtxt.value) {
        addError(inputtxt, "Seleziona lo stato del prodotto.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkNoteDifetti(inputtxt) {
    const stato = document.getElementById("stato").value;
    if (stato === "Usato") {
        const val = inputtxt.value.trim();
        if (val.length < 5 || val.length > 500) {
            addError(inputtxt, "Le note sui difetti devono contenere tra 5 e 500 caratteri.");
            return false;
        }
    }
    removeError(inputtxt);
    return true;
}

function checkIva(inputtxt) {
    const val = parseFloat(inputtxt.value);
    if (isNaN(val) || val < 0 || val > 100) {
        addError(inputtxt, "Inserisci una percentuale IVA valida tra 0 e 100.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkPrezzoAcquisto(inputtxt) {
    const isCustom = document.getElementById("checkPrezzoCustom").checked;
    if (isCustom) {
        removeError(inputtxt);
        return true;
    }

    const val = parseFloat(inputtxt.value);
    if (isNaN(val) || val <= 0) {
        addError(inputtxt, "Inserisci un prezzo di acquisto valido maggiore di 0.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkPrezzoAttuale(inputtxt) {
    const isCustom = document.getElementById("checkPrezzoCustom").checked;
    if (!isCustom) {
        removeError(inputtxt);
        return true;
    }

    const val = parseFloat(inputtxt.value);
    if (isNaN(val) || val <= 0) {
        addError(inputtxt, "Inserisci un prezzo attuale valido maggiore di 0.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkFoto(inputtxt) {
    if (inputtxt.files && inputtxt.files[0]) {
        const file = inputtxt.files[0];
        const tipiConsentiti = ["image/jpeg", "image/png", "image/webp"];
        if (!tipiConsentiti.includes(file.type)) {
            addError(inputtxt, "Formato file non supportato (usa JPG, PNG o WEBP).");
            return false;
        }
        if (file.size > 1024 * 1024 * 10) {
            addError(inputtxt, "La foto non può superare i 10MB.");
            return false;
        }
    }
    removeError(inputtxt);
    return true;
}

// --- Controlli Sezioni Dinamiche ---

function checkSviluppatore(inputtxt) {
    const val = inputtxt.value.trim();
    if (val.length < 2 || val.length > 100) {
        addError(inputtxt, "Lo sviluppatore deve contenere tra 2 e 100 caratteri.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkModelloSpecifico(inputtxt) {
    const val = inputtxt.value.trim();
    if (val.length < 2 || val.length > 100) {
        addError(inputtxt, "Il modello specifico deve contenere tra 2 e 100 caratteri.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkTipoMateriale(inputtxt) {
    const val = inputtxt.value.trim();
    if (val.length < 2 || val.length > 100) {
        addError(inputtxt, "Il tipo di materiale deve contenere tra 2 e 100 caratteri.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkTipoGadget(inputtxt) {
    const val = inputtxt.value.trim();
    if (val.length < 2 || val.length > 100) {
        addError(inputtxt, "Il tipo di gadget deve contenere tra 2 e 100 caratteri.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkTipoSistemaArcade(inputtxt) {
    const val = inputtxt.value.trim();
    if (val.length < 2 || val.length > 100) {
        addError(inputtxt, "Il sistema arcade deve contenere tra 2 e 100 caratteri.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkDimensioniCm(inputtxt) {
    const val = inputtxt.value.trim();
    const dimensioniRegex = /^\d{1,3}\s*x\s*\d{1,3}\s*x\s*\d{1,3}$/i;
    if (!dimensioniRegex.test(val)) {
        addError(inputtxt, "Formato non valido. Usa il formato LxPxA (es. 60x80x170).");
        return false;
    }
    removeError(inputtxt);
    return true;
}


// ==========================================
// 2. FUNZIONI DI GESTIONE INTERFACCIA
// ==========================================

function gestisciTipo() {
    let tipo = document.getElementById("tipo").value;

    document.getElementById("sezioneGioco").style.display = "none";
    document.getElementById("sezioneConsole").style.display = "none";
    document.getElementById("sezioneGadget").style.display = "none";
    document.getElementById("sezioneCabinato").style.display = "none";

    if (tipo === "Gioco") {
        document.getElementById("sezioneGioco").style.display = "block";
    } else if (tipo === "Console") {
        document.getElementById("sezioneConsole").style.display = "block";
    } else if (tipo === "Gadget") {
        document.getElementById("sezioneGadget").style.display = "block";
    } else if (tipo === "Cabinato") {
        document.getElementById("sezioneCabinato").style.display = "block";
    }
}

function gestisciStato() {
    let stato = document.getElementById("stato").value;
    let containerNote = document.getElementById("containerNoteDifetti");
    let inputNote = document.getElementById("noteDifetti");

    if (stato === "Usato") {
        containerNote.style.display = "block";
        inputNote.required = true;
    } else {
        containerNote.style.display = "none";
        inputNote.required = false;
        inputNote.value = "";
        removeError(inputNote);
    }
}

function gestisciPrezzoCustom() {
    const isCustom = document.getElementById("checkPrezzoCustom").checked;
    const containerAcquisto = document.getElementById("containerPrezzoAcquisto");
    const inputAcquisto = document.getElementById("prezzoAcquisto");
    const containerAttuale = document.getElementById("containerPrezzoAttuale");
    const inputAttuale = document.getElementById("prezzoAttuale");

    if (isCustom) {
        containerAcquisto.style.display = "none";
        inputAcquisto.required = false;
        inputAcquisto.value = "-1";
        removeError(inputAcquisto);

        containerAttuale.style.display = "block";
        inputAttuale.required = true;
    } else {
        containerAcquisto.style.display = "block";
        inputAcquisto.required = true;
        if (inputAcquisto.value === "-1") {
            inputAcquisto.value = "";
        }

        containerAttuale.style.display = "none";
        inputAttuale.required = false;
        inputAttuale.value = "";
        removeError(inputAttuale);
    }
}


// ==========================================
// 3. FUNZIONE PRINCIPALE DI VALIDAZIONE
// ==========================================

function validate(obj) {
    let valid = true;

    const tipo = document.getElementById("tipo");
    if (!checkTipo(tipo)) valid = false;

    const titolo = document.getElementById("titolo");
    if (!checkTitolo(titolo)) valid = false;

    const descrizione = document.getElementById("descrizione");
    if (!checkDescrizione(descrizione)) valid = false;

    const annoRilascio = document.getElementById("annoRilascio");
    if (!checkAnnoRilascio(annoRilascio)) valid = false;

    const azienda = document.getElementById("azienda");
    if (!checkAzienda(azienda)) valid = false;

    const stato = document.getElementById("stato");
    if (!checkStato(stato)) valid = false;

    const noteDifetti = document.getElementById("noteDifetti");
    if (stato.value === "Usato") {
        if (!checkNoteDifetti(noteDifetti)) valid = false;
    } else {
        removeError(noteDifetti);
    }

    const iva = document.getElementById("iva");
    if (!checkIva(iva)) valid = false;

    const isCustom = document.getElementById("checkPrezzoCustom").checked;
    const prezzoAcquisto = document.getElementById("prezzoAcquisto");
    const prezzoAttuale = document.getElementById("prezzoAttuale");

    if (isCustom) {
        if (!checkPrezzoAttuale(prezzoAttuale)) valid = false;
        removeError(prezzoAcquisto);
    } else {
        if (!checkPrezzoAcquisto(prezzoAcquisto)) valid = false;
        removeError(prezzoAttuale);
    }

    const foto = document.getElementById("foto");
    if (!checkFoto(foto)) valid = false;

    // Controlli condizionali sul tipo di prodotto
    if (tipo.value === "Gioco") {
        const sviluppatore = document.getElementById("sviluppatore");
        if (!checkSviluppatore(sviluppatore)) valid = false;
    } else if (tipo.value === "Console") {
        const modelloSpecifico = document.getElementById("modelloSpecifico");
        if (!checkModelloSpecifico(modelloSpecifico)) valid = false;
    } else if (tipo.value === "Gadget") {
        const tipoMateriale = document.getElementById("tipoMateriale");
        if (!checkTipoMateriale(tipoMateriale)) valid = false;

        const tipoGadget = document.getElementById("tipoGadget");
        if (!checkTipoGadget(tipoGadget)) valid = false;
    } else if (tipo.value === "Cabinato") {
        const tipoSistemaArcade = document.getElementById("tipoSistemaArcade");
        if (!checkTipoSistemaArcade(tipoSistemaArcade)) valid = false;

        const dimensioniCm = document.getElementById("dimensioniCm");
        if (!checkDimensioniCm(dimensioniCm)) valid = false;
    }

    // Invio della form se tutti i campi sono validi
    if (valid) {
        obj.submit();
    }
}


// ==========================================
// 4. UTILITY GESTIONE ERRORI DOM
// ==========================================

function addError(obj, errore) {
    
    const errorSpan = document.getElementById("err-" + obj.id);
    
    if (errorSpan) {
        errorSpan.textContent = errore;
        errorSpan.style.display = "block";
    }

    obj.classList.add("input-error-style"); 
}

function removeError(obj) {
    const errorSpan = document.getElementById("err-" + obj.id);
    
    if (errorSpan) {
        errorSpan.textContent = "";
        errorSpan.style.display = "none";
    }
    obj.classList.remove("input-error-style");
}