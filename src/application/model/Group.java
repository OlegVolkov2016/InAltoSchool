package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Group {
	
	// Course group
	private IntegerProperty group_id;
	private IntegerProperty course_id;
	private StringProperty group_name;
	private StringProperty group_details;
	private StringProperty course_name;
	private StringProperty author_name;
	
	public Group() {
		// TODO Auto-generated constructor stub
	}
	
	public Group(int group_id, int course_id, String group_name, String group_details, String course_name, String author_name) {
		this.group_id = new SimpleIntegerProperty(group_id);
		this.course_id = new SimpleIntegerProperty(course_id);
		this.group_name = new SimpleStringProperty(group_name);
		this.group_details = new SimpleStringProperty(group_details);
		this.course_name = new SimpleStringProperty(course_name);
		this.author_name = new SimpleStringProperty(author_name);
	}

	// Getters and setters
	public IntegerProperty group_idProperty() {
		return group_id;
	}
	
	public int getGroup_id() {
		return group_id.get();
	}
	
	public void setGroup_id(int group_id) {
		this.group_id.set(group_id);
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
	
	public StringProperty group_nameProperty() {
		return group_name;
	}
	
	public String getGroup_name() {
		return group_name.get();
	}
	
	public void setGroup_name(String group_name) {
		this.group_name.set(group_name);
	}
	
	public StringProperty group_detailsProperty() {
		return group_details;
	}
	
	public String getGroup_details() {
		return group_details.get();
	}
	
	public void setGroup_details(String group_details) {
		this.group_details.set(group_details);
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
	
	public StringProperty author_nameProperty() {
		return author_name;
	}
	
	public String getAuthor_name() {
		return author_name.get();
	}
	
	public void setAuthor_name(String author_name) {
		this.author_name.set(author_name);
	}
	
}
