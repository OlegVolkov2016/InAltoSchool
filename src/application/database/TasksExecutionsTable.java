package application.database;

import java.time.format.DateTimeFormatter;
import java.util.Formatter;

import application.model.Execution;

public class TasksExecutionsTable extends Table {

	// Lessons or projects tasks executions table
	private static TasksExecutionsTable instance = new TasksExecutionsTable();
	
	private TasksExecutionsTable() {
		// TODO Auto-generated constructor stub
		String name = "tasks_executions";
		String create = "(tasks_execution_id INT(11) AUTO_INCREMENT COMMENT 'Task execution\\'s ID', task_id INT(11) NOT NULL COMMENT 'Appropriate task\\'s ID', "
				+ "executor_id INT(11) NOT NULL COMMENT 'Appropriate executor\\'s ID', tasks_execution_start_date DATE COMMENT 'Task execution\\'s start date', "
				+ "tasks_execution_end_date DATE COMMENT 'Task execution\\'s end date', tasks_execution_status INT(1) NOT NULL DEFAULT 0 "
				+ "COMMENT 'Task execution\\'s status (0 - issued, 1 - performed, 2 - checked, 3 - finished)', tasks_execution_result INT(3) NOT NULL DEFAULT 0 "
				+ "COMMENT 'Task execution\\'s result (rating, mark)', PRIMARY KEY (tasks_execution_id), FOREIGN KEY (task_id) REFERENCES tasks (task_id) "
				+ "ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (executor_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE, UNIQUE (task_id, executor_id))";
		String options = "ENGINE = InnoDB COMMENT = 'Task executions table'";
		String select = "";
		setDatabase(InAltoSchoolDatabase.getInstance());
		setTableName(name,create,options,select);
	}
	
	public static TasksExecutionsTable getInstance() {
		return instance;
	}
	
	@Override
	public boolean insert (Object row) {
		// TODO Auto-generated method stub
		if (row != null) {
			String start_date, end_date;
			if (((Execution) row).getExecution_start_date() == null)
				start_date = "NULL";
			else
				start_date = "\'"+((Execution) row).getExecution_start_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\'";
			if (((Execution) row).getExecution_end_date() == null)
				end_date = "NULL";
			else
				end_date = "\'"+((Execution) row).getExecution_end_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\'";
			String query;
			String fields = "(task_id, executor_id, tasks_execution_start_date, tasks_execution_end_date, tasks_execution_status, tasks_execution_result)";
			String values = "("+((Execution) row).getSection_id()+", "+((Execution) row).getExecutor_id()+", "+start_date+", "+end_date+", "+
					((Execution) row).getExecution_status()+", "+((Execution) row).getExecution_result()+")";
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
			String start_date, end_date;
			if (((Execution) row).getExecution_start_date() == null)
				start_date = "NULL";
			else
				start_date = "\'"+((Execution) row).getExecution_start_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\'";
			if (((Execution) row).getExecution_end_date() == null)
				end_date = "NULL";
			else
				end_date = "\'"+((Execution) row).getExecution_end_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\'";
			String query;
			String options = "";
			String values = "SET task_id = "+((Execution) row).getSection_id()+", executor_id = "+((Execution) row).getExecutor_id()+
					", tasks_execution_start_date = "+start_date+", tasks_execution_end_date = "+end_date+
					", tasks_execution_status = "+((Execution) row).getExecution_status()+", tasks_execution_result = "+((Execution) row).getExecution_result();
			String where = "WHERE tasks_execution_id = "+row_id;
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
			String where = "WHERE tasks_execution_id = "+row_id;
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
