package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class QuizController extends AbstractController {
	
	@FXML
	private Button btRetour;
	
	public void handleBtRetour() {
		mainApp.showAccueilview();
	}

}