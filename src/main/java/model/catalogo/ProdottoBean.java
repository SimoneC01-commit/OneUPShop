package model.catalogo;


public class ProdottoBean {
	
	private int ID;
	private String titolo;
	private byte[] foto;
	private float prezzo;

	public int getID() {
		return ID;
	}
	
	public void setID(int id) {
		this.ID = id;
	}
	
	public String getTitolo() {
	    return titolo;
	}

	public void setTitolo(String titolo) {
	    this.titolo = titolo;
	}
	
	public byte[] getFoto() {
	    return foto;
	}

	public void setFoto(byte[] foto) {
	    this.foto = foto;
	}

	public float getPrezzo() {
	    return prezzo;
	}

	public void setPrezzo(float prezzo) {
	    this.prezzo = prezzo;
	}
}