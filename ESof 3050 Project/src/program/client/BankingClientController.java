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

public class BankingClientController extends Application implements IBankingClientController {
	//create instance of BankingClient to pass messages to server
	//ip4v and port of server
<<<<<<< HEAD
	static String ipAdd = "192.168.250.44";
	static int port = 99511;
=======
	static String ipAdd = "10.100.150.1";
	static int port = 9950;
>>>>>>> branch 'master' of https://github.com/Arduckert/ESOF-3050-Group-1
	public static BankingClient bc;
	
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
		bc = new BankingClient(ipAdd,port,this);
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
    void TellerCancelButtonPressed(ActionEvent event) throws Exception{
    	switchToTellerMainMenu(event);
    }
	
	//********************************************************************
	
	//**********************************************************************
	//Functions to change GUI page
	
	//Changes scene to teller main menu
	public void switchToTellerMainMenu(ActionEvent event) throws Exception{
		changeScene(event,"TellerMainMenu.fxml");
	}
	
	//Changes scene to login choice screen
	public void switchToLoginChoiceScreen(ActionEvent event) throws Exception{
		changeScene(event,"LoginChoice.fxml");
	}
	
	//Change scene to account holder creation screen
	public void switchToNewAccountHolderScreen(ActionEvent event) throws Exception{
		changeScene(event,"NewAccountHolder.fxml");
	}
	
	//Change scene to account holder creation address screen
	public void switchToNewAccountHolderAddressScreen(ActionEvent event) throws Exception{
		changeScene(event,"NewAccountHolderAddress.fxml");
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
    void CreateNewAccountHolderButtonPressed(ActionEvent event) throws Exception {
    	switchToNewAccountHolderScreen(event);
    }
    
    //**************************************************************************
    

    //**************************************************************************
    //GUI components for new account holder screen
    
    @FXML
    private TextField sinTextField;
    
    @FXML
    private TextField FirstNameTextField;
    
    @FXML
    private TextField LastNameTextField;
    
    @FXML
    private TextField emailTextField;
    
    @FXML
    private TextField dayTextField;
    
    @FXML
    private TextField monthTextField;
    
    @FXML
    private TextField yearTextField;
    
    @FXML
    void NewAccountNextButtonPressed(ActionEvent event) throws Exception{
    	switchToNewAccountHolderAddressScreen(event);
    }
    
    
    //**************************************************************************
    
    //************************************************************************
    //GUI components for new account holder address addition
    
    @FXML
    void AddressBackButtonPressed(ActionEvent event) throws Exception{
    	switchToNewAccountHolderScreen(event);
    }
    
    @FXML
    void AddAddressButtonPressed(ActionEvent event) throws Exception{
    	//open secondary window for address entry
    }
    
    //**************************************************************************
    
    //***************************************************************************
    //GUI components for address fill out screen
    @FXML
    void AddressSubmitButtonPressed(ActionEvent event) throws Exception{
    	//increment address total
    	//add new address on server side
    }
    
    @FXML
    void AddressCancelButtonPressed(ActionEvent event) throws Exception{
    	//close window
    }
    
    //**************************************************************************
  
    
    //**************************************
    //Test for OCSF functionality
    
    @FXML
    private TextField TestConnectionField;
    @FXML
    void BalanceFinderSubmitButtonPressed(ActionEvent event) throws Exception
    {
    	String testConnectionString = TestConnectionField.getText();
    	
    	//send string to server and save it
    	//bc.SendTestMessageToServer(testConnectionString);
    	
    	//testing login function
    	bc.LoginAsAccountHolder("1234567",testConnectionString);
    	
    	//if good > change page
    	
    	//if bad > pop up
    	
    }
    
    void switchToSampleBalanceScreen(ActionEvent event) throws Exception{
    	changeScene(event,"SampleBalanceFinder.fxml");
    }
    
    //*********************************************
    
    //************************************************
    //Send message to server
    
    public void sendToServer(String msg) throws Exception
    {
    	bc.sendToServer(msg);
    }
    
    //**************************************************
    
    //Start function
	public static void main(String[] args) throws Exception{
		launch(args);
	}
}
