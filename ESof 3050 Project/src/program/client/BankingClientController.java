package src.program.client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import src.program.structs.AccountHolderInfo;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
	void AccountHolderCancelButtonPressed(ActionEvent event) throws Exception{
		switchToAccountHolderMainMenu(event);
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
	
	@FXML
    private ChoiceBox<?> SendingAccountChoiceBox;

    @FXML
    private TextField TransferAmountTextField;
	
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
	
	public void switchToAccountHolderLoginScreen(ActionEvent event) throws Exception{
		changeScene(event,"AccountHolderLogin.fxml");
	}
	
	public void switchToAccountHolderMainMenu(ActionEvent event) throws Exception{
		changeScene(event,"AccountHolderMainMenu.fxml");
	}
	
	public void switchToTellerSearchScreen(ActionEvent event) throws Exception{
		changeScene(event,"TellerSearchScreen.fxml");
	}
	
	public void switchToTransferBetweenAccountsScreen(ActionEvent event) throws Exception{
		changeScene(event,"TransferBetweenYourAccounts.fxml");
	}
	
	public void switchToTransferToAnotherAccountHolderScreen(ActionEvent event) throws Exception{
		changeScene(event,"TransferToAnotherAccountHolder.fxml");
	}
	
	public void switchToTransferConfirmationScreen(ActionEvent event) throws Exception{
		changeScene(event,"TransferConfirmation.fxml");
	}
	
	public void switchToDepositScreen(ActionEvent event) throws Exception{
		changeScene(event,"Deposit.fxml");
	}
	
	public void switchToWithdrawScreen(ActionEvent event) throws Exception{
		changeScene(event,"Withdraw.fxml");
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
    void AccountHolderLoginButtonPressed(ActionEvent event) throws Exception{
		switchToAccountHolderLoginScreen(event);
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
		if(bc==null||!bc.isConnected())
			switchToTellerMainMenu(event);
		else {
			switchToTellerMainMenu(event);//remove later
			//bc.loginAsTeller(TellerNumberTextField.getText(), TellerPasswordTextField.getText());
			//verify
			//if good switch to main menu
			//else change error label
			//if bc.ProccessTellerResult == true
		}
	}
	
	//**************************************************************************
	
	//***************************************************************************
	//GUI components for teller main menu
	
    @FXML
    void SearchExistingAccountHolderButtonPressed(ActionEvent event) throws Exception {
    	//temporary for testing OCSF
    	switchToTellerSearchScreen(event);
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
    
  //***********************************************************************
  	//GUI components for account holder login
  	
  	@FXML
  	private TextField AccountHolderCardNumberTextField;
  	
  	@FXML
  	private TextField AccountHolderPinTextField;
  	
  	@FXML
  	private TextArea AccountHolderLoginErrorTextArea;
  	
  	@FXML
  	void AccountHolderLoginSubmitButtonPressed(ActionEvent event) throws Exception{
  		if(bc==null || !bc.isConnected())
  			switchToAccountHolderMainMenu(event);
		else {
			//switchToAccountHolderMainMenu(event);//remove later
			//bc.loginAsAccoutnHolder(TellerNumberTextField.getText(), TellerPasswordTextField.getText());
			//verify
			//if good switch to main menu
			//else change error label
			//if bc.ProccessTellerResult == true
		}
	}
  	
  	//************************************************************************
  	
  	//**********************************************************************
  	//GUI components for teller search for account holder screen
  
    @FXML
    private ChoiceBox<String> TellerSearchParameterComboBox = new ChoiceBox<>();
    

    @FXML
    private TextField TellerSearchValueTextField;

    @FXML
    void TellerSearchSubmitButtonPressed(ActionEvent event) {

    }
    
  	//*********************************************************************
    
    //************************************************************************
    //GUI components for account holder main menu
    
    @FXML
    void TransferBetweenAccountsButtonPressed(ActionEvent event) throws Exception{
    	switchToTransferBetweenAccountsScreen(event);
    }
    
    @FXML
    void TransferToAnotherAccountHolderButtonPressed(ActionEvent event) throws Exception {
    	switchToTransferToAnotherAccountHolderScreen(event);
    }
    
    @FXML
    void ViewAccountsButtonPressed(ActionEvent event) throws Exception {
    	//TODO make list view GUI and link here
    }
    
    @FXML
    void DepositButtonPressed(ActionEvent event) throws Exception {
    	switchToDepositScreen(event);
    }

    @FXML
    void WithdrawButtonPressed(ActionEvent event) throws Exception {
    	switchToWithdrawScreen(event);
    }
    
    //**********************************************************************
    
    //**********************************************************************
    //GUI components for transfer between your accounts screen
    
    @FXML
    private ChoiceBox<?> ReceivingAccountChoiceBox;

    @FXML
    void TransferBetweenAccountsSubmitButtonPressed(ActionEvent event) throws Exception {
    	switchToTransferConfirmationScreen(event);
    }
    
    //*********************************************************************
    
    //********************************************************************
    //GUI components for transfer to another account holder
    
    @FXML
    private TextField ReceivingAccountTextField;
    
    @FXML
    void TransferToAnotherAccountHolderSubmitButtonPressed(ActionEvent event) throws Exception {
    	switchToTransferConfirmationScreen(event);
    }
    
    //*****************************************************************
    
    //*****************************************************************
    //GUI components for transfer confirmation
    
    @FXML
    private TextField ReceivingAccountConfirmationTextField;

    @FXML
    private TextField SendingAccountConfirmationTextField;

    @FXML
    private TextField TransferAmountConfirmationTextField;
    
    //****************************************************************
    
    //*****************************************************************
    //GUI components for account list view
    
    //*******************************************************************
  	
    //***************************************************************
    //GUI components for deposit screen
    
    @FXML
    void DepositSubmitButtonPressed(ActionEvent event) throws Exception{
    	switchToTransferConfirmationScreen(event);
    }
    
    //***************************************************************
    
    //**************************************************************
    //GUI components for withdraw screen
    
    @FXML
    void WithdrawSubmitButtonPressed(ActionEvent event) throws Exception{
    	switchToTransferConfirmationScreen(event);
    }
    
    //*************************************************************
    
    
    
    
    
    
    
    
    
    
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

	/*****************************************************
	 * REQUESTS AND HANDLE METHODS FOR THE OCSF
	 * Note: Methods with TODO need additional implementation
	 */
	
	////////////////////
	// BASIC MESSAGES //
	////////////////////
	
	/**
	 * handle a basic message sent by the server
	 */
	@Override
	public void handleBasicMessage(String message)
	{
		// TODO add handle code here
	}

	/**
	 * sends a basic message to the server
	 */
	@Override
	public void sendBasicMessage(String message)
	{
		try
		{
			bc.SendTestMessageToServer(message);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/////////////////////////////
	// LOGIN AS ACCOUNT HOLDER //
	/////////////////////////////
	
	/**
	 * sends a login request to the server
	 */
	@Override
	public void sendAccountHolderLoginRequest(String cardNumber, String pin)
	{
		try
		{
			bc.loginAsAccountHolder(cardNumber, pin);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/**
	 * handle login result
	 */
	@Override
	public void handleAccountHolderLoginResult(boolean isSuccessful)
	{
		// TODO handle login result
		
		if (isSuccessful)
		{
			//login successful
		}
		else
		{
			//login failed
		}
	}

	/////////////////////
	// LOGIN AS TELLER //
	/////////////////////
	
	/**
	 * login as teller request
	 */
	@Override
	public void sendTellerLoginRequest(String empID, String password)
	{
		try
		{
			bc.loginAsTeller(empID, password);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/**
	 * handle login as teller result from server
	 */
	@Override
	public void handleTellerLoginResult(boolean isSuccessful)
	{
		// TODO add handle code here
		
		if (isSuccessful)
		{
			//login successful change screen
		}
		else
		{
			//login failed pop up message
		}
	}

	/////////////////////////
	// FIND ACCOUNT HOLDER //
	/////////////////////////
	
	/**
	 * find account holder by email
	 */
	@Override
	public void sendFindAccountHolderByEmailRequest(String email)
	{
		try
		{
			bc.findAccountHolderByEmail(email);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}

	/**
	 * handle find result of the account holder
	 */
	@Override
	public void handleFindAccountHolderByEmailResult(AccountHolderInfo ahi)
	{
		// TODO add handle code here
		
		if (ahi.getHasInfo())
		{
			//show account holder information
		}
		else
		{
			//show not found message
		}
	}

	///////////////////////////
	// CREATE ACCOUNT HOLDER //
	///////////////////////////
	
	/**
	 * creates a new account holder
	 */
	@Override
	public void createNewAccountHolder(String email)
	{
		try
		{
			bc.createAccountHolder(email);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * handles the result of an account holder creation
	 */
	@Override
	public void handleCreateNewAccountHolderResult(AccountHolderInfo ahi)
	{
		// TODO add handle code
		
		if (ahi.getHasInfo())
		{
			//account was created
		}
		else
		{
			//could not create new account
		}
	}

	///////////////////////////
	// DELETE ACCOUNT HOLDER //
	///////////////////////////
	
	/**
	 * deletes an existing account holder
	 */
	@Override
	public void deleteAccountHolder(String accountNumber, String pin)
	{
		try
		{
			bc.deleteAccountHolder(accountNumber, pin);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	/**
	 * handles the result of an account holder deletion
	 */
	@Override
	public void handleAccountHolderDeletion(boolean isSuccessful)
	{
		// TODO add handle code
		
		if (isSuccessful)
		{
			//account was deleted
		}
		else
		{
			//could not delete existing account
		}
	}

	////////////////////
	// CREATE PERSON  //
	////////////////////
	
	@Override
	public void createNewPerson(String firstName, String lastName, String sin, String dateOfBirth)
	{
		try
		{
			bc.createPerson(firstName, lastName, sin, dateOfBirth);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public void handleCreatePersonResult(boolean isSuccessful)
	{
		// TODO add handle code
		
		if (isSuccessful)
		{
			//account was deleted
		}
		else
		{
			//could not delete existing account
		}
	}

	////////////////////
	// DELETE PERSON  //
	////////////////////
	
	@Override
	public void deletePerson(String sin)
	{
		try
		{
			bc.deletePerson(sin);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void handlePersonDeletion(boolean isSuccessful)
	{
		// TODO add handle code
		
		if (isSuccessful)
		{
			//account was deleted
		}
		else
		{
			//could not delete existing account
		}
	}
}
