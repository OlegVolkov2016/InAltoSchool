package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Login {
	
	// School user
	private IntegerProperty login_id;
	private IntegerProperty role_id;
	private StringProperty login_name;
	private IntegerProperty login_password;
	private StringProperty login_question;
	private IntegerProperty login_answer;
	private StringProperty role_name;
	private StringProperty user_name;
	private IntegerProperty user_id;
	
	public Login() {
		// TODO Auto-generated constructor stub
	}
	
	public Login(int login_id, int role_id, String login_name, int login_password, String login_question, int login_answer, String role_name, String user_name, int user_id) {
		this.login_id = new SimpleIntegerProperty(login_id);
		this.role_id = new SimpleIntegerProperty(role_id);
		this.login_name = new SimpleStringProperty(login_name);
		this.login_password = new SimpleIntegerProperty(login_password);
		this.login_question = new SimpleStringProperty(login_question);
		this.login_answer = new SimpleIntegerProperty(login_answer);
		this.role_name = new SimpleStringProperty(role_name);
		this.user_name = new SimpleStringProperty(user_name);
		this.user_id = new SimpleIntegerProperty(user_id);
	}

	// Getters and setters
	public IntegerProperty login_idProperty() {
		return login_id;
	}
	
	public int getLogin_id() {
		return login_id.get();
	}
	
	public void setLogin_id(int login_id) {
		this.login_id.set(login_id);
	}
	
	public IntegerProperty role_idProperty() {
		return role_id;
	}
	
	public int getRole_id() {
		return role_id.get();
	}
	
	public void setRole_id(int role_id) {
		this.role_id.set(role_id);
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
	
	public StringProperty user_nameProperty() {
		return user_name;
	}
	
	public String getUser_name() {
		return user_name.get();
	}
	
	public void setUser_firstName(String user_name) {
		this.user_name.set(user_name);
	}
	
	public IntegerProperty user_idProperty() {
		return user_id;
	}
	
	public int getUser_id() {
		return user_id.get();
	}
	
	public void setUser_id(int user_id) {
		this.user_id.set(user_id);
	}
	
}
