package model.admin.elencoOrdini;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ConnectionPool;

public class ElencoOrdiniDAO {
	public ArrayList<OrdineBean> doRetrieveAll() throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT ID_Ordine, Email_Utente, Data_Ordine, Stato_Ordine, Totale_Ordine, Indirizzo_Spedizione, Telefono, Metodo_Pagamento " +
						"FROM ordine " +
						"ORDER BY Data_Ordine DESC";
		
		ArrayList<OrdineBean> ordini = null;
		
		try {
			conn = ConnectionPool.getConnection();
    		ps = conn.prepareStatement(query);
    		
    		rs = ps.executeQuery();

    		ordini = new ArrayList<OrdineBean>();
    		
    		while(rs.next()) {
    			OrdineBean ordine = new OrdineBean();
    			
    			ordine.setIdOrdine(rs.getInt("ID_Ordine"));
    			ordine.setEmailUtente(rs.getString("Email_Utente"));
    			ordine.setDataOrdine(rs.getTimestamp("Data_Ordine"));
    			ordine.setStatoOrdine(rs.getString("Stato_Ordine"));
    			ordine.setTotaleOrdine(rs.getBigDecimal("Totale_Ordine"));
    			ordine.setIndirizzoSpedizione(rs.getString("Indirizzo_Spedizione"));
    			ordine.setTelefono(rs.getString("Telefono"));
    			ordine.setMetodoPagamento(rs.getString("Metodo_Pagamento"));
    			
    			ordini.add(ordine);
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
		
		return ordini;
	}

	public void removeOrdineByKey(int id) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		String query = "DELETE FROM Ordine WHERE ID_Ordine = ?";
		
		try {
    		conn = ConnectionPool.getConnection();
    		
    		conn.setAutoCommit(false);

    		ps = conn.prepareStatement(query);
    		ps.setInt(1, id);
    		ps.executeUpdate();
    		
    		conn.commit();
		}
		catch(SQLException e) {
    		if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
    		
    		e.printStackTrace();
    		
    		throw e;
    	}
		finally {
    		if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
			if(ps != null) {
				ps.close();
			}
			if(conn != null) {
				ConnectionPool.releaseConnection(conn);
			}
		}
		
	}
	
	public OrdineBean doRetrieveByKey(int id) throws SQLException{
		Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	String query = "SELECT ID_Ordine, Email_Utente, Data_Ordine, Stato_Ordine, Totale_Ordine, Indirizzo_Spedizione, Telefono, Metodo_Pagamento " +
    					"FROM ordine " +
    					"WHERE ID_Ordine = ?";
    	
    	OrdineBean ordine = null;
		
    	try {
    		conn = ConnectionPool.getConnection();
    		ps = conn.prepareStatement(query);
    		
    		ps.setInt(1, id);
    		
    		rs = ps.executeQuery();
    		
    		if(rs.next()) {
    			ordine = new OrdineBean();
                
    			ordine.setIdOrdine(rs.getInt("ID_Ordine"));
    			ordine.setEmailUtente(rs.getString("Email_Utente"));
    			ordine.setDataOrdine(rs.getTimestamp("Data_Ordine"));
    			ordine.setStatoOrdine(rs.getString("Stato_Ordine"));
    			ordine.setTotaleOrdine(rs.getBigDecimal("Totale_Ordine"));
    			ordine.setIndirizzoSpedizione(rs.getString("Indirizzo_Spedizione"));
    			ordine.setTelefono(rs.getString("Telefono"));
    			ordine.setMetodoPagamento(rs.getString("Metodo_Pagamento"));
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
    	
		return ordine;
	}
}
