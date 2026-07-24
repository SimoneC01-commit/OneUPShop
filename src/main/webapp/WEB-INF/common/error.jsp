<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Errore ${statusCode > 0 ? statusCode : ''} - RetroGaming Shop</title>
    <script src="${pageContext.request.contextPath}/resources/generalScript.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/error/styleError.css">
</head>
<body>

<jsp:include page="header.jsp"/>

    <h1>Game Over</h1>

    <main class="error-container">
        <article class="error-content">
            
            <div class="error-card">
                
                <h2>
                    <c:out value="${errorTitle}" />
                </h2>
                
                <p>
                    <c:out value="${errorDetail}" />
                </p>
                
            </div>
            
            <div class="error-actions">
				<a href="${pageContext.request.contextPath}/Home" class="btn-error">
			        Torna alla Home
			    </a>
			</div>

        </article>
    </main>

<jsp:include page="footer.jsp"/>

</body>
</html>