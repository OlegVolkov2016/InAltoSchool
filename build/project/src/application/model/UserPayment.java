package application.model;

import java.time.LocalDate;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserPayment {
	
	// School course access
	private IntegerProperty users_payment_id;
	private IntegerProperty user_id;
	private IntegerProperty course_id;
	private ObjectProperty<LocalDate> users_payment_date;
	private DoubleProperty users_payment_sum;
	private StringProperty course_name;
	private StringProperty user_name;
	private IntegerProperty author_id;
	
	public UserPayment() {
		// TODO Auto-generated constructor stub
	}
	
	public UserPayment(int users_payment_id, int user_id, int course_id, LocalDate users_payment_date, 
			double users_payment_sum, String course_name, String user_name, int author_id) {
		this.users_payment_id = new SimpleIntegerProperty(users_payment_id);
		this.user_id = new SimpleIntegerProperty(user_id);
		this.course_id = new SimpleIntegerProperty(course_id);
		this.users_payment_date = new SimpleObjectProperty<LocalDate>(users_payment_date);
		this.users_payment_sum = new SimpleDoubleProperty(users_payment_sum);
		this.course_name = new SimpleStringProperty(course_name);
		this.user_name = new SimpleStringProperty(user_name);
		this.author_id = new SimpleIntegerProperty(author_id);
	}

	// Getters and setters
	public IntegerProperty users_payment_idProperty() {
		return users_payment_id;
	}
	
	public int getUsers_payment_id() {
		return users_payment_id.get();
	}
	
	public void setUsers_payment_id(int users_payment_id) {
		this.users_payment_id.set(users_payment_id);
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
	
	public IntegerProperty course_idProperty() {
		return course_id;
	}

	public int getCourse_id() {
		return course_id.get();
	}
	
	public void setCourse_id(int course_id) {
		this.course_id.set(course_id);
	}
	
	public ObjectProperty<LocalDate> users_payment_dateProperty() {
		return users_payment_date;
	}

	public LocalDate getUsers_payment_date() {
		return users_payment_date.get();
	}
	public void setUsers_payment_date(LocalDate users_payment_date) {
		this.users_payment_date.set(users_payment_date);
	}
	
	public DoubleProperty users_payment_sumProperty() {
		return users_payment_sum;
	}

	public double getUsers_payment_sum() {
		return users_payment_sum.get();
	}
	
	public void setUsers_payment_sum(double users_payment_sum) {
		this.users_payment_sum.set(users_payment_sum);
	}
	
	public StringProperty course_nameProperty() {
		return course_name;
	}

	public String getCourse_name() {
		return course_name.get();
	}
	
	public void setCourse_name(String course_name) {
		this.course_name.set(course_name);
	}
	
	public StringProperty user_nameProperty() {
		return user_name;
	}
	
	public String getUser_name() {
		return user_name.get();
	}
	
	public void setUser_name(String user_name) {
		this.user_name.set(user_name);
	}
	
	public IntegerProperty author_idProperty() {
		return author_id;
	}
	
	public int getAuthor_id() {
		return author_id.get();
	}
	
	public void setAuthor_id(int author_id) {
		this.author_id.set(author_id);
	}
	
}
