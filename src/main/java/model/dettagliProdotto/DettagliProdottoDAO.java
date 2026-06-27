package model.dettagliProdotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.ConnectionPool;

public class DettagliProdottoDAO {

	public DettagliProdottoBean doRetrieveByKey(Integer key) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = null;
		String query = "SELECT p.ID_Prodotto, Titolo, Descrizione, Anno_Rilascio, Foto_BLOB, Azienda, p.Tipo, Prezzo_Attuale, Stato, Note_Difetti, Sviluppatore, Tipo_Sistema_Arcade, Dimensioni_cm, Modello_Specifico, Tipo_Materiale, Tipo_Gadget " +
				"FROM PRODOTTO p LEFT JOIN GIOCO g ON p.ID_Prodotto = g.ID_Prodotto " +
				"LEFT JOIN CABINATO c ON p.ID_Prodotto = c.ID_Prodotto " +
				"LEFT JOIN CONSOLE con ON p.ID_Prodotto = con.ID_Prodotto " +
				"LEFT JOIN GADGET gad ON p.ID_Prodotto = gad.ID_Prodotto " +
				"WHERE p.ID_prodotto = ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		DettagliProdottoBean dpb = null;
		
		try {
			conn = ConnectionPool.getConnection();
			
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, key);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				String tipo = rs.getString("p.Tipo");
				
				if(tipo.equals("Gioco")) {
					DettagliGiocoBean gioco = new DettagliGiocoBean();
					gioco.setSviluppatore(rs.getString("Sviluppatore"));
					
					dpb = gioco;
				}
				
				if(tipo.equals("Cabinato")) {
					DettagliCabinatoBean cabinato = new DettagliCabinatoBean();
					cabinato.setTipoSistemaArcade(rs.getString("Tipo_Sistema_Arcade"));
					cabinato.setDimensioniCm(rs.getString("Dimensioni_cm"));
					
					dpb = cabinato;
				}
				
				if(tipo.equals("Console")) {
					DettagliConsoleBean console = new DettagliConsoleBean();
					console.setModelloSpecifico(rs.getString("Modello_Specifico"));
					
					dpb = console;
				}
				
				if(tipo.equals("Gadget")) {
					DettagliGadgetBean gadget = new DettagliGadgetBean();
					gadget.setTipoGadget(rs.getString("Tipo_Gadget"));
					gadget.setTipoMateriale(rs.getString("Tipo_Materiale"));
					
					dpb = gadget;
				}
				
				//p., Titolo, Descrizione, Anno_Rilascio, Foto_BLOB, Azienda, p.Tipo, Prezzo_Attuale, Stato, Note_Difetti
				
				if(dpb != null) {
					dpb.setIdProdotto(rs.getInt("ID_Prodotto"));
					dpb.setTitolo(rs.getString("Titolo"));
					dpb.setDescrizione(rs.getString("Descrizione"));
					dpb.setAnnoRilascio(rs.getInt("Anno_Rilascio"));
					dpb.setFotoBlob(rs.getBytes("Foto_BLOB"));
					dpb.setAzienda(rs.getString("Azienda"));
					dpb.setTipo(rs.getString("Tipo"));
					dpb.setPrezzoAttuale(rs.getFloat("Prezzo_Attuale"));
					dpb.setStato(rs.getString("Stato"));
					dpb.setNoteDifetti(rs.getString("Note_Difetti"));
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			
			throw e;
		}
		finally {
			if(rs != null) {
				rs.close();
			}
			if(ps != null) {
				ps.close();
			}
			if(conn != null) {
				ConnectionPool.releaseConnection(conn);
			}
		}
		
		return dpb;
	}
}
