package application.view;

import application.Indexes;
import application.Strings;
import application.model.Lesson;
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

public class LessonDialogController {
	
	// Stage reference
	private Stage dialogStage;
	// OKButton flag
	private boolean isOKButton = false;
	// Task data
	private Lesson lesson;
	
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
			if (lesson == null) {
				lesson = new Lesson(0,0,textTaskName.getText(),textTaskDetails.getHtmlText(),"",textProjectAuthor.getText());
			}
			else {
				lesson.setLesson_name(textTaskName.getText());
				lesson.setLesson_details(textTaskDetails.getHtmlText());
				lesson.setAuthor_name(textProjectAuthor.getText());
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
		labelProjectAuthor.setText(Strings.LabelProjectAuthor.getValue(Indexes.AppLanguage_index.getValue()));
		labelProjectName.setText(Strings.LabelProjectName.getValue(Indexes.AppLanguage_index.getValue()));
		labelTaskName.setText(Strings.LabelTaskName.getValue(Indexes.AppLanguage_index.getValue()));
		labelTaskDetails.setText(Strings.LabelTaskDetails.getValue(Indexes.AppLanguage_index.getValue()));
		textProjectName.setPromptText(Strings.TextTaskName.getValue(Indexes.AppLanguage_index.getValue()));
//		textTaskDetails.setPromptText(Strings.TextLessonDetails.getValue(Indexes.AppLanguage_index.getValue()));
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
		textProjectAuthor.setText(lesson.getAuthor_name());
		textProjectName.setText(lesson.getCourse_name());
		textTaskName.setText(lesson.getLesson_name());
		textTaskDetails.setHtmlText(lesson.getLesson_details());
	}

}
