<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/header/style.css">
<script src="${pageContext.request.contextPath}/resources/header/scriptHeader.js" defer></script>

<header class="main-header hide-on-print">
        
    <!-- RIGA SUPERIORE -->
    <div class="header-top">
        
        <!-- Menu Hamburger e Profilo -->
        <div class="mobile-left-group hide-on-desktop">
            <button class="hamburger-btn" id="menuToggle" onclick="toggleMenu()">☰</button>
            <a href="${pageContext.request.contextPath}/Profilo" class="icon-link">
            <img src="${pageContext.request.contextPath}/resources/img/profilo.png" alt="Profilo" class="icon-img"></a>
        </div>

        <!-- Logo  -->
        <div class="logo">
            <a href="${pageContext.request.contextPath}/Home" class="logo-link">
             <img src="${pageContext.request.contextPath}/resources/img/logo.png" alt="Logo Retrogaming" class="logo-img"> 
             </a>
        </div>

        <!-- Barra di ricerca (Nascosta su Mobile, va nel cassetto) -->
        <div class="search-container hide-on-mobile">
            <form action="${pageContext.request.contextPath}/RicercaProdotto" method="GET" class="search-form">
                <span class="search-icon"> <img src="${pageContext.request.contextPath}/resources/img/search.png" alt="Search" class="search-img"></span>
                <input type="search" id="searchbar" name="q" placeholder="Cerca..." class="search-input" onkeyup="suggerimenti()" data-context-path="${pageContext.request.contextPath}">
                <div id="box-suggerimenti"></div>
            </form>
        </div>

        <!-- Icone-->
        <div class="header-icons">
        
            <a href="${pageContext.request.contextPath}/Profilo" class="icon-link hide-on-mobile"> 
            <img src="${pageContext.request.contextPath}/resources/img/profilo.png" alt="Profilo" class="icon-img">
            </a>
            
            <a href="${pageContext.request.contextPath}/Wishlist" class="icon-link">
                <img src="${pageContext.request.contextPath}/resources/img/heart.jpg" alt="Whishlist" class="icon-img">
            </a>
            
            <a href="${pageContext.request.contextPath}/DettagliCarrello" class="icon-link">
            <img src="${pageContext.request.contextPath}/resources/img/carrello.png" alt="Carrello" class="icon-img-cart"></a>
            
        </div>
    </div>

    <!-- RIGA INFERIORE -->
    <div class="header-bottom hide-on-mobile">
        <nav class="company-links">
            <ul>  
               <li><a href="${pageContext.request.contextPath}/Catalogo">Catalogo -</a></li>
	           <li><a href="${pageContext.request.contextPath}/CatalogoAzienda?azienda=Nintendo">Nintendo - </a></li>
		       <li><a href="${pageContext.request.contextPath}/CatalogoAzienda?azienda=Sony">Sony -</a></li>
		       <li><a href="${pageContext.request.contextPath}/CatalogoAzienda?azienda=Microsoft">Microsoft -</a></li>
		       <li><a href="${pageContext.request.contextPath}/CatalogoAzienda?azienda=Sega">Sega -</a></li>
            </ul>
        </nav>
        <div class="support-link">
            <a href="${pageContext.request.contextPath}/Contatti">Contatti</a>
        </div>
    </div>
        
    <!-- MENU LATERALE MOBILE (Cassetto nascosto) -->
    <nav class="mobile-sidebar" id="mobileSidebar">
        <!-- Tasto per chiudere -->
        <button class="close-btn" onclick="toggleMenu()">✕</button>
        
        <div class="sidebar-logo"> <img src="${pageContext.request.contextPath}/resources/img/logo.png" alt="Logo Retrogaming" class="logo-img-menu"> </div>
        
        <div class="sidebar-search">
            <form action="${pageContext.request.contextPath}/RicercaProdotto" method="GET" class="search-form">
                <span class="search-icon"><img src="${pageContext.request.contextPath}/resources/img/search.png" alt="Search" class="search-img"></span>
                <input type="text" name="q" placeholder="Search for..." class="search-input">
            </form>
        </div>

        <ul class="sidebar-links">
	        <li><a href="${pageContext.request.contextPath}/Catalogo">Catalogo -</a></li>
	           <li><a href="${pageContext.request.contextPath}/CatalogoAzienda?azienda=Nintendo">Nintendo - </a></li>
		       <li><a href="${pageContext.request.contextPath}/CatalogoAzienda?azienda=Sony">Sony -</a></li>
		       <li><a href="${pageContext.request.contextPath}/CatalogoAzienda?azienda=Microsoft">Microsoft -</a></li>
		         <li><a href="${pageContext.request.contextPath}/CatalogoAzienda?azienda=Sega">Sega -</a></li>
        </ul>

        <div class="sidebar-support">
            <a href="${pageContext.request.contextPath}/Contatti">Contatti</a>
        </div>
    </nav>
        
</header>