package controllers;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import qcm.models.pojo.Domaine;
import qcm.models.pojo.Rang;
import qcm.utils.GenericCellFactory;

public class RangController extends AbstractController{

	@FXML
	private ListView<Rang> rangList;

	@FXML
	private Button btRetour;

	@FXML
	private void initialize() {
		rangList.setCellFactory(new GenericCellFactory<Rang>());
	}

	public void handleBtRetour() {
		mainApp.showAccueilview();
	}
	
	public void handleAddRang() {
		
	}
	
	public void handleEditRang () {
		
	}
	
	public void handleDeleteRang() {
		
	}
	
	public void setMainApp(Main mainApp) {
		super.setMainApp(mainApp);
		rangList.setItems(mainApp.getRangData());
	}
}
