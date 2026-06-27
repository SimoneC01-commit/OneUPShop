package controller.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dettagliOrdine.DettagliOrdineBean;
import model.dettagliOrdine.DettagliOrdineDAO;
import model.ordine.OrdineBean;
import model.ordine.OrdineDAO;

/**
 * Servlet implementation class DettagliOrdine
 */
@WebServlet("/DettagliOrdine")
public class DettagliOrdine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DettagliOrdine() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession sessione = request.getSession();
	    model.autentificazione.UtenteBean utente = (model.autentificazione.UtenteBean) sessione.getAttribute("utente");
	    
	    if(utente == null) {
			request.setAttribute("errorMessage", "Devi essere loggato per poter visualizzare i tuoi ordini.");
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			return;
		}
	    
		String idOrdineStr = request.getParameter("idOrdine");
		
		if(idOrdineStr == null || idOrdineStr.trim().isEmpty()) {
			response.sendError(404, "Campo ID mancante");
			return;
		}
		
		try {
			
			int idOrdine = Integer.parseInt(idOrdineStr);
			
			OrdineBean ordine = new OrdineDAO().retrieveOrdineByKey(idOrdine);
			
			if (ordine == null) {
	            response.sendError(404, "Ordine non trovato");
	            return;
	        }
			
			if(!ordine.getEmailUtente().equals(utente.getEmail())) {
				response.sendError(400, "Non sei autorizzato a visualizzare questo ordine");
				return;
			}
			
			ArrayList<DettagliOrdineBean> dettagliOrdine = new DettagliOrdineDAO().retrieveProdottiOrdine(ordine.getIdOrdine());
			
			request.setAttribute("ordine", ordine);
			request.setAttribute("dettagliOrdine", dettagliOrdine);
			
			request.getRequestDispatcher("/WEB-INF/dettagliOrdine.jsp").forward(request, response);
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			
			response.sendError(404, "ID prodotto non valido");
			
		} catch (SQLException e) {
			e.printStackTrace();

			response.sendError(500, "Errore nel database");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
