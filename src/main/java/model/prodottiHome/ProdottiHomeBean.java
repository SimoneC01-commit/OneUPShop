package model.prodottiHome;

public class ProdottiHomeBean {
    private int ID;
    private String titolo;
    private byte[] copertina;
    private String tipo;
    private float prezzo;
    private int iva;
    private String stato;

    public ProdottiHomeBean() {
    }

    public int getId() {
        return ID;
    }

    public void setId(int ID) {
        this.ID = ID;
    }

    public String getTitolo() {
        return titolo;
    }
    
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public byte[] getCopertina() {
        return copertina;
    }
    
    public void setCopertina(byte[] copertina) {
        this.copertina = copertina;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public float getPrezzo() {
        return prezzo;
    }
    
    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }
    
    public int getIva() {
        return iva;
    }
    
    public void setIva(int iva) {
        this.iva = iva;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}