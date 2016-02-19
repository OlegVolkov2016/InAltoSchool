package application.view;

import application.Indexes;
import application.Strings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AuthorizationDialogController {
	
	// Stage reference
	private Stage dialogStage;
	// OKButton flag
	private boolean isOKButton, isRegistrationLink, isForgotLink;
	
	// Children elements references	
	@FXML
	private Button okButton, cancelButton;
	
	@FXML
	private Label labelLogin, labelPassword;
	
	@FXML
	private TextField textLogin;
	
	@FXML
	private PasswordField textPassword;
	
	@FXML
	private Hyperlink linkRegistration, linkForgot;

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
	
	@FXML
	private void linkForgot() {
		if (getLogin().equals("")) {
			textLogin.requestFocus();
			Alert splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.EmptyError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();
			return;
		}
		isForgotLink = true;
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
		labelLogin.setText(Strings.AuthorizationLabelLogin.getValue(Indexes.AppLanguage_index.getValue()));
		labelPassword.setText(Strings.AuthorizationLabelPassword.getValue(Indexes.AppLanguage_index.getValue()));
		textLogin.setPromptText(Strings.AuthorizationTextLogin.getValue(Indexes.AppLanguage_index.getValue()));
		textPassword.setPromptText(Strings.AuthorizationTextPassword.getValue(Indexes.AppLanguage_index.getValue()));
		linkRegistration.setText(Strings.AuthorizationLinkRegistration.getValue(Indexes.AppLanguage_index.getValue()));
		linkForgot.setText(Strings.AuthorizationLinkForgot.getValue(Indexes.AppLanguage_index.getValue()));
		okButton.setText(Strings.OKButton.getValue(Indexes.AppLanguage_index.getValue()));
		cancelButton.setText(Strings.CancelButton.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// Input Validation
	private boolean isValid() {
		boolean isValid = true;
		if (getLogin().equals("")) {
			textLogin.requestFocus();
			isValid = false;
		}
		else if (getPassword().equals("")) {
			textPassword.requestFocus();
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
	
	public boolean isForgotLink() {
		return isForgotLink;
	}
	
	public void clearFlags() {
		isOKButton = false;
		isRegistrationLink = false;
		isForgotLink = false;
	}
	
	public String getLogin() {
		return textLogin.getText();
	}
	
	public String getPassword() {
		return textPassword.getText();
	}
	
}
