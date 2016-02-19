package application.database;

import java.util.Formatter;

import application.model.Lesson;

public class LessonsTable extends Table {

	// Courses lessons table
	private static LessonsTable instance = new LessonsTable();
	
	private LessonsTable() {
		// TODO Auto-generated constructor stub
		String name = "lessons";
		String create = "(lesson_id INT(11) AUTO_INCREMENT COMMENT 'Lesson\\'s ID', course_id INT(11) NOT NULL COMMENT 'Appropriate course\\'s ID', "
				+ "lesson_name VARCHAR(50) NOT NULL COMMENT 'Lesson\\'s name', lesson_details TEXT COMMENT 'Lesson\\'s information (content)', "
				+ "PRIMARY KEY (lesson_id), FOREIGN KEY (course_id) REFERENCES courses (course_id) ON DELETE CASCADE ON UPDATE CASCADE, UNIQUE (course_id, lesson_name))";
		String options = "ENGINE = InnoDB COMMENT = 'Course lessons table'";
		String select = "";
		setDatabase(InAltoSchoolDatabase.getInstance());
		setTableName(name,create,options,select);
	}
	
	public static LessonsTable getInstance() {
		return instance;
	}
	
	@Override
	public boolean insert (Object row) {
		// TODO Auto-generated method stub
		if (row != null) {
			String view = ((Lesson) row).getLesson_details();
			view = view.replace("\u0027","\\'");
			String query;
			String fields = "(course_id, lesson_name, lesson_details)";
			String values = "("+((Lesson) row).getCourse_id()+", \'"+((Lesson) row).getLesson_name()+"\', \'"+view+"\')";
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
			String view = ((Lesson) row).getLesson_details();
			view = view.replace("\u0027","\\'");
			String query;
			String options = "";
			String values = "SET course_id = "+((Lesson) row).getCourse_id()+", lesson_name = \'"+((Lesson) row).getLesson_name()+
					"\', lesson_details = \'"+view+"\'";
			String where = "WHERE lesson_id = "+row_id;
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
			String where = "WHERE lesson_id = "+row_id;
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
