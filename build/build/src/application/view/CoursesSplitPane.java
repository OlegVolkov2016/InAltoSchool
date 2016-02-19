package application.view;

import java.io.IOException;

import application.InAltoSchool;
import application.Strings;
import javafx.fxml.FXMLLoader;

public class CoursesSplitPane extends MainSplitPane implements View {
	
	// Controller reference
	private CoursesSplitPaneController controller;

	public CoursesSplitPane(InAltoSchool mainApp) {
		// TODO Auto-generated constructor stub
		super();
		setMainApp(mainApp);	
		initLayout();
		changeLanguage();
		changeLogin();
	}
	
	@Override
	public void initLayout() {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(InAltoSchool.class.getResource(Strings.CoursesSplitPane.getValue(0)));
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("IOException when loading: "+e.getMessage());		
		}
		controller = ((CoursesSplitPaneController) loader.getController());
		controller.setMainApp(getMainApp());
	}

	@Override
	public void changeLanguage() {
		// TODO Auto-generated method stub
		controller.changeLanguage();	
	}
	
	@Override
	public void changeLogin() {
		// TODO Auto-generated method stub
		controller.changeLogin();
	}
	
	// Getters and setters
	public CoursesSplitPaneController getController() {
		return controller;
	}

}
