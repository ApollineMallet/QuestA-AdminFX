package qcm.utils;

import java.io.IOException;
import java.util.function.Function;

import application.Main;
import controllers.AbstractController;
import controllers.ModalController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ViewUtils {

	public static <T extends ModalController> boolean showDialog(String fxmlFile, Window owner, Function<T, String> initControllerFunc, Function<T, Boolean> finalControllerFunc) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(fxmlFile));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.setResizable(false);
			dialogStage.initOwner(owner);
			Scene scene = new Scene(page);
			scene.getStylesheets().add(Main.class.getResource("/views/my.css").toString());
			dialogStage.setScene(scene);

			T controller = loader.getController();
			controller.setDialogStage(dialogStage);
			dialogStage.setTitle(initControllerFunc.apply(controller));

			dialogStage.showAndWait();

			boolean result = controller.isOkClicked();
			if (result && finalControllerFunc != null)
				return finalControllerFunc.apply(controller);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static <T extends ModalController> boolean showDialog(String fxmlFile, Window owner, Function<T, String> initControllerFunc) {
		return showDialog(fxmlFile, owner, initControllerFunc, null);
	}

	@SuppressWarnings("unchecked")
	public static <T extends AbstractController, V extends Pane> T loadCenterPane(String fxmlFile, Main mainApp, Class<V> paneClass) {
		V newPane = null;
		T controller = null;
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(fxmlFile));
			newPane = (V) loader.load();

			mainApp.getRootLayout().setCenter(newPane);

			controller = loader.getController();
			controller.setMainApp(mainApp);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return controller;
	}

}
