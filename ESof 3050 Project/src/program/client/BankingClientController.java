package program.client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.application.Platform;

public class BankingClientController extends Application {
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
	public void changeScene(ActionEvent event,String file) throws Exception{
		root = FXMLLoader.load(getClass().getResource(file));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void switchToTellerMainMenu(ActionEvent event) throws Exception{
		changeScene(event,"TellerMainMenu.fxml");
	}
	public void switchToLoginChoiceScreen(ActionEvent event) throws Exception{
		changeScene(event,"LoginChoice.fxml");
	}
	
	@FXML
	void LogoutButtonPressed(ActionEvent event) throws Exception{
		switchToLoginChoiceScreen(event);
	}
	@FXML
    void AccountHolderLoginButtonPressed(ActionEvent event) {
    	//change root to AccountHolderMainMenu.fxml
    }
    @FXML
    void EndSessionButtonPressed(ActionEvent event) {
    	Platform.exit();
    }
    @FXML
    void SearchExistingAccountHolderButtonPressed(ActionEvent event) throws Exception {
    	//temporary for testing OCSF
    	switchToSampleBalanceScreen(event);
    }
    @FXML
    void CreateNewAccountHolderButtonPressed(ActionEvent event) {

    }
    
    //Test for OCSF functionality
    //**************************************
    @FXML
    private TextField AccountNumberField;
    @FXML
    private TextField BalanceField;
    @FXML
    void BalanceFinderSubmitButtonPressed(ActionEvent event) {
    	int cardNumber = Integer.parseInt(AccountNumberField.getText());
    	//send cardNumber to server and receive balance
    	//double balance = sendFunction(cardNumber);
    	double balance = 800.35;
    	BalanceField.setText(Double.toString(balance));
    }
    void switchToSampleBalanceScreen(ActionEvent event) throws Exception{
    	changeScene(event,"SampleBalanceFinder.fxml");
    }
    @FXML
    void CancelButtonPressed(ActionEvent event) throws Exception{
    	switchToTellerMainMenu(event);
    }
    //*********************************************
    
	public static void main(String[] args) {
		launch(args);
	}
}
