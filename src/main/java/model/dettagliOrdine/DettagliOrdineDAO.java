package model.dettagliOrdine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ConnectionPool;

public class DettagliOrdineDAO {
	
	public ArrayList<DettagliOrdineBean> retrieveProdottiOrdine(int idOrdine) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT d.ID_Ordine, d.ID_Prodotto, d.Prezzo_Vendita_Storico, p.Titolo, p.Foto_BLOB, p.Tipo " +
						"FROM dettaglio_ordine d " +
						"JOIN prodotto p " +
								"ON d.ID_Prodotto = p.ID_Prodotto " +
						"WHERE d.ID_Ordine = ?";
		
		ArrayList<DettagliOrdineBean> prodotti = null;
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, idOrdine);
			
			rs = ps.executeQuery();
			
			prodotti = new ArrayList<DettagliOrdineBean>();
			
			while(rs.next()) {
				DettagliOrdineBean bean = new DettagliOrdineBean();
				
				ProdottoBean prodotto = new ProdottoBean();
				prodotto.setID(rs.getInt("ID_Prodotto"));
				prodotto.setTitolo(rs.getString("Titolo"));
				prodotto.setFoto(rs.getBytes("Foto_BLOB"));
				prodotto.setTipo(rs.getString("Tipo"));
				
				bean.setIdOrdine(idOrdine);
				bean.setProdotto(prodotto);
				bean.setPrezzoVenditaStorico(rs.getFloat("Prezzo_Vendita_Storico"));
				
				prodotti.add(bean);
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
		
		return prodotti;
	}

}
