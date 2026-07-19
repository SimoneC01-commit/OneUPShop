package model.console;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.DAOInterface;

public class ConsoleDAO implements DAOInterface<ConsoleBean, Integer> {

	@Override
	public void doSave(ConsoleBean entry) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "INSERT INTO console (ID_Prodotto, Tipo, Modello_Specifico) VALUES (?, ?, ?)";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, entry.getIdProdotto());
			ps.setString(2, entry.getTipo());
			ps.setString(3, entry.getModelloSpecifico());

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
	public ConsoleBean doRetrieveByKey(Integer key) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ConsoleBean bean = null;
		String query = "SELECT ID_Prodotto, Tipo, Modello_Specifico FROM console WHERE ID_Prodotto = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, key);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				bean = new ConsoleBean();
				
				bean.setIdProdotto(rs.getInt("ID_Prodotto"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setModelloSpecifico(rs.getString("Modello_Specifico"));
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
	public List<ConsoleBean> doRetrieveAll() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ConsoleBean> lista = null;
		String query = "SELECT ID_Prodotto, Tipo, Modello_Specifico FROM console";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			
			lista = new ArrayList<ConsoleBean>();
			
			while(rs.next()) {
				ConsoleBean bean = new ConsoleBean();
				
				bean.setIdProdotto(rs.getInt("ID_Prodotto"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setModelloSpecifico(rs.getString("Modello_Specifico"));
				
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
	public void doUpdate(ConsoleBean entry) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "UPDATE console SET Tipo = ?, Modello_Specifico = ? WHERE ID_Prodotto = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setString(1, entry.getTipo());
			ps.setString(2, entry.getModelloSpecifico());
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
		String query = "DELETE FROM console WHERE ID_Prodotto = ?";
		
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