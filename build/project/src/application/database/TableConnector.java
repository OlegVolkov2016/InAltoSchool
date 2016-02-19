package application.database;

import java.sql.ResultSet;

public interface TableConnector {
	
	// Interface of table operations
	
	public String getTableName();
	public void setTableName(String tableName, String create, String options, String select);
	public DatabaseConnector getDatabase();
	public void setDatabase(DatabaseConnector database);
	public ResultSet executeSelect(String query, String options, String from, String where, String group, String having, String order);
	public boolean executeUpdate(String options, String values, String where);
	public boolean executeDelete(String where);
	public boolean insert(Object row);
	public boolean update(int row_id, Object row);
	public boolean delete(int row_id);

}
