<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catalogo ${param.azienda} - RetroGaming Shop</title>
    <script src="${pageContext.request.contextPath}/resources/generalScript.js"></script>
    <script src="${pageContext.request.contextPath}/resources/catalogo/scriptCatalogo.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/catalogo/styleCatalogo.css">
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/favicon.ico">
   
</head>
<body>
<jsp:include page="common/header.jsp" />
	<h1>Catalogo ${param.azienda}</h1>

   
    	 
    <main class="container">
        <!-- SIDEBAR FILTRI -->
        <aside> 
    <form action="${pageContext.request.contextPath}/CatalogoAzienda" method="get" class="filters" onsubmit="puliziaFiltri(event, this)">
        
        <h3>Filtra per:</h3>
        <input type="hidden" name="azienda" value="${param.azienda}">
        <!-- Filtro Tipo -->
        <div class="form-group">
            <label for="tipo">Categoria</label>
            <select id="tipo" name="tipo">
                <option value="">Tutti i tipi</option>
                <option value="Console" ${param.tipo == 'Console' ? 'selected' : ''}>Console</option>
                <option value="Gioco" ${param.tipo == 'Gioco' ? 'selected' : ''}>Gioco</option>
                <option value="Cabinato" ${param.tipo == 'Cabinato' ? 'selected' : ''}>Cabinato</option>
                <option value="Gadget" ${param.tipo == 'Gadget' ? 'selected' : ''}>Gadget</option>
            </select>
        </div>

        <!-- Filtro Stato -->
        <div class="form-group">
            <label for="stato">Stato</label>
            <select id="stato" name="stato">
                <option value="">Nessuna Opzione</option>
                <option value="Nuovo" ${param.stato == 'Nuovo' ? 'selected' : ''}>Nuovo</option>
                <option value="Usato" ${param.stato == 'Usato' ? 'selected' : ''}>Usato</option>
            </select>
        </div>

        <!-- Filtro Prezzo -->
        <div class="form-group">
            <label>Prezzo (€)</label>
            <div class="filter-row">
                <input type="number" step="0.01" name="minPrice" placeholder="Min" value="${param.minPrice}">
                <input type="number" step="0.01" name="maxPrice" placeholder="Max" value="${param.maxPrice}">
            </div>
        </div>

        <!-- Filtro Anno -->
        <div class="form-group">
            <label>Anno di Rilascio</label>
            <div class="filter-row">
                <input type="number" name="minYear" placeholder="Da" value="${param.minYear}">
                <input type="number" name="maxYear" placeholder="A" value="${param.maxYear}">
            </div>
        </div>

        <button type="submit" class="btn-submit">Applica Filtri</button>
        <div class="reset-container" style="text-align: center; margin-top: 10px;">
            <a href="${pageContext.request.contextPath}/CatalogoAzienda?azienda=${param.azienda}"
               style="color: #666; font-size: 0.9em; text-decoration: underline;">Reset </a>
        </div>
    </form>
</aside>

        <!-- AREA PRODOTTI -->
        <article class="catalog">
        
        <c:if test="${not empty errorMessage}">
                <div class="error-msg">${errorMessage}</div>
            </c:if>
       			 <c:choose>
                <c:when test="${not empty listaProdotti}">
                
            <div class="stats">
                Prodotti Trovati <strong>${numeroProdotti}</strong> per "${param.azienda}"  
                (Pagina ${paginaCorrente} di ${totalePagine})
            </div>
            
                    <div class="product-grid">
                        <c:forEach items="${listaProdotti}" var="p">
                        
                            <div class="product-card">
                                <!-- Immagine caricata via Servlet GetPicture -->
                                <img src="${pageContext.request.contextPath}/GetPicture?idProdotto=${p.idProdotto}" 
                                     alt="${p.titolo}"  class="img-placeholder"/>

                                  <div class="card-details">
			              
					                <span class="category">${p.tipo}</span>
					                <h3 class="title">
					                    <a href="DettagliProdotto?idProdotto=${p.idProdotto}">${p.titolo}</a>
					                </h3>
					                
					                <p class="price">€ ${p.prezzoAttuale} EUR</p>
					                
					                
					                <button type="button" class="bottone" onclick="aggiungiAlCarrello(this)" data-id-prodotto="${p.idProdotto}" data-context-path="${pageContext.request.contextPath}">
					                    Aggiungi al carrello
					                </button>
					            </div>
                            </div>
                        </c:forEach>
                    </div>

                    <!-- PAGINAZIONE -->
                     <c:if test="${totalePagine > 1}">
                        <div class="pagination">
                            <c:forEach var="i" begin="1" end="${totalePagine}">
                                <a href="CatalogoAzienda?azienda=${param.azienda}&pagCorrente=${i}&tipo=${param.tipo}&stato=${param.stato}&minPrice=${param.minPrice}&maxPrice=${param.maxPrice}&minYear=${param.minYear}&maxYear=${param.maxYear}" 
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
        </article>

</main>
<jsp:include page="common/footer.jsp" />
</body>
</html>