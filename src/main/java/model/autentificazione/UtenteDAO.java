package model.autentificazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.ConnectionPool;
import model.DAOInterface;

public class UtenteDAO implements DAOInterface<UtenteBean, String> {

	@Override
	public void doSave(UtenteBean entry) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "INSERT INTO UTENTE (Nome, Cognome, Email, Password) VALUES (?, ?, ?, ?)";
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setString(1, entry.getNome());
			ps.setString(2, entry.getCognome());
			ps.setString(3, entry.getEmail());
			ps.setString(4, entry.getPassword());
			
			ps.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
			
			throw e;
		}
		finally {
	    	if(ps != null) {
	    		ps.close();
	    	}
	    	if(conn != null) {
	    		ConnectionPool.releaseConnection(conn);
	    	}
	    }
	}

	@Override
	public List<UtenteBean> doRetrieveAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doUpdate(UtenteBean entry) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UtenteBean doRetrieveByKey(String key) throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String query = "SELECT ID_Utente, Nome, Cognome, Email, Password, Telefono, Indirizzo, Ruolo, Saldo_Wallet, Metodo_Pagamento_Default " +
						"FROM utente " +
						"WHERE Email = ?";
	    UtenteBean utenteEsistente = null;
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setString(1, key);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				utenteEsistente = new UtenteBean();
				
				utenteEsistente.setIdUtente(rs.getInt("ID_Utente"));
	            utenteEsistente.setNome(rs.getString("Nome"));
	            utenteEsistente.setCognome(rs.getString("Cognome"));
	            utenteEsistente.setEmail(rs.getString("Email"));
	            utenteEsistente.setPassword(rs.getString("Password"));
	            utenteEsistente.setTelefono(rs.getString("Telefono"));
	            utenteEsistente.setIndirizzo(rs.getString("Indirizzo"));
	            utenteEsistente.setRuolo(rs.getString("Ruolo"));
	            utenteEsistente.setSaldoWallet(rs.getBigDecimal("Saldo_Wallet"));
	            utenteEsistente.setMetodoPagamentoDefault(rs.getString("Metodo_Pagamento_Default"));
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
	    
	    return utenteEsistente;
	}

	@Override
	public void doDelete(String key) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
