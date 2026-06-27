package model.prodottiHome;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ConnectionPool;

public class ProdottiHomeNuoviDAO {

	public List<ProdottiHomeBean> doRetrieveAll() throws SQLException {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT ID_Prodotto, Titolo, Foto_BLOB, Tipo, Prezzo_Attuale, Stato " +
				"FROM prodotto " +
				"ORDER BY Data_Aggiunta " +
				"DESC LIMIT 10";
		
		List<ProdottiHomeBean> lp = new ArrayList<ProdottiHomeBean>();
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ProdottiHomeBean bean = new ProdottiHomeBean();

				bean.setID(rs.getInt("ID_Prodotto"));
				bean.setTitolo(rs.getString("Titolo"));
				bean.setCopertina(rs.getBytes("Foto_BLOB"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setPrezzo(rs.getFloat("Prezzo_Attuale"));
				bean.setStato(rs.getString("Stato"));
				
				lp.add(bean);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw e;
		}
		finally {
			if(rs != null){
				rs.close();
			}
			
			if(ps != null){
				ps.close();
			}
			
			if(conn != null) {
				ConnectionPool.releaseConnection(conn);
			}
		}
		return lp;
	}
}