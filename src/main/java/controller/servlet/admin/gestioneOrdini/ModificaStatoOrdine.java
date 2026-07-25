package controller.servlet.admin.gestioneOrdini;

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

import model.ordine.OrdineBean;
import model.ordine.OrdineDAO;

/**
 * Servlet implementation class ModificaStatoOrdine
 */
@WebServlet("/ModificaStatoOrdine")
public class ModificaStatoOrdine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaStatoOrdine() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect(request.getContextPath() + "/ElencoOrdini");
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
		
		String nuovoStatoOrdine = request.getParameter("statoOrdine");
		String idOrdineStr = request.getParameter("idOrdine");
		
		if(nuovoStatoOrdine == null ||
				(!nuovoStatoOrdine.equals("In elaborazione") &&
				!nuovoStatoOrdine.equals("Spedito") &&
				!nuovoStatoOrdine.equals("Consegnato"))) {
			
			risposta.put("esito", false);
			risposta.put("messaggio", "Nuovo stato ordine non valido.");
			
			response.getWriter().write(gson.toJson(risposta));
			
			return;
		}
		
		if(idOrdineStr == null || idOrdineStr.trim().isEmpty()) {
			
			risposta.put("esito", false);
			risposta.put("messaggio", "ID ordine errato.");
			
			response.getWriter().write(gson.toJson(risposta));
			
			return;
		}
		
		try {
			int idOrdine = Integer.parseInt(idOrdineStr);
			
			OrdineDAO oDAO = new OrdineDAO();
			
			OrdineBean ordineEsistente = oDAO.doRetrieveByKey(idOrdine);
			
			if(ordineEsistente != null) {
				
				if(nuovoStatoOrdine.equals(ordineEsistente.getStatoOrdine())) {
					risposta.put("esito", true);
					risposta.put("messaggio", "L'ordine " + idOrdine + " si trova già in questo stato!");
				}
				else {
					oDAO.doUpdateStato(idOrdine, nuovoStatoOrdine);
					
					risposta.put("esito", true);
					risposta.put("messaggio", "Stato ordine " + idOrdine + " modificato!");
					risposta.put("nuovoStato", nuovoStatoOrdine);
				}
			}
			else {
				risposta.put("esito", false);
				risposta.put("messaggio", "L'ordine non esiste!");
			}
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			risposta.put("esito", false);
			risposta.put("messaggio", "Formato ID ordine non valido.");
			
						
		} catch (SQLException e) {
			e.printStackTrace();
			risposta.put("esito", false);
			risposta.put("messaggio", "Errore del database durante la modifica.");
		}
		
		response.getWriter().write(gson.toJson(risposta));
	}

}
