<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/footer/styleF.css">

<footer class="main-footer">
    <div class="footer-content">
        
        <!-- BLOCCO SINISTRA: Logo e Info -->
        <div class="footer-left">
            <a href="${pageContext.request.contextPath}/Home">
                <img src="${pageContext.request.contextPath}/resources/img/logoW.png" alt="Logo 1-UP Shop" class="footer-logo">
            </a>
            <div class="footer-contact">
                <p>info@1upshop.com</p>
                <p>+39 123 456 7890</p>
            </div>
        </div>

        <!-- BLOCCO DESTRA: Link e Copyright -->
        <div class="footer-right">
            <nav class="footer-links">
                <a href="${pageContext.request.contextPath}/Profilo">Account</a>
                <a href="${pageContext.request.contextPath}/Wishlist">Wishlist</a>
                <a href="${pageContext.request.contextPath}/DettagliCarrello">Carrello</a>
                <a href="#">Contatti</a>
            </nav>
            <p class="footer-copyright">© 2026 1-UP Shop. All rights reserved.</p>
            <img src="${pageContext.request.contextPath}/resources/img/star.gif" alt="Star" class="footer-gif">
        </div>
        
    </div>
</footer>
