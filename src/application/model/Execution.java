package application.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Execution {
	
	// Lesson task execution
	private IntegerProperty execution_id;
	private IntegerProperty section_id;
	private IntegerProperty executor_id;
	private IntegerProperty group_id;
	private ObjectProperty<LocalDate> execution_start_date;
	private ObjectProperty<LocalDate> execution_end_date;
	private IntegerProperty execution_status;
	private IntegerProperty execution_result;
	private StringProperty section_name;
	private StringProperty executor_name;
	
	public Execution() {
		// TODO Auto-generated constructor stub
	}
	
	public Execution(int execution_id, int section_id, int executor_id, int group_id, LocalDate execution_start_date, LocalDate execution_end_date, 
			int execution_status, int execution_result, String section_name, String executor_name) {
		this.execution_id = new SimpleIntegerProperty(execution_id);
		this.section_id = new SimpleIntegerProperty(section_id);
		this.executor_id = new SimpleIntegerProperty(executor_id);
		this.group_id = new SimpleIntegerProperty(group_id);
		this.execution_start_date = new SimpleObjectProperty<LocalDate>(execution_start_date);
		this.execution_end_date = new SimpleObjectProperty<LocalDate>(execution_end_date);
		this.execution_status = new SimpleIntegerProperty(execution_status);
		this.execution_result = new SimpleIntegerProperty(execution_result);
		this.section_name = new SimpleStringProperty(section_name);
		this.executor_name = new SimpleStringProperty(executor_name);
	}

	// Getters and setters
	public IntegerProperty execution_idProperty() {
		return execution_id;
	}
	
	public IntegerProperty section_idProperty() {
		return section_id;
	}
	
	public IntegerProperty executor_idProperty() {
		return executor_id;
	}
	
	public IntegerProperty group_idProperty() {
		return group_id;
	}
	
	public ObjectProperty<LocalDate> execution_start_dateProperty() {
		return execution_start_date;
	}
	
	public ObjectProperty<LocalDate> execution_end_dateProperty() {
		return execution_end_date;
	}
	
	public IntegerProperty execution_statusProperty() {
		return execution_status;
	}
	
	public IntegerProperty execution_resultProperty() {
		return execution_result;
	}
	
	public StringProperty section_nameProperty() {
		return section_name;
	}
	
	public StringProperty executor_nameProperty() {
		return executor_name;
	}
	
	public int getExecution_id() {
		return execution_id.get();
	}
	
	public void setExecution_id(int execution_id) {
		this.execution_id.set(execution_id);
	}
	
	public int getSection_id() {
		return section_id.get();
	}
	
	public void setSection_id(int section_id) {
		this.section_id.set(section_id);
	}
	
	public int getExecutor_id() {
		return executor_id.get();
	}
	
	public void setExecutor_id(int executor_id) {
		this.executor_id.set(executor_id);
	}
	
	public int getGroup_id() {
		return group_id.get();
	}
	
	public void setGroup_id(int group_id) {
		this.group_id.set(group_id);
	}
	
	public LocalDate getExecution_start_date() {
		return execution_start_date.get();
	}
	
	public void setExecution_start_date(LocalDate execution_start_date) {
		this.execution_start_date.set(execution_start_date);
	}
	
	public LocalDate getExecution_end_date() {
		return execution_end_date.get();
	}
	
	public void setExecution_end_date(LocalDate execution_end_date) {
		this.execution_end_date.set(execution_end_date);
	}
	
	public int getExecution_status() {
		return execution_status.get();
	}
	
	public void setExecution_status(int execution_status) {
		this.execution_status.set(execution_status);
	}
	
	public int getExecution_result() {
		return execution_result.get();
	}
	
	public void setExecution_result(int execution_result) {
		this.execution_result.set(execution_result);
	}
	
	public String getSection_name() {
		return section_name.get();
	}
	
	public void setSection_name(String section_name) {
		this.section_name.set(section_name);
	}
	
	public String getExecutor_name() {
		return executor_name.get();
	}
	
	public void setExecutor_name(String executor_name) {
		this.executor_name.set(executor_name);
	}
	
}
