package model.ordine;

import java.sql.Timestamp;

public class OrdineBean {
    private int idOrdine;
    private String emailUtente;
    private Timestamp dataOrdine;
    private String statoOrdine;
    private float totaleOrdine;
    private String indirizzoSpedizioneStorico;
    private String telefono;
    private String metodoPagamento;
    
    public int getIdOrdine() { return idOrdine; }
    public void setIdOrdine(int idOrdine) { this.idOrdine = idOrdine; }

    public String getEmailUtente() { return emailUtente; }
    public void setEmailUtente(String emailUtente) { this.emailUtente = emailUtente; }

    public Timestamp getDataOrdine() { return dataOrdine; }
    public void setDataOrdine(Timestamp dataOrdine) { this.dataOrdine = dataOrdine; }

    public String getStatoOrdine() { return statoOrdine; }
    public void setStatoOrdine(String statoOrdine) { this.statoOrdine = statoOrdine; }

    public float getTotaleOrdine() { return totaleOrdine; }
    public void setTotaleOrdine(float totaleOrdine) { this.totaleOrdine = totaleOrdine; }

    public String getIndirizzoSpedizioneStorico() { return indirizzoSpedizioneStorico; }
    public void setIndirizzoSpedizioneStorico(String indirizzoSpedizioneStorico) { this.indirizzoSpedizioneStorico = indirizzoSpedizioneStorico; }
    
	public String getTelefono() { return telefono; }
	public void setTelefono(String telefono) { this.telefono = telefono; }
	
	public String getMetodoPagamento() { return metodoPagamento; }
	public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }
}