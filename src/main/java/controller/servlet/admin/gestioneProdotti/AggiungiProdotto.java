package controller.servlet.admin.gestioneProdotti;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.sql.Timestamp;
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
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/aggiungiProdotto.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String tipo = request.getParameter("tipo");
        String titolo = request.getParameter("titolo") != null ? request.getParameter("titolo").trim() : null;
        String descrizione = request.getParameter("descrizione") != null ? request.getParameter("descrizione").trim() : null;
        String azienda = request.getParameter("azienda") != null ? request.getParameter("azienda").trim() : null;
        String stato = request.getParameter("stato");
        String noteDifetti = request.getParameter("noteDifetti") != null ? request.getParameter("noteDifetti").trim() : null;
        String checkPrezzoCustomStr = request.getParameter("checkPrezzoCustom");
        boolean isCustomPrice = checkPrezzoCustomStr != null && (checkPrezzoCustomStr.equals("on") || checkPrezzoCustomStr.equals("true"));
        
        String titoloRegex = "^[a-zA-Z0-9\\s'’:\\-\\.!,?()]{2,100}$";
        String aziendaRegex = "^[a-zA-Z0-9\\s'’&:\\-\\.]{2,100}$";
        String dimensioniRegex = "^\\d{1,3}\\s*x\\s*\\d{1,3}\\s*x\\s*\\d{1,3}$";
        
        try {
            if (tipo == null || tipo.isEmpty()) 
                throw new IllegalArgumentException("Seleziona un tipo di prodotto.");
            
            if (titolo == null || !titolo.matches(titoloRegex) || titolo.length() < 2 || titolo.length() > 100) 
                throw new IllegalArgumentException("Il titolo deve contenere tra 2 e 100 caratteri validi.");
            
            if (descrizione == null || descrizione.length() < 10 || descrizione.length() > 1000) 
                throw new IllegalArgumentException("La descrizione deve contenere tra 10 e 1000 caratteri.");
            
            int annoRilascio = Integer.parseInt(request.getParameter("annoRilascio"));
            int annoCorrente = Year.now().getValue();
            if (annoRilascio < 1950 || annoRilascio > annoCorrente) 
                throw new IllegalArgumentException("L'anno deve essere compreso tra 1950 e " + annoCorrente + ".");
            
            if (azienda == null || !azienda.matches(aziendaRegex) || azienda.length() < 2 || azienda.length() > 100) 
                throw new IllegalArgumentException("Nome azienda non valido (deve essere tra 2 e 100 caratteri).");
            
            if (stato == null || stato.isEmpty()) 
                throw new IllegalArgumentException("Seleziona lo stato del prodotto.");
            
            if ("Usato".equals(stato)) {
                if (noteDifetti == null || noteDifetti.length() < 5 || noteDifetti.length() > 500) 
                    throw new IllegalArgumentException("Le note sui difetti devono contenere tra 5 e 500 caratteri.");
            }
            
            String ivaStr = request.getParameter("iva");
            double ivaVal = (ivaStr != null && !ivaStr.trim().isEmpty()) ? Double.parseDouble(ivaStr) : 22.0;
            if (ivaVal < 0 || ivaVal > 100) 
                throw new IllegalArgumentException("Inserisci una percentuale IVA valida tra 0 e 100.");
            
            if (isCustomPrice) {
                String prezzoAttualeStr = request.getParameter("prezzoAttuale");
                if (prezzoAttualeStr == null) throw new IllegalArgumentException("Inserisci un prezzo attuale valido.");
                double prezzoAttuale = Double.parseDouble(prezzoAttualeStr);
                if (prezzoAttuale <= 0) throw new IllegalArgumentException("Inserisci un prezzo attuale valido maggiore di 0.");
            } else {
                String prezzoAcquistoStr = request.getParameter("prezzoAcquisto");
                if (prezzoAcquistoStr == null) throw new IllegalArgumentException("Inserisci un prezzo di acquisto valido.");
                double prezzoAcquisto = Double.parseDouble(prezzoAcquistoStr);
                if (prezzoAcquisto <= 0) throw new IllegalArgumentException("Inserisci un prezzo di acquisto valido maggiore di 0.");
            }
            
            Part filePart = request.getPart("foto");
            if (filePart != null && filePart.getSize() > 0) {
                String contentType = filePart.getContentType();
                if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType) && !"image/webp".equals(contentType)) {
                    throw new IllegalArgumentException("Formato file non supportato (usa JPG, PNG o WEBP).");
                }
                if (filePart.getSize() > 1024 * 1024 * 10) {
                    throw new IllegalArgumentException("La foto non può superare i 10MB.");
                }
            }
            
            if ("Gioco".equals(tipo)) {
                String sviluppatore = request.getParameter("sviluppatore");
                if (sviluppatore == null || sviluppatore.trim().length() < 2 || sviluppatore.trim().length() > 100) 
                    throw new IllegalArgumentException("Lo sviluppatore deve contenere tra 2 e 100 caratteri.");
            } else if ("Console".equals(tipo)) {
                String modelloSpecifico = request.getParameter("modelloSpecifico");
                if (modelloSpecifico == null || modelloSpecifico.trim().length() < 2 || modelloSpecifico.trim().length() > 100) 
                    throw new IllegalArgumentException("Il modello specifico deve contenere tra 2 e 100 caratteri.");
            } else if ("Gadget".equals(tipo)) {
                String tipoMateriale = request.getParameter("tipoMateriale");
                if (tipoMateriale == null || tipoMateriale.trim().length() < 2 || tipoMateriale.trim().length() > 100) 
                    throw new IllegalArgumentException("Il tipo di materiale deve contenere tra 2 e 100 caratteri.");
                String tipoGadget = request.getParameter("tipoGadget");
                if (tipoGadget == null || tipoGadget.trim().length() < 2 || tipoGadget.trim().length() > 100) 
                    throw new IllegalArgumentException("Il tipo di gadget deve contenere tra 2 e 100 caratteri.");
            } else if ("Cabinato".equals(tipo)) {
                String tipoSistemaArcade = request.getParameter("tipoSistemaArcade");
                if (tipoSistemaArcade == null || tipoSistemaArcade.trim().length() < 2 || tipoSistemaArcade.trim().length() > 100) 
                    throw new IllegalArgumentException("Il sistema arcade deve contenere tra 2 e 100 caratteri.");
                String dimensioniCm = request.getParameter("dimensioniCm");
                if (dimensioniCm == null || !dimensioniCm.trim().matches(dimensioniRegex)) 
                    throw new IllegalArgumentException("Formato non valido. Usa il formato LxPxA (es. 60x80x170).");
            }

        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Valori numerici non validi.");
            request.getRequestDispatcher("/WEB-INF/aggiungiProdotto.jsp").forward(request, response);
            return;
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", "Errore validazione: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/aggiungiProdotto.jsp").forward(request, response);
            return;
        }
        
        ProdottoBean prodotto = null;
        
        if ("Cabinato".equals(tipo)) {
            CabinatoBean cabinato = new CabinatoBean();
            cabinato.setTipoSistemaArcade(HtmlDecoder.encodeHtmlEntities(request.getParameter("tipoSistemaArcade")));
            cabinato.setDimensioniCm(HtmlDecoder.encodeHtmlEntities(request.getParameter("dimensioniCm")));
            prodotto = cabinato;
            
        } else if ("Console".equals(tipo)) {
            ConsoleBean console = new ConsoleBean();
            console.setModelloSpecifico(HtmlDecoder.encodeHtmlEntities(request.getParameter("modelloSpecifico")));
            prodotto = console;
            
        } else if ("Gadget".equals(tipo)) {
            GadgetBean gadget = new GadgetBean();
            gadget.setTipoMateriale(HtmlDecoder.encodeHtmlEntities(request.getParameter("tipoMateriale")));
            gadget.setTipoGadget(HtmlDecoder.encodeHtmlEntities(request.getParameter("tipoGadget")));
            prodotto = gadget;
            
        } else if ("Gioco".equals(tipo)) {
            GiocoBean gioco = new GiocoBean();
            gioco.setSviluppatore(HtmlDecoder.encodeHtmlEntities(request.getParameter("sviluppatore")));
            prodotto = gioco;
        }
        
        if(prodotto == null) {
            request.setAttribute("errorMessage", "Errore interno. Tipo prodotto non valido.");
            request.getRequestDispatcher("/WEB-INF/aggiungiProdotto.jsp").forward(request, response);
            return;
        }
        
        try {
            
            prodotto.setTitolo(HtmlDecoder.encodeHtmlEntities(request.getParameter("titolo")));
            prodotto.setDescrizione(HtmlDecoder.encodeHtmlEntities(request.getParameter("descrizione")));
            prodotto.setAnnoRilascio(Integer.parseInt(request.getParameter("annoRilascio")));
            prodotto.setAzienda(HtmlDecoder.encodeHtmlEntities(request.getParameter("azienda")));
            prodotto.setTipo(HtmlDecoder.encodeHtmlEntities(tipo));
            prodotto.setDataAggiunta(new Timestamp(System.currentTimeMillis()));
            prodotto.setDisponibile(true);

            prodotto.setStato(HtmlDecoder.encodeHtmlEntities(stato));
            
            if ("Nuovo".equalsIgnoreCase(stato)) {
                prodotto.setNoteDifetti(null);
            } else {
                prodotto.setNoteDifetti(HtmlDecoder.encodeHtmlEntities(request.getParameter("noteDifetti")));
            }
            
            String prezzoAcquistoStr = request.getParameter("prezzoAcquisto");
            String prezzoAttualeStr = request.getParameter("prezzoAttuale");
            String ivaStr = request.getParameter("iva");

            BigDecimal prezzoAttualeBase;
            
            if (prezzoAcquistoStr != null && !prezzoAcquistoStr.trim().isEmpty() && !isCustomPrice) {
                BigDecimal prezzoAcquisto = new BigDecimal(prezzoAcquistoStr.trim());
                prodotto.setPrezzoAcquisto(prezzoAcquisto);
                
                prezzoAttualeBase = prezzoAcquisto.multiply(new BigDecimal("1.5"));
                
            } else {
                prodotto.setPrezzoAcquisto(null);
                
                if (prezzoAttualeStr != null && !prezzoAttualeStr.trim().isEmpty()) {
                    prezzoAttualeBase = new BigDecimal(prezzoAttualeStr.trim());
                } else {
                    throw new IllegalArgumentException("Se non inserisci il prezzo d'acquisto, devi specificare un prezzo attuale.");
                }
            }
            
            int iva = (ivaStr != null && !ivaStr.trim().isEmpty()) ? Integer.parseInt(ivaStr.trim()) : 22;
            
            BigDecimal percentualeIva = new BigDecimal(iva).divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
            BigDecimal moltiplicatoreIva = BigDecimal.ONE.add(percentualeIva);
            
            BigDecimal prezzoConIva = prezzoAttualeBase.multiply(moltiplicatoreIva).setScale(2, RoundingMode.HALF_UP);
            BigDecimal parteIntera = prezzoConIva.setScale(0, RoundingMode.DOWN);
            prezzoConIva = parteIntera.add(new BigDecimal("0.99"));
            
            prodotto.setIva(iva);
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