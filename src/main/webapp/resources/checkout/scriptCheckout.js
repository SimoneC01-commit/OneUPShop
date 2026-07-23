function checkVia(inputtxt){
	const viaRegex = /^[a-zA-ZàèéìòùÀÈÉÌÒÙ'’.\s-]+,\s*\d+(?:\/[a-zA-Z0-9]+)?$/;
	const val = inputtxt.value.trim();
	
	if (val.length < 5 || val.length > 100) {
		addError(inputtxt, "L'indirizzo deve contenere tra 5 e 100 caratteri.");
		return false;
	}
	
	if(inputtxt.value.match(viaRegex)){
		removeError(inputtxt);
		return true;
	}

	addError(inputtxt, "La via deve contenere un nome valido separato da virgola e seguito da numero civico.");
	return false;
}

function checkCAP(inputtxt){
	const capRegex = /^\d{5}$/;
	
	inputtxt.value = inputtxt.value.replace(/\D/g, '');
	
	if(inputtxt.value.match(capRegex)){
		removeError(inputtxt);
		return true;
	}

	addError(inputtxt, "CAP non valido.");
	return false;
}

function checkCitta(inputtxt){
	const cittaRegex = /^[a-zA-ZàèéìòùÀÈÉÌÒÙ'’\s-]{2,50}$/;
	if(inputtxt.value.match(cittaRegex)){
		removeError(inputtxt);
		return true;
	}

	addError(inputtxt, "Inserire un nome valido.");
	return false;
}

function checkTel(inputtxt){
	const telRegex = /^\+39\s\d{3}\s?\d{3}\s?\d{4}$/;
	if(inputtxt.value.match(telRegex)){
		removeError(inputtxt);
		return true;
	}

	addError(inputtxt, "Il numero di telefono deve iniziare per +39 e deve essere seguito dalle restanti cifre.");
	return false;
}

function validate(obj){
	let valid = true;

	const via = document.getElementById("via");
	if(!checkVia(via)){
		valid = false;
	}
	
	const cap = document.getElementById("cap");
	if(!checkCAP(cap)){
		valid = false;
	}
	
	const citta = document.getElementById("citta");
	if(!checkCitta(citta)){
		valid = false;
	}
	
	const tel = document.getElementById("telefono");
	if(!checkTel(tel)){
		valid = false;
	}
	
	if(valid){
		obj.submit();
	}
}

function addError(obj, errore){
	
	removeError(obj);
	
	const elem = document.createElement("div");
	elem.textContent = errore;
	elem.classList.add("error");
	obj.after(elem);
}

function removeError(obj){
	if(obj.nextElementSibling?.classList.contains("error")){
		const elem = obj.nextElementSibling;
		elem.remove();
	}
}