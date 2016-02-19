package application.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
	
	// School user
	private IntegerProperty user_id;
	private IntegerProperty login_id;
	private StringProperty user_firstname;
	private StringProperty user_lastname;
	private ObjectProperty<LocalDate> user_birthday;
	private StringProperty user_address;
	private StringProperty user_phone;
	private StringProperty user_email;
	private StringProperty user_skype;
	private StringProperty user_details;
	private StringProperty login_name;
	private IntegerProperty login_password;
	private StringProperty login_question;
	private IntegerProperty login_answer;
	private StringProperty role_name;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	public User(int user_id, int login_id, String user_firstname, String user_lastname, LocalDate user_birthday, String user_address, String user_phone, 
			String user_email, String user_skype, String user_details, String login_name, int login_password, String login_question, int login_answer, 
			String role_name) {
		this.user_id = new SimpleIntegerProperty(user_id);
		this.login_id = new SimpleIntegerProperty(login_id);
		this.user_firstname = new SimpleStringProperty(user_firstname);
		this.user_lastname = new SimpleStringProperty(user_lastname);
		this.user_birthday = new SimpleObjectProperty<LocalDate>(user_birthday);
		this.user_address = new SimpleStringProperty(user_address);
		this.user_phone = new SimpleStringProperty(user_phone);
		this.user_email = new SimpleStringProperty(user_email);
		this.user_skype = new SimpleStringProperty(user_skype);
		this.user_details = new SimpleStringProperty(user_details);
		this.login_name = new SimpleStringProperty(login_name);
		this.login_password = new SimpleIntegerProperty(login_password);
		this.login_question = new SimpleStringProperty(login_question);
		this.login_answer = new SimpleIntegerProperty(login_answer);
		this.role_name = new SimpleStringProperty(role_name);
	}

	// Getters and setters
	public IntegerProperty user_idProperty() {
		return user_id;
	}
	
	public int getUser_id() {
		return user_id.get();
	}
	
	public void setUser_id(int user_id) {
		this.user_id.set(user_id);
	}
	
	public IntegerProperty login_idProperty() {
		return login_id;
	}
	
	public int getLogin_id() {
		return login_id.get();
	}
	
	public void setLogin_id(int login_id) {
		this.login_id.set(login_id);
	}
	
	public StringProperty user_firstnameProperty() {
		return user_firstname;
	}
	
	public String getUser_firstname() {
		return user_firstname.get();
	}
	
	public void setUser_firstname(String user_firstname) {
		this.user_firstname.set(user_firstname);
	}
	
	public StringProperty user_lastnameProperty() {
		return user_lastname;
	}
	
	public String getUser_lastname() {
		return user_lastname.get();
	}
	
	public void setUser_lastname(String user_lastname) {
		this.user_lastname.set(user_lastname);
	}
	
	public ObjectProperty<LocalDate> user_birthdayProperty() {
		return user_birthday;
	}
	
	public LocalDate getUser_birthday() {
		return user_birthday.get();
	}
	
	public void setUser_birthday(LocalDate user_birthday) {
		this.user_birthday.set(user_birthday);
	}
	
	public StringProperty user_addressProperty() {
		return user_address;
	}
	
	public String getUser_address() {
		return user_address.get();
	}
	
	public void setUser_address(String user_address) {
		this.user_address.set(user_address);
	}
	
	public StringProperty user_phoneProperty() {
		return user_phone;
	}
	
	public String getUser_phone() {
		return user_phone.get();
	}
	
	public void setUser_phone(String user_phone) {
		this.user_phone.set(user_phone);
	}
	
	public StringProperty user_emailProperty() {
		return user_email;
	}
	
	public String getUser_email() {
		return user_email.get();
	}
	
	public void setUser_email(String user_email) {
		this.user_email.set(user_email);
	}
	
	public StringProperty user_skypeProperty() {
		return user_skype;
	}
	
	public String getUser_skype() {
		return user_skype.get();
	}
	
	public void setUser_skype(String user_skype) {
		this.user_skype.set(user_skype);
	}
	
	public StringProperty user_detailsProperty() {
		return user_details;
	}
	
	public String getUser_details() {
		return user_details.get();
	}
	
	public void setUser_details(String user_details) {
		this.user_details.set(user_details);
	}
	
	public StringProperty login_nameProperty() {
		return login_name;
	}
	
	public String getLogin_name() {
		return login_name.get();
	}
	
	public void setLogin_name(String login_name) {
		this.login_name.set(login_name);
	}
	
	public IntegerProperty login_passwordProperty() {
		return login_password;
	}
	
	public int getLogin_password() {
		return login_password.get();
	}
	
	public void setLogin_password(int login_password) {
		this.login_password.set(login_password);
	}
	
	public StringProperty login_questionProperty() {
		return login_question;
	}
	
	public String getLogin_question() {
		return login_question.get();
	}
	
	public void setLogin_question(String login_question) {
		this.login_question.set(login_question);
	}
	
	public IntegerProperty login_answerProperty() {
		return login_answer;
	}
	
	public int getLogin_answer() {
		return login_answer.get();
	}
	
	public void setLogin_answer(int login_answer) {
		this.login_answer.set(login_answer);
	}
	
	public StringProperty role_nameProperty() {
		return role_name;
	}
	
	public String getRole_name() {
		return role_name.get();
	}
	
	public void setRole_name(String role_name) {
		this.role_name.set(role_name);
	}
	
}
