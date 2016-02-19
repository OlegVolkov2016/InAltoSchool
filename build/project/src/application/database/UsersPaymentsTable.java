package application.database;

import java.time.format.DateTimeFormatter;
import java.util.Formatter;

import application.model.UserPayment;

public class UsersPaymentsTable extends Table {

	// Courses users payments table
	private static UsersPaymentsTable instance = new UsersPaymentsTable();
	
	private UsersPaymentsTable() {
		// TODO Auto-generated constructor stub
		String name = "users_payments";
		String create = "(users_payment_id INT(11) AUTO_INCREMENT COMMENT 'User\\'s payment ID', user_id INT(11) NOT NULL COMMENT 'Appropriate user ID', "
				+ "course_id INT(11) NOT NULL COMMENT 'Appropriate course ID', users_payment_date DATE NOT NULL COMMENT 'Appropriate date of payment', "
				+ "users_payment_sum DECIMAL(7,2) NOT NULL COMMENT 'Sum of payment', PRIMARY KEY (users_payment_id), FOREIGN KEY (user_id) REFERENCES users (user_id) "
				+ "ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (course_id) REFERENCES courses (course_id) ON DELETE CASCADE ON UPDATE CASCADE,"
				+ "UNIQUE (user_id, course_id, users_payment_date))";
		String options = "ENGINE = InnoDB COMMENT = 'Users payments table'";
		String select = "";
		setDatabase(InAltoSchoolDatabase.getInstance());
		setTableName(name,create,options,select);
	}
	
	public static UsersPaymentsTable getInstance() {
		return instance;
	}
	
	@Override
	public boolean insert (Object row) {
		// TODO Auto-generated method stub
		if (row != null) {
			String date;
			if (((UserPayment) row).getUsers_payment_date() == null)
				date = "NULL";
			else
				date = "\'"+((UserPayment) row).getUsers_payment_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\'";
			String query;
			String fields = "(user_id, course_id, users_payment_date, users_payment_sum)";
			String values = "("+((UserPayment) row).getUser_id()+", "+ ((UserPayment) row).getCourse_id()+", "+date+", "+((UserPayment) row).getUsers_payment_sum()+")";
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
			if (((UserPayment) row).getUsers_payment_date() == null)
				date = "NULL";
			else
				date = "\'"+((UserPayment) row).getUsers_payment_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\'";
			String query;
			String options = "";
			String values = "SET user_id = "+((UserPayment) row).getUser_id()+", course_id = "+((UserPayment) row).getCourse_id()+
					", users_payment_date = "+date+", users_payment_sum = "+((UserPayment) row).getUsers_payment_sum();
			String where = "WHERE users_payment_id = "+row_id;
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
			String where = "WHERE users_payment_id = "+row_id;
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
