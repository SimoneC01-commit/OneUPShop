package model.admin.elencoProdotti;

import java.math.BigDecimal;
import java.sql.Date;

public class ProdottoElencoBean {
	
	private int idProdotto;
    private String titolo;
    private String descrizione;
    private int annoRilascio;
    private byte[] fotoBlob;
    private String azienda;
    private String tipo;
    private BigDecimal prezzoAcquisto;
    private BigDecimal prezzoAttuale;
    private Date dataAggiunta;
    private String stato;
    private String noteDifetti;
    private boolean disponibile;
    private int iva;
    
    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getAnnoRilascio() {
        return annoRilascio;
    }

    public void setAnnoRilascio(int annoRilascio) {
        this.annoRilascio = annoRilascio;
    }

    public byte[] getFotoBlob() {
        return fotoBlob;
    }

    public void setFotoBlob(byte[] fotoBlob) {
        this.fotoBlob = fotoBlob;
    }

    public String getAzienda() {
        return azienda;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPrezzoAcquisto() {
        return prezzoAcquisto;
    }

    public void setPrezzoAcquisto(BigDecimal prezzoAcquisto) {
        this.prezzoAcquisto = prezzoAcquisto;
    }

    public BigDecimal getPrezzoAttuale() {
        return prezzoAttuale;
    }

    public void setPrezzoAttuale(BigDecimal prezzoAttuale) {
        this.prezzoAttuale = prezzoAttuale;
    }

    public Date getDataAggiunta() {
        return dataAggiunta;
    }

    public void setDataAggiunta(Date dataAggiunta) {
        this.dataAggiunta = dataAggiunta;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getNoteDifetti() {
        return noteDifetti;
    }

    public void setNoteDifetti(String noteDifetti) {
        this.noteDifetti = noteDifetti;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }
}