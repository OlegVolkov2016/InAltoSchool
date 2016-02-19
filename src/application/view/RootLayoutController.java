package application.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import application.InAltoSchool;
import application.Indexes;
import application.Strings;
import application.database.InAltoSchoolDatabase;
import application.database.LoginsTable;
import application.database.RolesTable;
import application.database.UsersTable;
import application.model.Login;
import application.model.User;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class RootLayoutController {
	
	// Main application reference
	private InAltoSchool mainApp;
	// Root layout reference
	private RootLayout rootLayout;
	
	// Menus references
	@FXML
	private Menu aboutMenu, educationMenu, usersMenu, loginMenu, administrationMenu, languageMenu;
	
	@FXML
	private MenuItem whoAreWeMenu, advantagesMenu, contactsMenu, exitMenu, coursesMenu, projectsMenu, teachersMenu, studentsMenu,
		authorizationMenu, registrationMenu, editProfileMenu, tablesMenu, usersTableMenu, projectsTableMenu,
		coursesTableMenu, lessonsTableMenu, tasksTableMenu, projectsExecutionsTableMenu, coursesExecutionsTableMenu, lessonsExecutionsTableMenu,
		tasksExecutionsTableMenu, userPaymentsMenu, languageUKMenu, languageENMenu;
	
	@FXML
	private ToggleGroup languageToggle;
	
	// StatusBar references
	@FXML
	private Label leftStatus, rightStatus;
	
	@FXML
	private void initialize() {
		languageToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				Indexes.AppLanguage_index.setValue(((RadioMenuItem) newValue).getParentMenu().getItems().indexOf(((RadioMenuItem) newValue)));
				this.changeLanguage();
				this.mainApp.getRootLayout().changeLanguage();
			}
		});			
	}
	
	// Menus events
	@FXML
	private void whoAreWeMenu() {
        Stage dialogStage = new Stage();
        WebViewDialog viewDialog = new WebViewDialog(dialogStage);
        viewDialog.getController().loadContent(Strings.WhoAreWeDialogTextArea.getValue(Indexes.AppLanguage_index.getValue()));
		dialogStage.setTitle(Strings.WhoAreWeDialogTitle.getValue(Indexes.AppLanguage_index.getValue()));
		Scene scene = new Scene(viewDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
	}
	
	@FXML
	private void advantagesMenu() {
        Stage dialogStage = new Stage();
        WebViewDialog viewDialog = new WebViewDialog(dialogStage);
        viewDialog.getController().loadContent(Strings.AdvantagesDialogTextArea.getValue(Indexes.AppLanguage_index.getValue()));
		dialogStage.setTitle(Strings.AdvantagesDialogTitle.getValue(Indexes.AppLanguage_index.getValue()));
		Scene scene = new Scene(viewDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
	}
	
	@FXML
	private void contactsMenu() {
		Stage dialogStage = new Stage();
		ContactsDialog contactsDialog = new ContactsDialog(dialogStage);
        Scene scene = new Scene(contactsDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        dialogStage.showAndWait();
        if (contactsDialog.getController().IsSendButton())
        	sendEmail(contactsDialog.getController().getEmail(),Strings.GmailUsername.getValue(0),"From: "+contactsDialog.getController().getName()+
        		", email: "+contactsDialog.getController().getEmail()+", course: "+contactsDialog.getController().getCourseName(), 
        		contactsDialog.getController().getMessage());
	}
	
	@FXML
	private void exitMenu() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
		alert.setTitle(Strings.ExitDialogTitle.getValue(Indexes.AppLanguage_index.getValue()));
		alert.setHeaderText(null);
		alert.setContentText(Strings.ExitDialogText.getValue(Indexes.AppLanguage_index.getValue()));
		ButtonType okButton = new ButtonType(Strings.OKButton.getValue(Indexes.AppLanguage_index.getValue()), ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType(Strings.CancelButton.getValue(Indexes.AppLanguage_index.getValue()), ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(okButton,cancelButton);
		if (alert.showAndWait().get() == okButton)
			System.exit(0);
	}
	
	@FXML
	private void coursesMenu() {
		if (rootLayout.getMainSplitPanesList()[Indexes.CoursesSplitPane_id.getValue()] == null)
			rootLayout.getMainSplitPanesList()[Indexes.CoursesSplitPane_id.getValue()] = new CoursesSplitPane(this.mainApp);
		rootLayout.setMainSplitPane(rootLayout.getMainSplitPanesList()[Indexes.CoursesSplitPane_id.getValue()]);
		rootLayout.setCenter(rootLayout.getMainSplitPanesList()[Indexes.CoursesSplitPane_id.getValue()]);
	}
	
	@FXML
	private void projectsMenu() {
		if (rootLayout.getMainSplitPanesList()[Indexes.ProjectsSplitPane_id.getValue()] == null)
			rootLayout.getMainSplitPanesList()[Indexes.ProjectsSplitPane_id.getValue()] = new ProjectsSplitPane(this.mainApp);
		rootLayout.setMainSplitPane(rootLayout.getMainSplitPanesList()[Indexes.ProjectsSplitPane_id.getValue()]);
		rootLayout.setCenter(rootLayout.getMainSplitPanesList()[Indexes.ProjectsSplitPane_id.getValue()]);
	}
	
	@FXML
	private void teachersMenu() {
		if (rootLayout.getMainSplitPanesList()[Indexes.TeachersSplitPane_id.getValue()] == null)
			rootLayout.getMainSplitPanesList()[Indexes.TeachersSplitPane_id.getValue()] = new TeachersSplitPane(this.mainApp);
		rootLayout.setMainSplitPane(rootLayout.getMainSplitPanesList()[Indexes.TeachersSplitPane_id.getValue()]);
		rootLayout.setCenter(rootLayout.getMainSplitPanesList()[Indexes.TeachersSplitPane_id.getValue()]);
		((TeachersSplitPane) mainApp.getRootLayout().getMainSplitPane()).getController().getListUsers();
	}
	
	@FXML
	private void studentsMenu() {
		if (rootLayout.getMainSplitPanesList()[Indexes.StudentsSplitPane_id.getValue()] == null)
			rootLayout.getMainSplitPanesList()[Indexes.StudentsSplitPane_id.getValue()] = new StudentsSplitPane(this.mainApp);
		rootLayout.setMainSplitPane(rootLayout.getMainSplitPanesList()[Indexes.StudentsSplitPane_id.getValue()]);
		rootLayout.setCenter(rootLayout.getMainSplitPanesList()[Indexes.StudentsSplitPane_id.getValue()]);
		((StudentsSplitPane) mainApp.getRootLayout().getMainSplitPane()).getController().getListUsers();
	}
	
	@FXML
	public void authorizationMenu() {
		boolean isRepeat;
		Stage dialogStage = new Stage();
		AuthorizationDialog authorizationDialog = new AuthorizationDialog(dialogStage);
        Scene scene = new Scene(authorizationDialog);
        dialogStage.setScene(scene);
        dialogStage.initOwner(mainApp.getPrimaryStage());
        dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
        do {
        	isRepeat = false;
        	authorizationDialog.getController().clearFlags();
        	dialogStage.showAndWait();
        	if (authorizationDialog.getController().isOKButton()) {
        		if (loginUser(authorizationDialog.getController().getLogin(),authorizationDialog.getController().getPassword().hashCode())) {
        			Strings.LeftStatus.setValue(mainApp.getLogin().getRole_name()+": "+mainApp.getLogin().getUser_name(),
        					mainApp.getLogin().getRole_name()+": "+mainApp.getLogin().getUser_name());
        			editProfileMenu.setDisable(false);
        			changeStatus();
        			rootLayout.changeLogin();
					Alert splitAlert = new Alert(AlertType.INFORMATION);
					((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
					splitAlert.setTitle(Strings.InformationTitle.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setHeaderText(Strings.AuthorizationHeader.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setContentText(Strings.AuthorizationContent.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.showAndWait();
        		}
        		else {
        			isRepeat = true;
        			Alert splitAlert = new Alert(AlertType.ERROR);
        			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
        			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
        			splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
        			splitAlert.setContentText(Strings.LoginError.getValue(Indexes.AppLanguage_index.getValue()));
        			splitAlert.showAndWait();
        		}
        	}
        	else if (authorizationDialog.getController().isRegistrationLink()) {
        		registrationMenu();
        	}
        	else if (authorizationDialog.getController().isForgotLink()) {
        		if (loginUser(authorizationDialog.getController().getLogin(),("").hashCode())) {
        			if (!mainApp.getLogin().getLogin_question().equals("")) {
        				forgotMenu();
        			}
        			else {
        				isRepeat = true;
        				Alert splitAlert = new Alert(AlertType.ERROR);
        				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
        				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
        				splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
        				splitAlert.setContentText(Strings.QuestionError.getValue(Indexes.AppLanguage_index.getValue()));
        				splitAlert.showAndWait();
        			}      			
        		}
        		else {
        			isRepeat = true;
        			Alert splitAlert = new Alert(AlertType.ERROR);
        			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
        			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
        			splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
        			splitAlert.setContentText(Strings.LoginError.getValue(Indexes.AppLanguage_index.getValue()));
        			splitAlert.showAndWait();
        		}    	
        	}
        }
        while (isRepeat);
	}
	
	private void forgotMenu() {
		boolean isRepeat;
		Stage dialogStage = new Stage();
		ForgotDialog forgotDialog = new ForgotDialog(dialogStage);
		forgotDialog.getController().getTextQuestion().setText(mainApp.getLogin().getLogin_question());
		Scene scene = new Scene(forgotDialog);
		dialogStage.setScene(scene);
		dialogStage.initOwner(mainApp.getPrimaryStage());
		dialogStage.setMinWidth(((Pane) scene.getRoot()).getPrefWidth()+16);
        dialogStage.setMinHeight(((Pane) scene.getRoot()).getPrefHeight()+38);
		do {      
			isRepeat = false;
			forgotDialog.getController().clearFlags();
			dialogStage.showAndWait();
			if (forgotDialog.getController().isOKButton()) {
				if (mainApp.getLogin().getLogin_answer() == forgotDialog.getController().getAnswer().hashCode()) {
					Strings.LeftStatus.setValue(mainApp.getLogin().getRole_name()+": "+mainApp.getLogin().getUser_name(),
							mainApp.getLogin().getRole_name()+": "+mainApp.getLogin().getUser_name());
					editProfileMenu.setDisable(false);
					changeStatus();
					rootLayout.changeLogin();
					Alert splitAlert = new Alert(AlertType.INFORMATION);
					((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
					splitAlert.setTitle(Strings.InformationTitle.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setHeaderText(Strings.AuthorizationHeader.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setContentText(Strings.AuthorizationContent.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.showAndWait();
				}
				else {
					isRepeat = true;
					mainApp.setLogin(null);
					Alert splitAlert = new Alert(AlertType.ERROR);
					((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
					splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setContentText(Strings.AnswerError.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.showAndWait();
				}
			}
    	    else if (forgotDialog.getController().isRegistrationLink()) {
    	       	registrationMenu();
    	    }
		}
		while (isRepeat);
	}
	
	@FXML
	private void registrationMenu() {
		Stage dialogStage = new Stage();
		RegistrationDialog registrationDialog = new RegistrationDialog(dialogStage);
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
	private void editProfileMenu() {
		User user = selectUser();
		Stage dialogStage = new Stage();
		RegistrationDialog registrationDialog = new RegistrationDialog(dialogStage);
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
	private void usersTableMenu() {
		if (rootLayout.getMainSplitPanesList()[Indexes.UsersTableSplitPane_id.getValue()] == null)
			rootLayout.getMainSplitPanesList()[Indexes.UsersTableSplitPane_id.getValue()] = new UsersTableSplitPane(this.mainApp);
		rootLayout.setMainSplitPane(rootLayout.getMainSplitPanesList()[Indexes.UsersTableSplitPane_id.getValue()]);
		rootLayout.setCenter(rootLayout.getMainSplitPanesList()[Indexes.UsersTableSplitPane_id.getValue()]);
	}
	
	// ChangeLanguage for RootLayout
	public void changeLanguage () {
		aboutMenu.setText(Strings.AboutMenu.getValue(Indexes.AppLanguage_index.getValue()));
		educationMenu.setText(Strings.EducationMenu.getValue(Indexes.AppLanguage_index.getValue()));
		usersMenu.setText(Strings.UsersMenu.getValue(Indexes.AppLanguage_index.getValue()));
		loginMenu.setText(Strings.LoginMenu.getValue(Indexes.AppLanguage_index.getValue()));
		administrationMenu.setText(Strings.AdministartionMenu.getValue(Indexes.AppLanguage_index.getValue()));
		languageMenu.setText(Strings.LanguageMenu.getValue(Indexes.AppLanguage_index.getValue()));
		whoAreWeMenu.setText(Strings.WhoAreWeMenu.getValue(Indexes.AppLanguage_index.getValue()));
		advantagesMenu.setText(Strings.AdvantagesMenu.getValue(Indexes.AppLanguage_index.getValue()));
		contactsMenu.setText(Strings.ContactsMenu.getValue(Indexes.AppLanguage_index.getValue()));
		exitMenu.setText(Strings.ExitMenu.getValue(Indexes.AppLanguage_index.getValue()));
		coursesMenu.setText(Strings.CoursesMenu.getValue(Indexes.AppLanguage_index.getValue()));
		projectsMenu.setText(Strings.ProjectsMenu.getValue(Indexes.AppLanguage_index.getValue()));
		teachersMenu.setText(Strings.TeachersMenu.getValue(Indexes.AppLanguage_index.getValue()));
		studentsMenu.setText(Strings.StudentsMenu.getValue(Indexes.AppLanguage_index.getValue()));
		authorizationMenu.setText(Strings.AuthorizationMenu.getValue(Indexes.AppLanguage_index.getValue()));
		registrationMenu.setText(Strings.RegistrationMenu.getValue(Indexes.AppLanguage_index.getValue()));
		editProfileMenu.setText(Strings.EditProfileMenu.getValue(Indexes.AppLanguage_index.getValue()));
		usersTableMenu.setText(Strings.UsersTableMenu.getValue(Indexes.AppLanguage_index.getValue()));
//		tablesMenu.setText(Strings.TablesMenu.getValue(Indexes.AppLanguage_index.getValue()));		
//		rolesTableMenu.setText(Strings.RolesTableMenu.getValue(Indexes.AppLanguage_index.getValue()));
//		loginsTableMenu.setText(Strings.LoginsTableMenu.getValue(Indexes.AppLanguage_index.getValue()));
//		projectsTableMenu.setText(Strings.ProjectsMenu.getValue(Indexes.AppLanguage_index.getValue()));
//		coursesTableMenu.setText(Strings.CoursesMenu.getValue(Indexes.AppLanguage_index.getValue()));
//		lessonsTableMenu.setText(Strings.LessonsTableMenu.getValue(Indexes.AppLanguage_index.getValue()));
//		tasksTableMenu.setText(Strings.TasksTableMenu.getValue(Indexes.AppLanguage_index.getValue()));
//		projectsExecutionsTableMenu.setText(Strings.ProjectsExecutionsTableMenu.getValue(Indexes.AppLanguage_index.getValue()));
//		coursesExecutionsTableMenu.setText(Strings.CoursesExecutionsTableMenu.getValue(Indexes.AppLanguage_index.getValue()));
//		lessonsExecutionsTableMenu.setText(Strings.LessonsExecutionsTableMenu.getValue(Indexes.AppLanguage_index.getValue()));
//		tasksExecutionsTableMenu.setText(Strings.TasksExecutionsTableMenu.getValue(Indexes.AppLanguage_index.getValue()));
//		userPaymentsMenu.setText(Strings.UserPaymentsTableMenu.getValue(Indexes.AppLanguage_index.getValue()));
		languageUKMenu.setText(Strings.LanguageUKMenu.getValue(Indexes.AppLanguage_index.getValue()));
		languageENMenu.setText(Strings.LanguageENMenu.getValue(Indexes.AppLanguage_index.getValue()));
		leftStatus.setText(Strings.LeftStatus.getValue(Indexes.AppLanguage_index.getValue()));
		rightStatus.setText(Strings.RightStatus.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// ChangeStatus for RootLayout
	public void changeStatus() {
		leftStatus.setText(Strings.LeftStatus.getValue(Indexes.AppLanguage_index.getValue()));
		rightStatus.setText(Strings.RightStatus.getValue(Indexes.AppLanguage_index.getValue()));	
	}
	
	// Send Message
	private void sendEmail(String from, String to, String header, String text) {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		Session session = Session.getInstance(properties,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Strings.GmailUsername.getValue(0),Strings.GmailPassword.getValue(0));
			}
		  });
	    try {
		    MimeMessage message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(from));
		    message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		    message.setSubject(header);
		    message.setText(text);
		    Transport.send(message);
			Alert splitAlert = new Alert(AlertType.INFORMATION);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.InformationTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.MailHeader.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.MailContent.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();
	    } catch (MessagingException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
	    	System.out.println("MessagingException when sending message:"+e.getMessage());
		}
	}
	
	// Login User
	private boolean loginUser(String name, int password) {
		ResultSet result;
		String loginsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LoginsTable_id.getValue());
		String rolesTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.RolesTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		if (password == ("").hashCode())
			result = LoginsTable.getInstance().executeSelect("", loginsTable+".login_id, "+loginsTable+".role_id, login_name, login_password, login_question, login_answer, "
					+"role_name, CONCAT(user_firstname, ' ', user_lastname), "+usersTable+".user_id ", "FROM "+loginsTable+" JOIN "+rolesTable+" ON ("+loginsTable+".role_id = "+rolesTable+".role_id) JOIN "
					+usersTable+" ON ("+loginsTable+".login_id = "+usersTable+".login_id)", "WHERE login_name = \'"+name+"\'", "", "", "");
		else
			result = LoginsTable.getInstance().executeSelect("", loginsTable+".login_id, "+loginsTable+".role_id, login_name, login_password, login_question, login_answer, "
				+"role_name, CONCAT(user_firstname, ' ', user_lastname), "+usersTable+".user_id ", "FROM "+loginsTable+" JOIN "+rolesTable+" ON ("+loginsTable+".role_id = "+rolesTable+".role_id) JOIN "
				+usersTable+" ON ("+loginsTable+".login_id = "+usersTable+".login_id)", "WHERE (login_name = \'"+name+"\') AND (login_password = "+password+")",
				"", "", "");
		try {
			if (result.next()) {
				mainApp.setLogin(new Login(result.getInt(1),result.getInt(2),result.getString(3),result.getInt(4),result.getString(5),result.getInt(6),
					result.getString(7),result.getString(8),result.getInt(9)));
			}
			else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Alert splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.SelectError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();
		}
		return true;
	}
	
	// Select User info
	private User selectUser() {
		String loginsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LoginsTable_id.getValue());
		String rolesTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.RolesTable_id.getValue());
		String usersTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.UsersTable_id.getValue());
		ResultSet result = UsersTable.getInstance().executeSelect("", usersTable+".user_id, "+usersTable+".login_id, user_firstname, user_lastname, "
				+ "user_birthday, user_address, user_phone, user_email, user_skype, user_details, login_name, login_password, login_question, "
				+ "login_answer, role_name ", "FROM "+usersTable+" JOIN "+loginsTable+" ON ("+usersTable+".login_id = "+loginsTable+".login_id) "
				+"JOIN "+rolesTable+" ON ("+usersTable+".login_id = "+loginsTable+".login_id) AND ("+loginsTable+".role_id = "+rolesTable+".role_id) ", 
				"WHERE "+loginsTable+".login_id = "+mainApp.getLogin().getLogin_id(), "", "", "");
		try {
			if (result.next()) {
				LocalDate date;
				if (result.getString(5) == null)
					date = null;
				else
					date = LocalDate.parse(result.getString(5), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				return new User(result.getInt(1),result.getInt(2),result.getString(3),result.getString(4),date,result.getString(6),result.getString(7),
						result.getString(8),result.getString(9),result.getString(10),result.getString(11),result.getInt(12),result.getString(13),
						result.getInt(14),result.getString(15));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			Alert splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.SelectError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();
		}
		return null;
	}
	
	// Insert or update User
	private void updateUser(User user) {
		Login login;
    	if (user.getLogin_id() == 0) {
    		login = new Login(user.getLogin_id(),0,user.getLogin_name(),user.getLogin_password(),user.getLogin_question(),user.getLogin_answer(),
        			"Student",user.getUser_firstname()+" "+user.getUser_lastname(),user.getUser_id());
    		user.setLogin_id(insertLogin(login));
    		if (user.getLogin_id() != 0) {
    			if (UsersTable.getInstance().insert(user)) {
    				if (loginUser(login.getLogin_name(),login.getLogin_password())) {
            			Strings.LeftStatus.setValue(mainApp.getLogin().getRole_name()+": "+mainApp.getLogin().getUser_name(),
            					mainApp.getLogin().getRole_name()+": "+mainApp.getLogin().getUser_name());
            			editProfileMenu.setDisable(false);
            			changeStatus();
            			rootLayout.changeLogin();
    					Alert splitAlert = new Alert(AlertType.INFORMATION);
    					((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
    					splitAlert.setTitle(Strings.InformationTitle.getValue(Indexes.AppLanguage_index.getValue()));
    					splitAlert.setHeaderText(Strings.RegistrationHeader.getValue(Indexes.AppLanguage_index.getValue()));
    					splitAlert.setContentText(Strings.RegistrationContent.getValue(Indexes.AppLanguage_index.getValue()));
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
    		}
    	}
    	else {
    		login = new Login(user.getLogin_id(),mainApp.getLogin().getRole_id(),user.getLogin_name(),user.getLogin_password(),user.getLogin_question(),user.getLogin_answer(),
    				mainApp.getLogin().getRole_name(),user.getUser_firstname()+" "+user.getUser_lastname(),user.getUser_id());
    		if ((LoginsTable.getInstance().update(login.getLogin_id(),login)) && (UsersTable.getInstance().update(user.getUser_id(),user))) {
    			if (loginUser(login.getLogin_name(),login.getLogin_password())) {
        			Strings.LeftStatus.setValue(mainApp.getLogin().getRole_name()+": "+mainApp.getLogin().getUser_name(),
        					mainApp.getLogin().getRole_name()+": "+mainApp.getLogin().getUser_name());
        			editProfileMenu.setDisable(false);
        			changeStatus();
					Alert splitAlert = new Alert(AlertType.INFORMATION);
					((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
					splitAlert.setTitle(Strings.InformationTitle.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setHeaderText(Strings.EditProfileHeader.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.setContentText(Strings.EditProfileContent.getValue(Indexes.AppLanguage_index.getValue()));
					splitAlert.showAndWait();
    			}
    		}
    		else {
				Alert splitAlert = new Alert(AlertType.ERROR);
				((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
				splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.setContentText(Strings.UpdateError.getValue(Indexes.AppLanguage_index.getValue()));
				splitAlert.showAndWait();
    		}
    	}
	}
	
	// Insert new Login
	private int insertLogin(Login login) {
		int login_id = 0;
		ResultSet result;
		String loginsTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.LoginsTable_id.getValue());
		String rolesTable = InAltoSchoolDatabase.getInstance().getTables_names().get(Indexes.RolesTable_id.getValue());
		result = RolesTable.getInstance().executeSelect("", "role_id ", "FROM "+rolesTable, " WHERE role_name = \'"+login.getRole_name()+"\'", "", "", "");
		try {
			if (result.next()) {
				login.setRole_id(result.getInt(1));
				if (LoginsTable.getInstance().insert(login)) {
					result = LoginsTable.getInstance().executeSelect("", "login_id ", "FROM "+loginsTable, " WHERE login_name = \'"+login.getLogin_name()+"\'", "", "", "");
					if (result.next()) {
						login_id = result.getInt(1);
						login.setLogin_id(login_id);
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
		return login_id;
	}
	
	// Setters
	public void setMainApp(InAltoSchool mainApp) {
		this.mainApp = mainApp;
	}
	
	public void setRootLayout(RootLayout rootLayout) {
		this.rootLayout = rootLayout;
	}
	
	public Menu getAdministrationMenu() {
		return administrationMenu;
	}

}
