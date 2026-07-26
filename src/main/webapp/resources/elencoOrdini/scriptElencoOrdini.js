let idOrdine = null;

function toggleDettagli(id){
	
	const rigaDettagli = document.getElementById("dettagli-" + id);
	
	if(rigaDettagli.style.display === "none"){
		rigaDettagli.style.display = "table-row";
	}
	else{
		rigaDettagli.style.display = "none";
	}
}

async function cambiaStato(select){
	const stato = select.value;
	const statoIniziale = select.dataset.stato;
	
	if(stato === statoIniziale)
		return;
	
	select.disabled = true;
	
	if(!idOrdine){
		idOrdine = select.dataset.idOrdine;
	}
	
	const contextPath = select.dataset.contextPath;
	
	const params = new URLSearchParams();
	params.append("idOrdine", idOrdine);
	params.append("statoOrdine", stato);
	
	const url = `${contextPath}/ModificaStatoOrdine`;
	
	try{
		const r = await fetch(url, {
			method: "post",
			headers: {
				"Content-type": "application/x-www-form-urlencoded"
			},
			body: params
		})
		
		if(!r.ok)
			throw new Error(r.status);
		
		const data = await r.json();
		
		if(data.esito){
			select.dataset.stato = data.nuovoStato;
			select.value = data.nuovoStato;
			
			mostraPopup(data.messaggio, "#d4edda", "#155724", "#c3e6cb");
			
			const btnDelete = select.closest("tr").querySelector(".btn-delete");
	        if(btnDelete) {
				btnDelete.disabled = (stato !== "In elaborazione");
			}
		}
		else{
			select.value = statoIniziale;
			            
            mostraPopup(data.messaggio, "#fde8e8", "red", "#f9b8b8");
		}
	}
	catch(err){
		console.log(err);
		alert("Si è verificato un errore di rete durante la modifica.");
		select.value = statoIniziale;
	}
	finally{
		select.disabled = false;
			
		idOrdine = null;
	}	
}

function annulla(){
	const elem = document.getElementById("dlg-cancellazione");
	
	if(elem){
		elem.close();
	}
	
	idOrdine = null;
}

function confermaEliminazione(id){
	
	if(!idOrdine){
		idOrdine = id;
	}
	
	const elem = document.getElementById("dlg-cancellazione");
	if(elem){
		elem.showModal();
	}
}

async function elimina(button){
	if(!idOrdine) return;
	
	const contextPath = button.dataset.contextPath;
	
	const params = new URLSearchParams();
	params.append("idOrdine", idOrdine);
	
	const url = `${contextPath}/CancellaOrdine`;
	
	try{
		const r = await fetch(url, {
			method: "post",
			headers: {
				"Content-Type": "application/x-www-form-urlencoded"
			},
			body: params
		})
		
		if(!r.ok)
			throw new Error(r.status);
		
		const data = await r.json();
		
		if(data.esito){
			const rigaTabella = document.getElementById(`ordine-${idOrdine}`);
			if (rigaTabella) {
				rigaTabella.remove();
			}

			annulla();
			
			mostraPopup(data.messaggio, "#d4edda", "#155724", "#c3e6cb");
			
			const righeRimaste = document.querySelectorAll('.main-order-row');
			if (righeRimaste.length === 0) {
			    const tableBody = document.getElementById('table-body');
			    if (tableBody) {
			        tableBody.innerHTML = `
						<tr>
	                        <td colspan="7">Nessun ordine presente nel sistema.</td>
	                    </tr>`;
			    }
			}
		}
		else{
			mostraPopup(data.messaggio, "#edd4d4", "#571515", "#e6c3c3");
		}
	}
	catch(err){
		console.log(err);
		alert("Si è verificato un errore di rete durante la cancellazione.");
	}
	
	idOrdine = null;
}

function mostraPopup(messaggio, coloreSfondo, coloreTesto, coloreBordo) {
    const response = document.getElementById("response");
    
    response.innerHTML = messaggio;
    response.style.backgroundColor = coloreSfondo;
    response.style.color = coloreTesto;
	response.style.border = `1px solid ${coloreBordo}`;
    
    response.style.display = "block";
    
    setTimeout(() => {
        response.style.display = "none";
    }, 3000);
}