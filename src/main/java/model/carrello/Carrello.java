package model.carrello;

import java.util.ArrayList;

public class Carrello {
	private ArrayList<ProdottoCarrelloBean> lista;

	public Carrello() {
		lista = new ArrayList<ProdottoCarrelloBean>();
	}
	
	public ArrayList<ProdottoCarrelloBean> getLista(){
		return lista;
	}
	
	public void aggiungiProdotto(ProdottoCarrelloBean prodotto) {
	    for (ProdottoCarrelloBean p : lista) {
	        if (p.getId() == prodotto.getId()) {
	            return; 
	        }
	    }
	    this.lista.add(prodotto);
	}
	
	public void rimuoviProdotto(int idProdotto) {
        lista.removeIf(p -> p.getId() == idProdotto);
    }
	
	public void svuota() {
        lista.clear();
    }
	
	public float getTotale() {
	    return lista.stream()
	                .map(p -> p.getPrezzo())
	                .reduce(0.0f, Float::sum);
	}
}
