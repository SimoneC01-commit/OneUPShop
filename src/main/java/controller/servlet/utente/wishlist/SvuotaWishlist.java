package controller.servlet.utente.wishlist;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.utente.UtenteBean;
import model.wishlist.WishlistDAO;

/**
 * Servlet implementation class SvuotaWishlist
 */
@WebServlet("/SvuotaWishlist")
public class SvuotaWishlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SvuotaWishlist() {
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
			WishlistDAO wDAO = new WishlistDAO();
			
			wDAO.doDeleteAllForUser(utente.getEmail());
			
			response.sendRedirect(request.getContextPath() + "/Wishlist");
			
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
