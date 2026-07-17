package model.wishlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ConnectionPool;

public class WishlistDAO {
	
	public ArrayList<ElementoWishlistBean> doRetrieveAllByKey(String email) throws SQLException{
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT Email_Utente, ID_Prodotto, Data_Inserimento FROM wishlist WHERE Email_Utente = ?";
		
		ArrayList<ElementoWishlistBean> wishlist = null;		
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			
			wishlist = new ArrayList<ElementoWishlistBean>();
			
			while(rs.next()) {
				ElementoWishlistBean bean = new ElementoWishlistBean();
				
				bean.setEmailUtente(rs.getString("Email_Utente"));
				bean.setIdProdotto(rs.getInt("ID_Prodotto"));
				bean.setDataInserimento(rs.getTimestamp("Data_Inserimento"));
				
				wishlist.add(bean);
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
		
		return wishlist;
	}

	public void removeProdottoByKey(String email, int idProdotto) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		
		String query = "DELETE FROM wishlist WHERE Email_Utente = ? AND ID_Prodotto = ?";
		
		try {
    		conn = ConnectionPool.getConnection();
    		
    		conn.setAutoCommit(false);

    		ps = conn.prepareStatement(query);
    		ps.setString(1, email);
    		ps.setInt(2, idProdotto);
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
	
	public ElementoWishlistBean doRetrieveByKey(String email, int idProdotto) throws SQLException{
		Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	String query = "SELECT Email_Utente, ID_Prodotto, Data_Inserimento " +
					    "FROM wishlist " +
					    "WHERE Email_Utente = ? AND ID_Prodotto = ?";
    	
    	ElementoWishlistBean elem = null;
		
    	try {
    		conn = ConnectionPool.getConnection();
    		ps = conn.prepareStatement(query);
    		
    		ps.setString(1, email);
    		ps.setInt(2, idProdotto);
    		
    		rs = ps.executeQuery();
    		
    		if(rs.next()) {
    			elem = new ElementoWishlistBean();
                
    			elem.setEmailUtente(rs.getString("Email_Utente"));
    			elem.setIdProdotto(rs.getInt("ID_Prodotto"));
    			elem.setDataInserimento(rs.getTimestamp("Data_Inserimento"));
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
    	
		return elem;
	}
	
	public void doSave(ElementoWishlistBean elemento) throws SQLException{
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "INSERT INTO wishlist (Email_Utente, ID_Prodotto, Data_Inserimento)" + 
        				"VALUES(?, ?, ?)";
        
        try {
            conn = ConnectionPool.getConnection();
            conn.setAutoCommit(false);
            
            ps = conn.prepareStatement(query);
            
            ps.setString(1, elemento.getEmailUtente());
            ps.setInt(2, elemento.getIdProdotto());
            ps.setTimestamp(3, elemento.getDataInserimento());
            
            ps.executeUpdate();
            
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
            if (ps != null) {
            	ps.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true);
                ConnectionPool.releaseConnection(conn);
            }
        }
	}
}