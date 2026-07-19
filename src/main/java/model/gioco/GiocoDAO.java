package model.gioco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.DAOInterface;

public class GiocoDAO implements DAOInterface<GiocoBean, Integer> {

	@Override
	public void doSave(GiocoBean entry) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "INSERT INTO gioco (ID_Prodotto, Tipo, Sviluppatore) VALUES (?, ?, ?)";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, entry.getIdProdotto());
			ps.setString(2, entry.getTipo());
			ps.setString(3, entry.getSviluppatore());

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
	public GiocoBean doRetrieveByKey(Integer key) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		GiocoBean bean = null;
		String query = "SELECT ID_Prodotto, Tipo, Sviluppatore FROM gioco WHERE ID_Prodotto = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, key);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				bean = new GiocoBean();
				
				bean.setIdProdotto(rs.getInt("ID_Prodotto"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setSviluppatore(rs.getString("Sviluppatore"));
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
	public List<GiocoBean> doRetrieveAll() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<GiocoBean> lista = null;
		String query = "SELECT ID_Prodotto, Tipo, Sviluppatore FROM gioco";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			
			lista = new ArrayList<GiocoBean>();
			
			while(rs.next()) {
				GiocoBean bean = new GiocoBean();
				
				bean.setIdProdotto(rs.getInt("ID_Prodotto"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setSviluppatore(rs.getString("Sviluppatore"));
				
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
	public void doUpdate(GiocoBean entry) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "UPDATE gioco SET Tipo = ?, Sviluppatore = ? WHERE ID_Prodotto = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setString(1, entry.getTipo());
			ps.setString(2, entry.getSviluppatore());
			ps.setInt(3, entry.getIdProdotto());
			
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
	public void doDelete(Integer key) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "DELETE FROM gioco WHERE ID_Prodotto = ?";
		
		try {
    		conn = ConnectionPool.getConnection();
    		ps = conn.prepareStatement(query);
    		
    		ps.setInt(1, key);
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