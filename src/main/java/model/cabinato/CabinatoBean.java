package model.cabinato;

import java.io.Serializable;

import model.prodotto.ProdottoBean;

public class CabinatoBean extends ProdottoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tipoSistemaArcade;
    private String dimensioniCm;

    public String getTipoSistemaArcade() {
        return tipoSistemaArcade;
    }

    public void setTipoSistemaArcade(String tipoSistemaArcade) {
        this.tipoSistemaArcade = tipoSistemaArcade;
    }

    public String getDimensioniCm() {
        return dimensioniCm;
    }

    public void setDimensioniCm(String dimensioniCm) {
        this.dimensioniCm = dimensioniCm;
    }
}