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
import qcm.models.pojo.Utilisateur;
import qcm.utils.*;

public class GroupController extends AbstractController{

    @FXML
    private TableView<Groupe> groupList;

    @FXML
    private TableColumn<Groupe, String> codeColumn;

    public TableColumn<Groupe, String> getCodeColumn() {
		return codeColumn;
	}

	public void setCodeColumn(TableColumn<Groupe, String> codeColumn) {
		this.codeColumn = codeColumn;
	}

	public TableColumn<Groupe, String> getLibelleColumn() {
		return libelleColumn;
	}

	public void setLibelleColumn(TableColumn<Groupe, String> libelleColumn) {
		this.libelleColumn = libelleColumn;
	}



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

	@Override
	public void setMainApp(MainApp mainApp) {
		super.setMainApp(mainApp);

	
	}

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
  
    
    public void handleEditGroupe() throws ClientProtocolException, IllegalAccessException, IOException {
		Groupe selectedGroupe = groupList.getSelectionModel().getSelectedItem();
		if (selectedGroupe != null) {
			boolean okClicked = mainApp.showGroupEditDialog(selectedGroupe);
			if (okClicked) {
				try {
					mainApp.getTaskQueue().update(selectedGroupe, selectedGroupe.getId());
					// mainApp.getWebGate().update(selectedDomaine,
					// selectedDomaine.getId());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				groupList.refresh();
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("Aucun groupe n'est selectionné.");
			alert.setContentText("Veuillez selectionner un groupe dans la liste.");
			alert.showAndWait();
		}
	}
    
    @FXML
	public void handleDeleteGroupe() {
		int selectedIndex = groupList.getSelectionModel().getSelectedIndex();
		Groupe selectedGroupe = groupList.getSelectionModel().getSelectedItem();
		if (selectedIndex >= 0) {
			groupList.getItems().remove(selectedIndex);
			mainApp.getTaskQueue().delete(selectedGroupe, selectedGroupe.getId());
			groupList.refresh();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("Aucun domaine n'est selectionné.");
			alert.setContentText("Veuillez selectionner un domaine dans la liste.");

			alert.showAndWait();
		}
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
    public void handleAddDomaine() {
		mainApp.getTaskQueue().getAll(Groupe.class);
		Groupe groupe = new Groupe();
		boolean okClicked = mainApp.showGroupEditDialog(groupe);
		if (okClicked) {
			mainApp.getGroupData().add(groupe);
			try {
				mainApp.getWebGate().add(groupe);
			} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    
    
    
    public void handleBtRetour() {
		mainApp.showAccueilview();
	}
  
    
//    @FXML
//    void handleDelete(ActionEvent event) {
//
//		
//    }

}
