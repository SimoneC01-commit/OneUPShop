<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/header/style.css">

<header class="main-header">
    <div class="container">
        
        <!-- RIGA SUPERIORE -->
        <div class="header-top">
            <!-- Logo -->
            <div class="logo">
                <a href="${pageContext.request.contextPath}/Home">LOGO</a>
            </div>

            <!-- Barra di ricerca -->
            <div class="search-container">
                <form action="${pageContext.request.contextPath}/Ricerca" method="GET" class="search-form">
                    <span class="search-icon">🔍</span>
                    <input type="text" name="q" placeholder="Search for..." class="search-input">
                </form>
            </div>

            <!-- Icone destra -->
            <div class="header-icons">
                <a href="${pageContext.request.contextPath}/DettagliCarrello" class="icon-link">Carrello</a>
                <a href="#" class="icon-link">🤍</a>
                <a href="${pageContext.request.contextPath}/Login" class="icon-link">👤</a>
            </div>
        </div>

        <!-- RIGA INFERIORE -->
        <div class="header-bottom">
            <!-- Link centrali -->
            <nav class="company-links">
                <ul>
                    <li><a href="#">Nintendo - </a></li>
                    <li><a href="#">Sony -</a></li>
                    <li><a href="#">Microsoft -</a></li>
                    <li><a href="#">Cabinati -</a></li>
                </ul>
            </nav>
            
           <!--  <nav class="company-links">
    <ul>
        Passiamo il parametro 'azienda' alla Servlet 'Catalogo'
        <li><a href="${pageContext.request.contextPath}/Catalogo?azienda=Nintendo">• Nintendo</a></li>
        <li><a href="${pageContext.request.contextPath}/Catalogo?azienda=Sony">• Sony</a></li>
        <li><a href="${pageContext.request.contextPath}/Catalogo?azienda=Sega">• Sega</a></li>
        <li><a href="${pageContext.request.contextPath}/Catalogo?azienda=Atari">• Atari</a></li>
    </ul>
</nav> -->
            
            <!-- Link supporto a destra -->
            <div class="support-link">
                <a href="#">Support</a>
            </div>
        </div>
        
    </div>
</header>