<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>1-UpShop</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>
	<header>
        <div class="header-top">
            <div class="logo">
            <a href="${pageContext.request.contextPath}/Home">1UpShop</a>
            </div>
            
            <div class="search-bar"> <%--Barra di ricerca --%>
                <form action="${pageContext.request.contextPath}/Cerca" method="GET">
                    <input type="text" name="query" placeholder="Search for...">
                </form>
            </div>
            
            <div class="header-icons">
                <a href="carrello.jsp" class="cart-icon">Carrello <span class="badge">0</span></a> <%--Serve per far apparire il pallino di notifica --%>
                <a href="wishlist.jsp">Wishlist</a>
                <a href="account.jsp">Account</a>
            </div>
        </div>

       <nav class="sub-navbar">
            <ul class="categories-menu">
                
                <%-- Questo ciclo legge la lista delle aziende dal database--%>
              <c:forEach items="${sessionScope.listaAziende}" var="azienda">
                    <li class="dropdown-item">
                        <a href="#">${azienda.nome}</a>
                        
                        <%-- Il menu a tendina sdoppiato come nel tuo wireframe --%>
                        <div class="mega-menu">
                            <%-- Lato sinistro: i link --%>
                            <div class="menu-links-side">
                                <ul>
                                    <li><a href="catalogo?brand=${azienda.id}&cat=console">&gt; Console</a></li>
                                    <li><a href="catalogo?brand=${azienda.id}&cat=games">&gt; Games</a></li>
                                    <li><a href="catalogo?brand=${azienda.id}&cat=gadget">&gt; Gadget</a></li>
                                </ul>
                            </div>
                            <%-- Lato destro: l'immagine pubblicitaria --%>
                            <div class="menu-image-side">
                                <img src="${pageContext.request.contextPath}/images/${azienda.immaginePromo}" alt="Promo ${azienda.nome}">
                            </div>
                        </div>
                    </li>
                </c:forEach>

            </ul>
            
            <div class="support-link">
                <a href="#">🎧 Support</a>
            </div>
        </nav>
    </header>