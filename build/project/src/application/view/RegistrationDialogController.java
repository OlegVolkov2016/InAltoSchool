package application.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import application.Indexes;
import application.Strings;
import application.database.InAltoSchoolDatabase;
import application.database.RolesTable;
import application.model.Role;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

public class RegistrationDialogController {
	
	// Stage reference
	private Stage dialogStage;
	// OKButton flag
	private boolean isOKButton;
	// User data
	private User user;
	// Security change flags
	private boolean changePassword = false, changeAnswer = false;
	// Observable lists
	ObservableList<Role> listRoles;
	
	// Children elements references	
	@FXML
	private Button okButton, cancelButton;
	
	@FXML
	private Label labelRoleName, labelLogin, labelPassword, labelPasswordRepeat, labelQuestion, labelAnswer, labelFirstName, labelLastName, labelBirthday, labelAddress,
		labelPhone, labelEmail, labelSkype, labelDetails;
	
	@FXML
	private TextField textLogin, textQuestion, textFirstName, textLastName, textBirthday, textAddress,
	textPhone, textEmail, textSkype;
	
	@FXML
	private PasswordField textPassword, textPasswordRepeat, textAnswer;
	
	@FXML
	private TextArea textDetails;
	
	@FXML
	private ComboBox<Role> comboRoles;
	
	@FXML
	private void initialize() {
		// Set lists
		listRoles = FXCollections.observableArrayList();
		getListRoles(listRoles);
		comboRoles.setItems(listRoles);
		listRoles.forEach(role -> {
			if (role.getRole_name().equals("Student")) {
				comboRoles.getSelectionModel().select(role);
				return;
			}
		});
		// Set Handlers
		setHandlers();
	}

