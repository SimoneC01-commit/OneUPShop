package model.dettaglioOrdine;

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

public class DettaglioOrdineDAO implements DAOInterface<DettaglioOrdineBean, DettaglioOrdineKey> {

	@Override
	public void doSave(DettaglioOrdineBean entry) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "INSERT INTO dettaglio_ordine (ID_Ordine, ID_Prodotto, Prezzo_Vendita_Storico, IVA_Storico) VALUES (?, ?, ?, ?)";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, entry.getIdOrdine());
			ps.setInt(2, entry.getProdotto().getIdProdotto());
			ps.setBigDecimal(3, entry.getPrezzoVenditaStorico());
			ps.setInt(4, entry.getIvaStorico());

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
	public DettaglioOrdineBean doRetrieveByKey(DettaglioOrdineKey key) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DettaglioOrdineBean bean = null;
		String query = "SELECT ID_Ordine, ID_Prodotto, Prezzo_Vendita_Storico, IVA_Storico FROM dettaglio_ordine WHERE ID_Ordine = ? AND ID_Prodotto = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, key.getIdOrdine());
			ps.setInt(2, key.getIdProdotto());
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				bean = new DettaglioOrdineBean();
				bean.setIdOrdine(rs.getInt("ID_Ordine"));
				
				ProdottoDAO prodottoDAO = new ProdottoDAO();
				ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(rs.getInt("ID_Prodotto"));
				bean.setProdotto(prodotto);
				
				bean.setPrezzoVenditaStorico(rs.getBigDecimal("Prezzo_Vendita_Storico"));
				bean.setIvaStorico(rs.getInt("IVA_Storico"));
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
	public List<DettaglioOrdineBean> doRetrieveAll() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DettaglioOrdineBean> lista = null;
		String query = "SELECT ID_Ordine, ID_Prodotto, Prezzo_Vendita_Storico, IVA_Storico FROM dettaglio_ordine";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			
			lista = new ArrayList<DettaglioOrdineBean>();
			ProdottoDAO prodottoDAO = new ProdottoDAO();
			
			while(rs.next()) {
				DettaglioOrdineBean bean = new DettaglioOrdineBean();
				bean.setIdOrdine(rs.getInt("ID_Ordine"));
				
				ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(rs.getInt("ID_Prodotto"));
				bean.setProdotto(prodotto);
				
				bean.setPrezzoVenditaStorico(rs.getBigDecimal("Prezzo_Vendita_Storico"));
				bean.setIvaStorico(rs.getInt("IVA_Storico"));
				
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

	public List<DettaglioOrdineBean> doRetrieveByOrdine(int idOrdine) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DettaglioOrdineBean> lista = null;
		String query = "SELECT ID_Ordine, ID_Prodotto, Prezzo_Vendita_Storico, IVA_Storico FROM dettaglio_ordine WHERE ID_Ordine = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, idOrdine);
			rs = ps.executeQuery();
			
			lista = new ArrayList<DettaglioOrdineBean>();
			ProdottoDAO prodottoDAO = new ProdottoDAO();
			
			while(rs.next()) {
				DettaglioOrdineBean bean = new DettaglioOrdineBean();
				bean.setIdOrdine(rs.getInt("ID_Ordine"));
				
				ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(rs.getInt("ID_Prodotto"));
				bean.setProdotto(prodotto);
				
				bean.setPrezzoVenditaStorico(rs.getBigDecimal("Prezzo_Vendita_Storico"));
				bean.setIvaStorico(rs.getInt("IVA_Storico"));
				
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
	public void doUpdate(DettaglioOrdineBean entry) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "UPDATE dettaglio_ordine SET Prezzo_Vendita_Storico = ?, IVA_Storico = ? WHERE ID_Ordine = ? AND ID_Prodotto = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setBigDecimal(1, entry.getPrezzoVenditaStorico());
			ps.setInt(2, entry.getIvaStorico());
			ps.setInt(3, entry.getIdOrdine());
			ps.setInt(4, entry.getProdotto().getIdProdotto());
			
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
	public void doDelete(DettaglioOrdineKey key) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "DELETE FROM dettaglio_ordine WHERE ID_Ordine = ? AND ID_Prodotto = ?";
		
		try {
    		conn = ConnectionPool.getConnection();
    		ps = conn.prepareStatement(query);
    		
    		ps.setInt(1, key.getIdOrdine());
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
	
	public List<DettaglioOrdineBean> doRetrieveAllByOrder(int idOrdine) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<DettaglioOrdineBean> lista = null;
		String query = "SELECT ID_Ordine, ID_Prodotto, Prezzo_Vendita_Storico, IVA_Storico FROM dettaglio_ordine WHERE ID_Ordine = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);

			ps.setInt(1, idOrdine);
			
			rs = ps.executeQuery();
			
			lista = new ArrayList<DettaglioOrdineBean>();
			ProdottoDAO prodottoDAO = new ProdottoDAO();
			
			while(rs.next()) {
				DettaglioOrdineBean bean = new DettaglioOrdineBean();
				bean.setIdOrdine(rs.getInt("ID_Ordine"));
				
				ProdottoBean prodotto = prodottoDAO.doRetrieveByKey(rs.getInt("ID_Prodotto"));
				bean.setProdotto(prodotto);
				
				bean.setPrezzoVenditaStorico(rs.getBigDecimal("Prezzo_Vendita_Storico"));
				bean.setIvaStorico(rs.getInt("IVA_Storico"));
				
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
}