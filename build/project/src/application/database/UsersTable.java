package application.database;

import java.time.format.DateTimeFormatter;
import java.util.Formatter;

import application.model.User;

public class UsersTable extends Table {

	private static UsersTable instance = new UsersTable();
	
	private UsersTable() {
		// TODO Auto-generated constructor stub
		String name = "users";
		String create = "(user_id INT(11) AUTO_INCREMENT COMMENT 'User\\'s ID', login_id INT(11) NOT NULL COMMENT 'Appropriate login\\'s ID', "
				+ "user_firstname VARCHAR(50) COMMENT 'User\\'s first name', user_lastname VARCHAR(50) COMMENT 'User \\'s last name', "
				+ "user_birthday DATE COMMENT 'User\\'s birthday', user_address VARCHAR(50) COMMENT 'User\\'s address', "
				+ "user_phone VARCHAR(13) COMMENT 'User\\'s phone', user_email VARCHAR(50) COMMENT 'User\\'s email', "
				+ "user_skype VARCHAR(50) COMMENT 'User\\'s skype login', user_details TEXT COMMENT 'User\\'s information', "
				+ "PRIMARY KEY (user_id), FOREIGN KEY (login_id) REFERENCES logins (login_id) ON DELETE CASCADE ON UPDATE CASCADE, UNIQUE (login_id))";
		String options = "ENGINE = InnoDB COMMENT = 'Users data table'";
		String select = "";
		setDatabase(InAltoSchoolDatabase.getInstance());
		setTableName(name,create,options,select);
	}
	
	public static UsersTable getInstance() {
		return instance;
	}
	
	@Override
	public boolean insert (Object row) {
		// TODO Auto-generated method stub
		if (row != null) {
			String date;
			if (((User) row).getUser_birthday() == null)
				date = "NULL";
			else
				date = "\'"+((User) row).getUser_birthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\'";
			String view = ((User) row).getUser_details();
			view = view.replace("\u0027","\\'");
			String query;
			String fields = "(login_id, user_firstname, user_lastname, user_birthday, user_address, user_phone, user_email, user_skype, user_details)";
			String values = "("+((User) row).getLogin_id()+", \'"+((User) row).getUser_firstname()+"\', \'"+((User) row).getUser_lastname()+"\', "+
					date+", \'"+((User) row).getUser_address()+"\', \'"+((User) row).getUser_phone()+"\', \'"+((User) row).getUser_email()+"\', \'"+
					((User) row).getUser_skype()+"\', \'"+view+ "\')";
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
			String date;
			if (((User) row).getUser_birthday() == null)
				date = "NULL";
			else
				date = "\'"+((User) row).getUser_birthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\'";
			String view = ((User) row).getUser_details();
			view = view.replace("\u0027","\\'");
			String query;
			String options = "";
			String values = "SET login_id = "+((User) row).getLogin_id()+", user_firstname = \'"+((User) row).getUser_firstname()+
					"\', user_lastname = \'"+((User) row).getUser_lastname()+"\', user_birthday = "+date+", user_address = \'"+
					((User) row).getUser_address()+"\', user_phone = \'"+((User) row).getUser_phone()+"\', user_email = \'"+
					((User) row).getUser_email()+"\', user_skype = \'"+((User) row).getUser_skype()+"\', user_details = \'"+
					view+"\'";
			String where = "WHERE user_id = "+row_id;
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
			String where = "WHERE user_id = "+row_id;
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
