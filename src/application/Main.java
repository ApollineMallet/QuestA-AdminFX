package application;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;

import controllers.AccueilController;
import controllers.DomaineController;
import controllers.EditController;
import controllers.GroupController;
import controllers.LoginController;
import controllers.MainController;
import controllers.PersonnViewController;
import controllers.QuestionController;
import controllers.QuizController;
import controllers.RangController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import qcm.models.pojo.Domaine;
import qcm.models.pojo.Groupe;
import qcm.models.pojo.Groupe_questionnaire;
import qcm.models.pojo.Questionnaire;

import qcm.models.pojo.Question;

import qcm.models.pojo.Rang;

import qcm.models.pojo.Reponse;
import qcm.models.pojo.Utilisateur;
import qcm.utils.ViewUtils;
import qcm.utils.WebGate;
import qcm.utils.saves.SaveOperationTypes;
import qcm.utils.saves.TaskQueue;

public class Main extends Application implements Observer {
	private Stage primaryStage;
	private BorderPane rootLayout;
	private ObservableList<Utilisateur> usersList;
	private ObservableList<Domaine> domainesList;
	private ObservableList<Rang> rangsList;
	private ObservableList<Questionnaire> quizList;
	private ObservableList<Reponse> reponsesList;
	private ObservableList<Question> questList;
	private ObservableList<Groupe_questionnaire> GrquizList;
	private ObservableList<Groupe> groupeList;
	private PersonnViewController personnViewController;
	

	private DomaineController domaineController;
	private RangController rangController;
	private AccueilController accueilController;
	private WebGate webGate;
	private TaskQueue taskQueue;
	private MainController mainController;
	private Utilisateur activeUser;
	private GroupController groupController;
	private QuestionController questController;
	private QuizController quizController;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Quiz-Admin");
		this.primaryStage.setOnCloseRequest(e -> Platform.exit());
		initRootLayout();
		// showPersonOverview();
		taskQueue.start();
		showConnexion();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/views/MainView.fxml"));
			rootLayout = (BorderPane) loader.load();
			mainController = loader.getController();
			mainController.setMainApp(this);
			mainController.getPbTasks().progressProperty().bind(taskQueue.getService().progressProperty());

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(Main.class.getResource("/views/my.css").toString());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the person overview inside the root layout.
	 */
	public void showAccueilview() {
		// personnViewController =
		// ViewUtils.loadCenterPane("/views/PersonnView.fxml", this,
		// AnchorPane.class);
		accueilController = ViewUtils.loadCenterPane("/views/AccueilView.fxml", this, AnchorPane.class);
	}

	/**
	 * Shows the person overview inside the root layout.
	 */

	public void showPersonOverview() {
		personnViewController = ViewUtils.loadCenterPane("/views/PersonnView.fxml", this, AnchorPane.class);
	}

	public void showGroupOverview() {
		groupController = ViewUtils.loadCenterPane("/views/GroupView.fxml", this, AnchorPane.class);
	}
	public void showQuestOverview(){
		questController = ViewUtils.loadCenterPane("/views/QuestionsView.fxml", this, AnchorPane.class);
	
	}

	public void showDomaineOverview() {
		domaineController = ViewUtils.loadCenterPane("/views/DomaineView.fxml", this, AnchorPane.class);
	}

	public void showQuizOverview() {
		quizController = ViewUtils.loadCenterPane("/views/Quiz.fxml", this, AnchorPane.class);
	}
	
	public void showRangOverview() {
		rangController = ViewUtils.loadCenterPane("/views/RangView.fxml", this, AnchorPane.class);
	}

	/**
	 * Opens a dialog to edit details for the specified person. If the user
	 * clicks OK, the changes are saved into the provided person object and true
	 * is returned.
	 *
	 * @param user
	 *            the person object to be edited
	 * @return true if the user clicked OK, false otherwise.
	 */
	public boolean showPersonEditDialog(Utilisateur user) {
		return ViewUtils.showDialog("/views/EditView.fxml", primaryStage, new Function<EditController, String>() {
			@Override
			public String apply(EditController t) {
				t.setUser(user);
				return "Edition utilisateur";
			}
		});
	}

	
	public boolean showGroupEditDialog(Groupe groupe) {
		return ViewUtils.showDialog("/views/EditGroup.fxml", primaryStage, new Function<EditController, String>() {
			@Override
			public String apply(EditController t) {
				t.setGroup(groupe);
				return "Edition groupe";
			}
		});
	}
	
	public boolean showQuestEditDialog(Question quest) {
		return ViewUtils.showDialog("/views/EditQuest.fxml", primaryStage, new Function<EditController, String>() {
			@Override
			public String apply(EditController t) {
				t.setQuest(quest);
				return "Edition question";
			}
		});
	}
	


	public boolean showDomaineEditDialog(Domaine domaine) {
		return ViewUtils.showDialog("/views/EditDomaine.fxml", primaryStage, new Function<EditController, String>() {
			@Override
			public String apply(EditController t) {
				t.setDomaine(domaine);
				return "Edition domaine";
			}
		});
	}

	
	
