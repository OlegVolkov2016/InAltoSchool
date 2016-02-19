package application.view;

import java.io.IOException;

import application.InAltoSchool;
import application.Strings;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WebViewDialog extends AnchorPane implements View {
	
	// Stage reference
	private Stage dialogStage;
	private WebViewDialogController controller;
	
	public WebViewDialog(Stage dialogStage) {
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
		loader.setLocation(InAltoSchool.class.getResource(Strings.WebViewDialog.getValue(0)));
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("IOException when loading: "+e.getMessage());		
		}
		controller = loader.getController();
		controller.setDialogStage(dialogStage);
		controller.changeLanguage();
	}

	@Override
	public void changeLanguage() {
		// TODO Auto-generated method stub
//		dialogStage.setTitle(Strings.WebViewDialogTitle.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	public WebViewDialogController getController() {
		return controller;
	}

}
