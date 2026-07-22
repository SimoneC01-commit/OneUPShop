async function aggiungiAlCarrello(buttonElement){
	const idProdotto = buttonElement.dataset.idProdotto;
	
	const contextPath = buttonElement.dataset.contextPath || '';

	const params = new URLSearchParams();
    params.append("idProdotto", idProdotto);
	
	const url = `${contextPath}/AggiungiAlCarrello`;
	
	try{
		const r = await fetch(`${contextPath}/AggiungiAlCarrello`, {
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
		
		console.log(data);
		
	} 
	catch(err){
		console.log(err);
	}
}