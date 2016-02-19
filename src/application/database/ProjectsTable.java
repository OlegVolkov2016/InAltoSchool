package application.database;

import java.util.Formatter;

import application.model.Project;

public class ProjectsTable extends Table {

	// School projects table 
	private static ProjectsTable instance = new ProjectsTable();
	
	private ProjectsTable() {
		// TODO Auto-generated constructor stub
		String name = "projects";
		String create = "(project_id INT(11) AUTO_INCREMENT COMMENT 'Project\\'s ID', author_id INT(11) NOT NULL COMMENT 'Appropriate author\\'s ID', "
				+ "project_name VARCHAR(50) NOT NULL COMMENT 'Project\\'s name', project_details TEXT COMMENT 'Project\\'s information', "
				+ "PRIMARY KEY (project_id), FOREIGN KEY (author_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE, UNIQUE (author_id, project_name))";
		String options = "ENGINE = InnoDB COMMENT = 'School projects table'";
		String select = "";
		setDatabase(InAltoSchoolDatabase.getInstance());
		setTableName(name,create,options,select);
	}
	
	public static ProjectsTable getInstance() {
		return instance;
	}
	
	@Override
	public boolean insert (Object row) {
		// TODO Auto-generated method stub
		if (row != null) {
			String view = ((Project) row).getProject_details();
			view = view.replace("\u0027","\\'");
			String query;
			String fields = "(author_id, project_name, project_details)";
			String values = "("+((Project) row).getAuthor_id()+", \'"+((Project) row).getProject_name()+"\', \'"+view+ "\')";
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
			String view = ((Project) row).getProject_details();
			view = view.replace("\u0027","\\'");
			String query;
			String options = "";
			String values = "SET author_id = "+((Project) row).getAuthor_id()+", project_name =\'"+((Project) row).getProject_name()+
					"\', project_details = \'"+view+"\'";
			String where = "WHERE project_id = "+row_id;
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
			String where = "WHERE project_id = "+row_id;
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
