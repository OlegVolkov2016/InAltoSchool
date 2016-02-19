package application.view;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import application.InAltoSchool;
import application.Indexes;
import application.Strings;
import application.database.InAltoSchoolDatabase;
import application.database.ProjectsExecutionsTable;
import application.database.TasksExecutionsTable;
import application.database.TasksTable;
import application.database.UsersTable;
import application.model.Course;
import application.model.Execution;
import application.model.Lesson;
import application.model.Login;
import application.model.Task;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class LessonsTasksSplitPaneController {

	// Main application reference 
	private InAltoSchool mainApp;
	
	// Observable lists
	ObservableList<Task> listTasks;
	ObservableList<User> listStudents;
	
	// Course data
	private Course course;
	
	// Lesson data
	private Lesson lesson;
	
	// Current IDs
	private int task_id;
	
	// Children elements references	
	@FXML
	private Button viewButton, addButton, removeButton, statusButton, newButton, editButton, deleteButton, projectsButton;
	
	@FXML
	private Label labelProjectName, labelTaskTitle, labelAuthor, labelInformation, labelDetails, labelStudents;
	
	@FXML
	private TextField textAuthorName;
	
	@FXML
	private TableView<Task> tableTasks;
	
	@FXML
	private TableColumn<Task, String> columnTaskName;
	
	@FXML
	private TableColumn<User, LocalDate> columnStudentDate;
	
	@FXML
	private TableView<User> tableStudents;
	
	@FXML
	private TableColumn<User, String> columnStudentName;
	
	@FXML
	private TextArea textProjectName;
	
	@FXML
	private WebView textDetails;
	
	@FXML
	private void initialize() {
		// Set Lists
		listTasks = FXCollections.observableArrayList();
		listStudents = FXCollections.observableArrayList();
		tableTasks.setItems(listTasks);
		columnTaskName.setCellValueFactory(cell -> cell.getValue().task_nameProperty());
		tableStudents.setItems(listStudents);
		columnStudentName.setCellValueFactory(cell -> cell.getValue().user_firstnameProperty());
		columnStudentDate.setCellValueFactory(cell -> cell.getValue().user_birthdayProperty());
		// Set Handlers
		setHandlers();	
	}
	
	// Button Events
	@FXML
	private void viewButton() {
		Task task = tableTasks.getSelectionModel().getSelectedItem();
		Stage dialogStage = new Stage();
		WebViewDialog viewDialog = new WebViewDialog(dialogStage);
		viewDialog.getController().loadContent(task.getTask_details());
		dialogStage.setTitle(Strings.LabelTaskDetails.getValue(Indexes.AppLanguage_index.getValue()));
		Scene scene = new Scene(viewDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
	}
	
	@FXML
	private void addButton() {
		Task task = tableTasks.getSelectionModel().getSelectedItem();
		Stage dialogStage = new Stage();
		StudentsLessonTaskDialog studentsLessonTaskDialog = new StudentsLessonTaskDialog(dialogStage);
		studentsLessonTaskDialog.getController().setCourse(course);
		studentsLessonTaskDialog.getController().setLesson(lesson);
		studentsLessonTaskDialog.getController().setTask(task);
        Scene scene = new Scene(studentsLessonTaskDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (studentsLessonTaskDialog.getController().isAddButton()) {
        	updateStudent(studentsLessonTaskDialog.getController().getUser(),studentsLessonTaskDialog.getController().getGroup());
        }
	}
	
	@FXML
	private void removeButton() {
		User user = tableStudents.getSelectionModel().getSelectedItem();
		Alert splitAlert = new Alert(AlertType.CONFIRMATION);
		((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
		splitAlert.setTitle(Strings.ConfirmTitle.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setHeaderText(Strings.ConfirmDeleteHeader.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setContentText(Strings.ConfirmDeleteContent.getValue(Indexes.AppLanguage_index.getValue())+
				user.getUser_firstname()+"?");
		Optional<ButtonType> result = splitAlert.showAndWait();
		if (result.get() == ButtonType.OK) {
			String tasksExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.TasksExecutionsTable_id.getValue());
			ResultSet resultSet = TasksExecutionsTable.getInstance().executeSelect("", "tasks_execution_id ", "FROM "+tasksExecutionsTable, 
				"WHERE task_id = "+task_id+" AND executor_id = "+user.getUser_id(), "", "", "");
			try {
				if (resultSet.next())
					if (TasksExecutionsTable.getInstance().delete(resultSet.getInt(1))) {
						listStudents.remove(user);
						if (listStudents.size() == 0) {
							tableStudents.getSelectionModel().select(null);
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
	}
	
	@FXML
	private void statusButton() {
		Task task = tableTasks.getSelectionModel().getSelectedItem();
		User user = tableStudents.getSelectionModel().getSelectedItem();
		String tasksExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.TasksExecutionsTable_id.getValue());
		ResultSet result = TasksExecutionsTable.getInstance().executeSelect("", "tasks_execution_id, task_id, executor_id, tasks_execution_start_date, "+
				"tasks_execution_end_date, tasks_execution_status, tasks_execution_result ", "FROM "+tasksExecutionsTable, 
				"WHERE task_id = "+task_id+" AND executor_id = "+user.getUser_id(), "", "", "");
		try {
			if (result.next()) {
				LocalDate startDate, endDate;
				if (result.getString(4) == null)
					startDate = null;
				else
					startDate = LocalDate.parse(result.getString(4), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				if (result.getString(5) == null)
					endDate = null;
				else
					endDate = LocalDate.parse(result.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				Execution execution = new Execution(result.getInt(1),result.getInt(2),result.getInt(3),0,startDate,endDate,
						result.getInt(6),result.getInt(7),lesson.getLesson_name(),user.getUser_firstname());
				Stage dialogStage = new Stage();
				LessonTaskStatusDialog statusDialog = new LessonTaskStatusDialog(dialogStage);
				statusDialog.getController().setLesson(lesson);
				statusDialog.getController().setTask(task);
				statusDialog.getController().setExecution(execution);
				if ((mainApp.getLogin().getRole_name().equals("Administrator")) || 
					(mainApp.getLogin().getUser_id() == course.getAuthor_id()))
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
		Task task = new Task(0,lesson.getLesson_id(),0,"","","","",lesson.getLesson_name(),lesson.getAuthor_name());
		Stage dialogStage = new Stage();
		LessonTaskDialog lessonTaskDialog = new LessonTaskDialog(dialogStage);
		lessonTaskDialog.getController().setLesson(lesson);
		lessonTaskDialog.getController().setTask(task);
        Scene scene = new Scene(lessonTaskDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (lessonTaskDialog.getController().isOKButton()) {
        	updateTask(lessonTaskDialog.getController().getTask());
        }
	}
	
	@FXML
	private void editButton() {
		Task task = tableTasks.getSelectionModel().getSelectedItem();
		Stage dialogStage = new Stage();
		LessonTaskDialog lessonTaskDialog = new LessonTaskDialog(dialogStage);
		lessonTaskDialog.getController().setLesson(lesson);
		lessonTaskDialog.getController().setTask(task);
        Scene scene = new Scene(lessonTaskDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (lessonTaskDialog.getController().isOKButton()) {
        	updateTask(lessonTaskDialog.getController().getTask());
        }
	}
	
	@FXML
	private void deleteButton() {
		Task task = tableTasks.getSelectionModel().getSelectedItem();
		Alert splitAlert = new Alert(AlertType.CONFIRMATION);
		((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
		splitAlert.setTitle(Strings.ConfirmTitle.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setHeaderText(Strings.ConfirmDeleteHeader.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setContentText(Strings.ConfirmDeleteContent.getValue(Indexes.AppLanguage_index.getValue())+
				task.getTask_name()+"?");
		Optional<ButtonType> result = splitAlert.showAndWait();
		if (result.get() == ButtonType.OK) {
			if (TasksTable.getInstance().delete(task.getTask_id())) {
				listTasks.remove(task);
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
	private void projectsButton() {
		if (mainApp.getRootLayout().getMainSplitPanesList()[Indexes.LessonsSplitPane_id.getValue()] == null)
			mainApp.getRootLayout().getMainSplitPanesList()[Indexes.LessonsSplitPane_id.getValue()] = new LessonsSplitPane(this.mainApp);
		mainApp.getRootLayout().setMainSplitPane(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.LessonsSplitPane_id.getValue()]);
		mainApp.getRootLayout().setCenter(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.LessonsSplitPane_id.getValue()]);
	}
	
	// ChangeLanguage for elements
	public void changeLanguage () {
		labelProjectName.setText(Strings.LabelCourseName.getValue(Indexes.AppLanguage_index.getValue()));
		labelTaskTitle.setText(Strings.LabelTaskTitle.getValue(Indexes.AppLanguage_index.getValue()));
		labelAuthor.setText(Strings.LabelAuthor.getValue(Indexes.AppLanguage_index.getValue()));
		labelInformation.setText(Strings.LabelInformation.getValue(Indexes.AppLanguage_index.getValue()));
		labelDetails.setText(Strings.LabelTaskDetails.getValue(Indexes.AppLanguage_index.getValue()));
		labelStudents.setText(Strings.LabelCoursesStudents.getValue(Indexes.AppLanguage_index.getValue()));
		columnTaskName.setText(Strings.ColumnTaskName.getValue(Indexes.AppLanguage_index.getValue()));
		columnStudentName.setText(Strings.ColumnExecutorName.getValue(Indexes.AppLanguage_index.getValue()));
		columnStudentDate.setText(Strings.ColumnExecutorDate.getValue(Indexes.AppLanguage_index.getValue()));
		addButton.setText(Strings.AddButton.getValue(Indexes.AppLanguage_index.getValue()));
		removeButton.setText(Strings.RemoveButton.getValue(Indexes.AppLanguage_index.getValue()));
		statusButton.setText(Strings.StatusButton.getValue(Indexes.AppLanguage_index.getValue()));
		newButton.setText(Strings.NewButton.getValue(Indexes.AppLanguage_index.getValue()));
		editButton.setText(Strings.EditButton.getValue(Indexes.AppLanguage_index.getValue()));
		deleteButton.setText(Strings.DeleteButton.getValue(Indexes.AppLanguage_index.getValue()));
		projectsButton.setText(Strings.LessonsBackButton.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// ChangeLogin for elements
	public void changeLogin () {
		tableTasks.getSelectionModel().clearSelection();
		if (lesson != null) {
			if (isAccessLesson(getMainApp().getLogin(), lesson)) {
				if ((getMainApp().getLogin().getRole_name().equals("Student")) ||
						((getMainApp().getLogin().getUser_id() != course.getAuthor_id()) && 
						(!getMainApp().getLogin().getRole_name().equals("Administrator")))) {
						viewButton.setDisable(true);
						addButton.setDisable(true);
						removeButton.setDisable(true);
						statusButton.setDisable(true);
						newButton.setDisable(true);
						editButton.setDisable(true);
						deleteButton.setDisable(true);
						projectsButton.setDisable(false);
				}
				else {
					viewButton.setDisable(true);
					addButton.setDisable(true);
					removeButton.setDisable(true);
					statusButton.setDisable(true);
					newButton.setDisable(false);
					editButton.setDisable(true);
					deleteButton.setDisable(true);
					projectsButton.setDisable(false);
				}
			}
			else {
				projectsButton();
				((LessonsSplitPane) getMainApp().getRootLayout().getMainSplitPane()).getController().getTableLessons().getSelectionModel().clearSelection();
			}
		}
	}
	
	// Set Handlers
	private void setHandlers() {
		// Tasks list
		tableTasks.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
			@Override
			public void changed(ObservableValue<? extends Task> observable, Task oldValue, Task newValue) {
				// TODO Auto-generated method stub
				if (newValue != null) {
					task_id = newValue.getTask_id();
					if ((mainApp.getLogin() != null) && (isAccessTask(mainApp.getLogin(),newValue))) {
						updateInformation(newValue);
						viewButton.setDisable(false);
						addButton.setDisable(false);
						removeButton.setDisable(true);
						statusButton.setDisable(true);
						newButton.setDisable(false);
						editButton.setDisable(false);
						deleteButton.setDisable(false);
						projectsButton.setDisable(false);
						if ((!mainApp.getLogin().getRole_name().equals("Administrator")) && (mainApp.getLogin().getUser_id() != course.getAuthor_id())) {
							viewButton.setDisable(false);
							addButton.setDisable(true);
							removeButton.setDisable(true);
							statusButton.setDisable(true);
							newButton.setDisable(true);
							editButton.setDisable(true);
							deleteButton.setDisable(true);
							projectsButton.setDisable(false);
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
		tableStudents.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				// TODO Auto-generated method stub
				if (newValue != null) {
					if ((mainApp.getLogin().getRole_name().equals("Administrator")) || 
						(mainApp.getLogin().getUser_id() == course.getAuthor_id())) {
						removeButton.setDisable(false);
						statusButton.setDisable(false);	
					}
					else if (mainApp.getLogin().getUser_id() == newValue.getUser_id()) {
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
		columnStudentDate.setCellFactory(new Callback<TableColumn<User,LocalDate>, TableCell<User,LocalDate>>() {
			@Override
			public TableCell<User, LocalDate> call(TableColumn<User, LocalDate> param) {
				// TODO Auto-generated method stub
				TableCell<User, LocalDate> tableCell = new TableCell<User, LocalDate>() {
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
	
	// Check access for lesson
	private boolean isAccessLesson(Login login, Lesson lesson) {
		if (login.getRole_name().equals("Administrator"))
			return true;
		if (login.getUser_id() == course.getAuthor_id())
			return true;
		String lessonsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsExecutionsTable_id.getValue());
		ResultSet result = ProjectsExecutionsTable.getInstance().executeSelect("", "lessons_execution_id ", "FROM "+lessonsExecutionsTable+" ", 
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
	
	// Check access for task
	private boolean isAccessTask(Login login, Task task) {
		if (login.getRole_name().equals("Administrator"))
			return true;
		if (login.getUser_id() == course.getAuthor_id())
			return true;
		String tasksExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.TasksExecutionsTable_id.getValue());
		ResultSet result = TasksExecutionsTable.getInstance().executeSelect("", "tasks_execution_id ", "FROM "+tasksExecutionsTable+" ", 
			"WHERE (task_id = "+task.getTask_id()+") AND (executor_id = "+login.getUser_id()+")", "", "", "");
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
	private void getListTasks(ObservableList<Task> listTasks) {
		String tasksTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.TasksTable_id.getValue());
		ResultSet result = TasksTable.getInstance().executeSelect("", "task_id, lesson_id, project_id, task_name, task_details ",
					"FROM "+tasksTable, " WHERE lesson_id = "+lesson.getLesson_id(), "", "", "");
		try {
			while(result.next()) {
				listTasks.add(new Task(result.getInt(1),result.getInt(2),result.getInt(3),result.getString(4),result.getString(5),"","",
					lesson.getLesson_name(),course.getAuthor_name()));
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
	
	private void getListStudents(ObservableList<User> listStudents) {
		LocalDate date;
		String tasksExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.TasksExecutionsTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		ResultSet result = UsersTable.getInstance().executeSelect("", "user_id, CONCAT(user_firstname, ' ', user_lastname), tasks_execution_start_date ", "FROM "+usersTable+" JOIN "+tasksExecutionsTable
				+" ON "+usersTable+".user_id = "+tasksExecutionsTable+".executor_id", " WHERE "+tasksExecutionsTable+".task_id = "+task_id, " GROUP BY user_id", "", "");
		try {
			while (result.next()) {
				if (result.getString(3) == null)
					date = null;
				else
					date = LocalDate.parse(result.getString(3), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				listStudents.add(new User(result.getInt(1),0,result.getString(2),"",date,"","","","","","",0,"",0,""));
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
	
	// Insert or update tasks
	private void updateTask(Task task) {
		ResultSet result;
		String tasksTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.TasksTable_id.getValue());
		if (task.getTask_id() == 0) {
			result = TasksTable.getInstance().executeSelect("", "task_id", "FROM "+tasksTable, 
					"WHERE (lesson_id = "+task.getLesson_id()+") AND (task_name = \'"+task.getTask_name()+"\')", "", "", "");
			try {
				if (!result.next())
					if (TasksTable.getInstance().insert(task)) {
						result = TasksTable.getInstance().executeSelect("", "task_id", "FROM "+tasksTable, 
								"WHERE (lesson_id = "+task.getLesson_id()+") AND (task_name = \'"+task.getTask_name()+"\')", "", "", "");
						try {
							if (result.next()) {
								task.setTask_id(result.getInt(1));
								listTasks.add(task);
								tableTasks.getSelectionModel().select(task);
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
				else {
					Alert splitAlert = new Alert(AlertType.ERROR);
					((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
					splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setContentText(Strings.InsertError.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.showAndWait();
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
		else
			if (TasksTable.getInstance().update(task.getTask_id(),task))
				updateInformation(task);
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
	private void updateInformation(Task task) {
		if (task != null) {
			String view = task.getTask_details();
			if(view.contains("contenteditable=\"true\"")){
				view=view.replace("contenteditable=\"true\"", "contenteditable=\"false\"");
			}
			textDetails.getEngine().loadContent(view);
			listStudents.clear();
			getListStudents(listStudents);
		}
		else {
			textDetails.getEngine().loadContent("");
			listStudents.clear();
		}
	}
	
	// Insert student
	private void updateStudent(User user, User group) {
		if (user != null) {
			user.setUser_birthday(LocalDate.now());
			Execution execution = new Execution(0,task_id,user.getUser_id(),0,user.getUser_birthday(),null,0,0,
					tableTasks.getSelectionModel().getSelectedItem().getTask_name(),user.getUser_firstname());
			if (TasksExecutionsTable.getInstance().insert(execution)) {
				listStudents.add(user);
				tableStudents.getSelectionModel().select(user);
			}
			else {
				Alert splitAlert = new Alert(AlertType.ERROR);
				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setContentText(Strings.InsertError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.showAndWait();
			}
		}
		else {
			ArrayList<Execution> executions = new ArrayList<>();
			String lessonsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsExecutionsTable_id.getValue());
			String tasksExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.TasksExecutionsTable_id.getValue());
			String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
			ResultSet result = UsersTable.getInstance().executeSelect("", usersTable+".user_id, CONCAT(user_firstname, ' ', user_lastname) ", "FROM "+usersTable+" JOIN "+lessonsExecutionsTable
					+" ON ("+usersTable+".user_id = "+lessonsExecutionsTable+".executor_id) LEFT JOIN "+tasksExecutionsTable+" ON ("+usersTable+".user_id = "+tasksExecutionsTable+".executor_id) AND ("
					+tasksExecutionsTable+".task_id = "+task_id+") ", "WHERE ("+tasksExecutionsTable+".executor_id IS NULL) AND ("+lessonsExecutionsTable+".lesson_id = "+lesson.getLesson_id()
					+") AND ("+lessonsExecutionsTable+".group_id = "+group.getUser_id()+") ", "GROUP BY user_id ", "", "");
			try {
				while (result.next()) {
					executions.add(new Execution(0,task_id,result.getInt(1),0,LocalDate.now(),null,0,0,
							tableTasks.getSelectionModel().getSelectedItem().getTask_name(),result.getString(2)));
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
			executions.forEach(value -> {
				if (TasksExecutionsTable.getInstance().insert(value)) {
					User groupUser = new User(value.getExecution_id(),0,value.getExecutor_name(),"",value.getExecution_start_date(),"","","","","","",0,"",0,"");
					listStudents.add(groupUser);
					tableStudents.getSelectionModel().select(groupUser);
				}
				else {
					Alert childAlert = new Alert(AlertType.ERROR);
					((Stage) childAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
					childAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
					childAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
					childAlert.setContentText(Strings.InsertError.getValue(Indexes.AppLanguage_index.getValue()));
					childAlert.showAndWait();
				}
			});
		}
	}
	
	// Update execution
	private void updateStatus(Execution execution) {
		if (!TasksExecutionsTable.getInstance().update(execution.getExecution_id(),execution)) {
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
	
	public Course getCourse() {
		return course;
	}
	
	public void setCourse(Course course) {
		this.course = course;
	}
	
	public Lesson getLesson() {
		return lesson;
	}
	
	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
		textAuthorName.setText(lesson.getAuthor_name());
		textProjectName.setText(Strings.LabelCourseName.getValue(Indexes.AppLanguage_index.getValue())+" "+lesson.getCourse_name()+"\n"+
				Strings.LabelLessonName.getValue(Indexes.AppLanguage_index.getValue())+" "+lesson.getLesson_name());
		listTasks.clear();
		getListTasks(listTasks);
		changeLogin();
	}

}
