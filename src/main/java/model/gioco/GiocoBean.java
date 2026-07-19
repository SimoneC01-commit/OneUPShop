package model.gioco;

import java.io.Serializable;

import model.prodotto.ProdottoBean;

public class GiocoBean extends ProdottoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sviluppatore;

    public String getSviluppatore() {
        return sviluppatore;
    }

    public void setSviluppatore(String sviluppatore) {
        this.sviluppatore = sviluppatore;
    }
}