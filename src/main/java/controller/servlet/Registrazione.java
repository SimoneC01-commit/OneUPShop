package controller.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import controller.utility.HtmlDecoder;
import controller.utility.PasswordEncrypter;
import model.utente.UtenteBean;
import model.utente.UtenteDAO;

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
		String email = request.getParameter("email");
		String ajax = request.getParameter("ajax");

		UtenteDAO dao = new UtenteDAO();
		
		if(ajax != null) {
		
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			boolean esiste;
			
			try {
				esiste = dao.doRetrieveByKey(email) != null;
				
				Map<String, Object> risposta = new HashMap<>();
				
				risposta.put("esiste", esiste);
				
				if(esiste) {
					
					risposta.put("messaggio", "Un account con questa email è già registrato.");
				}
				
				response.getWriter().write(new Gson().toJson(risposta));
				
				return;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.setStatus(500);
				return;
			}
		}
		

		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String passwordUtente = request.getParameter("password");
		
		String nameRegex = "^[A-Z][a-z]*(?: [A-Z][a-z]*)*$";
		String emailRegex = "^[a-zA-Z0-9]+([.-][a-zA-Z0-9]+)*@[a-zA-Z0-9]+([.-][a-zA-Z0-9]+)*\\.[a-zA-Z]{2,10}$";
		String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,100}$";
		
		if (nome == null || !nome.matches(nameRegex) || (nome.length() < 2 || nome.length() > 100) ||
				cognome == null || !cognome.matches(nameRegex) || (cognome.length() < 2 || cognome.length() > 100) ||
				email == null || !email.matches(emailRegex) || (email.length() > 100) ||
				passwordUtente == null || !passwordUtente.matches(passwordRegex)) {
				
			request.setAttribute("errorMessage", "Errore compilazione form.");
			request.getRequestDispatcher("/WEB-INF/registrazione.jsp").forward(request, response);
			return;
		}
		
		try {
			
			boolean result = dao.doRetrieveByKey(email) != null;
			
			if(result) {
				
				request.setAttribute("errorMessage", "Un account con questa email è già registrato.");
				request.getRequestDispatcher("/WEB-INF/registrazione.jsp").forward(request, response);
				return;
			}
			
			String passwordCifrata = PasswordEncrypter.toHash(passwordUtente);
			
			UtenteBean utente = new UtenteBean();
			
			utente.setNome(HtmlDecoder.encodeHtmlEntities(nome));
			utente.setCognome(HtmlDecoder.encodeHtmlEntities(cognome));
			utente.setEmail(HtmlDecoder.encodeHtmlEntities(email));
			utente.setPassword(passwordCifrata);
			utente.setRuolo("Cliente");
			utente.setSaldoWallet(new BigDecimal(0));
			
			dao.doSave(utente);
			
			response.sendRedirect(request.getContextPath() + "/Login?registrato=true");
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
