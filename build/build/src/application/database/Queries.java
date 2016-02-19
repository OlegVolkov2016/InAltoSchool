package application.database;

public enum Queries {
	
	// String database constants
	DatabaseDriver("com.mysql.jdbc.Driver"),
	DatabaseUrl("jdbc:mysql://localhost:3306/"),
	DatabaseCreate("CREATE DATABASE IF NOT EXISTS %1$s %2$s %3$s"),
	TableCreate("CREATE TABLE IF NOT EXISTS %1$s %2$s %3$s %4$s"),
	TableSelect("SELECT %1$s %2$s %3$s %4$s %5$s %6$s %7$s"),
	TableInsert("INSERT INTO %1$s %2$s VALUES %3$s"),
	TableUpdate("UPDATE %1$s %2$s %3$s %4$s"),
	TableDelete("DELETE FROM %1$s %2$s");
	
	private String value;
	
	private Queries(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

}
