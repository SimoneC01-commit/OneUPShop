package model.carrello;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.ConnectionPool;

public class ProdottoCarrelloDAO {

	public ProdottoCarrelloBean doRetrieveByKey(Integer key) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT ID_Prodotto, Titolo, Foto_BLOB, Prezzo_Attuale " +
						"FROM prodotto " +
						"WHERE ID_Prodotto = ? AND Disponibile = 1";
		
		ProdottoCarrelloBean prodotto = null;
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			ps.setInt(1, key);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				prodotto = new ProdottoCarrelloBean();
				prodotto.setId(rs.getInt("ID_Prodotto"));
				prodotto.setTitolo(rs.getString("Titolo"));
				prodotto.setFoto(rs.getBytes("Foto_BLOB"));
				prodotto.setPrezzo(rs.getFloat("Prezzo_Attuale"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			
			throw e;
		} 
		finally {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				ConnectionPool.releaseConnection(conn);
			}
		}
		
		return prodotto;
	}
}
