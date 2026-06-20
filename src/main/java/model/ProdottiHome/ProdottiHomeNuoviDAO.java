package model.ProdottiHome;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ConnectionPool;

import model.DAOInterface;

public class ProdottiHomeNuoviDAO implements DAOInterface<ProdottiHomeBean, Integer>{

	@Override
	public void doSave(ProdottiHomeBean entry) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProdottiHomeBean doRetrieveByKey(Integer key) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdottiHomeBean> doRetrieveAll() throws SQLException {
		
		Connection conn = null;
		String query = "SELECT ID_Prodotto, Titolo, Foto_BLOB, Tipo, Prezzo_Attuale, Stato FROM prodotto ORDER BY Data_Aggiunta DESC LIMIT 10";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<ProdottiHomeBean> lp = new ArrayList<ProdottiHomeBean>();
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ProdottiHomeBean bean = new ProdottiHomeBean();
				
				bean.setID(rs.getInt(1));
				bean.setTitolo(rs.getString(2));
				bean.setCopertina(rs.getBytes(3));
				bean.setTipo(rs.getString(4));
				bean.setPrezzo(rs.getFloat(5));
				bean.setStato(rs.getString(6));
				
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

	@Override
	public void doUpdate(ProdottiHomeBean entry) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete(Integer key) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
}