package application.database;

import java.util.Formatter;

import application.model.Login;

public class LoginsTable extends Table {

	// School user logins table
	private static LoginsTable instance = new LoginsTable();
	
	private LoginsTable() {
		// TODO Auto-generated constructor stub
		String name = "logins";
		String create = "(login_id INT(11) AUTO_INCREMENT COMMENT 'Login\\'s ID', role_id INT(11) NOT NULL COMMENT 'Appropriate role\\'s ID', "
				+ "login_name VARCHAR(50) NOT NULL COMMENT 'Login\\'s name', login_password INT(11) NOT NULL COMMENT 'Login\\'s password hashcode', "
				+ "login_question VARCHAR(50) NOT NULL COMMENT 'Login\\'s security question', login_answer INT(11) NOT NULL "
				+ "COMMENT 'Login\\'s security answer hashcode', PRIMARY KEY (login_id), FOREIGN KEY (role_id) "
				+ "REFERENCES roles (role_id) ON DELETE CASCADE ON UPDATE CASCADE, UNIQUE (login_name))";
		String options = "ENGINE = InnoDB COMMENT = 'Users logins table'";
		String select = "";
		setDatabase(InAltoSchoolDatabase.getInstance());
		setTableName(name,create,options,select);
	}
	
	public static LoginsTable getInstance() {
		return instance;
	}
	
	@Override
	public boolean insert (Object row) {
		// TODO Auto-generated method stub
		if (row != null) {
			String query;
			String fields = "(role_id, login_name, login_password, login_question, login_answer)";
			String values = "("+((Login) row).getRole_id()+", \'"+((Login) row).getLogin_name()+"\', "+((Login) row).getLogin_password()+
					", \'"+((Login) row).getLogin_question()+"\', "+((Login) row).getLogin_answer()+")";
			Formatter formatter = new Formatter();
			formatter.format(Queries.TableInsert.getValue(),getTableName(),fields,values);
			query = formatter.toString();
			formatter.close();
			if (getDatabase().executeUpdate(query) >= 0)
				return true;
		}
		return false;
	}

	@Override
	public boolean update(int row_id, Object row) {
		// TODO Auto-generated method stub
		if ((row_id > 0) && (row != null)) {
			String query;
			String options = "";
			String values = "SET role_id = "+((Login) row).getRole_id()+", login_name = \'"+((Login) row).getLogin_name()+
					"\', login_password = "+((Login) row).getLogin_password()+", login_question = \'"+((Login) row).getLogin_question()+
					"\', login_answer = "+((Login) row).getLogin_answer();
			String where = "WHERE login_id = "+row_id;
			Formatter formatter = new Formatter();
			formatter.format(Queries.TableUpdate.getValue(),options,getTableName(),values,where);
			query = formatter.toString();
			formatter.close();
			if (getDatabase().executeUpdate(query) >= 0)
				return true;
		}
		return false;
	}

	@Override
	public boolean delete(int row_id) {
		// TODO Auto-generated method stub
		if (row_id > 0) {
			String query;
			String where = "WHERE login_id = "+row_id;
			Formatter formatter = new Formatter();
			formatter.format(Queries.TableDelete.getValue(),getTableName(),where);
			query = formatter.toString();
			formatter.close();
			if (getDatabase().executeUpdate(query) >= 0)
				return true;
		}
		return false;
	}

}
