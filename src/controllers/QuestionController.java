package controllers;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import application.Main;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import qcm.models.pojo.Domaine;
import qcm.models.pojo.Groupe;
import qcm.models.pojo.Questionnaire;
import qcm.models.pojo.Question;
import qcm.models.pojo.Utilisateur;
import qcm.utils.*;

public class QuestionController extends AbstractController{

	
	@FXML
    private TextField idField;
    
    @FXML
    private TextField codeField;

    @FXML
    private TextField libelleField;

    @FXML
    private TableView<Utilisateur> userList;

    @FXML
    private TableColumn<Utilisateur, String> userColumn;

    @FXML
    private TableView<Questionnaire> quizzList;
	
	 @FXML
	    private TableColumn<Question, String> idColumn;
	 
	 @FXML
	    private TableColumn<Question, String> libelleColumn;
	 
	 public TableColumn<Question, String> getLibelleColumn() {
		return libelleColumn;
	}

	public void setLibelleColumn(TableColumn<Question, String> libelleColumn) {
		this.libelleColumn = libelleColumn;
	}


	@FXML
	    private TableView<Question> questList;
	
    public void showQuest(Question quest){
    	if(quest == null){
    		idField.setText("0");
    		libelleField.setText("");
    		
    	}else{
    		idField.setText(String.valueOf(quest.getId()));
    		
    		libelleField.setText(quest.getLibelle());
    		idField.setVisible(true);
			libelleField.setVisible(true);
		
    	}
    }
    
    
    public void handleEditQuestion() throws ClientProtocolException, IllegalAccessException, IOException {
		Question selectedQuest = questList.getSelectionModel().getSelectedItem();
		if (selectedQuest != null) {
			boolean okClicked = mainApp.showQuestEditDialog(selectedQuest);
			if (okClicked) {
				try {
					mainApp.getTaskQueue().update(selectedQuest, selectedQuest.getId());
					// mainApp.getWebGate().update(selectedDomaine,
					// selectedDomaine.getId());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				questList.refresh();
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("Aucune question n'est selectionnée.");
			alert.setContentText("Veuillez selectionner une question dans la liste.");
			alert.showAndWait();
		}
	}
    
    @FXML
	public void handleDeleteQuestion() {
		int selectedIndex = questList.getSelectionModel().getSelectedIndex();
		Question selectedQuest = questList.getSelectionModel().getSelectedItem();
		if (selectedIndex >= 0) {
			questList.getItems().remove(selectedIndex);
			mainApp.getTaskQueue().delete(selectedQuest, selectedQuest.getId());
			questList.refresh();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("Aucune question n'est selectionnée.");
			alert.setContentText("Veuillez selectionner une questionS dans la liste.");

			alert.showAndWait();
		}
	}
    
    @FXML
    public void handleAddQuest() {
		mainApp.getTaskQueue().getAll(Question.class);
		Question quest = new Question();
		boolean okClicked = mainApp.showQuestEditDialog(quest);
		if (okClicked) {
			mainApp.getQuestData().add(quest);
			try {
				mainApp.getWebGate().add(quest);
			} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    
    
    
    public void handleBtRetour() {
		mainApp.showAccueilview();
	}
    
    @FXML
	private void initialize() {
    		
		libelleColumn.setCellValueFactory((CellDataFeatures<Question, String> feature) -> {
			Question quest = feature.getValue();
			return new SimpleObjectProperty<>(quest.getLibelle());
		});	
		
		showQuest(null);
		
questList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showQuest(newValue));	
	
	}
    
    public void setMainApp(Main mainApp) {
  		super.setMainApp(mainApp);
  		
  				
  		questList.setItems(mainApp.getQuestList());		
  		
  		
  		
  	
  	}
	 
	 public TableColumn<Question, String> getIdColumn() {
		return idColumn;
	}

	public void setIdColumn(TableColumn<Question, String> idColumn) {
		this.idColumn = idColumn;
	}

	public TableView<Question> getQuestList() {
		return questList;
	}

	public void setQuestList(TableView<Question> questList) {
		this.questList = questList;
	}

	
		
	
}