	// Button Events
	@FXML
	private void okButton() {
		if (isValid()) {
			LocalDate date;
			if (textBirthday.getText().equals(""))
				date = null;
			else
				date = LocalDate.parse(textBirthday.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			isOKButton = true;
			if (user == null) {
				user = new User(0,0,textFirstName.getText(),textLastName.getText(),date,
						textAddress.getText(),textPhone.getText(),textEmail.getText(),textSkype.getText(),textDetails.getText(),textLogin.getText(),
						textPassword.getText().hashCode(),textQuestion.getText(),textAnswer.getText().hashCode(),comboRoles.getSelectionModel().getSelectedItem().getRole_name());
			}
			else {
				user.setUser_firstname(textFirstName.getText());
				user.setUser_lastname(textLastName.getText());
				user.setUser_birthday(date);
				user.setUser_address(textAddress.getText());
				user.setUser_phone(textPhone.getText());
				user.setUser_email(textEmail.getText());
				user.setUser_skype(textSkype.getText());
				user.setUser_details(textDetails.getText());
				user.setLogin_name(textLogin.getText());
				if (changePassword)
					user.setLogin_password(textPassword.getText().hashCode());
				user.setLogin_question(textQuestion.getText());
				if (changeAnswer)
					user.setLogin_answer(textAnswer.getText().hashCode());
				user.setRole_name(comboRoles.getSelectionModel().getSelectedItem().getRole_name());
			}
			dialogStage.close();
		}
	}
	
	@FXML
	private void cancelButton() {
		dialogStage.close();
	}
	
	@FXML
	private void changePassword() {
		changePassword = true;
	}
	
	@FXML
	private void changeAnswer() {
		changeAnswer = true;
	}
	
	// ChangeLanguage for elements
	public void changeLanguage () {
		labelRoleName.setText(Strings.RegistrationLabelRoleName.getValue(Indexes.AppLanguage_index.getValue()));
		labelLogin.setText(Strings.RegistrationLabelLogin.getValue(Indexes.AppLanguage_index.getValue()));
		labelPassword.setText(Strings.RegistrationLabelPassword.getValue(Indexes.AppLanguage_index.getValue()));
		labelPasswordRepeat.setText(Strings.RegistrationLabelPasswordRepeat.getValue(Indexes.AppLanguage_index.getValue()));
		labelQuestion.setText(Strings.RegistrationLabelQuestion.getValue(Indexes.AppLanguage_index.getValue()));
		labelAnswer.setText(Strings.RegistrationLabelAnswer.getValue(Indexes.AppLanguage_index.getValue()));
		labelFirstName.setText(Strings.RegistrationLabelFirstName.getValue(Indexes.AppLanguage_index.getValue()));
		labelLastName.setText(Strings.RegistrationLabelLastName.getValue(Indexes.AppLanguage_index.getValue()));
		labelBirthday.setText(Strings.RegistrationLabelBirthday.getValue(Indexes.AppLanguage_index.getValue()));
		labelAddress.setText(Strings.RegistrationLabelAddress.getValue(Indexes.AppLanguage_index.getValue()));
		labelPhone.setText(Strings.RegistrationLabelPhone.getValue(Indexes.AppLanguage_index.getValue()));
		labelEmail.setText(Strings.RegistrationLabelEmail.getValue(Indexes.AppLanguage_index.getValue()));
		labelSkype.setText(Strings.RegistrationLabelSkype.getValue(Indexes.AppLanguage_index.getValue()));
		labelDetails.setText(Strings.RegistrationLabelDetails.getValue(Indexes.AppLanguage_index.getValue()));
		textLogin.setPromptText(Strings.RegistrationTextLogin.getValue(Indexes.AppLanguage_index.getValue()));
		textPassword.setPromptText(Strings.RegistrationTextPassword.getValue(Indexes.AppLanguage_index.getValue()));
		textPasswordRepeat.setPromptText(Strings.RegistrationTextPasswordRepeat.getValue(Indexes.AppLanguage_index.getValue()));
		textQuestion.setPromptText(Strings.RegistrationTextQuestion.getValue(Indexes.AppLanguage_index.getValue()));
		textAnswer.setPromptText(Strings.RegistrationTextAnswer.getValue(Indexes.AppLanguage_index.getValue()));
		textFirstName.setPromptText(Strings.RegistrationTextFirstName.getValue(Indexes.AppLanguage_index.getValue()));
		textLastName.setPromptText(Strings.RegistrationTextLastName.getValue(Indexes.AppLanguage_index.getValue()));
		textBirthday.setPromptText(Strings.RegistrationTextBirthday.getValue(Indexes.AppLanguage_index.getValue()));
		textAddress.setPromptText(Strings.RegistrationTextAddress.getValue(Indexes.AppLanguage_index.getValue()));
		textPhone.setPromptText(Strings.RegistrationTextPhone.getValue(Indexes.AppLanguage_index.getValue()));
		textEmail.setPromptText(Strings.RegistrationTextEmail.getValue(Indexes.AppLanguage_index.getValue()));
		textSkype.setPromptText(Strings.RegistrationTextSkype.getValue(Indexes.AppLanguage_index.getValue()));
		textDetails.setPromptText(Strings.RegistrationTextDetails.getValue(Indexes.AppLanguage_index.getValue()));
		okButton.setText(Strings.OKButton.getValue(Indexes.AppLanguage_index.getValue()));
		cancelButton.setText(Strings.CancelButton.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	private boolean isValid() {
		boolean isValid = true;
		if (comboRoles.getSelectionModel().getSelectedItem() == null) {
			comboRoles.requestFocus();
			isValid = false;
		}
		else if (textLogin.getText().equals("")) {
			textLogin.requestFocus();
			isValid = false;
		}
		else if (changePassword && (textPassword.getText().equals(""))) {
			textPassword.requestFocus();
			isValid = false;
		}
		else if (changePassword && (textPasswordRepeat.getText().equals(""))) {
			textPasswordRepeat.requestFocus();
			isValid = false;
		}
		else if (textFirstName.getText().equals("")) {
			textFirstName.setText(textLogin.getText());
			textFirstName.requestFocus();
			isValid = false;
		}
		else if (!textQuestion.getText().equals("") && changeAnswer && textAnswer.getText().equals("")) {
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
		if (changePassword && (textPassword.getText().hashCode() != textPasswordRepeat.getText().hashCode())) {
			textPasswordRepeat.requestFocus();
			isValid = false;
		}
		if (!isValid) {
			Alert splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.PasswordError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();	
			return isValid;			
		}
		try {
			if (!textBirthday.getText().equals("")) 
				LocalDate.parse(textBirthday.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			if (!textPhone.getText().equals(""))
				Long.parseLong(textPhone.getText());
			if (!textEmail.getText().equals(""))
				InternetAddress.parse(textEmail.getText());
		}
		catch (DateTimeParseException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			textBirthday.requestFocus();
			isValid = false;
		}
		catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			textPhone.requestFocus();
			isValid = false;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			textEmail.requestFocus();
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
		// Roles list
		comboRoles.setCellFactory(new Callback<ListView<Role>, ListCell<Role>>() {		
			@Override
			public ListCell<Role> call(ListView<Role> param) {
				// TODO Auto-generated method stub
				ListCell<Role> listCell = new ListCell<Role>() {
					@Override
					protected void updateItem(Role role, boolean empty) {
						super.updateItem(role, empty);      
						if(role != null) {
							setText(role.getRole_name());
						}
						else {
							setText(null);
						}
					}
				};
            	return listCell;
			}
		});
		comboRoles.setButtonCell(new ListCell<Role>() {
			@Override
			protected void updateItem(Role role, boolean empty) {
				super.updateItem(role, empty);      
				if(role != null) {
					setText(role.getRole_name());
				}
				else {
					setText(null);
				}
			}
		});
	}
	
	// List getters
	private void getListRoles(ObservableList<Role> listRoles) {
		String rolesTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.RolesTable_id.getValue());
		ResultSet result = RolesTable.getInstance().executeSelect("", rolesTable+".role_id, role_name, role_details ",
					"FROM "+rolesTable, "", "", "", "");
		try {
			while (result.next()) {
				listRoles.add(new Role(result.getInt(1),result.getString(2),result.getString(3)));
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
		listRoles.forEach(role -> {
			if (role.getRole_name().equals(user.getRole_name())) {
				comboRoles.getSelectionModel().select(role);
				return;
			}
		});
		textLogin.setText(user.getLogin_name());
		textPassword.setText("");
		textPasswordRepeat.setText("");
		textQuestion.setText(user.getLogin_question());
		textAnswer.setText("");
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
	
	public ComboBox<Role> getComboRoles() {
		return comboRoles;
	}

}
