<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Base64" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Catalogo Prodotti</title>
    <style>
        body { font-family: sans-serif; margin: 30px; background-color: #f8f9fa; color: #333; }
        .container { display: flex; gap: 30px; align-items: flex-start; }
        
        /* Stili Filtri */
        .filters { background: white; padding: 20px; border-radius: 8px; border: 1px solid #ddd; width: 250px; }
        .filters h3 { margin-top: 0; color: #0056b3; }
        .form-group { margin-bottom: 15px; display: flex; flex-direction: column; gap: 5px; }
        .form-group label { font-size: 0.9em; font-weight: bold; }
        .form-group input, .form-group select { padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
        .btn-submit { background: #0056b3; color: white; border: none; padding: 10px; cursor: pointer; border-radius: 4px; font-weight: bold; width: 100%; }
        .btn-submit:hover { background: #004494; }
        
        /* Stili Griglia Prodotti */
        .catalog { flex-grow: 1; }
        .error-msg { color: red; font-weight: bold; padding: 10px; background: #fdd; border-radius: 4px; margin-bottom: 20px; }
        .stats { margin-bottom: 15px; font-size: 0.9em; color: #666; }
        .grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(220px, 1fr)); gap: 20px; }
        .card { background: white; padding: 15px; border-radius: 8px; border: 1px solid #ddd; display: flex; flex-direction: column; justify-content: space-between; }
        .img-box { height: 150px; background: #eee; display: flex; align-items: center; justify-content: center; border-radius: 4px; overflow: hidden; margin-bottom: 10px; }
        .img-box img { max-width: 100%; max-height: 100%; object-fit: cover; }
        .card h4 { margin: 0 0 10px 0; font-size: 1.1em; }
        .price { color: #28a745; font-weight: bold; font-size: 1.2em; margin-bottom: 15px; }
        .btn-link { background: #333; color: white; text-align: center; padding: 8px; text-decoration: none; border-radius: 4px; font-size: 0.9em; }
        .btn-link:hover { background: #555; }
        
        /* Stili Paginazione */
        .pagination { display: flex; justify-content: center; gap: 5px; margin-top: 30px; }
        .page-btn { padding: 8px 12px; border: 1px solid #ddd; background: white; color: #0056b3; text-decoration: none; border-radius: 4px; }
        .page-btn.active { background: #0056b3; color: white; border-color: #0056b3; pointer-events: none; }
        .page-btn:hover:not(.active) { background: #e9ecef; }
    </style>
</head>
<body>

    <h1>Catalogo</h1>

    <div class="container">
        
        <form action="Catalogo" method="GET" class="filters">
            <h3>Filtra per:</h3>
            
            <div class="form-group">
                <label>Tipo</label>
                <select name="tipo">
                    <option value="">Tutti</option>
                    <option value="Gioco" ${param.tipo == 'Gioco' ? 'selected' : ''}>Gioco</option>
                    <option value="Cabinato" ${param.tipo == 'Cabinato' ? 'selected' : ''}>Cabinato</option>
                    <option value="Console" ${param.tipo == 'Console' ? 'selected' : ''}>Console</option>
                    <option value="Gadget" ${param.tipo == 'Gadget' ? 'selected' : ''}>Gadget</option>
                </select>
            </div>

            <div class="form-group">
                <label>Stato</label>
                <select name="stato">
                    <option value="">Tutti</option>
                    <option value="Nuovo" ${param.stato == 'Nuovo' ? 'selected' : ''}>Nuovo</option>
                    <option value="Usato" ${param.stato == 'Usato' ? 'selected' : ''}>Usato</option>
                </select>
            </div>

            <div class="form-group">
                <label>Prezzo Min (€)</label>
                <input type="number" step="0.01" name="minPrice" value="${param.minPrice}">
            </div>

            <div class="form-group">
                <label>Prezzo Max (€)</label>
                <input type="number" step="0.01" name="maxPrice" value="${param.maxPrice}">
            </div>

            <div class="form-group">
                <label>Anno Min</label>
                <input type="number" name="minYear" value="${param.minYear}">
            </div>

            <div class="form-group">
                <label>Anno Max</label>
                <input type="number" name="maxYear" value="${param.maxYear}">
            </div>

            <button type="submit" class="btn-submit">Applica Filtri</button>
            <div style="text-align: center; margin-top: 10px;">
                <a href="Catalogo" style="color: #666; font-size: 0.9em; text-decoration: none;">Reset</a>
            </div>
        </form>

        <div class="catalog">
            
            <c:if test="${not empty errorMessage}">
                <div class="error-msg">${errorMessage}</div>
            </c:if>

            <c:choose>
                <c:when test="${not empty listaProdotti}">
                    
                    <div class="stats">
                        Prodotti trovati: <strong>${numeroProdotti}</strong> 
                        (Pagina ${paginaCorrente} di ${totalePagine})
                    </div>

                    <div class="grid">
                        <c:forEach var="p" items="${listaProdotti}">
                            <div class="card">
                                <div>
                                    <div class="img-box">
                                        <c:choose>
                                            <c:when test="${not empty p.foto}">
                                                <%
                                                    model.catalogo.ProdottoBean pb = (model.catalogo.ProdottoBean) pageContext.getAttribute("p");
                                                    String b64 = Base64.getEncoder().encodeToString(pb.getFoto());
                                                %>
                                                <img src="data:image/jpeg;base64,<%= b64 %>" alt="${p.titolo}">
                                            </c:when>
                                            <c:otherwise>
                                                <span style="color: #999;">No Foto</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <h4>${p.titolo}</h4>
                                </div>
                                
                                <div>
                                    <div class="price">€ ${p.prezzo}</div>
                                    <a href="DettagliProdotto?idProdotto=${p.ID}" class="btn-link">Vedi Dettagli</a>
                                </div>
                                <div>
                                    <a href="AggiungiAlCarrello?idProdotto=${p.ID}" class="btn-link">Aggiungi al carrello</a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <c:if test="${totalePagine > 1}">
                        <div class="pagination">
                            <c:forEach var="i" begin="1" end="${totalePagine}">
                                <a href="Catalogo?pagCorrente=${i}&tipo=${param.tipo}&stato=${param.stato}&minPrice=${param.minPrice}&maxPrice=${param.maxPrice}&minYear=${param.minYear}&maxYear=${param.maxYear}" 
                                   class="page-btn ${paginaCorrente == i ? 'active' : ''}">
                                    ${i}
                                </a>
                            </c:forEach>
                        </div>
                    </c:if>

                </c:when>
                <c:otherwise>
                    <p style="font-style: italic; color: #777;">Nessun risultato per i filtri impostati.</p>
                </c:otherwise>
            </c:choose>
            
        </div>
    </div>

</body>
</html>