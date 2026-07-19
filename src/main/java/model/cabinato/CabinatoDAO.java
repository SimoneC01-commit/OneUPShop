package model.cabinato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.DAOInterface;

public class CabinatoDAO implements DAOInterface<CabinatoBean, Integer> {

	@Override
	public void doSave(CabinatoBean entry) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "INSERT INTO cabinato (ID_Prodotto, Tipo, Tipo_Sistema_Arcade, Dimensioni_cm) VALUES (?, ?, ?, ?)";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, entry.getIdProdotto());
			ps.setString(2, entry.getTipo());
			ps.setString(3, entry.getTipoSistemaArcade());
			ps.setString(4, entry.getDimensioniCm());

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
	public CabinatoBean doRetrieveByKey(Integer key) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CabinatoBean bean = null;
		String query = "SELECT ID_Prodotto, Tipo, Tipo_Sistema_Arcade, Dimensioni_cm FROM cabinato WHERE ID_Prodotto = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, key);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				bean = new CabinatoBean();
				
				bean.setIdProdotto(rs.getInt("ID_Prodotto"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setTipoSistemaArcade(rs.getString("Tipo_Sistema_Arcade"));
				bean.setDimensioniCm(rs.getString("Dimensioni_cm"));
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
	public List<CabinatoBean> doRetrieveAll() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<CabinatoBean> lista = null;
		String query = "SELECT ID_Prodotto, Tipo, Tipo_Sistema_Arcade, Dimensioni_cm FROM cabinato";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			
			lista = new ArrayList<CabinatoBean>();
			
			while(rs.next()) {
				CabinatoBean bean = new CabinatoBean();
				
				bean.setIdProdotto(rs.getInt("ID_Prodotto"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setTipoSistemaArcade(rs.getString("Tipo_Sistema_Arcade"));
				bean.setDimensioniCm(rs.getString("Dimensioni_cm"));
				
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
	public void doUpdate(CabinatoBean entry) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "UPDATE cabinato SET Tipo = ?, Tipo_Sistema_Arcade = ?, Dimensioni_cm = ? WHERE ID_Prodotto = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setString(1, entry.getTipo());
			ps.setString(2, entry.getTipoSistemaArcade());
			ps.setString(3, entry.getDimensioniCm());
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
		String query = "DELETE FROM cabinato WHERE ID_Prodotto = ?";
		
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