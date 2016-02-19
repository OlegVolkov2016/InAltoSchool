package application.database;

import java.util.Formatter;

import application.model.Role;

public class RolesTable extends Table {

	// School users roles table
	private static RolesTable instance = new RolesTable();
	
	private RolesTable() {
		// TODO Auto-generated constructor stub
		String name = "roles";
		String create = "(role_id INT(11) AUTO_INCREMENT COMMENT 'Role\\'s ID', role_name VARCHAR(50) NOT NULL COMMENT 'Role\\'s name', "
				+ "role_details TEXT COMMENT 'Role\\'s Information', PRIMARY KEY (role_id), UNIQUE (role_name))";
		String options = "ENGINE = InnoDB COMMENT = 'Users roles (rights) table'";
		String select = "";
		setDatabase(InAltoSchoolDatabase.getInstance());
		setTableName(name,create,options,select);
	}
	
	public static RolesTable getInstance() {
		return instance;
	}
	
	@Override
	public boolean insert (Object row) {
		// TODO Auto-generated method stub
		if (row != null) {
			String view = ((Role) row).getRole_details();
			view = view.replace("\u0027","\\'");
			String query;
			String fields = "(role_name, role_details)";
			String values = "(\'"+((Role) row).getRole_name()+"\', \'"+view+ "\')";
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
			String view = ((Role) row).getRole_details();
			view = view.replace("\u0027","\\'");
			String query;
			String options = "";
			String values = "SET role_name = \'"+((Role) row).getRole_name()+"\', role_details = \'"+view+"\'";
			String where = "WHERE role_id = "+row_id;
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
			String where = "WHERE role_id = "+row_id;
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
