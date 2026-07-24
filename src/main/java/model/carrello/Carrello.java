package model.carrello;

import java.math.BigDecimal;
import java.util.ArrayList;

import model.prodotto.ProdottoBean;

public class Carrello {
	private ArrayList<ProdottoBean> lista;

	public Carrello() {
		lista = new ArrayList<ProdottoBean>();
	}
	
	public ArrayList<ProdottoBean> getLista(){
		return lista;
	}
	
	public void aggiungiProdotto(ProdottoBean prodotto) {
	    for (ProdottoBean p : lista) {
	        if (p.getIdProdotto() == prodotto.getIdProdotto()) {
	            return; 
	        }
	    }
	    this.lista.add(prodotto);
	}
	
	public void rimuoviProdotto(int idProdotto) {
        lista.removeIf(p -> p.getIdProdotto() == idProdotto);
    }
	
	public void svuota() {
        lista.clear();
    }
	
	public BigDecimal getTotale() {
	    return lista.stream()
	                .map(p -> p.getPrezzoAttuale())
	                .reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public boolean contiene(int idProdotto) {
		return lista.stream()
					.map(p -> p.getIdProdotto())
					.anyMatch(p -> p == idProdotto);
	}
}
