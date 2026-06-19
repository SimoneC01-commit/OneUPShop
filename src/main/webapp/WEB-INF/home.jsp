<%@page import="controller.servlet.Home"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="model.ProdottiHome.*" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Home</title>
	</head>
	<body>
	
		<h1>Lista nuovi prodotti:</h1>
		<ul>
		<%
			List<ProdottiHomeBean> lista1 = (List<ProdottiHomeBean>) request.getAttribute("prodottiHomeNuovi");
			
			if(lista1 != null && !lista1.isEmpty()){
				for(ProdottiHomeBean prodotto: lista1){
					%>
					<li>Titolo: <%=prodotto.getTitolo()%> (<%=prodotto.getPrezzo()%> &euro;)</li>
					<%
				}
			}
			else{
				%>
					<li>Nessun oggetto trovato.</li>
				<%
			}
		%>
		</ul>
		
		<h1>Lista prodotti consigliati:</h1>
		<ul>
		<%
			List<ProdottiHomeBean> lista2 = (List<ProdottiHomeBean>) request.getAttribute("prodottiHomeConsigliati");
			
			if(lista2 != null && !lista2.isEmpty()){
				for(ProdottiHomeBean prodotto: lista2){
					%>
					<li>Titolo: <%=prodotto.getTitolo()%> (<%=prodotto.getPrezzo()%> &euro;)</li>
					<%
				}
			}
			else{
				%>
					<li>Nessun oggetto trovato.</li>
				<%
			}
		%>
		</ul>
	</body>
</html>