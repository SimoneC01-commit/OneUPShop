package model.dettaglioOrdine;

import java.io.Serializable;
import java.math.BigDecimal;
import model.prodotto.ProdottoBean;

public class DettaglioOrdineBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idOrdine;
    private ProdottoBean prodotto;
    private BigDecimal prezzoVenditaStorico;
    private int ivaStorico;

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public ProdottoBean getProdotto() {
        return prodotto;
    }

    public void setProdotto(ProdottoBean prodotto) {
        this.prodotto = prodotto;
    }

    public BigDecimal getPrezzoVenditaStorico() {
        return prezzoVenditaStorico;
    }

    public void setPrezzoVenditaStorico(BigDecimal prezzoVenditaStorico) {
        this.prezzoVenditaStorico = prezzoVenditaStorico;
    }

    public int getIvaStorico() {
        return ivaStorico;
    }

    public void setIvaStorico(int ivaStorico) {
        this.ivaStorico = ivaStorico;
    }
}