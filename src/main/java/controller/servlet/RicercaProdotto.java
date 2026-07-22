package controller.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;

/**
 * Servlet implementation class RicercaProdotto
 */
@WebServlet("/RicercaProdotto")
public class RicercaProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RicercaProdotto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String q = request.getParameter("q");
		
		List<ProdottoBean> lista = null;
		
		ProdottoDAO rDAO = new ProdottoDAO();
		
		try {
			lista = rDAO.doRetrieveAllByTitolo(q);
			
			String ajax = request.getParameter("ajax");
			
			if(ajax != null) {
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				
				String risposta = new Gson().toJson(lista);
				
				response.getWriter().write(risposta);
				
				return;
			}
			
			if(lista.size() == 0) {
				response.sendError(404, "Prodotto non trovato");
				return;
			}
			
			if(lista.size() == 1) {
				response.sendRedirect(request.getContextPath() + "/DettagliProdotto?idProdotto=" + lista.get(0).getIdProdotto());
				return;
			}
			
			request.setAttribute("risultati", lista);
			request.getRequestDispatcher("/WEB-INF/ricercaProdotto.jsp").forward(request, response);
			return;
			
		} catch(SQLException e) {
			e.printStackTrace();
			
			request.setAttribute("errorMessage", "Errore nella ricerca del prodotto");
			request.getRequestDispatcher("/Home").forward(request, response);
			return;
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
