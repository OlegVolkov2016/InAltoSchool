package application;
	
import application.database.InAltoSchoolDatabase;
import application.model.Login;
import application.view.RootLayout;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class InAltoSchool extends Application {
	
	// Main stage reference
	private Stage primaryStage;
	// Main layout reference
	private RootLayout rootLayout;
	// Current login
	private Login login;
	
	// Main application
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.rootLayout = new RootLayout(this);
		Scene scene = new Scene(this.rootLayout);
		scene.getStylesheets().add(getClass().getResource(Strings.MainApplicationCSS.getValue(0)).toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// Getters 
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public RootLayout getRootLayout() {
		return rootLayout;
	}
	
	public Login getLogin() {
		return login;
	}
	
	public void setLogin(Login login) {
		this.login = login;
	}
		
	public static void main(String[] args) {
		InAltoSchoolDatabase.getInstance().setTables();
		launch(args);
	}
}
