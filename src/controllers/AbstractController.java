package controllers;

import application.Main;

public abstract class AbstractController {
	protected Main mainApp;

	public AbstractController() {

	}

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

}
