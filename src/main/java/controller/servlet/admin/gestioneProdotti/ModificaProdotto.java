package controller.servlet.admin.gestioneProdotti;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.Year;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import controller.utility.HtmlDecoder;
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

/**
 * Servlet implementation class ModificaProdotto
 */
@WebServlet("/ModificaProdotto")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
	    maxFileSize = 1024 * 1024 * 10,       // 10MB
	    maxRequestSize = 1024 * 1024 * 50     // 50MB
	)
public class ModificaProdotto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModificaProdotto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String idProdottoStr = request.getParameter("idProdotto");

		if (idProdottoStr == null || idProdottoStr.trim().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/ElencoProdotti");
			return;
		}

		try {
			int idProdotto = Integer.parseInt(idProdottoStr);
			ProdottoDAO pDAO = new ProdottoDAO();
			ProdottoBean prodotto = pDAO.doRetrieveByKey(idProdotto);

			if (prodotto == null) {
				response.sendRedirect(request.getContextPath() + "/ElencoProdotti");
				return;
			}
			
			impostaAttributiSottotipo(request, prodotto);
			request.setAttribute("prodotto", prodotto);
			request.getRequestDispatcher("/WEB-INF/modificaProdotto.jsp").forward(request, response);

		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/ElencoProdotti");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String idProdottoStr = request.getParameter("idProdotto");
		
		if (idProdottoStr == null || idProdottoStr.trim().isEmpty()) {
	        response.sendRedirect(request.getContextPath() + "/ElencoProdotti");
	        return;
	    }
		
		try {
			int idProdotto = Integer.parseInt(idProdottoStr);
			ProdottoDAO pDAO = new ProdottoDAO();
			ProdottoBean prodottoEsistente = pDAO.doRetrieveByKey(idProdotto);

			if (prodottoEsistente == null) {
				response.sendRedirect(request.getContextPath() + "/ElencoProdotti");
				return;
			}
			
			String nuovoTitolo = request.getParameter("nuovoTitolo");
			String nuovaDescrizione = request.getParameter("nuovaDescrizione");
			String nuovoAnnoRilascioStr = request.getParameter("nuovoAnnoRilascio");
			String nuovaAzienda = request.getParameter("nuovaAzienda");
			String nuovoPrezzoAcquistoStr = request.getParameter("nuovoPrezzoAcquisto");
			String nuovoPrezzoAttualeStr = request.getParameter("nuovoPrezzoAttuale");
			String nuovoStato = request.getParameter("nuovoStato");
			String nuoveNoteDifetti = request.getParameter("nuoveNoteDifetti");
			String nuovaIvaStr = request.getParameter("nuovaIva");
			
			String errorMessage = null;
			int annoCorrente = Year.now().getValue();

			if (nuovoTitolo == null || !nuovoTitolo.matches("^[a-zA-Z0-9\\s'’:\\-\\.!,?()]{2,100}$")) {
				errorMessage = "Formato titolo non valido. Deve contenere tra 2 e 100 caratteri.";
			}
			else if (nuovaDescrizione == null || nuovaDescrizione.trim().length() < 10 || nuovaDescrizione.trim().length() > 1000) {
				errorMessage = "La descrizione deve contenere tra 10 e 1000 caratteri.";
			}
			else if (nuovoAnnoRilascioStr != null && !nuovoAnnoRilascioStr.trim().isEmpty()) {
				try {
					int anno = Integer.parseInt(nuovoAnnoRilascioStr);
					if (anno < 1950 || anno > annoCorrente) {
						errorMessage = "L'anno deve essere compreso tra 1950 e " + annoCorrente + ".";
					}
				} catch (NumberFormatException e) {
					errorMessage = "Formato anno non valido.";
				}
			}
			else if (nuovaAzienda == null || !nuovaAzienda.matches("^[a-zA-Z0-9\\s'’&:\\-\\.]{2,100}$")) {
				errorMessage = "Nome azienda non valido. (2-100 caratteri ammessi).";
			}
			else if (nuovoStato == null || (!nuovoStato.equals("Nuovo") && !nuovoStato.equals("Usato"))) {
				errorMessage = "Stato prodotto non valido.";
			}
			else if ("Usato".equals(nuovoStato) && (nuoveNoteDifetti == null || nuoveNoteDifetti.trim().length() < 5 || nuoveNoteDifetti.trim().length() > 500)) {
				errorMessage = "Per i prodotti usati è obbligatorio specificare le note/difetti (da 5 a 500 caratteri).";
			}
			else if (nuovaIvaStr != null && !nuovaIvaStr.trim().isEmpty()) {
				try {
					int iva = Integer.parseInt(nuovaIvaStr);
					if (iva < 0 || iva > 100) errorMessage = "L'IVA deve essere compresa tra 0 e 100.";
				} catch (NumberFormatException e) {
					errorMessage = "Formato IVA non valido.";
				}
			}

			if (errorMessage == null) {
				String tipo = prodottoEsistente.getTipo();
				
				if ("Gioco".equals(tipo)) {
					String sviluppatore = request.getParameter("nuovoSviluppatore");
					if (sviluppatore == null || sviluppatore.trim().length() < 2 || sviluppatore.trim().length() > 100) {
						errorMessage = "Lo sviluppatore deve contenere tra 2 e 100 caratteri.";
					}
				} else if ("Console".equals(tipo)) {
					String modello = request.getParameter("nuovoModelloSpecifico");
					if (modello == null || modello.trim().length() < 2 || modello.trim().length() > 100) {
						errorMessage = "Il modello specifico deve contenere tra 2 e 100 caratteri.";
					}
				} else if ("Gadget".equals(tipo)) {
					String materiale = request.getParameter("nuovoTipoMateriale");
					String tipoGadget = request.getParameter("nuovoTipoGadget");
					if (materiale == null || materiale.trim().length() < 2 || materiale.trim().length() > 100) {
						errorMessage = "Il materiale deve contenere tra 2 e 100 caratteri.";
					} else if (tipoGadget == null || tipoGadget.trim().length() < 2 || tipoGadget.trim().length() > 100) {
						errorMessage = "Il tipo gadget deve contenere tra 2 e 100 caratteri.";
					}
				} else if ("Cabinato".equals(tipo)) {
					String sistema = request.getParameter("nuovoTipoSistemaArcade");
					String dimensioni = request.getParameter("nuoveDimensioniCm");
					if (sistema == null || sistema.trim().length() < 2 || sistema.trim().length() > 100) {
						errorMessage = "Il sistema arcade deve contenere tra 2 e 100 caratteri.";
					} else if (dimensioni == null || !dimensioni.matches("^\\d{1,3}\\s*x\\s*\\d{1,3}\\s*x\\s*\\d{1,3}$")) {
						errorMessage = "Dimensioni non valide (Usa il formato LxPxA, es. 60x80x170).";
					}
				}
			}

			if (errorMessage != null) {
				request.setAttribute("errorMessage", errorMessage);
				impostaAttributiSottotipo(request, prodottoEsistente);
				request.setAttribute("prodotto", prodottoEsistente);
				request.getRequestDispatcher("/WEB-INF/modificaProdotto.jsp").forward(request, response);
				return;
			}

			ProdottoBean prodottoModificato = new ProdottoBean();
			prodottoModificato.setIdProdotto(idProdotto);
			prodottoModificato.setTitolo(HtmlDecoder.encodeHtmlEntities(nuovoTitolo));
	        prodottoModificato.setDescrizione(HtmlDecoder.encodeHtmlEntities(nuovaDescrizione));
	        prodottoModificato.setAnnoRilascio((nuovoAnnoRilascioStr != null && !nuovoAnnoRilascioStr.trim().isEmpty()) ? Integer.parseInt(nuovoAnnoRilascioStr) : prodottoEsistente.getAnnoRilascio());
	        prodottoModificato.setAzienda(HtmlDecoder.encodeHtmlEntities(nuovaAzienda));
	        prodottoModificato.setStato(HtmlDecoder.encodeHtmlEntities(nuovoStato));
	        prodottoModificato.setNoteDifetti("Usato".equalsIgnoreCase(nuovoStato) ? HtmlDecoder.encodeHtmlEntities(nuoveNoteDifetti) : null);
	        
	        BigDecimal prezzoAttualeBase;
	        
	        if (nuovoPrezzoAcquistoStr != null && !nuovoPrezzoAcquistoStr.trim().isEmpty()) {
	            BigDecimal prezzoAcquisto = new BigDecimal(nuovoPrezzoAcquistoStr.trim());
	            if(prezzoAcquisto.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Prezzo acquisto non valido");
	            prodottoModificato.setPrezzoAcquisto(prezzoAcquisto);
	            prezzoAttualeBase = prezzoAcquisto.multiply(new BigDecimal("1.5"));
	        } else {
	            prodottoModificato.setPrezzoAcquisto(null);
	            if (nuovoPrezzoAttualeStr != null && !nuovoPrezzoAttualeStr.trim().isEmpty()) {
	                prezzoAttualeBase = new BigDecimal(nuovoPrezzoAttualeStr.trim());
	                if(prezzoAttualeBase.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Prezzo attuale non valido");
	            } else {
	                throw new IllegalArgumentException("Specificare un prezzo di acquisto o di vendita.");
	            }
	        }
	        
	        int iva = (nuovaIvaStr != null && !nuovaIvaStr.trim().isEmpty()) ? Integer.parseInt(nuovaIvaStr) : prodottoEsistente.getIva();

	        BigDecimal percentualeIva = new BigDecimal(iva).divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
	        BigDecimal moltiplicatoreIva = BigDecimal.ONE.add(percentualeIva);
	        
	        BigDecimal prezzoConIva = prezzoAttualeBase.multiply(moltiplicatoreIva).setScale(2, RoundingMode.HALF_UP);
	        BigDecimal parteIntera = prezzoConIva.setScale(0, RoundingMode.DOWN);
	        prezzoConIva = parteIntera.add(new BigDecimal("0.99"));
	        
	        prodottoModificato.setIva(iva);
	        prodottoModificato.setPrezzoAttuale(prezzoConIva);
			
			prodottoModificato.setTipo(prodottoEsistente.getTipo());
			prodottoModificato.setDataAggiunta(prodottoEsistente.getDataAggiunta());
			prodottoModificato.setDisponibile(prodottoEsistente.isDisponibile());

			Part fotoPart = request.getPart("nuovaFoto");
			if (fotoPart != null && fotoPart.getSize() > 0) {
				String contentType = fotoPart.getContentType();
				if(contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png") && !contentType.equals("image/webp"))) {
					request.setAttribute("errorMessage", "Formato immagine non supportato (usa JPG, PNG o WEBP).");
					impostaAttributiSottotipo(request, prodottoEsistente);
					request.setAttribute("prodotto", prodottoEsistente);
					request.getRequestDispatcher("/WEB-INF/modificaProdotto.jsp").forward(request, response);
					return;
				}
				
				try (InputStream is = fotoPart.getInputStream()) {
					byte[] nuovaFotoBytes = is.readAllBytes();
					prodottoModificato.setFotoBlob(nuovaFotoBytes);
				}
			} else {
				prodottoModificato.setFotoBlob(prodottoEsistente.getFotoBlob());
			}
			
			pDAO.doUpdate(prodottoModificato);

			if("Cabinato".equals(prodottoModificato.getTipo())) {
				CabinatoDAO caDAO = new CabinatoDAO();
				CabinatoBean cabinatoEsistente = caDAO.doRetrieveByKey(idProdotto);
				CabinatoBean cabinatoModificato = new CabinatoBean();
				String nuovoTipoSistemaArcade = request.getParameter("nuovoTipoSistemaArcade");
				String nuoveDimensioniCm = request.getParameter("nuoveDimensioniCm");
				
				cabinatoModificato.setIdProdotto(idProdotto);
				cabinatoModificato.setTipoSistemaArcade(nuovoTipoSistemaArcade != null && !nuovoTipoSistemaArcade.trim().isEmpty() ? HtmlDecoder.encodeHtmlEntities(nuovoTipoSistemaArcade) : cabinatoEsistente.getTipoSistemaArcade());
				cabinatoModificato.setDimensioniCm(nuoveDimensioniCm != null && !nuoveDimensioniCm.trim().isEmpty() ? HtmlDecoder.encodeHtmlEntities(nuoveDimensioniCm) : cabinatoEsistente.getDimensioniCm());
				caDAO.doUpdate(cabinatoModificato);
				
			} else if("Console".equals(prodottoModificato.getTipo())) {
				ConsoleDAO coDAO = new ConsoleDAO();
				ConsoleBean consoleEsistente = coDAO.doRetrieveByKey(idProdotto);
				ConsoleBean consoleModificato = new ConsoleBean();
				String nuovoModelloSpecifico = request.getParameter("nuovoModelloSpecifico");
				
				consoleModificato.setIdProdotto(idProdotto);
				consoleModificato.setModelloSpecifico(nuovoModelloSpecifico != null && !nuovoModelloSpecifico.trim().isEmpty() ? HtmlDecoder.encodeHtmlEntities(nuovoModelloSpecifico) : consoleEsistente.getModelloSpecifico());
				coDAO.doUpdate(consoleModificato);
				
			} else if("Gadget".equals(prodottoModificato.getTipo())) {
				GadgetDAO gaDAO = new GadgetDAO();
				GadgetBean gadgetEsistente = gaDAO.doRetrieveByKey(idProdotto);
				GadgetBean gadgetModificato = new GadgetBean();
				String nuovoTipoMateriale = request.getParameter("nuovoTipoMateriale");
				String nuovoTipoGadget = request.getParameter("nuovoTipoGadget");
				
				gadgetModificato.setIdProdotto(idProdotto);
				gadgetModificato.setTipoMateriale(nuovoTipoMateriale != null && !nuovoTipoMateriale.trim().isEmpty() ? HtmlDecoder.encodeHtmlEntities(nuovoTipoMateriale) : gadgetEsistente.getTipoMateriale());
				gadgetModificato.setTipoGadget(nuovoTipoGadget != null && !nuovoTipoGadget.trim().isEmpty() ? HtmlDecoder.encodeHtmlEntities(nuovoTipoGadget) : gadgetEsistente.getTipoGadget());
				gaDAO.doUpdate(gadgetModificato);
				
			} else if("Gioco".equals(prodottoModificato.getTipo())) {
				GiocoDAO giDAO = new GiocoDAO();
				GiocoBean giocoEsistente = giDAO.doRetrieveByKey(idProdotto);
				GiocoBean giocoModificato = new GiocoBean();
				String nuovoSviluppatore = request.getParameter("nuovoSviluppatore");
				
				giocoModificato.setIdProdotto(idProdotto);
				giocoModificato.setSviluppatore(nuovoSviluppatore != null && !nuovoSviluppatore.trim().isEmpty() ? HtmlDecoder.encodeHtmlEntities(nuovoSviluppatore) : giocoEsistente.getSviluppatore());
				giDAO.doUpdate(giocoModificato);
			}
			
			response.sendRedirect(request.getContextPath() + "/ElencoProdotti");

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Dati numerici non validi: " + e.getMessage());
			request.getRequestDispatcher("/WEB-INF/modificaProdotto.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore di connessione al database durante la modifica.");
			request.getRequestDispatcher("/WEB-INF/modificaProdotto.jsp").forward(request, response);
		}
	}
	
	private void impostaAttributiSottotipo(HttpServletRequest request, ProdottoBean prodotto) throws SQLException {
		if("Cabinato".equals(prodotto.getTipo())) {
			request.setAttribute("cabinato", new CabinatoDAO().doRetrieveByKey(prodotto.getIdProdotto()));
		} else if("Console".equals(prodotto.getTipo())) {
			request.setAttribute("console", new ConsoleDAO().doRetrieveByKey(prodotto.getIdProdotto()));
		} else if("Gadget".equals(prodotto.getTipo())) {
			request.setAttribute("gadget", new GadgetDAO().doRetrieveByKey(prodotto.getIdProdotto()));
		} else if("Gioco".equals(prodotto.getTipo())) {
			request.setAttribute("gioco", new GiocoDAO().doRetrieveByKey(prodotto.getIdProdotto()));
		}
	}

}
