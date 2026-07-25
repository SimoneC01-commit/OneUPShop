package controller.servlet.admin.gestioneOrdini;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dettaglioOrdine.DettaglioOrdineBean;
import model.dettaglioOrdine.DettaglioOrdineDAO;
import model.ordine.OrdineBean;
import model.ordine.OrdineDAO;

/**
 * Servlet implementation class ElencoOrdini
 */
@WebServlet("/ElencoOrdini")
public class ElencoOrdini extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ElencoOrdini() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			List<OrdineBean> ordini = new OrdineDAO().doRetrieveAll();
			List<DettaglioOrdineBean> dettagliOrdini = new DettaglioOrdineDAO().doRetrieveAll();
			
			request.setAttribute("ordini", ordini);
			request.setAttribute("dettagliOrdini", dettagliOrdini);
			
			request.getRequestDispatcher("/WEB-INF/elencoOrdini.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			request.setAttribute("errorMessage", "Errore durante il recupero ordini. Riprova.");
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
