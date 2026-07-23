<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Risultati ricerca per "${param.q}" - RetroGaming Shop</title>
    <style>
        body { 
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; 
            margin: 0; 
            padding: 20px; 
            background-color: #f8f9fa; 
            color: #333; 
        }
        .container { 
            max-width: 1200px; 
            margin: 0 auto; 
        }
        .header { 
            display: flex; 
            justify-content: space-between; 
            align-items: center; 
            margin-bottom: 25px; 
            border-bottom: 2px solid #e9ecef; 
            padding-bottom: 15px; 
        }
        .header h1 { 
            margin: 0; 
            color: #2c3e50; 
            font-size: 1.8em; 
        }
        .search-query { 
            color: #007bff; 
        }
        .back-link { 
            text-decoration: none; 
            color: #007bff; 
            font-weight: bold; 
        }
        .back-link:hover { 
            text-decoration: underline; 
        }
        .results-info { 
            margin-bottom: 20px; 
            color: #6c757d; 
            font-size: 0.95em; 
        }
        
        /* Griglia Prodotti */
        .grid { 
            display: grid; 
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); 
            gap: 20px; 
        }
        
        /* Card Prodotto */
        .card { 
            background: white; 
            border-radius: 8px; 
            overflow: hidden; 
            box-shadow: 0 2px 5px rgba(0,0,0,0.1); 
            display: flex; 
            flex-direction: column; 
            justify-content: space-between; 
            transition: transform 0.2s, box-shadow 0.2s; 
        }
        .card:hover { 
            transform: translateY(-4px); 
            box-shadow: 0 4px 10px rgba(0,0,0,0.15); 
        }
        .card-img { 
            width: 100%; 
            height: 180px; 
            object-fit: cover; 
            background-color: #eee; 
        }
        .card-body { 
            padding: 15px; 
            flex-grow: 1; 
            display: flex; 
            flex-direction: column; 
            justify-content: space-between; 
        }
        .card-title { 
            font-size: 1.1em; 
            font-weight: bold; 
            margin: 0 0 8px 0; 
            color: #2c3e50; 
        }
        .card-meta { 
            font-size: 0.85em; 
            color: #7f8c8d; 
            margin-bottom: 12px; 
        }
        .card-price { 
            font-size: 1.25em; 
            font-weight: bold; 
            color: #27ae60; 
            margin-bottom: 15px; 
        }
        .btn-details { 
            display: block; 
            text-align: center; 
            background-color: #34495e; 
            color: white; 
            padding: 10px; 
            border-radius: 4px; 
            text-decoration: none; 
            font-size: 0.9em; 
            font-weight: bold; 
        }
        .btn-details:hover { 
            background-color: #2c3e50; 
        }
        
        .no-results { 
            background: white; 
            padding: 40px; 
            text-align: center; 
            border-radius: 8px; 
            box-shadow: 0 2px 5px rgba(0,0,0,0.05); 
            color: #6c757d; 
        }
    </style>
</head>
<body>

<div class="container">

    <div class="header">
        <h1>Risultati per: <span class="search-query">"${param.q}"</span></h1>
        <a href="${pageContext.request.contextPath}/Home" class="back-link">← Torna alla Home</a>
    </div>

    <c:choose>
        <c:when test="${not empty risultati}">
            <div class="results-info">
                Trovati <strong>${risultati.size()}</strong> prodotti corrispondenti alla ricerca.
            </div>

            <div class="grid">
                <c:forEach items="${risultati}" var="p">
                    <div class="card">
                        <!-- Recupero immagine tramite la Servlet GetPicture -->
                        <img src="${pageContext.request.contextPath}/GetPicture?idProdotto=${p.idProdotto}" 
                             alt="${p.titolo}" 
                             class="card-img"/>
                        
                        <div class="card-body">
                            <div>
                                <h2 class="card-title">${p.titolo}</h2>
                                <div class="card-meta">
                                    <span>${p.azienda}</span> • 
                                    <span>${p.tipo}</span> • 
                                    <span>${p.stato}</span>
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
        </c:when>

        <c:otherwise>
            <div class="no-results">
                <h2>Nessun prodotto trovato</h2>
                <p>Nessun articolo corrisponde alla ricerca "<strong>${param.q}</strong>".</p>
                <a href="${pageContext.request.contextPath}/Home" class="btn-details" style="display:inline-block; margin-top:15px; width:auto; padding:10px 20px;">
                    Torna al Catalogo
                </a>
            </div>
        </c:otherwise>
    </c:choose>

</div>

</body>
</html>