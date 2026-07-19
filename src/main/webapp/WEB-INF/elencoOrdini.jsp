<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.ordine.OrdineBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestione Ordini - Amministrazione</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f6f9;
        }
        h1 {
            color: #333;
        }
        .error {
            color: red;
            background-color: #fde8e8;
            padding: 10px;
            border: 1px solid #f9b8b8;
            margin-bottom: 20px;
            max-width: 800px;
        }
        .table-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            max-width: 1200px;
            overflow-x: auto;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            text-align: left;
        }
        th, td {
            padding: 12px 15px;
            border-bottom: 1px solid #e0e0e0;
        }
        th {
            background-color: #343a40;
            color: white;
        }
        tr:hover {
            background-color: #f8f9fa;
        }
        .status {
            padding: 5px 10px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: bold;
            display: inline-block;
        }
        .status-elaborazione { background-color: #ffeeba; color: #856404; }
        .status-spedito { background-color: #b8daff; color: #004085; }
        .status-consegnato { background-color: #c3e6cb; color: #155724; }
        .status-annullato { background-color: #f5c6cb; color: #721c24; }
        
        .btn-delete {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 6px 12px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.2s;
        }
        .btn-delete:hover {
            background-color: #bd2130;
        }
        .btn-delete:disabled {
            background-color: #6c757d;
            opacity: 0.5;
            cursor: not-allowed;
        }
    </style>
</head>
<body>

    <h1>Elenco Ordini Ricevuti</h1>

    <% 
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) { 
    %>
        <div class="error"><%= errorMessage %></div>
    <% 
        } 
    %>

    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>ID Ordine</th>
                    <th>Utente</th>
                    <th>Data</th>
                    <th>Metodo Pagamento</th>
                    <th>Totale</th>
                    <th>Stato</th>
                    <th>Azioni</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    @SuppressWarnings("unchecked")
                    ArrayList<OrdineBean> ordini = (ArrayList<OrdineBean>) request.getAttribute("ordini");
                    if (ordini != null && !ordini.isEmpty()) {
                        for (OrdineBean ordine : ordini) {
                            String stato = ordine.getStatoOrdine();
                            String statusClass = "";
                            
                            if ("In elaborazione".equals(stato)) statusClass = "status-elaborazione";
                            else if ("Spedito".equals(stato)) statusClass = "status-spedito";
                            else if ("Consegnato".equals(stato)) statusClass = "status-consegnato";
                            else if ("Annullato".equals(stato)) statusClass = "status-annullato";
                %>
                <tr>
                    <td>#<%= ordine.getIdOrdine() %></td>
                    <td><%= ordine.getEmailUtente() %></td>
                    <td><%= ordine.getDataOrdine() %></td>
                    <td><%= ordine.getMetodoPagamento() %></td>
                    <td>&euro; <%= ordine.getTotaleOrdine() %></td>
                    <td>
                        <span class="status <%= statusClass %>"><%= stato %></span>
                    </td>
                    <td>
                        <form action="<%= request.getContextPath() %>/CancellaOrdine" method="POST" style="margin:0;" onsubmit="return confirm('Sei sicuro di voler cancellare questo ordine?');">
                            <input type="hidden" name="idOrdine" value="<%= ordine.getIdOrdine() %>">
                            <button type="submit" class="btn-delete" <%= !"In elaborazione".equals(stato) ? "disabled" : "" %>>
                                Cancella
                            </button>
                        </form>
                    </td>
                </tr>
                <% 
                        }
                    } else { 
                %>
                <tr>
                    <td colspan="7" style="text-align: center;">Nessun ordine presente nel sistema.</td>
                </tr>
                <% 
                    } 
                %>
            </tbody>
        </table>
    </div>

</body>
</html>