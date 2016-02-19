package application.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import application.Indexes;
import application.Strings;
import application.database.CoursesTable;
import application.database.InAltoSchoolDatabase;
import application.model.Course;
import application.model.UserPayment;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class UserPaymentDialogController {
	
	// Stage reference
	private Stage dialogStage;
	// OKButton flag
	private boolean isAddButton = false;
	// Admin flag
	private boolean isAdmin = false;
	// UserPayment data
	private UserPayment userPayment;
	// Observable lists
	ObservableList<Course> listCourses;
	// Current Course
	private Course course = null;
	
	// Children elements references	
	@FXML
	private Button addButton, cancelButton;
	
	@FXML
	private Label labelCourses, labelCourseAuthor, labelCourseName, labelPaymentDate, labelPaymentSum;
	
	@FXML
	private TextField textCourseAuthor, textPaymentDate, textPaymentSum;
	
	@FXML
	private TextArea textCourseName;
	
	@FXML
	private ComboBox<Course> comboCourses;
	
	@FXML
	private void initialize() {
		// Set lists
		listCourses = FXCollections.observableArrayList();
		comboCourses.setItems(listCourses);
		// Set Handlers
		setHandlers();
	}
	
	// Button Events
	@FXML
	private void addButton() {
		if (isValid()) {
			isAddButton = true;
			LocalDate date;
			if (textPaymentDate.getText().equals(""))
				date = null;
			else
				date = LocalDate.parse(textPaymentDate.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			course = comboCourses.getValue();
			userPayment.setCourse_id(course.getCourse_id());
			userPayment.setCourse_name(course.getCourse_name());
			userPayment.setAuthor_id(course.getAuthor_id());
			userPayment.setUsers_payment_date(date);
			userPayment.setUsers_payment_sum(Double.parseDouble(textPaymentSum.getText()));
			dialogStage.close();
		}
	}
	
	@FXML
	private void cancelButton() {
		dialogStage.close();
	}
	
	// ChangeLanguage for elements
	public void changeLanguage () {
		labelCourses.setText(Strings.LabelCourseTitle.getValue(Indexes.AppLanguage_index.getValue()));
		labelCourseAuthor.setText(Strings.LabelCourseAuthor.getValue(Indexes.AppLanguage_index.getValue()));
		labelCourseName.setText(Strings.LabelCourseName.getValue(Indexes.AppLanguage_index.getValue()));
		labelPaymentDate.setText(Strings.LabelPaymentDate.getValue(Indexes.AppLanguage_index.getValue()));
		labelPaymentSum.setText(Strings.LabelPaymentSum.getValue(Indexes.AppLanguage_index.getValue()));
		textPaymentDate.setPromptText(Strings.TextPaymentDate.getValue(Indexes.AppLanguage_index.getValue()));
		textPaymentSum.setPromptText(Strings.TextPaymentSum.getValue(Indexes.AppLanguage_index.getValue()));
		addButton.setText(Strings.AddButton.getValue(Indexes.AppLanguage_index.getValue()));
		cancelButton.setText(Strings.CancelButton.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// Input Validation
	private boolean isValid() {
		boolean isValid = true;
		if (comboCourses.getValue() == null) {
			comboCourses.requestFocus();
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
		try {
			if (!textPaymentDate.getText().equals("")) 
				LocalDate.parse(textPaymentDate.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			if (!textPaymentSum.getText().equals(""))
				Double.parseDouble(textPaymentSum.getText());
		}
		catch (DateTimeParseException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			textPaymentDate.requestFocus();
			isValid = false;
		}
		catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			textPaymentSum.requestFocus();
			isValid = false;
		}
		if (!isValid) {
			Alert splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.FormatError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();	
			return isValid;			
		}
		return isValid;
	}
	
	// Set Handlers
	private void setHandlers() {
		// Courses list
		comboCourses.setCellFactory(new Callback<ListView<Course>, ListCell<Course>>() {		
			@Override
			public ListCell<Course> call(ListView<Course> param) {
				// TODO Auto-generated method stub
				ListCell<Course> listCell = new ListCell<Course>() {
					@Override
					protected void updateItem(Course course, boolean empty) {
						super.updateItem(course, empty);      
						if(course != null) {
							setText(course.getCourse_name());
						}
						else {
							setText(null);
						}
					}
				};
            	return listCell;
			}
		});
		comboCourses.setButtonCell(new ListCell<Course>() {
			@Override
			protected void updateItem(Course course, boolean empty) {
				super.updateItem(course, empty);      
				if(course != null) {
					setText(course.getCourse_name());
				}
				else {
					setText(null);
				}
			}
		});
		comboCourses.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Course>() {
			@Override
			public void changed(ObservableValue<? extends Course> observable, Course oldValue, Course newValue) {
				// TODO Auto-generated method stub
				if (newValue != null) {
					textCourseAuthor.setText(newValue.getAuthor_name());
					textCourseName.setText(newValue.getCourse_name());
				}
				else {
					textCourseAuthor.setText("");
					textCourseName.setText("");
				}
			}
		});
	}
	
	// List getters
	private void getListCourses(ObservableList<Course> listCourses) {
		ResultSet result;
		String coursesExecutionsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesExecutionsTable_id.getValue());
		String coursesTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.CoursesTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		if (isAdmin) {
			result = CoursesTable.getInstance().executeSelect("", coursesTable+".course_id, "+coursesTable+".author_id, course_name, course_details, "
					+"course_group_price, course_individual_price, CONCAT(user_firstname, ' ', user_lastname) ", "FROM "+coursesTable+" JOIN "+usersTable
					+" ON ("+coursesTable+".author_id = "+usersTable+".user_id) JOIN "+coursesExecutionsTable+" ON ("+coursesExecutionsTable+".course_id = "
					+coursesTable+".course_id) ", "WHERE ("+coursesExecutionsTable+".executor_id = "+userPayment.getUser_id()+")", "", "", "");
		}
		else {
			result = CoursesTable.getInstance().executeSelect("", coursesTable+".course_id, "+coursesTable+".author_id, course_name, course_details, "
					+"course_group_price, course_individual_price, CONCAT(user_firstname, ' ', user_lastname) ", "FROM "+coursesTable+" JOIN "+usersTable
					+" ON ("+coursesTable+".author_id = "+usersTable+".user_id) JOIN "+coursesExecutionsTable+" ON ("+coursesExecutionsTable+".course_id = "
					+coursesTable+".course_id) ", "WHERE ("+coursesExecutionsTable+".executor_id = "+userPayment.getUser_id()+") AND ("+coursesTable
					+".author_id = "+userPayment.getAuthor_id()+")", "", "", "");
		}
		try {
			while (result.next()) {
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
	
	// Getters and setters
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public boolean isAddButton() {
		return isAddButton;
	}
	
	public void clearFlags() {
		isAddButton = false;
		isAdmin = false;
		course = null;
	}
	
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public UserPayment getUserPayment() {
		return userPayment;
	}
	
	public void setUserPayment(UserPayment userPayment) {
		this.userPayment = userPayment;
		getListCourses(listCourses);
		if (userPayment.getCourse_id() != 0) {
			listCourses.forEach(course -> {
				if (course.getCourse_id() == userPayment.getCourse_id()) {
					comboCourses.getSelectionModel().select(course);
					return;
				}
			});
		}
		String date;
		if (userPayment.getUsers_payment_date() == null)
			date = "";
		else
			date = userPayment.getUsers_payment_date().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		textPaymentDate.setText(date);
		textPaymentSum.setText(userPayment.getUsers_payment_sum()+"");
	}

}
