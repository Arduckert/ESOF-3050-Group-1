/**
 * Brief description of this file:
 * Contains the handlers for user interaction of the GUI
 * Performs the required function calls in response to a user input
 * and alters the GUI according to server response
 * 
 * File created by Matthew Camire
 * File approved by Connor McNally and Aric Duckert
 * 
 */


package src.program.client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import src.program.structs.AccountHolderInfo;
import src.program.structs.AccountInfo;
import src.program.structs.AccountType;
import src.program.structs.BillAction;
import src.program.structs.InputType;
import src.program.structs.RecordInfo;
import src.program.structs.TransactionInfo;
import src.program.structs.TransferType;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.Inet4Address;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
	
	//static elements to pass between scenes
	//methods called by BankingClient can't access GUI components without these
	private static ActionEvent ae;
	private static TextArea ta;
	private static ListView<AccountHolderInfo> ahilv;
	
	//5 parameters for account holder + pin and card number
	private static String firstName;
	private static String lastName;
	private static String sin;
	private static String dob;
	private static String email;
	private static String pin;
	
	
	private static String cardNumber;
	private static String empID;
	
	//5 lists for address parameters
	private static ObservableList<String> streetNameList=FXCollections.observableArrayList();
	private static ObservableList<String> streetNumberList=FXCollections.observableArrayList();
	private static ObservableList<String> postalCodeList=FXCollections.observableArrayList();
	private static ObservableList<String> provinceList=FXCollections.observableArrayList();
	private static ObservableList<String> countryList=FXCollections.observableArrayList();
	//addAddress(streetName.get(n), streetNumber.get(n), postalCode.get(n), province.get(n), country.get(n), sin)
	
	//
	private static String searchParameter;
	private static String searchValue;
	private static ObservableList<AccountHolderInfo> searchList=FXCollections.observableArrayList();
	
	private static String accountType;
	
	private static ObservableList<AccountInfo> accountList=FXCollections.observableArrayList();
	private static ObservableList<AccountInfo> receivingAccountList=FXCollections.observableArrayList();
	private static ObservableList<AccountInfo> sendingAccountList=FXCollections.observableArrayList();
	
	private static AccountInfo deletedAccount;
	
	private static String sendingAccount;
	private static String receivingAccount;
	private static String amount;
	
	private static String newAccount;
	
	private static ObservableList<TransactionInfo> historyList=FXCollections.observableArrayList();
	
	private static AccountInfo selectedAccount;
	
	
	
	
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
	
	public void refreshAccounts() {
		accountList.clear();
		sendingAccountList.clear();
		receivingAccountList.clear();
		getAccounts(cardNumber);
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
    private ChoiceBox<AccountInfo> SendingAccountChoiceBox;
	
	@FXML
	private ChoiceBox<AccountInfo> ReceivingAccountChoiceBox;

    @FXML
    private TextField TransferAmountTextField;
    
    @FXML
    void EditCancelButtonPressed(ActionEvent event) throws Exception{
    	switchToEditProfileScreen(event);
    }
    
    @FXML
    private TextArea ErrorTextArea;
    
    @FXML
    private TextField SenderBalanceTextField;
    
    @FXML
    private TextField ReceiverBalanceTextField;
    
	//********************************************************************
	
	//**********************************************************************
	//Functions to change GUI page
	
	//Changes scene to teller main menu
	public void switchToTellerMainMenu(ActionEvent event) throws Exception{
		//values set to null here because main menu has no passive elements (like text box)
		//therefore we can't detect it in initialize()
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
		
		searchList.removeAll(searchList); //deletes all
		
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
		refreshAccounts();
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
		refreshAccounts();
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
	
	public void switchToCreateAccountConfirmationScreen(ActionEvent event) throws Exception{
		changeScene(event,"CreateAccountConfirmation.fxml");
	}
	
	public void switchToDeleteAccountConfirmationScreen(ActionEvent event) throws Exception{
		changeScene(event,"DeleteAccountConfirmation.fxml");
	}
	
	public void switchToDeleteProfileConfirmationScreen(ActionEvent event) throws Exception{
		changeScene(event,"DeleteProfileConfirmation.fxml");
	}
	
	public void switchToMortgageAccountScreen(ActionEvent event) throws Exception{
		changeScene(event,"MortgageAccount.fxml");
	}
	
	public void switchToLOCScreen(ActionEvent event) throws Exception{
		changeScene(event,"LOCAccount.fxml");
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
			empID=TellerNumberTextField.getText();
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
    	try {
    		Integer.valueOf(monthTextField.getText());
    		Integer.valueOf(dayTextField.getText());
    		Integer.valueOf(yearTextField.getText());
    		if(monthTextField.getText().length()==2&&dayTextField.getText().length()==2&&yearTextField.getText().length()==4)
    			dob=monthTextField.getText()+"/"+dayTextField.getText()+"/"+yearTextField.getText();
    		else
    			CreateAccountHolderErrorTextArea.setText("Date of birth invalid");
    	}
    	catch(Exception e) {CreateAccountHolderErrorTextArea.setText("Date of birth invalid");}
		
    	if(!FirstNameTextField.getText().equals(""))
    		firstName=FirstNameTextField.getText();
    	else
    		CreateAccountHolderErrorTextArea.setText("Blank field");
    	if(!LastNameTextField.getText().equals(""))
    		lastName=LastNameTextField.getText();
    	else
    		CreateAccountHolderErrorTextArea.setText("Blank field");
    	try {
    		Integer.valueOf(sinTextField.getText());
    		if(!sinTextField.getText().equals(""))
    			sin=sinTextField.getText();
    		else
    			CreateAccountHolderErrorTextArea.setText("Blank field");
    	}
    	catch(Exception e) {CreateAccountHolderErrorTextArea.setText("Sin invalid");}
    	if(!emailTextField.getText().equals(""))
    		email=emailTextField.getText();
    	else
    		CreateAccountHolderErrorTextArea.setText("Blank field");
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
  	private TextArea AddressErrorTextArea;
  	
  	@FXML
  	void AddressCancelButtonPressed(ActionEvent event) throws Exception{
  		switchToNewAccountHolderAddressScreen(event);
  	}
  	
  	@FXML
  	void AddressSubmitButtonPressed(ActionEvent event) throws Exception{
  		try {
  			Integer.valueOf(StreetNumberTextField.getText());
  			streetNameList.add(StreetNameTextField.getText());
  			streetNumberList.add(StreetNumberTextField.getText());
  			postalCodeList.add(PostalCodeTextField.getText());
  			provinceList.add(ProvinceTextField.getText());
  			countryList.add(CountryTextField.getText());
  			switchToNewAccountHolderAddressScreen(event);
  		}
  		catch(Exception e) {AddressErrorTextArea.setText("Invalid street number");}
  	}
  	
  	//*******************************************************************
  	
  	//******************************************************************
  	//GUI components for PIN entry
  	
  	@FXML
  	private TextField NewPinTextField;
  	
  	@FXML
  	private TextArea PinErrorTextArea;
  	
  	@FXML
  	void PinBackButtonPressed(ActionEvent event) throws Exception{
  		switchToNewAccountHolderAddressScreen(event);
  	}
  	
  	@FXML
  	void PinDoneButtonPressed(ActionEvent event) throws Exception{
  		try {
  			Integer.valueOf(NewPinTextField.getText());
  			if(NewPinTextField.getText().length()==4) {
  				pin=NewPinTextField.getText();
  				ae=event;
  				ta=PinErrorTextArea;
  				createNewPerson(firstName,lastName,sin,dob);
  				createNewAccountHolder(email,pin,sin);
  				for(int i=0;i<streetNameList.size();i++) {
  					addAddress(streetNameList.get(i),streetNumberList.get(i),
  						postalCodeList.get(i),provinceList.get(i), countryList.get(i),sin);
  				}
  			}
  			else
  				PinErrorTextArea.setText("Invalid pin");
  		}
  		catch(Exception e) {PinErrorTextArea.setText("Invalid pin");}
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
			cardNumber=AccountHolderCardNumberTextField.getText();
			sendAccountHolderLoginRequest(AccountHolderCardNumberTextField.getText(), AccountHolderPinPasswordField.getText());
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
    	searchParameter=TellerSearchParameterChoiceBox.getValue();
    	searchValue=TellerSearchValueTextField.getText();
    	if(searchParameter.equals("Email"))
    		sendFindAccountHolderRequest(InputType.EMAIL,searchValue);
    	else if(searchParameter.equals("SIN"))
    		sendFindAccountHolderRequest(InputType.SIN,searchValue);
    	else if(searchParameter.equals("Card Number"))
    		sendFindAccountHolderRequest(InputType.CARD_NUMBER,searchValue);
    	switchToTellerSearchResultScreen(event);
    	//TODO add other search parameters
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
    	historyList.clear();
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
    void TransferBetweenAccountsSubmitButtonPressed(ActionEvent event) throws Exception {
    	try {
    		ta=ErrorTextArea;
    		ae=event;
    		Double.valueOf(TransferAmountTextField.getText());
    		if(ReceivingAccountChoiceBox.getValue()!=null&&SendingAccountChoiceBox.getValue()!=null) {
    			receivingAccount=ReceivingAccountChoiceBox.getValue().accountNumber;
    			sendingAccount=SendingAccountChoiceBox.getValue().accountNumber;
    			amount=TransferAmountTextField.getText();
    			if(!receivingAccount.equals(sendingAccount))
    			transfer(TransferType.TRANSFER, sendingAccount,
    					receivingAccount, amount);
    			else
    				ErrorTextArea.setText("Same account in both fields");
    				
    		}
    		else
    			ErrorTextArea.setText("Empty field");
    	}
    	catch(Exception e) {ErrorTextArea.setText("Invalid amount");}
    }
    
    //*********************************************************************
    
    //********************************************************************
    //GUI components for transfer to another account holder
    
    @FXML
    private TextField ReceivingAccountTextField;
    
    @FXML
    void TransferToAnotherAccountHolderSubmitButtonPressed(ActionEvent event) throws Exception {
    	try {
    		ta=ErrorTextArea;
    		ae=event;
    		Double.valueOf(TransferAmountTextField.getText());
    		if(SendingAccountChoiceBox.getValue()!=null&&ReceivingAccountTextField.getText().equals("")!=true) {
    			receivingAccount=ReceivingAccountTextField.getText();
    			sendingAccount=SendingAccountChoiceBox.getValue().accountNumber;
    			amount=TransferAmountTextField.getText();
    			System.out.println(receivingAccount);
    			System.out.println(sendingAccount);
    			if(!receivingAccount.equals(sendingAccount))
        			transfer(TransferType.TRANSFER, sendingAccount,
        					receivingAccount, amount);
        			else
        				ErrorTextArea.setText("Same account in both fields");
    		}
    		else
    			ErrorTextArea.setText("Empty field");
    	}
    	catch(Exception e) {ErrorTextArea.setText("Invalid amount");}
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
    	try {
    		ta=ErrorTextArea;
    		ae=event;
    		Double.valueOf(TransferAmountTextField.getText());
    		if(ReceivingAccountChoiceBox.getValue()!=null) {
    			receivingAccount=ReceivingAccountChoiceBox.getValue().accountNumber;
    			sendingAccount=null;
    			amount=TransferAmountTextField.getText();
    			transfer(TransferType.DEPOSIT, null,receivingAccount, amount);
    		}
    		else
    			ErrorTextArea.setText("Empty field");
    	}
    	catch(Exception e) {ErrorTextArea.setText("Invalid amount");}
    }
    
    //***************************************************************
    
    //**************************************************************
    //GUI components for withdraw screen
    
    @FXML
    void WithdrawSubmitButtonPressed(ActionEvent event) throws Exception{
    	try {
    		ta=ErrorTextArea;
    		ae=event;
    		Double.valueOf(TransferAmountTextField.getText());
    		if(SendingAccountChoiceBox.getValue()!=null) {
    			receivingAccount=null;
    			sendingAccount=SendingAccountChoiceBox.getValue().accountNumber;
    			amount=TransferAmountTextField.getText();
    			transfer(TransferType.WITHDRAW, sendingAccount,null, amount);
    		}
    		else
    			ErrorTextArea.setText("Empty field");
    	}
    	catch(Exception e) {ErrorTextArea.setText("Invalid amount");}
    }
    
    //*************************************************************
    
    //*************************************************************
    //GUI components for search results screen
    
    @FXML
    private ListView<AccountHolderInfo> CardNumberListView;

    @FXML
    private TextField ResultEmailTextField;

    @FXML
    private TextField ResultNameTextField;

    @FXML
    private TextField ResultSearchValueTextField;

    @FXML
    private TextField ResultSinTextField;

    @FXML
    private TextField ResultTypeTextField;

    @FXML
    void SelectAccountHolderButtonPressed(ActionEvent event) throws Exception{
    	if(CardNumberListView.getSelectionModel().getSelectedItem()!=null) {
    	cardNumber=CardNumberListView.getSelectionModel().getSelectedItem().cardNumber;
    		if(cardNumber!=null)
    			switchToEditProfileScreen(event);
    	}
    }
  
    //*************************************************************
    
    //********************************************************
    //GUI components for account viewer
    
    @FXML
    private ListView<AccountInfo> AccountNumberListView;

    @FXML
    private TextField AccountsBalanceTextField;

    @FXML
    private TextField AccountsCardNumberTextField;

    @FXML
    private TextField AccountsTypeTextField;

    @FXML
    void ViewAccountHistoryButtonPressed(ActionEvent event) throws Exception {
    	ae=event;
    	if(AccountNumberListView.getSelectionModel().getSelectedItem()!=null) {
    		selectedAccount=AccountNumberListView.getSelectionModel().getSelectedItem();
    		getTransactions(AccountNumberListView.getSelectionModel().getSelectedItem().accountNumber);
    	}
    }
    
    //**********************************************************
    
    //***********************************************************
    //GUI components for account history screen
    
    @FXML
    private ListView<TransactionInfo> AccountHistoryListView;

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
    
    @FXML
    private TextField TransferHistoryTypeTextField;
    
    //**********************************************************
    
    //********************************************************
    //GUI components for edit profile screen
    
    @FXML
    private TextField EditCardNumberTextField;
    
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
    void CreateAccountButtonPressed(ActionEvent event) throws Exception {
    	ae=event;
    	accountType=AccountTypeChoiceBox.getValue();
    	if(accountType==null)
    		ErrorTextArea.setText("No type selected");
    	else if(accountType.equals("Chequing Account")) {
    		createAccount(AccountType.CHEQUING, cardNumber,empID);
    	}
    	else if(accountType.equals("Savings Account")) {
    		createAccount(AccountType.SAVINGS, cardNumber,empID);
    	}
    	else if(accountType.equals("Mortgage Account")) {
    		switchToMortgageAccountScreen(event);
    	}
    	else if(accountType.equals("Line-Of-Credit")) {
    		switchToLOCScreen(event);
    	}
    }
    
    //*********************************************************
    
    //*******************************************************
    //GUI components for new account confirmation screen
    
    @FXML
    private TextField AccountNumberConfirmationTextField;

    @FXML
    private TextField AccountTypeConfirmationTextField;
    
    //*******************************************************
    
    //*******************************************************
    //GUI components for delete account
    
    @FXML
    private ChoiceBox<AccountInfo> DeleteAccountChoiceBox;
    
    @FXML
    private CheckBox DeleteAccountCheckBox;
    
    @FXML
    void DeleteAccountButtonPressed(ActionEvent event) throws Exception{
    	ta=ErrorTextArea;
    	ae=event;
    	if(DeleteAccountCheckBox.isSelected()&&DeleteAccountChoiceBox.getValue()!=null) {
    		deletedAccount=DeleteAccountChoiceBox.getValue();
    		deleteAccount(DeleteAccountChoiceBox.getValue().accountNumber,empID);
    	}
    	else if(!DeleteAccountCheckBox.isSelected())
    		ErrorTextArea.setText("Check box not checked");
    	else if(DeleteAccountChoiceBox.getValue()==null)
    		ErrorTextArea.setText("Account not selected");
    }
    
    //********************************************************
    
    //********************************************************
    //GUI components for delete account confirmation
    
    @FXML
    private TextField AccountDeletedTextField;
   
    //********************************************************
    
    //**************************************************
    //GUI components for delete profile
    
    @FXML
    private CheckBox DeleteProfileCheckBox;
    
    @FXML
    void DeleteProfileSubmitButtonPressed(ActionEvent event) throws Exception{
    	ae=event;
    	ta=ErrorTextArea;
    	if(DeleteProfileCheckBox.isSelected()) { 
    		deleteAccountHolder(cardNumber);
    		//TODO send delete(profile)
    	}
    }
    
    //****************************************************
    
    //**************************************************
    //GUI comp for mortgage account

    @FXML
    private TextField InterestTextField;

    @FXML
    private TextField MortgageValueTextField;
    
    @FXML
    private TextField MonthsTextField;

    @FXML
    void CreateMortgageAccountButtonPressed(ActionEvent event) {
    	ae=event;
    	ta=ErrorTextArea;
    	try {
    		Integer.valueOf(MonthsTextField.getText());
    		Double.valueOf(MortgageValueTextField.getText());
    		Double.valueOf(InterestTextField.getText());
    		setupMortgageAccount(cardNumber, MonthsTextField.getText(), InterestTextField.getText(),
        			MortgageValueTextField.getText(), empID);
    	}
    	catch(Exception e) {ErrorTextArea.setText("Invalid field(s)");}
    }
    
    //*************************************************
    
    //************************************************
    //GUI comp for LOC
    
    @FXML
    private TextField CreditLimitTextField;

    @FXML
    private TextField LOCInterestTextField;

    @FXML
    void CreateLOCButtonPressed(ActionEvent event) {
    	//TODO make loc
    	ae=event;
    	ta=ErrorTextArea;
    	try {
    		Double.valueOf(CreditLimitTextField.getText());
    		Double.valueOf(InterestTextField.getText());
    		setupLineOfCreditAccount(cardNumber, CreditLimitTextField.getText(), InterestTextField.getText(), empID);
    	}
    	catch(Exception e) {ErrorTextArea.setText("Invalid field(s)");}
    }
    
    //************************************************
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //**************************************************
    
    private String SearchParameterList[]= {"Card Number","SIN","Email"};
    private String AccountTypeList[]= {"Chequing Account","Savings Account","Mortgage Account","Line-Of-Credit"};
    
    //Initializer of GUI elements
    public void initialize() {
    	
    	//*************************************************
    	//Fill saved values for new account holder creation
    	
    	if(FirstNameTextField!=null && firstName!=null)
    		FirstNameTextField.setText(firstName);
    	if(LastNameTextField!=null && lastName!=null)
    		LastNameTextField.setText(lastName);
    	if(sinTextField!=null && sin!=null)
    		sinTextField.setText(sin);
    	if(emailTextField!=null && email!=null)
    		emailTextField.setText(email);
    	if(dayTextField!=null && dob!=null)
    		dayTextField.setText(dob.substring(3, 5)); //day
    	if(monthTextField!=null && dob!=null)
    		monthTextField.setText(dob.substring(0, 2)); //month
    	if(yearTextField!=null && dob!=null)
    		yearTextField.setText(dob.substring(6, 10)); //year
    	if(NewPinTextField!=null && pin!=null)
    		NewPinTextField.setText(pin);
    	
    	//track number of addresses added
    	if(NewAccountHolderAddressTotalTextField!=null)
    		NewAccountHolderAddressTotalTextField.setText(Integer.toString(streetNameList.size()));
    	
    	//fill values on confirmation page
    	if(ConfirmationFirstNameTextField!=null&&firstName!=null)
    		ConfirmationFirstNameTextField.setText(firstName);
    	if(ConfirmationLastNameTextField!=null&&lastName!=null)
    		ConfirmationLastNameTextField.setText(lastName);
    	if(ConfirmationPinTextField!=null&&pin!=null)
    		ConfirmationPinTextField.setText(pin);
    	if(ConfirmationCardNumberTextField!=null&&cardNumber!=null)
    		ConfirmationCardNumberTextField.setText(cardNumber);
    	
    	//************************************************
    	//set offline to false if on connect screen
    	
    	if(ipAddTextField!=null) //connect screen
    		offline=false;
    	
    	//**************************************************
    	//fill search parameter choice box
    	
    	if(TellerSearchParameterChoiceBox!=null) //teller search screen
    		TellerSearchParameterChoiceBox.getItems().addAll(SearchParameterList);
    	
    	//*************************************************
    	//fill account type choice box
    	
    	if(AccountTypeChoiceBox!=null)//new account
    		AccountTypeChoiceBox.getItems().addAll(AccountTypeList);
    	
    	//*************************************************
    	//fill choice box with accounts (for transfers)
    	
    	//*************************************************
    	//initializing GUI for search results page
    	
    	if(CardNumberListView!=null) {
    		CardNumberListView.getItems().addAll(searchList);
    		ahilv=CardNumberListView;
    		CardNumberListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AccountHolderInfo>() {
    			@Override
    			public void changed(ObservableValue<? extends AccountHolderInfo> ov, AccountHolderInfo oldValue, AccountHolderInfo newValue) {
    				    ResultEmailTextField.setText(newValue.email);
    				    ResultNameTextField.setText(newValue.personName);
    				    ResultSinTextField.setText(newValue.sin);
    				    //TODO add other fields
    				    //We need first name, last name and sin to be passed from server
    			}});
    	}
    	if(ResultTypeTextField!=null)
    		ResultTypeTextField.setText(searchParameter);
    	if(ResultSearchValueTextField!=null)
    		ResultSearchValueTextField.setText(searchValue);
    	
    	//*************************************************
    	//Initialize edit menu and screens
    	
    	if(EditCardNumberTextField!=null)
    		EditCardNumberTextField.setText(cardNumber);
    	
    	//TODO get new account number
    	//if(AccountNumberConfirmationTextField!=null)
    		//AccountNumberConfirmationTextField.setText();
    	
    	if(AccountTypeConfirmationTextField!=null)
    		AccountTypeConfirmationTextField.setText(accountType);
    	
    	if(DeleteAccountChoiceBox!=null) {
    		DeleteAccountChoiceBox.getItems().clear();
    		DeleteAccountChoiceBox.setItems(accountList);
    	}
    	
    	//***************************************************
    	
    	if(AccountNumberListView!=null) {
    		AccountNumberListView.getItems().addAll(accountList);
    		AccountNumberListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AccountInfo>() {
    			@Override
    			public void changed(ObservableValue<? extends AccountInfo> ov, AccountInfo oldValue, AccountInfo newValue) {
    				AccountsBalanceTextField.setText(newValue.balance);
    				AccountsTypeTextField.setText(newValue.getAccountTypeString());
    			}});
    	}
    		
    	if(SendingAccountChoiceBox!=null) {
    		SendingAccountChoiceBox.getItems().clear();
    		SendingAccountChoiceBox.setItems(sendingAccountList);
    		SendingAccountChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AccountInfo>() {
    			@Override
    			public void changed(ObservableValue<? extends AccountInfo> ov, AccountInfo oldValue, AccountInfo newValue) {
    				if(SenderBalanceTextField!=null&&newValue!=null)
    					SenderBalanceTextField.setText(newValue.balance);
    			}});
    	}
    	if(ReceivingAccountChoiceBox!=null) {
    		ReceivingAccountChoiceBox.getItems().clear();
    		ReceivingAccountChoiceBox.setItems(receivingAccountList);
    		ReceivingAccountChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AccountInfo>() {
    			@Override
    			public void changed(ObservableValue<? extends AccountInfo> ov, AccountInfo oldValue, AccountInfo newValue) {
    				if(ReceiverBalanceTextField!=null&&newValue!=null)
    					ReceiverBalanceTextField.setText(newValue.balance);
    			}});
    	}
    	
    	if(AccountDeletedTextField!=null)
    		AccountDeletedTextField.setText(deletedAccount.accountNumber);
    	
    	if(SendingAccountConfirmationTextField!=null)
    		SendingAccountConfirmationTextField.setText(sendingAccount);
    	
    	if(ReceivingAccountConfirmationTextField!=null)
    		ReceivingAccountConfirmationTextField.setText(receivingAccount);
    	
    	if(TransferAmountConfirmationTextField!=null)
    		TransferAmountConfirmationTextField.setText(amount);
    	
    	if(AccountNumberConfirmationTextField!=null)
    		AccountNumberConfirmationTextField.setText(newAccount);
    	
    	if(AccountHistoryListView!=null) {
    		AccountHistoryListView.getItems().addAll(historyList);
    		AccountHistoryListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TransactionInfo>() {
    			@Override
    			public void changed(ObservableValue<? extends TransactionInfo> ov, TransactionInfo oldValue, TransactionInfo newValue) {
    				if(TransferHistoryDateTextField!=null)
    					TransferHistoryDateTextField.setText(newValue.date);
    				if(TransferHistorySenderTextField!=null)
    					TransferHistorySenderTextField.setText(newValue.sender);
    				if(TransferHistoryAmountTextField!=null)
    					TransferHistoryAmountTextField.setText(newValue.amount);
    				if(TransferHistoryReceiverTextField!=null)
    					TransferHistoryReceiverTextField.setText(newValue.recipient);
    				if(TransferHistoryTypeTextField!=null)
    					TransferHistoryTypeTextField.setText(newValue.transactionType);
    			}});
    	}
    	
    	if(AccountHistoryNumberTextField!=null)
    		AccountHistoryNumberTextField.setText(selectedAccount.accountNumber);
    }
    
    //Start function
	public static void main(String[] args) throws Exception{
		//initialize();
		launch(args);
	}
	
	//*****************************************************************************
	
	
	
	
	
	
	
	
	
	
	
	
	
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
		if (isSuccessful)
		{
			//login success next screen
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
	 *

	/**
	 * handle find result of the account holder
	 */

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
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						cardNumber=ahi.cardNumber;
						switchToNewAccountHolderConfirmationScreen(ae);
					}
					catch(Exception e) {e.printStackTrace();}
				}
			});
		}
		else
		{
			//could not create new account
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						ta.setText("Account creation failed");
					}
					catch(Exception e) {e.printStackTrace();}
				}
			});
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
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						switchToDeleteProfileConfirmationScreen(ae);
					}
					catch(Exception e) {e.printStackTrace();}
				}
			});
			
		}
		else
		{
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						ta.setText("Deletion Failed");
					}
					catch(Exception e) {e.printStackTrace();}
				}
			});
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
	public void createAccount(AccountType accountType, String cardNumber, String tellerID)
	{
		try
		{
			bc.createAccount(accountType, cardNumber, tellerID);
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
	public void handleAccountCreation(String accountNumber)
	{
		// TODO add handle code
		
		if (accountNumber != null)
		{
			newAccount=accountNumber;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						switchToCreateAccountConfirmationScreen(ae);
					}
					catch(Exception e) {e.printStackTrace();}
				}
			});
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
	public void deleteAccount(String accountNumber, String tellerID)
	{
		try
		{
			bc.deleteAccount(accountNumber,tellerID);
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
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						switchToDeleteAccountConfirmationScreen(ae);
					}
					catch(Exception e) {e.printStackTrace();}
				}
			});
		}
		else
		{
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						ta.setText("Deletion failed");
					}
					catch(Exception e) {e.printStackTrace();}
				}
			});
			
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
	public void getAccounts(String cardNumber)
	{
		try
		{
			bc.getAccounts(cardNumber);
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
	public void handleAccountInformation(ArrayList<AccountInfo> accountInfo)
	{
		//TODO: add handle code
		
		
		if (!accountInfo.isEmpty())
		{
			//add to account list
			for(int i=0; i<accountInfo.size();i++) {
				accountList.add(accountInfo.get(i));
				receivingAccountList.add(accountInfo.get(i));
				if(accountInfo.get(i).accountType!=AccountType.LINE_OF_CREDIT && accountInfo.get(i).accountType!=AccountType.MORTGAGE)
					sendingAccountList.add(accountInfo.get(i));
			}
		}
		else
		{
			//no info in account info
		}
	}
	
	//////////////
	// TRANSFER //
	//////////////
	
	/**
	 * handles the transfer of funds from one account to another (or to an account if depositing or withdrawing)
	 * @param accountType the account to transfer to or from (chequing, savings, etc.)
	 * @param transferType deposit, withdraw, or transfer
	 * @param recipientEmail the email address of the recipient (if doing a transfer)
	 * @param amount the amount to send
	 */
	public void transfer(TransferType transferType, String sendingAccountNum, String recipientAccountNum, String amount)
	{
		try
		{
			bc.transfer(transferType, sendingAccountNum, recipientAccountNum, amount);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * handles the transfer result
	 * @param isSuccessful true if the transfer was successful, false if not
	 * @param newBalance the new balance after the transfer
	 */
	public void handleTransferResult(boolean isSuccessful, String newBalance)
	{	
		if (isSuccessful)
		{
			//show new balance
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						switchToTransferConfirmationScreen(ae);
					}
					catch(Exception e) {e.printStackTrace();}
				}
			});
		}
		else
		{
			//error message
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						ta.setText("Transfer failed");
					}
					catch(Exception e) {e.printStackTrace();}
				}
			});
		}
	}

	@Override
	public void setupMortgageAccount(String accountNumber, String mortgageLength, String interestRate,
			String principleAmount, String TellerID){
		try
		{
			bc.setupMortgageAccount(accountNumber, mortgageLength, interestRate, principleAmount, TellerID);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void handleMortgageAccountSetupResult(String accNumber) {
		// TODO Auto-generated method stub
		if(accNumber != null) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						//TODO get account number
						newAccount=accNumber;
						switchToCreateAccountConfirmationScreen(ae);
					}
					catch(Exception e) {e.printStackTrace();}
				}
			});
		}
		else {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						ta.setText("Account creation failed");
					}
					catch(Exception e) {e.printStackTrace();}
				}
			});
		}
		
	}

	@Override
	public void setupLineOfCreditAccount(String accountNumber, String creditLimit, String interestRate, String TellerID) {
		// TODO Auto-generated method stub
		try
		{
			bc.setupLineOfCreditAccount(accountNumber, creditLimit, interestRate, TellerID);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void handleLineOfCreditSetupResult(String accNumber) {
		// TODO Auto-generated method stub
		if(accNumber != null) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						//TODO get account number
						newAccount=accNumber;
						switchToCreateAccountConfirmationScreen(ae);
					}
					catch(Exception e) {e.printStackTrace();}
				}
			});
		}
		else {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						ta.setText("Account creation failed");
					}
					catch(Exception e) {e.printStackTrace();}
				}
			});
		}
		
	}

	@Override
	public void manageBill(BillAction billAction, String locAccountNumber, String amount, String receiver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleBillManagementResult(boolean isSuccessful) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getTransactions(String accountNumber) {
		try {
			bc.getTransactions(accountNumber);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void handleTransactions(ArrayList<TransactionInfo> transactions) {
		if(transactions!=null) {
			historyList.addAll(transactions);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					try {
						switchToAccountHistoryScreen(ae);
					}
					catch(Exception e) {e.printStackTrace();}
				}
			});
		}
		else {
			
		}
		
	}

	@Override
	public void getAccountRecords() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleAccountRecords(ArrayList<RecordInfo> accountRecords) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getCustomerRecords() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleCustomerRecords(ArrayList<RecordInfo> customerRecords) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendFindAccountHolderRequest(InputType inputType, String parameter) {
		// TODO Auto-generated method stub
		try
		{
			bc.findAccountHolder(inputType,parameter);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	@Override
	public void handleFindAccountHolderResult(AccountHolderInfo ahi) {
		// TODO add handle code here
		
			if (ahi.getHasInfo())
			{
				//show account holder information
				searchList.add(ahi);
				if(ahilv!=null)
					ahilv.getItems().add(ahi);
			}
			else
			{
				//show not found message
			}
		
	}


}
