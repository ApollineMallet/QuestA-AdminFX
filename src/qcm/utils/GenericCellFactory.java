package qcm.utils;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class GenericCellFactory<T>
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
					setGraphic(img);
				}
			}
		};
	}

}
