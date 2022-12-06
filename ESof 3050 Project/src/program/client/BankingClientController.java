package src.program.client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import src.program.server.Address;
import src.program.structs.AccountHolderInfo;
import src.program.structs.AccountInfo;
import src.program.structs.AccountType;
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
	private static boolean offline;
	
	private static ActionEvent ae;
	private static TextArea ta;
	
	//5 parameters for account holder + pin
	private static String firstName;
	private static String lastName;
	private static String sin;
	private static String dob;
	private static String email;
	private static String pin;
	
	//5 lists for address parameters
	private static ObservableList<String> streetNameList=FXCollections.observableArrayList();
	private static ObservableList<String> streetNumberList=FXCollections.observableArrayList();
	private static ObservableList<String> postalCodeList=FXCollections.observableArrayList();
	private static ObservableList<String> provinceList=FXCollections.observableArrayList();
	private static ObservableList<String> countryList=FXCollections.observableArrayList();
	//addAddress(streetName.get(n), streetNumber.get(n), postalCode.get(n), province.get(n), country.get(n), sin)
	
	
	
	
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
		catch (Exception e) {e.printStackTrace();}
    	Platform.exit();
    }
	
	@FXML
	void LoginCancelButtonPressed(ActionEvent event) throws Exception{
		switchToLoginChoiceScreen(event);
	}
	
	@FXML
    private ChoiceBox<String> SendingAccountChoiceBox;

    @FXML
    private TextField TransferAmountTextField;
	
	//********************************************************************
	
	//**********************************************************************
	//Functions to change GUI page
	
	//Changes scene to teller main menu
	public void switchToTellerMainMenu(ActionEvent event) throws Exception{
		firstName=null;
		lastName=null;
		sin=null;
		dob=null;
		email=null;
		pin=null;
		
		streetNameList.removeAll(streetNameList);//clear list of addresses
		streetNumberList.removeAll(streetNumberList);
		postalCodeList.removeAll(postalCodeList);
		provinceList.removeAll(provinceList);
		countryList.removeAll(countryList);
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
	
	public void switchToPinScreen(ActionEvent event) throws Exception{
		changeScene(event,"Pin.fxml");
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
	
	public void switchToTellerSearchResultScreen(ActionEvent event)throws Exception{
		changeScene(event,"TellerSearchResult.fxml");
	}
	
	public void switchToAccountViewerScreen(ActionEvent event) throws Exception{
		changeScene(event,"AccountViewer.fxml");
	}
	
	public void switchToAccountHistoryScreen(ActionEvent event) throws Exception{
		changeScene(event,"AccountHistory.fxml");
	}
	
	public void switchToEditProfileScreen(ActionEvent event) throws Exception{
		changeScene(event,"EditProfile.fxml");
	}
	
	public void switchToCreateAccountScreen(ActionEvent event) throws Exception{
		changeScene(event,"CreateAccount.fxml");
	}
	
	public void switchToDeleteAccountScreen(ActionEvent event) throws Exception{
		changeScene(event,"DeleteAccount.fxml");
	}
	
	public void switchToDeleteProfileScreen(ActionEvent event) throws Exception{
		changeScene(event,"DeleteProfile.fxml");
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
		switchToLoginChoiceScreen(event);
		}
		catch(Exception ex) {
			connectErrorTextArea.setText("Connection Failed");
		}
	}
	
	@FXML
	void OfflineButtonPressed(ActionEvent event) throws Exception{
		offline=true;
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
	private PasswordField TellerPasswordField;
	
	@FXML
	private TextArea TellerLoginErrorTextArea; //needs to be public to be changed by 
	
	@FXML
	void TellerLoginSubmitButtonPressed(ActionEvent event) throws Exception{
		if(offline)
			switchToTellerMainMenu(event);
		else {
			ae=event;
			ta=TellerLoginErrorTextArea;
			sendTellerLoginRequest(TellerNumberTextField.getText(), TellerPasswordField.getText());
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
    private TextArea CreateAccountHolderErrorTextArea;
    
    @FXML
    void NewAccountNextButtonPressed(ActionEvent event) throws Exception{
    	if(offline)
    		switchToNewAccountHolderAddressScreen(event);
    	if(!FirstNameTextField.getText().equals(""))
    		firstName=FirstNameTextField.getText();
    	else
    		CreateAccountHolderErrorTextArea.setText("Blank field");
    	if(!LastNameTextField.getText().equals(""))
    		lastName=LastNameTextField.getText();
    	else
    		CreateAccountHolderErrorTextArea.setText("Blank field");
    	if(!sinTextField.getText().equals(""))
    		sin=sinTextField.getText();
    	else
    		CreateAccountHolderErrorTextArea.setText("Blank field");
    	if(!emailTextField.getText().equals(""))
    		email=emailTextField.getText();
    	else
    		CreateAccountHolderErrorTextArea.setText("Blank field");
		if(monthTextField.getText().length()==2&&dayTextField.getText().length()==2&&yearTextField.getText().length()==4) {
			dob=monthTextField.getText()+"/"+dayTextField.getText()+"/"+yearTextField.getText();
		}
		else
			CreateAccountHolderErrorTextArea.setText("Date of birth invalid");
		if(firstName!=null&&lastName!=null&&sin!=null&&email!=null&&dob!=null)
			switchToNewAccountHolderAddressScreen(event);
    }
    
    
    //**************************************************************************
    
    //************************************************************************
    //GUI components for new account holder address addition
    
    @FXML
    private TextField NewAccountHolderAddressTotalTextField;
    
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
    	switchToPinScreen(event);
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
  		streetNameList.add(StreetNameTextField.getText());
  		streetNumberList.add(StreetNumberTextField.getText());
  		postalCodeList.add(PostalCodeTextField.getText());
  		provinceList.add(ProvinceTextField.getText());
  		countryList.add(CountryTextField.getText());
  		
  		switchToNewAccountHolderAddressScreen(event);
  	}
  	
  	//*******************************************************************
  	
  	//******************************************************************
  	//GUI components for card number generation and PIN entry
  	
  	@FXML
  	private TextField NewPinTextField;
  	
  	@FXML
  	void PinBackButtonPressed(ActionEvent event) throws Exception{
  		switchToNewAccountHolderAddressScreen(event);
  	}
  	
  	@FXML
  	void PinDoneButtonPressed(ActionEvent event) throws Exception{
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
  	private PasswordField AccountHolderPinPasswordField;
  	
  	@FXML
  	private TextArea AccountHolderLoginErrorTextArea;
  	
  	@FXML
  	void AccountHolderLoginSubmitButtonPressed(ActionEvent event) throws Exception{
  		if(offline)
  			switchToAccountHolderMainMenu(event);
		else {
			ae=event;
			ta=AccountHolderLoginErrorTextArea;
			sendAccountHolderLoginRequest(AccountHolderCardNumberTextField.getText(), AccountHolderPinPasswordField.getText());
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
    private ChoiceBox<String> TellerSearchParameterChoiceBox;
    

    @FXML
    private TextField TellerSearchValueTextField;

    @FXML
    void TellerSearchSubmitButtonPressed(ActionEvent event) throws Exception{
    	switchToTellerSearchResultScreen(event);
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
    	switchToAccountViewerScreen(event);
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
    
    //*************************************************************
    //GUI components for search results screen
    
    @FXML
    private ListView<?> CardNumberListView;

    @FXML
    private TextField ResultEmailTextField;

    @FXML
    private TextField ResultFirstNameTextField;

    @FXML
    private TextField ResultLastNameTextField;

    @FXML
    private TextField ResultSearchValueTextField;

    @FXML
    private TextField ResultSinTextField;

    @FXML
    private TextField ResultTypeTextField;

    @FXML
    void SelectAccountHolderButtonPressed(ActionEvent event) throws Exception{
    	switchToEditProfileScreen(event);
    }
    
    //*************************************************************
    
    //********************************************************
    //GUI components for account viewer
    
    @FXML
    private ListView<?> AccountNumberListView;

    @FXML
    private TextField AccountsBalanceTextField;

    @FXML
    private TextField AccountsCardNumberTextField;

    @FXML
    private TextField AccountsTypeTextField;

    @FXML
    void ViewAccountHistoryButtonPressed(ActionEvent event) throws Exception {
    	switchToAccountHistoryScreen(event);
    }
    
    //**********************************************************
    
    //***********************************************************
    //GUI components for account history screen
    
    @FXML
    private ListView<?> AccountHistoryListView;

    @FXML
    private TextField AccountHistoryNumberTextField;

    @FXML
    private TextField TransferHistoryAmountTextField;

    @FXML
    private TextField TransferHistoryDateTextField;

    @FXML
    private TextField TransferHistoryReceiverTextField;

    @FXML
    private TextField TransferHistorySenderTextField;
    
    //**********************************************************
    
    //********************************************************
    //GUI components for edit profile screen
    
    @FXML
    void AddAccountButtonPressed(ActionEvent event) throws Exception {
    	switchToCreateAccountScreen(event);
    }

    @FXML
    void DeleteProfileButtonPressed(ActionEvent event) throws Exception {
    	switchToDeleteProfileScreen(event);
    }

    @FXML
    void RemoveAccountButtonPressed(ActionEvent event) throws Exception {
    	switchToDeleteAccountScreen(event);
    }

    @FXML
    void ViewAddressesButtonPressed(ActionEvent event) throws Exception {

    }
    
    //*******************************************************
    
    //********************************************************
    //GUI components for create account screen
    
    @FXML
    private ChoiceBox<String> AccountTypeChoiceBox;

    @FXML
    private TextField TellerCardNumberTextField;

    @FXML
    void CreateAccountButtonPressed(ActionEvent event) {

    }
    
    //*********************************************************
    
    
    
    
    
    
    
    
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
    
    private String SearchParameterList[]= {"Card Number","SIN","Email"};
    private String AccountTypeList[]= {"Chequing Account","Savings Account","Mortgage Account","Line-Of-Credit"};
    private ObservableList<String> ActiveAccounts = FXCollections.observableArrayList();
    
    //Initializer of GUI elements
    public void initialize() {
    	if(FirstNameTextField!=null && firstName!=null) {
    		FirstNameTextField.setText(firstName);
    		LastNameTextField.setText(lastName);
    		sinTextField.setText(sin);
    		emailTextField.setText(email);
    		dayTextField.setText(dob.substring(3, 5)); //day
    		monthTextField.setText(dob.substring(0, 2)); //month
    		yearTextField.setText(dob.substring(6, 10)); //year
    	}
    	if(ipAddTextField!=null) //connect screen
    		offline=false;
    	if(TellerSearchParameterChoiceBox!=null) //teller search screen
    		TellerSearchParameterChoiceBox.getItems().addAll(SearchParameterList);
    	if(AccountTypeChoiceBox!=null) { //new account
    		AccountTypeChoiceBox.getItems().addAll(AccountTypeList);
    	}
    	if(SendingAccountChoiceBox!=null) { //
    		SendingAccountChoiceBox.getItems().addAll(ActiveAccounts);
    		//TODO have BankingClient add items to ActiveAccounts. Specifically get all accounts belonging to a card number.
    	}
    	if(NewAccountHolderAddressTotalTextField!=null) {
    		NewAccountHolderAddressTotalTextField.setText(Integer.toString(streetNameList.size()));
    	}
    }
    
    //Start function
	public static void main(String[] args) throws Exception{
		//initialize();
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
			//login success next screen
			System.out.println("Login Success");
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						switchToAccountHolderMainMenu(ae);
					}
					catch(Exception e) {e.printStackTrace();}
				}
			});
		}
		else
		{
			//login failed print error
			System.out.println("Login Failed");
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						ta.setText("Login Failed");
					}
					catch(Exception e) {e.printStackTrace();}
				}
			});
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
			
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							switchToTellerMainMenu(ae);
						}
						catch(Exception e) {e.printStackTrace();}
					}
				});
		}
		else
		{
			
			//login failed print error
			System.out.println("Login Failed");
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						ta.setText("Login Failed");
					}
					catch(Exception e) {e.printStackTrace();}
				}
			});
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
	public void createNewAccountHolder(String email, String pin, String sin)
	{
		try
		{
			bc.createAccountHolder(email,pin,sin);
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
	public void deleteAccountHolder(String accountNumber)
	{
		try
		{
			bc.deleteAccountHolder(accountNumber);
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
			//person was created
		}
		else
		{
			//person was not created
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
			//person was deleted
		}
		else
		{
			//person was not deleted
		}
	}

	//////////////////
	// ADD ADDRESS  //
	//////////////////
	
	@Override
	public void addAddress(String streetName, String streetNumber, String postalCode, String province, String country,
			String sid)
	{
		try
		{
			bc.addAddress(streetName, streetNumber, postalCode, province, country, sid);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public void handleAddAddressResult(boolean isSuccessful)
	{
		// TODO add handle code
		
		if (isSuccessful)
		{
			//address was added
		}
		else
		{
			//address could not be added
		}
	}

	/////////////////////
	// REMOVE ADDRESS  //
	/////////////////////
	
	@Override
	public void removeAddress(String sin, String postalCode)
	{
		try
		{
			bc.removeAddress(sin, postalCode);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void handleRemoveAddressResult(boolean isSuccessful)
	{
		// TODO add handle code
		
		if (isSuccessful)
		{
			//address was removed
		}
		else
		{
			//address was not removed
		}
	}

	////////////////////////////////////////
	// ADD ACCOUNT HOLDER ROLE TO PERSON  //
	////////////////////////////////////////
	
	/**
	 * sends a request to the server to add an account holder
	 * role to a person
	 */
	@Override
	public void addAccountHolderToPerson(String sin, String email)
	{
		try
		{
			bc.addAccountHolderToPerson(sin, email);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * handles the result of adding an account holder to a person
	 */
	@Override
	public void handleAccountHolderToPersonResult(boolean isSuccessful)
	{
		// TODO add handle code
		
		if (isSuccessful)
		{
			//account holder added to person
		}
		else
		{
			//not added
		}
	}
	
	////////////////////
	// CREATE ACCOUNT //
	////////////////////
	
	/**
	 * tells the server to create a new account and add it to an
	 * existing account holder. Only tellers can do this action.
	 * @param accountType account type
	 * @param cardNumber the account holder's card number
	 */
	@Override
	public void createAccount(AccountType accountType, String cardNumber)
	{
		try
		{
			bc.createAccount(accountType, cardNumber);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Handles the result of an account being created
	 * @param isSuccessful true if the account was deleted, false
	 * if not
	 */
	@Override
	public void handleAccountCreation(boolean isSuccessful)
	{
		// TODO add handle code
		
		if (isSuccessful)
		{
			//account created
		}
		else
		{
			//not created
		}
	}
	
	////////////////////
	// DELETE ACCOUNT //
	////////////////////
	
	/**
	 * tells the server to delete an existing account and remove it from an
	 * existing account holder. Only tellers can do this action.
	 * @param accountType account type
	 * @param cardNumber the account holder's card number
	 */
	@Override
	public void deleteAccount(AccountType accountType, String cardNumber)
	{
		try
		{
			bc.deleteAccount(accountType, cardNumber);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Handles the result of an account being deleted
	 * @param isSuccessful true if the account holder was deleted, false
	 * if not
	 */
	@Override
	public void handleAccountDeletion(boolean isSuccessful)
	{
		// TODO add handle code
		
		if (isSuccessful)
		{
			//account deleted
		}
		else
		{
			//account not deleted
		}
	}

	/////////////////
	// GET ACCOUNT //
	/////////////////
	
	/**
	 * Tells the server to get information about a specific account from
	 * a specific account holder
	 * @param accountType account type (chequing, savings, etc.)
	 * @param cardNumber the account holder's card number
	 */
	@Override
	public void getAccount(AccountType accountType, String cardNumber)
	{
		try
		{
			bc.getAccount(accountType, cardNumber);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Handles the information obtained from the server about an account
	 * @param accountInfo
	 */
	@Override
	public void handleAccountInformation(AccountInfo accountInfo)
	{
		//TODO: add handle code
		
		if (accountInfo.getHasInfo())
		{
			//populate info
		}
		else
		{
			//no info in account info
		}
	}
}
