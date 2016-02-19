package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Formatter;
import java.util.Properties;

abstract class Database implements DatabaseConnector {
	
	// Abstract Database implements DatabaseConnector 
	
	private String databaseName;
	private Connection databaseConnection;
	private Statement databaseStatement;
	private final String user = "root";
	private final String password = "";
	private final String encoding = "UTF-8"; 
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		disconnect();
	}
	
	// Interface methods implementations
	@Override
	public boolean connect() {
		if (getDatabaseName().equals("")) {
			System.out.println("Database name is not defined");
			return false;
		}
		else {
			try {
				Properties properties=new Properties();
				properties.setProperty("user",user);
				properties.setProperty("password",password);
				properties.setProperty("useUnicode","true");
				properties.setProperty("characterEncoding",encoding);
				databaseConnection = DriverManager.getConnection(Queries.DatabaseUrl.getValue()+getDatabaseName(),properties);
				databaseStatement = getDatabaseConnection().createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				System.out.println("SQLException when connect: "+e.getMessage());
			}
		}
		return true;
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		try {
			databaseStatement.close();
			databaseConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("SQLException when disconnect: "+e.getMessage());
		}	
	}

	@Override
	public int executeUpdate(String query) {
		// TODO Auto-generated method stub
		try {
			return getDatabaseStatement().executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("SQLEception when executeUpdate: "+e.getMessage());
			return -1;
		}
	}

	@Override
	public ResultSet executeQuery(String query) {
		// TODO Auto-generated method stub
		try {
			return getDatabaseStatement().executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("SQLException when executeQuery: "+e.getMessage());
			return null;
		}
	}

	@Override
	public String getDatabaseName() {
		// TODO Auto-generated method stub
		return databaseName;
	}
	
	@Override
	public void setDatabaseName(String databaseName, String charset, String collate) {
		// TODO Auto-generated method stub
		String query;
		try {
			Class.forName(Queries.DatabaseDriver.getValue()).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("SQLException when setting driver: "+e.getMessage());
			return;
		}
		try {
			databaseConnection = DriverManager.getConnection(Queries.DatabaseUrl.getValue(),user,password);
			databaseStatement = getDatabaseConnection().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("SQLException when connecting to mysql: "+e.getMessage());
			return;
		}
		Formatter formatter = new Formatter();
		formatter.format(Queries.DatabaseCreate.getValue(), databaseName, charset, collate);
		query = formatter.toString();
		formatter.close();
		if (executeUpdate(query) != -1) {
			this.databaseName = databaseName;
			connect();
		}
	}
	
	@Override
	public Connection getDatabaseConnection() {
		// TODO Auto-generated method stub
		return databaseConnection;
	}
	
	@Override
	public Statement getDatabaseStatement() {
		// TODO Auto-generated method stub
		return databaseStatement;
	}

}
