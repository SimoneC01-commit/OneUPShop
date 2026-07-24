package controller.servlet.admin.gestioneProdotti;

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
		String idStr = request.getParameter("idProdotto");

		if (idStr == null || idStr.trim().isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/ElencoProdotti");
			return;
		}

		try {
			int idProdotto = Integer.parseInt(idStr);
			ProdottoDAO pDAO = new ProdottoDAO();
			ProdottoBean prodotto = pDAO.doRetrieveByKey(idProdotto);

			if (prodotto == null) {
				response.sendRedirect(request.getContextPath() + "/ElencoProdotti");
				return;
			}
			
			if("Cabinato".equals(prodotto.getTipo())) {
				CabinatoDAO caDAO = new CabinatoDAO();
				CabinatoBean cabinato = caDAO.doRetrieveByKey(prodotto.getIdProdotto());
				request.setAttribute("cabinato", cabinato);
				
			} else if("Console".equals(prodotto.getTipo())) {
				ConsoleDAO coDAO = new ConsoleDAO();
				ConsoleBean console = coDAO.doRetrieveByKey(prodotto.getIdProdotto());
				request.setAttribute("console", console);
				
			} else if("Gadget".equals(prodotto.getTipo())) {
				GadgetDAO gaDAO = new GadgetDAO();
				GadgetBean gadget = gaDAO.doRetrieveByKey(prodotto.getIdProdotto());
				request.setAttribute("gadget", gadget);
				
			} else if("Gioco".equals(prodotto.getTipo())) {
				GiocoDAO giDAO = new GiocoDAO();
				GiocoBean gioco = giDAO.doRetrieveByKey(prodotto.getIdProdotto());
				request.setAttribute("gioco", gioco);
			}

			request.setAttribute("prodotto", prodotto);
			request.getRequestDispatcher("/WEB-INF/modificaProdotto.jsp").forward(request, response);

		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/ElencoProdotti");
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath() + "/ElencoProdotti");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8");
		
		String idProdottoStr = request.getParameter("idProdotto");
		String nuovoTitolo = request.getParameter("nuovoTitolo");
		String nuovaDescrizione = request.getParameter("nuovaDescrizione");
		String nuovoAnnoRilascioStr = request.getParameter("nuovoAnnoRilascio");
		String nuovaAzienda = request.getParameter("nuovaAzienda");
		String nuovoPrezzoAcquistoStr = request.getParameter("nuovoPrezzoAcquisto");
		String nuovoPrezzoAttualeStr = request.getParameter("nuovoPrezzoAttuale");
		String nuovoStato = request.getParameter("nuovoStato");
		String nuoveNoteDifetti = request.getParameter("nuoveNoteDifetti");
		String nuovaIvaStr = request.getParameter("nuovaIva");
		
		if (idProdottoStr == null || idProdottoStr.trim().isEmpty() ||
			nuovoTitolo == null || nuovoTitolo.trim().isEmpty() ||
			nuovoPrezzoAttualeStr == null || nuovoPrezzoAttualeStr.trim().isEmpty()) {

			request.setAttribute("errorMessage", "Errore compilazione form. Compila tutti i campi obbligatori.");
			request.getRequestDispatcher("/WEB-INF/modificaProdotto.jsp").forward(request, response);
			return;
		}
		
		if ("Usato".equals(nuovoStato) && (nuoveNoteDifetti == null || nuoveNoteDifetti.trim().isEmpty())) {
		    request.setAttribute("errorMessage", "Per i prodotti usati è obbligatorio specificare le note/difetti.");
		    request.getRequestDispatcher("/WEB-INF/modificaProdotto.jsp").forward(request, response);
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
			
			ProdottoBean prodottoModificato = new ProdottoBean();

			prodottoModificato.setIdProdotto(idProdotto);

			prodottoModificato.setTitolo(HtmlDecoder.encodeHtmlEntities(nuovoTitolo));
			prodottoModificato.setDescrizione(HtmlDecoder.encodeHtmlEntities(nuovaDescrizione));
			prodottoModificato.setAnnoRilascio((nuovoAnnoRilascioStr != null && !nuovoAnnoRilascioStr.trim().isEmpty()) ? Integer.parseInt(nuovoAnnoRilascioStr) : prodottoEsistente.getAnnoRilascio());
			prodottoModificato.setAzienda(HtmlDecoder.encodeHtmlEntities(nuovaAzienda));
			prodottoModificato.setPrezzoAcquisto((nuovoPrezzoAcquistoStr != null && !nuovoPrezzoAcquistoStr.trim().isEmpty()) ? new BigDecimal(nuovoPrezzoAcquistoStr) : prodottoEsistente.getPrezzoAcquisto());
			prodottoModificato.setPrezzoAttuale(new BigDecimal(nuovoPrezzoAttualeStr));
			prodottoModificato.setStato(HtmlDecoder.encodeHtmlEntities(nuovoStato));
			prodottoModificato.setNoteDifetti("Usato".equals(nuovoStato) ? HtmlDecoder.encodeHtmlEntities(nuoveNoteDifetti) : null);
			prodottoModificato.setIva((nuovaIvaStr != null && !nuovaIvaStr.trim().isEmpty()) ? Integer.parseInt(nuovaIvaStr) : prodottoEsistente.getIva());

			prodottoModificato.setTipo(prodottoEsistente.getTipo());
			prodottoModificato.setDataAggiunta(prodottoEsistente.getDataAggiunta());
			prodottoModificato.setDisponibile(prodottoEsistente.isDisponibile());

			Part fotoPart = request.getPart("nuovaFoto");
			
			if (fotoPart != null && fotoPart.getSize() > 0) {
				
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
				consoleModificato.setModelloSpecifico(nuovoModelloSpecifico != null && !nuovoModelloSpecifico.trim().isEmpty() ? nuovoModelloSpecifico : consoleEsistente.getModelloSpecifico());
				
				coDAO.doUpdate(consoleModificato);
				
			} else if("Gadget".equals(prodottoModificato.getTipo())) {
				GadgetDAO gaDAO = new GadgetDAO();
				GadgetBean gadgetEsistente = gaDAO.doRetrieveByKey(idProdotto);
				GadgetBean gadgetModificato = new GadgetBean();
				
				String nuovoTipoMateriale = request.getParameter("nuovoTipoMateriale");
				String nuovoTipoGadget = request.getParameter("nuovoTipoGadget");
				
				gadgetModificato.setIdProdotto(idProdotto);
				gadgetModificato.setTipoMateriale(nuovoTipoMateriale != null && !nuovoTipoMateriale.trim().isEmpty() ? nuovoTipoMateriale : gadgetEsistente.getTipoMateriale());
				gadgetModificato.setTipoGadget(nuovoTipoGadget != null && !nuovoTipoGadget.trim().isEmpty() ? nuovoTipoGadget : gadgetEsistente.getTipoGadget());
				
				gaDAO.doUpdate(gadgetModificato);
				
			} else if("Gioco".equals(prodottoModificato.getTipo())) {
				GiocoDAO giDAO = new GiocoDAO();
				GiocoBean giocoEsistente = giDAO.doRetrieveByKey(idProdotto);
				GiocoBean giocoModificato = new GiocoBean();
				
				String nuovoSviluppatore = request.getParameter("nuovoSviluppatore");
				
				giocoModificato.setIdProdotto(idProdotto);
				giocoModificato.setSviluppatore(nuovoSviluppatore != null && !nuovoSviluppatore.trim().isEmpty() ? nuovoSviluppatore : giocoEsistente.getSviluppatore());
				
				giDAO.doUpdate(giocoModificato);
			}
			
			response.sendRedirect(request.getContextPath() + "/ElencoProdotti");

		} catch (NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore durante la modifica del prodotto. Riprova.");
			request.getRequestDispatcher("/WEB-INF/modificaProdotto.jsp").forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Errore durante la modifica del prodotto. Riprova.");
			request.getRequestDispatcher("/WEB-INF/modificaProdotto.jsp").forward(request, response);
			
		}
	}

}
