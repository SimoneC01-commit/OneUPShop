package controller.servlet.carrello;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.carrello.Carrello;

/**
 * Servlet implementation class PulisciCarrello
 */
@WebServlet("/PulisciCarrello")
public class PulisciCarrello extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PulisciCarrello() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession sessione = request.getSession();
		
		Carrello carrello = (Carrello) sessione.getAttribute("carrello");
		
		if (carrello == null) {
            carrello = new Carrello();
            sessione.setAttribute("carrello", carrello);
        }
		
		carrello.svuota();
		
		response.sendRedirect(request.getContextPath() + "/DettagliCarrello");		
	}

}
