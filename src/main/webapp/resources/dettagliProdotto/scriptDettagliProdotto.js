async function aggiungiAllaWishlist(buttonElement){
	
	if(buttonElement.disabled === true)
		return;
	
	buttonElement.disabled = true;
	
	const idProdotto = buttonElement.dataset.idProdotto;
	
	const contextPath = buttonElement.dataset.contextPath || "";

	const params = new URLSearchParams();
    params.append("idProdotto", idProdotto);
	
	const url = `${contextPath}/AggiungiAllaWishlist`;
	
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
		
		if(data.esito){
			const testoOG = buttonElement.innerHTML;
			
			buttonElement.innerHTML = data.messaggio;
			buttonElement.classList.add("btn-aggiuntoW");
			
			setTimeout(() => {
				buttonElement.innerHTML = testoOG;
				buttonElement.classList.remove("btn-aggiuntoW");
				buttonElement.disabled = false;
			}, 1500);
		}
		else{
			const testoOG = buttonElement.innerHTML;
			
			buttonElement.innerHTML = data.messaggio;
			buttonElement.classList.add("btn-errorW");
			
			setTimeout(() => {
				buttonElement.innerHTML = testoOG;
				buttonElement.classList.remove("btn-errorW");
				buttonElement.disabled = false;
			}, 1500);
		}
	}
	catch(err){
		console.log(err);
	}
}