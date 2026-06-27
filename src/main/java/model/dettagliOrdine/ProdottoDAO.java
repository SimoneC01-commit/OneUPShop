package model.dettagliOrdine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.ConnectionPool;

public class ProdottoDAO{
	public ProdottoBean retrieveProdotto(int idProdotto) throws SQLException {
		Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	String query = "SELECT ID_Prodotto, Titolo, Foto_BLOB, Tipo " +
    					"FROM prodotto " +
    					"WHERE ID_Prodotto = ?";
    	
    	ProdottoBean bean = null;
    	
    	try {
    		conn = ConnectionPool.getConnection();
    		ps = conn.prepareStatement(query);
    		
    		ps.setInt(1, idProdotto);
    		
    		rs = ps.executeQuery();
    		
    		if(rs.next()) {
        		bean = new ProdottoBean();
        		
    			bean.setID(rs.getInt("ID_Prodotto"));
    			bean.setTitolo(rs.getString("Titolo"));
    			bean.setFoto(rs.getBytes("Foto_BLOB"));
    			bean.setTipo(rs.getString("Tipo"));
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
    	
    	return bean;
	}
}