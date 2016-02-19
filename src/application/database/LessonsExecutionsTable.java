package application.database;

import java.time.format.DateTimeFormatter;
import java.util.Formatter;

import application.model.Execution;

public class LessonsExecutionsTable extends Table {

	// Lessons or projects lessons executions table
	private static LessonsExecutionsTable instance = new LessonsExecutionsTable();
	
	private LessonsExecutionsTable() {
		// TODO Auto-generated constructor stub
		String name = "lessons_executions";
		String create = "(lessons_execution_id INT(11) AUTO_INCREMENT COMMENT 'Lesson execution\\'s ID', lesson_id INT(11) NOT NULL COMMENT 'Appropriate lesson\\'s ID', "
				+ "executor_id INT(11) COMMENT 'Appropriate student\\'s ID', group_id INT(11) COMMENT 'Appropriate group\\'s ID', lessons_execution_start_date DATE "
				+ "COMMENT 'Lesson learning start date', lessons_execution_end_date DATE COMMENT 'Lesson learning end date', lessons_execution_status INT(1) NOT NULL DEFAULT 0 "
				+ "COMMENT 'Lesson learning status (0 - issued, 1 - performed, 2 - checked, 3 - finished)', lessons_execution_result INT(3) NOT NULL DEFAULT 0 "
				+ "COMMENT 'Lesson learning result (rating, mark)', PRIMARY KEY (lessons_execution_id), FOREIGN KEY (lesson_id) REFERENCES lessons (lesson_id) "
				+ "ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (executor_id) REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (group_id) "
				+ "REFERENCES groups (group_id) ON DELETE CASCADE ON UPDATE CASCADE, UNIQUE (lesson_id, executor_id, group_id, lessons_execution_start_date))";
		String options = "ENGINE = InnoDB COMMENT = 'Lesson executions (learning) table'";
		String select = "";
		setDatabase(InAltoSchoolDatabase.getInstance());
		setTableName(name,create,options,select);
	}
	
	public static LessonsExecutionsTable getInstance() {
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
			String fields = "(lesson_id, executor_id, group_id, lessons_execution_start_date, lessons_execution_end_date, lessons_execution_status, lessons_execution_result)";
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
			String values = "SET lesson_id = "+((Execution) row).getSection_id()+", executor_id = "+executor_id+
					", group_id = "+group_id+", lessons_execution_start_date = "+start_date+", lessons_execution_end_date = "+end_date+
					", lessons_execution_status = "+((Execution) row).getExecution_status()+", lessons_execution_result = "+((Execution) row).getExecution_result();
			String where = "WHERE lessons_execution_id = "+row_id;
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
			String where = "WHERE lessons_execution_id = "+row_id;
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
