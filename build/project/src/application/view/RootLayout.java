package application.view;

import java.io.IOException;

import application.InAltoSchool;
import application.Indexes;
import application.Strings;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class RootLayout extends BorderPane implements View {
	
	// Main application reference 
	private InAltoSchool mainApp;
	// Controller reference
	RootLayoutController controller;
	// SplitPanes array
	private MainSplitPane[] mainSplitPanesList;
	// Current SplitPane reference
	private MainSplitPane mainSplitPane;
	
	public RootLayout(InAltoSchool mainApp) {
		// TODO Auto-generated constructor stub
		this.mainApp = mainApp;	
		this.mainSplitPanesList = new MainSplitPane[Indexes.MainSplitPaneListSize.getValue()];
		this.mainApp.getPrimaryStage().getIcons().add(new Image(Strings.MainIcon_id.getValue(0))); 
		initLayout();
		changeLanguage();
	}
	
	@Override
	public void initLayout() {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(InAltoSchool.class.getResource(Strings.MainRootLayout.getValue(0)));
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("IOException when loading: "+e.getMessage());		
		}
		controller = ((RootLayoutController) loader.getController());
		controller.setMainApp(mainApp);
		controller.setRootLayout(this);
		controller.changeLanguage();
		if (mainSplitPanesList[Indexes.MainSplitPane_id.getValue()] == null)
			mainSplitPanesList[Indexes.MainSplitPane_id.getValue()] = new MainSplitPane(this.mainApp);
		mainSplitPane = mainSplitPanesList[Indexes.MainSplitPane_id.getValue()];
		setCenter(mainSplitPanesList[Indexes.MainSplitPane_id.getValue()]);
		this.mainApp.getPrimaryStage().setMinWidth(getPrefWidth()+16);
		this.mainApp.getPrimaryStage().setMinHeight(getPrefHeight()+38);
	}
	
	@Override
	public void changeLanguage () {
		// TODO Auto-generated method stub
		this.mainApp.getPrimaryStage().setTitle(Strings.MainTitle.getValue(Indexes.AppLanguage_index.getValue()));
		for (MainSplitPane splitPane : mainSplitPanesList)
			if (splitPane != null)
				splitPane.changeLanguage();
	}
	
	public void changeLogin() {
		// TODO Auto-generated method stub
		if ((mainApp.getLogin() !=  null) && (mainApp.getLogin().getRole_name().equals("Administrator"))) {
			controller.getAdministrationMenu().setVisible(true);
		}
		else {
			controller.getAdministrationMenu().setVisible(false);
		}
		for (MainSplitPane splitPane : mainSplitPanesList)
			if (splitPane != null)
				splitPane.changeLogin();
	}
	
	// Getters
	public InAltoSchool getMainApp() {
		return mainApp;
	}
	
	public RootLayoutController getController() {
		return controller;
	}
	
	public MainSplitPane[] getMainSplitPanesList() {
		return mainSplitPanesList;
	}
	
	public MainSplitPane getMainSplitPane() {
		return mainSplitPane;
	}
	
	public void setMainSplitPane(MainSplitPane mainSplitPane) {
		this.mainSplitPane = mainSplitPane;
	}
	
}
