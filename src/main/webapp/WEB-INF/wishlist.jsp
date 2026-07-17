<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.wishlist.ElementoWishlistBean" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>La Mia Wishlist</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f6f9;
        }
        h1 {
            color: #333;
        }
        .error {
            color: red;
            background-color: #fde8e8;
            padding: 10px;
            border: 1px solid #f9b8b8;
            margin-bottom: 20px;
            max-width: 800px;
        }
        .wishlist-container {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            max-width: 900px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            text-align: left;
        }
        th, td {
            padding: 12px 15px;
            border-bottom: 1px solid #e0e0e0;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:hover {
            background-color: #f8f9fa;
        }
        .btn-view {
            background-color: #28a745;
            color: white;
            text-decoration: none;
            padding: 6px 12px;
            border-radius: 4px;
            font-size: 14px;
            display: inline-block;
            margin-right: 5px;
        }
        .btn-view:hover {
            background-color: #218838;
        }
        .btn-remove {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 6px 12px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
        .btn-remove:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>

    <h1>La Mia Wishlist</h1>

    <% 
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) { 
    %>
        <div class="error"><%= errorMessage %></div>
    <% 
        } 
    %>

    <div class="wishlist-container">
        <table>
            <thead>
                <tr>
                    <th>ID Prodotto</th>
                    <th>Data Inserimento</th>
                    <th>Azioni</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    @SuppressWarnings("unchecked")
                    ArrayList<ElementoWishlistBean> wishlist = (ArrayList<ElementoWishlistBean>) request.getAttribute("wishlist");
                    if (wishlist != null && !wishlist.isEmpty()) {
                        for (ElementoWishlistBean item : wishlist) {
                %>
                <tr>
                    <td>#<%= item.getIdProdotto() %></td>
                    <td><%= item.getDataInserimento() %></td>
                    <td>
                        <a href="<%= request.getContextPath() %>/DettaglioProdotto?id=<%= item.getIdProdotto() %>" class="btn-view">
                            Vedi Prodotto
                        </a>
                        
                        <form action="<%= request.getContextPath() %>/RimuoviDaWishlist" method="POST" style="display:inline;" onsubmit="return confirm('Rimuovere il prodotto dalla wishlist?');">
                            <input type="hidden" name="idProdotto" value="<%= item.getIdProdotto() %>">
                            <button type="submit" class="btn-remove">Rimuovi</button>
                        </form>
                    </td>
                </tr>
                <% 
                        }
                    } else { 
                %>
                <tr>
                    <td colspan="3" style="text-align: center; padding: 20px; color: #6c757d;">
                        La tua wishlist è vuota.
                    </td>
                </tr>
                <% 
                    } 
                %>
            </tbody>
        </table>
    </div>

</body>
</html>