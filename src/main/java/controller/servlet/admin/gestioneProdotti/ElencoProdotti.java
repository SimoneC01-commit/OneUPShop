package controller.servlet.admin.gestioneProdotti;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;
import model.utente.UtenteBean;

/**
 * Servlet implementation class ElencoProdotti
 */
@WebServlet("/ElencoProdotti")
public class ElencoProdotti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ElencoProdotti() {
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
			List<ProdottoBean> prodotti = new ProdottoDAO().doRetrieveAll();
			
			request.setAttribute("prodotti", prodotti);
			
			request.getRequestDispatcher("/WEB-INF/elencoProdotti.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			request.setAttribute("errorMessage", "Errore durante il recupero prodotti. Riprova.");
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
