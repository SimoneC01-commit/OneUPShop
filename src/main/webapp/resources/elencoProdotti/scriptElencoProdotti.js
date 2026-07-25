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
			
			const response = document.getElementById("response");
			response.style.display = "block";
			response.style.backgroundColor = "#d4edda";
			response.style.border = "1px solid #c3e6cb";
			response.style.color = "#155724";
			response.innerHTML = data.messaggio;
		}
		else{
			const response = document.getElementById("response");
			response.style.display = "block";
			response.style.backgroundColor = "#fde8e8";
			response.style.border = "1px solid #f9b8b8"
			response.style.color = "red";
			response.innerHTML = data.messaggio;
		}
	}
	catch(err){
		console.log(err);
		alert("Si è verificato un errore di rete durante la cancellazione.");
	}
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