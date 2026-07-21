<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="java.util.Base64" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Pannello Admin - Elenco Prodotti</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background-color: #f8f9fa; }
        .header-container { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .btn-group { display: flex; gap: 10px; }
        .btn-back { padding: 10px 15px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 4px; }
        .btn-back:hover { background-color: #5a6268; }
        
        /* Stile pulsante Aggiungi Prodotto */
        .btn-add { padding: 10px 15px; background-color: #28a745; color: white; text-decoration: none; border-radius: 4px; font-weight: bold; }
        .btn-add:hover { background-color: #218838; }
        
        /* NUOVO STILE: Pulsante Modifica */
        .btn-edit { padding: 6px 12px; background-color: #007bff; color: white; text-decoration: none; border-radius: 4px; font-size: 0.9em; font-weight: bold; display: inline-block; }
        .btn-edit:hover { background-color: #0056b3; }

        /* Stile pulsante Cancella attivo */
        .btn-delete { padding: 6px 12px; background-color: #dc3545; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 0.9em; font-weight: bold; }
        .btn-delete:hover { background-color: #c82333; }
        
        /* Pulsante Cancella disabilitato (prodotto non disponibile) */
        .btn-delete:disabled { background-color: #6c757d; color: #e9ecef; cursor: not-allowed; opacity: 0.65; }
        
        /* Contenitore per affiancare i bottoni delle azioni */
        .action-group { display: flex; gap: 6px; align-items: center; }

        table { width: 100%; border-collapse: collapse; background: white; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }
        th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #eee; }
        th { background-color: #343a40; color: white; }
        tr:hover { background-color: #f1f3f5; }
        
        .img-preview { width: 50px; height: 50px; object-fit: cover; border-radius: 4px; border: 1px solid #ddd; }
        .no-img { font-size: 0.85em; color: #888; font-style: italic; }
        
        .badge { padding: 4px 8px; border-radius: 4px; font-size: 0.85em; font-weight: bold; display: inline-block; }
        .badge-nuovo { background-color: #28a745; color: white; }
        .badge-usato { background-color: #ffc107; color: #212529; }
        .badge-disp { background-color: #17a2b8; color: white; }
        .badge-not-disp { background-color: #dc3545; color: white; }
    </style>
</head>
<body>

    <div class="header-container">
        <h1>Elenco Prodotti</h1>
        <div class="btn-group">
            <!-- Tasto Aggiungi Prodotto -->
            <a href="<%= request.getContextPath() %>/AggiungiProdotto" class="btn-add">+ Aggiungi Prodotto</a>
            <!-- Tasto Torna al Profilo -->
            <a href="<%= request.getContextPath() %>/Profilo" class="btn-back">Torna al Profilo</a>
        </div>
    </div>

    <%
        // Recuperiamo la lista dei prodotti passata dalla servlet
        ArrayList<ProdottoBean> prodotti = (ArrayList<ProdottoBean>) request.getAttribute("prodotti");
        
        if (prodotti == null || prodotti.isEmpty()) {
    %>
        <div style="background: white; padding: 20px; border-radius: 8px; text-align: center; border: 1px solid #ddd;">
            <p>Nessun prodotto presente nel catalogo.</p>
        </div>
    <%
        } else {
    %>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Foto</th>
                    <th>Titolo</th>
                    <th>Azienda</th>
                    <th>Tipo</th>
                    <th>Stato</th>
                    <th>Prezzo</th>
                    <th>Disponibile</th>
                    <th>IVA</th>
                    <th>Azioni</th> 
                </tr>
            </thead>
            <tbody>
                <%
                    for (ProdottoBean p : prodotti) {
                        // Converte i byte del blob in una stringa Base64 per visualizzare l'immagine
                        String base64Image = "";
                        if (p.getFotoBlob() != null && p.getFotoBlob().length > 0) {
                            base64Image = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(p.getFotoBlob());
                        }
                %>
                    <tr>
                        <td><%= p.getIdProdotto() %></td>
                        <td>
                            <% if (!base64Image.isEmpty()) { %>
                                <img src="<%= base64Image %>" alt="Anteprima" class="img-preview" />
                            <% } else { %>
                                <span class="no-img">No foto</span>
                            <% } %>
                        </td>
                        <td><strong><%= p.getTitolo() %></strong></td>
                        <td><%= p.getAzienda() %></td>
                        <td><%= p.getTipo() %></td>
                        <td>
                            <% if ("Nuovo".equals(p.getStato())) { %>
                                <span class="badge badge-nuovo">Nuovo</span>
                            <% } else { %>
                                <span class="badge badge-usato" title="Difetti: <%= p.getNoteDifetti() != null ? p.getNoteDifetti() : "Nessuno" %>">Usato</span>
                            <% } %>
                        </td>
                        <td>€ <%= p.getPrezzoAttuale() %></td>
                        <td>
                            <% if (p.isDisponibile()) { %>
                                <span class="badge badge-disp">Sì</span>
                            <% } else { %>
                                <span class="badge badge-not-disp">No</span>
                            <% } %>
                        </td>
                        <td><%= p.getIva() %>%</td>
                        <td>
                            <div class="action-group">
                                <!-- Pulsante Modifica (richiama ModificaProdotto in GET passando l'idProdotto) -->
                                <a href="<%= request.getContextPath() %>/ModificaProdotto?idProdotto=<%= p.getIdProdotto() %>" class="btn-edit">
                                    Modifica
                                </a>

                                <!-- Form per eliminare il prodotto tramite POST -->
                                <form action="<%= request.getContextPath() %>/CancellaProdotto" method="POST" 
                                      onsubmit="return confirm('Sei sicuro di voler eliminare il prodotto: <%= p.getTitolo().replace("'", "\\'") %>?');" 
                                      style="margin:0;">
                                    
                                    <!-- Passiamo l'ID del prodotto come parametro nascosto -->
                                    <input type="hidden" name="id" value="<%= p.getIdProdotto() %>" />
                                    
                                    <!-- Controllo inline per disabilitare il bottone se non disponibile -->
                                    <button type="submit" class="btn-delete" <%= !p.isDisponibile() ? "disabled" : "" %>>Cancella</button>
                                </form>
                            </div>
                        </td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    <%
        }
    %>

</body>
</html>