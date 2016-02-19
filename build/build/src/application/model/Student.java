package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
	
	// Group student
	private IntegerProperty student_id;
	private IntegerProperty group_id;
	private IntegerProperty user_id;
	private StringProperty group_name;
	private StringProperty course_name;
	private StringProperty user_name;
	
	public Student() {
		// TODO Auto-generated constructor stub
	}
	
	public Student(int student_id, int group_id, int user_id, String group_name, String course_name, String user_name) {
		this.student_id = new SimpleIntegerProperty(student_id);
		this.group_id = new SimpleIntegerProperty(group_id);
		this.user_id = new SimpleIntegerProperty(user_id);
		this.group_name = new SimpleStringProperty(group_name);
		this.course_name = new SimpleStringProperty(course_name);
		this.user_name = new SimpleStringProperty(user_name);
	}

	// Getters and setters
	public IntegerProperty student_idProperty() {
		return student_id;
	}
	
	public int getStudent_id() {
		return student_id.get();
	}
	
	public void setStudent_id(int student_id) {
		this.student_id.set(student_id);
	}
	
	public IntegerProperty group_idProperty() {
		return group_id;
	}

	public int getGroup_id() {
		return group_id.get();
	}
	
	public void setGroup_id(int group_id) {
		this.group_id.set(group_id);
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
	
	public StringProperty group_nameProperty() {
		return group_name;
	}

	public String getGroup_name() {
		return group_name.get();
	}
	
	public void setGroup_name(String group_name) {
		this.group_name.set(group_name);
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
	
}
