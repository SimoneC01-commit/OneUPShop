<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Catalogo ${param.azienda} - RetroGaming Shop</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 0; padding: 20px; background-color: #f8f9fa; color: #333; }
        .container { max-width: 1200px; margin: 0 auto; }
        
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; border-bottom: 2px solid #ddd; padding-bottom: 10px; }
        .header h1 { margin: 0; color: #2c3e50; }
        
        .catalog-layout { display: flex; gap: 25px; }
        
        /* Sidebar Filtri */
        .sidebar { width: 260px; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); height: fit-content; }
        .sidebar h3 { margin-top: 0; color: #34495e; border-bottom: 1px solid #eee; padding-bottom: 8px; }
        .filter-group { margin-bottom: 15px; }
        .filter-group label { display: block; font-size: 0.9em; font-weight: bold; margin-bottom: 5px; color: #555; }
        .filter-group input, .filter-group select { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        .filter-row { display: flex; gap: 10px; }
        .btn-filter { width: 100%; padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 4px; font-weight: bold; cursor: pointer; margin-top: 10px; }
        .btn-filter:hover { background-color: #0056b3; }
        .btn-reset { display: block; text-align: center; margin-top: 8px; font-size: 0.85em; color: #6c757d; text-decoration: none; }
        
        /* Area Prodotti */
        .products-area { flex: 1; }
        .products-count { margin-bottom: 15px; font-size: 0.9em; color: #666; }
        .grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(240px, 1fr)); gap: 20px; }
        
        /* Card Prodotto */
        .card { background: white; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 5px rgba(0,0,0,0.1); display: flex; flex-direction: column; justify-content: space-between; transition: transform 0.2s; }
        .card:hover { transform: translateY(-4px); }
        .card-img { width: 100%; height: 180px; object-fit: cover; background-color: #eee; }
        .card-body { padding: 15px; flex-grow: 1; display: flex; flex-direction: column; justify-content: space-between; }
        .card-title { font-size: 1.1em; font-weight: bold; margin: 0 0 8px 0; color: #2c3e50; }
        .card-info { font-size: 0.85em; color: #7f8c8d; margin-bottom: 10px; }
        .card-price { font-size: 1.25em; font-weight: bold; color: #27ae60; margin-bottom: 12px; }
        .btn-details { display: block; text-align: center; background-color: #34495e; color: white; padding: 8px; border-radius: 4px; text-decoration: none; font-size: 0.9em; }
        .btn-details:hover { background-color: #2c3e50; }
        
        /* Paginazione */
        .pagination { display: flex; justify-content: center; gap: 8px; margin-top: 30px; }
        .page-link { padding: 8px 12px; background: white; border: 1px solid #ddd; border-radius: 4px; text-decoration: none; color: #007bff; font-weight: bold; }
        .page-link.active { background-color: #007bff; color: white; border-color: #007bff; }
        .page-link:hover:not(.active) { background-color: #e9ecef; }
        
        .no-results { background: white; padding: 40px; text-align: center; border-radius: 8px; color: #777; }
    </style>
</head>
<body>

<div class="container">

    <div class="header">
        <h1>Catalogo ${param.azienda}</h1>
        <a href="${pageContext.request.contextPath}/Home" style="text-decoration:none; color:#007bff;">← Torna alla Home</a>
    </div>

    <div class="catalog-layout">
        
        <!-- SIDEBAR FILTRI -->
        <aside class="sidebar">
            <h3>Filtra Prodotti</h3>
            <form action="${pageContext.request.contextPath}/CatalogoAzienda" method="get">
                <!-- Mantiene il parametro azienda obbligatorio -->
                <input type="hidden" name="azienda" value="${param.azienda}">
                
                <!-- Filtro Tipo -->
                <div class="filter-group">
                    <label for="tipo">Tipologia</label>
                    <select id="tipo" name="tipo">
                        <option value="">Tutti i tipi</option>
                        <option value="Console" ${param.tipo == 'Console' ? 'selected' : ''}>Console</option>
                        <option value="Gioco" ${param.tipo == 'Gioco' ? 'selected' : ''}>Gioco</option>
                        <option value="Cabinato" ${param.tipo == 'Cabinato' ? 'selected' : ''}>Cabinato</option>
                        <option value="Gadget" ${param.tipo == 'Gadget' ? 'selected' : ''}>Gadget</option>
                    </select>
                </div>

                <!-- Filtro Stato -->
                <div class="filter-group">
                    <label for="stato">Stato</label>
                    <select id="stato" name="stato">
                        <option value="">Tutti</option>
                        <option value="Nuovo" ${param.stato == 'Nuovo' ? 'selected' : ''}>Nuovo</option>
                        <option value="Usato" ${param.stato == 'Usato' ? 'selected' : ''}>Usato</option>
                    </select>
                </div>

                <!-- Filtro Prezzo -->
                <div class="filter-group">
                    <label>Prezzo (€)</label>
                    <div class="filter-row">
                        <input type="number" step="0.01" name="minPrice" placeholder="Min" value="${param.minPrice}">
                        <input type="number" step="0.01" name="maxPrice" placeholder="Max" value="${param.maxPrice}">
                    </div>
                </div>

                <!-- Filtro Anno -->
                <div class="filter-group">
                    <label>Anno di Rilascio</label>
                    <div class="filter-row">
                        <input type="number" name="minYear" placeholder="Da" value="${param.minYear}">
                        <input type="number" name="maxYear" placeholder="A" value="${param.maxYear}">
                    </div>
                </div>

                <button type="submit" class="btn-filter">Applica Filtri</button>
                <a href="${pageContext.request.contextPath}/CatalogoAzienda?azienda=${param.azienda}" class="btn-reset">Azzera filtri</a>
            </form>
        </aside>

        <!-- AREA PRODOTTI -->
        <main class="products-area">
            <div class="products-count">
                Trovati <strong>${numeroProdotti}</strong> prodotti per "${param.azienda}"
            </div>

            <c:choose>
                <c:when test="${not empty listaProdotti}">
                    <div class="grid">
                        <c:forEach items="${listaProdotti}" var="p">
                            <div class="card">
                                <!-- Immagine caricata via Servlet GetPicture -->
                                <img src="${pageContext.request.contextPath}/GetPicture?idProdotto=${p.idProdotto}" 
                                     alt="${p.titolo}" 
                                     class="card-img"/>
                                
                                <div class="card-body">
                                    <div>
                                        <h2 class="card-title">${p.titolo}</h2>
                                        <div class="card-info">
                                            <span>${p.tipo}</span> • <span>Anno ${p.annoRilascio}</span> • <span>${p.stato}</span>
                                        </div>
                                    </div>
                                    <div>
                                        <div class="card-price">€ ${p.prezzoAttuale}</div>
                                        <a href="${pageContext.request.contextPath}/DettaglioProdotto?idProdotto=${p.idProdotto}" class="btn-details">
                                            Vedi Dettagli
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <!-- PAGINAZIONE -->
                    <c:if test="${totalePagine > 1}">
                        <div class="pagination">
                            <c:forEach begin="1" end="${totalePagine}" var="i">
                                <a href="${pageContext.request.contextPath}/CatalogoAzienda?azienda=${param.azienda}&pagCorrente=${i}&tipo=${param.tipo}&stato=${param.stato}&minPrice=${param.minPrice}&maxPrice=${param.maxPrice}&minYear=${param.minYear}&maxYear=${param.maxYear}" 
                                   class="page-link ${i == paginaCorrente ? 'active' : ''}">
                                    ${i}
                                </a>
                            </c:forEach>
                        </div>
                    </c:if>

                </c:when>
                <c:otherwise>
                    <div class="no-results">
                        <h2>Nessun prodotto trovato</h2>
                        <p>Prova a modificare o resettare i filtri di ricerca per questa azienda.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </main>

    </div>

</div>

</body>
</html>