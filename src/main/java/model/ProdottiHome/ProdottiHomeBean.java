package model.ProdottiHome;

public class ProdottiHomeBean {
	private String titolo;
	private byte[] copertina;
	private float prezzo;
	
	public String getTitolo() {
		return titolo;
	}
	
	public void setTitolo(String t) {
		this.titolo = t;
	}

	public byte[] getCopertina() {
		return copertina;
	}
	
	public void setCopertina(byte[] c) {
		this.copertina = c;
	}
	
	public float getPrezzo() {
		return prezzo;
	}
	
	public void setPrezzo(float p) {
		this.prezzo = p;
	}
}