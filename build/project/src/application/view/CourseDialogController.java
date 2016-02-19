package application.view;

import application.Indexes;
import application.Strings;
import application.model.Course;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class CourseDialogController {
	
	// Stage reference
	private Stage dialogStage;
	// OKButton flag
	private boolean isOKButton = false;
	// Course data
	private Course course;
	
	// Children elements references	
	@FXML
	private Button okButton, cancelButton;
	
	@FXML
	private Label labelCourseAuthor, labelCourseName, labelCourseGroupPrice, labelCourseIndividualPrice, labelCourseDetails;
	
	@FXML
	private TextField textCourseAuthor, textCourseName, textCourseGroupPrice, textCourseIndividualPrice;
	
	@FXML
	private HTMLEditor textCourseDetails;
	
	@FXML
	private void initialize() {
		// Empty
	}
	
	// Button Events
	@FXML
	private void okButton() {
		if (isValid()) {
			isOKButton = true;
			if (course == null) {
				course = new Course(0,0,textCourseName.getText(),textCourseDetails.getHtmlText(),Double.parseDouble(textCourseGroupPrice.getText()),
					Double.parseDouble(textCourseIndividualPrice.getText()),textCourseAuthor.getText());
			}
			else {
				course.setCourse_name(textCourseName.getText());
				course.setCourse_details(textCourseDetails.getHtmlText());
				course.setCourse_group_price(Double.parseDouble(textCourseGroupPrice.getText()));
				course.setCourse_individual_price(Double.parseDouble(textCourseIndividualPrice.getText()));
				course.setAuthor_name(textCourseAuthor.getText());
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
		labelCourseAuthor.setText(Strings.LabelCourseAuthor.getValue(Indexes.AppLanguage_index.getValue()));
		labelCourseName.setText(Strings.LabelCourseName.getValue(Indexes.AppLanguage_index.getValue()));
		labelCourseGroupPrice.setText(Strings.LabelCourseGroupPrice.getValue(Indexes.AppLanguage_index.getValue()));
		labelCourseIndividualPrice.setText(Strings.LabelCourseIndividualPrice.getValue(Indexes.AppLanguage_index.getValue()));
		labelCourseDetails.setText(Strings.LabelCourseDetails.getValue(Indexes.AppLanguage_index.getValue()));
		textCourseName.setPromptText(Strings.TextCourseName.getValue(Indexes.AppLanguage_index.getValue()));
		textCourseGroupPrice.setPromptText(Strings.TextCourseGroupPrice.getValue(Indexes.AppLanguage_index.getValue()));
		textCourseIndividualPrice.setPromptText(Strings.TextCourseIndividualPrice.getValue(Indexes.AppLanguage_index.getValue()));
//		textCourseDetails.setPromptText(Strings.TextCourseDetails.getValue(Indexes.AppLanguage_index.getValue()));
		okButton.setText(Strings.OKButton.getValue(Indexes.AppLanguage_index.getValue()));
		cancelButton.setText(Strings.CancelButton.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// Input Validation
	private boolean isValid() {
		boolean isValid = true;
		if (textCourseName.getText().equals("")) {
			textCourseName.requestFocus();
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
			if (!textCourseGroupPrice.getText().equals(""))
				Double.parseDouble(textCourseGroupPrice.getText());
		}
		catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			textCourseGroupPrice.requestFocus();
			isValid = false;
		}
		try {
			if (!textCourseIndividualPrice.getText().equals(""))
				Double.parseDouble(textCourseIndividualPrice.getText());
		}
		catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			textCourseIndividualPrice.requestFocus();
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
	
	public Course getCourse() {
		return course;
	}
	
	public void setCourse(Course course) {
		this.course = course;
		textCourseAuthor.setText(course.getAuthor_name());
		textCourseName.setText(course.getCourse_name());
		textCourseGroupPrice.setText(course.getCourse_group_price()+"");
		textCourseIndividualPrice.setText(course.getCourse_individual_price()+"");
		textCourseDetails.setHtmlText(course.getCourse_details());
	}

}
