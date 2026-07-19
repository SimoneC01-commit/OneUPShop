package model.ordine;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrdineBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idOrdine;
    private String emailUtente;
    private Timestamp dataOrdine;
    private String statoOrdine;
    private BigDecimal totaleOrdine;
    private String indirizzoSpedizione;
    private String telefono;
    private String metodoPagamento;

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public String getEmailUtente() {
        return emailUtente;
    }

    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }

    public Timestamp getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(Timestamp dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public String getStatoOrdine() {
        return statoOrdine;
    }

    public void setStatoOrdine(String statoOrdine) {
        this.statoOrdine = statoOrdine;
    }

    public BigDecimal getTotaleOrdine() {
        return totaleOrdine;
    }

    public void setTotaleOrdine(BigDecimal totaleOrdine) {
        this.totaleOrdine = totaleOrdine;
    }

    public String getIndirizzoSpedizione() {
        return indirizzoSpedizione;
    }

    public void setIndirizzoSpedizione(String indirizzoSpedizione) {
        this.indirizzoSpedizione = indirizzoSpedizione;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }
}