<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Base64" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catalogo Prodotti</title>
     <script src="${pageContext.request.contextPath}/resources/generalScript.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/catalogo/styleCatalogo.css">
</head>
<body>

<jsp:include page="common/header.jsp" />

    <h1>Catalogo</h1>

    <main class="container">
    
       <aside>  <!-- FILTRI -->
        <form action="Catalogo" method="GET" class="filters">
            <h3>Filtra per:</h3>
            
            <div class="form-group">
                <label>Categoria</label>
                <select name="tipo">
                    <option value="">Tutti i tipi</option>
                    <option value="Gioco" ${param.tipo == 'Gioco' ? 'selected' : ''}>Gioco</option>
                    <option value="Cabinato" ${param.tipo == 'Cabinato' ? 'selected' : ''}>Cabinato</option>
                    <option value="Console" ${param.tipo == 'Console' ? 'selected' : ''}>Console</option>
                    <option value="Gadget" ${param.tipo == 'Gadget' ? 'selected' : ''}>Gadget</option>
                </select>
            </div>

            <div class="form-group">
                <label>Stato</label>
                <select name="stato">
                    <option value="">Nessuna Opzione</option>
                    <option value="Nuovo" ${param.stato == 'Nuovo' ? 'selected' : ''}>Nuovo</option>
                    <option value="Usato" ${param.stato == 'Usato' ? 'selected' : ''}>Usato</option>
                </select>
            </div>
			
			 <!-- Filtro Anno -->
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
            <div style="text-align: center; margin-top: 10px;">
                <a href="Catalogo" style="color: #666; font-size: 0.9em; text-decoration: underline;">Reset</a>
            </div>
        </form>
      </aside>
	
	
	<!-- CATALOGO -->
        <article class="catalog">
            
            <c:if test="${not empty errorMessage}">
                <div class="error-msg">${errorMessage}</div>
            </c:if>

            <c:choose>
                <c:when test="${not empty listaProdotti}">
                    
                    <div class="stats">
                        Prodotti trovati: <strong>${numeroProdotti}</strong> 
                        (Pagina ${paginaCorrente} di ${totalePagine})
                    </div>

			 <!-- CARD -->
			  <div class="product-grid">
			    <c:forEach var="p" items="${listaProdotti}">
			        <div class="product-card">
			            
			            <!-- IMMAGINE -->
			            <c:choose>
			                <c:when test="${not empty p.fotoBlob}">
			                    <%
			                        model.prodotto.ProdottoBean pb = (model.prodotto.ProdottoBean) pageContext.getAttribute("p");
			                        String b64 = Base64.getEncoder().encodeToString(pb.getFotoBlob());
			                    %>
			                    <img src="data:image/jpeg;base64,<%= b64 %>" class="img-placeholder" alt="${p.titolo}">
			                </c:when>
			                <c:otherwise>
			                    <!-- Fallback se non c'è la foto: img-placeholder per mantenere il layout quadrato -->
			                    <div class="img-placeholder" style="display: flex; align-items: center; justify-content: center; color: #999;">
			                        Nessuna Foto
			                    </div>
			                </c:otherwise>
			            </c:choose>
			
			          
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
            
        </article>
    </main>
	
	<jsp:include page="common/footer.jsp" />
</body>
</html>