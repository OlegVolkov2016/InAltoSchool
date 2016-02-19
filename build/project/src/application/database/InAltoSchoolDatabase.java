package application.database;

import java.util.ArrayList;


public class InAltoSchoolDatabase extends Database {
	
	// Main database
	
	private static InAltoSchoolDatabase instance = new InAltoSchoolDatabase();
	private ArrayList<TableConnector> tables;
	private ArrayList<String> tables_names;
	
	private InAltoSchoolDatabase() {
		// TODO Auto-generated constructor stub
		String name = "inaltoschool";
		String charset = "CHARACTER SET utf8";
		String collate = "COLLATE utf8_bin";
		setDatabaseName(name,charset,collate);
		tables = new ArrayList<TableConnector>();
		tables_names = new ArrayList<String>();
	}
	
	public static InAltoSchoolDatabase getInstance() {
		return instance;
	}
	
	public ArrayList<TableConnector> getTables() {
		return tables;
	}
	
	public void setTables() {
		getTables().add(RolesTable.getInstance());
		getTables().add(LoginsTable.getInstance());
		getTables().add(UsersTable.getInstance());
		getTables().add(ProjectsTable.getInstance());
		getTables().add(ProjectsExecutionsTable.getInstance());
		getTables().add(CoursesTable.getInstance());
		getTables().add(GroupsTable.getInstance());
		getTables().add(StudentsTable.getInstance());
		getTables().add(CoursesExecutionsTable.getInstance());
		getTables().add(LessonsTable.getInstance());
		getTables().add(LessonsExecutionsTable.getInstance());
		getTables().add(TasksTable.getInstance());
		getTables().add(TasksExecutionsTable.getInstance());
		getTables().add(UsersPaymentsTable.getInstance());
		setTables_names();
	}
	
	
	public ArrayList<String> getTables_names() {
		return tables_names;
	}
	
	public void setTables_names() {
		getTables_names().add(RolesTable.getInstance().getTableName());
		getTables_names().add(LoginsTable.getInstance().getTableName());
		getTables_names().add(UsersTable.getInstance().getTableName());
		getTables_names().add(ProjectsTable.getInstance().getTableName());
		getTables_names().add(ProjectsExecutionsTable.getInstance().getTableName());
		getTables_names().add(CoursesTable.getInstance().getTableName());
		getTables_names().add(GroupsTable.getInstance().getTableName());
		getTables_names().add(StudentsTable.getInstance().getTableName());	
		getTables_names().add(CoursesExecutionsTable.getInstance().getTableName());	
		getTables_names().add(LessonsTable.getInstance().getTableName());
		getTables_names().add(LessonsExecutionsTable.getInstance().getTableName());
		getTables_names().add(TasksTable.getInstance().getTableName());
		getTables_names().add(TasksExecutionsTable.getInstance().getTableName());
		getTables_names().add(UsersPaymentsTable.getInstance().getTableName());
	}

}
