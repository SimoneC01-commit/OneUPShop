package model.admin.elencoProdotti;

public class CabinatoBean extends ProdottoBean {
	
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