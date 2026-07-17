package controller.servlet.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.admin.elencoOrdini.ElencoOrdiniDAO;
import model.admin.elencoOrdini.OrdineBean;
import model.autentificazione.UtenteBean;

/**
 * Servlet implementation class ElencoOrdini
 */
@WebServlet("/ElencoOrdini")
public class ElencoOrdini extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ElencoOrdini() {
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
		
		if(utente == null || !"Admin".equals(utente.getRuolo())) {
			request.setAttribute("errorMessage", "Non hai i diritti di accesso a questa pagina.");
			request.getRequestDispatcher("/Home").forward(request, response);
			return;
		}
		
		try {
			ArrayList<OrdineBean> ordini = new ElencoOrdiniDAO().doRetrieveAll();
			
			request.setAttribute("ordini", ordini);
			
			request.getRequestDispatcher("/WEB-INF/elencoOrdini.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			request.setAttribute("errorMessage", "Errore durante il recupero ordini. Riprova.");
			request.getRequestDispatcher("/Profilo").forward(request, response);
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
