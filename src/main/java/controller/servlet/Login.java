package controller.servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.utility.PasswordEncrypter;
import model.autentificazione.UtenteBean;
import model.autentificazione.UtenteDAO;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String passwordUtente = request.getParameter("password");

		if(email == null || email.isEmpty() || 
				passwordUtente == null || passwordUtente.isEmpty()) {
			
			request.setAttribute("errorMessage", "Errore compilazione form.");
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			return;
		}
		
		UtenteDAO uDAO = new UtenteDAO();

		try {
			UtenteBean utente = uDAO.doRetrieveByKey(email);
			
			if(utente == null) {
				request.setAttribute("errorMessage", "Non è registrato nessun account con questa email.");
				request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
				return;
			}
			
			String passwordCifrata = PasswordEncrypter.toHash(passwordUtente, email);
			
			if(utente.getPassword().equals(passwordCifrata)) {
				
				HttpSession sessione = request.getSession(false);
				if (sessione != null) {
					sessione.invalidate();
				}
				
				sessione = request.getSession(true);
				sessione.setAttribute("utente", utente);
				
				response.sendRedirect(request.getContextPath() + "/Home");
				return;
			}
			else {
				request.setAttribute("errorMessage", "Email o Password errati.");
			    request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
			    return;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore durante il login. Riprova.");
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore durante il login. Riprova.");
			request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		}

	}

}
