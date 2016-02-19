package application.view;

import application.Indexes;
import application.Strings;
import application.model.Lesson;
import application.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class LessonTaskDialogController {
	
	// Stage reference
	private Stage dialogStage;
	// OKButton flag
	private boolean isOKButton = false;
	// Lesson data
	private Lesson lesson;
	// Task data
	private Task task;
	
	// Children elements references	
	@FXML
	private Button okButton, cancelButton;
	
	@FXML
	private Label labelProjectAuthor, labelProjectName, labelTaskName, labelTaskDetails;
	
	@FXML
	private TextField textProjectAuthor, textTaskName;
	
	@FXML
	private TextArea textProjectName;
	
	@FXML
	private HTMLEditor textTaskDetails;
	
	@FXML
	private void initialize() {
		// Empty
	}
	
	// Button Events
	@FXML
	private void okButton() {
		if (isValid()) {
			isOKButton = true;
			if (task == null) {
				task = new Task(0,0,0,textTaskName.getText(),textTaskDetails.getHtmlText(),"","","",textProjectAuthor.getText());
			}
			else {
				task.setTask_name(textTaskName.getText());
				task.setTask_details(textTaskDetails.getHtmlText());
				task.setAuthor_name(textProjectAuthor.getText());
			}
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
		labelTaskName.setText(Strings.LabelTaskName.getValue(Indexes.AppLanguage_index.getValue()));
		labelTaskDetails.setText(Strings.LabelTaskDetails.getValue(Indexes.AppLanguage_index.getValue()));
		textProjectName.setPromptText(Strings.TextTaskName.getValue(Indexes.AppLanguage_index.getValue()));
//		textTaskDetails.setPromptText(Strings.TextTaskDetails.getValue(Indexes.AppLanguage_index.getValue()));
		okButton.setText(Strings.OKButton.getValue(Indexes.AppLanguage_index.getValue()));
		cancelButton.setText(Strings.CancelButton.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// Input Validation
	private boolean isValid() {
		boolean isValid = true;
		if (textTaskName.getText().equals("")) {
			textTaskName.requestFocus();
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
	
	// Getters and setters
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public boolean isOKButton() {
		return isOKButton;
	}
	
	public void clearFlags() {
		isOKButton = false;
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
		textProjectAuthor.setText(task.getAuthor_name());
		textProjectName.setText(Strings.LabelCourseName.getValue(Indexes.AppLanguage_index.getValue())+" "+lesson.getCourse_name()+"\n"+
				Strings.LabelLessonName.getValue(Indexes.AppLanguage_index.getValue())+" "+task.getLesson_name()+"\n"+
				Strings.LabelTaskName.getValue(Indexes.AppLanguage_index.getValue())+" "+task.getTask_name());
		textTaskName.setText(task.getTask_name());
		textTaskDetails.setHtmlText(task.getTask_details());
	}

}
