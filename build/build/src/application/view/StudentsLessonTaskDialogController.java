package application.view;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.Indexes;
import application.Strings;
import application.database.GroupsTable;
import application.database.InAltoSchoolDatabase;
import application.database.UsersTable;
import application.model.Course;
import application.model.Lesson;
import application.model.Task;
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

public class StudentsLessonTaskDialogController {
	
	// Stage reference
	private Stage dialogStage;
	// OKButton flag
	private boolean isAddButton = false;
	// Course data
	private Course course;
	// Lesson data
	private Lesson lesson;
	// Task data
	private Task task;
	// Observable lists
	ObservableList<User> listStudents;
	ObservableList<User> listGroups;
	// Current User
	private User user = null;
	// Current Group
	private User group = null;
	
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
	private ComboBox<User> comboGroups;
	
	@FXML
	private void initialize() {
		// Set lists
		listStudents = FXCollections.observableArrayList();
		comboStudents.setItems(listStudents);
		listGroups = FXCollections.observableArrayList();
		comboStudents.setItems(listGroups);
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
		labelCourseName.setText(Strings.LabelTaskName.getValue(Indexes.AppLanguage_index.getValue()));
		labelStudents.setText(Strings.LabelCoursesStudents.getValue(Indexes.AppLanguage_index.getValue()));
		labelGroups.setText(Strings.LabelLessonsGroups.getValue(Indexes.AppLanguage_index.getValue()));
		addButton.setText(Strings.AddButton.getValue(Indexes.AppLanguage_index.getValue()));
		cancelButton.setText(Strings.CancelButton.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// Input Validation
	private boolean isValid() {
		boolean isValid = true;
		if (comboStudents.getValue() == null) {
			if (comboGroups.getValue() == null) {
				comboGroups.requestFocus();
				isValid = false;
			}
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
							comboGroups.setValue(null);
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
		comboGroups.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
			@Override
			public ListCell<User> call(ListView<User> param) {
				// TODO Auto-generated method stub
				ListCell<User> listCell = new ListCell<User>() {
					@Override
					protected void updateItem(User user, boolean empty) {
						super.updateItem(user, empty);      
						if(user != null) {
							setText(user.getUser_firstname());
							comboStudents.setValue(null);
						}
						else {
							setText(null);
						}
					}
				};
            	return listCell;
			}
		});
		comboGroups.setButtonCell(new ListCell<User>() {
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
	private void getListGroups(ObservableList<User> listGroups) {
		String lessonsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsExecutionsTable_id.getValue());
		String groupsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.GroupsTable_id.getValue());
		ResultSet result = GroupsTable.getInstance().executeSelect("", groupsTable+".group_id, group_name ", "FROM "+groupsTable+" JOIN "+lessonsExecutionsTable
				+" ON ("+groupsTable+".group_id = "+lessonsExecutionsTable+".group_id) ", "WHERE ("+lessonsExecutionsTable+".lesson_id = "+lesson.getLesson_id()
				+") AND ("+lessonsExecutionsTable+".executor_id IS NULL) ", "GROUP BY group_id ", "", "");
		try {
			while (result.next()) {
				listGroups.add(new User(result.getInt(1),0,result.getString(2),"",null,"","","","","","",0,"",0,""));
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
		String lessonsExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LessonsExecutionsTable_id.getValue());
		String tasksExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.TasksExecutionsTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
//		ResultSet result = UsersTable.getInstance().executeSelect("", usersTable+".user_id, CONCAT(user_firstname, ' ', user_lastname) ", "FROM "+usersTable+" JOIN "+lessonsExecutionsTable
//				+" ON ("+usersTable+".user_id = "+lessonsExecutionsTable+".executor_id) LEFT JOIN "+tasksExecutionsTable+" ON ("+usersTable+".user_id = "+tasksExecutionsTable+".executor_id) AND ("
//				+tasksExecutionsTable+".task_id = "+task.getTask_id()+") ", "WHERE ("+tasksExecutionsTable+".executor_id IS NULL) AND ("+lessonsExecutionsTable+".lesson_id = "+lesson.getLesson_id()
//				+") AND ("+lessonsExecutionsTable+".group_id IS NULL) ", "GROUP BY user_id ", "", "");
		ResultSet result = UsersTable.getInstance().executeSelect("", usersTable+".user_id, CONCAT(user_firstname, ' ', user_lastname) ", "FROM "+usersTable+" JOIN "+lessonsExecutionsTable
				+" ON ("+usersTable+".user_id = "+lessonsExecutionsTable+".executor_id) LEFT JOIN "+tasksExecutionsTable+" ON ("+usersTable+".user_id = "+tasksExecutionsTable+".executor_id) AND ("
				+tasksExecutionsTable+".task_id = "+task.getTask_id()+") ", "WHERE ("+tasksExecutionsTable+".executor_id IS NULL) AND ("+lessonsExecutionsTable+".lesson_id = "+lesson.getLesson_id()
				+") ", "GROUP BY user_id ", "", "");
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
		group = null;
		user = null;
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
	}
	
	public Task getTask() {
		return task;
	}
	
	public void setTask(Task task) {
		this.task = task;
		textCourseAuthor.setText(task.getAuthor_name());
		textCourseName.setText(Strings.LabelCourseName.getValue(Indexes.AppLanguage_index.getValue())+" "+lesson.getCourse_name()+"\n"+
				Strings.LabelLessonName.getValue(Indexes.AppLanguage_index.getValue())+" "+task.getLesson_name()+"\n"+
				Strings.LabelTaskName.getValue(Indexes.AppLanguage_index.getValue())+" "+task.getTask_name());
		getListGroups(listGroups);
		comboGroups.setItems(listGroups);
		getListStudents(listStudents);
		comboStudents.setItems(listStudents);
	}
	
	public User getUser() {
		return user;
	}
	
	public User getGroup() {
		return group;
	}
	

}
