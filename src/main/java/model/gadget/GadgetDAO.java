package model.gadget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.DAOInterface;

public class GadgetDAO implements DAOInterface<GadgetBean, Integer> {

	@Override
	public void doSave(GadgetBean entry) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "INSERT INTO gadget (ID_Prodotto, Tipo, Tipo_Materiale, Tipo_Gadget) VALUES (?, ?, ?, ?)";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, entry.getIdProdotto());
			ps.setString(2, entry.getTipo());
			ps.setString(3, entry.getTipoMateriale());
			ps.setString(4, entry.getTipoGadget());

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
	public GadgetBean doRetrieveByKey(Integer key) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		GadgetBean bean = null;
		String query = "SELECT ID_Prodotto, Tipo, Tipo_Materiale, Tipo_Gadget FROM gadget WHERE ID_Prodotto = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, key);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				bean = new GadgetBean();
				
				bean.setIdProdotto(rs.getInt("ID_Prodotto"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setTipoMateriale(rs.getString("Tipo_Materiale"));
				bean.setTipoGadget(rs.getString("Tipo_Gadget"));
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
	public List<GadgetBean> doRetrieveAll() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<GadgetBean> lista = null;
		String query = "SELECT ID_Prodotto, Tipo, Tipo_Materiale, Tipo_Gadget FROM gadget";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			
			lista = new ArrayList<GadgetBean>();
			
			while(rs.next()) {
				GadgetBean bean = new GadgetBean();
				
				bean.setIdProdotto(rs.getInt("ID_Prodotto"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setTipoMateriale(rs.getString("Tipo_Materiale"));
				bean.setTipoGadget(rs.getString("Tipo_Gadget"));
				
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
	public void doUpdate(GadgetBean entry) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "UPDATE gadget SET Tipo = ?, Tipo_Materiale = ?, Tipo_Gadget = ? WHERE ID_Prodotto = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setString(1, entry.getTipo());
			ps.setString(2, entry.getTipoMateriale());
			ps.setString(3, entry.getTipoGadget());
			ps.setInt(4, entry.getIdProdotto());
			
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
		String query = "DELETE FROM gadget WHERE ID_Prodotto = ?";
		
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