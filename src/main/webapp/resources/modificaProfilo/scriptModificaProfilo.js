function toggleField(obj, inputIDS){
	inputIDS.forEach(id => {
		const elem = document.getElementById(id);
		
		elem.disabled = !obj.checked;
		
		if(!obj.checked)
			removeError(elem);
	});
}

function checkName(inputtxt){
	const val = inputtxt.value.trim();
	if (val.length < 2 || val.length > 100) {
		addError(inputtxt, "Il nome deve contenere tra 2 e 100 caratteri.");
		return false;
	}

	const nameRegex = /^[A-Z][a-z]*(?: [A-Z][a-z]*)*$/;
	if(nameRegex.test(val)){
		removeError(inputtxt);
		return true;
	}

	addError(inputtxt, "Il nome deve contentere solo lettere ed iniziare con una lettera MAIUSCOLA.");
	return false;
}

function checkSurname(inputtxt){
	const val = inputtxt.value.trim();
	if (val.length < 2 || val.length > 100) {
		addError(inputtxt, "Il cognome deve contenere tra 2 e 100 caratteri.");
		return false;
	}

	const surnameRegex = /^[A-Z][a-z]*(?: [A-Z][a-z]*)*$/;
	if(surnameRegex.test(val)){
		removeError(inputtxt);
		return true;
	}

	addError(inputtxt, "Il nome deve contentere solo lettere ed iniziare con una lettera MAIUSCOLA.");
	return false;
}

function checkPassword(inputtxt){
	const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]).{8,100}$/;
	if(inputtxt.value.match(passwordRegex)){
		removeError(inputtxt);
		return true;
	}

	addError(inputtxt, "La password deve contenere almeno una lettera MAIUSCOLA, una MINUSCOLA, un NUMERO e un CARATTERE SPECIALE");
	return false;
}

function checkConfermaPassword(pass, passRipetuta){
	if(pass.value === passRipetuta.value && pass.value !== ""){
		removeError(passRipetuta);
		return true;
	}

	addError(passRipetuta, "Le password non combaciano");
	return false;
}

function validate(obj){
	let valid = true;
	
	const name = document.getElementById("nuovoNome");
	if(!name.disabled && !checkName(name)){
		valid = false;
	}
	
	const surname = document.getElementById("nuovoCognome");
	if(!surname.disabled && !checkSurname(surname)){
		valid = false;
	}
	
	const password = document.getElementById("nuovaPassword");
	if(!password.disabled && !checkPassword(password)){
		valid = false;
	}
	
	const confermaPassword = document.getElementById("confermaNuovaPassword");
	if(!confermaPassword.disabled && !checkConfermaPassword(password, confermaPassword)){
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