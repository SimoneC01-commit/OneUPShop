package controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Error
 */
@WebServlet("/Error")
public class Error extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Error() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Integer status = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        
        String errorTitle = "Errore Generico";
        String errorDetail = "Si è verificato un problema imprevisto durante la navigazione.";

        if (throwable != null && throwable.getMessage() != null && !throwable.getMessage().trim().isEmpty()) {
            errorTitle = throwable.getMessage();
        } else if (status != null && status > 0) {
            errorTitle = "Errore HTTP " + status;
        }

        if (status != null) {
            if (status == 404) {
                errorDetail = "La pagina o la risorsa che stai cercando non esiste o è stata spostata.";
            } else if (status >= 400 && status < 500) {
                errorDetail = "Qualcosa è andato storto nella richiesta inviata al server.";
            } else if (status >= 500) {
                errorDetail = "Si è verificato un errore interno al server. Riprova più tardi.";
            }
        }
        
        request.setAttribute("errorTitle", errorTitle);
        request.setAttribute("errorDetail", errorDetail);
        request.setAttribute("statusCode", status);
        
        request.getRequestDispatcher("/WEB-INF/common/error.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
