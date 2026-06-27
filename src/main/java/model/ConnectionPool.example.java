package model;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ConnectionPool {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/oneupshop"
            + "?useUnicode=true&useJDBCCompliantTimezoneShift=true"
            + "&useLegacyDatetimecode=false&serverTimezone=UTC";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static int INITIAL_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 20;

    private static List<Connection> freeDbConnections;
    private static int currentPoolSize = 0;
    private static boolean initialized = false;

    public static synchronized void init(int poolSize) throws SQLException {
        if (initialized) {
            return;
        }

        INITIAL_POOL_SIZE = poolSize > 0 && poolSize <= MAX_POOL_SIZE? poolSize : INITIAL_POOL_SIZE;

        freeDbConnections = new LinkedList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                freeDbConnections.add(createDBConnection());
            }
            currentPoolSize = INITIAL_POOL_SIZE;
            initialized = true;
        } catch (ClassNotFoundException e) {
            initialized = false;
            throw new SQLException("MySQL JDBC Driver not found or failed to load.", e);
        } catch (SQLException e) {
            initialized = false;
            throw e;
        }
    }

    private static Connection createDBConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        connection.setAutoCommit(true);
        return connection;
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (!initialized || freeDbConnections == null) {
            throw new SQLException("[ConnectionPool] Pool is not initialized.");
        }

        if (freeDbConnections.isEmpty()) {
            if (currentPoolSize < MAX_POOL_SIZE) {
                Connection conn = createDBConnection();
                currentPoolSize++;
                return conn;
            } else {
                throw new SQLException("[ConnectionPool] Pool exhausted: max connections reached (" + MAX_POOL_SIZE + ").");
            }
        }

        Connection conn = freeDbConnections.remove(0);

        try {
            if (!conn.isValid(1)) {
                try {
                    conn.close();
                } catch (SQLException ignored) {
                }
                currentPoolSize--;
                return getConnection();
            }
        } catch (SQLException e) {
            currentPoolSize--;
            return getConnection();
        }

        return conn;
    }

    public static synchronized void releaseConnection(Connection connection) {
        if (connection != null && initialized) {
            try {
                if (!connection.isClosed() && connection.isValid(1)) {
                    freeDbConnections.add(connection);
                } else {
                    connection.close();
                    currentPoolSize--;
                }
            } catch (SQLException ignored) {
            }
        } else if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {
            }
            currentPoolSize--;
        }
    }

    public static synchronized boolean isInitialized() {
        return initialized;
    }

    public static synchronized void shutdown() {
        if (!initialized) {
            return;
        }

        for (Connection conn : freeDbConnections) {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ignored) {
            }
        }
        if (freeDbConnections != null) {
            freeDbConnections.clear();
        }
        initialized = false;
        currentPoolSize = 0;

        releaseResources();
    }

    public static void releaseResources() {
        java.util.Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException ignored) {
            }
        }

        try {
            Class<?> clazz = Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread");
            clazz.getMethod("checkedShutdown").invoke(null);
        } catch (Exception ignored) {
        }
    }
}
