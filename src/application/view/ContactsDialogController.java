package application.view;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import application.Indexes;
import application.Strings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ContactsDialogController {
	
	// Stage reference
	private Stage dialogStage;
	// SendButton flag
	private boolean isSendButton = false;
	
	// Children elements references
	@FXML
	private TextArea textMessage;
	
	@FXML
	private Button sendButton, cancelButton;
	
	@FXML
	private Label labelTitle, labelQuestion, labelName, labelEmail, labelCourseName, labelMessage;
	
	@FXML
	private TextField textName, textEmail, textCourseName;

	@FXML
	private void initialize() {
		// Empty
	}
	
	// Button Events
	@FXML
	private void sendButton() {
		if (isValid()) {
			isSendButton = true;
			dialogStage.close();
		}
	}
	
	@FXML
	private void cancelButton() {
		dialogStage.close();
	}
	
	// ChangeLanguage for elements
	public void changeLanguage () {
		labelTitle.setText(Strings.ContactsLabelTitle.getValue(Indexes.AppLanguage_index.getValue()));
		labelQuestion.setText(Strings.ContactsLabelQuestion.getValue(Indexes.AppLanguage_index.getValue()));
		labelName.setText(Strings.ContactsLabelName.getValue(Indexes.AppLanguage_index.getValue()));
		labelEmail.setText(Strings.ContactsLabelEmail.getValue(Indexes.AppLanguage_index.getValue()));
		labelCourseName.setText(Strings.ContactsLabelCourseName.getValue(Indexes.AppLanguage_index.getValue()));
		labelMessage.setText(Strings.ContactsLabelMessage.getValue(Indexes.AppLanguage_index.getValue()));
		textName.setPromptText(Strings.ContactsTextName.getValue(Indexes.AppLanguage_index.getValue()));
		textEmail.setPromptText(Strings.ContactsTextEmail.getValue(Indexes.AppLanguage_index.getValue()));
		textCourseName.setPromptText(Strings.ContactsTextCourseName.getValue(Indexes.AppLanguage_index.getValue()));
		textMessage.setPromptText(Strings.ContactsTextMessage.getValue(Indexes.AppLanguage_index.getValue()));
		sendButton.setText(Strings.SendButton.getValue(Indexes.AppLanguage_index.getValue()));
		cancelButton.setText(Strings.CancelButton.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// Input Validation
	private boolean isValid() {
		boolean isValid = true;
		if (getName().equals("")) {
			textName.requestFocus();
			isValid = false;
		}
		else if (getEmail().equals("")) {
			textEmail.requestFocus();
			isValid = false;
		}
		else if (getMessage().equals("")) {
			textMessage.requestFocus();
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
			InternetAddress emailAddress = new InternetAddress(getEmail());
			emailAddress.validate();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			isValid = false;
		}
		if (!isValid) {
			Alert splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.EmailError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();	
			return isValid;			
		}
		return isValid;
	}
	
	// Getters and setters
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public boolean IsSendButton() {
		return isSendButton;
	}
	
	public String getName() {
		return textName.getText();
	}
	
	public String getEmail() {
		return textEmail.getText();
	}
	
	public String getCourseName() {
		return textCourseName.getText();
	}
	
	public String getMessage() {
		return textMessage.getText();
	}

}
