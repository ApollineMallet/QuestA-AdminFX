package qcm.utils;

import application.Main;
import controllers.AbstractController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class GenericCellFactory<T> extends AbstractController
		implements Callback<ListView<T>, ListCell<T>> {

	@FXML
	public Image back = new Image("/views/img/back.png");
	public ImageView img = new ImageView(back);	
	
	@Override
	public ListCell<T> call(ListView<T> param) {
		return new ListCell<T>() {
			@Override
			protected void updateItem(T o, boolean empty) {
				super.updateItem(o, empty);
				if (o != null) {
					setText(o.toString());
					/*ImageView img = new ImageView(back);
					img.setFitWidth(20);
					img.setFitHeight(20);
					setGraphic(img);*/
				}
			}
		};
	}
}