package application.database;

import java.util.Formatter;

import application.model.Group;

public class GroupsTable extends Table {

	// Courses groups table
	private static GroupsTable instance = new GroupsTable();
	
	private GroupsTable() {
		// TODO Auto-generated constructor stub
		String name = "groups";
		String create = "(group_id INT(11) AUTO_INCREMENT COMMENT 'Group\\'s ID', course_id INT(11) NOT NULL COMMENT 'Appropriate course\\'s ID', "
				+ "group_name VARCHAR(50) NOT NULL COMMENT 'Group\\'s name', group_details TEXT COMMENT 'Group\\'s information', "
				+ "PRIMARY KEY (group_id), FOREIGN KEY (course_id) REFERENCES courses (course_id) ON DELETE CASCADE ON UPDATE CASCADE, UNIQUE (course_id, group_name))";
		String options = "ENGINE = InnoDB COMMENT = 'Course groups table'";
		String select = "";
		setDatabase(InAltoSchoolDatabase.getInstance());
		setTableName(name,create,options,select);
	}
	
	public static GroupsTable getInstance() {
		return instance;
	}
	
	@Override
	public boolean insert (Object row) {
		// TODO Auto-generated method stub
		if (row != null) {
			String view = ((Group) row).getGroup_details();
			view = view.replace("\u0027","\\'");
			String query;
			String fields = "(course_id, group_name, group_details)";
			String values = "("+((Group) row).getCourse_id()+", \'"+((Group) row).getGroup_name()+"\', \'"+view+"\')";
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
			String view = ((Group) row).getGroup_details();
			view = view.replace("\u0027","\\'");
			String query;
			String options = "";
			String values = "SET course_id = "+((Group) row).getCourse_id()+", group_name = \'"+((Group) row).getGroup_name()+
					"\', group_details = \'"+view+"\'";
			String where = "WHERE group_id = "+row_id;
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
			String where = "WHERE group_id = "+row_id;
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
