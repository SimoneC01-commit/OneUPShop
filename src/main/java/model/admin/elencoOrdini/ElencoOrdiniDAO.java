package model.admin.elencoOrdini;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ConnectionPool;

public class ElencoOrdiniDAO {
	public ArrayList<OrdineBean> doRetrieveAll() throws SQLException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String query = "SELECT ID_Ordine, Email_Utente, Data_Ordine, Stato_Ordine, Totale_Ordine, Indirizzo_Spedizione, Telefono, Metodo_Pagamento " +
						"FROM ordine " +
						"ORDER BY Data_Ordine DESC";
		
		ArrayList<OrdineBean> ordini = null;
		
		try {
			conn = ConnectionPool.getConnection();
    		ps = conn.prepareStatement(query);
    		
    		rs = ps.executeQuery();

    		ordini = new ArrayList<OrdineBean>();
    		
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
    			
    			ordini.add(bean);
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
		
		return ordini;
	}

}
