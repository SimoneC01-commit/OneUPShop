package model.ProdottiHome;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.DAOInterface;

public class ProdottiHomeConsigliatiDAO implements DAOInterface<ProdottiHomeBean, Integer> {

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
		String query = "SELECT Titolo, Foto_BLOB, Prezzo_Attuale FROM prodotto ORDER BY RAND() LIMIT 10";
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<ProdottiHomeBean> lp = new ArrayList<ProdottiHomeBean>();
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ProdottiHomeBean bean = new ProdottiHomeBean();
				
				bean.setTitolo(rs.getString(1));
				bean.setCopertina(rs.getBytes(2));
				bean.setPrezzo(rs.getFloat(3));
				
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
