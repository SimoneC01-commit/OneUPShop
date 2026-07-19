package model.console;

import java.io.Serializable;

import model.prodotto.ProdottoBean;

public class ConsoleBean extends ProdottoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private String modelloSpecifico;

    public String getModelloSpecifico() {
        return modelloSpecifico;
    }

    public void setModelloSpecifico(String modelloSpecifico) {
        this.modelloSpecifico = modelloSpecifico;
    }
}