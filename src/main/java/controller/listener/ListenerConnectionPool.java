package controller.listener;

import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;

import model.ConnectionPool;

@WebListener
public class ListenerConnectionPool implements ServletContextListener {
	public void contextInitialized(ServletContextEvent sce){
		try{
			ConnectionPool.init(5);
			System.out.println("[OneUPShop] Connection pool initialised");
		}
		catch(SQLException e) {
			System.out.println("[OneUPShop] Connection pool NOT initialised");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		ConnectionPool.shutdown();
		System.out.println("[OneUPShop] Connection pool closed");
	}
}