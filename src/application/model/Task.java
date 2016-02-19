package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {
	
	// Lesson task
	private IntegerProperty task_id;
	private IntegerProperty lesson_id;
	private IntegerProperty project_id;
	private StringProperty task_name;
	private StringProperty task_details;
	private StringProperty lesson_name;
	private StringProperty course_name;
	private StringProperty project_name;
	private StringProperty author_name;
	
	
	public Task() {
		// TODO Auto-generated constructor stub
	}
	
	public Task(int task_id, int lesson_id, int project_id, String task_name, String task_details, String lesson_name, String course_name, String project_name, String author_name) {
		this.task_id = new SimpleIntegerProperty(task_id);
		this.lesson_id = new SimpleIntegerProperty(lesson_id);
		this.project_id = new SimpleIntegerProperty(project_id);
		this.task_name = new SimpleStringProperty(task_name);
		this.task_details = new SimpleStringProperty(task_details);
		this.lesson_name = new SimpleStringProperty(lesson_name);
		this.course_name = new SimpleStringProperty(course_name);
		this.project_name = new SimpleStringProperty(project_name);
		this.author_name = new SimpleStringProperty(author_name);
	}

	// Getters and setters
	public IntegerProperty task_idProperty() {
		return task_id;
	}
	
	public int getTask_id() {
		return task_id.get();
	}
	
	public void setTask_id(int task_id) {
		this.task_id.set(task_id);
	}
	
	public IntegerProperty lesson_idProperty() {
		return lesson_id;
	}

	public int getLesson_id() {
		return lesson_id.get();
	}
	
	public void setLesson_id(int lesson_id) {
		this.lesson_id.set(lesson_id);
	}
	
	public IntegerProperty project_idProperty() {
		return project_id;
	}
	
	public int getProject_id() {
		return project_id.get();
	}
	
	public void setProject_id(int project_id) {
		this.project_id.set(project_id);
	}
	
	public StringProperty task_nameProperty() {
		return task_name;
	}

	public String getTask_name() {
		return task_name.get();
	}
	
	public void setTask_name(String task_name) {
		this.task_name.set(task_name);
	}
	
	public StringProperty task_detailsProperty() {
		return task_details;
	}

	public String getTask_details() {
		return task_details.get();
	}
	
	public void setTask_details(String task_details) {
		this.task_details.set(task_details);
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
	
	public StringProperty course_nameProperty() {
		return course_name;
	}

	public String getCourse_name() {
		return course_name.get();
	}
	
	public void setCourse_name(String course_name) {
		this.course_name.set(course_name);
	}
	
	public StringProperty project_nameProperty() {
		return project_name;
	}

	public String getProject_name() {
		return project_name.get();
	}
	
	public void setProject_name(String project_name) {
		this.project_name.set(project_name);
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
