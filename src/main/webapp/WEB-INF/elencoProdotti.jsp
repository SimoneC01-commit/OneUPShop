<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.prodotto.ProdottoBean" %>
<%@ page import="java.util.Base64" %>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Pannello Admin - Elenco Prodotti</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/elencoProdotti/styleElencoProdotti.css">
    <script src="${pageContext.request.contextPath}/resources/elencoProdotti/scriptElencoProdotti.js" defer></script>
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
                                
                                <dialog id="modalConferma">
								   <h3>Conferma eliminazione</h3>
								   <p>Sei sicuro di voler eliminare questo prodotto? L'azione non è reversibile.</p>
								   <div class="modal-actions">
								       <button type="button" id="btnAnnulla" onclick="annulla()">Annulla</button>
								       <button type="button" id="btnConferma" class="btn-danger" 
								       		onclick="elimina()" data-context-path="${pageContext.request.getContextPath() }"
								       		data-id-prodotto="${prodotto.idProdotto}">Elimina</button>
								    </div>
								</dialog>
								
                                <button type="button" class="btn-delete" 
                                	onclick="confermaEliminazione()" <%= !p.isDisponibile() ? "disabled" : "" %>>Elimina</button>
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