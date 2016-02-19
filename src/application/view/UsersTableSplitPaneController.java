package application.view;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import application.InAltoSchool;
import application.Indexes;
import application.Strings;
import application.database.InAltoSchoolDatabase;
import application.database.LoginsTable;
import application.database.RolesTable;
import application.database.UsersTable;
import application.model.Login;
import application.model.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class UsersTableSplitPaneController {

	// Main application reference 
	private InAltoSchool mainApp;
	
	// Observable lists
	ObservableList<User> listUsers;
	
	// Children elements references	
	@FXML
	private Button newButton, editButton, deleteButton;
	
	@FXML
	private Label labelUserTitle, labelInformation, labelRoleName, labelLogin, labelQuestion, labelAddress,
		labelPhone, labelEmail, labelSkype, labelDetails;
	
	@FXML
	private TextField textRoleName, textLogin, textQuestion, textAddress, textPhone, textEmail, textSkype;
	
	@FXML
	private TextArea textDetails;
	
	@FXML
	private TableView<User> tableUsers;
	
	@FXML
	private TableColumn<User, String> columnFirstName, columnLastName;
	
	@FXML
	private TableColumn<User, LocalDate> columnBirthday;
	
	@FXML
	private void initialize() {
		// Set Lists
		listUsers = FXCollections.observableArrayList();
		getListUsers();
		tableUsers.setItems(listUsers);
		columnFirstName.setCellValueFactory(cell -> cell.getValue().user_firstnameProperty());
		columnLastName.setCellValueFactory(cell -> cell.getValue().user_lastnameProperty());
		columnBirthday.setCellValueFactory(cell -> cell.getValue().user_birthdayProperty());
		// Set Handlers
		setHandlers();	
	}
	
	// Button Events
	@FXML
	private void newButton() {
		Stage dialogStage = new Stage();
		RegistrationDialog registrationDialog = new RegistrationDialog(dialogStage);
		registrationDialog.getController().getComboRoles().setDisable(false);
        Scene scene = new Scene(registrationDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (registrationDialog.getController().isOKButton()) {
        	updateUser(registrationDialog.getController().getUser());
        }
	}
	
	@FXML
	private void editButton() {
		User user = tableUsers.getSelectionModel().getSelectedItem();
		Stage dialogStage = new Stage();
		RegistrationDialog registrationDialog = new RegistrationDialog(dialogStage);
		registrationDialog.getController().getComboRoles().setDisable(false);
		registrationDialog.getController().setUser(user);
        Scene scene = new Scene(registrationDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (registrationDialog.getController().isOKButton()) {
        	updateUser(registrationDialog.getController().getUser());
        }
	}
	
	@FXML
	private void deleteButton() {
		User user = tableUsers.getSelectionModel().getSelectedItem();
		Alert splitAlert = new Alert(AlertType.CONFIRMATION);
		((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
		splitAlert.setTitle(Strings.ConfirmTitle.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setHeaderText(Strings.ConfirmDeleteHeader.getValue(Indexes.AppLanguage_index.getValue()));
		splitAlert.setContentText(Strings.ConfirmDeleteContent.getValue(Indexes.AppLanguage_index.getValue())+
				user.getUser_firstname()+" "+user.getUser_lastname()+"?");
		Optional<ButtonType> result = splitAlert.showAndWait();
		if (result.get() == ButtonType.OK) {
			if (LoginsTable.getInstance().delete(user.getLogin_id())) {
				listUsers.remove(user);
				if (listUsers.size() == 0)
					tableUsers.getSelectionModel().clearSelection();
			}
			else {
				splitAlert = new Alert(AlertType.ERROR);
				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setHeaderText(Strings.DeleteErrorHeader.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setContentText(Strings.DeleteErrorContent.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.showAndWait();
			}	
		}
	}
	
	// ChangeLanguage for elements
	public void changeLanguage () {
		labelUserTitle.setText(Strings.LabelStudentTitle.getValue(Indexes.AppLanguage_index.getValue()));
		labelInformation.setText(Strings.LabelInformation.getValue(Indexes.AppLanguage_index.getValue()));
		labelRoleName.setText(Strings.RegistrationLabelRoleName.getValue(Indexes.AppLanguage_index.getValue()));
		labelLogin.setText(Strings.RegistrationLabelLogin.getValue(Indexes.AppLanguage_index.getValue()));
		labelQuestion.setText(Strings.RegistrationLabelQuestion.getValue(Indexes.AppLanguage_index.getValue()));
		labelAddress.setText(Strings.RegistrationLabelAddress.getValue(Indexes.AppLanguage_index.getValue()));
		labelPhone.setText(Strings.RegistrationLabelPhone.getValue(Indexes.AppLanguage_index.getValue()));
		labelEmail.setText(Strings.RegistrationLabelEmail.getValue(Indexes.AppLanguage_index.getValue()));
		labelSkype.setText(Strings.RegistrationLabelSkype.getValue(Indexes.AppLanguage_index.getValue()));
		labelDetails.setText(Strings.RegistrationLabelDetails.getValue(Indexes.AppLanguage_index.getValue()));
		columnFirstName.setText(Strings.ColumnFirstName.getValue(Indexes.AppLanguage_index.getValue()));
		columnLastName.setText(Strings.ColumnLastName.getValue(Indexes.AppLanguage_index.getValue()));
		columnBirthday.setText(Strings.ColumnBirthday.getValue(Indexes.AppLanguage_index.getValue()));
		newButton.setText(Strings.NewButton.getValue(Indexes.AppLanguage_index.getValue()));
		editButton.setText(Strings.EditButton.getValue(Indexes.AppLanguage_index.getValue()));
		deleteButton.setText(Strings.DeleteButton.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// ChangeLogin for elements
	public void changeLogin () {
		tableUsers.getSelectionModel().clearSelection();
		newButton.setDisable(false);
		editButton.setDisable(true);
		deleteButton.setDisable(true);
	}
	
	// Set Handlers
	private void setHandlers() {
		// Users list
		tableUsers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				// TODO Auto-generated method stub
				if (newValue != null) {
					updateInformation(newValue);
					newButton.setDisable(false);
					if (mainApp.getLogin().getUser_id() != newValue.getUser_id()) {
						editButton.setDisable(false);
						deleteButton.setDisable(false);						
					}
					else {
						editButton.setDisable(false);
						deleteButton.setDisable(true);
					}
				}
				else {
					updateInformation(null);
					newButton.setDisable(false);
					editButton.setDisable(true);
					deleteButton.setDisable(true);
				}
			}
		});
		columnBirthday.setCellFactory(new Callback<TableColumn<User,LocalDate>, TableCell<User,LocalDate>>() {
			@Override
			public TableCell<User, LocalDate> call(TableColumn<User, LocalDate> param) {
				// TODO Auto-generated method stub
				TableCell<User, LocalDate> tableCell = new TableCell<User, LocalDate>() {
					@Override
					protected void updateItem(LocalDate date, boolean empty) {
						super.updateItem(date, empty);    
						setAlignment(Pos.CENTER);
						if(date != null) {
							setText(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
						}
						else {
							setText(null);
						}
					}
				};
				return tableCell;
			}
		});
	}

	// List getters
	public void getListUsers() {
		listUsers.clear();
		LocalDate date;
		String usersTable = InAltoSchoolDatabase .getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		String loginsTable = InAltoSchoolDatabase .getInstance().getTables_names().get(Indexes.LoginsTable_id.getValue());
		String rolesTable = InAltoSchoolDatabase .getInstance().getTables_names().get(Indexes.RolesTable_id.getValue());
		ResultSet result = UsersTable.getInstance().executeSelect("", usersTable+".user_id, "+usersTable+".login_id, user_firstname, user_lastname, "
				+ "user_birthday, user_address, user_phone, user_email, user_skype, user_details, login_name, login_password, login_question, "
				+ "login_answer, role_name ", "FROM "+usersTable+" JOIN "+loginsTable
				+" ON "+usersTable+".login_id = "+loginsTable+".login_id JOIN "+rolesTable +" ON "+loginsTable+".role_id = "+rolesTable+".role_id ", 
				"", "", "", "");
		try {
			while (result.next()) {
			if (result.getString(5) == null)
				date = null;
			else
				date = LocalDate.parse(result.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				listUsers.add(new User(result.getInt(1),result.getInt(2),result.getString(3),result.getString(4),date,result.getString(6),result.getString(7),
					result.getString(8),result.getString(9),result.getString(10),result.getString(11),result.getInt(12),result.getString(13),
					result.getInt(14),result.getString(15)));
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
	
	// Update Information
	private void updateInformation(User user) {
		if (user != null) {
			textRoleName.setText(user.getRole_name());
			textLogin.setText(user.getLogin_name());
			textQuestion.setText(user.getLogin_question());
			textAddress.setText(user.getUser_address());
			textPhone.setText(user.getUser_phone());
			textEmail.setText(user.getUser_email());
			textSkype.setText(user.getUser_skype());
			textDetails.setText(user.getUser_details());
		}
		else {
			textRoleName.setText("");
			textLogin.setText("");
			textQuestion.setText("");
			textAddress.setText("");
			textPhone.setText("");
			textEmail.setText("");
			textSkype.setText("");
			textDetails.setText("");
		}
	}
	
	// Insert or update User Payment
	private void updateUser(User user) {
		Login login;
    	if (user.getLogin_id() == 0) {
    		login = new Login(user.getLogin_id(),0,user.getLogin_name(),user.getLogin_password(),user.getLogin_question(),user.getLogin_answer(),
        			user.getRole_name(),user.getUser_firstname()+" "+user.getUser_lastname(),user.getUser_id());
    		login.setRole_id(getRoleID(login));
    		user.setLogin_id(insertLogin(login));
    		if (user.getLogin_id() != 0) {
    			user.setUser_id(insertUser(user));
    			listUsers.add(user);
    			tableUsers.getSelectionModel().select(user);
    		}
    	}
    	else {
    		login = new Login(user.getLogin_id(),0,user.getLogin_name(),user.getLogin_password(),user.getLogin_question(),user.getLogin_answer(),
    				user.getRole_name(),user.getUser_firstname()+" "+user.getUser_lastname(),user.getUser_id());
    		login.setRole_id(getRoleID(login));
    		if ((LoginsTable.getInstance().update(login.getLogin_id(),login)) && (UsersTable.getInstance().update(user.getUser_id(),user)))
    			updateInformation(user);
    		else {
				Alert splitAlert = new Alert(AlertType.ERROR);
				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setContentText(Strings.UpdateError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.showAndWait();
    		}
    	}
    	mainApp.getRootLayout().changeLogin();
	}
	
	// Insert User
	private int insertUser(User user) {
		int user_id = 0;
		String userTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		if (UsersTable.getInstance().insert(user)) {
			ResultSet result = UsersTable.getInstance().executeSelect("", "user_id ", "FROM "+userTable, " WHERE login_id = "+user.getLogin_id(), "", "", "");
			try {
				if (result.next()) {
					user_id = result.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Alert splitAlert = new Alert(AlertType.ERROR);
				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setContentText(Strings.SelectError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.showAndWait();
			}
		}
		else {
			Alert splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.InsertError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();
		}
		return user_id;
	}
	
	// Insert Login
	private int insertLogin(Login login) {
		int login_id = 0;
		String loginsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LoginsTable_id.getValue());
		if (LoginsTable.getInstance().insert(login)) {
			ResultSet result = LoginsTable.getInstance().executeSelect("", "login_id ", "FROM "+loginsTable, " WHERE login_name = \'"+login.getLogin_name()+"\'", "", "", "");
			try {
				if (result.next()) {
					login_id = result.getInt(1);
					login.setLogin_id(login_id);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Alert splitAlert = new Alert(AlertType.ERROR);
				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setContentText(Strings.SelectError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.showAndWait();
			}
		}
		else {
			Alert splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.InsertError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();
		}
		return login_id;
	}

	// Get Role_ID
	private int getRoleID(Login login) {
		int role_id = 0;
		String rolesTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.RolesTable_id.getValue());
		ResultSet result = RolesTable.getInstance().executeSelect("", "role_id ", "FROM "+rolesTable, " WHERE role_name = \'"+login.getRole_name()+"\'", "", "", "");
		try {
			if (result.next()) {
				role_id = result.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Alert splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.SelectError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();
		}
		return role_id;
	}
	
	// Getters and setters
	public InAltoSchool getMainApp() {
		return mainApp;
	}
	
	public void setMainApp(InAltoSchool mainApp) {
		this.mainApp = mainApp;
	}

}
