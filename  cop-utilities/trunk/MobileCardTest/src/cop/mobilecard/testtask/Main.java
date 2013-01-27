package cop.mobilecard.testtask;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Oleg Cherednik
 * @since 27.01.2013
 */
public class Main {
	public static void main(String[] args) {
		getUserName();
		DatabaseUtils.getInstance().checkAvailableDatabases();

		Connection conn = null;

		try {
			conn = DatabaseUtils.getInstance().getConnection(getConnectionProperties());
		} catch (Exception e) {
			System.err.println("Can't connect to database. Please check connection properties.");
			return;
		}

		try {
			Set<Table> tables = DatabaseUtils.getInstance().getTables(conn);

			for (Table table : tables)
				System.out.println(table.getName() + " (" + table.getTotalIndexes() + ')');

			System.out.println("--- total " + tables.size() + " table(s) ---");
		} catch (Exception e) {
			System.err.println("Database operation error.");
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {}
		}
	}

	/**
	 * Ask user for database properties, that can be used to create database connection.
	 * 
	 * @return full set of database connection properties
	 * @throws IOException
	 */
	private static Properties getConnectionProperties() throws IOException {
		Properties props = new Properties();
		Database database = getDatabase();

		getDriverName(database);

		String jdbcUrl = getJdbcUrl(database);
		String host = getHost();
		int port = getPort(database);
		String databaseName = getDatabaseName();

		props.put(DatabaseUtils.PROP_URL, Database.getURL(jdbcUrl, host, port, databaseName));
		props.setProperty(DatabaseUtils.PROP_USER, getUser());
		props.setProperty(DatabaseUtils.PROP_PASSWORD, getPassword());

		return props;
	}

	/** Ask user form user name, to type greetings */
	private static void getUserName() {
		System.out.print("Please, type your name: ");
		String userName = ConsoleUtils.getInstance().readLine(false);
		System.out.println("Hello, " + userName + '.');
	}

	/**
	 * Ask user to select database from predefined list.
	 * 
	 * @return <tt>null</tt> or predefined database type
	 * @throws IOException
	 */
	private static Database getDatabase() throws IOException {
		if (DatabaseUtils.getInstance().getTotalAvailableDatabase() == 0)
			return null;

		System.out.println("\n> Database:");
		int i = 0;
		Map<Integer, Database> map = new HashMap<Integer, Database>();

		for (Database type : Database.values()) {
			if (!type.isAvailable())
				continue;

			map.put(++i, type);
			System.out.println(i + ". " + type.getName());
		}

		map.put(++i, null);
		System.out.println(i + ". other");
		System.out.print("Your choice: ");

		int val = ConsoleUtils.getInstance().readInt(map.keySet());

		if (map.containsKey(val))
			return map.get(val);

		return null;
	}

	/**
	 * Ask user for drive name, if give database type is not set (<tt>null</tt>)
	 * 
	 * @param database predefined database type, can be <tt>null</tt>
	 * @return not <tt>null</tt> database drive name
	 */
	private static String getDriverName(Database database) {
		if (database != null)
			return database.getDriverName();
		String driverName;

		while (true) {
			System.out.print("\n> Driver name (e.g. '" + Database.POSTGRESQL.getDriverName() + "', required): ");

			driverName = ConsoleUtils.getInstance().readLine(false);

			if (Database.register(driverName))
				return driverName;

			System.err.println("Driver is not found: " + driverName + '.');
		}
	}

	/**
	 * Ask user for jdbc url, if give database type is not set (<tt>null</tt>).
	 * 
	 * @param database predefined database type, can be <tt>null</tt>
	 * @return not <tt>null</tt> jdbc url
	 */

	private static String getJdbcUrl(Database database) {
		if (database != null)
			return database.getJdbcUrl();

		while (true) {
			System.out.print("\n> Jdbc URL (e.g. '" + Database.POSTGRESQL.getJdbcUrl() + "', required): ");

			return ConsoleUtils.getInstance().readLine(false);
		}
	}

	/**
	 * Ask user for database port, if give database type is not set (<tt>null</tt>).
	 * 
	 * @param type database predefined database type, can be <tt>null</tt>
	 * @return database port
	 */
	private static int getPort(Database type) {
		if (type != null) {
			System.out.print("\n> Port '" + type.getPort() + "', optional: ");
			int port = ConsoleUtils.getInstance().readInt(1);
			return port <= 0 ? type.getPort() : port;
		} else {
			System.out.println("\n> Port: ");
			return ConsoleUtils.getInstance().readInt(1);
		}
	}

	/**
	 * Ask user for database host.
	 * 
	 * @return not <ttnull</tt> database host
	 */
	private static String getHost() {
		System.out.print("\n> Host name (e.g. 'localhost'), optional: ");
		String hostName = ConsoleUtils.getInstance().readLine(true);
		return hostName.isEmpty() ? "localhost" : hostName;
	}

	/**
	 * Ask user for database name.
	 * 
	 * @return not <ttnull</tt> database name
	 */
	private static String getDatabaseName() {
		System.out.print("\n> Database name (e.g. 'pilot', required): ");
		return ConsoleUtils.getInstance().readLine(false);
	}

	/**
	 * Ask user for database user.
	 * 
	 * @return not <ttnull</tt> database user
	 */
	private static String getUser() {
		System.out.print("\n> User (e.g. 'oleg', required): ");
		return ConsoleUtils.getInstance().readLine(false);
	}

	/**
	 * Ask user for database password.
	 * 
	 * @return not <ttnull</tt> database password
	 */
	private static String getPassword() {
		System.out.print("\n> Password (e.g. 'password', required): ");
		return ConsoleUtils.getInstance().readLine(false);
	}
}
