package model.carrello;

import java.math.BigDecimal;
import java.util.ArrayList;

import model.prodotto.ProdottoBean;

public class Carrello {
	private ArrayList<ProdottoBean> lista;
	private int numElem;

	public Carrello() {
		lista = new ArrayList<ProdottoBean>();
		numElem = 0;
	}
	
	public ArrayList<ProdottoBean> getLista(){
		return lista;
	}
	
	public int getNumElem(){
		return numElem;
	}
	
	public void aggiungiProdotto(ProdottoBean prodotto) {
	    for (ProdottoBean p : lista) {
	        if (p.getIdProdotto() == prodotto.getIdProdotto()) {
	            return; 
	        }
	    }
	    this.lista.add(prodotto);
	    this.numElem++;
	}
	
	public void rimuoviProdotto(int idProdotto) {
        lista.removeIf(p -> p.getIdProdotto() == idProdotto);
        numElem--;
    }
	
	public void svuota() {
        lista.clear();
        numElem = 0;
    }
	
	public BigDecimal getTotale() {
	    return lista.stream()
	                .map(p -> p.getPrezzoAttuale())
	                .reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
