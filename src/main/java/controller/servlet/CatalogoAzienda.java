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

import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;

/**
 * Servlet implementation class Catalogo
 */
@WebServlet("/CatalogoAzienda")
public class CatalogoAzienda extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CatalogoAzienda() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String azienda = request.getParameter("azienda");
		
		if(azienda == null || azienda.trim().isEmpty() ||
				!(
						azienda.toLowerCase().equals("nintendo") || 
						azienda.toLowerCase().equals("sony") || 
						azienda.toLowerCase().equals("microsoft") || 
						azienda.toLowerCase().equals("sega"))
				) {

			response.sendRedirect(request.getContextPath() + "/Catalogo");
			return;
		}

		String minYearStr = request.getParameter("minYear");
		String maxYearStr = request.getParameter("maxYear");
		String tipo = request.getParameter("tipo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		String stato = request.getParameter("stato");
		String pagCorrenteStr = request.getParameter("pagCorrente");
		
		Integer minYear = (minYearStr != null && !minYearStr.isEmpty()) ? Integer.parseInt(minYearStr) : null;
		Integer maxYear = (maxYearStr != null && !maxYearStr.isEmpty()) ? Integer.parseInt(maxYearStr) : null;
		
		Float minPrice = (minPriceStr != null && !minPriceStr.isEmpty()) ? Float.parseFloat(minPriceStr) : null;
		Float maxPrice = (maxPriceStr != null && !maxPriceStr.isEmpty()) ? Float.parseFloat(maxPriceStr) : null;
		
		Integer pagCorrente = (pagCorrenteStr != null && !pagCorrenteStr.isEmpty()) ? Integer.parseInt(pagCorrenteStr) : 1;
		
		int elemForPage = 8;
		
		int nProd = 0;

		int totPag = 1;
		
		List<ProdottoBean> lista = null;
		
		ProdottoDAO cDAO = new ProdottoDAO();
		
		try{
			nProd = cDAO.doCountByFiltersAndAzienda(azienda, minYear, maxYear, tipo, minPrice, maxPrice, stato);
			
			totPag = (int) Math.ceil((double) nProd / elemForPage);
			
			if(totPag == 0) {
				totPag = 1;
			}
			
			lista = cDAO.doRetriveAllByPageNumberAndAzienda(azienda, minYear, maxYear, tipo, minPrice, maxPrice, stato, pagCorrente, elemForPage);
			
			request.setAttribute("paginaCorrente", pagCorrente);
			request.setAttribute("numeroProdotti", nProd);
			request.setAttribute("totalePagine", totPag);
			request.setAttribute("listaProdotti", lista);
		}
		catch(SQLException e){
			e.printStackTrace();
			
			response.sendRedirect(request.getContextPath() + "/Catalogo");
			return;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/catalogoAzienda.jsp");
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
