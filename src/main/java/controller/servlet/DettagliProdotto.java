package controller.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.cabinato.CabinatoBean;
import model.cabinato.CabinatoDAO;
import model.console.ConsoleBean;
import model.console.ConsoleDAO;
import model.gadget.GadgetBean;
import model.gadget.GadgetDAO;
import model.gioco.GiocoBean;
import model.gioco.GiocoDAO;
import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;

/**
 * Servlet implementation class DettagliProdotto
 */
@WebServlet("/DettagliProdotto")
public class DettagliProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DettagliProdotto() {
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
			
			int idProdotto = Integer.parseInt(idProdottoStr);
			
			ProdottoBean prodotto = new ProdottoDAO().doRetrieveByKey(idProdotto);
			
			if(prodotto == null) {
				response.sendError(404, "Il prodotto non esiste...");
				return;
			}
			
			if(prodotto.getTipo().equals("Cabinato")) {
				CabinatoBean cabinato = new CabinatoDAO().doRetrieveByKey(prodotto.getIdProdotto());
				
				request.setAttribute("tipo", cabinato);
			} else {
				if(prodotto.getTipo().equals("Console")) {
					ConsoleBean console = new ConsoleDAO().doRetrieveByKey(prodotto.getIdProdotto());
					
					request.setAttribute("tipo", console);
				} else {
					if(prodotto.getTipo().equals("Gadget")) {
						GadgetBean gadget = new GadgetDAO().doRetrieveByKey(prodotto.getIdProdotto());
						
						request.setAttribute("tipo", gadget);
					} else {
						if(prodotto.getTipo().equals("Gioco")) {
							GiocoBean gioco = new GiocoDAO().doRetrieveByKey(prodotto.getIdProdotto());
							
							request.setAttribute("tipo", gioco);
						} else {
							response.sendError(404, "Il prodotto non ha tipo...");
							return;
						}
					}
				}
			}
			
			List<ProdottoBean> consigliati = new ProdottoDAO().doRetrieveAllSuggested().subList(0, 3);
			
			request.setAttribute("prodotto", prodotto);
			request.setAttribute("consigliati", consigliati);
			
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/dettagliProdotto.jsp");
			rd.forward(request, response);
			
		} catch(NullPointerException e) {
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
