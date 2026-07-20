package controller.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.utente.UtenteBean;

/**
 * Servlet Filter implementation class Autenticazione
 */
@WebFilter(filterName ="/Autenticazione", urlPatterns = {
		"/CancellazioneOrdine",
		"/DettagliOrdine",
		"/Ordini",
		"/AggiungiAllaWishlist",
		"/RimuoviDallaWishlist",
		"/SvuotaWishlist",
		"/Wishlist",
		"/Checkout",
		"/Profilo",
		"/ModificaProfilo"
})
public class Autenticazione extends HttpFilter implements Filter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public Autenticazione() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		HttpSession sessione = httpRequest.getSession();
		
		UtenteBean utente = (UtenteBean) sessione.getAttribute("utente");
		
		if(utente == null) {

			sessione.setAttribute("errorMessage", "Devi essere autenticato per poter accedere a questa pagina.");
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/Login");
			
			return;
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
