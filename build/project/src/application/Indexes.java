package application;

public enum Indexes {
	
	// Language indexes
	LanguageUK_index(0),
	LanguageEN_index(1),
	AppLanguage_index(Indexes.LanguageUK_index.getValue()),
	
	// Table indexes
	RolesTable_id(0),
	LoginsTable_id(1),
	UsersTable_id(2),
	ProjectsTable_id(3),
	ProjectsExecutionsTable_id(4),
	CoursesTable_id(5),
	GroupsTable_id(6),
	StudentsTable_id(7),
	CoursesExecutionsTable_id(8),
	LessonsTable_id(9),
	LessonsExecutionsTable_id(10),
	TasksTable_id(11),
	TasksExecutionsTable_id(12),
	UsersPaymentsTable_id(13),
	
	// SplitPanes indexes
	MainSplitPaneListSize(9),
	MainSplitPane_id(0),
	CoursesSplitPane_id(1),
	LessonsSplitPane_id(2),
	LessonsTasksSplitPane_id(3),
	ProjectsSplitPane_id(4),
	ProjectsTasksSplitPane_id(5),
	TeachersSplitPane_id(6),
	StudentsSplitPane_id(7),
	UsersTableSplitPane_id(8),
	;
	
	private int value;
	
	private Indexes(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}

}
