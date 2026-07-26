function checkTitolo(inputtxt) {
    if (!inputtxt) return true;
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
    if (!inputtxt) return true;
    const val = inputtxt.value.trim();
    if (val.length < 10 || val.length > 1000) {
        addError(inputtxt, "La descrizione deve contenere tra 10 e 1000 caratteri.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkAnnoRilascio(inputtxt) {
    if (!inputtxt) return true;
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
    if (!inputtxt) return true;
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
    if (!inputtxt) return true;
    if (!inputtxt.value) {
        addError(inputtxt, "Seleziona lo stato del prodotto.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkNoteDifetti(inputtxt) {
    if (!inputtxt) return true;
    const statoElem = document.getElementById("nuovoStato");
    if (statoElem && statoElem.value === "Usato") {
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
    if (!inputtxt) return true;
    const val = parseFloat(inputtxt.value);
    if (isNaN(val) || val < 0 || val > 100) {
        addError(inputtxt, "Inserisci una percentuale IVA valida tra 0 e 100.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkPrezzoAttuale(inputtxt) {
    if (!inputtxt) return true;
    const val = parseFloat(inputtxt.value);
    if (isNaN(val) || val <= 0) {
        addError(inputtxt, "Inserisci un prezzo di vendita valido maggiore di 0.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkFoto(inputtxt) {
    if (!inputtxt) return true;
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

function checkSviluppatore(inputtxt) {
    if (!inputtxt) return true;
    const val = inputtxt.value.trim();
    if (val.length < 2 || val.length > 100) {
        addError(inputtxt, "Lo sviluppatore deve contenere tra 2 e 100 caratteri.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkModelloSpecifico(inputtxt) {
    if (!inputtxt) return true;
    const val = inputtxt.value.trim();
    if (val.length < 2 || val.length > 100) {
        addError(inputtxt, "Il modello specifico deve contenere tra 2 e 100 caratteri.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkTipoMateriale(inputtxt) {
    if (!inputtxt) return true;
    const val = inputtxt.value.trim();
    if (val.length < 2 || val.length > 100) {
        addError(inputtxt, "Il tipo di materiale deve contenere tra 2 e 100 caratteri.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkTipoGadget(inputtxt) {
    if (!inputtxt) return true;
    const val = inputtxt.value.trim();
    if (val.length < 2 || val.length > 100) {
        addError(inputtxt, "Il tipo di gadget deve contenere tra 2 e 100 caratteri.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkTipoSistemaArcade(inputtxt) {
    if (!inputtxt) return true;
    const val = inputtxt.value.trim();
    if (val.length < 2 || val.length > 100) {
        addError(inputtxt, "Il sistema arcade deve contenere tra 2 e 100 caratteri.");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function checkDimensioniCm(inputtxt) {
    if (!inputtxt) return true;
    const val = inputtxt.value.trim();
    const dimensioniRegex = /^\d{1,3}\s*x\s*\d{1,3}\s*x\s*\d{1,3}$/i;
    if (!dimensioniRegex.test(val)) {
        addError(inputtxt, "Formato non valido. Usa il formato LxPxA (es. 60x80x170).");
        return false;
    }
    removeError(inputtxt);
    return true;
}

function gestisciStato() {
    const statoElem = document.getElementById("nuovoStato");
    const containerNote = document.getElementById("containerNoteDifetti");
    const inputNote = document.getElementById("nuoveNoteDifetti");

    if (!statoElem || !containerNote || !inputNote) return;

    if (statoElem.value === "Usato") {
        containerNote.style.display = "block";
        inputNote.required = true;
    } else {
        containerNote.style.display = "none";
        inputNote.required = false;
        removeError(inputNote);
    }
}

function validate(obj) {
    let valid = true;

    const titolo = document.getElementById("nuovoTitolo");
    if (titolo && !checkTitolo(titolo)) valid = false;

    const descrizione = document.getElementById("nuovaDescrizione");
    if (descrizione && !checkDescrizione(descrizione)) valid = false;

    const annoRilascio = document.getElementById("nuovoAnnoRilascio");
    if (annoRilascio && !checkAnnoRilascio(annoRilascio)) valid = false;

    const azienda = document.getElementById("nuovaAzienda");
    if (azienda && !checkAzienda(azienda)) valid = false;

    const stato = document.getElementById("nuovoStato");
    if (stato && !checkStato(stato)) valid = false;

    const noteDifetti = document.getElementById("nuoveNoteDifetti");
    if (stato && stato.value === "Usato") {
        if (noteDifetti && !checkNoteDifetti(noteDifetti)) valid = false;
    } else if (noteDifetti) {
        removeError(noteDifetti);
    }

    const iva = document.getElementById("nuovaIva");
    if (iva && !checkIva(iva)) valid = false;

    const prezzoAttuale = document.getElementById("nuovoPrezzoAttuale");
    if (prezzoAttuale && !checkPrezzoAttuale(prezzoAttuale)) valid = false;

    const foto = document.getElementById("nuovaFoto");
    if (foto && !checkFoto(foto)) valid = false;
	
    const sviluppatore = document.getElementById("nuovoSviluppatore");
    if (sviluppatore && !checkSviluppatore(sviluppatore)) valid = false;

    const modelloSpecifico = document.getElementById("nuovoModelloSpecifico");
    if (modelloSpecifico && !checkModelloSpecifico(modelloSpecifico)) valid = false;

    const tipoMateriale = document.getElementById("nuovoTipoMateriale");
    if (tipoMateriale && !checkTipoMateriale(tipoMateriale)) valid = false;

    const tipoGadget = document.getElementById("nuovoTipoGadget");
    if (tipoGadget && !checkTipoGadget(tipoGadget)) valid = false;

    const tipoSistemaArcade = document.getElementById("nuovoTipoSistemaArcade");
    if (tipoSistemaArcade && !checkTipoSistemaArcade(tipoSistemaArcade)) valid = false;

    const dimensioniCm = document.getElementById("nuoveDimensioniCm");
    if (dimensioniCm && !checkDimensioniCm(dimensioniCm)) valid = false;
	
    if (valid) {
        obj.submit();
    }
}

function addError(obj, errore) {
    removeError(obj);

    const elem = document.createElement("div");
    elem.textContent = errore;
    elem.classList.add("error");
    obj.after(elem);
}

function removeError(obj) {
    if (obj.nextElementSibling?.classList.contains("error")) {
        const elem = obj.nextElementSibling;
        elem.remove();
    }
}

document.addEventListener("DOMContentLoaded", function () {
    gestisciStato();
});