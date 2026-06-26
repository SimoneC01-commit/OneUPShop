package model.autentificazione;

import java.math.BigDecimal;

public class UtenteBean {

	private int idUtente;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String telefono;
    private String indirizzo;
    private String ruolo;
    private BigDecimal saldoWallet;
    private String metodoPagamentoDefault;
    
    public int getIdUtente() { return idUtente; }
    public void setIdUtente(int idUtente) { this.idUtente = idUtente; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }

    public BigDecimal getSaldoWallet() { return saldoWallet; }
    public void setSaldoWallet(BigDecimal saldoWallet) { this.saldoWallet = saldoWallet; }

    public String getMetodoPagamentoDefault() { return metodoPagamentoDefault; }
    public void setMetodoPagamentoDefault(String metodoPagamentoDefault) { this.metodoPagamentoDefault = metodoPagamentoDefault; }
}