package cop.mobilecard.testtask;

/**
 * This class describes database table properites.
 * 
 * @author Oleg Cherednik
 * @since 28.01.2013
 */
final class Table implements Comparable<Table> {
	/** table name */
	private final String name;
	/** table indexes amount */
	private final int totalIndexes;

	public Table(String name, int totalIndexes) {
		assert name != null && name.length() != 0 : "Table name can't be empty";

		this.name = name;
		this.totalIndexes = totalIndexes;
	}

	public String getName() {
		return name;
	}

	public int getTotalIndexes() {
		return totalIndexes;
	}

	// ========== Comparable ==========

	@Override
	public int compareTo(Table table) {
		if (table == null)
			return 1;
		int cmp = name.compareTo(table.name);
		return cmp != 0 ? cmp : Integer.compare(totalIndexes, table.totalIndexes);
	}

	// ========== Object ==========

	public String toString() {
		return "table: " + name + ", indexes: " + totalIndexes;
	}
}
