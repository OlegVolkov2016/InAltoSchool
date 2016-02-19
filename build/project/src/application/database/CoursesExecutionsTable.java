package application.database;

import java.time.format.DateTimeFormatter;
import java.util.Formatter;

import application.model.Execution;

public class CoursesExecutionsTable extends Table {

	// Courses or projects courses executions table
	private static CoursesExecutionsTable instance = new CoursesExecutionsTable();
	
	private CoursesExecutionsTable() {
		// TODO Auto-generated constructor stub
		String name = "courses_executions";
		String create = "(courses_execution_id INT(11) AUTO_INCREMENT COMMENT 'Course execution\\'s ID', course_id INT(11) NOT NULL COMMENT 'Appropriate course\\'s ID', "
				+ "executor_id INT(11) COMMENT 'Appropriate student\\'s ID', group_id INT(11) COMMENT 'Appropriate group\\'s ID', "
				+ "courses_execution_start_date DATE COMMENT 'Course training start date', courses_execution_end_date DATE COMMENT 'Course training end date', "
				+ "courses_execution_status INT(1) NOT NULL DEFAULT 0 COMMENT 'Course training status (0 - issued, 1 - performed, 2 - checked, 3 - finished)', "
				+ "courses_execution_result INT(3) NOT NULL DEFAULT 0 COMMENT 'Course training result (rating, mark)', PRIMARY KEY (courses_execution_id), "
				+ "FOREIGN KEY (course_id) REFERENCES courses (course_id) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (executor_id) REFERENCES users (user_id) "
				+ "ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (group_id) REFERENCES groups (group_id) ON DELETE CASCADE ON UPDATE CASCADE, UNIQUE (course_id, executor_id, group_id, courses_execution_start_date))";
		String options = "ENGINE = InnoDB COMMENT = 'Course executions (training) table'";
		String select = "";
		setDatabase(InAltoSchoolDatabase.getInstance());
		setTableName(name,create,options,select);
	}
	
	public static CoursesExecutionsTable getInstance() {
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
			String executor_id, group_id;
			if (((Execution) row).getExecutor_id() == 0)
				executor_id = "NULL";
			else
				executor_id = ((Execution) row).getExecutor_id()+"";
			if (((Execution) row).getGroup_id() == 0)
				group_id = "NULL";
			else
				group_id = ((Execution) row).getGroup_id()+"";
			String query;
			String fields = "(course_id, executor_id, group_id, courses_execution_start_date, courses_execution_end_date, courses_execution_status, courses_execution_result)";
			String values = "("+((Execution) row).getSection_id()+", "+executor_id+", "+group_id+", "+start_date+", "+end_date+", "+
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
			String executor_id, group_id;
			if (((Execution) row).getExecutor_id() == 0)
				executor_id = "NULL";
			else
				executor_id = ((Execution) row).getExecutor_id()+"";
			if (((Execution) row).getGroup_id() == 0)
				group_id = "NULL";
			else
				group_id = ((Execution) row).getGroup_id()+"";
			String query;
			String options = "";
			String values = "SET course_id = "+((Execution) row).getSection_id()+", executor_id = "+executor_id+
					", group_id = "+group_id+", courses_execution_start_date = "+start_date+", courses_execution_end_date = "+end_date+
					", courses_execution_status = "+((Execution) row).getExecution_status()+", courses_execution_result = "+((Execution) row).getExecution_result();
			String where = "WHERE courses_execution_id = "+row_id;
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
			String where = "WHERE courses_execution_id = "+row_id;
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
