package model.dettagliProdotto;

public class DettagliCabinatoBean extends DettagliProdottoBean {
	private String tipoSistemaArcade;
    private String dimensioniCm;
    
    public String getTipoSistemaArcade() { return tipoSistemaArcade; }
    public void setTipoSistemaArcade(String tipoSistemaArcade) { this.tipoSistemaArcade = tipoSistemaArcade; }

    public String getDimensioniCm() { return dimensioniCm; }
    public void setDimensioniCm(String dimensioniCm) { this.dimensioniCm = dimensioniCm; }
}