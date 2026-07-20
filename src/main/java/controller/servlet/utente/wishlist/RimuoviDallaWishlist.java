package controller.servlet.utente.wishlist;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;
import model.utente.UtenteBean;
import model.wishlist.WishlistDAO;
import model.wishlist.WishlistKey;

/**
 * Servlet implementation class RimuoviDallaWishlist
 */
@WebServlet("/RimuoviDallaWishlist")
public class RimuoviDallaWishlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RimuoviDallaWishlist() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String idProdottoStr = request.getParameter("idProdotto");
		
		if(idProdottoStr == null || idProdottoStr.isEmpty()) {
			response.sendError(404, "Campo ID mancante");
			return;
		}
		
		try {
			
			Integer idProdotto = Integer.parseInt(idProdottoStr);
			
			ProdottoBean prodotto = new ProdottoDAO().doRetrieveByKeyAndAvailable(idProdotto);
			
			if(prodotto != null) {
				HttpSession sessione = request.getSession();
				
				UtenteBean utente = (UtenteBean) sessione.getAttribute("utente");

				WishlistKey key = new WishlistKey();
				
				key.setEmailUtente(utente.getEmail());
				key.setIdProdotto(prodotto.getIdProdotto());
				
				WishlistDAO wDAO = new WishlistDAO();
				
				wDAO.doDelete(key);
				
				response.sendRedirect(request.getContextPath() + "/Wishlist");
			}
			else {
				response.sendError(404, "Prodotto non trovato");
	            return;
			}
			
		} catch(NumberFormatException e) {
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
