<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String error = null;
	String errorDesc= null;
	if(pageContext.getErrorData().getThrowable() != null) {
	    error = pageContext.getErrorData().getThrowable().getMessage();
	}
	else{
		int status = response.getStatus();
		
		error = String.valueOf(status);
		
		if(status < 500){
			errorDesc = "Qualcosa è andato storto nella richiesta...";
		}
		else{
			errorDesc = "Qualcosa è andato storto nella risposta...";
		}
	}
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Error Page</title>
	</head>
	<body>
		<h1>Errore: <%= error %></h1>
		
		<%if(errorDesc != null) {%>
			<p><%= errorDesc %></p>
		<%} %>
	</body>
</html>