package src.program.client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;

// *** TODO: IMPLEMENT INTERFACE *** //
public class BankingClientController extends Application implements IBankingClientController {
	//create instance of BankingClient to pass messages to server
	//ip4v and port of server
	static String ipAdd;
	static int port;
	public static BankingClient bc;
	
	private Scene scene;
	private Stage stage;
	private Parent root;
	private String startScreen="Connect.fxml";
	private String appTitle="Banking Client";
	
	//********************************************************************
	//Common methods
	
	//Start function
	@Override
	public void start(Stage stage) throws Exception{
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
	
	@FXML
    void EndSessionButtonPressed(ActionEvent event) {
		try{
			if(bc!=null)
				bc.closeConnection();
		}
		catch (Exception ex) {System.err.println(ex);}
    	Platform.exit();
    }
	
	@FXML
	void LoginCancelButtonPressed(ActionEvent event) throws Exception{
		switchToLoginChoiceScreen(event);
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
	
	public void switchToTellerLoginScreen(ActionEvent event) throws Exception{
		changeScene(event,"TellerLogin.fxml");
	}
	
	public void switchToNewAddressScreen(ActionEvent event) throws Exception{
		changeScene(event,"NewAddress.fxml");
	}
	
	public void switchToCardNumberAndPinScreen(ActionEvent event) throws Exception{
		changeScene(event,"CardNumberAndPin.fxml");
	}
	
	public void switchToNewAccountHolderConfirmationScreen(ActionEvent event) throws Exception{
		changeScene(event,"AccountHolderConfirmation.fxml");
	}
	
	//************************************************************************
	
	//*************************************************************************
	//GUI components for initial connection screen
	
	@FXML
	private TextField ipAddTextField;
	
	@FXML
	private TextField portTextField;
	
	@FXML
	private TextArea connectErrorTextArea;
	
	@FXML 
	void ConnectButtonPressed(ActionEvent event) throws Exception{
		try {
		if(!ipAddTextField.getText().equals(""))
			ipAdd=ipAddTextField.getText();
		else
			ipAdd = Inet4Address.getLocalHost().getHostAddress(); //default to local ip for testing
		if(!portTextField.getText().equals(""))
			port=Integer.parseInt(portTextField.getText());
		else
			port=9950; //default to 9950
		bc = new BankingClient(ipAdd,port,this);
		bc.openConnection();
		System.out.println("Connection active: " + bc.isConnected());
		switchToLoginChoiceScreen(event);
		}
		catch(Exception ex) {
			connectErrorTextArea.setText("Connection Failed");
		}
	}
	
	@FXML
	void OfflineButtonPressed(ActionEvent event) throws Exception{
		switchToLoginChoiceScreen(event);
	}
	
	//**********************************************************************
	
	//************************************************************************
	//GUI components for login choice screen
	
	@FXML
    void AccountHolderLoginButtonPressed(ActionEvent event) {
    	//change root to AccountHolderMainMenu.fxml
    }
	
	@FXML
	void TellerLoginButtonPressed(ActionEvent event) throws Exception {
		switchToTellerLoginScreen(event);
	}
	
	//***************************************************************************
	
	//*************************************************************************
	//GUI components for teller login screen
	
	@FXML
	private TextField TellerNumberTextField;
	
	@FXML
	private TextField TellerPasswordTextField;
	
	@FXML
	public  TextArea TellerLoginErrorTextArea; //needs to be public to be changed by 
	
	@FXML
	void TellerLoginSubmitButtonPressed(ActionEvent event) throws Exception{
		if(bc==null)
			switchToTellerMainMenu(event);
		else {
			switchToTellerMainMenu(event);//remove later
			//bc.loginAsTeller(TellerNumberTextField.getText(), TellerPasswordTextField.getText());
			//verify
			//if good switch to main menu
			//else change error label
		}
	}
	
	//**************************************************************************
	
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
    	//List addressList = new ArrayList();
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
    	switchToNewAddressScreen(event);
    }
    
    @FXML
    void AddressNextButtonPressed(ActionEvent event) throws Exception{
    	switchToCardNumberAndPinScreen(event);
    }
    
    //**************************************************************************
    
    //********************************************************************
  	//GUI components for new address screen
  	
  	@FXML
  	private TextField StreetNameTextField;
  	
  	@FXML
  	private TextField StreetNumberTextField;
  	
  	@FXML
  	private TextField PostalCodeTextField;
  	
  	@FXML
  	private TextField ProvinceTextField;
  	
  	@FXML
  	private TextField CountryTextField;
  	
  	@FXML
  	void AddressCancelButtonPressed(ActionEvent event) throws Exception{
  		switchToNewAccountHolderAddressScreen(event);
  	}
  	
  	@FXML
  	void AddressSubmitButtonPressed(ActionEvent event) throws Exception{
  		//increment count
  		//new address to list
  		switchToNewAccountHolderAddressScreen(event);
  	}
  	
  	//*******************************************************************
  	
  	//******************************************************************
  	//GUI components for card number generation and PIN entry
  	
  	@FXML
  	private TextField NewCardNumberTextField;
  	
  	@FXML
  	private TextField NewPinTextField;
  	
  	@FXML
  	void CardNumberAndPinBackButtonPressed(ActionEvent event) throws Exception{
  		switchToNewAccountHolderAddressScreen(event);
  	}
  	
  	@FXML
  	void CardNumberAndPinDoneButtonPressed(ActionEvent event) throws Exception{
  		switchToNewAccountHolderConfirmationScreen(event);
  	}
  	
  	//*******************************************************************
  	
  	//********************************************************************
  	//GUI components for new account holder confirmation
  	
  	@FXML
  	private TextField ConfirmationFirstNameTextField;
  	
  	@FXML
  	private TextField ConfirmationLastNameTextField;
  	
  	@FXML
  	private TextField ConfirmationCardNumberTextField;
  	
  	@FXML
  	private TextField ConfirmationPinTextField;
  	
  	//*********************************************************************
    
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
    	bc.loginAsAccountHolder("1234567",testConnectionString);
    	
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
