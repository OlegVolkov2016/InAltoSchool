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
import application.database.LessonsExecutionsTable;
import application.database.StudentsTable;
import application.database.TasksExecutionsTable;
import application.database.CoursesExecutionsTable;
import application.database.CoursesTable;
import application.database.GroupsTable;
import application.database.UsersTable;
import application.model.Execution;
import application.model.Group;
import application.model.Login;
import application.model.Student;
import application.model.Course;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class CoursesSplitPaneController {

	// Main application reference 
	private InAltoSchool mainApp;
	
	// Observable lists
	ObservableList<Course> listCourses;
	ObservableList<User> listAuthors;
	
	// Current IDs
	private int author_id;
	private int course_id;
	
	// Children elements references	
	@FXML
	private Button viewButton, newGroupButton, editGroupButton, addButton, removeButton, statusButton, newButton, editButton, deleteButton, lessonsButton, loginButton;
	
	@FXML
	private Label labelCourseTitle, labelAuthors, labelInformation, labelCourseGroupPrice, labelCourseIndividualPrice, labelDetails, labelStudents;
	
	@FXML
	private TextField textCourseGroupPrice, textCourseIndividualPrice;
	
	@FXML
	private ComboBox<User> comboAuthors;
	
	@FXML
	private TableView<Course> tableCourses;
	
	@FXML
	private TableColumn<Course, String> columnCourseName, columnCourseAuthor;
	
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
		author_id = 0;
		course_id = 0;
		listAuthors = FXCollections.observableArrayList();
		listCourses = FXCollections.observableArrayList();
		getListAuthors(listAuthors);
		comboAuthors.setItems(listAuthors);
		comboAuthors.setValue(listAuthors.get(author_id));
		getListCourses(listCourses);
		tableCourses.setItems(listCourses);
		columnCourseName.setCellValueFactory(cell -> cell.getValue().course_nameProperty());
		columnCourseAuthor.setCellValueFactory(cell -> cell.getValue().author_nameProperty());
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
		Course course = tableCourses.getSelectionModel().getSelectedItem();
		Stage dialogStage = new Stage();
		WebViewDialog viewDialog = new WebViewDialog(dialogStage);
		viewDialog.getController().loadContent(course.getCourse_details());
		dialogStage.setTitle(Strings.LabelCourseDetails.getValue(Indexes.AppLanguage_index.getValue()));
		Scene scene = new Scene(viewDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
	}
	
	@FXML
	private void newGroupButton() {
		Course course = tableCourses.getSelectionModel().getSelectedItem();
		Group group = new Group(0,course_id,"","",course.getCourse_name(),course.getAuthor_name());
		Stage dialogStage = new Stage();
		GroupDialog groupDialog = new GroupDialog(dialogStage);
		groupDialog.getController().setGroup(group);
        Scene scene = new Scene(groupDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (groupDialog.getController().isOKButton()) {
        	updateGroup(groupDialog.getController().getGroup());
        }
	}
	
	@FXML
	private void editGroupButton() {
		User user = tableStudents.getSelectionModel().getSelectedItem().getValue();
		Group group = new Group(user.getUser_id(),user.getLogin_id(),user.getUser_firstname(),user.getUser_address(),user.getUser_phone(),user.getUser_email());
		Stage dialogStage = new Stage();
		GroupDialog groupDialog = new GroupDialog(dialogStage);
		groupDialog.getController().setGroup(group);
        Scene scene = new Scene(groupDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (groupDialog.getController().isOKButton()) {
        	updateGroup(groupDialog.getController().getGroup());
        }
	}
	
	@FXML
	private void addButton() {
		ObservableList<TreeItem<User>> listGroups = FXCollections.observableArrayList();
		Course course = tableCourses.getSelectionModel().getSelectedItem();
		Stage dialogStage = new Stage();
		StudentsCourseDialog studentsDialog = new StudentsCourseDialog(dialogStage);
		studentsDialog.getController().setCourse(course);
		listGroups.add(tableStudents.getRoot());
		tableStudents.getRoot().getChildren().forEach((item -> {
			if (item.getValue().getUser_lastname().equals("Group"))
				listGroups.add(item);
		}));
		studentsDialog.getController().setListGroups(listGroups);
        Scene scene = new Scene(studentsDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (studentsDialog.getController().isAddButton()) {
        	updateStudent(studentsDialog.getController().getUser(),studentsDialog.getController().getGroup());
        }
	}
	
	@FXML
	private void removeButton() {
		Alert splitAlert;
		TreeItem<User> user = tableStudents.getSelectionModel().getSelectedItem();
		String lessonsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsTable_id.getValue());
		splitAlert = new Alert(AlertType.CONFIRMATION);
		((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
		splitAlert.setTitle(Strings.ConfirmTitle.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setHeaderText(Strings.ConfirmDeleteHeader.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setContentText(Strings.ConfirmDeleteContent.getValue(Indexes.AppLanguage_index.getValue())+
				user.getValue().getUser_firstname()+"?");
		Optional<ButtonType> result = splitAlert.showAndWait();
		if (result.get() == ButtonType.OK) {
			if (user.getValue().getUser_lastname().equals("Group")) {
				if (GroupsTable.getInstance().delete(user.getValue().getUser_id())) {
					if (LessonsExecutionsTable.getInstance().executeDelete(" WHERE (group_id = "+user.getValue().getUser_id()+") AND (lesson_id IN (SELECT "
						+lessonsTable+".lesson_id FROM "+lessonsTable+" WHERE "+lessonsTable+".course_id = "+course_id+"))")) {
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
				else {
					splitAlert = new Alert(AlertType.ERROR);
					((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
					splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setHeaderText(Strings.DeleteErrorHeader.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setContentText(Strings.DeleteErrorContent.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.showAndWait();
				}
			}
			else if (user.getValue().getUser_lastname().equals("Student")) {
				removeStudent(user);
			}
			else {
				removeUser(user);
			}
		}
	}
	
	@FXML
	private void statusButton() {
		ResultSet result;
		Course course = tableCourses.getSelectionModel().getSelectedItem();
		User user = tableStudents.getSelectionModel().getSelectedItem().getValue();
		String coursesExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesExecutionsTable_id.getValue());
		if (user.getUser_lastname().equals("Group")) {
			result = CoursesExecutionsTable.getInstance().executeSelect("", "courses_execution_id, course_id, executor_id, group_id, courses_execution_start_date, "+
					"courses_execution_end_date, courses_execution_status, courses_execution_result ", "FROM "+coursesExecutionsTable, 
					"WHERE (course_id = "+course_id+") AND (group_id = "+user.getUser_id()+") AND (executor_id IS NULL)", "", "", "");
		}
		else if (user.getUser_lastname().equals("Student")) {
			result = CoursesExecutionsTable.getInstance().executeSelect("", "courses_execution_id, course_id, executor_id, group_id, courses_execution_start_date, "+
					"courses_execution_end_date, courses_execution_status, courses_execution_result ", "FROM "+coursesExecutionsTable, 
					"WHERE (course_id = "+course_id+") AND (group_id = "+tableStudents.getSelectionModel().getSelectedItem().getParent().getValue().getUser_id()+
					") AND (executor_id = "+user.getUser_id()+")", "", "", "");
		}
		else {
			result = CoursesExecutionsTable.getInstance().executeSelect("", "courses_execution_id, course_id, executor_id, group_id, courses_execution_start_date, "+
				"courses_execution_end_date, courses_execution_status, courses_execution_result ", "FROM "+coursesExecutionsTable, 
				"WHERE (course_id = "+course_id+") AND (group_id IS NULL) AND (executor_id = "+user.getUser_id()+")", "", "", "");
		}
		try {
			if (result.next()) {
				LocalDate startDate, endDate;
				if (result.getString(5) == null)
					startDate = null;
				else
					startDate = LocalDate.parse(result.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				if (result.getString(6) == null)
					endDate = null;
				else
					endDate = LocalDate.parse(result.getString(6), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				Execution execution = new Execution(result.getInt(1),result.getInt(2),result.getInt(3),result.getInt(4),startDate,endDate,
						result.getInt(7),result.getInt(8),course.getCourse_name(),user.getUser_firstname());
				Stage dialogStage = new Stage();
				CourseStatusDialog statusDialog = new CourseStatusDialog(dialogStage);
				statusDialog.getController().setCourse(course);
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
		Course course = new Course(0,mainApp.getLogin().getUser_id(),"","",0,0,mainApp.getLogin().getUser_name());
		Stage dialogStage = new Stage();
		CourseDialog courseDialog = new CourseDialog(dialogStage);
		courseDialog.getController().setCourse(course);
        Scene scene = new Scene(courseDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (courseDialog.getController().isOKButton()) {
        	updateCourse(courseDialog.getController().getCourse());
        }
	}
	
	@FXML
	private void editButton() {
		Course course = tableCourses.getSelectionModel().getSelectedItem();
		Stage dialogStage = new Stage();
		CourseDialog courseDialog = new CourseDialog(dialogStage);
		courseDialog.getController().setCourse(course);
		Scene scene = new Scene(courseDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (courseDialog.getController().isOKButton()) {
        	updateCourse(courseDialog.getController().getCourse());
        }
	}
	
	@FXML
	private void deleteButton() {
		Course course = tableCourses.getSelectionModel().getSelectedItem();
		Alert splitAlert = new Alert(AlertType.CONFIRMATION);
		((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
		splitAlert.setTitle(Strings.ConfirmTitle.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setHeaderText(Strings.ConfirmDeleteHeader.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setContentText(Strings.ConfirmDeleteContent.getValue(Indexes.AppLanguage_index.getValue())+
				course.getCourse_name()+"?");
		Optional<ButtonType> result = splitAlert.showAndWait();
		if (result.get() == ButtonType.OK) {
			if (CoursesTable.getInstance().delete(course.getCourse_id())) {
				listCourses.remove(course);
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
	private void lessonsButton() {
		Course course = tableCourses.getSelectionModel().getSelectedItem();
		if (mainApp.getRootLayout().getMainSplitPanesList()[Indexes.LessonsSplitPane_id.getValue()] == null)
			mainApp.getRootLayout().getMainSplitPanesList()[Indexes.LessonsSplitPane_id.getValue()] = new LessonsSplitPane(this.mainApp);
		mainApp.getRootLayout().setMainSplitPane(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.LessonsSplitPane_id.getValue()]);
		mainApp.getRootLayout().setCenter(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.LessonsSplitPane_id.getValue()]);
		((LessonsSplitPane) mainApp.getRootLayout().getMainSplitPane()).getController().setCourse(course);
	}
	
	@FXML
	private void loginButton() {
		getMainApp().getRootLayout().getController().authorizationMenu();
	}
	
	// ChangeLanguage for elements
	public void changeLanguage () {
		labelCourseTitle.setText(Strings.LabelCourseTitle.getValue(Indexes.AppLanguage_index.getValue()));
		labelAuthors.setText(Strings.LabelAuthors.getValue(Indexes.AppLanguage_index.getValue()));
		labelInformation.setText(Strings.LabelInformation.getValue(Indexes.AppLanguage_index.getValue()));
		labelCourseGroupPrice.setText(Strings.LabelCourseGroupPrice.getValue(Indexes.AppLanguage_index.getValue()));
		labelCourseIndividualPrice.setText(Strings.LabelCourseIndividualPrice.getValue(Indexes.AppLanguage_index.getValue()));
		labelDetails.setText(Strings.LabelCourseDetails.getValue(Indexes.AppLanguage_index.getValue()));
		labelStudents.setText(Strings.LabelCourseStudents.getValue(Indexes.AppLanguage_index.getValue()));
		columnCourseName.setText(Strings.ColumnCourseName.getValue(Indexes.AppLanguage_index.getValue()));
		columnCourseAuthor.setText(Strings.ColumnCourseAuthor.getValue(Indexes.AppLanguage_index.getValue()));
		columnStudentName.setText(Strings.ColumnCourseStudentName.getValue(Indexes.AppLanguage_index.getValue()));
		columnStudentDate.setText(Strings.ColumnCourseStudentDate.getValue(Indexes.AppLanguage_index.getValue()));
		newGroupButton.setText(Strings.NewGroupButton.getValue(Indexes.AppLanguage_index.getValue()));
		editGroupButton.setText(Strings.EditGroupButton.getValue(Indexes.AppLanguage_index.getValue()));
		addButton.setText(Strings.AddStudentButton.getValue(Indexes.AppLanguage_index.getValue()));
		removeButton.setText(Strings.RemoveButton.getValue(Indexes.AppLanguage_index.getValue()));
		statusButton.setText(Strings.StatusButton.getValue(Indexes.AppLanguage_index.getValue()));
		newButton.setText(Strings.NewButton.getValue(Indexes.AppLanguage_index.getValue()));
		editButton.setText(Strings.EditButton.getValue(Indexes.AppLanguage_index.getValue()));
		deleteButton.setText(Strings.DeleteButton.getValue(Indexes.AppLanguage_index.getValue()));
		lessonsButton.setText(Strings.LessonsButton.getValue(Indexes.AppLanguage_index.getValue()));
		loginButton.setText(Strings.LoginButton.getValue(Indexes.AppLanguage_index.getValue()));
		tableStudents.getRoot().getValue().setUser_firstname(Strings.LabelCourseRoot.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// ChangeLogin for elements
	public void changeLogin () {
		tableCourses.getSelectionModel().clearSelection();
		if (getMainApp().getLogin() ==  null) {
			viewButton.setVisible(true);
			newGroupButton.setVisible(false);
			editGroupButton.setVisible(false);
			addButton.setVisible(false);
			removeButton.setVisible(false);
			statusButton.setVisible(false);
			newButton.setVisible(false);
			editButton.setVisible(false);
			deleteButton.setVisible(false);
			lessonsButton.setVisible(false);
			loginButton.setVisible(true);
		}
		else {
			viewButton.setVisible(true);
			newGroupButton.setVisible(true);
			editGroupButton.setVisible(true);
			addButton.setVisible(true);
			removeButton.setVisible(true);
			statusButton.setVisible(true);
			newButton.setVisible(true);
			editButton.setVisible(true);
			deleteButton.setVisible(true);
			lessonsButton.setVisible(true);
			loginButton.setVisible(false);
			if (getMainApp().getLogin().getRole_name().equals("Student")) {
				viewButton.setDisable(true);
				newGroupButton.setDisable(true);
				editGroupButton.setDisable(true);
				addButton.setDisable(true);
				removeButton.setDisable(true);
				statusButton.setDisable(true);
				newButton.setDisable(true);
				editButton.setDisable(true);
				deleteButton.setDisable(true);
				lessonsButton.setDisable(true);
			}
			else {
				viewButton.setDisable(true);
				newGroupButton.setDisable(true);
				editGroupButton.setDisable(true);
				addButton.setDisable(true);
				removeButton.setDisable(true);
				statusButton.setDisable(true);
				newButton.setDisable(false);
				editButton.setDisable(true);
				deleteButton.setDisable(true);
				lessonsButton.setDisable(true);
			}
		}
	}
	
	// Set Handlers
	private void setHandlers() {
		// Authors list
		comboAuthors.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
			@Override
			public ListCell<User> call(ListView<User> param) {
				// TODO Auto-generated method stub
				ListCell<User> listCell = new ListCell<User>() {
					@Override
					protected void updateItem(User user, boolean empty) {
						super.updateItem(user, empty);      
						if(user != null) {
							setText(user.getUser_firstname()+" "+user.getUser_lastname());
						}
						else {
							setText(null);
						}
					}
				};
            	return listCell;
			}
		});
		comboAuthors.setButtonCell(new ListCell<User>() {
			@Override
			protected void updateItem(User user, boolean empty) {
				super.updateItem(user, empty);      
				if(user != null) {
					setText(user.getUser_firstname()+" "+user.getUser_lastname());
				}
				else {
					setText(null);
				}
			}
		});
		comboAuthors.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				// TODO Auto-generated method stub
				if (newValue != null) {
					author_id = newValue.getUser_id();
					listCourses.clear();
					getListCourses(listCourses);
				}
			}
		});
		// Courses list
		tableCourses.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Course>() {
			@Override
			public void changed(ObservableValue<? extends Course> observable, Course oldValue, Course newValue) {
				// TODO Auto-generated method stub
				if (newValue != null) {
					course_id = newValue.getCourse_id();
					if ((mainApp.getLogin() != null) && (isAccessCourse(mainApp.getLogin(),newValue))) {
						updateInformation(newValue);
						viewButton.setDisable(false);
						newGroupButton.setDisable(false);
						editGroupButton.setDisable(true);
						addButton.setDisable(false);
						removeButton.setDisable(true);
						statusButton.setDisable(true);
						editButton.setDisable(false);
						deleteButton.setDisable(false);
						lessonsButton.setDisable(false);
						if ((!mainApp.getLogin().getRole_name().equals("Administrator")) && (mainApp.getLogin().getUser_id() != newValue.getAuthor_id())) {
							viewButton.setDisable(false);
							newGroupButton.setDisable(true);
							editGroupButton.setDisable(true);
							addButton.setDisable(true);
							removeButton.setDisable(true);
							statusButton.setDisable(true);
							editButton.setDisable(true);
							deleteButton.setDisable(true);
							lessonsButton.setDisable(false);
						}
					}
					else {
						updateInformation(newValue);
						tableStudents.getRoot().getChildren().clear();
						viewButton.setDisable(false);
						newGroupButton.setDisable(true);
						editGroupButton.setDisable(true);
						addButton.setDisable(true);
						removeButton.setDisable(true);
						statusButton.setDisable(true);
						editButton.setDisable(true);
						deleteButton.setDisable(true);
						lessonsButton.setDisable(true);
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
							(mainApp.getLogin().getUser_id() == (tableCourses.getSelectionModel().getSelectedItem().getAuthor_id()))) {
							removeButton.setDisable(false);
							statusButton.setDisable(false);
							if (newValue.getValue().getUser_lastname().equals("Group")) {
								editGroupButton.setDisable(false);
							}
							else {
								editGroupButton.setDisable(true);
							}
					}
					else if ((mainApp.getLogin().getUser_id() == newValue.getValue().getUser_id()) && (!newValue.getValue().getUser_lastname().equals("Group"))) {
						editGroupButton.setDisable(true);
						removeButton.setDisable(true);
						statusButton.setDisable(false);
					}
					else {
						editGroupButton.setDisable(true);
						removeButton.setDisable(true);
						statusButton.setDisable(true);						
					}
				}
				else {
					editGroupButton.setDisable(true);
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
	
	// List getters
	private void getListAuthors(ObservableList<User> listAuthors) {
		String usersTable = InAltoSchoolDatabase .getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		String coursesTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesTable_id.getValue());
		listAuthors.add(0,new User(0,0,Strings.AllItems.getValue(0)+" /",Strings.AllItems.getValue(1),null,"","","","","","",0,"",0,""));
		ResultSet result = UsersTable.getInstance().executeSelect("", "user_id, user_firstname, user_lastname ", "FROM "+usersTable+" JOIN "+coursesTable
				+" ON "+usersTable+".user_id = "+coursesTable+".author_id", "", "GROUP BY user_id", "", "");
		try {
			while(result.next()) {
				listAuthors.add(new User(result.getInt(1),0,result.getString(2),result.getString(3),null,"","","","","","",0,"",0,""));
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
	
	private void getListCourses(ObservableList<Course> listCourses) {
		ResultSet result;
		String coursesTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		if (author_id == 0) {
			result = CoursesTable.getInstance().executeSelect("", coursesTable+".course_id, "+coursesTable+".author_id, course_name, course_details, "
					+"course_group_price, course_individual_price, CONCAT(user_firstname, ' ', user_lastname) ", "FROM "+coursesTable+" JOIN "+usersTable
					+" ON ("+coursesTable+".author_id = "+usersTable+".user_id)", "", "", "", "");
		}
		else {
			result = CoursesTable.getInstance().executeSelect("", coursesTable+".course_id, "+coursesTable+".author_id, course_name, course_details, "
					+"course_group_price, course_individual_price, CONCAT(user_firstname, ' ', user_lastname) ", "FROM "+coursesTable+" JOIN "+usersTable
					+" ON ("+coursesTable+".author_id = "+usersTable+".user_id) AND ("+usersTable+".user_id = "+author_id+")", "", "", "", "");

		}
		try {
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
	
	private void getListStudents() {
		LocalDate date;
		String groupsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.GroupsTable_id.getValue());
		String studentsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.StudentsTable_id.getValue());
		String coursesTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesTable_id.getValue());
		String coursesExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesExecutionsTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		Course course = tableCourses.getSelectionModel().getSelectedItem();
		if (mainApp.getLogin() != null) {
			if ((mainApp.getLogin().getRole_name().equals("Administrator")) || (mainApp.getLogin().getUser_id() == course.getAuthor_id())) {
//				ResultSet resultGroups = GroupsTable.getInstance().executeSelect("", "group_id, group_name, group_details ", "FROM "+groupsTable+" JOIN "+coursesTable
//					+" ON "+groupsTable+".course_id = "+coursesTable+".course_id", " WHERE "+groupsTable+".course_id = "+course_id, "", "", "");
				ResultSet resultGroups = GroupsTable.getInstance().executeSelect("", groupsTable+".group_id, group_name, group_details, courses_execution_start_date ", 
					"FROM "+groupsTable+" JOIN "+coursesTable+" ON "+groupsTable+".course_id = "+coursesTable+".course_id JOIN "+coursesExecutionsTable+" ON "+groupsTable
					+".group_id = "+coursesExecutionsTable+".group_id "," WHERE ("+groupsTable+".course_id = "+course_id+") AND ("+coursesExecutionsTable+".executor_id IS NULL)", "", "", "");
				try {
					while (resultGroups.next()) {
						if (resultGroups.getString(4) == null)
							date = null;
						else
							date = LocalDate.parse(resultGroups.getString(4), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
						TreeItem<User> item = new TreeItem<User>(new User(resultGroups.getInt(1),course.getCourse_id(),resultGroups.getString(2),"Group",date,
								resultGroups.getString(3),course.getCourse_name(),course.getAuthor_name(),"","","",0,"",0,""));
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
//				ResultSet resultGroups = GroupsTable.getInstance().executeSelect("", groupsTable+".group_id, group_name, group_details ", "FROM "+groupsTable+" JOIN "+coursesTable
//					+" ON "+groupsTable+".course_id = "+coursesTable+".course_id JOIN "+studentsTable+" ON "+groupsTable+".group_id = "+studentsTable+".group_id ",
//					" WHERE ("+groupsTable+".course_id = "+course_id+") AND ("+studentsTable+".user_id = "+mainApp.getLogin().getUser_id()+")", "", "", "");
				ResultSet resultGroups = GroupsTable.getInstance().executeSelect("",  groupsTable+".group_id, group_name, group_details, courses_execution_start_date ", 
					"FROM "+groupsTable+" JOIN "+coursesTable+" ON "+groupsTable+".course_id = "+coursesTable+".course_id JOIN "+coursesExecutionsTable+" ON "+groupsTable
					+".group_id = "+coursesExecutionsTable+".group_id JOIN "+studentsTable+" ON "+groupsTable+".group_id = "+studentsTable+".group_id ", 
					" WHERE ("+groupsTable+".course_id = "+course_id+") AND ("+coursesExecutionsTable+".executor_id IS NULL) AND ("+studentsTable+".user_id = "+mainApp.getLogin().getUser_id()+")", "", "", "");
				try {
					while (resultGroups.next()) {
						if (resultGroups.getString(4) == null)
							date = null;
						else
							date = LocalDate.parse(resultGroups.getString(4), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
						TreeItem<User> item = new TreeItem<User>(new User(resultGroups.getInt(1),course.getCourse_id(),resultGroups.getString(2),"Group",date,
								resultGroups.getString(3),course.getCourse_name(),course.getAuthor_name(),"","","",0,"",0,""));
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
//				ResultSet resultStudents = StudentsTable.getInstance().executeSelect("", studentsTable+".user_id, CONCAT(user_firstname, ' ', user_lastname) ", "FROM "+studentsTable+
//					" JOIN "+usersTable+" ON "+studentsTable+".user_id = "+usersTable+".user_id", " WHERE "+studentsTable+".group_id = "+item.getValue().getUser_id(), "", "", "");
				ResultSet resultStudents = UsersTable.getInstance().executeSelect("", usersTable+".user_id, CONCAT(user_firstname, ' ', user_lastname), courses_execution_start_date ", 
					"FROM "+usersTable+" JOIN "+coursesExecutionsTable +" ON "+usersTable+".user_id = "+coursesExecutionsTable+".executor_id", 
					" WHERE ("+coursesExecutionsTable+".course_id = "+course_id+") AND ("+coursesExecutionsTable+".group_id = "+item.getValue().getUser_id()+")", "", "", "");
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
//				resultUsers = UsersTable.getInstance().executeSelect("", usersTable+".user_id, CONCAT(user_firstname, ' ', user_lastname) ", "FROM "+usersTable+" JOIN "+coursesExecutionsTable
//					+" ON "+usersTable+".user_id = "+coursesExecutionsTable+".executor_id", " WHERE ("+coursesExecutionsTable+".course_id = "+course_id+") AND ("+usersTable+".user_id NOT IN ("
//					+"SELECT "+usersTable+".user_id FROM "+usersTable+" JOIN "+studentsTable+" ON ("+usersTable+".user_id = "+studentsTable+".user_id) JOIN "+groupsTable+" ON ("
//					+studentsTable+".group_id = "+groupsTable+".group_id) WHERE "+groupsTable+".course_id = "+course_id+"))", "", "", "");
				resultUsers = UsersTable.getInstance().executeSelect("", usersTable+".user_id, CONCAT(user_firstname, ' ', user_lastname), courses_execution_start_date ", "FROM "+usersTable
					+" JOIN "+coursesExecutionsTable+" ON "+usersTable+".user_id = "+coursesExecutionsTable+".executor_id", " WHERE ("+coursesExecutionsTable+".course_id = "+course_id
					+") AND ("+coursesExecutionsTable+".group_id IS NULL)", "", "", "");
			}
			else {
//				resultUsers = UsersTable.getInstance().executeSelect("", usersTable+".user_id, CONCAT(user_firstname, ' ', user_lastname) ", "FROM "+usersTable+" JOIN "+coursesExecutionsTable
//					+" ON "+usersTable+".user_id = "+coursesExecutionsTable+".executor_id", " WHERE ("+coursesExecutionsTable+".course_id = "+course_id+") AND ("+usersTable+".user_id = "
//					+mainApp.getLogin().getUser_id()+") AND ("+usersTable+".user_id NOT IN ( SELECT "+usersTable+".user_id FROM "+usersTable+" JOIN "+studentsTable+" ON ("+usersTable+".user_id = "
//					+studentsTable+".user_id) JOIN "+groupsTable+" ON ("+studentsTable+".group_id = "+groupsTable+".group_id) WHERE "+groupsTable+".course_id = "+course_id+"))", "", "", "");
				resultUsers = UsersTable.getInstance().executeSelect("", usersTable+".user_id, CONCAT(user_firstname, ' ', user_lastname), courses_execution_start_date ", "FROM "+usersTable
					+" JOIN "+coursesExecutionsTable+" ON "+usersTable+".user_id = "+coursesExecutionsTable+".executor_id", " WHERE ("+coursesExecutionsTable+".course_id = "+course_id
					+") AND ("+coursesExecutionsTable+".group_id IS NULL) AND ("+usersTable+".user_id = "+mainApp.getLogin().getUser_id()+")", "", "", "");
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
	
	// Insert or update courses
	private void updateCourse(Course course) {
		if (course.getCourse_id() == 0)
			if (CoursesTable.getInstance().insert(course)) {
				String coursesTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesTable_id.getValue());
				ResultSet result = CoursesTable.getInstance().executeSelect("", "course_id", "FROM "+coursesTable, 
						"WHERE (author_id = "+course.getAuthor_id()+") AND (course_name = \'"+course.getCourse_name()+"\')", "", "", "");
				try {
					if (result.next()) {
						course.setCourse_id(result.getInt(1));
						listCourses.add(course);
						tableCourses.getSelectionModel().select(course);
						User user = comboAuthors.getValue();
						getListAuthors(listAuthors);
						comboAuthors.setValue(user);
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
			if (CoursesTable.getInstance().update(course.getCourse_id(),course))
				updateInformation(course);
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
	private void updateInformation(Course course) {
		if (course != null) {
			textCourseGroupPrice.setText(course.getCourse_group_price()+"");
			textCourseIndividualPrice.setText(course.getCourse_individual_price()+"");
			String view = course.getCourse_details();
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
	private void updateGroup(Group group) {
		Alert splitAlert;
		if (group.getGroup_id() == 0)
			if (GroupsTable.getInstance().insert(group)) {
				String groupsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.GroupsTable_id.getValue());
				ResultSet result = GroupsTable.getInstance().executeSelect("", "group_id", "FROM "+groupsTable, 
						"WHERE group_name = \'"+group.getGroup_name()+"\' AND course_id = "+group.getCourse_id(), "", "", "");
				try {
					if (result.next()) {
						group.setGroup_id(result.getInt(1));
						TreeItem<User> item = new TreeItem<User>(new User(group.getGroup_id(),group.getCourse_id(),group.getGroup_name(),"Group",LocalDate.now(),
							group.getGroup_details(),group.getCourse_name(),group.getAuthor_name(),"","","",0,"",0,""));
						Execution execution = new Execution(0,course_id,0,group.getGroup_id(),item.getValue().getUser_birthday(),null,0,0,
								group.getCourse_name(),group.getGroup_name());
						if (CoursesExecutionsTable.getInstance().insert(execution)) {
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
			else {
				splitAlert = new Alert(AlertType.ERROR);
				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setContentText(Strings.InsertError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.showAndWait();
			}
		else
			if (GroupsTable.getInstance().update(group.getGroup_id(),group)) {
				TreeItem<User> item = tableStudents.getSelectionModel().getSelectedItem();
				item.getValue().setUser_firstname(group.getGroup_name());
				item.getValue().setUser_address(group.getGroup_details());
			}
			else {
				splitAlert = new Alert(AlertType.ERROR);
				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setContentText(Strings.UpdateError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.showAndWait();
			}
	}
	
	// Insert student
	private void updateStudent(User user,TreeItem<User> group) {
		Alert splitAlert;
		String coursesExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesExecutionsTable_id.getValue());
		String lessonsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsExecutionsTable_id.getValue());
		String lessonsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsTable_id.getValue());
		Execution execution;
		if (group.getValue().getUser_lastname().equals("Group")) {
			user.setUser_lastname("Student");
			Student student = new Student(0,group.getValue().getUser_id(),user.getUser_id(),group.getValue().getUser_firstname(),
				group.getValue().getUser_phone(),user.getUser_firstname());
			if (!StudentsTable.getInstance().insert(student)) {
				splitAlert = new Alert(AlertType.ERROR);
				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setContentText(Strings.InsertError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.showAndWait();
				return;
			}
			else {
				ResultSet resultSet = CoursesExecutionsTable.getInstance().executeSelect("", "courses_execution_id, course_id, group_id, courses_execution_start_date, "+
						"courses_execution_end_date, courses_execution_status, courses_execution_result ", "FROM "+coursesExecutionsTable, 
						"WHERE (course_id = "+course_id+") AND (group_id = "+group.getValue().getUser_id()+")", "", "", "");
				try {
					if (resultSet.next()) {
						LocalDate startDate, endDate;
						if (resultSet.getString(4) == null)
							startDate = null;
						else
							startDate = LocalDate.parse(resultSet.getString(4), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
						if (resultSet.getString(5) == null)
							endDate = null;
						else
							endDate = LocalDate.parse(resultSet.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
						user.setUser_birthday(startDate);
						execution = new Execution(0,course_id,user.getUser_id(),group.getValue().getUser_id(),startDate,endDate,resultSet.getInt(6),
								resultSet.getInt(7),tableCourses.getSelectionModel().getSelectedItem().getCourse_name(),user.getUser_firstname());
						resultSet = LessonsExecutionsTable.getInstance().executeSelect("", "lessons_execution_id, "+lessonsExecutionsTable+".lesson_id, group_id, "
								+ "lessons_execution_start_date, lessons_execution_end_date,  lessons_execution_status,  lessons_execution_result, lesson_name ", 
								"FROM "+lessonsExecutionsTable+" JOIN "+ lessonsTable+" ON "+lessonsExecutionsTable+".lesson_id = "+lessonsTable+".lesson_id", 
								" WHERE ("+lessonsTable+".course_id = "+course_id+") AND (group_id = "+group.getValue().getUser_id()+") AND ("+lessonsExecutionsTable+".executor_id IS NULL)",
								"", "", "");
						ArrayList<Execution> executions = new ArrayList<>();
						while (resultSet.next()) {
							if (resultSet.getString(4) == null)
								startDate = null;
							else
								startDate = LocalDate.parse(resultSet.getString(4), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
							if (resultSet.getString(5) == null)
								endDate = null;
							else
								endDate = LocalDate.parse(resultSet.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
							executions.add(new Execution(0,resultSet.getInt(2),user.getUser_id(),resultSet.getInt(3),startDate,endDate,resultSet.getInt(6),
									resultSet.getInt(7),resultSet.getString(8),user.getUser_firstname()));
						}
						executions.forEach(value -> {
							if (!LessonsExecutionsTable.getInstance().insert(value)) {
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
						// e.printStackTrace();
						splitAlert = new Alert(AlertType.ERROR);
						((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
						splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
						splitAlert.setHeaderText(Strings.InitError.getValue(Indexes.AppLanguage_index.getValue()));
						splitAlert.setContentText(Strings.SelectError.getValue(Indexes.AppLanguage_index.getValue()));
						splitAlert.showAndWait();
						return;						
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
					return;
				}
			};
		}
		else {
			user.setUser_lastname("User");
			user.setUser_birthday(LocalDate.now());
			execution = new Execution(0,course_id,user.getUser_id(),0,user.getUser_birthday(),null,0,0,
					tableCourses.getSelectionModel().getSelectedItem().getCourse_name(),user.getUser_firstname());
		}
		if (CoursesExecutionsTable.getInstance().insert(execution)) {
			TreeItem<User> addUser = new TreeItem<User>(user);
			group.getChildren().add(addUser);
			tableStudents.getSelectionModel().select(addUser);
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
	
	// Remove group student
	private void removeStudent(TreeItem<User> user) {
		Alert splitAlert;
		String studentsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.StudentsTable_id.getValue());
		ResultSet resultSet = StudentsTable.getInstance().executeSelect("", "student_id ", "FROM "+studentsTable, "WHERE user_id = "+user.getValue().getUser_id()
				+" AND group_id = "+user.getParent().getValue().getUser_id(),"","","");
		try {
			if (resultSet.next()) {
				if (StudentsTable.getInstance().delete(resultSet.getInt(1))) {
					removeUser(user);
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

	// Remove individual user
	private void removeUser(TreeItem<User> user) {
		Alert splitAlert;
		String coursesExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesExecutionsTable_id.getValue());
		String tasksTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.TasksTable_id.getValue());
		String lessonsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsTable_id.getValue());
		ResultSet resultSet = CoursesExecutionsTable.getInstance().executeSelect("", "courses_execution_id ", "FROM "+coursesExecutionsTable, 
				"WHERE (course_id = "+course_id+") AND (executor_id = "+user.getValue().getUser_id()+")", "", "", "");
		try {
			if (resultSet.next())
				if (CoursesExecutionsTable.getInstance().delete(resultSet.getInt(1))) {
					if (LessonsExecutionsTable.getInstance().executeDelete(" WHERE (executor_id = "+user.getValue().getUser_id()+") AND (lesson_id IN (SELECT "
							+lessonsTable+".lesson_id FROM "+lessonsTable+" WHERE "+lessonsTable+".course_id = "+course_id+"))")) {
						if (TasksExecutionsTable.getInstance().executeDelete("WHERE (executor_id = "+user.getValue().getUser_id()+") AND (task_id IN (SELECT "
								+tasksTable+".task_id FROM "+tasksTable+" WHERE "+tasksTable+".lesson_id IN (SELECT "+lessonsTable+".lesson_id FROM "+lessonsTable
								+" WHERE "+lessonsTable+".course_id = "+course_id+")))")) {
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
			if ((CoursesExecutionsTable.getInstance().executeUpdate("", "SET courses_execution_start_date = "+start_date+", courses_execution_end_date = "+end_date,
					" WHERE course_id = "+course_id+" AND group_id = "+user.getUser_id())) && (CoursesExecutionsTable.getInstance().update(execution.getExecution_id(),execution))) {
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
		else if (CoursesExecutionsTable.getInstance().update(execution.getExecution_id(),execution)) {
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
	
	public TableView<Course> getTableCourses() {
		return tableCourses;
	}

}
