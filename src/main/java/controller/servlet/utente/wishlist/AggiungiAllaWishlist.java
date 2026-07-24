package controller.servlet.utente.wishlist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;
import model.utente.UtenteBean;
import model.wishlist.WishlistBean;
import model.wishlist.WishlistDAO;
import model.wishlist.WishlistKey;

/**
 * Servlet implementation class AggiungiAllaWishlist
 */
@WebServlet("/AggiungiAllaWishlist")
public class AggiungiAllaWishlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiungiAllaWishlist() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		Map<String, Object> risposta = new HashMap<>();
		
		Gson gson = new Gson();

		String idProdottoStr = request.getParameter("idProdotto");
		
		if(idProdottoStr == null || idProdottoStr.isEmpty()) {
			risposta.put("esito", false);
			risposta.put("messaggio", "Campo ID mancante.");
			
			response.getWriter().write(gson.toJson(risposta));
			
			return;
		}
		
		try {
			
			Integer idProdotto = Integer.parseInt(idProdottoStr);
			
			ProdottoBean prodotto = new ProdottoDAO().doRetrieveByKeyAndAvailable(idProdotto);
			
			if(prodotto != null) {
				
				HttpSession sessione = request.getSession();
				
				UtenteBean utente = (UtenteBean) sessione.getAttribute("utente");
				
				WishlistKey key = new WishlistKey();
				
				key.setIdProdotto(idProdotto);
				key.setEmailUtente(utente.getEmail());
				
				WishlistDAO wDAO = new WishlistDAO();
				
				if(wDAO.doRetrieveByKey(key) == null) {
					WishlistBean bean = new WishlistBean();
					
					bean.setEmailUtente(utente.getEmail());
					bean.setProdotto(prodotto);
					
					wDAO.doSave(bean);
					
					risposta.put("esito", true);
					risposta.put("messaggio", "Aggiunto alla Wishlist!");

				}
				else {
					risposta.put("esito", false);
					risposta.put("messaggio", "Prodotto già presente!");
				}
				
			}
			else {
				risposta.put("esito", false);
				risposta.put("messaggio", "Prodotto non trovato.");
			}
			
		} catch(NumberFormatException e) {
			e.printStackTrace();
			
			risposta.put("esito", false);
			risposta.put("messaggio", "ID prodotto non valido.");
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			risposta.put("esito", false);
			risposta.put("messaggio", "Errore nel database.");
		}
		
		response.getWriter().write(gson.toJson(risposta));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
