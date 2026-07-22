function toggleMenu() {
    const sidebar = document.getElementById("mobileSidebar");
    if (sidebar) {
        sidebar.classList.toggle("open");
    }
}

async function suggerimenti() {
	const searchbar = document.getElementById("searchbar");
	const box = document.getElementById("box-suggerimenti");
	
	const query = searchbar.value.trim();
	
	if(query.length < 2) {
        box.innerHTML = "";
        box.style.display = "none";
        return;
    }
	
	const params = new URLSearchParams();
	
	params.append("q", query);
	params.append("ajax", true);

	const contextPath = searchbar.dataset.contextPath || '';
	
	const url = `${contextPath}/RicercaProdotto?${params}`;
	
	try{
		const r = await fetch(url);
		
		if(!r.ok)
			throw new Error(r.status);
		
		const prodotti = await r.json();
		
		box.innerHTML = "";
		
		if(prodotti.length === 0){
			const empty = document.createElement("div");
			empty.className = "suggerimento-item empty";
			empty.textContent = "Nessun risultato trovato";
			box.appendChild(empty);
			box.style.display = "block";
			return;
		}
		
		const maxSugg = Math.min(prodotti.length, 5);
		
		for(let i = 0; i < maxSugg; i++){
			const p = prodotti[i];
			
			const a = document.createElement("a");
			a.className = "suggerimento-item";
			a.textContent = p.titolo;
			a.href = `${contextPath}/DettagliProdotto?idProdotto=${p.idProdotto}`;
			
			box.appendChild(a);
		}
		
		box.style.display = "block";
	}
	catch(err){
		console.log(err);
	}
}

document.addEventListener("DOMContentLoaded", function() {
    const searchbar = document.getElementById("searchbar");
    const box = document.getElementById("box-suggerimenti");

    if (!searchbar || !box) return;

    searchbar.addEventListener("blur", function() {
        setTimeout(function() {
            box.style.display = "none";
        }, 150);
    });

    searchbar.addEventListener("focus", function() {
        if (searchbar.value.trim().length >= 2 && box.children.length > 0) {
            box.style.display = "block";
        }
    });
});