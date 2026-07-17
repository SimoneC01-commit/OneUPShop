package controller.servlet.admin;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.admin.elencoProdotti.CabinatoBean;
import model.admin.elencoProdotti.ConsoleBean;
import model.admin.elencoProdotti.ElencoProdottiDAO;
import model.admin.elencoProdotti.GadgetBean;
import model.admin.elencoProdotti.GiocoBean;
import model.admin.elencoProdotti.ProdottoElencoBean;
import model.autentificazione.UtenteBean;

/**
 * Servlet implementation class AggiungiProdotto
 */
@WebServlet("/AggiungiProdotto")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
	    maxFileSize = 1024 * 1024 * 10,       // 10MB
	    maxRequestSize = 1024 * 1024 * 50     // 50MB
	)
public class AggiungiProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AggiungiProdotto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("/WEB-INF/aggiungiProdotto.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession sessione = request.getSession();
		
		UtenteBean utente = (UtenteBean) sessione.getAttribute("utente");
		
		if(utente == null || !"Admin".equals(utente.getRuolo())) {
			request.setAttribute("errorMessage", "Non hai i diritti di accesso a questa pagina.");
			response.sendRedirect(request.getContextPath() + "/Home");
			return;
		}
		
		String tipo = request.getParameter("tipo");
	    ProdottoElencoBean prodotto = null;
	    
	    if(tipo == null || tipo.trim().isEmpty()) {
	    	request.setAttribute("errorMessage", "Errore compilazione form. Tutti i campi sono obbligatori.");
		    request.getRequestDispatcher("/WEB-INF/aggiungiProdotto.jsp").forward(request, response);
		    return;
	    }

	    if ("Gioco".equals(tipo)) {
	        GiocoBean gioco = new GiocoBean();
	        gioco.setSviluppatore(request.getParameter("sviluppatore"));
	        prodotto = gioco;
	        
	    } else if ("Console".equals(tipo)) {
	        ConsoleBean console = new ConsoleBean();
	        console.setModelloSpecifico(request.getParameter("modelloSpecifico"));
	        prodotto = console;
	        
	    } else if ("Gadget".equals(tipo)) {
	        GadgetBean gadget = new GadgetBean();
	        gadget.setTipoMateriale(request.getParameter("tipoMateriale"));
	        gadget.setTipoGadget(request.getParameter("tipoGadget"));
	        prodotto = gadget;
	        
	    } else if ("Cabinato".equals(tipo)) {
	        CabinatoBean cabinato = new CabinatoBean();
	        cabinato.setTipoSistemaArcade(request.getParameter("tipoSistemaArcade"));
	        cabinato.setDimensioniCm(request.getParameter("dimensioniCm"));
	        prodotto = cabinato;
	        
	    }
	    
	    if(prodotto == null) {
	    	request.setAttribute("errorMessage", "Errore compilazione form. Tutti i campi sono obbligatori.");
		    request.getRequestDispatcher("/WEB-INF/aggiungiProdotto.jsp").forward(request, response);
		    return;
	    }
	    
	    try {
	        String annoStr = request.getParameter("annoRilascio");
	        String ivaStr = request.getParameter("iva");
	        
	        if (annoStr == null || annoStr.isEmpty() || ivaStr == null || ivaStr.isEmpty()) {
	            throw new IllegalArgumentException("Anno di rilascio e IVA sono obbligatori.");
	        }
	        
		    prodotto.setTitolo(request.getParameter("titolo"));
	        prodotto.setDescrizione(request.getParameter("descrizione"));
	        prodotto.setAnnoRilascio(Integer.parseInt(request.getParameter("annoRilascio")));
	        prodotto.setAzienda(request.getParameter("azienda"));
	        prodotto.setTipo(tipo);
	        prodotto.setDataAggiunta(new java.sql.Date(System.currentTimeMillis()));
	        prodotto.setIva(Integer.parseInt(request.getParameter("iva")));
	
	        String stato = request.getParameter("stato");
	        prodotto.setStato(stato);
	        
	        if ("Nuovo".equalsIgnoreCase(stato)) {
	            prodotto.setNoteDifetti(null);
	        } else {
	            prodotto.setNoteDifetti(request.getParameter("noteDifetti"));
	        }
	
	        String prezzoAcquistoStr = request.getParameter("prezzoAcquisto");
	        BigDecimal prezzoAcquisto = new BigDecimal(prezzoAcquistoStr);
	
	        if (prezzoAcquisto.compareTo(new BigDecimal("-1")) == 0) {
	            prodotto.setPrezzoAcquisto(null);
	            prodotto.setPrezzoAttuale(new BigDecimal(request.getParameter("prezzoAttuale")));
	        } else {
	            prodotto.setPrezzoAcquisto(prezzoAcquisto);
	            prodotto.setPrezzoAttuale(prezzoAcquisto.multiply(new BigDecimal("1.5")));
	        }
	
	        Part filePart = request.getPart("foto");
	        if (filePart != null && filePart.getSize() > 0) {
	            try (InputStream inputStream = filePart.getInputStream()) {
	                prodotto.setFotoBlob(inputStream.readAllBytes());
	            }
	        }

            ElencoProdottiDAO dao = new ElencoProdottiDAO();
            dao.doSave(prodotto);
            response.sendRedirect(request.getContextPath() + "/ElencoProdotti");
            
	    } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Dati inseriti non validi: " + e.getMessage());
                request.getRequestDispatcher("/WEB-INF/aggiungiProdotto.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Errore nel salvataggio del prodotto.");
            request.getRequestDispatcher("/WEB-INF/aggiungiProdotto.jsp").forward(request, response);
        }
    }

}
