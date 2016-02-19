package application.view;

import java.io.IOException;

import application.InAltoSchool;
import application.Indexes;
import application.Strings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class MainSplitPane extends AnchorPane implements View {
	
	// Main application reference 
	private InAltoSchool mainApp;
	
	// Label reference
	@FXML
	private Label mainSplitPaneTitle;
	
	public MainSplitPane() {
		// Empty
	}
	
	public MainSplitPane(InAltoSchool mainApp) {
		// TODO Auto-generated constructor stub
		this.mainApp = mainApp;	
		initLayout();
		changeLanguage();
	}
	
	@Override
	public void initLayout() {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(InAltoSchool.class.getResource(Strings.MainSplitPane.getValue(0)));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("IOException when loading: "+e.getMessage());		
		}
	}
	
	@Override
	public void changeLanguage() {
		// TODO Auto-generated method stub
		this.mainSplitPaneTitle.setText(Strings.MainSplitPaneTitle.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	public void changeLogin() {
		// Empty
	}
	
	// Getters and setters
	public InAltoSchool getMainApp() {
		return mainApp;
	}
	
	public void setMainApp(InAltoSchool mainApp) {
		this.mainApp = mainApp;
	}

}
