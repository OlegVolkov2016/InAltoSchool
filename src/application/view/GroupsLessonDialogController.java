package application.view;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.Indexes;
import application.Strings;
import application.database.GroupsTable;
import application.database.InAltoSchoolDatabase;
import application.model.Course;
import application.model.Lesson;
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

public class GroupsLessonDialogController {
	
	// Stage reference
	private Stage dialogStage;
	// OKButton flag
	private boolean isAddButton = false;
	// Course data
	private Course course;
	// Lesson data
	private Lesson lesson;
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
		labelProjectAuthor.setText(Strings.LabelCourseAuthor.getValue(Indexes.AppLanguage_index.getValue()));
		labelProjectName.setText(Strings.LabelLessonName.getValue(Indexes.AppLanguage_index.getValue()));
		labelStudents.setText(Strings.LabelLessonsGroups.getValue(Indexes.AppLanguage_index.getValue()));
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
		String coursesExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesExecutionsTable_id.getValue());
		String coursesTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesTable_id.getValue());
		String groupsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.GroupsTable_id.getValue());
		ResultSet result = GroupsTable.getInstance().executeSelect("", groupsTable+".group_id, group_name ", 
				"FROM "+groupsTable+" JOIN "+coursesTable+" ON "+groupsTable+".course_id = "+coursesTable+".course_id JOIN "+coursesExecutionsTable+" ON "+groupsTable
				+".group_id = "+coursesExecutionsTable+".group_id "," WHERE ("+groupsTable+".course_id = "+course.getCourse_id()+") AND ("+coursesExecutionsTable+".executor_id IS NULL)", "", "", "");
		try {
			while (result.next()) {
				listStudents.add(new User(result.getInt(1),0,result.getString(2),"Group",null,"","","","","","",0,"",0,""));
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
	
	public void setCourse(Course course) {
		this.course = course;
	}
	
	
	public Lesson getLesson() {
		return lesson;
	}
	
	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
		textProjectAuthor.setText(lesson.getAuthor_name());
		textProjectName.setText(Strings.LabelCourseName.getValue(Indexes.AppLanguage_index.getValue())+" "+lesson.getCourse_name()+"\n"+
			Strings.LabelLessonName.getValue(Indexes.AppLanguage_index.getValue())+" "+lesson.getLesson_name());
		getListStudents(listStudents);
	}

	public User getUser() {
		return user;
	}

}
