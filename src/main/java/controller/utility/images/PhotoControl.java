package controller.utility.images;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.ConnectionPool;

public class PhotoControl {
	
	public synchronized static byte[] load(int idProdotto) throws SQLException {
		
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "SELECT Foto_BLOB FROM prodotto WHERE ID_Prodotto = ?";
		ResultSet rs = null;
		
		byte[] bt = null;
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			ps.setInt(1, idProdotto);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				bt = rs.getBytes("Foto_BLOB");
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
		
		return bt;
	}
	
	public synchronized static void upload(int idProdotto, String photo) throws SQLException, IOException{
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "UPDATE prodotto SET Foto_BLOB = ? WHERE ID_Prodotto = ?";

		File file = new File(photo);
		
		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
			
			try {
				FileInputStream fis = new FileInputStream(file);
				ps.setBinaryStream(1, fis, fis.available()); 
				ps.setInt(2, idProdotto);
				
				ps.executeUpdate();
				
				conn.commit();
				
			} catch(IOException e) {
				e.printStackTrace();
				
				throw e;
			}
		} catch(SQLException e) {
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

	public synchronized static void upload(int idProdotto, InputStream is, int lenght) throws SQLException, IOException{
		Connection conn = null;
		PreparedStatement ps = null;
		String query = "UPDATE prodotto SET Foto_BLOB = ? WHERE ID_Prodotto = ?";

		try {
			conn = ConnectionPool.getConnection();
			ps = conn.prepareStatement(query);
		
			ps.setBinaryStream(1, is, lenght); 
			ps.setInt(2, idProdotto);
			
			ps.executeUpdate();
			
		} catch(SQLException e) {
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
