package application.view;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import application.InAltoSchool;
import application.Indexes;
import application.Strings;
import application.database.CoursesExecutionsTable;
import application.database.CoursesTable;
import application.database.InAltoSchoolDatabase;
import application.database.ProjectsExecutionsTable;
import application.database.ProjectsTable;
import application.database.UsersPaymentsTable;
import application.database.UsersTable;
import application.model.Course;
import application.model.CourseInfo;
import application.model.Login;
import application.model.Project;
import application.model.User;
import application.model.UserPayment;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class StudentsSplitPaneController {

	// Main application reference 
	private InAltoSchool mainApp;
	
	// Observable lists
	ObservableList<User> listUsers;
	ObservableList<CourseInfo> listCourses;
	ObservableList<UserPayment> listPayments;
	ObservableList<CourseInfo> listProjects;
	
	// Current IDs
	private int user_id;
	
	// Children elements references	
	@FXML
	private Button detailsButton, coursesToButton, projectsToButton, newButton, editButton, deleteButton;
	
	@FXML
	private Label labelUserTitle, labelCourseTitle,  labelCoursePayments, labelProjectTitle, labelInformation;
	
	@FXML
	private TableView<User> tableUsers;
	
	@FXML
	private TableView<CourseInfo> tableCourses;
	
	@FXML
	private TableView<UserPayment> tablePayments;
	
	@FXML
	private TableView<CourseInfo> tableProjects;
	
	@FXML
	private TableColumn<User, String> columnFirstName, columnLastName;
	
	@FXML
	private TableColumn<User, LocalDate> columnBirthday;
	
	@FXML
	private TableColumn<CourseInfo, String> columnCourseName, columnCourseGroup;
	
	@FXML
	private TableColumn<CourseInfo, LocalDate> columnCourseDate;
	
	@FXML
	private TableColumn<CourseInfo, Integer> columnCourseLessons;
	
	@FXML
	private TableColumn<UserPayment, String> columnPaymentName;
	
	@FXML
	private TableColumn<UserPayment, LocalDate> columnPaymentDate;
	
	@FXML
	private TableColumn<UserPayment, Double> columnPaymentSum;
	
	@FXML
	private TableColumn<CourseInfo, String> columnProjectName;
	
	@FXML
	private TableColumn<CourseInfo, LocalDate> columnProjectDate;
	
	@FXML
	private void initialize() {
		// Set Lists
		user_id = 0;
		listUsers = FXCollections.observableArrayList();
		listCourses = FXCollections.observableArrayList();
		listPayments = FXCollections.observableArrayList();
		listProjects = FXCollections.observableArrayList();
		tableUsers.setItems(listUsers);
		tableCourses.setItems(listCourses);
		tablePayments.setItems(listPayments);
		tableProjects.setItems(listProjects);
		columnFirstName.setCellValueFactory(cell -> cell.getValue().user_firstnameProperty());
		columnLastName.setCellValueFactory(cell -> cell.getValue().user_lastnameProperty());
		columnBirthday.setCellValueFactory(cell -> cell.getValue().user_birthdayProperty());
		columnCourseName.setCellValueFactory(cell -> cell.getValue().course_nameProperty());
		columnCourseGroup.setCellValueFactory(cell -> cell.getValue().course_groupProperty());
		columnCourseDate.setCellValueFactory(cell -> cell.getValue().course_dateProperty());
		columnCourseLessons.setCellValueFactory(cell -> cell.getValue().course_lessonsProperty().asObject());
		columnPaymentName.setCellValueFactory(cell -> cell.getValue().course_nameProperty());
		columnPaymentDate.setCellValueFactory(cell -> cell.getValue().users_payment_dateProperty());
		columnPaymentSum.setCellValueFactory(cell -> cell.getValue().users_payment_sumProperty().asObject());
		columnProjectName.setCellValueFactory(cell -> cell.getValue().course_nameProperty());
		columnProjectDate.setCellValueFactory(cell -> cell.getValue().course_dateProperty());
		// Set Handlers
		setHandlers();	
	}
	
	// Button Events
	@FXML
	private void detailsButton() {
		User user = tableUsers.getSelectionModel().getSelectedItem();
		Stage dialogStage = new Stage();
		StudentDialog studentDialog = new StudentDialog(dialogStage);
		studentDialog.getController().setUser(user);
		dialogStage.setTitle(Strings.StudentDialogTitle.getValue(Indexes.AppLanguage_index.getValue()));
		Scene scene = new Scene(studentDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
	}
	
	@FXML
	private void coursesToButton() {
		CourseInfo courseInfo = tableCourses.getSelectionModel().getSelectedItem();
		Course course;
		if (courseInfo != null) {
			course = new Course(courseInfo.getCourse_id(),courseInfo.getAuthor_id(),courseInfo.getCourse_name(),courseInfo.getCourse_details(),
					courseInfo.getCourse_group_price(),courseInfo.getCourse_individual_price(),courseInfo.getAuthor_name());
		}
		else {
			course = null;
		}
		if ((mainApp.getLogin() != null) && (course != null) && (isAccessCourse(mainApp.getLogin(),course))) {
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
		CourseInfo courseInfo = tableProjects.getSelectionModel().getSelectedItem();
		Project project;
		if (courseInfo != null) {
			project = new Project(courseInfo.getCourse_id(),courseInfo.getAuthor_id(),courseInfo.getCourse_name(),courseInfo.getCourse_details(),
					courseInfo.getAuthor_name());
		}
		else {
			project = null;
		}
		if ((mainApp.getLogin() != null) && (project != null) && (isAccessProject(mainApp.getLogin(),project))) {
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
	
	@FXML
	private void newButton() {
		User user = tableUsers.getSelectionModel().getSelectedItem();
		UserPayment userPayment = new UserPayment(0,user.getUser_id(),0,LocalDate.now(),0,"",user.getUser_firstname()+" "+user.getUser_lastname(),mainApp.getLogin().getUser_id());
		Stage dialogStage = new Stage();
		UserPaymentDialog userPaymentDialog = new UserPaymentDialog(dialogStage);
		if (mainApp.getLogin().getRole_name().equals("Administrator"))
			userPaymentDialog.getController().setAdmin(true);
		userPaymentDialog.getController().setUserPayment(userPayment);
        Scene scene = new Scene(userPaymentDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (userPaymentDialog.getController().isAddButton()) {
        	updatePayment(userPaymentDialog.getController().getUserPayment());
        }
	}
	
	@FXML
	private void editButton() {
		UserPayment userPayment = tablePayments.getSelectionModel().getSelectedItem();
		Stage dialogStage = new Stage();
		UserPaymentDialog userPaymentDialog = new UserPaymentDialog(dialogStage);
		if (mainApp.getLogin().getRole_name().equals("Administrator"))
			userPaymentDialog.getController().setAdmin(true);
		userPaymentDialog.getController().setUserPayment(userPayment);
        Scene scene = new Scene(userPaymentDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (userPaymentDialog.getController().isAddButton()) {
        	updatePayment(userPaymentDialog.getController().getUserPayment());
        }
	}
	
	@FXML
	private void deleteButton() {
		UserPayment userPayment = tablePayments.getSelectionModel().getSelectedItem();
		String date;
		if (userPayment.getUsers_payment_date() != null)
			date = userPayment.getUsers_payment_date().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		else
			date = "";
		Alert splitAlert = new Alert(AlertType.CONFIRMATION);
		((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
		splitAlert.setTitle(Strings.ConfirmTitle.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setHeaderText(Strings.ConfirmDeleteHeader.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setContentText(Strings.ConfirmDeleteContent.getValue(Indexes.AppLanguage_index.getValue())+
				userPayment.getCourse_name()+", "+date+", "+userPayment.getUsers_payment_sum()+"?");
		Optional<ButtonType> result = splitAlert.showAndWait();
		if (result.get() == ButtonType.OK) {
			if (UsersPaymentsTable.getInstance().delete(userPayment.getUsers_payment_id())) {
				listPayments.remove(userPayment);
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
	
	// ChangeLanguage for elements
	public void changeLanguage () {
		labelUserTitle.setText(Strings.LabelStudentTitle.getValue(Indexes.AppLanguage_index.getValue()));
		labelCourseTitle.setText(Strings.LabelCourseTitle.getValue(Indexes.AppLanguage_index.getValue()));
		labelCoursePayments.setText(Strings.LabelCoursePayments.getValue(Indexes.AppLanguage_index.getValue()));
		labelProjectTitle.setText(Strings.LabelProjectTitle.getValue(Indexes.AppLanguage_index.getValue()));
		labelInformation.setText(Strings.LabelInformation.getValue(Indexes.AppLanguage_index.getValue()));
		columnFirstName.setText(Strings.ColumnFirstName.getValue(Indexes.AppLanguage_index.getValue()));
		columnLastName.setText(Strings.ColumnLastName.getValue(Indexes.AppLanguage_index.getValue()));
		columnBirthday.setText(Strings.ColumnBirthday.getValue(Indexes.AppLanguage_index.getValue()));
		columnCourseName.setText(Strings.ColumnCourseName.getValue(Indexes.AppLanguage_index.getValue()));
		columnCourseGroup.setText(Strings.ColumnCourseGroup.getValue(Indexes.AppLanguage_index.getValue()));
		columnCourseDate.setText(Strings.ColumnCourseStudentDate.getValue(Indexes.AppLanguage_index.getValue()));
		columnCourseLessons.setText(Strings.ColumnCourseLessons.getValue(Indexes.AppLanguage_index.getValue()));
		columnPaymentName.setText(Strings.ColumnCourseName.getValue(Indexes.AppLanguage_index.getValue()));
		columnProjectName.setText(Strings.ColumnProjectName.getValue(Indexes.AppLanguage_index.getValue()));
		columnProjectDate.setText(Strings.ColumnProjectStudentDate.getValue(Indexes.AppLanguage_index.getValue()));
		detailsButton.setText(Strings.DetailsButton.getValue(Indexes.AppLanguage_index.getValue()));
		coursesToButton.setText(Strings.CoursesToButton.getValue(Indexes.AppLanguage_index.getValue()));
		projectsToButton.setText(Strings.ProjectsToButton.getValue(Indexes.AppLanguage_index.getValue()));
		newButton.setText(Strings.NewButton.getValue(Indexes.AppLanguage_index.getValue()));
		editButton.setText(Strings.EditButton.getValue(Indexes.AppLanguage_index.getValue()));
		deleteButton.setText(Strings.DeleteButton.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// ChangeLogin for elements
	public void changeLogin () {
		tableUsers.getSelectionModel().clearSelection();
		if (getMainApp().getLogin() ==  null) {
			detailsButton.setDisable(true);
			coursesToButton.setDisable(false);
			projectsToButton.setDisable(false);
			newButton.setDisable(true);
			editButton.setDisable(true);
			deleteButton.setDisable(true);
		}
		else {
			detailsButton.setDisable(true);
			coursesToButton.setDisable(false);
			projectsToButton.setDisable(false);
			newButton.setDisable(true);
			editButton.setDisable(true);
			deleteButton.setDisable(true);
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
					user_id = newValue.getUser_id();
					if ((mainApp.getLogin() != null) && ((mainApp.getLogin().getRole_name().equals("Administrator")) || 
						((mainApp.getLogin().getRole_name().equals("Teacher")) && (newValue.getRole_name().equals("Student"))) ||	
						(mainApp.getLogin().getUser_id() == newValue.getUser_id()))) {
						updateInformation(newValue);
						detailsButton.setDisable(false);
						if ((mainApp.getLogin().getRole_name().equals("Administrator")) || 
							(mainApp.getLogin().getRole_name().equals("Teacher"))) {
							newButton.setDisable(false);
							editButton.setDisable(true);
							deleteButton.setDisable(true);
						}
						else {
							newButton.setDisable(true);
							editButton.setDisable(true);
							deleteButton.setDisable(true);
						}
					}
					else {
						updateInformation(null);
						detailsButton.setDisable(true);
						newButton.setDisable(true);
						editButton.setDisable(true);
						deleteButton.setDisable(true);
					}		
				}
				else {
					updateInformation(null);
					detailsButton.setDisable(true);
					newButton.setDisable(true);
					editButton.setDisable(true);
					deleteButton.setDisable(true);
				}
			}
		});
		columnBirthday.setCellFactory(new Callback<TableColumn<User,LocalDate>, TableCell<User,LocalDate>>() {
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
		// Courses list
		columnCourseGroup.setCellFactory(new Callback<TableColumn<CourseInfo,String>, TableCell<CourseInfo,String>>() {	
			@Override
			public TableCell<CourseInfo, String> call(TableColumn<CourseInfo, String> param) {
				// TODO Auto-generated method stub
				TableCell<CourseInfo, String> tableCell = new TableCell<CourseInfo, String>() {
					@Override
					protected void updateItem(String value, boolean empty) {
						super.updateItem(value, empty);    
						setAlignment(Pos.CENTER);
						if (value != null)
							setText(value);
						else
							setText(null);
					}
				};
				return tableCell;
			}
		});
		columnCourseDate.setCellFactory(new Callback<TableColumn<CourseInfo,LocalDate>, TableCell<CourseInfo,LocalDate>>() {
			@Override
			public TableCell<CourseInfo, LocalDate> call(TableColumn<CourseInfo, LocalDate> param) {
				// TODO Auto-generated method stub
				TableCell<CourseInfo,LocalDate> tableCell = new TableCell<CourseInfo,LocalDate>() {
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
		columnCourseLessons.setCellFactory(new Callback<TableColumn<CourseInfo,Integer>, TableCell<CourseInfo,Integer>>() {	
			@Override
			public TableCell<CourseInfo, Integer> call(TableColumn<CourseInfo, Integer> param) {
				// TODO Auto-generated method stub
				TableCell<CourseInfo,Integer> tableCell = new TableCell<CourseInfo,Integer>() {
					@Override
					protected void updateItem(Integer value, boolean empty) {
						super.updateItem(value, empty);    
						setAlignment(Pos.CENTER);
						if (value != null)
							setText(value.toString());
						else
							setText(null);
					}
				};
				return tableCell;
			}
		});
		// User Payments List
		tablePayments.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserPayment>() {
			@Override
			public void changed(ObservableValue<? extends UserPayment> observable, UserPayment oldValue, UserPayment newValue) {
				// TODO Auto-generated method stub
				if (newValue != null) {
					if ((mainApp.getLogin() != null) && ((mainApp.getLogin().getRole_name().equals("Administrator")) || 
						((mainApp.getLogin().getRole_name().equals("Teacher")) && (mainApp.getLogin().getUser_id() == newValue.getAuthor_id())))) {
						editButton.setDisable(false);
						deleteButton.setDisable(false);
					}
					else {
						editButton.setDisable(true);
						deleteButton.setDisable(true);
					}		
				}
				else {
					updateInformation(null);
				}
			}
		});
		columnPaymentDate.setCellFactory(new Callback<TableColumn<UserPayment,LocalDate>, TableCell<UserPayment,LocalDate>>() {
			@Override
			public TableCell<UserPayment, LocalDate> call(TableColumn<UserPayment, LocalDate> param) {
				// TODO Auto-generated method stub
				TableCell<UserPayment, LocalDate> tableCell = new TableCell<UserPayment, LocalDate>() {
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
		columnPaymentSum.setCellFactory(new Callback<TableColumn<UserPayment,Double>, TableCell<UserPayment,Double>>() {
			@Override
			public TableCell<UserPayment, Double> call(TableColumn<UserPayment, Double> param) {
				// TODO Auto-generated method stub
				TableCell<UserPayment, Double> tableCell = new TableCell<UserPayment, Double>() {
					@Override
					protected void updateItem(Double value, boolean empty) {
						super.updateItem(value, empty);    
						setAlignment(Pos.CENTER_RIGHT);
						if (value != null)
							setText(value.toString());
						else
							setText(null);
					}
				};
				return tableCell;
			}
		});
		// Projects list
		columnProjectDate.setCellFactory(new Callback<TableColumn<CourseInfo,LocalDate>, TableCell<CourseInfo,LocalDate>>() {	
			@Override
			public TableCell<CourseInfo, LocalDate> call(TableColumn<CourseInfo, LocalDate> param) {
				// TODO Auto-generated method stub
				TableCell<CourseInfo, LocalDate> tableCell = new TableCell<CourseInfo, LocalDate>() {
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
				"", "", "", "");
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
	
	private void getListCourses(ObservableList<CourseInfo> listCourses) {
		LocalDate date;
		String group;
		String coursesExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesExecutionsTable_id.getValue());
		String lessonsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsExecutionsTable_id.getValue());
		String coursesTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesTable_id.getValue());
		String lessonsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		String groupsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.GroupsTable_id.getValue());
		if (user_id != 0) {
			ResultSet result = CoursesTable.getInstance().executeSelect("", coursesTable+".course_id, "+coursesTable+".author_id, course_name, course_details, "
					+"course_group_price, course_individual_price, CONCAT(user_firstname, ' ', user_lastname), group_name, courses_execution_start_date, COUNT("
					+lessonsExecutionsTable+".lessons_execution_id) ", "FROM "+coursesTable+" JOIN "+usersTable+" ON ("+coursesTable+".author_id = "+usersTable
					+".user_id) JOIN "+coursesExecutionsTable+" ON ("+coursesExecutionsTable+".course_id = "+coursesTable+".course_id) LEFT JOIN "+groupsTable
					+" ON ("+coursesExecutionsTable+".group_id = "+groupsTable+".group_id) JOIN "+lessonsTable+" ON ("+lessonsTable+".course_id = "+coursesTable
					+".course_id) JOIN "+lessonsExecutionsTable+" ON ("+lessonsExecutionsTable+".lesson_id = "+lessonsTable+".lesson_id)", 
					" WHERE ("+coursesExecutionsTable+".executor_id = "+user_id+") AND ("+lessonsExecutionsTable+".executor_id = "+user_id+") ", "GROUP BY course_id ", "", "");
			try {
				while(result.next()) {
					if (result.getString(9) == null)
						date = null;
					else
						date = LocalDate.parse(result.getString(9), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					group = result.getString(8);
					if (group == null)
						group = " - ";
					listCourses.add(new CourseInfo(result.getInt(1),result.getInt(2),result.getString(3),result.getString(4),result.getDouble(5),result.getDouble(6),result.getString(7),
						group,date,result.getInt(10)));
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
	
	private void getListPayments(ObservableList<UserPayment> listPayments) {
		LocalDate date;
		String userPaymentsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersPaymentsTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		String coursesTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesTable_id.getValue());
		if (user_id != 0) {
			ResultSet result = UsersPaymentsTable.getInstance().executeSelect("", userPaymentsTable+".users_payment_id, "+userPaymentsTable+".user_id, "
					+userPaymentsTable+".course_id, users_payment_date, users_payment_sum, course_name, CONCAT(user_firstname, ' ', user_lastname), "
					+coursesTable+".author_id ", "FROM "+userPaymentsTable+" JOIN "+coursesTable+" ON ("+userPaymentsTable+".course_id = "+coursesTable+".course_id) JOIN "
					+usersTable+" ON ("+userPaymentsTable+".user_id = "+usersTable+".user_id) ", "WHERE "+userPaymentsTable+".user_id = "+user_id, "", "", "");
			try {
				while(result.next()) {
					if (result.getString(4) == null)
						date = null;
					else
						date = LocalDate.parse(result.getString(4), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					listPayments.add(new UserPayment(result.getInt(1),result.getInt(2),result.getInt(3),date,result.getDouble(5),result.getString(6),
						result.getString(7),result.getInt(8)));
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
	
	private void getListProjects(ObservableList<CourseInfo> listProjects) {
		LocalDate date;
		String projectsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.ProjectsExecutionsTable_id.getValue());
		String projectsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.ProjectsTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		if (user_id != 0) {
			ResultSet result = ProjectsTable.getInstance().executeSelect("", projectsTable+".project_id, "+projectsTable+".author_id, project_name, project_details, "
					+ " CONCAT(user_firstname, ' ', user_lastname), projects_execution_start_date ", "FROM "+projectsTable+" JOIN "+usersTable+" ON ("+projectsTable+".author_id = "+usersTable+".user_id) JOIN "
					+projectsExecutionsTable+" ON "+projectsExecutionsTable+".project_id = "+projectsTable+".project_id ", 
					"WHERE "+projectsExecutionsTable+".executor_id = "+user_id, "", "", "");
			try {
				while(result.next()) {
					if (result.getString(6) == null)
						date = null;
					else
						date = LocalDate.parse(result.getString(6), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					listProjects.add(new CourseInfo(result.getInt(1),result.getInt(2),result.getString(3),result.getString(4),0,0,result.getString(5),"",
						date,0));
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
			listCourses.clear();
			listPayments.clear();
			listProjects.clear();
			getListCourses(listCourses);
			getListPayments(listPayments);
			getListProjects(listProjects);
		}
		else {
			listCourses.clear();
			listPayments.clear();
			listProjects.clear();
		}
	}
	
	// Insert or update User Payment
	private void updatePayment(UserPayment userPayment) {
		if (userPayment.getUsers_payment_id() == 0)
			if (UsersPaymentsTable.getInstance().insert(userPayment)) {
				String usersPaymentsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersPaymentsTable_id.getValue());
				String date;
				if (userPayment.getUsers_payment_date() == null)
					date = "NULL";
				else
					date = "\'"+(userPayment.getUsers_payment_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))+"\'";
				ResultSet result = UsersPaymentsTable.getInstance().executeSelect("", "users_payment_id", "FROM "+usersPaymentsTable, 
						"WHERE (user_id = "+userPayment.getUser_id()+") AND (course_id = "+userPayment.getCourse_id()+") AND (users_payment_date = "+date+")", "", "", "");
				try {
					if (result.next()) {
						userPayment.setUsers_payment_id((result.getInt(1)));
						listPayments.add(userPayment);
						tablePayments.getSelectionModel().select(userPayment);
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
			if (!UsersPaymentsTable.getInstance().update(userPayment.getUsers_payment_id(),userPayment)) {
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

}
