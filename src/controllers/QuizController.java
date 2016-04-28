package controllers;

import java.io.IOException;

import application.Main;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import qcm.models.pojo.Questionnaire;

public class QuizController extends AbstractController {
	
	@FXML
	private Button btRetour;
	
	@FXML
	private TableView<Questionnaire> mesQuizTable;
	
	@FXML
	private TableColumn<Questionnaire, String> quizTitreColumn;
	
	@FXML
	private TableColumn<Questionnaire, String> domaineColumn;
	
	
	
	
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		quizTitreColumn.setCellValueFactory((CellDataFeatures<Questionnaire, String> feature) -> {
			Questionnaire quiz = feature.getValue();
			return new SimpleObjectProperty<>(quiz.getLibelle());
		});
		domaineColumn.setCellValueFactory((CellDataFeatures<Questionnaire, String> feature) -> {
			Questionnaire quiz = feature.getValue();
			return new SimpleObjectProperty<>(quiz.getDomaine().getLibelle());
		});
		//mesQuizTable.getSelectionModel().selectedItemProperty()
	    //		.addListener((observable, oldValue, newValue) -> showQuiz(newValue));
		//lvQuizzes.setCellFactory(new GenericCellFactory<Questionnaire>());
		//cmbReponses.setCellFactory(new GenericCellFactory<Reponse>());
	}

	public TableView<Questionnaire> getmesQuizTable() {
		return mesQuizTable;
	}

	public void setmesQuizTable(TableView<Questionnaire> mesQuizTable) {
		this.mesQuizTable = mesQuizTable;
	}

	public TableColumn<Questionnaire, String> getquizTitreColumn() {
		return quizTitreColumn;
	}

	public void setquizTitreColumn(TableColumn<Questionnaire, String> quizTitreColumn) {
		this.quizTitreColumn = quizTitreColumn;
	}

	public TableColumn<Questionnaire, String> getNomColumn() {
		return domaineColumn;
	}

	public void setdomaineColumn(TableColumn<Questionnaire, String> domaineColumn) {
		this.domaineColumn = domaineColumn;
	}
	
	
	public void setMainApp(Main mainApp) {
		super.setMainApp(mainApp);
		mesQuizTable.setItems(mainApp.getQuizData());
		//lvQuizzes.setItems(mainApp.getQuizData());
		//cmbReponses.setItems(mainApp.getReponsesList());
		// lblCount.textProperty().bind(mainApp.getTaskQueue().getService().progressProperty().asString());
	}
	
	
	
	
	
	
	private void showQuiz(Questionnaire quiz) {
		if (quiz != null) {
			//nomTitre.setVisible(true);
			//prenomTitre.setVisible(true);
			//emailTitre.setVisible(true);
			//rangTitre.setVisible(true);
			
			//prenomLabel.setText(user.getPrenom());
			//nomLabel.setText(user.getNom());
			//emailLabel.setText(user.getMail());
			//rangLabel.setText(user.getRang().toString());
			//aucunUtiText.setVisible(false);

		} else {
			//aucunUtiText.setVisible(true);
			//prenomLabel.setText("");
			//nomLabel.setText("");
			//emailLabel.setText("");
			//rangLabel.setText("");
		}
	}
	
	public void handleBtRetour() {
		mainApp.showAccueilview();
	}

	public void handleAddQuiz() {
		mainApp.getTaskQueue().getAll(Questionnaire.class);
		Questionnaire quiz = new Questionnaire();
		boolean okClicked = mainApp.showQuizEditDialog(quiz);
		if (okClicked) {
			mainApp.getQuizData().add(quiz);
			try {
				mainApp.getWebGate().add(quiz);
			} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}