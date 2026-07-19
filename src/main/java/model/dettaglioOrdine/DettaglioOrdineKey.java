package model.dettaglioOrdine;

import java.io.Serializable;

public class DettaglioOrdineKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idOrdine;
    private int idProdotto;

    public DettaglioOrdineKey() {}

    public DettaglioOrdineKey(int idOrdine, int idProdotto) {
        this.idOrdine = idOrdine;
        this.idProdotto = idProdotto;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }
}