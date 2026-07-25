package model.ordine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.DAOInterface;

public class OrdineDAO implements DAOInterface<OrdineBean, Integer> {

	@Override
	public void doSave(OrdineBean entry) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "INSERT INTO ordine (Email_Utente, Data_Ordine, Stato_Ordine, Totale_Ordine, Indirizzo_Spedizione, Telefono, Metodo_Pagamento) VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, entry.getEmailUtente());
			ps.setTimestamp(2, entry.getDataOrdine());
			ps.setString(3, entry.getStatoOrdine());
			ps.setBigDecimal(4, entry.getTotaleOrdine());
			ps.setString(5, entry.getIndirizzoSpedizione());
			ps.setString(6, entry.getTelefono());
			ps.setString(7, entry.getMetodoPagamento());

			ps.executeUpdate();
            
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				entry.setIdOrdine(rs.getInt(1));
			}
        } catch (SQLException e) {
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
                ConnectionPool.releaseConnection(conn);
            }
        }
	}

	@Override
	public OrdineBean doRetrieveByKey(Integer key) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		OrdineBean bean = null;
		String query = "SELECT ID_Ordine, Email_Utente, Data_Ordine, Stato_Ordine, Totale_Ordine, Indirizzo_Spedizione, Telefono, Metodo_Pagamento FROM ordine WHERE ID_Ordine = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, key);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				bean = new OrdineBean();
				
				bean.setIdOrdine(rs.getInt("ID_Ordine"));
				bean.setEmailUtente(rs.getString("Email_Utente"));
				bean.setDataOrdine(rs.getTimestamp("Data_Ordine"));
				bean.setStatoOrdine(rs.getString("Stato_Ordine"));
				bean.setTotaleOrdine(rs.getBigDecimal("Totale_Ordine"));
				bean.setIndirizzoSpedizione(rs.getString("Indirizzo_Spedizione"));
				bean.setTelefono(rs.getString("Telefono"));
				bean.setMetodoPagamento(rs.getString("Metodo_Pagamento"));
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
	public List<OrdineBean> doRetrieveAll() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<OrdineBean> lista = null;
		String query = "SELECT ID_Ordine, Email_Utente, Data_Ordine, Stato_Ordine, Totale_Ordine, Indirizzo_Spedizione, Telefono, Metodo_Pagamento FROM ordine";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			
			lista = new ArrayList<OrdineBean>();
			
			while(rs.next()) {
				OrdineBean bean = new OrdineBean();
				
				bean.setIdOrdine(rs.getInt("ID_Ordine"));
				bean.setEmailUtente(rs.getString("Email_Utente"));
				bean.setDataOrdine(rs.getTimestamp("Data_Ordine"));
				bean.setStatoOrdine(rs.getString("Stato_Ordine"));
				bean.setTotaleOrdine(rs.getBigDecimal("Totale_Ordine"));
				bean.setIndirizzoSpedizione(rs.getString("Indirizzo_Spedizione"));
				bean.setTelefono(rs.getString("Telefono"));
				bean.setMetodoPagamento(rs.getString("Metodo_Pagamento"));
				
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
	public void doUpdate(OrdineBean entry) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "UPDATE ordine SET Email_Utente = ?, Data_Ordine = ?, Stato_Ordine = ?, Totale_Ordine = ?, Indirizzo_Spedizione = ?, Telefono = ?, Metodo_Pagamento = ? WHERE ID_Ordine = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setString(1, entry.getEmailUtente());
			ps.setTimestamp(2, entry.getDataOrdine());
			ps.setString(3, entry.getStatoOrdine());
			ps.setBigDecimal(4, entry.getTotaleOrdine());
			ps.setString(5, entry.getIndirizzoSpedizione());
			ps.setString(6, entry.getTelefono());
			ps.setString(7, entry.getMetodoPagamento());
			ps.setInt(8, entry.getIdOrdine());
			
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
		String query = "DELETE FROM ordine WHERE ID_Ordine = ?";
		
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
	
	public List<OrdineBean> doRetrieveAllForUser(String email) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<OrdineBean> lista = null;
		String query = "SELECT ID_Ordine, Email_Utente, Data_Ordine, Stato_Ordine, Totale_Ordine, Indirizzo_Spedizione, Telefono, Metodo_Pagamento FROM ordine WHERE Email_Utente = ?";
		
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
				bean.setTotaleOrdine(rs.getBigDecimal("Totale_Ordine"));
				bean.setIndirizzoSpedizione(rs.getString("Indirizzo_Spedizione"));
				bean.setTelefono(rs.getString("Telefono"));
				bean.setMetodoPagamento(rs.getString("Metodo_Pagamento"));
				
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
	
	public void doUpdateStato(int idOrdine, String statoOrdine) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "UPDATE ordine SET Stato_Ordine = ? WHERE ID_Ordine = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setString(1, statoOrdine);
			
			ps.setInt(2, idOrdine);
			
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