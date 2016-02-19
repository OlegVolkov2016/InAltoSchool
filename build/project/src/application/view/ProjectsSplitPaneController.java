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
import application.database.ProjectsExecutionsTable;
import application.database.ProjectsTable;
import application.database.TasksExecutionsTable;
import application.database.UsersTable;
import application.model.Execution;
import application.model.Login;
import application.model.Project;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ProjectsSplitPaneController {

	// Main application reference 
	private InAltoSchool mainApp;
	
	// Observable lists
	ObservableList<Project> listProjects;
	ObservableList<User> listAuthors, listStudents;
	
	// Current IDs
	private int author_id;
	private int project_id;
	
	// Children elements references	
	@FXML
	private Button viewButton, addButton, removeButton, statusButton, newButton, editButton, deleteButton, tasksButton, loginButton;
	
	@FXML
	private Label labelProjectTitle, labelAuthors, labelInformation, labelDetails, labelStudents;
	
	@FXML
	private ComboBox<User> comboAuthors;
	
	@FXML
	private TableView<Project> tableProjects;
	
	@FXML
	private TableColumn<Project, String> columnProjectName, columnProjectAuthor;
	
	@FXML
	private TableView<User> tableStudents;
	
	@FXML
	private TableColumn<User, String> columnStudentName;
	
	@FXML
	private TableColumn<User, LocalDate> columnStudentDate;
	
	@FXML
	private WebView textDetails;
	
	@FXML
	private void initialize() {
		// Set Lists
		author_id = 0;
		project_id = 0;
		listAuthors = FXCollections.observableArrayList();
		listProjects = FXCollections.observableArrayList();
		listStudents = FXCollections.observableArrayList();
		getListAuthors(listAuthors);
		comboAuthors.setItems(listAuthors);
		comboAuthors.setValue(listAuthors.get(author_id));
		getListProjects(listProjects);
		tableProjects.setItems(listProjects);
		columnProjectName.setCellValueFactory(cell -> cell.getValue().project_nameProperty());
		columnProjectAuthor.setCellValueFactory(cell -> cell.getValue().author_nameProperty());
		tableStudents.setItems(listStudents);
		columnStudentName.setCellValueFactory(cell -> cell.getValue().user_firstnameProperty());
		columnStudentDate.setCellValueFactory(cell -> cell.getValue().user_birthdayProperty());
		// Set Handlers
		setHandlers();	
	}
	
	// Button Events
	@FXML
	private void viewButton() {
		Project project = tableProjects.getSelectionModel().getSelectedItem();
		Stage dialogStage = new Stage();
		WebViewDialog viewDialog = new WebViewDialog(dialogStage);
		viewDialog.getController().loadContent(project.getProject_details());
		dialogStage.setTitle(Strings.LabelProjectDetails.getValue(Indexes.AppLanguage_index.getValue()));
		Scene scene = new Scene(viewDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
	}
	
	@FXML
	private void addButton() {
		Project project = tableProjects.getSelectionModel().getSelectedItem();
		Stage dialogStage = new Stage();
		StudentsProjectDialog studentsDialog = new StudentsProjectDialog(dialogStage);
		studentsDialog.getController().setProject(project);
        Scene scene = new Scene(studentsDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (studentsDialog.getController().isAddButton()) {
        	updateStudent(studentsDialog.getController().getUser());
        }
	}
	
	@FXML
	private void removeButton() {
		User user = tableStudents.getSelectionModel().getSelectedItem();
		String tasksTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.TasksTable_id.getValue());
		Alert splitAlert = new Alert(AlertType.CONFIRMATION);
		((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
		splitAlert.setTitle(Strings.ConfirmTitle.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setHeaderText(Strings.ConfirmDeleteHeader.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setContentText(Strings.ConfirmDeleteContent.getValue(Indexes.AppLanguage_index.getValue())+
				user.getUser_firstname()+"?");
		Optional<ButtonType> result = splitAlert.showAndWait();
		if (result.get() == ButtonType.OK) {
			String projectsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.ProjectsExecutionsTable_id.getValue());
			ResultSet resultSet = ProjectsExecutionsTable.getInstance().executeSelect("", "projects_execution_id ", "FROM "+projectsExecutionsTable, 
				"WHERE (project_id = "+project_id+") AND (executor_id = "+user.getUser_id()+")", "", "", "");
			try {
				if (resultSet.next())
					if (ProjectsExecutionsTable.getInstance().delete(resultSet.getInt(1))) {
						listStudents.remove(user);
						if (listStudents.size() == 0) {
							tableStudents.getSelectionModel().select(null);
						}
						if (!TasksExecutionsTable.getInstance().executeDelete("WHERE (executor_id = "+user.getUser_id()+") AND (task_id IN (SELECT "
								+tasksTable+".task_id FROM "+tasksTable+" WHERE "+tasksTable+".project_id = "+project_id+"))")) {
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
	}
	
	@FXML
	private void statusButton() {
		Project project = tableProjects.getSelectionModel().getSelectedItem();
		User user = tableStudents.getSelectionModel().getSelectedItem();
		String projectsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.ProjectsExecutionsTable_id.getValue());
		ResultSet result = ProjectsExecutionsTable.getInstance().executeSelect("", "projects_execution_id, project_id, executor_id, projects_execution_start_date, "+
				"projects_execution_end_date, projects_execution_status, projects_execution_result ", "FROM "+projectsExecutionsTable, 
				"WHERE (project_id = "+project_id+") AND (executor_id = "+user.getUser_id()+")", "", "", "");
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
						result.getInt(6),result.getInt(7),project.getProject_name(),user.getUser_firstname());
				Stage dialogStage = new Stage();
				ProjectStatusDialog statusDialog = new ProjectStatusDialog(dialogStage);
				statusDialog.getController().setProject(project);
				statusDialog.getController().setExecution(execution);
				if ((mainApp.getLogin().getRole_name().equals("Administrator")) || 
					(mainApp.getLogin().getUser_id() == project.getAuthor_id()))
					statusDialog.getController().setAuthor();
		        Scene scene = new Scene(statusDialog);
		        dialogStage.setScene(scene);
		        dialogStage.initOwner(mainApp.getPrimaryStage());
		        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
		        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
		        dialogStage.showAndWait();
		        if (statusDialog.getController().isOKButton()) {
		        	user.setUser_birthday(statusDialog.getController().getExecution().getExecution_start_date());
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
		Project project = new Project(0,mainApp.getLogin().getUser_id(),"","",mainApp.getLogin().getUser_name());
		Stage dialogStage = new Stage();
		ProjectDialog projectDialog = new ProjectDialog(dialogStage);
		projectDialog.getController().setProject(project);
        Scene scene = new Scene(projectDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (projectDialog.getController().isOKButton()) {
        	updateProject(projectDialog.getController().getProject());
        }
	}
	
	@FXML
	private void editButton() {
		Project project = tableProjects.getSelectionModel().getSelectedItem();
		Stage dialogStage = new Stage();
		ProjectDialog projectDialog = new ProjectDialog(dialogStage);
		projectDialog.getController().setProject(project);
		Scene scene = new Scene(projectDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (projectDialog.getController().isOKButton()) {
        	updateProject(projectDialog.getController().getProject());
        }
	}
	
	@FXML
	private void deleteButton() {
		Project project = tableProjects.getSelectionModel().getSelectedItem();
		Alert splitAlert = new Alert(AlertType.CONFIRMATION);
		((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
		splitAlert.setTitle(Strings.ConfirmTitle.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setHeaderText(Strings.ConfirmDeleteHeader.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setContentText(Strings.ConfirmDeleteContent.getValue(Indexes.AppLanguage_index.getValue())+
				project.getProject_name()+"?");
		Optional<ButtonType> result = splitAlert.showAndWait();
		if (result.get() == ButtonType.OK) {
			if (ProjectsTable.getInstance().delete(project.getProject_id())) {
				listProjects.remove(project);
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
	private void tasksButton() {
		Project project = tableProjects.getSelectionModel().getSelectedItem();
		if (mainApp.getRootLayout().getMainSplitPanesList()[Indexes.ProjectsTasksSplitPane_id.getValue()] == null)
			mainApp.getRootLayout().getMainSplitPanesList()[Indexes.ProjectsTasksSplitPane_id.getValue()] = new ProjectsTasksSplitPane(this.mainApp);
		mainApp.getRootLayout().setMainSplitPane(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.ProjectsTasksSplitPane_id.getValue()]);
		mainApp.getRootLayout().setCenter(mainApp.getRootLayout().getMainSplitPanesList()[Indexes.ProjectsTasksSplitPane_id.getValue()]);
		((ProjectsTasksSplitPane) mainApp.getRootLayout().getMainSplitPane()).getController().setProject(project);
	}
	
	@FXML
	private void loginButton() {
		getMainApp().getRootLayout().getController().authorizationMenu();
	}
	
	// ChangeLanguage for elements
	public void changeLanguage () {
		labelProjectTitle.setText(Strings.LabelProjectTitle.getValue(Indexes.AppLanguage_index.getValue()));
		labelAuthors.setText(Strings.LabelAuthors.getValue(Indexes.AppLanguage_index.getValue()));
		labelInformation.setText(Strings.LabelInformation.getValue(Indexes.AppLanguage_index.getValue()));
		labelDetails.setText(Strings.LabelProjectDetails.getValue(Indexes.AppLanguage_index.getValue()));
		labelStudents.setText(Strings.LabelProjectStudents.getValue(Indexes.AppLanguage_index.getValue()));
		columnProjectName.setText(Strings.ColumnProjectName.getValue(Indexes.AppLanguage_index.getValue()));
		columnProjectAuthor.setText(Strings.ColumnProjectAuthor.getValue(Indexes.AppLanguage_index.getValue()));
		columnStudentName.setText(Strings.ColumnProjectStudentName.getValue(Indexes.AppLanguage_index.getValue()));
		columnStudentDate.setText(Strings.ColumnProjectStudentDate.getValue(Indexes.AppLanguage_index.getValue()));
		addButton.setText(Strings.AddButton.getValue(Indexes.AppLanguage_index.getValue()));
		removeButton.setText(Strings.RemoveButton.getValue(Indexes.AppLanguage_index.getValue()));
		statusButton.setText(Strings.StatusButton.getValue(Indexes.AppLanguage_index.getValue()));
		newButton.setText(Strings.NewButton.getValue(Indexes.AppLanguage_index.getValue()));
		editButton.setText(Strings.EditButton.getValue(Indexes.AppLanguage_index.getValue()));
		deleteButton.setText(Strings.DeleteButton.getValue(Indexes.AppLanguage_index.getValue()));
		tasksButton.setText(Strings.TasksButton.getValue(Indexes.AppLanguage_index.getValue()));
		loginButton.setText(Strings.LoginButton.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// ChangeLogin for elements
	public void changeLogin () {
		tableProjects.getSelectionModel().clearSelection();
		if (getMainApp().getLogin() ==  null) {
			viewButton.setVisible(false);
			addButton.setVisible(false);
			removeButton.setVisible(false);
			statusButton.setVisible(false);
			newButton.setVisible(false);
			editButton.setVisible(false);
			deleteButton.setVisible(false);
			tasksButton.setVisible(false);
			loginButton.setVisible(true);
		}
		else {
			viewButton.setVisible(true);
			addButton.setVisible(true);
			removeButton.setVisible(true);
			statusButton.setVisible(true);
			newButton.setVisible(true);
			editButton.setVisible(true);
			deleteButton.setVisible(true);
			tasksButton.setVisible(true);
			loginButton.setVisible(false);
			if (getMainApp().getLogin().getRole_name().equals("Student")) {
				viewButton.setDisable(true);
				addButton.setDisable(true);
				removeButton.setDisable(true);
				statusButton.setDisable(true);
				newButton.setDisable(true);
				editButton.setDisable(true);
				deleteButton.setDisable(true);
				tasksButton.setDisable(true);
			}
			else {
				viewButton.setDisable(true);
				addButton.setDisable(true);
				removeButton.setDisable(true);
				statusButton.setDisable(true);
				newButton.setDisable(false);
				editButton.setDisable(true);
				deleteButton.setDisable(true);
				tasksButton.setDisable(true);
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
					listProjects.clear();
					getListProjects(listProjects);
				}
			}
		});
		// Projects list
		tableProjects.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Project>() {
			@Override
			public void changed(ObservableValue<? extends Project> observable, Project oldValue, Project newValue) {
				// TODO Auto-generated method stub
				if (newValue != null) {
					project_id = newValue.getProject_id();
					if ((mainApp.getLogin() != null) && (isAccessProject(mainApp.getLogin(),newValue))) {
						updateInformation(newValue);
						viewButton.setDisable(false);
						addButton.setDisable(false);
						removeButton.setDisable(true);
						statusButton.setDisable(true);
						editButton.setDisable(false);
						deleteButton.setDisable(false);
						tasksButton.setDisable(false);
						if ((!mainApp.getLogin().getRole_name().equals("Administrator")) && (mainApp.getLogin().getUser_id() != newValue.getAuthor_id())) {
							viewButton.setDisable(false);
							addButton.setDisable(true);
							removeButton.setDisable(true);
							statusButton.setDisable(true);
							editButton.setDisable(true);
							deleteButton.setDisable(true);
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
		tableStudents.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				// TODO Auto-generated method stub
				if (newValue != null) {
					if ((mainApp.getLogin().getRole_name().equals("Administrator")) || 
						(mainApp.getLogin().getUser_id() == (tableProjects.getSelectionModel().getSelectedItem().getAuthor_id()))) {
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
	private void getListAuthors(ObservableList<User> listAuthors) {
		String usersTable = InAltoSchoolDatabase .getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		String projectsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.ProjectsTable_id.getValue());
		listAuthors.add(0,new User(0,0,Strings.AllItems.getValue(0)+" /",Strings.AllItems.getValue(1),null,"","","","","","",0,"",0,""));
		ResultSet result = UsersTable.getInstance().executeSelect("", "user_id, user_firstname, user_lastname ", "FROM "+usersTable+" JOIN "+projectsTable
				+" ON "+usersTable+".user_id = "+projectsTable+".author_id", "", "GROUP BY user_id", "", "");
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
	
	private void getListProjects(ObservableList<Project> listProjects) {
		ResultSet result;
		String projectsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.ProjectsTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		if (author_id == 0) {
			result = ProjectsTable.getInstance().executeSelect("", projectsTable+".project_id, "+projectsTable+".author_id, project_name, project_details, "
					+"CONCAT(user_firstname, ' ', user_lastname) ", "FROM "+projectsTable+" JOIN "+usersTable+" ON ("+projectsTable+".author_id = "+usersTable+".user_id)", "", "", "", "");
		}
		else {
			result = ProjectsTable.getInstance().executeSelect("", projectsTable+".project_id, "+projectsTable+".author_id, project_name, project_details, "
					+"CONCAT(user_firstname, ' ', user_lastname) ", "FROM "+projectsTable+" JOIN "+usersTable+" ON ("+projectsTable+".author_id = "+usersTable+".user_id) AND ("
					+usersTable+".user_id = "+author_id+")", "", "", "", "");

		}
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
	
	private void getListStudents(ObservableList<User> listStudents) {
		LocalDate date;
		String projectsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.ProjectsExecutionsTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		ResultSet result = UsersTable.getInstance().executeSelect("", "user_id, CONCAT(user_firstname, ' ', user_lastname), projects_execution_start_date ", "FROM "+usersTable+" JOIN "+projectsExecutionsTable
				+" ON "+usersTable+".user_id = "+projectsExecutionsTable+".executor_id", " WHERE "+projectsExecutionsTable+".project_id = "+project_id, " GROUP BY user_id", "", "");
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
	
	// Insert or update projects
	private void updateProject(Project project) {
		if (project.getProject_id() == 0)
			if (ProjectsTable.getInstance().insert(project)) {
				String projectsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.ProjectsTable_id.getValue());
				ResultSet result = ProjectsTable.getInstance().executeSelect("", "project_id", "FROM "+projectsTable, 
						"WHERE (author_id = "+project.getAuthor_id()+") AND (project_name = \'"+project.getProject_name()+"\')", "", "", "");
				try {
					if (result.next()) {
						project.setProject_id(result.getInt(1));
						listProjects.add(project);
						tableProjects.getSelectionModel().select(project);
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
			if (ProjectsTable.getInstance().update(project.getProject_id(),project))
				updateInformation(project);
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
	private void updateInformation(Project project) {
		if (project != null) {
			String view = project.getProject_details();
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
	private void updateStudent(User user) {
		user.setUser_birthday(LocalDate.now());
		Execution execution = new Execution(0,project_id,user.getUser_id(),0,user.getUser_birthday(),null,0,0,
				tableProjects.getSelectionModel().getSelectedItem().getProject_name(),user.getUser_firstname());
		if (ProjectsExecutionsTable.getInstance().insert(execution)) {
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
	
	// Update execution
	private void updateStatus(Execution execution) {
		if (!ProjectsExecutionsTable.getInstance().update(execution.getExecution_id(),execution)) {
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
	
	public TableView<Project> getTableProjects() {
		return tableProjects;
	}

}
