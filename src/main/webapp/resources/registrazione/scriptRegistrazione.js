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

function checkEmail(inputtxt){
	const val = inputtxt.value.trim();
	if (val.length > 100) {
		addError(inputtxt, "L'email non può superare i 100 caratteri.");
		return false;
	}

	const emailRegex = /^[a-zA-Z0-9]+([.-][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([.-][a-zA-Z0-9]+)*\.[a-zA-Z]{2,10}$/;
	if(emailRegex.test(val)){
		removeError(inputtxt);
		return true;
	}
	
	addError(inputtxt, "Email non valida");
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

async function validate(obj){
	let valid = true;
	
	const name = document.getElementById("nome");
	if(!checkName(name)){
		valid = false;
	}
	
	const surname = document.getElementById("cognome");
	if(!checkSurname(surname)){
		valid = false;
	}
	
	const email = document.getElementById("email");
	if(!checkEmail(email)){
		valid = false;
	}
	
	const password = document.getElementById("password");
	if(!checkPassword(password)){
		valid = false;
	}
	
	const confermaPassword = document.getElementById("confermaPassword");
	if(!checkConfermaPassword(password, confermaPassword)){
		valid = false;
	}
	
	if(valid){
		
		const contextPath = obj.dataset.contextPath || "";

		const params = new URLSearchParams();

		params.append("email", email.value);
		params.append("ajax", true);

		const url = `${contextPath}/Registrazione`;

		try{
			const r = await fetch(url, {
				method: "POST",
				headers: {
				                "Content-Type": "application/x-www-form-urlencoded"
				},
				body: params
			});
			
			if(!r.ok)
				throw new Error(r.status);
			
			const data = await r.json();
			
			if(data.esiste){
				addError(email, data.messaggio);
			}
			else{
				removeError(email);
				obj.submit();
			}
		}
		catch(err){
			console.log(err);
		}
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