package controller.servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.utility.PasswordEncrypter;
import model.autentificazione.UtenteBean;
import model.autentificazione.UtenteDAO;

/**
 * Servlet implementation class Registrazione
 */
@WebServlet("/Registrazione")
public class Registrazione extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registrazione() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/registrazione.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String email = request.getParameter("email");
		String passwordUtente = request.getParameter("password");
		
		if (nome == null || nome.isEmpty() || 
				cognome == null || cognome.isEmpty() || 
				email == null || email.isEmpty() || 
				passwordUtente == null || passwordUtente.isEmpty()) {
				
				request.setAttribute("errorMessage", "Errore compilazione form.");
				request.getRequestDispatcher("/WEB-INF/registrazione.jsp").forward(request, response);
				return;
		}
		
		UtenteDAO dao = new UtenteDAO();
		
		try {
			if(dao.doRetrieveByKey(email) != null) {
				request.setAttribute("errorMessage", "Un account con questa email è già registrato.");
				request.getRequestDispatcher("/WEB-INF/registrazione.jsp").forward(request, response);
				return;
			}
			
			String passwordCifrata = PasswordEncrypter.toHash(passwordUtente, email);
			
			UtenteBean utente = new UtenteBean();
			
			utente.setNome(nome);
			utente.setCognome(cognome);
			utente.setEmail(email);
			utente.setPassword(passwordCifrata);
			
			dao.doSave(utente);
			
			response.sendRedirect(request.getContextPath() + "/WEB-INF/login.jsp?registrato=true");
		}
		catch(SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore durante la registrazione. Riprova.");
			request.getRequestDispatcher("/WEB-INF/registrazione.jsp").forward(request, response);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore durante la registrazione. Riprova.");
			request.getRequestDispatcher("/WEB-INF/registrazione.jsp").forward(request, response);
		}
	}

}
