package model.wishlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.DAOInterface;
import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;

public class WishlistDAO implements DAOInterface<WishlistBean, WishlistKey> {

	@Override
	public void doSave(WishlistBean entry) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "INSERT INTO wishlist (Email_Utente, ID_Prodotto) VALUES (?, ?)";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setString(1, entry.getEmailUtente());
			ps.setInt(2, entry.getProdotto().getIdProdotto());

			ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
            	ps.close();
            }
            if (conn != null) {
                ConnectionPool.releaseConnection(conn);
            }
        }
	}

	@Override
	public WishlistBean doRetrieveByKey(WishlistKey key) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		WishlistBean bean = null;
		String query = "SELECT Email_Utente, ID_Prodotto, Data_Inserimento FROM wishlist WHERE Email_Utente = ? AND ID_Prodotto = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setString(1, key.getEmailUtente());
			ps.setInt(2, key.getIdProdotto());
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				bean = new WishlistBean();
				bean.setEmailUtente(rs.getString("Email_Utente"));
				
				ProdottoDAO prodottoDAO = new ProdottoDAO();
				ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(rs.getInt("ID_Prodotto"));
				bean.setProdotto(prodotto);
				
				bean.setDataInserimento(rs.getTimestamp("Data_Inserimento"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
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

	@Override
	public List<WishlistBean> doRetrieveAll() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WishlistBean> lista = null;
		String query = "SELECT Email_Utente, ID_Prodotto, Data_Inserimento FROM wishlist";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			
			lista = new ArrayList<WishlistBean>();
			ProdottoDAO prodottoDAO = new ProdottoDAO();
			
			while(rs.next()) {
				WishlistBean bean = new WishlistBean();
				bean.setEmailUtente(rs.getString("Email_Utente"));
				
				ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(rs.getInt("ID_Prodotto"));
				bean.setProdotto(prodotto);
				
				bean.setDataInserimento(rs.getTimestamp("Data_Inserimento"));
				
				lista.add(bean);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
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
	
	@Override
	public void doUpdate(WishlistBean entry) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "UPDATE wishlist SET Data_Inserimento = ? WHERE Email_Utente = ? AND ID_Prodotto = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setTimestamp(1, entry.getDataInserimento());
			ps.setString(2, entry.getEmailUtente());
			ps.setInt(3, entry.getProdotto().getIdProdotto());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
            	ps.close();
            }
            if (conn != null) {
                ConnectionPool.releaseConnection(conn);
            }
        }
	}

	@Override
	public void doDelete(WishlistKey key) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "DELETE FROM wishlist WHERE Email_Utente = ? AND ID_Prodotto = ?";
		
		try {
    		conn = ConnectionPool.getConnection();
    		ps = conn.prepareStatement(query);
    		
    		ps.setString(1, key.getEmailUtente());
    		ps.setInt(2, key.getIdProdotto());
    		ps.executeUpdate();
    		
		} catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
            	ps.close();
            }
            if (conn != null) {
                ConnectionPool.releaseConnection(conn);
            }
        }
	}
	
	public List<WishlistBean> doRetrieveByUser(String emailUtente) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WishlistBean> lista = null;
		String query = "SELECT Email_Utente, ID_Prodotto, Data_Inserimento FROM wishlist WHERE Email_Utente = ? ORDER BY Data_Inserimento DESC";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setString(1, emailUtente);
			rs = ps.executeQuery();
			
			lista = new ArrayList<WishlistBean>();
			ProdottoDAO prodottoDAO = new ProdottoDAO();
			
			while(rs.next()) {
				WishlistBean bean = new WishlistBean();
				bean.setEmailUtente(rs.getString("Email_Utente"));
				
				ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(rs.getInt("ID_Prodotto"));
				bean.setProdotto(prodotto);
				
				bean.setDataInserimento(rs.getTimestamp("Data_Inserimento"));
				
				lista.add(bean);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
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
	
	public void doDeleteAllForUser(String email) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "DELETE FROM wishlist WHERE Email_Utente = ?";
		
		try {
    		conn = ConnectionPool.getConnection();
    		ps = conn.prepareStatement(query);
    		
    		ps.setString(1, email);
    		ps.executeUpdate();
    		
		} catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
            	ps.close();
            }
            if (conn != null) {
                ConnectionPool.releaseConnection(conn);
            }
        }
	}

}