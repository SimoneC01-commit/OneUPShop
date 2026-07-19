package model.wishlist;

import java.io.Serializable;
import java.sql.Timestamp;
import model.prodotto.ProdottoBean;

public class WishlistBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String emailUtente;
    private ProdottoBean prodotto;
    private Timestamp dataInserimento;

    public String getEmailUtente() {
        return emailUtente;
    }

    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }

    public ProdottoBean getProdotto() {
        return prodotto;
    }

    public void setProdotto(ProdottoBean prodotto) {
        this.prodotto = prodotto;
    }

    public Timestamp getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(Timestamp dataInserimento) {
        this.dataInserimento = dataInserimento;
    }
}