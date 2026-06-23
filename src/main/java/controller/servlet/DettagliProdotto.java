package controller.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dettagliProdotto.DettagliProdottoBean;
import model.dettagliProdotto.DettagliProdottoDAO;

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
		}
		
		try {
			
			Integer idProdotto = Integer.parseInt(idProdottoStr);
			
			DettagliProdottoBean bean = new DettagliProdottoDAO().doRetrieveByKey(idProdotto);
			
			if(bean == null) {
				response.sendError(404, "Il prodotto non esiste...");
				return;
			}
			
			request.setAttribute("prodotto", bean);
			
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
