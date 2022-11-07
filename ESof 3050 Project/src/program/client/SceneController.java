package program.client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.application.Application;

public class SceneController extends Application {
	private Scene scene;
	private Stage stage;
	private Parent root;
	
	@Override
	public void start(Stage stage) throws Exception{
		root = FXMLLoader.load(getClass().getResource("LoginChoice.fxml"));
		scene = new Scene(root);
		stage.setTitle("Banking Client");
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	public void switchToTellerMainMenu(ActionEvent event) throws Exception{
		root = FXMLLoader.load(getClass().getResource("TellerMainMenu.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	public void switchToLoginChoice(ActionEvent event) throws Exception{
		root = FXMLLoader.load(getClass().getResource("LoginChoice.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@FXML
    void AccountHolderLoginButtonPressed(ActionEvent event) {
    	//change root to AccountHolderMainMenu.fxml
    }

    @FXML
    void EndSessionButtonPressed(ActionEvent event) {

    }
    
    @FXML
    void SearchExistingAccountHolderButtonPressed(ActionEvent event) {

    }
    
    @FXML
    void CreateNewAccountHolderButtonPressed(ActionEvent event) {

    }
    
	public static void main(String[] args) {
		launch(args);
	}
}
