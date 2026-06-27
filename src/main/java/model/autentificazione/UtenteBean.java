package model.autentificazione;

import java.math.BigDecimal;

public class UtenteBean {

    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String ruolo;
    private BigDecimal saldoWallet;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }

    public BigDecimal getSaldoWallet() { return saldoWallet; }
    public void setSaldoWallet(BigDecimal saldoWallet) { this.saldoWallet = saldoWallet; }
}