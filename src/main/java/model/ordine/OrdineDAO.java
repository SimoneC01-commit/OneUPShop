package model.ordine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.ConnectionPool;
import model.carrello.ProdottoCarrelloBean;

public class OrdineDAO {

    public void saveOrdine(OrdineBean ordine, List<ProdottoCarrelloBean> prodottiCarrello) throws SQLException {
        Connection conn = null;
        PreparedStatement psOrdine = null;
        PreparedStatement psDettaglio = null;
        ResultSet rs = null;
        
        String queryOrdine = "INSERT INTO ordine (Email_Utente, Totale_Ordine, Indirizzo_Spedizione_Storico, Telefono, Metodo_Pagamento) VALUES (?, ?, ?, ?, ?)";
        String queryDettaglio = "INSERT INTO dettaglio_ordine (ID_Ordine, ID_Prodotto, Prezzo_Vendita_Storico) VALUES (?, ?, ?)";
        
        try {
            conn = ConnectionPool.getConnection();
            conn.setAutoCommit(false);
            
            psOrdine = conn.prepareStatement(queryOrdine, Statement.RETURN_GENERATED_KEYS);
            psOrdine.setString(1, ordine.getEmailUtente());
            psOrdine.setFloat(2, ordine.getTotaleOrdine());
            psOrdine.setString(3, ordine.getIndirizzoSpedizioneStorico());
            psOrdine.setString(4, ordine.getTelefono());
            psOrdine.setString(5, ordine.getMetodoPagamento());
            
            if (psOrdine.executeUpdate() == 0) {
                throw new SQLException("Creazione ordine fallita.");
            }
            
            rs = psOrdine.getGeneratedKeys();
            int idOrdineGenerato = 0;
            if (rs.next()) {
                idOrdineGenerato = rs.getInt(1);
            } else {
                throw new SQLException("Impossibile recuperare l'ID ordine.");
            }
            
            psDettaglio = conn.prepareStatement(queryDettaglio);
            for (ProdottoCarrelloBean p : prodottiCarrello) {
                psDettaglio.setInt(1, idOrdineGenerato);
                psDettaglio.setInt(2, p.getId());
                psDettaglio.setFloat(3, p.getPrezzo());
                psDettaglio.addBatch();
            }
            
            psDettaglio.executeBatch();
            
            conn.commit();
            
        } catch (SQLException e) {
            if (conn != null) {
                try { 
                	conn.rollback(); 
                } catch (SQLException ex) { 
                	ex.printStackTrace(); 
                }
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
            	rs.close();
            }
            if (psOrdine != null) {
            	psOrdine.close();
            }
            if (psDettaglio != null) {
            	psDettaglio.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true);
                ConnectionPool.releaseConnection(conn);
            }
        }
    }
    
    public ArrayList<OrdineBean> retrieveOrdini(String email) throws SQLException {
    	
    	Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	String query = "SELECT ID_Ordine, Email_Utente, Data_Ordine, Stato_Ordine, Totale_Ordine, Indirizzo_Spedizione_Storico, Telefono, Metodo_Pagamento " +
    					"FROM ordine " +
    					"WHERE Email_Utente = ?";
    	
    	ArrayList<OrdineBean> lista = null;
    	
    	try {
    		conn = ConnectionPool.getConnection();
    		ps = conn.prepareStatement(query);
    		
    		ps.setString(1, email);
    		
    		rs = ps.executeQuery();
    		
    		lista = new ArrayList<OrdineBean>();
    		
    		while(rs.next()) {
    			OrdineBean bean = new OrdineBean();
    			
    			bean.setIdOrdine(rs.getInt("ID_Ordine"));
    			bean.setEmailUtente(rs.getString("Email_Utente"));
    			bean.setDataOrdine(rs.getTimestamp("Data_Ordine")); 
    			bean.setStatoOrdine(rs.getString("Stato_Ordine"));
    			bean.setTotaleOrdine(rs.getFloat("Totale_Ordine"));
    			bean.setIndirizzoSpedizioneStorico(rs.getString("Indirizzo_Spedizione_Storico"));
    			bean.setTelefono(rs.getString("Telefono"));
    			bean.setMetodoPagamento(rs.getString("Metodo_Pagamento"));
    			
    			lista.add(bean);
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
    	
    	return lista;
    }
    
    public OrdineBean retrieveOrdineByKey(int id) throws SQLException {
    	Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	String query = "SELECT ID_Ordine, Email_Utente, Data_Ordine, Stato_Ordine, Totale_Ordine, Indirizzo_Spedizione_Storico, Telefono, Metodo_Pagamento " +
				"FROM ordine " +
				"WHERE ID_Ordine = ?";
    	
    	OrdineBean bean = null;
    	
    	try {
    		conn = ConnectionPool.getConnection();
    		ps = conn.prepareStatement(query);
    		
    		ps.setInt(1, id);
    		
    		rs = ps.executeQuery();
    		
    		if(rs.next()) {
        		bean = new OrdineBean();
        		
    			bean.setIdOrdine(rs.getInt("ID_Ordine"));
    			bean.setEmailUtente(rs.getString("Email_Utente"));
    			bean.setDataOrdine(rs.getTimestamp("Data_Ordine")); 
    			bean.setStatoOrdine(rs.getString("Stato_Ordine"));
    			bean.setTotaleOrdine(rs.getFloat("Totale_Ordine"));
    			bean.setIndirizzoSpedizioneStorico(rs.getString("Indirizzo_Spedizione_Storico"));
    			bean.setTelefono(rs.getString("Telefono"));
    			bean.setMetodoPagamento(rs.getString("Metodo_Pagamento"));
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

	public void deleteOrdineByKey(int idOrdine) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = null;
        PreparedStatement psOrdine = null;
        PreparedStatement psDettaglio = null;
    	
    	String queryDettaglio = "DELETE FROM dettaglio_ordine WHERE ID_Ordine = ?";
    	String queryOrdine = "DELETE FROM ordine WHERE ID_Ordine = ?";
    	
    	try {
    		conn = ConnectionPool.getConnection();
    		
    		conn.setAutoCommit(false);
    		
    		psDettaglio = conn.prepareStatement(queryDettaglio);
    		psDettaglio.setInt(1, idOrdine);
    		psDettaglio.executeUpdate();
    		
    		psOrdine = conn.prepareStatement(queryOrdine);
    		psOrdine.setInt(1, idOrdine);
    		psOrdine.executeUpdate();
    		
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
			if(psOrdine != null) {
				psOrdine.close();
			}
			if(psDettaglio != null) {
				psDettaglio.close();
			}
			if(conn != null) {
				ConnectionPool.releaseConnection(conn);
			}
		}
	}
}