	public boolean showQuizEditDialog(Questionnaire Q) {
		return ViewUtils.showDialog("/views/QuizCreate.fxml", primaryStage, new Function<EditController, String>() {
			@Override
			public String apply(EditController t) {
				t.setQuiz(Q);
				return "Edition domaine";
			}
		});
	}
	

	public void showConnexion() {
		if (ViewUtils.showDialog("/views/LoginView.fxml", primaryStage, new Function<LoginController, String>() {

			@Override
			public String apply(LoginController t) {
				t.setMainApp(Main.this);
				return "Connexion";
			}
		}, new Function<LoginController, Boolean>() {

			@Override
			public Boolean apply(LoginController t) {
				Main.this.activeUser = t.getUser();
				return Main.this.activeUser != null;
			}

		})) {
			showAccueilview();
			loadLists();
			accueilController.setQuizzes(activeUser.getQuestionnaires());
		}
	}

	public void handleDelete() {
		personnViewController.deleteUser();
	}

	public Main() {
		super();
		webGate = new WebGate();
		taskQueue = new TaskQueue("mainFx", webGate);
		taskQueue.addObserver(this);

		usersList = webGate.getList(Utilisateur.class);
		quizList = webGate.getList(Questionnaire.class);
		reponsesList = webGate.getList(Reponse.class);
		domainesList = webGate.getList(Domaine.class);
		groupeList = webGate.getList(Groupe.class);

		questList = webGate.getList(Question.class);
		

		rangsList = webGate.getList(Rang.class);


		/*
		 * try { List<Utilisateur> users = webGate.getAll(Utilisateur.class);
		 * for (Utilisateur u : users) { usersList.add(u); } } catch
		 * (IOException e) { // TODO Alert Bootstrap JavaFX e.printStackTrace();
		 * }
		 */
		// loadLists();
	}

	public ObservableList<Question> getQuestList() {
		return questList;
	}

	public void setQuestList(ObservableList<Question> questList) {
		this.questList = questList;
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */

	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public ObservableList<Utilisateur> getPersonData() {
		return usersList;
	}

	public void setPersonData(ObservableList<Utilisateur> personData) {
		this.usersList = personData;
	}
	
	public ObservableList<Rang> getRangData() {
		return rangsList;
	}
	
	public void setRangData(ObservableList<Rang> rangData) {
		this.rangsList = rangData;
	}	
	
	public ObservableList<Groupe> getGroupData() {
		return groupeList;
	}

	public void setGroupData(ObservableList<Groupe> groupData) {
		this.groupeList = groupData;
		}
	public ObservableList<Question> getQuestData() {
		return questList;
	}

	public void setQuestData(ObservableList<Question> questData) {
		this.questList = questData;
		}

	public ObservableList<Domaine> getDomaineData() {
		return domainesList;
	}

	public void setDomaineData(ObservableList<Domaine> domaineData) {
		this.domainesList = domaineData;
	}

	public WebGate getWebGate() {
		return webGate;
	}

	public TaskQueue getTaskQueue() {
		return taskQueue;
	}
	
	
	
	
	
	

	public void setQuizData(ObservableList<Questionnaire> quizData) {
		this.quizList = quizData;
	}

	@Override
	public void stop() throws Exception {
		taskQueue.stop();
		super.stop();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		Object[] args = (Object[]) arg;
		if (args[0].equals(SaveOperationTypes.GET)) {
			webGate.addAll((List<Object>) args[2], (Class<Object>) args[1]);
		}
	}

	public ObservableList<Questionnaire> getQuizData() {
		return quizList;
	}

	public ObservableList<Reponse> getReponsesList() {
		return reponsesList;
	}

	public void loadLists() {
		taskQueue.getAll(Questionnaire.class);
		taskQueue.getAll(Reponse.class);
		taskQueue.getAll(Domaine.class);
		taskQueue.getAll(Groupe.class);
		taskQueue.getAll(Utilisateur.class);
		taskQueue.getAll(Question.class);
	}

	public BorderPane getRootLayout() {
		return rootLayout;
	}

	public Utilisateur getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(Utilisateur activeUser) {
		this.activeUser = activeUser;
	}

	public GroupController getGroupController() {
		return groupController;
	}

	public void setGroupController(GroupController groupController) {
		this.groupController = groupController;
	}

	public ObservableList<Groupe> getGroupeList() {
		return groupeList;
	}

	public ObservableList<Questionnaire> getQuizList() {
		return quizList;
	}

	public void setQuizList(ObservableList<Questionnaire> quizList) {
		this.quizList = quizList;
	}

	public ObservableList<Utilisateur> getUsersList() {
		return usersList;
	}

	public void setUsersList(ObservableList<Utilisateur> usersList) {
		this.usersList = usersList;
	}

	public void setGroupeList(ObservableList<Groupe> groupeList) {
		this.groupeList = groupeList;
	}
}
