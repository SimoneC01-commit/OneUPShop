async function aggiungiAlCarrello(buttonElement){
	
	if(buttonElement.disabled === true)
		return;
	
	buttonElement.disabled = true;
	
	const idProdotto = buttonElement.dataset.idProdotto;
	
	const contextPath = buttonElement.dataset.contextPath || '';

	const params = new URLSearchParams();
    params.append("idProdotto", idProdotto);
	
	const url = `${contextPath}/AggiungiAlCarrello`;
	
	try{
		const r = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: params
        });
		
		if(!r.ok){
			throw new Error(r.status);
		}
		
		const data = await r.json();
		
		if(data){
			const testoOG = buttonElement.innerHTML;
			
			buttonElement.innerHTML = "Aggiunto al carrello!";
			buttonElement.classList.add("btn-aggiunto");
			
			setTimeout(() => {
				buttonElement.innerHTML = testoOG;
				buttonElement.classList.remove("btn-aggiunto");
				buttonElement.disabled = false;
			}, 1500);
		}
		else{
			buttonElement.disabled = false;
		}
	}
	catch(err){
		console.log(err);
	}
}