package src.program.client;
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
	//create instance of BankingClient to pass messages to server
	//ip4v and port of server
	static String ipAdd = "10.0.0.119";
	static int port = 9950;
	public static BankingClient bc = new BankingClient(ipAdd,port);
	
	private Scene scene;
	private Stage stage;
	private Parent root;
	private String startScreen="LoginChoice.fxml";
	private String appTitle="Banking Client";
	
	//********************************************************************
	//Common methods
	
	//Start function
	@Override
	public void start(Stage stage) throws Exception{
		bc.openConnection();
		System.out.println("Connection active: " + bc.isConnected());
		root = FXMLLoader.load(getClass().getResource(startScreen));
		scene = new Scene(root);
		stage.setTitle(appTitle);
		stage.setScene(scene);
		stage.show();
	}
	
	//Changes scene to fxml file passed in "file"
	public void changeScene(ActionEvent event,String file) throws Exception{
		root = FXMLLoader.load(getClass().getResource(file));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	//***********************************************************************
	
	//**********************************************************************
	//Common GUI components
	
	@FXML
	void LogoutButtonPressed(ActionEvent event) throws Exception{
		switchToLoginChoiceScreen(event);
	}
	
	@FXML
    void CancelButtonPressed(ActionEvent event) throws Exception{
    	switchToPreviousScreen(event);
    }
	
	//********************************************************************
	
	//**********************************************************************
	//Functions to change GUI page
	
	//Changes to previous screen
	public void switchToPreviousScreen(ActionEvent event) throws Exception{
		//Fix this!
		switchToTellerMainMenu(event);
		//root
		//scene = new Scene();
		//stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		//stage.setScene(scene);
		//stage.show();
	}
	
	//Changes scene to teller main menu
	public void switchToTellerMainMenu(ActionEvent event) throws Exception{
		changeScene(event,"TellerMainMenu.fxml");
	}
	
	//Changes scene to login choice screen
	public void switchToLoginChoiceScreen(ActionEvent event) throws Exception{
		changeScene(event,"LoginChoice.fxml");
	}
	
	//************************************************************************
	
	//************************************************************************
	//GUI components for login choice screen
	
	@FXML
    void AccountHolderLoginButtonPressed(ActionEvent event) {
    	//change root to AccountHolderMainMenu.fxml
    }
	
	@FXML
    void EndSessionButtonPressed(ActionEvent event) {
		try{
			bc.closeConnection();
		}
		catch (Exception ex) {System.err.println(ex);}
    	Platform.exit();
    }
	
	@FXML
	void TellerLoginButtonPressed(ActionEvent event) throws Exception {
		switchToTellerMainMenu(event);
	}
	
	//***************************************************************************
	
	//***************************************************************************
	//GUI components for teller main menu
	
    @FXML
    void SearchExistingAccountHolderButtonPressed(ActionEvent event) throws Exception {
    	//temporary for testing OCSF
    	switchToSampleBalanceScreen(event);
    }
    @FXML
    void CreateNewAccountHolderButtonPressed(ActionEvent event) {

    }
    
    //**************************************************************************
    
    //**************************************
    //Test for OCSF functionality
    
    @FXML
    private TextField TestConnectionField;
    @FXML
    void BalanceFinderSubmitButtonPressed(ActionEvent event) throws Exception {
    	String testConnectionString = TestConnectionField.getText();
    	//send string to server and save it
    	sendToServer(testConnectionString);
    }
    void switchToSampleBalanceScreen(ActionEvent event) throws Exception{
    	changeScene(event,"SampleBalanceFinder.fxml");
    }
    
    //*********************************************
    
    //************************************************
    //Send message to server
    
    public void sendToServer(Object msg) throws Exception{
    	bc.sendToServer(msg);
    }
    
    //**************************************************
    
    //Start function
	public static void main(String[] args) throws Exception{
		launch(args);
	}
}
