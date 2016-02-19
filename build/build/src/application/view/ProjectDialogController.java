package application.view;

import application.Indexes;
import application.Strings;
import application.model.Project;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class ProjectDialogController {
	
	// Stage reference
	private Stage dialogStage;
	// OKButton flag
	private boolean isOKButton = false;
	// Project data
	private Project project;
	
	// Children elements references	
	@FXML
	private Button okButton, cancelButton;
	
	@FXML
	private Label labelProjectAuthor, labelProjectName, labelProjectDetails;
	
	@FXML
	private TextField textProjectAuthor, textProjectName;
	
	@FXML
	private HTMLEditor textProjectDetails;
	
	@FXML
	private void initialize() {
		// Empty
	}
	
	// Button Events
	@FXML
	private void okButton() {
		if (isValid()) {
			isOKButton = true;
			if (project == null) {
				project = new Project(0,0,textProjectName.getText(),textProjectDetails.getHtmlText(),textProjectAuthor.getText());
			}
			else {
				project.setProject_name(textProjectName.getText());
				project.setProject_details(textProjectDetails.getHtmlText());
				project.setAuthor_name(textProjectAuthor.getText());
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
		labelProjectDetails.setText(Strings.LabelProjectDetails.getValue(Indexes.AppLanguage_index.getValue()));
		textProjectName.setPromptText(Strings.TextProjectName.getValue(Indexes.AppLanguage_index.getValue()));
//		textProjectDetails.setPromptText(Strings.TextProjectDetails.getValue(Indexes.AppLanguage_index.getValue()));
		okButton.setText(Strings.OKButton.getValue(Indexes.AppLanguage_index.getValue()));
		cancelButton.setText(Strings.CancelButton.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// Input Validation
	private boolean isValid() {
		boolean isValid = true;
		if (textProjectName.getText().equals("")) {
			textProjectName.requestFocus();
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
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
		textProjectAuthor.setText(project.getAuthor_name());
		textProjectName.setText(project.getProject_name());
		textProjectDetails.setHtmlText(project.getProject_details());
	}

}
