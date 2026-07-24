package controller.servlet.utente.profilo;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.utility.HtmlDecoder;
import controller.utility.PasswordEncrypter;
import model.utente.UtenteBean;
import model.utente.UtenteDAO;

/**
 * Servlet implementation class ModificaProfilo
 */
@WebServlet("/ModificaProfilo")
public class ModificaProfilo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaProfilo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/modificaProfilo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nuovoNome = request.getParameter("nuovoNome");
		String nuovoCognome = request.getParameter("nuovoCognome");
		String nuovaPassword = request.getParameter("nuovaPassword");
		
		String nameRegex = "^[A-Z][a-z]*(?: [A-Z][a-z]*)*$";
		String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,100}$";
		
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
		
		try {
			UtenteDAO uDAO = new UtenteDAO();
			
			UtenteBean utenteModificato = new UtenteBean();
			
			boolean valid = true;
			
			if(nuovoNome == null || nuovoNome.trim().isEmpty()) {
				utenteModificato.setNome(HtmlDecoder.encodeHtmlEntities(utente.getNome()));
			}
			else {
				if(nuovoNome.matches(nameRegex)) {
					utenteModificato.setNome(HtmlDecoder.encodeHtmlEntities(nuovoNome));
				}
				else {
					valid = false;
				}
			}
			
			if(nuovoCognome == null || nuovoCognome.trim().isEmpty()) {
				utenteModificato.setCognome(HtmlDecoder.encodeHtmlEntities(utente.getCognome()));
			}
			else {
				if(nuovoCognome.matches(nameRegex)) {
					utenteModificato.setCognome(HtmlDecoder.encodeHtmlEntities(nuovoCognome));
				}
				else {
					valid = false;
				}
			}
			
			if(nuovaPassword == null || nuovaPassword.trim().isEmpty()) {
				utenteModificato.setPassword(utente.getPassword());
			}
			else {
				if(nuovaPassword.matches(passwordRegex)) {
					String hashNuovaPassword = PasswordEncrypter.toHash(nuovaPassword);
					
					utenteModificato.setPassword(hashNuovaPassword);
				}
				else {
					valid = false;
				}
			}
			
			if(valid) {

				utenteModificato.setEmail(utente.getEmail());
				utenteModificato.setRuolo(utente.getRuolo());
				
				uDAO.doUpdate(utenteModificato);
				
				utente = uDAO.doRetrieveByKey(utenteModificato.getEmail());

				request.getSession().setAttribute("utente", utente);
				
				response.sendRedirect(request.getContextPath() + "/Profilo");
			}
			else {
				request.setAttribute("errorMessage", "Errore compilazione form.");
				request.getRequestDispatcher("/WEB-INF/modificaProfilo.jsp").forward(request, response);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore durante la modifica. Riprova.");
			request.getRequestDispatcher("/WEB-INF/modificaProfilo.jsp").forward(request, response);
			
		} catch(NoSuchAlgorithmException e) { 
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore durante la modifica. Riprova.");
			request.getRequestDispatcher("/WEB-INF/modificaProfilo.jsp").forward(request, response);
		}
	}

}
