package controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import qcm.models.pojo.Domaine;

public class DomaineController extends AbstractController {

	@FXML
	private TableView<Domaine> domaineTable;
	@FXML
	private TableColumn<Domaine, String> groupeColumn;

	@FXML
	private void initialize() {
		groupeColumn.setCellValueFactory((CellDataFeatures<Domaine, String> feature) -> {
			Domaine domaine = feature.getValue();
			return new SimpleObjectProperty<>(domaine.getLibelle());
		});
	}

}
