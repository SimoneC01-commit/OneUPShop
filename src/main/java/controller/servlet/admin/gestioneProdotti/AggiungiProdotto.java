package controller.servlet.admin.gestioneProdotti;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.cabinato.CabinatoBean;
import model.cabinato.CabinatoDAO;
import model.console.ConsoleBean;
import model.console.ConsoleDAO;
import model.gadget.GadgetBean;
import model.gadget.GadgetDAO;
import model.gioco.GiocoBean;
import model.gioco.GiocoDAO;
import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;
import model.utente.UtenteBean;

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
		ProdottoBean prodotto = null;
	    
	    if(tipo == null || tipo.trim().isEmpty()) {
	    	request.setAttribute("errorMessage", "Errore compilazione form. Tutti i campi sono obbligatori.");
		    request.getRequestDispatcher("/WEB-INF/aggiungiProdotto.jsp").forward(request, response);
		    return;
	    }

	    if ("Cabinato".equals(tipo)) {
	        CabinatoBean cabinato = new CabinatoBean();
	        cabinato.setTipoSistemaArcade(request.getParameter("tipoSistemaArcade"));
	        cabinato.setDimensioniCm(request.getParameter("dimensioniCm"));
	        prodotto = cabinato;
	        
	    } else if ("Console".equals(tipo)) {
	        ConsoleBean console = new ConsoleBean();
	        console.setModelloSpecifico(request.getParameter("modelloSpecifico"));
	        prodotto = console;
	        
	    } else if ("Gadget".equals(tipo)) {
	        GadgetBean gadget = new GadgetBean();
	        gadget.setTipoMateriale(request.getParameter("tipoMateriale"));
	        gadget.setTipoGadget(request.getParameter("tipoGadget"));
	        prodotto = gadget;
	        
	    } else if ("Gioco".equals(tipo)) {
	        GiocoBean gioco = new GiocoBean();
	        gioco.setSviluppatore(request.getParameter("sviluppatore"));
	        prodotto = gioco;
	        
	    }
	    
	    if(prodotto == null) {
	    	request.setAttribute("errorMessage", "Errore compilazione form. Tutti i campi sono obbligatori.");
		    request.getRequestDispatcher("/WEB-INF/aggiungiProdotto.jsp").forward(request, response);
		    return;
	    }
	    
	    try {
	    	
		    prodotto.setTitolo(request.getParameter("titolo"));
	        prodotto.setDescrizione(request.getParameter("descrizione"));
	        prodotto.setAnnoRilascio(Integer.parseInt(request.getParameter("annoRilascio")));
	        prodotto.setAzienda(request.getParameter("azienda"));
	        prodotto.setTipo(tipo);
	        prodotto.setDataAggiunta(new Timestamp(System.currentTimeMillis()));
	        prodotto.setDisponibile(true);
	        
	        int iva = Integer.parseInt(request.getParameter("iva"));
	        prodotto.setIva(iva);
	
	        String stato = request.getParameter("stato");
	        prodotto.setStato(stato);
	        
	        if ("Nuovo".equalsIgnoreCase(stato)) {
	            prodotto.setNoteDifetti(null);
	        } else {
	            prodotto.setNoteDifetti(request.getParameter("noteDifetti"));
	        }
	        
	        BigDecimal prezzoAcquisto = new BigDecimal(request.getParameter("prezzoAcquisto"));
	        BigDecimal prezzoAttualeBase;
	
	        if (prezzoAcquisto.compareTo(new BigDecimal("-1")) == 0) {
	            prodotto.setPrezzoAcquisto(null);
	            prezzoAttualeBase = new BigDecimal(request.getParameter("prezzoAttuale"));
	            
	        } else {
	            prodotto.setPrezzoAcquisto(prezzoAcquisto);
	            prezzoAttualeBase = prezzoAcquisto.multiply(new BigDecimal("1.5"));
	            
	        }
	        
	        BigDecimal percentualeIva = new BigDecimal(iva).divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
	        BigDecimal moltiplicatoreIva = BigDecimal.ONE.add(percentualeIva);
	        
	        BigDecimal prezzoConIva = prezzoAttualeBase.multiply(moltiplicatoreIva).setScale(2, RoundingMode.HALF_UP);
	        
	        BigDecimal parteIntera = prezzoConIva.setScale(0, RoundingMode.DOWN);
	        
	        prezzoConIva= parteIntera.add(new BigDecimal("0.99"));
	        
	        prodotto.setPrezzoAttuale(prezzoConIva);
	
	        Part filePart = request.getPart("foto");
	        if (filePart != null && filePart.getSize() > 0) {
	            try (InputStream inputStream = filePart.getInputStream()) {
	                prodotto.setFotoBlob(inputStream.readAllBytes());
	            }
	        }

            ProdottoDAO dao = new ProdottoDAO();
            dao.doSave(prodotto);
            
            if(prodotto instanceof CabinatoBean) {
            	CabinatoDAO caDAO = new CabinatoDAO();
            	caDAO.doSave((CabinatoBean) prodotto);
            	
            } else if(prodotto instanceof ConsoleBean) {
	            	ConsoleDAO coDAO = new ConsoleDAO();
	            	coDAO.doSave((ConsoleBean) prodotto);
	            	
            } else if(prodotto instanceof GadgetBean) {
            	GadgetDAO gaDAO = new GadgetDAO();
            	gaDAO.doSave((GadgetBean) prodotto);
            	
            } else if(prodotto instanceof GiocoBean) {
            	GiocoDAO giDAO = new GiocoDAO();
            	giDAO.doSave((GiocoBean) prodotto);
            	
            }
            
            response.sendRedirect(request.getContextPath() + "/ElencoProdotti");
            
	    } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Dati inseriti non validi: " + e.getMessage());
                request.getRequestDispatcher("/WEB-INF/aggiungiProdotto.jsp").forward(request, response);
                
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Dati inseriti non validi: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/aggiungiProdotto.jsp").forward(request, response);
            
        } catch (SQLException e) {
        	e.printStackTrace();
            request.setAttribute("errorMessage", "Errore nel salvataggio del prodotto.");
            request.getRequestDispatcher("/WEB-INF/aggiungiProdotto.jsp").forward(request, response);
            
        }
    }

}
