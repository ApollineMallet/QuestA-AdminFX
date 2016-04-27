package controllers;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import qcm.models.pojo.Domaine;
import qcm.utils.GenericCellFactory;

public class DomaineController extends AbstractController {

	@FXML
	private ListView<Domaine> domaineList;

	@FXML
	private Button btRetour;

	@FXML
	private void initialize() {
		domaineList.setCellFactory(new GenericCellFactory<Domaine>());
	}

	public void handleBtRetour() {
		mainApp.showAccueilview();
	}

	public void handleAddDomaine() {
		mainApp.getTaskQueue().getAll(Domaine.class);
		Domaine domaine = new Domaine();
		boolean okClicked = mainApp.showDomaineEditDialog(domaine);
		if (okClicked) {
			try {
				String res = mainApp.getWebGate().add(domaine);
				String jsonElement = mainApp.getWebGate().getControllerUrl(Domaine.class);
				Domaine d = (Domaine) mainApp.getWebGate().getJsonObject(res,jsonElement, Domaine.class);
				mainApp.getDomaineData().add(domaine);
				mainApp.showDomaineOverview();
			} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void handleEditDomaine() throws ClientProtocolException, IllegalAccessException, IOException {
		Domaine selectedDomaine = domaineList.getSelectionModel().getSelectedItem();
		if (selectedDomaine != null) {
			boolean okClicked = mainApp.showDomaineEditDialog(selectedDomaine);
			if (okClicked) {
				try {
					mainApp.getWebGate().update(selectedDomaine, selectedDomaine.getId());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				domaineList.refresh();
			}
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("Aucun domaine n'est selectionné.");
			alert.setContentText("Veuillez selectionner un domaine dans la liste.");
			alert.showAndWait();
		}
	}

	@FXML
	public void handleDeleteDomaine() {
		int selectedIndex = domaineList.getSelectionModel().getSelectedIndex();
		Domaine selectedDomaine = domaineList.getSelectionModel().getSelectedItem();
		if (selectedIndex >= 0) {
			domaineList.getItems().remove(selectedIndex);
			mainApp.getTaskQueue().delete(selectedDomaine, selectedDomaine.getId());
			mainApp.showDomaineOverview();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("Aucun domaine n'est selectionné.");
			alert.setContentText("Veuillez selectionner un domaine dans la liste.");

			alert.showAndWait();
		}
	}

	public void setMainApp(Main mainApp) {
		super.setMainApp(mainApp);
		domaineList.setItems(mainApp.getDomaineData());
	}

}
