function annulla(){
	const elem = document.getElementById("modalConferma");
	
	elem.close();
}

function confermaEliminazione(){
	const elem = document.getElementById("modalConferma");
	
	elem.showModal();
}

async function elimina(idProdotto){
	console.log(idProdotto);
}