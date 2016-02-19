package application.view;

import java.io.IOException;

import application.InAltoSchool;
import application.Strings;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TeacherDialog extends AnchorPane implements View {
	
	// Stage reference
	private Stage dialogStage;
	// Controller reference
	private TeacherDialogController controller;
	
	public TeacherDialog(Stage dialogStage) {
		// TODO Auto-generated constructor stub
		this.dialogStage = dialogStage;
		this.dialogStage.initModality(Modality.WINDOW_MODAL);
		this.dialogStage.getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
		initLayout();
		changeLanguage();
	}

	@Override
	public void initLayout() {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(InAltoSchool.class.getResource(Strings.UserDialog.getValue(0)));
		loader.setRoot(this);
		controller = new TeacherDialogController();
		loader.setController(controller);
		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("IOException when loading: "+e.getMessage());		
		}
		controller.setDialogStage(dialogStage);
		controller.changeLanguage();	
	}

	@Override
	public void changeLanguage() {
		// TODO Auto-generated method stub
//		 dialogStage.setTitle(Strings.UserDialogTitle.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// Getters
	public TeacherDialogController getController() {
		return controller;
	}

}
