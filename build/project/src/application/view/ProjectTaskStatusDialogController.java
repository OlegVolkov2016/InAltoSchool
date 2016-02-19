package application.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import application.Indexes;
import application.Strings;
import application.model.Execution;
import application.model.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ProjectTaskStatusDialogController {
	
	// Stage reference
	private Stage dialogStage;
	// OKButton flag
	private boolean isOKButton = false;
	// Task data
	private Task task;
	// Execution data
	private Execution execution;
	// Author flag
	private boolean isAuthor = false;
	// Current status
	private int status_id;
	
	// Children elements references	
	@FXML
	private Button okButton, cancelButton;
	
	@FXML
	private Label labelProjectAuthor, labelProjectName, labelStudent, labelStartDate, labelEndDate, labelResult, labelStatus;
	
	@FXML
	private TextField textProjectAuthor, textStudent, textStartDate, textEndDate, textResult;
	
	@FXML
	private TextArea textProjectName;
	
	@FXML
	private RadioButton radioIssued, radioPerformed, radioChecked, radioFinished;
	
	@FXML
	private ToggleGroup toggleStatus;
	
	@FXML
	private void initialize() {
		// Set Handlers
		setHandlers();
	}
	
	// Button Events
	@FXML
	private void okButton() {
		if (isValid()) {
			isOKButton = true;
			execution.setExecution_status(status_id);
			if (isAuthor) {
				LocalDate startDate, endDate;
				if (textStartDate.getText().equals(""))
					startDate = null;
				else
					startDate = LocalDate.parse(textStartDate.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
				execution.setExecution_start_date(startDate);
				if (textEndDate.getText().equals(""))
					endDate = null;
				else
					endDate = LocalDate.parse(textEndDate.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
				execution.setExecution_end_date(endDate);
				if (!textResult.getText().equals(""))
					execution.setExecution_result(Integer.parseInt(textResult.getText()));
				else
					execution.setExecution_result(0);
			}
			dialogStage.close();
		}
	}
	
	@FXML
	private void cancelButton() {
		dialogStage.close();
	}
	
	// ChangeLanguage for elements
	public void changeLanguage () {
		labelProjectAuthor.setText(Strings.LabelProjectAuthor.getValue(Indexes.AppLanguage_index.getValue()));
		labelProjectName.setText(Strings.LabelTaskName.getValue(Indexes.AppLanguage_index.getValue()));
		labelStudent.setText(Strings.LabelProjectStudent.getValue(Indexes.AppLanguage_index.getValue()));
		labelStartDate.setText(Strings.LabelStartDate.getValue(Indexes.AppLanguage_index.getValue()));
		labelEndDate.setText(Strings.LabelEndDate.getValue(Indexes.AppLanguage_index.getValue()));
		labelResult.setText(Strings.LabelResult.getValue(Indexes.AppLanguage_index.getValue()));
		labelStatus.setText(Strings.LabelStatus.getValue(Indexes.AppLanguage_index.getValue()));
		textStartDate.setPromptText(Strings.TextStartDate.getValue(Indexes.AppLanguage_index.getValue()));
		textEndDate.setPromptText(Strings.TextEndDate.getValue(Indexes.AppLanguage_index.getValue()));
		textResult.setPromptText(Strings.TextResult.getValue(Indexes.AppLanguage_index.getValue()));
		radioIssued.setText(Strings.RadioIssued.getValue(Indexes.AppLanguage_index.getValue()));
		radioPerformed.setText(Strings.RadioPerformed.getValue(Indexes.AppLanguage_index.getValue()));
		radioChecked.setText(Strings.RadioChecked.getValue(Indexes.AppLanguage_index.getValue()));
		radioFinished.setText(Strings.RadioFinished.getValue(Indexes.AppLanguage_index.getValue()));
		okButton.setText(Strings.OKButton.getValue(Indexes.AppLanguage_index.getValue()));
		cancelButton.setText(Strings.CancelButton.getValue(Indexes.AppLanguage_index.getValue()));
	}
	
	// Input Validation
	private boolean isValid() {
		boolean isValid = true;
		try {
			if (!textStartDate.getText().equals("")) 
				LocalDate.parse(textStartDate.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		}
		catch (DateTimeParseException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			textStartDate.requestFocus();
			isValid = false;
		}
		try {
			if (!textEndDate.getText().equals("")) 
				LocalDate.parse(textEndDate.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
			if (!textResult.getText().equals(""))
				Integer.parseInt(textResult.getText());
		}
		catch (DateTimeParseException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			textEndDate.requestFocus();
			isValid = false;
		}
		catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			textResult.requestFocus();
			isValid = false;
		}
		if (!isValid) {
			Alert splitAlert = new Alert(AlertType.ERROR);
			((Stage) splitAlert.getDialogPane().getScene().getWindow()).getIcons().add(new Image(Strings.MainIcon_id.getValue(0)));
			splitAlert.setTitle(Strings.ErrorTitle.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setHeaderText(Strings.InputError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.setContentText(Strings.FormatError.getValue(Indexes.AppLanguage_index.getValue()));
			splitAlert.showAndWait();	
			return isValid;			
		}
		return isValid;
	}
	
	// Set Handlers
	private void setHandlers() {
		// Status
		radioIssued.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				status_id = 0;
			}
		});
		radioPerformed.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				status_id = 1;
			}
		});
		radioChecked.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				status_id = 2;
			}
		});
		radioFinished.setOnAction(new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				status_id = 3;
			}
		});
	}
	
	// Getters and setters
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public boolean isOKButton() {
		return isOKButton;
	}
	
	public void clearFlags() {
		isOKButton = false;
		isAuthor = false;
	}
	
	public void setAuthor() {
		isAuthor = true;
		textStartDate.setEditable(true);
		textStartDate.setFocusTraversable(true);
		textStartDate.setStyle("-fx-control-inner-background: white;");
		textEndDate.setEditable(true);
		textEndDate.setFocusTraversable(true);
		textEndDate.setStyle("-fx-control-inner-background: white;");
		textResult.setEditable(true);
		textResult.setFocusTraversable(true);
		textResult.setStyle("-fx-control-inner-background: white;");
		radioIssued.setDisable(false);
		radioFinished.setDisable(false);
	}
	
	public Task getTask() {
		return task;
	}
	
	public void setTask(Task task) {
		this.task = task;
		textProjectAuthor.setText(task.getAuthor_name());
		textProjectName.setText(Strings.LabelProjectName.getValue(Indexes.AppLanguage_index.getValue())+" "+task.getProject_name()+"\n"+
				Strings.LabelTaskName.getValue(Indexes.AppLanguage_index.getValue())+" "+task.getTask_name());
	}
	
	public Execution getExecution() {
		return execution;
	}
	
	public void setExecution(Execution execution) {
		this.execution = execution;
		textStudent.setText(execution.getExecutor_name());
		if (execution.getExecution_start_date() != null)
			textStartDate.setText(execution.getExecution_start_date().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
		if (execution.getExecution_end_date() != null)
			textEndDate.setText(execution.getExecution_end_date().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
		textResult.setText(execution.getExecution_result()+"");
		switch (execution.getExecution_status()) {
		case 1: {
			radioPerformed.setSelected(true);
			break;
		}
		case 2: {
			radioChecked.setSelected(true);
			break;
		}
		case 3: {
			radioFinished.setSelected(true);
			break;
		}
		default:
			radioIssued.setSelected(true);
		}
	}

}
