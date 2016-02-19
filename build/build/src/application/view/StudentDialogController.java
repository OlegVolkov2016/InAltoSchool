package application.view;

import java.time.format.DateTimeFormatter;
import application.Indexes;
import application.Strings;
import application.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentDialogController {
	
	// Stage reference
	private Stage dialogStage;
	// OKButton flag
	private boolean isOKButton;
	// User data
	private User user;
	
	// Children elements references	
	@FXML
	private Button okButton;
	
	@FXML
	private Label labelFirstName, labelLastName, labelBirthday, labelAddress, labelPhone, labelEmail, labelSkype, labelDetails;
	
	@FXML
	private TextField textFirstName, textLastName, textBirthday, textAddress, textPhone, textEmail, textSkype;
	
	@FXML
	private TextArea textDetails;
	
	@FXML
	private void initialize() {
		// Empty
	}
	
	// Button Events
	@FXML
	private void okButton() {
		isOKButton = true;
		dialogStage.close();
	}
	
	// ChangeLanguage for elements
	public void changeLanguage () {
		labelFirstName.setText(Strings.StudentLabelFirstName.getValue(Indexes.AppLanguage_index.getValue()));
		labelLastName.setText(Strings.StudentLabelLastName.getValue(Indexes.AppLanguage_index.getValue()));
		labelBirthday.setText(Strings.RegistrationLabelBirthday.getValue(Indexes.AppLanguage_index.getValue()));
		labelAddress.setText(Strings.RegistrationLabelAddress.getValue(Indexes.AppLanguage_index.getValue()));
		labelPhone.setText(Strings.RegistrationLabelPhone.getValue(Indexes.AppLanguage_index.getValue()));
		labelEmail.setText(Strings.RegistrationLabelEmail.getValue(Indexes.AppLanguage_index.getValue()));
		labelSkype.setText(Strings.RegistrationLabelSkype.getValue(Indexes.AppLanguage_index.getValue()));
		labelDetails.setText(Strings.RegistrationLabelDetails.getValue(Indexes.AppLanguage_index.getValue()));
		okButton.setText(Strings.OKButton.getValue(Indexes.AppLanguage_index.getValue()));
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
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
		textFirstName.setText(user.getUser_firstname());
		textLastName.setText(user.getUser_lastname());
		if (user.getUser_birthday() != null)
			textBirthday.setText(user.getUser_birthday().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
		textAddress.setText(user.getUser_address());
		textPhone.setText(user.getUser_phone());
		textEmail.setText(user.getUser_email());
		textSkype.setText(user.getUser_skype());
		textDetails.setText(user.getUser_details());
	}

}
