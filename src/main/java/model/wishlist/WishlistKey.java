package model.wishlist;

import java.io.Serializable;

public class WishlistKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private String emailUtente;
    private int idProdotto;

    public WishlistKey() {}

    public WishlistKey(String emailUtente, int idProdotto) {
        this.emailUtente = emailUtente;
        this.idProdotto = idProdotto;
    }

    public String getEmailUtente() {
        return emailUtente;
    }

    public void setEmailUtente(String emailUtente) {
        this.emailUtente = emailUtente;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }
}