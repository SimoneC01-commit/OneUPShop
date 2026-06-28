package controller.servlet.ordine;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
 * Servlet implementation class Ordini
 */
@WebServlet("/Ordini")
public class Ordini extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Ordini() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession sessione = request.getSession();

		UtenteBean utente = (UtenteBean) sessione.getAttribute("utente");
		
		if(utente == null) {
			request.setAttribute("errorMessage", "Devi essere loggato per poter visualizzare i tuoi ordini.");
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			return;
		}
		
		try {
			ArrayList<OrdineBean> ordini = new OrdineDAO().retrieveOrdini(utente.getEmail());
			
			request.setAttribute("ordini", ordini);
			
			request.getRequestDispatcher("/WEB-INF/ordini.jsp").forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			request.setAttribute("errorMessage", "Errore durante il recupero ordini. Riprova.");
			request.getRequestDispatcher("/Home").forward(request, response);
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
