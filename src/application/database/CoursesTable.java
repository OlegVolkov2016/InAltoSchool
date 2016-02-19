package application.database;

import java.util.Formatter;

import application.model.Course;

public class CoursesTable extends Table {

	// School courses
	private static CoursesTable instance = new CoursesTable();
	
	private CoursesTable() {
		// TODO Auto-generated constructor stub
		String name = "courses";
		String create = "(course_id INT(11) AUTO_INCREMENT COMMENT 'Course\\'s ID', author_id INT(11) NOT NULL COMMENT 'Appropriate author\\'s ID', "
				+ "course_name VARCHAR(50) NOT NULL COMMENT 'Course\\'s name', course_details TEXT COMMENT 'Course\\'s information (program)', "
				+ "course_group_price DECIMAL(7,2) NOT NULL COMMENT 'Course\\'s group price', course_individual_price DECIMAL(7,2) NOT NULL "
				+ "COMMENT 'Course\\'s individual price', PRIMARY KEY (course_id), FOREIGN KEY (author_id) "
				+ "REFERENCES users (user_id) ON DELETE CASCADE ON UPDATE CASCADE, UNIQUE (author_id, course_name))";
		String options = "ENGINE = InnoDB COMMENT = 'School courses table'";
		String select = "";
		setDatabase(InAltoSchoolDatabase.getInstance());
		setTableName(name,create,options,select);
	}
	
	public static CoursesTable getInstance() {
		return instance;
	}
	
	@Override
	public boolean insert (Object row) {
		// TODO Auto-generated method stub
		if (row != null) {
			String view = ((Course) row).getCourse_details();
			view = view.replace("\u0027","\\'");
			String query;
			String fields = "(author_id, course_name, course_details, course_group_price, course_individual_price)";
			String values = "("+((Course) row).getAuthor_id()+", \'"+((Course) row).getCourse_name()+"\', \'"+view+ "\', "+
					((Course) row).getCourse_group_price()+", "+((Course) row).getCourse_individual_price()+")";
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
			String view = ((Course) row).getCourse_details();
			view = view.replace("\u0027","\\'");
			String query;
			String options = "";
			String values = "SET author_id = "+((Course) row).getAuthor_id()+", course_name = \'"+((Course) row).getCourse_name()+
					"\', course_details = \'"+view+"\', course_group_price = "+((Course) row).getCourse_group_price()+
					", course_individual_price = "+((Course) row).getCourse_individual_price();
			String where = "WHERE course_id = "+row_id;
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
			String where = "WHERE course_id = "+row_id;
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
