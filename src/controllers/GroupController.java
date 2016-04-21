package controllers;

import application.Main;
import javafx.beans.property.SimpleObjectProperty;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import qcm.models.pojo.Groupe;
import qcm.models.pojo.Questionnaire;
import qcm.models.pojo.Utilisateur;

public class GroupController extends AbstractController{

    @FXML
    private TableView<Groupe> groupList;

    @FXML
    private TableColumn<Groupe, String> codeColumn;

    @FXML
    private TableColumn<Groupe, String> libelleColumn;

    
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
    private TableColumn<Questionnaire, String> quizzColumn;



    @FXML
	private void initialize() {
    		
		libelleColumn.setCellValueFactory((CellDataFeatures<Groupe, String> feature) -> {
			Groupe groupe = feature.getValue();
			return new SimpleObjectProperty<>(groupe.getLibelle());
		});
		
		codeColumn.setCellValueFactory((CellDataFeatures<Groupe, String> feature) -> {
			Groupe groupe = feature.getValue();
			return new SimpleObjectProperty<>(groupe.getCode());
		});
	
		userColumn.setCellValueFactory((CellDataFeatures<Utilisateur, String> feature) -> {
			Utilisateur user = feature.getValue();
			return new SimpleObjectProperty<>(user.getNom()+" "+user.getPrenom());
		});
		
		quizzColumn.setCellValueFactory((CellDataFeatures<Questionnaire, String> feature) -> {
			Questionnaire quizz = feature.getValue();
			return new SimpleObjectProperty<>(quizz.getLibelle());
		});
		
		showGroup(null);
		
		groupList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showGroup(newValue));	
	
	}
    
    public TableView<Groupe> getGroupeTable() {
		return groupList;
	}
    
    public void setMainApp(Main mainApp) {
		super.setMainApp(mainApp);
		groupList.setItems(mainApp.getGroupeList());
		
	}
  
    public void showGroup(Groupe group){
    	if(group == null){
    		idField.setText("0");
    		libelleField.setText("coucou");
    		codeField.setText("");
    	}else{
    		idField.setText(String.valueOf(group.getId()));
    		codeField.setText(group.getCode());
    		libelleField.setText(group.getLibelle());
    		idField.setVisible(true);
			codeField.setVisible(true);
			libelleField.setVisible(true);
		
    	}
    }
    
    
    @FXML
    void handleSave(ActionEvent event) {
    
    }
    
   @FXML
   void handleNew(ActionEvent event) {
//   	mainApp.getTaskQueue().getAll(Groupe.class);
//		Groupe group = new Groupe();
//		boolean okClicked = mainApp.showPersonEditDialog(group);
//		if (okClicked) {
//			mainApp.getPersonData().add(group);
//			try {
//				mainApp.getWebGate().add(group);
//			} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
    }
    
  
    
    @FXML
    void handleDelete(ActionEvent event) {

		
    }

}
