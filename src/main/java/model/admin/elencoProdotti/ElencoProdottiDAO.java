package model.admin.elencoProdotti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.ConnectionPool;

public class ElencoProdottiDAO {
	
	public ArrayList<ProdottoElencoBean> doRetrieveAll() throws SQLException{
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT ID_Prodotto, Titolo, Descrizione, Anno_Rilascio, Foto_BLOB, " +
						    	"Azienda, Tipo, Prezzo_Acquisto, Prezzo_Attuale, Data_Aggiunta, " +
						    	"Stato, Note_Difetti, Disponibile, IVA " +
					    "FROM prodotto " +
					    "ORDER BY ID_Prodotto DESC";
		
		ArrayList<ProdottoElencoBean> prodotti = null;
		
		try {
    		conn = ConnectionPool.getConnection();
    		ps = conn.prepareStatement(query);
    		
    		rs = ps.executeQuery();
    		
    		prodotti = new ArrayList<ProdottoElencoBean>();
    		
    		while (rs.next()) {
                ProdottoElencoBean bean = new ProdottoElencoBean();
                
                bean.setIdProdotto(rs.getInt("ID_Prodotto"));
                bean.setTitolo(rs.getString("Titolo"));
                bean.setDescrizione(rs.getString("Descrizione"));
                bean.setAnnoRilascio(rs.getInt("Anno_Rilascio"));
                bean.setFotoBlob(rs.getBytes("Foto_BLOB"));
                bean.setAzienda(rs.getString("Azienda"));
                bean.setTipo(rs.getString("Tipo"));
                bean.setPrezzoAcquisto(rs.getBigDecimal("Prezzo_Acquisto"));
                bean.setPrezzoAttuale(rs.getBigDecimal("Prezzo_Attuale"));
                bean.setDataAggiunta(rs.getDate("Data_Aggiunta"));
                bean.setStato(rs.getString("Stato"));
                bean.setNoteDifetti(rs.getString("Note_Difetti"));
                bean.setDisponibile(rs.getBoolean("Disponibile"));
                bean.setIva(rs.getInt("IVA"));
                
                prodotti.add(bean);
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
		
		return prodotti;
	}

	public void removeProdottoByKey(int id) throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		
		String query = "DELETE FROM Prodotto WHERE ID_Prodotto = ?";
		
		try {
    		conn = ConnectionPool.getConnection();
    		
    		conn.setAutoCommit(false);

    		ps = conn.prepareStatement(query);
    		ps.setInt(1, id);
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
	
	public ProdottoElencoBean doRetrieveByKey(int id) throws SQLException{
		Connection conn = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	
    	String query = "SELECT ID_Prodotto, Titolo, Descrizione, Anno_Rilascio, Foto_BLOB, " +
						    	"Azienda, Tipo, Prezzo_Acquisto, Prezzo_Attuale, Data_Aggiunta, " +
						    	"Stato, Note_Difetti, Disponibile, IVA " +
					    "FROM prodotto " +
					    "WHERE ID_Prodotto = ?";
    	
    	ProdottoElencoBean prodotto = null;
		
    	try {
    		conn = ConnectionPool.getConnection();
    		ps = conn.prepareStatement(query);
    		
    		ps.setInt(1, id);
    		
    		rs = ps.executeQuery();
    		
    		if(rs.next()) {
    			prodotto = new ProdottoElencoBean();
                
    			prodotto.setIdProdotto(rs.getInt("ID_Prodotto"));
    			prodotto.setTitolo(rs.getString("Titolo"));
    			prodotto.setDescrizione(rs.getString("Descrizione"));
    			prodotto.setAnnoRilascio(rs.getInt("Anno_Rilascio"));
    			prodotto.setFotoBlob(rs.getBytes("Foto_BLOB"));
    			prodotto.setAzienda(rs.getString("Azienda"));
    			prodotto.setTipo(rs.getString("Tipo"));
    			prodotto.setPrezzoAcquisto(rs.getBigDecimal("Prezzo_Acquisto"));
    			prodotto.setPrezzoAttuale(rs.getBigDecimal("Prezzo_Attuale"));
    			prodotto.setDataAggiunta(rs.getDate("Data_Aggiunta"));
    			prodotto.setStato(rs.getString("Stato"));
    			prodotto.setNoteDifetti(rs.getString("Note_Difetti"));
    			prodotto.setDisponibile(rs.getBoolean("Disponibile"));
    			prodotto.setIva(rs.getInt("IVA"));
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
    	
		return prodotto;
	}
	
	public void doSave(ProdottoElencoBean prodotto) throws SQLException{
		Connection conn = null;
        PreparedStatement psProdotto = null;
        PreparedStatement psTipo = null;
        ResultSet rs = null;

        String queryProdotto = "INSERT INTO Prodotto (Titolo, Descrizione, Anno_Rilascio, Foto_BLOB, Azienda, Tipo, Prezzo_Acquisto, Prezzo_Attuale, Data_Aggiunta, Stato, Note_Difetti, IVA)" + 
        						"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        String queryTipo = null;
        
        try {
            conn = ConnectionPool.getConnection();
            conn.setAutoCommit(false);
            
            psProdotto = conn.prepareStatement(queryProdotto, Statement.RETURN_GENERATED_KEYS);
            
            psProdotto.setString(1, prodotto.getTitolo());
            psProdotto.setString(2, prodotto.getDescrizione());
            psProdotto.setInt(3, prodotto.getAnnoRilascio());
            psProdotto.setBytes(4, prodotto.getFotoBlob());
            psProdotto.setString(5, prodotto.getAzienda());
            psProdotto.setString(6, prodotto.getTipo());
            
            if (prodotto.getPrezzoAcquisto() != null) {
            	psProdotto.setBigDecimal(7, prodotto.getPrezzoAcquisto());
            } else {
            	psProdotto.setNull(7, java.sql.Types.DECIMAL);
            }

            psProdotto.setBigDecimal(8, prodotto.getPrezzoAttuale());
            psProdotto.setDate(9, prodotto.getDataAggiunta());
            psProdotto.setString(10, prodotto.getStato());
            
            if (prodotto.getNoteDifetti() != null) {
            	psProdotto.setString(11, prodotto.getNoteDifetti());
            } else {
            	psProdotto.setNull(11, java.sql.Types.VARCHAR);
            }
            
            psProdotto.setInt(12, prodotto.getIva());
            
            if (psProdotto.executeUpdate() == 0) {
                throw new SQLException("Aggiunta prodotto fallita.");
            }
            
            rs = psProdotto.getGeneratedKeys();
            int idProdottoAggiunto = 0;
            if (rs.next()) {
            	idProdottoAggiunto = rs.getInt(1);
            } else {
                throw new SQLException("Impossibile recuperare l'ID prodotto.");
            }
            
            if(prodotto instanceof GiocoBean) {
                queryTipo = "INSERT INTO Gioco (ID_Prodotto, Tipo, Sviluppatore) VALUES (?, ?, ?)";
                
                GiocoBean gioco = (GiocoBean) prodotto;
                
                psTipo = conn.prepareStatement(queryTipo);
                
                psTipo.setInt(1, idProdottoAggiunto);
                psTipo.setString(2, gioco.getTipo());
                
                if (gioco.getSviluppatore() != null) {
                    psTipo.setString(3, gioco.getSviluppatore());
                } else {
                    psTipo.setNull(3, java.sql.Types.VARCHAR);
                }
            } 
            else if(prodotto instanceof ConsoleBean) {
                queryTipo = "INSERT INTO Console (ID_Prodotto, Tipo, Modello_Specifico) VALUES (?, ?, ?)";
                
                ConsoleBean console = (ConsoleBean) prodotto;
                
                psTipo = conn.prepareStatement(queryTipo);
                
                psTipo.setInt(1, idProdottoAggiunto);
                psTipo.setString(2, console.getTipo());
                
                if (console.getModelloSpecifico() != null) {
                    psTipo.setString(3, console.getModelloSpecifico());
                } else {
                    psTipo.setNull(3, java.sql.Types.VARCHAR);
                }
            } 
            else if(prodotto instanceof GadgetBean) {
                queryTipo = "INSERT INTO Gadget (ID_Prodotto, Tipo, Tipo_Materiale, Tipo_Gadget) VALUES (?, ?, ?, ?)";
                
                GadgetBean gadget = (GadgetBean) prodotto;
                
                psTipo = conn.prepareStatement(queryTipo);
                
                psTipo.setInt(1, idProdottoAggiunto);
                psTipo.setString(2, gadget.getTipo());
                
                if (gadget.getTipoMateriale() != null) {
                    psTipo.setString(3, gadget.getTipoMateriale());
                } else {
                    psTipo.setNull(3, java.sql.Types.VARCHAR);
                }
                
                if (gadget.getTipoGadget() != null) {
                    psTipo.setString(4, gadget.getTipoGadget());
                } else {
                    psTipo.setNull(4, java.sql.Types.VARCHAR);
                }
            } 
            else if(prodotto instanceof CabinatoBean) {
                queryTipo = "INSERT INTO Cabinato (ID_Prodotto, Tipo, Tipo_Sistema_Arcade, Dimensioni_cm) VALUES (?, ?, ?, ?)";
                
                CabinatoBean cabinato = (CabinatoBean) prodotto;
                
                psTipo = conn.prepareStatement(queryTipo);
                
                psTipo.setInt(1, idProdottoAggiunto);
                psTipo.setString(2, cabinato.getTipo());
                
                if (cabinato.getTipoSistemaArcade() != null) {
                    psTipo.setString(3, cabinato.getTipoSistemaArcade());
                } else {
                    psTipo.setNull(3, java.sql.Types.VARCHAR);
                }
                
                if (cabinato.getDimensioniCm() != null) {
                    psTipo.setString(4, cabinato.getDimensioniCm());
                } else {
                    psTipo.setNull(4, java.sql.Types.VARCHAR);
                }
            }
            
            if(psTipo != null) {
            	psTipo.executeUpdate();
            }
            
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
            if (psProdotto != null) {
            	psProdotto.close();
            }
            if (psTipo != null) {
            	psTipo.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true);
                ConnectionPool.releaseConnection(conn);
            }
        }
	}
}
