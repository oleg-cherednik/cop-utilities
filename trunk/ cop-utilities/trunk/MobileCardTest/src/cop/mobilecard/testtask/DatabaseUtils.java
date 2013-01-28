package cop.mobilecard.testtask;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class contains some methods to work with database using JDBC.
 * 
 * @author Oleg Cherednik
 * @since 27.01.2013
 */
final class DatabaseUtils {
	private static final DatabaseUtils INSTANCE = new DatabaseUtils();

	public static final String PROP_URL = "url";
	public static final String PROP_USER = "user";
	public static final String PROP_PASSWORD = "password";

	public static DatabaseUtils getInstance() {
		return INSTANCE;
	}

	private DatabaseUtils() {}

	/** Checks if predefined databases are available or not */
	public void checkAvailableDatabases() {
		System.out.println("\n> Check available drivers. ");

		for (Database type : Database.values())
			System.out.println(type.getName() + "... " + (type.isAvailable() ? "OK" : "NOT FOUND"));
	}

	/**
	 * Returns amount of available predefined databases.
	 * 
	 * @return amount of available predefined databases
	 */
	public int getTotalAvailableDatabase() {
		int total = 0;

		for (Database type : Database.values())
			if (type.isAvailable())
				total++;

		return total;
	}

	/**
	 * Returns database connection with given properties
	 * 
	 * @param props database connection properties
	 * @return not <tt>null</tt> database connection
	 * @throws SQLException if connection was not created
	 */
	public Connection getConnection(Properties props) throws SQLException {
		System.out.println("\n> Connection to database.");
		System.out.println(PROP_URL + '=' + props.getProperty(PROP_URL));
		System.out.println(PROP_USER + '=' + props.getProperty(PROP_USER));
		System.out.println(PROP_PASSWORD + '=' + props.getProperty(PROP_PASSWORD));

		return DriverManager.getConnection(props.getProperty(PROP_URL), props);
	}

	/**
	 * Return set of all tables in given database, including some properites.
	 * 
	 * @param conn not <tt>null</tt> database connection
	 * @return set not <tt>null</tt> set of all database tables
	 * @throws SQLException
	 */
	public Set<Table> getTables(Connection conn) throws SQLException {
		System.out.println("\n> Read tables info... ");

		DatabaseMetaData metaData = conn.getMetaData();
		Set<String> names = getTablesNames(metaData);

		if (names.isEmpty())
			return Collections.emptySet();

		Set<Table> tables = new TreeSet<Table>();

		for (String name : names)
			tables.add(new Table(name, getTotalTableIndexes(name, metaData)));

		return Collections.unmodifiableSet(tables);
	}

	/**
	 * Returns set of database tables names.
	 * 
	 * @param metaData database meta data
	 * @return not <tt>null</tt> set of database tables names
	 * @throws SQLException
	 */
	private Set<String> getTablesNames(DatabaseMetaData metaData) throws SQLException {
		ResultSet rs = metaData.getTables(null, null, "%", new String[] { "TABLE" });

		Set<String> names = new TreeSet<String>();

		while (rs.next())
			names.add(rs.getString("TABLE_NAME"));

		return names.isEmpty() ? Collections.<String> emptySet() : Collections.unmodifiableSet(names);
	}

	/**
	 * Returns total amount of given table's indexes
	 * 
	 * @param tableName database table name
	 * @param metaData database meta data
	 * @return amount of table's indexes
	 * @throws SQLException
	 */
	private int getTotalTableIndexes(String tableName, DatabaseMetaData metaData) throws SQLException {
		ResultSet rs = metaData.getIndexInfo(null, null, tableName, false, false);

		int total = 0;

		while (rs.next())
			total++;

		return total;
	}
}
