let idOrdine = null;

function annulla(){
	const elem = document.getElementById("dlg-cancellazione");
	
	if(elem){
		elem.close();
	}
	
	idOrdine = null;
}

function confermaEliminazione(id, button){
	
	const stato = button.dataset.stato;
	
	if("In elaborazione" !== stato){
		return;
	}
	
	if(!idOrdine){
		idOrdine = id;
	}
	
	const elem = document.getElementById("dlg-cancellazione");
	if(elem){
		elem.showModal();
	}
}

function elimina(button){
	
	if(!idOrdine){
		return;
	}
	
	const contextPath = button.dataset.contextPath;
	
	const url = `${contextPath}/CancellazioneOrdine`;
	
	const form = document.createElement("form");
	form.method = "POST";
	form.action = url;
	
	const inputNascosto = document.createElement('input');
    inputNascosto.type = 'hidden';
    inputNascosto.name = "idOrdine";
    inputNascosto.value = idOrdine;
    form.appendChild(inputNascosto);
	
	document.body.appendChild(form);
	form.submit();
}