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

public class CourseInfo {
	
	// School course
	private IntegerProperty course_id;
	private IntegerProperty author_id;
	private StringProperty course_name;
	private StringProperty course_details;
	private DoubleProperty course_group_price;
	private DoubleProperty course_individual_price;
	private StringProperty author_name;
	private StringProperty course_group;
	private ObjectProperty<LocalDate> course_date;
	private IntegerProperty course_lessons;
	
	public CourseInfo() {
		// TODO Auto-generated constructor stub
	}
	
	public CourseInfo(int course_id, int author_id, String course_name, String course_details, double course_group_price, double course_individual_price, 
			String author_name, String course_group, LocalDate course_date, int course_lessons) {
		this.course_id = new SimpleIntegerProperty(course_id);
		this.author_id = new SimpleIntegerProperty(author_id);
		this.course_name = new SimpleStringProperty(course_name);
		this.course_details = new SimpleStringProperty(course_details);
		this.course_group_price = new SimpleDoubleProperty(course_group_price);
		this.course_individual_price = new SimpleDoubleProperty(course_individual_price);
		this.author_name = new SimpleStringProperty(author_name);
		this.course_group = new SimpleStringProperty(course_group);
		this.course_date = new SimpleObjectProperty<LocalDate>(course_date);
		this.course_lessons = new SimpleIntegerProperty(course_lessons);
	}

	// Getters and setters
	public IntegerProperty course_idProperty() {
		return course_id;
	}
	
	public int getCourse_id() {
		return course_id.get();
	}
	
	public void setCourse_id(int course_id) {
		this.course_id.set(course_id);
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
	
	public StringProperty course_nameProperty() {
		return course_name;
	}
	
	public String getCourse_name() {
		return course_name.get();
	}
	
	public void setCourse_name(String course_name) {
		this.course_name.set(course_name);
	}
	
	public StringProperty course_detailsProperty() {
		return course_details;
	}
	
	public String getCourse_details() {
		return course_details.get();
	}
	
	public void setCourse_details(String course_details) {
		this.course_details.set(course_details);
	}
	
	public DoubleProperty course_group_priceProperty() {
		return course_group_price;
	}
	
	public double getCourse_group_price() {
		return course_group_price.get();
	}
	
	public void setCourse_group_price(double course_price) {
		this.course_group_price.set(course_price);
	}
	
	public DoubleProperty course_individual_priceProperty() {
		return course_individual_price;
	}
	
	public double getCourse_individual_price() {
		return course_individual_price.get();
	}
	
	public void setCourse_individual_price(double course_price) {
		this.course_individual_price.set(course_price);
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
	
	public StringProperty course_groupProperty() {
		return course_group;
	}
	
	public String getCourse_group() {
		return course_group.get();
	}
	
	public void setCourse_group(String course_group) {
		this.course_group.set(course_group);
	}
	
	public ObjectProperty<LocalDate> course_dateProperty() {
		return course_date;
	}
	
	public LocalDate getCourse_date() {
		return course_date.get();
	}
	
	public void setCourse_date(LocalDate course_date) {
		this.course_date.set(course_date);
	}
	
	public IntegerProperty course_lessonsProperty() {
		return course_lessons;
	}
	
	public int getCourse_lessons() {
		return course_lessons.get();
	}
	
	public void setCourse_lessons(int course_lessons) {
		this.course_lessons.set(course_lessons);
	}
	
}
