package application.view;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import application.InAltoSchool;
import application.Indexes;
import application.Strings;
import application.database.InAltoSchoolDatabase;
import application.database.TasksExecutionsTable;
import application.database.LessonsExecutionsTable;
import application.database.LessonsTable;
import application.database.CoursesExecutionsTable;
import application.database.GroupsTable;
import application.database.UsersTable;
import application.model.Course;
import application.model.Execution;
import application.model.Login;
import application.model.Lesson;
import application.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class LessonsSplitPaneController {

	// Main application reference 
	private InAltoSchool mainApp;
	
	// Observable lists
	ObservableList<Lesson> listLessons;
	
	// Course data
	private Course course;
	
	// Current IDs
	private int lesson_id;
	
	// Current execution start date for correct update
	private String date;
	
	// Children elements references	
	@FXML
	private Button viewButton, addGroupButton, addButton, removeButton, statusButton, newButton, editButton, deleteButton, coursesButton, tasksButton;
	
	@FXML
	private Label labelLessonTitle, labelAuthor, labelCourseName, labelInformation, labelDetails, labelStudents;
	
	@FXML
	private TextField textAuthorName;
	
	@FXML
	private TextArea textCourseName;
	
	@FXML
	private TableView<Lesson> tableLessons;
	
	@FXML
	private TableColumn<Lesson, String> columnLessonName;
	
	@FXML
	private TreeTableView<User> tableStudents;
	
	@FXML
	private TreeTableColumn<User, String> columnStudentName;
	
	@FXML
	private TreeTableColumn<User, LocalDate> columnStudentDate;
	
	@FXML
	private WebView textDetails;
	
	@FXML
	private void initialize() {
		// Set Lists
		lesson_id = 0;
		listLessons = FXCollections.observableArrayList();
		tableLessons.setItems(listLessons);
		columnLessonName.setCellValueFactory(cell -> cell.getValue().lesson_nameProperty());
		tableStudents.setRoot(new TreeItem<User>(new User(0,0,"","Root",null,"","","","","","",0,"",0,"")));
		tableStudents.getRoot().setExpanded(true);
		columnStudentName.setCellValueFactory(cell -> cell.getValue().getValue().user_firstnameProperty());
		columnStudentDate.setCellValueFactory(cell -> cell.getValue().getValue().user_birthdayProperty());
		// Set Handlers
		setHandlers();	
	}
	
	// Button Events
	@FXML
	private void viewButton() {
		Lesson lesson = tableLessons.getSelectionModel().getSelectedItem();
		Stage dialogStage = new Stage();
		WebViewDialog viewDialog = new WebViewDialog(dialogStage);
		viewDialog.getController().loadContent(lesson.getLesson_details());
		dialogStage.setTitle(Strings.LabelLessonDetails.getValue(Indexes.AppLanguage_index.getValue()));
		Scene scene = new Scene(viewDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
	}
	
	@FXML
	private void addGroupButton() {
		Lesson lesson = tableLessons.getSelectionModel().getSelectedItem();
		Stage dialogStage = new Stage();
		GroupsLessonDialog groupsDialog = new GroupsLessonDialog(dialogStage);
		groupsDialog.getController().setCourse(course);
		groupsDialog.getController().setLesson(lesson);
        Scene scene = new Scene(groupsDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (groupsDialog.getController().isAddButton()) {
        	updateGroup(groupsDialog.getController().getUser());
        }
	}
	
	@FXML
	private void addButton() {
		Lesson lesson = tableLessons.getSelectionModel().getSelectedItem();
		Stage dialogStage = new Stage();
		StudentsLessonDialog studentsDialog = new StudentsLessonDialog(dialogStage);
		studentsDialog.getController().setCourse(course);
		studentsDialog.getController().setLesson(lesson);
        Scene scene = new Scene(studentsDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (studentsDialog.getController().isAddButton()) {
        	updateUser(studentsDialog.getController().getUser());
        }
	}
	
	@FXML
	private void removeButton() {
		Alert splitAlert;
		TreeItem<User> user = tableStudents.getSelectionModel().getSelectedItem();
		splitAlert = new Alert(AlertType.CONFIRMATION);
		((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
		splitAlert.setTitle(Strings.ConfirmTitle.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setHeaderText(Strings.ConfirmDeleteHeader.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setContentText(Strings.ConfirmDeleteContent.getValue(Indexes.AppLanguage_index.getValue())+
				user.getValue().getUser_firstname()+"?");
		Optional<ButtonType> result = splitAlert.showAndWait();
		if (result.get() == ButtonType.OK) {
			if (user.getValue().getUser_lastname().equals("Group")) {
				removeGroup(user);
			}
			else {
				removeUser(user);
			}
		}
	}
	
	@FXML
	private void statusButton() {
		ResultSet result;
		Lesson lesson = tableLessons.getSelectionModel().getSelectedItem();
		User user = tableStudents.getSelectionModel().getSelectedItem().getValue();
		String lessonsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsExecutionsTable_id.getValue());
		String start_date;
		if (user.getUser_birthday() == null)
			start_date = "NULL";
		else
			start_date = "\'"+user.getUser_birthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\'";
		if (user.getUser_lastname().equals("Group")) {
			result = LessonsExecutionsTable.getInstance().executeSelect("", "lessons_execution_id, lesson_id, executor_id, group_id, lessons_execution_start_date, "+
					"lessons_execution_end_date, lessons_execution_status, lessons_execution_result ", "FROM "+lessonsExecutionsTable, 
					"WHERE (lesson_id = "+lesson_id+") AND (group_id = "+user.getUser_id()+") AND (executor_id IS NULL) AND (lessons_execution_start_date = "+start_date+") ", "", "", "");
		}
		else if (user.getUser_lastname().equals("Student")) {
			result = LessonsExecutionsTable.getInstance().executeSelect("", "lessons_execution_id, lesson_id, executor_id, group_id, lessons_execution_start_date, "+
					"lessons_execution_end_date, lessons_execution_status, lessons_execution_result ", "FROM "+lessonsExecutionsTable, 
					"WHERE (lesson_id = "+lesson_id+") AND (group_id = "+tableStudents.getSelectionModel().getSelectedItem().getParent().getValue().getUser_id()+
					") AND (executor_id = "+user.getUser_id()+") AND (lessons_execution_start_date = "+start_date+")", "", "", "");
		}
		else {
			result = LessonsExecutionsTable.getInstance().executeSelect("", "lessons_execution_id, lesson_id, executor_id, group_id, lessons_execution_start_date, "+
				"lessons_execution_end_date, lessons_execution_status, lessons_execution_result ", "FROM "+lessonsExecutionsTable, 
				"WHERE (lesson_id = "+lesson_id+") AND (group_id IS NULL) AND (executor_id = "+user.getUser_id()+") AND (lessons_execution_start_date = "+start_date+")", "", "", "");
		}
		try {
			if (result.next()) {
				LocalDate startDate, endDate;
				if (result.getString(5) == null) {
					startDate = null;
					date = "NULL";
				}
				else {
					startDate = LocalDate.parse(result.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					date = "\'"+result.getString(5)+"\'";
				}
				if (result.getString(6) == null)
					endDate = null;
				else
					endDate = LocalDate.parse(result.getString(6), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				Execution execution = new Execution(result.getInt(1),result.getInt(2),result.getInt(3),result.getInt(4),startDate,endDate,
						result.getInt(7),result.getInt(8),lesson.getLesson_name(),user.getUser_firstname());
				Stage dialogStage = new Stage();
				LessonStatusDialog statusDialog = new LessonStatusDialog(dialogStage);
				statusDialog.getController().setLesson(lesson);
				statusDialog.getController().setExecution(execution);
				if ((mainApp.getLogin().getRole_name().equals("Administrator")) || 
					(mainApp.getLogin().getUser_id() == course.getAuthor_id()))
					if (user.getUser_lastname().equals("Student"))
						statusDialog.getController().setStudent();
					else
						statusDialog.getController().setAuthor();
		        Scene scene = new Scene(statusDialog);
		        dialogStage.setScene(scene);
		        dialogStage.initOwner(mainApp.getPrimaryStage());
		        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
		        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
		        dialogStage.showAndWait();
		        if (statusDialog.getController().isOKButton()) {
		        	updateStatus(statusDialog.getController().getExecution());
		        }
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
	
	@FXML
	private void newButton() {
		Lesson lesson = new Lesson(0,course.getCourse_id(),"","",course.getCourse_name(),course.getAuthor_name());
		Stage dialogStage = new Stage();
		LessonDialog lessonDialog = new LessonDialog(dialogStage);
		lessonDialog.getController().setLesson(lesson);
        Scene scene = new Scene(lessonDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (lessonDialog.getController().isOKButton()) {
        	updateLesson(lessonDialog.getController().getLesson());
        }
	}
	
	@FXML
	private void editButton() {
		Lesson lesson = tableLessons.getSelectionModel().getSelectedItem();
		Stage dialogStage = new Stage();
		LessonDialog lessonDialog = new LessonDialog(dialogStage);
		lessonDialog.getController().setLesson(lesson);
		Scene scene = new Scene(lessonDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (lessonDialog.getController().isOKButton()) {
        	updateLesson(lessonDialog.getController().getLesson());
        }
	}
	
	@FXML
	private void deleteButton() {
		Lesson lesson = tableLessons.getSelectionModel().getSelectedItem();
		Alert splitAlert = new Alert(AlertType.CONFIRMATION);
		((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
		splitAlert.setTitle(Strings.ConfirmTitle.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setHeaderText(Strings.ConfirmDeleteHeader.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setContentText(Strings.ConfirmDeleteContent.getValue(Indexes.AppLanguage_index.getValue())+
				lesson.getLesson_name()+"?");
		Optional<ButtonType> result = splitAlert.showAndWait();
		if (result.get() == ButtonType.OK) {
			if (LessonsTable.getInstance().delete(lesson.getLesson_id())) {
				listLessons.remove(lesson);
			}
			else {
				splitAlert = new Alert(AlertType.ERROR);
				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setHeaderText(Strings.DeleteErrorHeader.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setContentText(Strings.DeleteErrorContent.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.showAndWait();
			}	
		}
	}
	
	@FXML
	private void coursesButton() {
		if (mainApp.getRootLayout().getMainSplitPanesList()[Indexes.CoursesSplitPane_id.getValue()] == null)
			mainApp.getRootLayout().getMainSplitPanesList()[Indexes.CoursesSplitPane_id.getValue()] = new CoursesSplitPane(this.mainApp);
		mainApp.getRootLayout().setMainSplitPane(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.CoursesSplitPane_id.getValue()]);
		mainApp.getRootLayout().setCenter(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.CoursesSplitPane_id.getValue()]);
	}
	
	@FXML
	private void tasksButton() {
		Lesson lesson = tableLessons.getSelectionModel().getSelectedItem();
		if (mainApp.getRootLayout().getMainSplitPanesList()[Indexes.LessonsTasksSplitPane_id.getValue()] == null)
			mainApp.getRootLayout().getMainSplitPanesList()[Indexes.LessonsTasksSplitPane_id.getValue()] = new LessonsTasksSplitPane(this.mainApp);
		mainApp.getRootLayout().setMainSplitPane(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.LessonsTasksSplitPane_id.getValue()]);
		mainApp.getRootLayout().setCenter(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.LessonsTasksSplitPane_id.getValue()]);
		((LessonsTasksSplitPane) mainApp.getRootLayout().getMainSplitPane()).getController().setCourse(course);
		((LessonsTasksSplitPane) mainApp.getRootLayout().getMainSplitPane()).getController().setLesson(lesson);
	}
	
	
	// ChangeLanguage for elements
	public void changeLanguage () {
		labelLessonTitle.setText(Strings.LabelLessonTitle.getValue(Indexes.AppLanguage_index.getValue()));
		labelAuthor.setText(Strings.LabelAuthor.getValue(Indexes.AppLanguage_index.getValue()));
		labelInformation.setText(Strings.LabelInformation.getValue(Indexes.AppLanguage_index.getValue()));
		labelDetails.setText(Strings.LabelLessonDetails.getValue(Indexes.AppLanguage_index.getValue()));
		labelStudents.setText(Strings.LabelCourseStudents.getValue(Indexes.AppLanguage_index.getValue()));
		columnLessonName.setText(Strings.ColumnLessonName.getValue(Indexes.AppLanguage_index.getValue()));
		columnStudentName.setText(Strings.ColumnCourseStudentName.getValue(Indexes.AppLanguage_index.getValue()));
		columnStudentDate.setText(Strings.ColumnCourseStudentDate.getValue(Indexes.AppLanguage_index.getValue()));
		addGroupButton.setText(Strings.AddGroupButton.getValue(Indexes.AppLanguage_index.getValue()));
		addButton.setText(Strings.AddStudentButton.getValue(Indexes.AppLanguage_index.getValue()));
		removeButton.setText(Strings.RemoveButton.getValue(Indexes.AppLanguage_index.getValue()));
		statusButton.setText(Strings.StatusButton.getValue(Indexes.AppLanguage_index.getValue()));
		newButton.setText(Strings.NewButton.getValue(Indexes.AppLanguage_index.getValue()));
		editButton.setText(Strings.EditButton.getValue(Indexes.AppLanguage_index.getValue()));
		deleteButton.setText(Strings.DeleteButton.getValue(Indexes.AppLanguage_index.getValue()));
		coursesButton.setText(Strings.CoursesButton.getValue(Indexes.AppLanguage_index.getValue()));
		tasksButton.setText(Strings.TasksButton.getValue(Indexes.AppLanguage_index.getValue()));
		tableStudents.getRoot().getValue().setUser_firstname(Strings.LabelCourseRoot.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// ChangeLogin for elements
	public void changeLogin () {
		tableLessons.getSelectionModel().clearSelection();	
		if (course != null) {
			if (isAccessCourse(getMainApp().getLogin(), course)) {
				if ((getMainApp().getLogin().getRole_name().equals("Student")) ||
						((getMainApp().getLogin().getUser_id() != course.getAuthor_id()) && 
						(!getMainApp().getLogin().getRole_name().equals("Administrator")))) {
						viewButton.setDisable(true);
						addGroupButton.setDisable(true);
						addButton.setDisable(true);
						removeButton.setDisable(true);
						statusButton.setDisable(true);
						newButton.setDisable(true);
						editButton.setDisable(true);
						deleteButton.setDisable(true);
						coursesButton.setDisable(false);
						tasksButton.setDisable(true);
				}
				else {
					viewButton.setDisable(true);
					addGroupButton.setDisable(true);
					addButton.setDisable(true);
					removeButton.setDisable(true);
					statusButton.setDisable(true);
					newButton.setDisable(false);
					editButton.setDisable(true);
					deleteButton.setDisable(true);
					coursesButton.setDisable(false);
					tasksButton.setDisable(true);
				}
			}
			else {
				coursesButton();
				((CoursesSplitPane) getMainApp().getRootLayout().getMainSplitPane()).getController().getTableCourses().getSelectionModel().clearSelection();
			}
		}
	}
	
	// Set Handlers
	private void setHandlers() {
		// Lessons list
		tableLessons.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Lesson>() {
			@Override
			public void changed(ObservableValue<? extends Lesson> observable, Lesson oldValue, Lesson newValue) {
				// TODO Auto-generated method stub
				if (newValue != null) {
					lesson_id = newValue.getLesson_id();
					if ((mainApp.getLogin() != null) && (isAccessLesson(mainApp.getLogin(),newValue))) {
						updateInformation(newValue);
						viewButton.setDisable(false);
						addGroupButton.setDisable(false);
						addButton.setDisable(false);
						removeButton.setDisable(true);
						statusButton.setDisable(true);
						editButton.setDisable(false);
						deleteButton.setDisable(false);
						coursesButton.setDisable(false);
						tasksButton.setDisable(false);
						if ((!mainApp.getLogin().getRole_name().equals("Administrator")) && (mainApp.getLogin().getUser_id() != course.getAuthor_id())) {
							viewButton.setDisable(false);
							addGroupButton.setDisable(true);
							addButton.setDisable(true);
							removeButton.setDisable(true);
							statusButton.setDisable(true);
							editButton.setDisable(true);
							deleteButton.setDisable(true);
							coursesButton.setDisable(false);
							tasksButton.setDisable(false);
						}
					}
					else {
						updateInformation(null);
						Alert splitAlert = new Alert(AlertType.INFORMATION);
						((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
						splitAlert.setTitle(Strings.InformationTitle.getValue(Indexes.AppLanguage_index.getValue()));
						splitAlert.setHeaderText(Strings.AccessFailHeader.getValue(Indexes.AppLanguage_index.getValue()));
						splitAlert.setContentText(Strings.AccessFailContent.getValue(Indexes.AppLanguage_index.getValue()));
						splitAlert.showAndWait();
					}		
				}
				else {
					updateInformation(null);
				}
			}
		});
		// Students list
		tableStudents.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<User>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<User>> observable, TreeItem<User> oldValue, TreeItem<User> newValue) {
				// TODO Auto-generated method stub
				if (newValue != null) {
					if ((mainApp.getLogin().getRole_name().equals("Administrator")) || 
							(mainApp.getLogin().getUser_id() == course.getAuthor_id())) {
						statusButton.setDisable(false);
						if (newValue.getValue().getUser_lastname().equals("Student")) {
							removeButton.setDisable(true);
						}
						else {
							removeButton.setDisable(false);
						}
					}
					else if ((mainApp.getLogin().getUser_id() == newValue.getValue().getUser_id()) && (!newValue.getValue().getUser_lastname().equals("Group"))) {
						removeButton.setDisable(true);
						statusButton.setDisable(false);
					}
					else {
						removeButton.setDisable(true);
						statusButton.setDisable(true);						
					}
				}
				else {
					removeButton.setDisable(true);
					statusButton.setDisable(true);		
				}
			}
		});
		// Column  Student Date
		columnStudentDate.setCellFactory(new Callback<TreeTableColumn<User,LocalDate>, TreeTableCell<User,LocalDate>>() {
			@Override
			public TreeTableCell<User, LocalDate> call(TreeTableColumn<User, LocalDate> param) {
				// TODO Auto-generated method stub
				TreeTableCell<User, LocalDate> tableCell = new TreeTableCell<User, LocalDate>() {
					@Override
					protected void updateItem(LocalDate date, boolean empty) {
						super.updateItem(date, empty);    
						setAlignment(Pos.CENTER);
						if(date != null) {
							setText(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
						}
						else {
							setText(null);
						}
					}
				};
				return tableCell;
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

	// Check access for lesson
	private boolean isAccessLesson(Login login, Lesson lesson) {
		if (login.getRole_name().equals("Administrator"))
			return true;
		if (login.getUser_id() == course.getAuthor_id())
			return true;
		String lessonsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsExecutionsTable_id.getValue());
		ResultSet result = LessonsExecutionsTable.getInstance().executeSelect("", "lessons_execution_id ", "FROM "+lessonsExecutionsTable+" ", 
			"WHERE (lesson_id = "+lesson.getLesson_id()+") AND (executor_id = "+login.getUser_id()+")", "", "", "");
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
		private void getListLessons(ObservableList<Lesson> listLessons) {
		String lessonsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsTable_id.getValue());
		String coursesTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		ResultSet result = LessonsTable.getInstance().executeSelect("", lessonsTable+".lesson_id, "+lessonsTable+".course_id, lesson_name, lesson_details, "
			+" course_name, CONCAT(user_firstname, ' ', user_lastname) ", "FROM "+lessonsTable+" JOIN "+coursesTable+" ON ("+lessonsTable+".course_id = "
			+coursesTable+".course_id) JOIN "+usersTable+" ON ("+coursesTable+".author_id = "+usersTable+".user_id)", " WHERE "+lessonsTable+".course_id = "+course.getCourse_id(), "", "", "");
		try {
			while(result.next()) {
				listLessons.add(new Lesson(result.getInt(1),result.getInt(2),result.getString(3),result.getString(4),result.getString(5),result.getString(6)));
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
	
	private void getListStudents() {
		LocalDate date;
		String groupsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.GroupsTable_id.getValue());
		String studentsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.StudentsTable_id.getValue());
		String lessonsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsExecutionsTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		Lesson lesson = tableLessons.getSelectionModel().getSelectedItem();
		if (mainApp.getLogin() != null) {
			if ((mainApp.getLogin().getRole_name().equals("Administrator")) || (mainApp.getLogin().getUser_id() == course.getAuthor_id())) {
				ResultSet resultGroups = GroupsTable.getInstance().executeSelect("", groupsTable+".group_id, group_name, lessons_execution_start_date ", 
					"FROM "+groupsTable+" JOIN "+lessonsExecutionsTable+" ON "+groupsTable+".group_id = "+lessonsExecutionsTable+".group_id ",
					" WHERE ("+groupsTable+".course_id = "+course.getCourse_id()+") AND ("+lessonsExecutionsTable+".executor_id IS NULL) AND ("
					+lessonsExecutionsTable+".lesson_id = "+lesson_id+")", "", "", "");
					try {
					while (resultGroups.next()) {
						if (resultGroups.getString(3) == null)
							date = null;
						else
							date = LocalDate.parse(resultGroups.getString(3), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
						TreeItem<User> item = new TreeItem<User>(new User(resultGroups.getInt(1),lesson.getLesson_id(),resultGroups.getString(2),"Group",date,
								"","","","","","",0,"",0,""));
						tableStudents.getRoot().getChildren().add(item);
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
			else {
				ResultSet resultGroups = GroupsTable.getInstance().executeSelect("",  groupsTable+".group_id, group_name, lessons_execution_start_date ", 
					"FROM "+groupsTable+" JOIN "+lessonsExecutionsTable+" ON "+groupsTable+".group_id = "+lessonsExecutionsTable+".group_id JOIN "+studentsTable+" ON "+groupsTable+".group_id = "+studentsTable+".group_id ", 
					" WHERE ("+groupsTable+".course_id = "+course.getCourse_id()+") AND ("+lessonsExecutionsTable+".executor_id IS NULL) AND ("
					+lessonsExecutionsTable+".lesson_id = "+lesson_id+") AND ("+studentsTable+".user_id = "+mainApp.getLogin().getUser_id()+")", "", "", "");
				try {
					while (resultGroups.next()) {
						if (resultGroups.getString(3) == null)
							date = null;
						else
							date = LocalDate.parse(resultGroups.getString(3), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
						TreeItem<User> item = new TreeItem<User>(new User(resultGroups.getInt(1),lesson.getLesson_id(),resultGroups.getString(2),"Group",date,
								"","","","","","",0,"",0,""));
						tableStudents.getRoot().getChildren().add(item);
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
			tableStudents.getRoot().getChildren().forEach(item -> {
				String start_date;
				if (item.getValue().getUser_birthday() == null)
					start_date = "NULL";
				else
					start_date = "\'"+item.getValue().getUser_birthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\'";
				ResultSet resultStudents = UsersTable.getInstance().executeSelect("", usersTable+".user_id, CONCAT(user_firstname, ' ', user_lastname), lessons_execution_start_date ", 
					"FROM "+usersTable+" JOIN "+lessonsExecutionsTable +" ON "+usersTable+".user_id = "+lessonsExecutionsTable+".executor_id", 
					" WHERE ("+lessonsExecutionsTable+".lesson_id = "+lesson_id+") AND ("+lessonsExecutionsTable+".group_id = "+item.getValue().getUser_id()+") AND ("
					+lessonsExecutionsTable+".lessons_execution_start_date = "+start_date+")", "", "", "");
				try {
					while (resultStudents.next()) {
						LocalDate dateStudent;
						if (resultStudents.getString(3) == null)
							dateStudent = null;
						else
							dateStudent = LocalDate.parse(resultStudents.getString(3), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
						item.getChildren().add(new TreeItem<User>(new User(resultStudents.getInt(1),0,resultStudents.getString(2),"Student",dateStudent,"","","","","","",0,"",0,"")));
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
			});
			ResultSet resultUsers;
			if ((mainApp.getLogin().getRole_name().equals("Administrator")) || (mainApp.getLogin().getUser_id() == course.getAuthor_id())) {
				resultUsers = UsersTable.getInstance().executeSelect("", usersTable+".user_id, CONCAT(user_firstname, ' ', user_lastname), lessons_execution_start_date ", "FROM "+usersTable
					+" JOIN "+lessonsExecutionsTable+" ON "+usersTable+".user_id = "+lessonsExecutionsTable+".executor_id", " WHERE ("+lessonsExecutionsTable+".lesson_id = "+lesson_id
					+") AND ("+lessonsExecutionsTable+".group_id IS NULL)", "", "", "");
			}
			else {
				resultUsers = UsersTable.getInstance().executeSelect("", usersTable+".user_id, CONCAT(user_firstname, ' ', user_lastname), lessons_execution_start_date ", "FROM "+usersTable
					+" JOIN "+lessonsExecutionsTable+" ON "+usersTable+".user_id = "+lessonsExecutionsTable+".executor_id", " WHERE ("+lessonsExecutionsTable+".lesson_id = "+lesson_id
					+") AND ("+lessonsExecutionsTable+".group_id IS NULL) AND ("+usersTable+".user_id = "+mainApp.getLogin().getUser_id()+")", "", "", "");
			}
			try {
				while (resultUsers.next()) {
					if (resultUsers.getString(3) == null)
						date = null;
					else
						date = LocalDate.parse(resultUsers.getString(3), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					TreeItem<User> item = new TreeItem<User>(new User(resultUsers.getInt(1),0,resultUsers.getString(2),"User",date,"","","","","","",0,"",0,""));
					tableStudents.getRoot().getChildren().add(item);
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
			tableStudents.getSelectionModel().clearSelection();
		}
	}
	
	// Insert or update lessons
	private void updateLesson(Lesson lesson) {
		if (lesson.getLesson_id() == 0)
			if (LessonsTable.getInstance().insert(lesson)) {
				String lessonsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsTable_id.getValue());
				ResultSet result = LessonsTable.getInstance().executeSelect("", "lesson_id", "FROM "+lessonsTable, 
						"WHERE (course_id = "+lesson.getCourse_id()+") AND (lesson_name = \'"+lesson.getLesson_name()+"\')", "", "", "");
				try {
					if (result.next()) {
						lesson.setLesson_id(result.getInt(1));
						listLessons.add(lesson);
						tableLessons.getSelectionModel().select(lesson);
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
			else {
				Alert splitAlert = new Alert(AlertType.ERROR);
				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setContentText(Strings.InsertError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.showAndWait();
			}
		else
			if (LessonsTable.getInstance().update(lesson.getLesson_id(),lesson))
				updateInformation(lesson);
			else {
				Alert splitAlert = new Alert(AlertType.ERROR);
				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setContentText(Strings.UpdateError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.showAndWait();
			}
	}
	
	// Update Information
	private void updateInformation(Lesson lesson) {
		if (lesson != null) {
			String view = lesson.getLesson_details();
			if(view.contains("contenteditable=\"true\"")){
				view=view.replace("contenteditable=\"true\"", "contenteditable=\"false\"");
			}
			textDetails.getEngine().loadContent(view);
			tableStudents.getRoot().getChildren().clear();
			getListStudents();
		}
		else {
			textDetails.getEngine().loadContent("");
			tableStudents.getRoot().getChildren().clear();
		}
	}
	
	// Insert Group
	private void updateGroup(User user) {
		Alert splitAlert;
		Execution execution;
		ResultSet result;
		Lesson lesson = tableLessons.getSelectionModel().getSelectedItem();
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		String coursesExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesExecutionsTable_id.getValue());
		String lessonsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsExecutionsTable_id.getValue());
		String start_date = "\'"+LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\'";
		result = LessonsExecutionsTable.getInstance().executeSelect("", "lessons_execution_id", "FROM "+lessonsExecutionsTable, 
				"WHERE (lesson_id = "+lesson_id+") AND (group_id = "+user.getUser_id()+")  AND (executor_id IS NULL) AND (lessons_execution_start_date = "+start_date+")", "", "", "");
		try {
			if (!result.next()) {
				TreeItem<User> item = new TreeItem<User>(new User(user.getUser_id(),0,user.getUser_firstname(),"Group",LocalDate.now(),
						"","","","","","",0,"",0,""));
				execution = new Execution(0,lesson_id,0,user.getUser_id(),item.getValue().getUser_birthday(),null,0,0,
						lesson.getLesson_name(),user.getUser_firstname());
				if (LessonsExecutionsTable.getInstance().insert(execution)) {
					tableStudents.getRoot().getChildren().add(item);
					tableStudents.getSelectionModel().select(item);
					result = UsersTable.getInstance().executeSelect("", usersTable+".user_id, CONCAT(user_firstname, ' ', user_lastname) ", 
							"FROM "+usersTable+" JOIN "+coursesExecutionsTable +" ON "+usersTable+".user_id = "+coursesExecutionsTable+".executor_id", 
							" WHERE ("+coursesExecutionsTable+".course_id = "+course.getCourse_id()+") AND ("+coursesExecutionsTable+".group_id = "+item.getValue().getUser_id()+")", "", "", "");
					try {
						while (result.next()) {
							item.getChildren().add(new TreeItem<User>(new User(result.getInt(1),0,result.getString(2),"Student",item.getValue().getUser_birthday(),"","","","","","",0,"",0,"")));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
						splitAlert = new Alert(AlertType.ERROR);
						((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
						splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
						splitAlert.setHeaderText(Strings.InitError.getValue(Indexes.AppLanguage_index.getValue()));
						splitAlert.setContentText(Strings.SelectError.getValue(Indexes.AppLanguage_index.getValue()));
						splitAlert.showAndWait();
					}
					item.getChildren().forEach(child -> {
						Execution childExecution = new Execution(0,lesson_id,child.getValue().getUser_id(),item.getValue().getUser_id(),item.getValue().getUser_birthday(),null,0,0,
								lesson.getLesson_name(),child.getValue().getUser_firstname());
						if (!LessonsExecutionsTable.getInstance().insert(childExecution)) {
							item.getChildren().remove(child);
							Alert childAlert = new Alert(AlertType.ERROR);
							((Stage) childAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
							childAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
							childAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
							childAlert.setContentText(Strings.InsertError.getValue(Indexes.AppLanguage_index.getValue()));
							childAlert.showAndWait();
						}
					});
				}
				else {
					splitAlert = new Alert(AlertType.ERROR);
					((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
					splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setContentText(Strings.InsertError.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.showAndWait();
				}
			}
			else {
				splitAlert = new Alert(AlertType.ERROR);
				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setContentText(Strings.InsertError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.showAndWait();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InitError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.SelectError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();
		}
	}
	
	// Insert student
	private void updateUser(User user) {
		Alert splitAlert;
		Execution execution;
		ResultSet result;
		Lesson lesson = tableLessons.getSelectionModel().getSelectedItem();
		String lessonsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsExecutionsTable_id.getValue());
		String start_date = "\'"+LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\'";
		result = LessonsExecutionsTable.getInstance().executeSelect("", "lessons_execution_id", "FROM "+lessonsExecutionsTable, 
				"WHERE (lesson_id = "+lesson_id+") AND (executor_id = "+user.getUser_id()+") AND (lessons_execution_start_date = "+start_date+")", "", "", "");
		try {
			if (!result.next()) {
				TreeItem<User> item = new TreeItem<User>(new User(user.getUser_id(),0,user.getUser_firstname(),"User",LocalDate.now(),
						"","","","","","",0,"",0,""));
				execution = new Execution(0,lesson_id,user.getUser_id(),0,item.getValue().getUser_birthday(),null,0,0,
						lesson.getLesson_name(),user.getUser_firstname());
				if (LessonsExecutionsTable.getInstance().insert(execution)) {
					tableStudents.getRoot().getChildren().add(item);
					tableStudents.getSelectionModel().select(item);
				}
				else {
					splitAlert = new Alert(AlertType.ERROR);
					((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
					splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setContentText(Strings.InsertError.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.showAndWait();
				}
			}
			else {
				splitAlert = new Alert(AlertType.ERROR);
				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setContentText(Strings.InsertError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.showAndWait();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InitError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.SelectError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();
		}
	}
	
	// Remove group and group students
	private void removeGroup(TreeItem<User> user) {
		Alert splitAlert;
		String start_date;
		if (user.getValue().getUser_birthday() == null)
			start_date = "NULL";
		else
			start_date = "\'"+user.getValue().getUser_birthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\'";
		if (LessonsExecutionsTable.getInstance().executeDelete(" WHERE lesson_id = "+lesson_id+" AND group_id = "+user.getValue().getUser_id()
				+" AND executor_id IS NULL AND lessons_execution_start_date ="+start_date)) {
			user.getChildren().forEach(child -> removeUser(child));
			user.getParent().getChildren().remove(user);
			if (tableStudents.getRoot().getChildren().size() == 0)
				tableStudents.getSelectionModel().clearSelection();
		}
		else {
			splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.DeleteErrorHeader.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.DeleteErrorContent.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();
		}
	}
	
	// Remove individual user
	private void removeUser(TreeItem<User> user) {
		Alert splitAlert;
		String tasksTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.TasksTable_id.getValue());
		String lessonsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsExecutionsTable_id.getValue());
		String start_date;
		if (user.getValue().getUser_birthday() == null)
			start_date = "NULL";
		else
			start_date = "\'"+user.getValue().getUser_birthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\'";
		ResultSet resultSet = LessonsExecutionsTable.getInstance().executeSelect("", "lessons_execution_id ", "FROM "+lessonsExecutionsTable, 
				" WHERE (lesson_id = "+lesson_id+") AND (executor_id = "+user.getValue().getUser_id()+") AND (lessons_execution_start_date = "+start_date+")", "", "", "");
		try {
			if (resultSet.next())
				if (LessonsExecutionsTable.getInstance().delete(resultSet.getInt(1))) {
					if (TasksExecutionsTable.getInstance().executeDelete("WHERE (executor_id = "+user.getValue().getUser_id()+") AND (task_id IN (SELECT "
							+tasksTable+".task_id FROM "+tasksTable+" WHERE "+tasksTable+".lesson_id = "+lesson_id+"))")) {
						user.getParent().getChildren().remove(user);
						if (tableStudents.getRoot().getChildren().size() == 0)
							tableStudents.getSelectionModel().clearSelection();
					}
					else {
						splitAlert = new Alert(AlertType.ERROR);
						((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
						splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
						splitAlert.setHeaderText(Strings.DeleteErrorHeader.getValue(Indexes.AppLanguage_index.getValue()));
						splitAlert.setContentText(Strings.DeleteErrorContent.getValue(Indexes.AppLanguage_index.getValue()));
						splitAlert.showAndWait();
					}
				}
				else {
					splitAlert = new Alert(AlertType.ERROR);
					((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
					splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setHeaderText(Strings.DeleteErrorHeader.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setContentText(Strings.DeleteErrorContent.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.showAndWait();
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InitError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.SelectError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();
		}
	}
	
	// Update execution
	private void updateStatus(Execution execution) {
		User user = tableStudents.getSelectionModel().getSelectedItem().getValue();
		if (user.getUser_lastname().equals("Group")) {
			String start_date, end_date;
			if (execution.getExecution_start_date() == null)
				start_date = "NULL";
			else
				start_date = "\'"+execution.getExecution_start_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\'";
			if (execution.getExecution_end_date() == null)
				end_date = "NULL";
			else
				end_date = "\'"+execution.getExecution_end_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"\'";
			if ((LessonsExecutionsTable.getInstance().executeUpdate("", "SET lessons_execution_start_date = "+start_date+", lessons_execution_end_date = "+end_date,
					" WHERE lesson_id = "+lesson_id+" AND group_id = "+user.getUser_id()+" AND lessons_execution_start_date = "+date)) && 
					(LessonsExecutionsTable.getInstance().update(execution.getExecution_id(),execution))) {
				user.setUser_birthday(execution.getExecution_start_date());
				tableStudents.getSelectionModel().getSelectedItem().getChildren().forEach(item -> item.getValue().setUser_birthday(execution.getExecution_start_date()));
			}
			else {
				Alert splitAlert = new Alert(AlertType.ERROR);
				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setContentText(Strings.UpdateError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.showAndWait();
			}
		}
		else if (LessonsExecutionsTable.getInstance().update(execution.getExecution_id(),execution)) {
			user.setUser_birthday(execution.getExecution_start_date());
		}
		else {
			Alert splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.UpdateError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();
		}
	}
	
	// Getters and setters
	public InAltoSchool getMainApp() {
		return mainApp;
	}
	
	public void setMainApp(InAltoSchool mainApp) {
		this.mainApp = mainApp;
	}
	
	public TableView<Lesson> getTableLessons() {
		return tableLessons;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public void setCourse(Course course) {
		this.course = course;
		textAuthorName.setText(course.getAuthor_name());
		textCourseName.setText(course.getCourse_name());
		listLessons.clear();
		getListLessons(listLessons);
		changeLogin();
	}

}
