package controllers;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import qcm.models.pojo.Domaine;
import qcm.utils.GenericCellFactory;

public class DomaineController extends AbstractController {

	@FXML
	private ListView<Domaine> domaineList;

	@FXML
	private void initialize() {
		domaineList.setCellFactory(new GenericCellFactory<Domaine>());
	}

	public void setMainApp(Main mainApp) {
		super.setMainApp(mainApp);
		domaineList.setItems(mainApp.getDomaineData());
	}

}
