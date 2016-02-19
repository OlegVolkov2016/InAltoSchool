package application.view;

import application.Indexes;
import application.Strings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebViewDialogController {
	
	// Stage reference
	private Stage dialogStage;
	// OKButton flag
	private boolean isOkButton = false;
	
	// Children elements references
	@FXML
	private WebView textArea;
	
	@FXML
	private Button okButton;
	
	@FXML
	private void initialize() {
		// Empty
	}
	
	// Button Events
	@FXML
	private void okButton() {
		isOkButton = true;
		dialogStage.close();
	}
	
	// ChangeLanguage for elements
	public void changeLanguage () {
		okButton.setText(Strings.OKButton.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// Getters and setters
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public boolean IsOkButton() {
		return isOkButton;
	}
	
	public void loadContent(String content) {
		String view = content;
		if(view.contains("contenteditable=\"true\"")){
			view=view.replace("contenteditable=\"true\"", "contenteditable=\"false\"");
		}
		textArea.getEngine().loadContent(view);
	}

}
