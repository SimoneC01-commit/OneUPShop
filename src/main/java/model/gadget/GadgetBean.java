package model.gadget;

import java.io.Serializable;

import model.prodotto.ProdottoBean;

public class GadgetBean  extends ProdottoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tipoMateriale;
    private String tipoGadget;

    public String getTipoMateriale() {
        return tipoMateriale;
    }

    public void setTipoMateriale(String tipoMateriale) {
        this.tipoMateriale = tipoMateriale;
    }

    public String getTipoGadget() {
        return tipoGadget;
    }

    public void setTipoGadget(String tipoGadget) {
        this.tipoGadget = tipoGadget;
    }
}