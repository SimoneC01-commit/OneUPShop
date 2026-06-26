package controller.servlet;

import model.prodottiHome.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProdottiHome
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			List<ProdottiHomeBean> prodNuovi = new ProdottiHomeNuoviDAO().doRetrieveAll();
			
			List<ProdottiHomeBean> prodConsigliati = new ProdottiHomeConsigliatiDAO().doRetrieveAll();
			
			request.setAttribute("prodottiHomeNuovi", prodNuovi);
			
			request.setAttribute("prodottiHomeConsigliati", prodConsigliati);
		}
		catch(SQLException e){
			e.printStackTrace();
			
			request.setAttribute("errorMessage", "Errore nel caricamento della home");
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/home.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
