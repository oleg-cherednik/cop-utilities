package cop.mobilecard.testtask;

import java.sql.Driver;
import java.sql.DriverManager;

/**
 * Predefined databases and connection settings
 * 
 * @author Oleg Cherednik
 * @since 27.01.2013
 */
enum Database {
	POSTGRESQL("PostgreSQL", "org.postgresql.Driver", "jdbc:postgresql://", 5432),
	MYSQL("MySQL", "com.mysql.jdbc.Driver", "jdbc:mysql://", 3306),
	MSSQL("Microsoft SQL", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:microsoft:sqlserver://", 1433),
	ORACLE("Oracle", "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@//server.", 1521);

	private final String name;
	private final String driverName;
	private final String jdbcUrl;
	private final int port;
	private final boolean available;

	Database(String name, String driverName, String jdbcUrl, int port) {
		this.name = name;
		this.driverName = driverName;
		this.jdbcUrl = jdbcUrl;
		this.port = port;
		available = register();
	}

	public String getName() {
		return name;
	}

	public boolean isAvailable() {
		return available;
	}

	public String getDriverName() {
		return driverName;
	}

	public int getPort() {
		return port;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	/**
	 * Return connection url
	 * 
	 * @param host database host
	 * @param databaseName database name
	 * @return connection url
	 */
	public String getURL(String host, String databaseName) {
		return getURL(jdbcUrl, host, port, databaseName);
	}

	public boolean register() {
		return register(driverName);
	}

	// ========== static ==========

	/**
	 * Register given database driver, if it's exists in classpath
	 * 
	 * @param driverName database driver name
	 * @return <tt>true</tt> if driver with given name was successfully registered
	 */
	public static boolean register(String driverName) {
		try {
			DriverManager.registerDriver((Driver)Class.forName(driverName).newInstance());
			return true;
		} catch (Exception ignored) {}

		return false;
	}

	public static String getURL(String jdbcUrl, String host, int port, String databaseName) {
		return jdbcUrl + host + ':' + port + '/' + databaseName;
	}
}
