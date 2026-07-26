async function rimuovi(button){
	
	const contextPath = button.dataset.contextPath;
	const idProdotto = button.dataset.idProdotto;
	
	const params = new URLSearchParams();
	params.append("idProdotto", idProdotto);
	
	const url = `${contextPath}/RimuoviDalCarrello`;
	
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
			
			const divisore = document.getElementById(`divider-${idProdotto}`);
			if (divisore) {
				divisore.remove();
			}
			
			const rigaRiepilogo = document.getElementById(`summary-${idProdotto}`);
		    if (rigaRiepilogo) {
		        rigaRiepilogo.remove();
		    }
			
			const spanTotale = document.getElementById("cart-total-price");
		    if (spanTotale && data.nuovoTotale !== undefined) {
		        spanTotale.innerHTML = `${Number(data.nuovoTotale).toFixed(2)} &euro;`;
		    }

			mostraPopup(data.messaggio, "#d4edda", "#155724", "#c3e6cb");
			
			const elementiRimasti = document.querySelectorAll('.prodotto-item');
			if (elementiRimasti.length === 0) {
			    const cartLayout = document.querySelector('.cart-layout');
			    if (cartLayout) {
			        cartLayout.outerHTML = `
			            <div class="carrello-vuoto">
			                <img src="${contextPath}/resources/img/sonicPensa.gif" alt="Sonic sta aspettando i tuoi prodotti" class="empty-cart-img">
			                <p>Il tuo carrello è attualmente vuoto.</p>
			                <a href="${contextPath}/Catalogo">Torna allo shopping</a>
			            </div>`;
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