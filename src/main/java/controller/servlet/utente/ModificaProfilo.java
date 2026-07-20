package controller.servlet.utente;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		if(nuovoNome == null || nuovoNome.trim().isEmpty() ||
				nuovoCognome == null || nuovoCognome.trim().isEmpty() ||
				nuovaPassword == null || nuovaPassword.trim().isEmpty()) {
			
			request.setAttribute("errorMessage", "Errore compilazione form.");
			request.getRequestDispatcher("/WEB-INF/modificaProfilo.jsp").forward(request, response);
			return;
		}
		
		UtenteBean utente = (UtenteBean) request.getSession().getAttribute("utente");
		
		try {
			UtenteDAO uDAO = new UtenteDAO();
			
			UtenteBean utenteModificato = new UtenteBean();
			
			String hashNuovaPassword = PasswordEncrypter.toHash(nuovaPassword);
			
			utenteModificato.setNome(nuovoNome);
			utenteModificato.setCognome(nuovoCognome);
			utenteModificato.setEmail(utente.getEmail());
			utenteModificato.setPassword(hashNuovaPassword);
			utenteModificato.setSaldoWallet(utente.getSaldoWallet());
			utenteModificato.setRuolo(utente.getRuolo());
			
			uDAO.doUpdate(utenteModificato);
			
			utente = uDAO.doRetrieveByKey(utente.getEmail());

			request.getSession().setAttribute("utente", utenteModificato);
			
			response.sendRedirect(request.getContextPath() + "/Profilo");
		}
		catch(SQLException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore durante la modifica. Riprova.");
			request.getRequestDispatcher("/WEB-INF/modificaProfilo.jsp").forward(request, response);
		}
	}

}
