package controller.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.autentificazione.UtenteBean;
import model.carrello.Carrello;
import model.ordine.OrdineBean;
import model.ordine.OrdineDAO;

/**
 * Servlet implementation class Checkout
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/Carrello").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession sessione = request.getSession();
		
		UtenteBean utente = (UtenteBean) sessione.getAttribute("utente");
		
		if(utente == null) {
			request.setAttribute("errorMessage", "Devi essere loggato per poter eseguire un acquisto.");
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			return;
		}
		
		Carrello carrello = (Carrello) sessione.getAttribute("carrello");
		
		if(carrello == null || carrello.getLista().isEmpty()) {
			request.setAttribute("errorMessage", "Devi avere del contenuto nel carrello");
			request.getRequestDispatcher("/DettagliCarrello").forward(request, response);
			return;
		}
		
		String via = request.getParameter("via");
		String cap = request.getParameter("cap");
		String citta = request.getParameter("citta");
		String telefono = request.getParameter("telefono");
		String metodoPagamento = request.getParameter("metodoPagamento");
		
		if(via == null || via.trim().isEmpty() 
		        || cap == null || cap.trim().isEmpty() 
		        || citta == null || citta.trim().isEmpty()
		        || telefono == null || telefono.trim().isEmpty()
		        || metodoPagamento == null || metodoPagamento.trim().isEmpty()) {
		    
		    request.setAttribute("errorMessage", "Errore compilazione form. Tutti i campi sono obbligatori.");
		    request.getRequestDispatcher("/WEB-INF/checkout.jsp").forward(request, response);
		    return;
		}
		
		String indirizzo = via.trim() + ", " + cap.trim() + ", " +citta.trim();

		OrdineBean ordine = new OrdineBean();
		
		ordine.setEmailUtente(utente.getEmail());
		ordine.setTotaleOrdine(carrello.getTotale());
		ordine.setIndirizzoSpedizioneStorico(indirizzo);
		ordine.setTelefono(telefono.trim());
		ordine.setMetodoPagamento(metodoPagamento.trim());
		
		try {
			OrdineDAO oDAO = new OrdineDAO();
			
			oDAO.saveOrdine(ordine, carrello.getLista());
			
			carrello.svuota();
			
			response.sendRedirect(request.getContextPath() + "/Ordini");
			return;
			
		} catch (SQLException e) {
			e.printStackTrace();

			request.setAttribute("errorMessage", "Errore durante il checkout. Riprova.");
			request.getRequestDispatcher("/WEB-INF/checkout.jsp").forward(request, response);
		}
	}

}
