package controller.servlet.ordine;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.autentificazione.UtenteBean;
import model.ordine.OrdineBean;
import model.ordine.OrdineDAO;

/**
 * Servlet implementation class CancellazioneOrdine
 */
@WebServlet("/CancellazioneOrdine")
public class CancellazioneOrdine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancellazioneOrdine() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect(request.getContextPath() + "/Ordini");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession sessione = request.getSession();

		UtenteBean utente = (UtenteBean) sessione.getAttribute("utente");
		
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
			
			if(ordine == null) {
				response.sendError(404, "Ordine non trovato");
				return;
			}
			
			if(!ordine.getEmailUtente().equals(utente.getEmail())){
				response.sendError(400, "Non sei autorizzato a cancellare questo ordine");
				return;
			}
			
			OrdineDAO oDAO = new OrdineDAO();
			
			oDAO.deleteOrdineByKey(idOrdine);
			
			response.sendRedirect(request.getContextPath() + "/Ordini");
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			
			response.sendError(404, "ID prodotto non valido");
			
		} catch (SQLException e) {
			e.printStackTrace();

			response.sendError(500, "Errore nel database");
		}
	}

}
