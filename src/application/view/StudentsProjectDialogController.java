package application.view;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.Indexes;
import application.Strings;
import application.database.InAltoSchoolDatabase;
import application.database.UsersTable;
import application.model.Project;
import application.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

public class StudentsProjectDialogController {
	
	// Stage reference
	private Stage dialogStage;
	// OKButton flag
	private boolean isAddButton = false;
	// Project data
	private Project project;
	// Observable lists
	ObservableList<User> listStudents;
	// Current User
	private User user = null;
	
	// Children elements references	
	@FXML
	private Button addButton, cancelButton;
	
	@FXML
	private Label labelProjectAuthor, labelProjectName, labelStudents;
	
	@FXML
	private TextField textProjectAuthor;
	
	@FXML
	private TextArea textProjectName;
	
	@FXML
	private ComboBox<User> comboStudents;
	
	@FXML
	private void initialize() {
		// Set lists
		listStudents = FXCollections.observableArrayList();
		comboStudents.setItems(listStudents);
		// Set Handlers
		setHandlers();
	}
	
	// Button Events
	@FXML
	private void addButton() {
		if (isValid()) {
			isAddButton = true;
			user = comboStudents.getValue();
			dialogStage.close();
		}
	}
	
	@FXML
	private void cancelButton() {
		dialogStage.close();
	}
	
	// ChangeLanguage for elements
	public void changeLanguage () {
		labelProjectAuthor.setText(Strings.LabelProjectAuthor.getValue(Indexes.AppLanguage_index.getValue()));
		labelProjectName.setText(Strings.LabelProjectName.getValue(Indexes.AppLanguage_index.getValue()));
		labelStudents.setText(Strings.LabelProjectStudents.getValue(Indexes.AppLanguage_index.getValue()));
		addButton.setText(Strings.AddButton.getValue(Indexes.AppLanguage_index.getValue()));
		cancelButton.setText(Strings.CancelButton.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// Input Validation
	private boolean isValid() {
		boolean isValid = true;
		if (comboStudents.getValue() == null) {
			comboStudents.requestFocus();
			isValid = false;
		}
		if (!isValid) {
			Alert splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.EmptyError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();	
			return isValid;			
		}
		return isValid;
	}
	
	// Set Handlers
	private void setHandlers() {
		// Students list
		comboStudents.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
			@Override
			public ListCell<User> call(ListView<User> param) {
				// TODO Auto-generated method stub
				ListCell<User> listCell = new ListCell<User>() {
					@Override
					protected void updateItem(User user, boolean empty) {
						super.updateItem(user, empty);      
						if(user != null) {
							setText(user.getUser_firstname());
						}
						else {
							setText(null);
						}
					}
				};
            	return listCell;
			}
		});
		comboStudents.setButtonCell(new ListCell<User>() {
			@Override
			protected void updateItem(User user, boolean empty) {
				super.updateItem(user, empty);      
				if(user != null) {
					setText(user.getUser_firstname());
				}
				else {
					setText(null);
				}
			}
		});
	}
	
	// List getters
	private void getListStudents(ObservableList<User> listStudents) {
		String projectsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.ProjectsExecutionsTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		ResultSet result = UsersTable.getInstance().executeSelect("", "user_id, CONCAT(user_firstname, ' ', user_lastname) ", "FROM "+usersTable+" LEFT JOIN "+projectsExecutionsTable
				+" ON ("+usersTable+".user_id = "+projectsExecutionsTable+".executor_id) AND ("+projectsExecutionsTable+".project_id = "+project.getProject_id()
				+") ", "WHERE ("+projectsExecutionsTable+".executor_id IS NULL) AND ("+usersTable+".user_id <> "+project.getAuthor_id()+")", "", "", "");
		try {
			while (result.next()) {
				listStudents.add(new User(result.getInt(1),0,result.getString(2),"",null,"","","","","","",0,"",0,""));
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
	
	// Getters and setters
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public boolean isAddButton() {
		return isAddButton;
	}
	
	public void clearFlags() {
		isAddButton = false;
		user = null;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
		textProjectAuthor.setText(project.getAuthor_name());
		textProjectName.setText(project.getProject_name());
		getListStudents(listStudents);
	}
	
	public User getUser() {
		return user;
	}

}