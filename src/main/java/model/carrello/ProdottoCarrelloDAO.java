package model.carrello;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.ConnectionPool;
import model.DAOInterface;

public class ProdottoCarrelloDAO implements DAOInterface<ProdottoCarrelloBean, Integer> {

	@Override
	public void doSave(ProdottoCarrelloBean entry) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
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

	@Override
	public List<ProdottoCarrelloBean> doRetrieveAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doUpdate(ProdottoCarrelloBean entry) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete(Integer key) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
