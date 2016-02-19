package application.view;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.Indexes;
import application.Strings;
import application.database.InAltoSchoolDatabase;
import application.database.UsersTable;
import application.model.Course;
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
import javafx.scene.control.TreeItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

public class StudentsCourseDialogController {
	
	// Stage reference
	private Stage dialogStage;
	// OKButton flag
	private boolean isAddButton = false;
	// Course data
	private Course course;
	// Observable lists
	ObservableList<User> listStudents;
	ObservableList<TreeItem<User>> listGroups;
	// Current User
	private User user = null;
	// Current Group
	private TreeItem<User> group = null;
	
	// Children elements references	
	@FXML
	private Button addButton, cancelButton;
	
	@FXML
	private Label labelCourseAuthor, labelCourseName, labelGroups, labelStudents;
	
	@FXML
	private TextField textCourseAuthor;
	
	@FXML
	private TextArea textCourseName;
	
	@FXML
	private ComboBox<User> comboStudents;
	
	@FXML
	private ComboBox<TreeItem<User>> comboGroups;
	
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
			group = comboGroups.getValue();
			dialogStage.close();
		}
	}
	
	@FXML
	private void cancelButton() {
		dialogStage.close();
	}
	
	// ChangeLanguage for elements
	public void changeLanguage () {
		labelCourseAuthor.setText(Strings.LabelCourseAuthor.getValue(Indexes.AppLanguage_index.getValue()));
		labelCourseName.setText(Strings.LabelCourseName.getValue(Indexes.AppLanguage_index.getValue()));
		labelStudents.setText(Strings.LabelCoursesStudents.getValue(Indexes.AppLanguage_index.getValue()));
		labelGroups.setText(Strings.LabelCoursesGroups.getValue(Indexes.AppLanguage_index.getValue()));
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
		else if (comboGroups.getValue() == null) {
			comboGroups.requestFocus();
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
		// Groups list
		comboGroups.setCellFactory(new Callback<ListView<TreeItem<User>>, ListCell<TreeItem<User>>>() {
			@Override
			public ListCell<TreeItem<User>> call(ListView<TreeItem<User>> param) {
				// TODO Auto-generated method stub
				ListCell<TreeItem<User>> listCell = new ListCell<TreeItem<User>>() {
					@Override
					protected void updateItem(TreeItem<User> user, boolean empty) {
						super.updateItem(user, empty);      
						if(user != null) {
							setText(user.getValue().getUser_firstname());
						}
						else {
							setText(null);
						}
					}
				};
            	return listCell;
			}
		});
		comboGroups.setButtonCell(new ListCell<TreeItem<User>>() {
			@Override
			protected void updateItem(TreeItem<User> user, boolean empty) {
				super.updateItem(user, empty);      
				if(user != null) {
					setText(user.getValue().getUser_firstname());
				}
				else {
					setText(null);
				}
			}
		});
	}
	
	// List getters
	private void getListStudents(ObservableList<User> listStudents) {
		String coursesExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesExecutionsTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		ResultSet result = UsersTable.getInstance().executeSelect("", "user_id, CONCAT(user_firstname, ' ', user_lastname) ", "FROM "+usersTable+" LEFT JOIN "+coursesExecutionsTable
				+" ON ("+usersTable+".user_id = "+coursesExecutionsTable+".executor_id) AND ("+coursesExecutionsTable+".course_id = "+course.getCourse_id()
				+") ", "WHERE ("+coursesExecutionsTable+".executor_id IS NULL) AND ("+usersTable+".user_id <> "+course.getAuthor_id()+")", "", "", "");
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
	
	public Course getCourse() {
		return course;
	}
	
	public void setCourse(Course course) {
		this.course = course;
		textCourseAuthor.setText(course.getAuthor_name());
		textCourseName.setText(course.getCourse_name());
		getListStudents(listStudents);
	}
	
	public void setListGroups(ObservableList<TreeItem<User>> listGroups) {
		this.listGroups = listGroups;
		comboGroups.setItems(listGroups);

	}
	
	public User getUser() {
		return user;
	}
	
	public TreeItem<User> getGroup() {
		return group;
	}
	

}
