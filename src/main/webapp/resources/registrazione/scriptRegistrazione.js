function checkName(inputtxt){
	var nameRegex = /^[A-Z][a-z]*(?: [A-Z][a-z]*)*$/;
	if(inputtxt.value.match(nameRegex)){
		removeError(inputtxt);
		return true;
	}

	addError(inputtxt, "Il nome deve contentere solo lettere ed iniziare con una lettera MAIUSCOLA");
	return false;
}

function checkSurname(inputtxt){
	var nameRegex = /^[A-Z][a-z]*(?: [A-Z][a-z]*)*$/;
	if(inputtxt.value.match(nameRegex)){
		removeError(inputtxt);
		return true;
	}

	addError(inputtxt, "Il cognome può contentere solo numeri e lettere");
	return false;
}

function checkEmail(inputtxt){
	var emailRegex = /^[a-zA-Z0-9]+([.-][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([.-][a-zA-Z0-9]+)*\.[a-zA-Z]{2,}$/;
	if(inputtxt.value.match(emailRegex)){
		removeError(inputtxt);
		return true;
	}
	
	addError(inputtxt, "Email non valida");
	return false;
}

function checkPassword(inputtxt){
	var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]).{8,}$/;
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
	var valid = true;
	
	var name = document.getElementById("nome");
	if(!checkName(name)){
		valid = false;
	}
	
	var surname = document.getElementById("cognome");
	if(!checkSurname(surname)){
		valid = false;
	}
	
	var email = document.getElementById("email");
	if(!checkEmail(email)){
		valid = false;
	}
	
	var password = document.getElementById("password");
	if(!checkPassword(password)){
		valid = false;
	}
	
	var confermaPassword = document.getElementById("confermaPassword");
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