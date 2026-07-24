function puliziaFiltri(event, form){
	event.preventDefault(); 
	
	const params = new URLSearchParams(new FormData(form));
	
	for(const [key, value] of Array.from(params.entries())){
		if(!value || value == ""){
			params.delete(key);
		}
	}
	const actionUrl = form.getAttribute('action')
	
	window.location.href = `${actionUrl}?${params}`;
}