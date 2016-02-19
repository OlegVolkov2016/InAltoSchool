package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Role {
	
	// Role of school users
	private IntegerProperty role_id;
	private StringProperty role_name;
	private StringProperty role_details;
	
	public Role() {
		// TODO Auto-generated constructor stub
	}
	
	public Role(int role_id, String role_name, String role_details) {
		this.role_id = new SimpleIntegerProperty(role_id);
		this.role_name = new SimpleStringProperty(role_name);
		this.role_details = new SimpleStringProperty(role_details);
	}

	// Getters and setters
	public IntegerProperty role_idProperty() {
		return role_id;
	}
	
	public int getRole_id() {
		return role_id.get();
	}
	
	public void setRole_id(int role_id) {
		this.role_id.set(role_id);
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
	
	public StringProperty role_detailsProperty() {
		return role_details;
	}
	
	public String getRole_details() {
		return role_details.get();
	}
	
	public void setRole_details(String role_details) {
		this.role_details.set(role_details);
	}
	
}
