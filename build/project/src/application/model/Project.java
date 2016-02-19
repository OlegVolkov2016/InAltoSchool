package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Project {
	
	// School project
	private IntegerProperty project_id;
	private IntegerProperty author_id;
	private StringProperty project_name;
	private StringProperty project_details;
	private StringProperty author_name;
	
	public Project() {
		// TODO Auto-generated constructor stub
	}
	
	public Project(int project_id, int author_id, String project_name, String project_details, String author_name) {
		this.project_id = new SimpleIntegerProperty(project_id);
		this.author_id = new SimpleIntegerProperty(author_id);
		this.project_name = new SimpleStringProperty(project_name);
		this.project_details = new SimpleStringProperty(project_details);
		this.author_name = new SimpleStringProperty(author_name);
	}

	// Getters and setters
	public IntegerProperty project_idProperty() {
		return project_id;
	}
	
	public int getProject_id() {
		return project_id.get();
	}
	
	public void setProject_id(int project_id) {
		this.project_id.set(project_id);
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
	
	public StringProperty project_nameProperty() {
		return project_name;
	}
	
	public String getProject_name() {
		return project_name.get();
	}
	
	public void setProject_name(String project_name) {
		this.project_name.set(project_name);
	}
	
	public StringProperty project_detailsProperty() {
		return project_details;
	}
	
	public String getProject_details() {
		return project_details.get();
	}
	
	public void setProject_details(String project_details) {
		this.project_details.set(project_details);
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
