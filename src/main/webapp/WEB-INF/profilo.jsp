<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Importiamo la classe UtenteBean per poterla usare nel codice Java -->
<%@ page import="model.utente.UtenteBean" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Profilo Utente</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .profilo-card { border: 1px solid #ccc; padding: 20px; border-radius: 8px; max-width: 400px; }
        .admin-panel { margin-top: 20px; padding: 15px; background-color: #f8d7da; border-radius: 8px; }
        .btn { padding: 10px 15px; margin-right: 10px; background-color: #007bff; color: white; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; display: inline-block; }
        .btn:hover { background-color: #0056b3; }
    </style>
</head>
<body>

    <h1>Il tuo Profilo</h1>

    <div class="profilo-card">
        <!-- La Expression Language (EL) funziona nativamente senza librerie -->
        <p><strong>Nome:</strong> ${utente.nome}</p>
        <p><strong>Cognome:</strong> ${utente.cognome}</p>
        <p><strong>Email:</strong> ${utente.email}</p>
        <p><strong>Saldo Wallet:</strong> € ${utente.saldoWallet}</p>
    </div>

    <!-- Controllo in puro Java (Scriptlet) invece di JSTL -->
    <%
        // Recuperiamo l'oggetto dalla request
        UtenteBean utenteLoggato = (UtenteBean) session.getAttribute("utente");
        
        // Controlliamo che esista e che il ruolo sia Admin
        if (utenteLoggato != null && "Admin".equals(utenteLoggato.getRuolo())) {
    %>
        
        <div class="admin-panel">
            <h3>Pannello di Amministrazione</h3>
            <form action="ElencoProdotti" method="GET" style="display:inline;">
                <button type="submit" class="btn">Elenco prodotti</button>
            </form>
            <form action="ElencoOrdini" method="GET" style="display:inline;">
                <button type="submit" class="btn">Elenco ordini</button>
            </form>
        </div>
        
    <%
        } // Chiusura dell'if Java
    %>

</body>
</html>