package application.database;

import java.util.Formatter;

import application.model.Student;

public class StudentsTable extends Table {

	// Groups or users students table
	private static StudentsTable instance = new StudentsTable();
	
	private StudentsTable() {
		// TODO Auto-generated constructor stub
		String name = "students";
		String create = "(student_id INT(11) AUTO_INCREMENT COMMENT 'Student\\'s ID', group_id INT(11) COMMENT 'Appropriate group\\'s ID', "
				+ "user_id INT(11) COMMENT 'Appropriate user\\'s ID', PRIMARY KEY (student_id), FOREIGN KEY (group_id) "
				+ "REFERENCES groups (group_id) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (user_id) REFERENCES users (user_id) "
				+ "ON DELETE CASCADE ON UPDATE CASCADE, UNIQUE (group_id, user_id))";
		String options = "ENGINE = InnoDB COMMENT = 'Groups students table'";
		String select = "";
		setDatabase(InAltoSchoolDatabase.getInstance());
		setTableName(name,create,options,select);
	}
	
	public static StudentsTable getInstance() {
		return instance;
	}
	
	@Override
	public boolean insert (Object row) {
		// TODO Auto-generated method stub
		if (row != null) {
			String query;
			String fields = "(group_id, user_id)";
			String values = "("+((Student) row).getGroup_id()+", "+((Student) row).getUser_id()+")";
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
			String values = "SET group_id = "+((Student) row).getGroup_id()+", user_id = "+((Student) row).getUser_id();
			String where = "WHERE student_id = "+row_id;
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
			String where = "WHERE student_id = "+row_id;
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
