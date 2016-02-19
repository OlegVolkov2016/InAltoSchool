package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Lesson {
	
	// Course lesson
	private IntegerProperty lesson_id;
	private IntegerProperty course_id;
	private StringProperty lesson_name;
	private StringProperty lesson_details;
	private StringProperty course_name;
	private StringProperty author_name;
	
	public Lesson() {
		// TODO Auto-generated constructor stub
	}
	
	public Lesson(int lesson_id, int course_id, String lesson_name, String lesson_details, String course_name, String author_name) {
		this.lesson_id = new SimpleIntegerProperty(lesson_id);
		this.course_id = new SimpleIntegerProperty(course_id);
		this.lesson_name = new SimpleStringProperty(lesson_name);
		this.lesson_details = new SimpleStringProperty(lesson_details);
		this.course_name = new SimpleStringProperty(course_name);
		this.author_name = new SimpleStringProperty(author_name);
	}

	// Getters and setters
	public IntegerProperty lesson_idProperty() {
		return lesson_id;
	}
	
	public int getLesson_id() {
		return lesson_id.get();
	}
	
	public void setLesson_id(int lesson_id) {
		this.lesson_id.set(lesson_id);
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
	
	public StringProperty lesson_nameProperty() {
		return lesson_name;
	}
	
	public String getLesson_name() {
		return lesson_name.get();
	}
	
	public void setLesson_name(String lesson_name) {
		this.lesson_name.set(lesson_name);
	}
	
	public StringProperty lesson_detailsProperty() {
		return lesson_details;
	}
	
	public String getLesson_details() {
		return lesson_details.get();
	}
	
	public void setLesson_details(String lesson_details) {
		this.lesson_details.set(lesson_details);
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
