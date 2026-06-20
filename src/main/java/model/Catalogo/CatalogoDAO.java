package model.Catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.DAOInterface;

public class CatalogoDAO implements DAOInterface<ProdottoBean, Integer> {

	@Override
	public void doSave(ProdottoBean entry) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProdottoBean doRetrieveByKey(Integer key) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProdottoBean> doRetrieveAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doUpdate(ProdottoBean entry) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete(Integer key) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public List<ProdottoBean> doRetriveByPages(Integer minYear, Integer maxYear, String tipo, Float minPrice, Float maxPrice, String stato, int pagCorrente, int elemForPage) throws SQLException{

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<ProdottoBean> lb = new ArrayList<ProdottoBean>();
		
		int offset = (pagCorrente - 1)*elemForPage;

		StringBuilder query = new StringBuilder("SELECT ID_Prodotto, Titolo, Foto_BLOB, Prezzo_Attuale FROM Prodotto WHERE Disponibile = 1");
		ArrayList<Object> parametri = new ArrayList<Object>();
		
		if (minYear != null) {
		    query.append(" AND Anno_Rilascio >= ?");
		    parametri.add(minYear);
		}
		
		if (maxYear != null) {
		    query.append(" AND Anno_Rilascio <= ?");
		    parametri.add(maxYear);
		}
		
	    if (tipo != null && (tipo.equals("Cabinato") || tipo.equals("Console") || tipo.equals("Gadget") || tipo.equals("Gioco"))) {
	        query.append(" AND Tipo = ?");
	        parametri.add(tipo);
	    }
	    
	    if (minPrice != null) {
	        query.append(" AND Prezzo_Attuale >= ?");
	        parametri.add(minPrice);
	    }
	    
	    if(maxPrice != null) {    
	        query.append(" AND Prezzo_Attuale <= ?");
	        parametri.add(maxPrice);
	    }
	    
	    if (stato != null && (stato.equals("Nuovo") || stato.equals("Usato"))) {
	        query.append(" AND Stato = ?");
	        parametri.add(stato);
	    }
	    
	    query.append(" LIMIT ? OFFSET ?");
	    parametri.add(elemForPage);
	    parametri.add(offset);
		
	    try {
	    	conn = ConnectionPool.getConnection();
	    	ps = conn.prepareStatement(query.toString());
	    	
	    	for(int i = 0; i < parametri.size(); i++) {
	    		ps.setObject(i+1, parametri.get(i));
	    	}
	    	
	    	rs = ps.executeQuery();
	    	
	    	while(rs.next()) {
	    		ProdottoBean pb = new ProdottoBean();
	    		
	    		pb.setID(rs.getInt(1));
	    		pb.setTitolo(rs.getString(2));
	    		pb.setFoto(rs.getBytes(3));
	    		pb.setPrezzo(rs.getFloat(4));
	    		
	    		lb.add(pb);
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
		
		return lb;
	}
	
	public int doCountByFilters(Integer minYear, Integer maxYear, String tipo, Float minPrice, Float maxPrice, String stato) throws SQLException {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int count = 0;
		
		StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM Prodotto WHERE Disponibile = 1");
		ArrayList<Object> parametri = new ArrayList<Object>();

		if (minYear != null) {
		    query.append(" AND Anno_Rilascio >= ?");
		    parametri.add(minYear);
		}
		
		if (maxYear != null) {
		    query.append(" AND Anno_Rilascio <= ?");
		    parametri.add(maxYear);
		}
		
	    if (tipo != null && (tipo.equals("Cabinato") || tipo.equals("Console") || tipo.equals("Gadget") || tipo.equals("Gioco"))) {
	        query.append(" AND Tipo = ?");
	        parametri.add(tipo);
	    }
	    
	    if (minPrice != null) {
	        query.append(" AND Prezzo_Attuale >= ?");
	        parametri.add(minPrice);
	    }
	    
	    if(maxPrice != null) {    
	        query.append(" AND Prezzo_Attuale <= ?");
	        parametri.add(maxPrice);
	    }
	    
	    if (stato != null && (stato.equals("Nuovo") || stato.equals("Usato"))) {
	        query.append(" AND Stato = ?");
	        parametri.add(stato);
	    }
	    
	    try {
	    	conn = ConnectionPool.getConnection();
	    	ps = conn.prepareStatement(query.toString());
	    	
	    	for(int i = 0; i < parametri.size(); i++) {
	    		ps.setObject(i+1, parametri.get(i));
	    	}
	    	
	    	rs = ps.executeQuery();
	    	
	    	rs.next();
	    	
	    	count = rs.getInt(1);
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
		
		return count;
	}
}
