package model.dettagliProdotto;

public class DettagliGadgetBean extends DettagliProdottoBean{
	private String tipoMateriale;
    private String tipoGadget;
    
    public String getTipoMateriale() { return tipoMateriale; }
    public void setTipoMateriale(String tipoMateriale) { this.tipoMateriale = tipoMateriale; }
    
    public String getTipoGadget() { return tipoGadget; }
    public void setTipoGadget(String tipoGadget) { this.tipoGadget = tipoGadget; }
}