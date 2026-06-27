package model.dettagliOrdine;

public class DettagliOrdineBean {
    private int idOrdine;
    private ProdottoBean prodotto;
    private float prezzoVenditaStorico;

    public int getIdOrdine() { return idOrdine; }
    public void setIdOrdine(int idOrdine) { this.idOrdine = idOrdine; }

    public ProdottoBean getProdotto() { return prodotto; }
    public void setProdotto(ProdottoBean prodotto) { this.prodotto = prodotto; }

    public float getPrezzoVenditaStorico() { return prezzoVenditaStorico; }
    public void setPrezzoVenditaStorico(float prezzoVenditaStorico) { this.prezzoVenditaStorico = prezzoVenditaStorico; }
}