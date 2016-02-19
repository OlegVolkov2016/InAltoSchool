package application.view;

import application.Indexes;
import application.Strings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ForgotDialogController {
	
	// Stage reference
	private Stage dialogStage;
	// OKButton flag
	private boolean isOKButton = false, isRegistrationLink = false;
	
	// Children elements references	
	@FXML
	private Button okButton, cancelButton;
	
	@FXML
	private Label labelQuestion, labelAnswer;
	
	@FXML
	private TextField textQuestion, textAnswer;
	
	@FXML
	private Hyperlink linkRegistration;

	@FXML
	private void initialize() {
		// Empty
	}
	
	// Link Events
	@FXML
	private void linkRegistration() {
		isRegistrationLink = true;
		dialogStage.close();
	}
	
	// Button Events
	@FXML
	private void okButton() {
		if (isValid()) {
			isOKButton = true;
			dialogStage.close();
		}
	}
	
	@FXML
	private void cancelButton() {
		dialogStage.close();
	}
	
	// ChangeLanguage for elements
	public void changeLanguage () {
		labelQuestion.setText(Strings.ForgotLabelQuestion.getValue(Indexes.AppLanguage_index.getValue()));
		labelAnswer.setText(Strings.ForgotLabelAnswer.getValue(Indexes.AppLanguage_index.getValue()));
		textAnswer.setPromptText(Strings.ForgotTextAnswer.getValue(Indexes.AppLanguage_index.getValue()));
		linkRegistration.setText(Strings.ForgotLinkRegistration.getValue(Indexes.AppLanguage_index.getValue()));
		okButton.setText(Strings.OKButton.getValue(Indexes.AppLanguage_index.getValue()));
		cancelButton.setText(Strings.CancelButton.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// Input Validation
	private boolean isValid() {
		boolean isValid = true;
		if (getAnswer().equals("")) {
			textAnswer.requestFocus();
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
	
	public boolean isRegistrationLink() {
		return isRegistrationLink;
	}
	
	public void clearFlags() {
		isOKButton = false;
		isRegistrationLink = false;
	}
	
	public TextField getTextQuestion() {
		return textQuestion;
	}
	
	public String getAnswer() {
		return textAnswer.getText();
	}
	
}
