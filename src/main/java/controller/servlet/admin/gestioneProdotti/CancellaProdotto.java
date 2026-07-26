package controller.servlet.admin.gestioneProdotti;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;

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
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		Map<String, Object> risposta = new HashMap<>();
		
		Gson gson = new Gson();
		
		String idProdottoStr = request.getParameter("idProdotto");
		
		if(idProdottoStr == null || idProdottoStr.trim().isEmpty()) {
			risposta.put("esito", false);
			risposta.put("messaggio", "Prodotto non trovato.");
			
			response.getWriter().write(gson.toJson(risposta));
			
			return;
		}
		
		try {	
			int idProdotto = Integer.parseInt(idProdottoStr);
			
			ProdottoBean prodotto = new ProdottoDAO().doRetrieveByKey(idProdotto);
			
			if(prodotto == null) {
				risposta.put("esito", false);
				risposta.put("messaggio", "Prodotto non trovato.");
			}
			else {
				ProdottoDAO epDAO = new ProdottoDAO();
				
				epDAO.doDelete(idProdotto);
				
				risposta.put("esito", true);
				risposta.put("messaggio", "Prodotto eliminato.");
			}
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			risposta.put("esito", false);
			risposta.put("messaggio", "Formato ID prodotto non valido.");
			
						
		} catch (SQLException e) {
			e.printStackTrace();
			risposta.put("esito", false);
			risposta.put("messaggio", "Errore del database durante l'eliminazione.");
		}
		
		response.getWriter().write(gson.toJson(risposta));
	}

}
