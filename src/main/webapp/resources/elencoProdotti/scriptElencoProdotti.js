let idProdotto = null;

function annulla(){
	const elem = document.getElementById("dlg-cancellazione");
	
	if(elem){
		elem.close();
	}
	
	idProdotto = null;
}

function confermaEliminazione(id){
	
	idProdotto = id;
	
	const elem = document.getElementById("dlg-cancellazione");
	if(elem){
		elem.showModal();
	}
}

async function elimina(obj){
	if(!idProdotto) return;
	
	const contextPath = obj.dataset.contextPath;
	
	const params = new URLSearchParams();
	params.append("idProdotto", idProdotto);
	
	const url = `${contextPath}/CancellaProdotto`;
	
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
			console.log(data.messaggio);
			
			const rigaTabella = document.getElementById(`prodotto-${idProdotto}`);
			if (rigaTabella) {
				rigaTabella.remove();
			}

			annulla();
			
			mostraPopup(data.messaggio, "#d4edda", "#155724", "#c3e6cb");
		}
		else{
			mostraPopup(data.messaggio, "#edd4d4", "#571515", "#e6c3c3");
		}
	}
	catch(err){
		console.log(err);
		alert("Si è verificato un errore di rete durante la cancellazione.");
	}
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

function modificaProdotto(id){
	idProdotto = id;
	
	window.location.href = `ModificaProdotto?idProdotto=${idProdotto}`;
}

document.addEventListener("DOMContentLoaded", () => {
    
    const searchInput = document.getElementById("adminFiltroTesto");
    const righeProdotti = document.querySelectorAll(".riga-prodotto");

    if (searchInput) {
        
        searchInput.addEventListener("input", function(e) {
            
            const termineRicerca = e.target.value.toLowerCase();

            righeProdotti.forEach(riga => {
                
                const colonnaTitolo = riga.querySelector(".titolo-prodotto");
                
                if (colonnaTitolo) {
                    const titolo = colonnaTitolo.textContent.toLowerCase();

                    if (titolo.includes(termineRicerca)) {
                        riga.style.display = "";
                    } else {
                        riga.style.display = "none";
                    }
                }
            });
        });
    }
});