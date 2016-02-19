package application.view;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import application.InAltoSchool;
import application.Indexes;
import application.Strings;
import application.database.CoursesExecutionsTable;
import application.database.CoursesTable;
import application.database.InAltoSchoolDatabase;
import application.database.ProjectsExecutionsTable;
import application.database.ProjectsTable;
import application.database.UsersTable;
import application.model.Course;
import application.model.Login;
import application.model.Project;
import application.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TeachersSplitPaneController {

	// Main application reference 
	private InAltoSchool mainApp;
	
	// Observable lists
	ObservableList<User> listUsers;
	ObservableList<Course> listCourses;
	ObservableList<Project> listProjects;
	
	// Current IDs
	private int author_id;
	
	// Children elements references	
	@FXML
	private Button detailsButton, coursesToButton, projectsToButton;
	
	@FXML
	private Label labelUserTitle, labelCourseTitle, labelProjectTitle, labelInformation, labelBirthday;
	
	@FXML
	private TableView<User> tableUsers;
	
	@FXML
	private TableView<Course> tableCourses;
	
	@FXML
	private TableView<Project> tableProjects;
	
	@FXML
	private TableColumn<User, String> columnFirstName, columnLastName;
	
	@FXML
	private TableColumn<Course, String> columnCourseName;
	
	@FXML
	private TableColumn<Project, String> columnProjectName;
	
	@FXML
	private TextField textBirthday;
	
	@FXML
	private void initialize() {
		// Set Lists
		author_id = 0;
		listUsers = FXCollections.observableArrayList();
		listCourses = FXCollections.observableArrayList();
		listProjects = FXCollections.observableArrayList();
		tableUsers.setItems(listUsers);
		tableCourses.setItems(listCourses);
		tableProjects.setItems(listProjects);
		columnFirstName.setCellValueFactory(cell -> cell.getValue().user_firstnameProperty());
		columnLastName.setCellValueFactory(cell -> cell.getValue().user_lastnameProperty());
		columnCourseName.setCellValueFactory(cell -> cell.getValue().course_nameProperty());
		columnProjectName.setCellValueFactory(cell -> cell.getValue().project_nameProperty());
		// Set Handlers
		setHandlers();	
	}
	
	// Button Events
	@FXML
	private void detailsButton() {
		User user = tableUsers.getSelectionModel().getSelectedItem();
		Stage dialogStage = new Stage();
		TeacherDialog teacherDialog = new TeacherDialog(dialogStage);
		teacherDialog.getController().setUser(user);
		dialogStage.setTitle(Strings.TeacherDialogTitle.getValue(Indexes.AppLanguage_index.getValue()));
		Scene scene = new Scene(teacherDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
	}
	
	@FXML
	private void coursesToButton() {
		Course course = tableCourses.getSelectionModel().getSelectedItem();
		if ((mainApp.getLogin() != null) && (isAccessCourse(mainApp.getLogin(),course))) {
			if (mainApp.getRootLayout().getMainSplitPanesList()[Indexes.LessonsSplitPane_id.getValue()] == null)
				mainApp.getRootLayout().getMainSplitPanesList()[Indexes.LessonsSplitPane_id.getValue()] = new LessonsSplitPane(this.mainApp);
			mainApp.getRootLayout().setMainSplitPane(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.LessonsSplitPane_id.getValue()]);
			mainApp.getRootLayout().setCenter(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.LessonsSplitPane_id.getValue()]);
			((LessonsSplitPane) mainApp.getRootLayout().getMainSplitPane()).getController().setCourse(course);
		}
		else {
			if (mainApp.getRootLayout().getMainSplitPanesList()[Indexes.CoursesSplitPane_id.getValue()] == null)
				mainApp.getRootLayout().getMainSplitPanesList()[Indexes.CoursesSplitPane_id.getValue()] = new CoursesSplitPane(this.mainApp);
			mainApp.getRootLayout().setMainSplitPane(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.CoursesSplitPane_id.getValue()]);
			mainApp.getRootLayout().setCenter(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.CoursesSplitPane_id.getValue()]);
		}
	}
	
	@FXML
	private void projectsToButton() {
		Project project = tableProjects.getSelectionModel().getSelectedItem();
		if ((mainApp.getLogin() != null) && (isAccessProject(mainApp.getLogin(),project))) {
			if (mainApp.getRootLayout().getMainSplitPanesList()[Indexes.ProjectsTasksSplitPane_id.getValue()] == null)
				mainApp.getRootLayout().getMainSplitPanesList()[Indexes.ProjectsTasksSplitPane_id.getValue()] = new ProjectsTasksSplitPane(this.mainApp);
			mainApp.getRootLayout().setMainSplitPane(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.ProjectsTasksSplitPane_id.getValue()]);
			mainApp.getRootLayout().setCenter(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.ProjectsTasksSplitPane_id.getValue()]);
			((ProjectsTasksSplitPane) mainApp.getRootLayout().getMainSplitPane()).getController().setProject(project);
		}
		else {
			if (mainApp.getRootLayout().getMainSplitPanesList()[Indexes.ProjectsSplitPane_id.getValue()] == null)
				mainApp.getRootLayout().getMainSplitPanesList()[Indexes.ProjectsSplitPane_id.getValue()] = new ProjectsSplitPane(this.mainApp);
			mainApp.getRootLayout().setMainSplitPane(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.ProjectsSplitPane_id.getValue()]);
			mainApp.getRootLayout().setCenter(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.ProjectsSplitPane_id.getValue()]);
		}
	}
	
	// ChangeLanguage for elements
	public void changeLanguage () {
		labelUserTitle.setText(Strings.LabelTeacherTitle.getValue(Indexes.AppLanguage_index.getValue()));
		labelCourseTitle.setText(Strings.LabelCourseTitle.getValue(Indexes.AppLanguage_index.getValue()));
		labelProjectTitle.setText(Strings.LabelProjectTitle.getValue(Indexes.AppLanguage_index.getValue()));
		labelInformation.setText(Strings.LabelInformation.getValue(Indexes.AppLanguage_index.getValue()));
		labelBirthday.setText(Strings.RegistrationLabelBirthday.getValue(Indexes.AppLanguage_index.getValue()));
		columnFirstName.setText(Strings.ColumnFirstName.getValue(Indexes.AppLanguage_index.getValue()));
		columnLastName.setText(Strings.ColumnLastName.getValue(Indexes.AppLanguage_index.getValue()));
		columnCourseName.setText(Strings.ColumnCourseName.getValue(Indexes.AppLanguage_index.getValue()));
		columnProjectName.setText(Strings.ColumnProjectName.getValue(Indexes.AppLanguage_index.getValue()));
		detailsButton.setText(Strings.DetailsButton.getValue(Indexes.AppLanguage_index.getValue()));
		coursesToButton.setText(Strings.CoursesToButton.getValue(Indexes.AppLanguage_index.getValue()));
		projectsToButton.setText(Strings.ProjectsToButton.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// ChangeLogin for elements
	public void changeLogin () {
		tableUsers.getSelectionModel().clearSelection();
		if (getMainApp().getLogin() ==  null) {
			detailsButton.setDisable(true);
			coursesToButton.setDisable(false);
			projectsToButton.setDisable(false);
		}
		else {
			detailsButton.setDisable(true);
			coursesToButton.setDisable(false);
			projectsToButton.setDisable(false);
		}
	}
	
	// Set Handlers
	private void setHandlers() {
		// Users list
		tableUsers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				// TODO Auto-generated method stub
				if (newValue != null) {
					author_id = newValue.getUser_id();
					updateInformation(newValue);
					if ((mainApp.getLogin() != null) && ((mainApp.getLogin().getRole_name().equals("Administrator")) || 
						(mainApp.getLogin().getUser_id() == newValue.getUser_id()))) {
						detailsButton.setDisable(false);
					}
					else {
						detailsButton.setDisable(true);
					}		
				}
				else {
					updateInformation(null);
				}
			}
		});	
	}
	
	// Check access for course
	private boolean isAccessCourse(Login login, Course course) {
		if (login.getRole_name().equals("Administrator"))
			return true;
		if (login.getUser_id() == course.getAuthor_id())
			return true;
		String coursesExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesExecutionsTable_id.getValue());
		ResultSet result = CoursesExecutionsTable.getInstance().executeSelect("", "courses_execution_id ", "FROM "+coursesExecutionsTable+" ", 
			"WHERE (course_id = "+course.getCourse_id()+") AND (executor_id = "+login.getUser_id()+")", "", "", "");
		try {
			if (result.next())
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Alert splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InitError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.SelectError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();
		}
		return false;
	}
	
	// Check access for project
	private boolean isAccessProject(Login login, Project project) {
		if (login.getRole_name().equals("Administrator"))
			return true;
		if (login.getUser_id() == project.getAuthor_id())
			return true;
		String projectsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.ProjectsExecutionsTable_id.getValue());
		ResultSet result = ProjectsExecutionsTable.getInstance().executeSelect("", "projects_execution_id ", "FROM "+projectsExecutionsTable+" ", 
				"WHERE (project_id = "+project.getProject_id()+") AND (executor_id = "+login.getUser_id()+")", "", "", "");
		try {
			if (result.next())
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Alert splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InitError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.SelectError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();
		}
		return false;
	}

	// List getters
	public void getListUsers() {
		listUsers.clear();
		LocalDate date;
		String usersTable = InAltoSchoolDatabase .getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		String loginsTable = InAltoSchoolDatabase .getInstance().getTables_names().get(Indexes.LoginsTable_id.getValue());
		String rolesTable = InAltoSchoolDatabase .getInstance().getTables_names().get(Indexes.RolesTable_id.getValue());
		ResultSet result = UsersTable.getInstance().executeSelect("", usersTable+".user_id, "+usersTable+".login_id, user_firstname, user_lastname, "
				+ "user_birthday, user_address, user_phone, user_email, user_skype, user_details, login_name, login_password, login_question, "
				+ "login_answer, role_name ", "FROM "+usersTable+" JOIN "+loginsTable
				+" ON "+usersTable+".login_id = "+loginsTable+".login_id JOIN "+rolesTable +" ON "+loginsTable+".role_id = "+rolesTable+".role_id ", 
				"WHERE ("+rolesTable+".role_name = \'Administrator\') OR ("+rolesTable+".role_name = \'Teacher\')", "", "", "");
		try {
			while (result.next()) {
			if (result.getString(5) == null)
				date = null;
			else
				date = LocalDate.parse(result.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				listUsers.add(new User(result.getInt(1),result.getInt(2),result.getString(3),result.getString(4),date,result.getString(6),result.getString(7),
					result.getString(8),result.getString(9),result.getString(10),result.getString(11),result.getInt(12),result.getString(13),
					result.getInt(14),result.getString(15)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Alert splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InitError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.SelectError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();
		}
		listCourses.clear();
		listProjects.clear();
	}
	
	private void getListCourses(ObservableList<Course> listCourses) {
		String coursesTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		if (author_id != 0) {
			ResultSet result = CoursesTable.getInstance().executeSelect("", coursesTable+".course_id, "+coursesTable+".author_id, course_name, course_details, "
					+"course_group_price, course_individual_price, CONCAT(user_firstname, ' ', user_lastname) ", "FROM "+coursesTable+" JOIN "+usersTable
					+" ON ("+coursesTable+".author_id = "+usersTable+".user_id) ", "WHERE "+coursesTable+".author_id = "+author_id, "", "", "");			try {
				while(result.next()) {
					listCourses.add(new Course(result.getInt(1),result.getInt(2),result.getString(3),result.getString(4),result.getDouble(5),result.getDouble(6),result.getString(7)));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				Alert splitAlert = new Alert(AlertType.ERROR);
				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setHeaderText(Strings.InitError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setContentText(Strings.SelectError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.showAndWait();
			}
		}
	}
	
	private void getListProjects(ObservableList<Project> listProjects) {
		String projectsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.ProjectsTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		if (author_id != 0) {
			ResultSet result = ProjectsTable.getInstance().executeSelect("", projectsTable+".project_id, "+projectsTable+".author_id, project_name, project_details, "
					+"CONCAT(user_firstname, ' ', user_lastname) ", "FROM "+projectsTable+" JOIN "+usersTable+" ON ("+projectsTable+".author_id = "+usersTable+".user_id) ", 
					"WHERE "+projectsTable+".author_id = "+author_id, "", "", "");
			try {
				while(result.next()) {
					listProjects.add(new Project(result.getInt(1),result.getInt(2),result.getString(3),result.getString(4),result.getString(5)));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				Alert splitAlert = new Alert(AlertType.ERROR);
				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setHeaderText(Strings.InitError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setContentText(Strings.SelectError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.showAndWait();
			}
		}
	}
	
	// Update Information
	private void updateInformation(User user) {
		if (user != null) {
			String date;
			if (user.getUser_birthday() == null) {
				date = "";
			}
			else {
				date = user.getUser_birthday().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			}
			textBirthday.setText(date);
			listCourses.clear();
			listProjects.clear();
			getListCourses(listCourses);
			getListProjects(listProjects);
		}
		else {
			textBirthday.setText("");
			listCourses.clear();
			listProjects.clear();
		}
	}
	
	// Getters and setters
	public InAltoSchool getMainApp() {
		return mainApp;
	}
	
	public void setMainApp(InAltoSchool mainApp) {
		this.mainApp = mainApp;
	}

}
