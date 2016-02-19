package application.database;

import java.util.Formatter;

import application.model.Task;

public class TasksTable extends Table {

	// Lessons or projects tasks table
	private static TasksTable instance = new TasksTable();
	
	private TasksTable() {
		// TODO Auto-generated constructor stub
		String name = "tasks";
		String create = "(task_id INT(11) AUTO_INCREMENT COMMENT 'Task\\'s ID', lesson_id INT(11) COMMENT 'Appropriate lesson\\'s ID', "
				+ "project_id INT(11) COMMENT 'Appropriate project\\'s ID', task_name VARCHAR(50) NOT NULL COMMENT 'Task\\'s name', "
				+ "task_details TEXT COMMENT 'Task\\'s information (statement)', PRIMARY KEY (task_id), FOREIGN KEY (lesson_id) "
				+ "REFERENCES lessons (lesson_id) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (project_id) REFERENCES projects (project_id) "
				+ "ON DELETE CASCADE ON UPDATE CASCADE, UNIQUE (lesson_id, project_id, task_name))";
		String options = "ENGINE = InnoDB COMMENT = 'Lesson or project tasks table'";
		String select = "";
		setDatabase(InAltoSchoolDatabase.getInstance());
		setTableName(name,create,options,select);
	}
	
	public static TasksTable getInstance() {
		return instance;
	}
	
	@Override
	public boolean insert (Object row) {
		// TODO Auto-generated method stub
		if (row != null) {
			String lesson_id, project_id;
			if (((Task) row).getLesson_id() == 0)
				lesson_id = "NULL";
			else
				lesson_id = ((Task) row).getLesson_id()+"";
			if (((Task) row).getProject_id() == 0)
				project_id = "NULL";
			else
				project_id = ((Task) row).getProject_id()+"";
			String view = ((Task) row).getTask_details();
			view = view.replace("\u0027","\\'");
			String query;
			String fields = "(lesson_id, project_id, task_name, task_details)";
			String values = "("+lesson_id+", "+project_id+", \'"+((Task) row).getTask_name()+"\', \'"+view+"\')";
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
			String lesson_id, project_id;
			if (((Task) row).getLesson_id() == 0)
				lesson_id = "NULL";
			else
				lesson_id = ((Task) row).getLesson_id()+"";
			if (((Task) row).getProject_id() == 0)
				project_id = "NULL";
			else
				project_id = ((Task) row).getProject_id()+"";
			String view = ((Task) row).getTask_details();
			view = view.replace("\u0027","\\'");
			String query;
			String options = "";
			String values = "SET lesson_id = "+lesson_id+", project_id = "+project_id+", task_name = \'"+((Task) row).getTask_name()
					+"\', task_details = \'"+view+"\'";
			String where = "WHERE task_id = "+row_id;
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
			String where = "WHERE task_id = "+row_id;
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
