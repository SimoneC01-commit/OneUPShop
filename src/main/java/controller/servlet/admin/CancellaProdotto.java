package controller.servlet.admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.admin.elencoProdotti.ElencoProdottiDAO;
import model.admin.elencoProdotti.ProdottoBean;
import model.autentificazione.UtenteBean;

/**
 * Servlet implementation class CancellaProdotto
 */
@WebServlet("/CancellaProdotto")
public class CancellaProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancellaProdotto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession sessione = request.getSession();

		UtenteBean utente = (UtenteBean) sessione.getAttribute("utente");
		
		if(utente == null || !"Admin".equals(utente.getRuolo())) {
			request.setAttribute("errorMessage", "Non hai i diritti di accesso a questa pagina.");
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			return;
		}
		
		String idProdottoStr = request.getParameter("id");
		
		if(idProdottoStr == null || idProdottoStr.trim().isEmpty()) {
			response.sendError(404, "Campo ID mancante");
			return;
		}
		
		try {	
			int idProdotto = Integer.parseInt(idProdottoStr);
			
			ProdottoBean prodotto = new ElencoProdottiDAO().doRetrieveByKey(idProdotto);
			
			if(prodotto == null) {
				response.sendError(404, "Prodotto non trovato");
				return;
			}
			
			ElencoProdottiDAO epDAO = new ElencoProdottiDAO();
			
			epDAO.removeProdottoByKey(idProdotto);
			
			response.sendRedirect(request.getContextPath() + "/ElencoProdotti");
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
