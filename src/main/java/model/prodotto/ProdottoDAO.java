package model.prodotto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;
import model.DAOInterface;

public class ProdottoDAO implements DAOInterface<ProdottoBean, Integer> {

	@Override
	public void doSave(ProdottoBean entry) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "INSERT INTO prodotto (Titolo, Descrizione, Anno_Rilascio, Foto_BLOB, Azienda, Tipo, Prezzo_Acquisto, Prezzo_Attuale, Data_Aggiunta, Stato, Note_Difetti, Disponibile, IVA) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			conn = ConnectionPool.getConnection();
			
			ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, entry.getTitolo());
			ps.setString(2, entry.getDescrizione());
			ps.setInt(3, entry.getAnnoRilascio());
			ps.setBytes(4, entry.getFotoBlob());
			ps.setString(5, entry.getAzienda());
			ps.setString(6, entry.getTipo());
			ps.setBigDecimal(7, entry.getPrezzoAcquisto());
			ps.setBigDecimal(8, entry.getPrezzoAttuale());
			ps.setTimestamp(9, entry.getDataAggiunta());
			ps.setString(10, entry.getStato());
			ps.setString(11, entry.getNoteDifetti());
			ps.setBoolean(12, entry.isDisponibile());
			ps.setInt(13, entry.getIva());

			ps.executeUpdate();
            
			rs = ps.getGeneratedKeys();
	        if (rs.next()) {
	            entry.setIdProdotto(rs.getInt(1));
	        }
	        
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
	public ProdottoBean doRetrieveByKey(Integer key) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProdottoBean bean = null;
		String query = "SELECT ID_Prodotto, Titolo, Descrizione, Anno_Rilascio, Foto_BLOB, Azienda, Tipo, Prezzo_Acquisto, Prezzo_Attuale, Data_Aggiunta, Stato, Note_Difetti, Disponibile, IVA FROM prodotto WHERE ID_Prodotto = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, key);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				bean = new ProdottoBean();
				
				bean.setIdProdotto(rs.getInt("ID_Prodotto"));
				bean.setTitolo(rs.getString("Titolo"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setAnnoRilascio(rs.getInt("Anno_Rilascio"));
				bean.setFotoBlob(rs.getBytes("Foto_BLOB"));
				bean.setAzienda(rs.getString("Azienda"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setPrezzoAcquisto(rs.getBigDecimal("Prezzo_Acquisto"));
				bean.setPrezzoAttuale(rs.getBigDecimal("Prezzo_Attuale"));
				bean.setDataAggiunta(rs.getTimestamp("Data_Aggiunta"));
				bean.setStato(rs.getString("Stato"));
				bean.setNoteDifetti(rs.getString("Note_Difetti"));
				bean.setDisponibile(rs.getBoolean("Disponibile"));
				bean.setIva(rs.getInt("IVA"));
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
	public List<ProdottoBean> doRetrieveAll() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ProdottoBean> lista = null;
		String query = "SELECT ID_Prodotto, Titolo, Descrizione, Anno_Rilascio, Foto_BLOB, Azienda, Tipo, Prezzo_Acquisto, Prezzo_Attuale, Data_Aggiunta, Stato, Note_Difetti, Disponibile, IVA FROM prodotto";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			
			lista = new ArrayList<ProdottoBean>();
			
			while(rs.next()) {
				ProdottoBean bean = new ProdottoBean();
				
				bean.setIdProdotto(rs.getInt("ID_Prodotto"));
				bean.setTitolo(rs.getString("Titolo"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setAnnoRilascio(rs.getInt("Anno_Rilascio"));
				bean.setFotoBlob(rs.getBytes("Foto_BLOB"));
				bean.setAzienda(rs.getString("Azienda"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setPrezzoAcquisto(rs.getBigDecimal("Prezzo_Acquisto"));
				bean.setPrezzoAttuale(rs.getBigDecimal("Prezzo_Attuale"));
				bean.setDataAggiunta(rs.getTimestamp("Data_Aggiunta"));
				bean.setStato(rs.getString("Stato"));
				bean.setNoteDifetti(rs.getString("Note_Difetti"));
				bean.setDisponibile(rs.getBoolean("Disponibile"));
				bean.setIva(rs.getInt("IVA"));
				
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
	public void doUpdate(ProdottoBean entry) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "UPDATE prodotto SET Titolo = ?, Descrizione = ?, Anno_Rilascio = ?, Foto_BLOB = ?, Azienda = ?, Tipo = ?, Prezzo_Acquisto = ?, Prezzo_Attuale = ?, Data_Aggiunta = ?, Stato = ?, Note_Difetti = ?, Disponibile = ?, IVA = ? WHERE ID_Prodotto = ?";
		
		try {
			conn = ConnectionPool.getConnection();
			
			ps = conn.prepareStatement(query);
			
			ps.setString(1, entry.getTitolo());
			ps.setString(2, entry.getDescrizione());
			ps.setInt(3, entry.getAnnoRilascio());
			ps.setBytes(4, entry.getFotoBlob());
			ps.setString(5, entry.getAzienda());
			ps.setString(6, entry.getTipo());
			ps.setBigDecimal(7, entry.getPrezzoAcquisto());
			ps.setBigDecimal(8, entry.getPrezzoAttuale());
			ps.setTimestamp(9, entry.getDataAggiunta());
			ps.setString(10, entry.getStato());
			
			if("Usato".equals(entry.getStato())) {
				String note = entry.getNoteDifetti();

				if (note == null || note.trim().isEmpty()) {
			        ps.setString(11, "Nessun difetto segnalato"); 
			    } else {
			        ps.setString(11, note);
			    }
			}
			else {
				ps.setNull(11, Types.VARCHAR);
			}
			
			ps.setBoolean(12, entry.isDisponibile());
			ps.setInt(13, entry.getIva());
			ps.setInt(14, entry.getIdProdotto());
			
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
		
		String query = "DELETE FROM prodotto WHERE ID_Prodotto = ?";
		
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
	
	public List<ProdottoBean> doRetrieveAllNew(int amount) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ProdottoBean> lista = null;
		String query = "SELECT ID_Prodotto, Titolo, Descrizione, Anno_Rilascio, Foto_BLOB, Azienda, Tipo, Prezzo_Acquisto, Prezzo_Attuale, Data_Aggiunta, Stato, Note_Difetti, Disponibile, IVA FROM prodotto WHERE Disponibile = 1 ORDER BY Data_Aggiunta DESC";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			
			lista = new ArrayList<ProdottoBean>();
			
			while(rs.next()) {
				ProdottoBean bean = new ProdottoBean();
				
				bean.setIdProdotto(rs.getInt("ID_Prodotto"));
				bean.setTitolo(rs.getString("Titolo"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setAnnoRilascio(rs.getInt("Anno_Rilascio"));
				bean.setFotoBlob(rs.getBytes("Foto_BLOB"));
				bean.setAzienda(rs.getString("Azienda"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setPrezzoAcquisto(rs.getBigDecimal("Prezzo_Acquisto"));
				bean.setPrezzoAttuale(rs.getBigDecimal("Prezzo_Attuale"));
				bean.setDataAggiunta(rs.getTimestamp("Data_Aggiunta"));
				bean.setStato(rs.getString("Stato"));
				bean.setNoteDifetti(rs.getString("Note_Difetti"));
				bean.setDisponibile(rs.getBoolean("Disponibile"));
				bean.setIva(rs.getInt("IVA"));
				
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
		
		return lista.subList(0, amount);
	}

	public List<ProdottoBean> doRetrieveAllSuggested(int amount) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ProdottoBean> lista = null;
		String query = "SELECT ID_Prodotto, Titolo, Descrizione, Anno_Rilascio, Foto_BLOB, Azienda, Tipo, Prezzo_Acquisto, Prezzo_Attuale, Data_Aggiunta, Stato, Note_Difetti, Disponibile, IVA FROM prodotto WHERE Disponibile = 1 ORDER BY RAND()";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
			
			lista = new ArrayList<ProdottoBean>();
			
			while(rs.next()) {
				ProdottoBean bean = new ProdottoBean();
				
				bean.setIdProdotto(rs.getInt("ID_Prodotto"));
				bean.setTitolo(rs.getString("Titolo"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setAnnoRilascio(rs.getInt("Anno_Rilascio"));
				bean.setFotoBlob(rs.getBytes("Foto_BLOB"));
				bean.setAzienda(rs.getString("Azienda"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setPrezzoAcquisto(rs.getBigDecimal("Prezzo_Acquisto"));
				bean.setPrezzoAttuale(rs.getBigDecimal("Prezzo_Attuale"));
				bean.setDataAggiunta(rs.getTimestamp("Data_Aggiunta"));
				bean.setStato(rs.getString("Stato"));
				bean.setNoteDifetti(rs.getString("Note_Difetti"));
				bean.setDisponibile(rs.getBoolean("Disponibile"));
				bean.setIva(rs.getInt("IVA"));
				
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
		
		return lista.subList(0, amount);
	}
	
	public List<ProdottoBean> doRetriveAllByPageNumber(Integer minYear, Integer maxYear, String tipo, Float minPrice, Float maxPrice, String stato, int pagCorrente, int elemForPage) throws SQLException{

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<ProdottoBean> lista = new ArrayList<ProdottoBean>();
		
		int offset = (pagCorrente - 1) * elemForPage;

		StringBuilder query = new StringBuilder("SELECT ID_Prodotto, Titolo, Descrizione, Anno_Rilascio, Foto_BLOB, Azienda, Tipo, Prezzo_Acquisto, Prezzo_Attuale, Data_Aggiunta, Stato, Note_Difetti, Disponibile, IVA FROM Prodotto WHERE Disponibile = 1");
		
		ArrayList<Object> parametri = new ArrayList<Object>();
		
		if (minYear != null) {
		    query.append(" AND Anno_Rilascio >= ?");
		    parametri.add(minYear);
		}
		
		if (maxYear != null) {
		    query.append(" AND Anno_Rilascio <= ?");
		    parametri.add(maxYear);
		}
		
	    if (tipo != null && (tipo.equals("Cabinato") || tipo.equals("Console") || tipo.equals("Gadget") || tipo.equals("Gioco"))) {
	        query.append(" AND Tipo = ?");
	        parametri.add(tipo);
	    }
	    
	    if (minPrice != null) {
	        query.append(" AND Prezzo_Attuale >= ?");
	        parametri.add(minPrice);
	    }
	    
	    if(maxPrice != null) {    
	        query.append(" AND Prezzo_Attuale <= ?");
	        parametri.add(maxPrice);
	    }
	    
	    if (stato != null && (stato.equals("Nuovo") || stato.equals("Usato"))) {
	        query.append(" AND Stato = ?");
	        parametri.add(stato);
	    }
	    
	    query.append(" LIMIT ? OFFSET ?");
	    parametri.add(elemForPage);
	    parametri.add(offset);
		
	    try {
	    	conn = ConnectionPool.getConnection();
	    	ps = conn.prepareStatement(query.toString());
	    	
	    	for(int i = 0; i < parametri.size(); i++) {
	    		ps.setObject(i+1, parametri.get(i));
	    	}
	    	
	    	rs = ps.executeQuery();
	    	
	    	while(rs.next()) {
	    		ProdottoBean bean = new ProdottoBean();
				
				bean.setIdProdotto(rs.getInt("ID_Prodotto"));
				bean.setTitolo(rs.getString("Titolo"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setAnnoRilascio(rs.getInt("Anno_Rilascio"));
				bean.setFotoBlob(rs.getBytes("Foto_BLOB"));
				bean.setAzienda(rs.getString("Azienda"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setPrezzoAcquisto(rs.getBigDecimal("Prezzo_Acquisto"));
				bean.setPrezzoAttuale(rs.getBigDecimal("Prezzo_Attuale"));
				bean.setDataAggiunta(rs.getTimestamp("Data_Aggiunta"));
				bean.setStato(rs.getString("Stato"));
				bean.setNoteDifetti(rs.getString("Note_Difetti"));
				bean.setDisponibile(rs.getBoolean("Disponibile"));
				bean.setIva(rs.getInt("IVA"));
				
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
	
	public int doCountByFilters(Integer minYear, Integer maxYear, String tipo, Float minPrice, Float maxPrice, String stato) throws SQLException {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int count = 0;
		
		StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM Prodotto WHERE Disponibile = 1");
		ArrayList<Object> parametri = new ArrayList<Object>();

		if (minYear != null) {
		    query.append(" AND Anno_Rilascio >= ?");
		    parametri.add(minYear);
		}
		
		if (maxYear != null) {
		    query.append(" AND Anno_Rilascio <= ?");
		    parametri.add(maxYear);
		}
		
	    if (tipo != null && (tipo.equals("Cabinato") || tipo.equals("Console") || tipo.equals("Gadget") || tipo.equals("Gioco"))) {
	        query.append(" AND Tipo = ?");
	        parametri.add(tipo);
	    }
	    
	    if (minPrice != null) {
	        query.append(" AND Prezzo_Attuale >= ?");
	        parametri.add(minPrice);
	    }
	    
	    if(maxPrice != null) {    
	        query.append(" AND Prezzo_Attuale <= ?");
	        parametri.add(maxPrice);
	    }
	    
	    if (stato != null && (stato.equals("Nuovo") || stato.equals("Usato"))) {
	        query.append(" AND Stato = ?");
	        parametri.add(stato);
	    }
	    
	    try {
	    	conn = ConnectionPool.getConnection();
	    	ps = conn.prepareStatement(query.toString());
	    	
	    	for(int i = 0; i < parametri.size(); i++) {
	    		ps.setObject(i+1, parametri.get(i));
	    	}
	    	
	    	rs = ps.executeQuery();
	    	
	    	rs.next();
	    	
	    	count = rs.getInt(1);
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
		
		return count;
	}
	
	public ProdottoBean doRetrieveByKeyAndAvailable(Integer key) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProdottoBean bean = null;
		String query = "SELECT ID_Prodotto, Titolo, Descrizione, Anno_Rilascio, Foto_BLOB, Azienda, Tipo, Prezzo_Acquisto, Prezzo_Attuale, Data_Aggiunta, Stato, Note_Difetti, Disponibile, IVA FROM prodotto WHERE ID_Prodotto = ? AND Disponibile = 1";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, key);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				bean = new ProdottoBean();
				
				bean.setIdProdotto(rs.getInt("ID_Prodotto"));
				bean.setTitolo(rs.getString("Titolo"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setAnnoRilascio(rs.getInt("Anno_Rilascio"));
				bean.setFotoBlob(rs.getBytes("Foto_BLOB"));
				bean.setAzienda(rs.getString("Azienda"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setPrezzoAcquisto(rs.getBigDecimal("Prezzo_Acquisto"));
				bean.setPrezzoAttuale(rs.getBigDecimal("Prezzo_Attuale"));
				bean.setDataAggiunta(rs.getTimestamp("Data_Aggiunta"));
				bean.setStato(rs.getString("Stato"));
				bean.setNoteDifetti(rs.getString("Note_Difetti"));
				bean.setDisponibile(rs.getBoolean("Disponibile"));
				bean.setIva(rs.getInt("IVA"));
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
	
	public List<ProdottoBean> doRetriveAllByPageNumberAndAzienda(String azienda, Integer minYear, Integer maxYear, String tipo, Float minPrice, Float maxPrice, String stato, int pagCorrente, int elemForPage) throws SQLException{

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<ProdottoBean> lista = new ArrayList<ProdottoBean>();
		
		int offset = (pagCorrente - 1) * elemForPage;

		StringBuilder query = new StringBuilder("SELECT ID_Prodotto, Titolo, Descrizione, Anno_Rilascio, Foto_BLOB, Azienda, Tipo, Prezzo_Acquisto, Prezzo_Attuale, Data_Aggiunta, Stato, Note_Difetti, Disponibile, IVA FROM Prodotto WHERE Disponibile = 1 AND Azienda LIKE ?");
		
		ArrayList<Object> parametri = new ArrayList<Object>();
		
		if (minYear != null) {
		    query.append(" AND Anno_Rilascio >= ?");
		    parametri.add(minYear);
		}
		
		if (maxYear != null) {
		    query.append(" AND Anno_Rilascio <= ?");
		    parametri.add(maxYear);
		}
		
	    if (tipo != null && (tipo.equals("Cabinato") || tipo.equals("Console") || tipo.equals("Gadget") || tipo.equals("Gioco"))) {
	        query.append(" AND Tipo = ?");
	        parametri.add(tipo);
	    }
	    
	    if (minPrice != null) {
	        query.append(" AND Prezzo_Attuale >= ?");
	        parametri.add(minPrice);
	    }
	    
	    if(maxPrice != null) {    
	        query.append(" AND Prezzo_Attuale <= ?");
	        parametri.add(maxPrice);
	    }
	    
	    if (stato != null && (stato.equals("Nuovo") || stato.equals("Usato"))) {
	        query.append(" AND Stato = ?");
	        parametri.add(stato);
	    }
	    
	    query.append(" LIMIT ? OFFSET ?");
	    parametri.add(elemForPage);
	    parametri.add(offset);
		
	    try {
	    	conn = ConnectionPool.getConnection();
	    	ps = conn.prepareStatement(query.toString());

	    	ps.setString(1, "%"+azienda+"%");
	    	
	    	for(int i = 0; i < parametri.size(); i++) {
	    		ps.setObject(i+2, parametri.get(i));
	    	}
	    	
	    	rs = ps.executeQuery();
	    	
	    	while(rs.next()) {
	    		ProdottoBean bean = new ProdottoBean();
				
				bean.setIdProdotto(rs.getInt("ID_Prodotto"));
				bean.setTitolo(rs.getString("Titolo"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setAnnoRilascio(rs.getInt("Anno_Rilascio"));
				bean.setFotoBlob(rs.getBytes("Foto_BLOB"));
				bean.setAzienda(rs.getString("Azienda"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setPrezzoAcquisto(rs.getBigDecimal("Prezzo_Acquisto"));
				bean.setPrezzoAttuale(rs.getBigDecimal("Prezzo_Attuale"));
				bean.setDataAggiunta(rs.getTimestamp("Data_Aggiunta"));
				bean.setStato(rs.getString("Stato"));
				bean.setNoteDifetti(rs.getString("Note_Difetti"));
				bean.setDisponibile(rs.getBoolean("Disponibile"));
				bean.setIva(rs.getInt("IVA"));
				
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
	
	public int doCountByFiltersAndAzienda(String azienda, Integer minYear, Integer maxYear, String tipo, Float minPrice, Float maxPrice, String stato) throws SQLException {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int count = 0;
		
		StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM Prodotto WHERE Disponibile = 1 AND Azienda LIKE ?");
		ArrayList<Object> parametri = new ArrayList<Object>();

		if (minYear != null) {
		    query.append(" AND Anno_Rilascio >= ?");
		    parametri.add(minYear);
		}
		
		if (maxYear != null) {
		    query.append(" AND Anno_Rilascio <= ?");
		    parametri.add(maxYear);
		}
		
	    if (tipo != null && (tipo.equals("Cabinato") || tipo.equals("Console") || tipo.equals("Gadget") || tipo.equals("Gioco"))) {
	        query.append(" AND Tipo = ?");
	        parametri.add(tipo);
	    }
	    
	    if (minPrice != null) {
	        query.append(" AND Prezzo_Attuale >= ?");
	        parametri.add(minPrice);
	    }
	    
	    if(maxPrice != null) {    
	        query.append(" AND Prezzo_Attuale <= ?");
	        parametri.add(maxPrice);
	    }
	    
	    if (stato != null && (stato.equals("Nuovo") || stato.equals("Usato"))) {
	        query.append(" AND Stato = ?");
	        parametri.add(stato);
	    }
	    
	    try {
	    	conn = ConnectionPool.getConnection();
	    	ps = conn.prepareStatement(query.toString());
	    	
	    	ps.setString(1, "%"+azienda+"%");
	    	
	    	for(int i = 0; i < parametri.size(); i++) {
	    		ps.setObject(i+2, parametri.get(i));
	    	}
	    	
	    	rs = ps.executeQuery();
	    	
	    	rs.next();
	    	
	    	count = rs.getInt(1);
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
		
		return count;
	}
	
	public List<ProdottoBean> doRetrieveAllByTitolo(String titolo) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ProdottoBean> lista = null;
		String query = "SELECT ID_Prodotto, Titolo, Descrizione, Anno_Rilascio, Foto_BLOB, Azienda, Tipo, Prezzo_Acquisto, Prezzo_Attuale, Data_Aggiunta, Stato, Note_Difetti, Disponibile, IVA FROM prodotto WHERE Titolo LIKE ? ORDER BY Titolo";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);

			ps.setString(1, "%" + titolo + "%");
			
			rs = ps.executeQuery();
			
			lista = new ArrayList<ProdottoBean>();
			
			while(rs.next()) {
				ProdottoBean bean = new ProdottoBean();
				
				bean.setIdProdotto(rs.getInt("ID_Prodotto"));
				bean.setTitolo(rs.getString("Titolo"));
				bean.setDescrizione(rs.getString("Descrizione"));
				bean.setAnnoRilascio(rs.getInt("Anno_Rilascio"));
				bean.setFotoBlob(rs.getBytes("Foto_BLOB"));
				bean.setAzienda(rs.getString("Azienda"));
				bean.setTipo(rs.getString("Tipo"));
				bean.setPrezzoAcquisto(rs.getBigDecimal("Prezzo_Acquisto"));
				bean.setPrezzoAttuale(rs.getBigDecimal("Prezzo_Attuale"));
				bean.setDataAggiunta(rs.getTimestamp("Data_Aggiunta"));
				bean.setStato(rs.getString("Stato"));
				bean.setNoteDifetti(rs.getString("Note_Difetti"));
				bean.setDisponibile(rs.getBoolean("Disponibile"));
				bean.setIva(rs.getInt("IVA"));
				
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