package controllers;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import qcm.models.pojo.Utilisateur;

public class LoginController extends ModalController {

	private Main mainApp;
	@FXML
	private TextField txtLogin;

	@FXML
	private TextField txtPassword;

	@FXML
	private Button btConnexion;

	@FXML
	private Label lblMessage;

	private Utilisateur user;

	public void handleConnexion() {
		try {
			user = mainApp.getWebGate().connect(txtLogin.getText(), txtPassword.getText());
			if (user != null) {
				lblMessage.setVisible(false);
				okClicked = true;
				dialogStage.close();
			} else {
				lblMessage.setText("Login ou mot de passe invalides");
				lblMessage.setVisible(true);
			}
		} catch (IOException e) {
			lblMessage.setText(e.getMessage());
			lblMessage.setVisible(true);
		}
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	public Utilisateur getUser() {
		return user;
	}

	public void setUser(Utilisateur user) {
		this.user = user;
	}

}
