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

import model.autentificazione.UtenteBean;
import model.wishlist.ElementoWishlistBean;
import model.wishlist.WishlistDAO;

/**
 * Servlet implementation class Wishlist
 */
@WebServlet("/Wishlist")
public class Wishlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Wishlist() {
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

		try {
			ArrayList<ElementoWishlistBean> lista = new WishlistDAO().doRetrieveAllByKey(utente.getEmail());

			request.setAttribute("wishlist", lista);
			
			request.getRequestDispatcher("/WEB-INF/wishlist.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			request.setAttribute("errorMessage", "Errore durante il recupero della wishlist. Riprova.");
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
