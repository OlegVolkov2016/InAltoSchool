package application.database;

import java.sql.ResultSet;
import java.util.Formatter;

abstract class Table implements TableConnector {
	
	// Abstract Table implements TableConnector 

	private String tableName;
	private DatabaseConnector database;
	
	// Some interface methods implementations
	@Override
	public String getTableName() {
		return tableName;
	}
	
	@Override
	public void setTableName(String tableName, String create, String options, String select) {
		Formatter formatter = new Formatter();
		String query;
		formatter.format(Queries.TableCreate.getValue(), tableName, create, options, select);
		query = formatter.toString();
		formatter.close();
		if (getDatabase().executeUpdate(query) != -1) {
			this.tableName = tableName;
		}
	}
	
	@Override
	public DatabaseConnector getDatabase() {
		return database;
	}
	
	@Override
	public void setDatabase(DatabaseConnector database) {
		this.database = database;
	}

	@Override
	public ResultSet executeSelect(String options, String expression, String from, String where, String group, String having, String order) {
		String query;
		Formatter formatter = new Formatter();
		formatter.format(Queries.TableSelect.getValue(),options,expression,from,where,group,having,order);
		query = formatter.toString();
		formatter.close();
		return getDatabase().executeQuery(query);
	}

	@Override
	public boolean executeUpdate(String options, String values, String where) {
		// TODO Auto-generated method stub
		String query;
		Formatter formatter = new Formatter();
		formatter.format(Queries.TableUpdate.getValue(),options,getTableName(),values,where);
		query = formatter.toString();
		formatter.close();
		if (getDatabase().executeUpdate(query) >= 0)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean executeDelete(String where) {
		// TODO Auto-generated method stub
		String query;
		Formatter formatter = new Formatter();
		formatter.format(Queries.TableDelete.getValue(),getTableName(),where);
		query = formatter.toString();
		formatter.close();
		if (getDatabase().executeUpdate(query) >= 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean insert(Object row) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(int row_id, Object row) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int row_id) {
		// TODO Auto-generated method stub
		return false;
	}

}
