package src.program.server;

import java.io.IOException;
import java.util.ArrayList;

import src.ocsf.server.AbstractServer;
import src.ocsf.server.ConnectionToClient;
import src.protocol.*;
import src.program.structs.*;

public class BankingServer extends AbstractServer
{
	IBankController bc;
	
	public BankingServer(int port, IBankController b)
	{
		super(port);
		this.bc = b;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client)
	{
		ClientProtocol cp = (ClientProtocol)msg;
		
		switch(cp.GetServerAction())
		{
			case LOGIN_ACCOUNTHOLDER:
				HandleAccountHolderLogin(cp, client);
				break;
			case LOGIN_TELLER:
				handleTellerLogin(cp, client);
				break;
			case TEST:
				ProcessTestMessage(cp, client);
				break;
			case FIND_ACCOUNTHOLDER_BY_EMAIL:
				handleFindAccountHolderByEmailRequest(cp, client);
				break;
			case CREATE_ACCOUNTHOLDER:
				handleAccountHolderCreationRequest(cp, client);
				break;
			case DELETE_ACCOUNTHOLDER:
				handleAccountHolderDeletionRequest(cp, client);
				break;
			case CREATE_PERSON:
				handlePersonCreationRequest(cp, client);
				break;
			case DELETE_PERSON:
				handlePersonDeletionRequest(cp, client);
				break;
			case ADD_ADDRESS_TO_PERSON:
				handleAddressAdditionRequest(cp, client);
				break;
			case REMOVE_ADDRESS_FROM_PERSON:
				handleAddressRemovalRequest(cp, client);
				break;
			case ADD_ACCOUNTHOLDER_ROLE_TO_PERSON:
				handleAccountHolderToPersonRequest(cp, client);
				break;
			case CREATE_ACCOUNT:
				handleAccountCreationRequest(cp, client);
				break;
			case DELETE_ACCOUNT:
				handleAccountDeletionRequest(cp, client);
				break;
			case GET_ACCOUNTS:
				handleAccountGetRequest(cp, client);
				break;
			case TRANSFER:
				handleTransferRequest(cp, client);
				break;
			case GET_ACCOUNT_TRANSACTIONS:
				handleGetTransactionHistoryRequest(cp, client);
				break;
			case GET_ACCOUNT_RECORDS:
				handleGetAccountRecordsRequest(cp, client);
				break;
			case GET_CUSTOMER_RECORDS:
				handleGetCustomerRecordsRequest(cp, client);
				break;
			default:
				break;
		}
	}

	@Override
	protected void clientConnected(ConnectionToClient client)
	{
		System.out.println("Client " + client.getInetAddress() + " connected to the system");
	}
	
	////////////////////
	// TEST MESSAGES  //
	////////////////////
	
	/**
	 * sends a test message back to the client
	 * @param client
	 * @param message
	 */
	public void SendTestMessageToClient(ConnectionToClient client, String message)
	{
		ServerProtocol sp = new ServerProtocol(MessageStatus.SUCCESS, Datatype.BASIC_MESSAGE);
		
		try
		{
			sp.AddData(message);
		} 
		catch (ParameterException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			client.sendToClient(sp);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//extracts the test message from the client protocol and sends it to the bank
	//controller for further processing
	private void ProcessTestMessage(ClientProtocol cp, ConnectionToClient client)
	{
		//get response message from the banking server
		String newMessage = bc.handleTestMessage(cp.GetParameters().get(0));
		
		
		ServerProtocol sp = new ServerProtocol(MessageStatus.SUCCESS, Datatype.BASIC_MESSAGE);
		
		try
		{
			sp.AddData(newMessage);
		}
		catch (ParameterException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try
		{
			client.sendToClient(sp);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * Handles an account holder login request
	 * @param cp
	 * @param client
	 */
	private void HandleAccountHolderLogin(ClientProtocol cp, ConnectionToClient client)
	{
		if (bc.authenticateAccountHolderLogin(cp.GetParameters().get(0),cp.GetParameters().get(1)))
		{
			ServerProtocol sp = new ServerProtocol(MessageStatus.SUCCESS, Datatype.LOGIN_RESULT_ACCOUNTHOLDER);

			//sets info in the connection to client to uniquely identify them
			client.setInfo("LoginType", LoginType.ACCOUNTHOLDER);
			client.setInfo("AccNum", cp.GetParameters().get(0));
			client.setInfo("AccPin", cp.GetParameters().get(1));
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		else
		{
			ServerProtocol sp = new ServerProtocol(MessageStatus.FAIL, Datatype.LOGIN_RESULT_ACCOUNTHOLDER);

			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} //END OF LOGIN ACCOUNT HOLDER CASE
	}
	
	/**
	 * Handles an account holder login request
	 * @param cp
	 * @param client
	 */
	private void handleTellerLogin(ClientProtocol cp, ConnectionToClient client)
	{
		if (bc.authenticateTellerLogin(cp.GetParameters().get(0),cp.GetParameters().get(1)))
		{
			ServerProtocol sp = new ServerProtocol(MessageStatus.SUCCESS, Datatype.LOGIN_RESULT_TELLER);

			//sets info in the connection to client to uniquely identify them
			client.setInfo("LoginType", LoginType.TELLER);
			client.setInfo("AccNum", cp.GetParameters().get(0));
			client.setInfo("AccPin", cp.GetParameters().get(1));
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		else
		{
			ServerProtocol sp = new ServerProtocol(MessageStatus.FAIL, Datatype.LOGIN_RESULT_TELLER);

			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		} //END OF LOGIN TELLER CASE
	}
	
	/**
	 * handles a find account holder by email request
	 * @param cp
	 * @param client
	 */
	private void handleFindAccountHolderByEmailRequest(ClientProtocol cp, ConnectionToClient client)
	{
		//sends a find account holder request to the bank controller
		AccountHolderInfo info = bc.findAccountHolder(cp.GetParameters().get(0));
		
		MessageStatus status = info.getHasInfo() ? MessageStatus.SUCCESS : MessageStatus.FAIL;
		ServerProtocol sp = new ServerProtocol(status, Datatype.ACCOUNT_HOLDER_FIND_RESULT);
		
		try
		{
			//adds the account holder information to the server protocol
			sp.AddData(info.email, info.cardNumber, info.pin);
			
			try
			{
				//sends that data to the client
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		catch (ParameterException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * handles an account creation request and dispatches it to the rest of the server
	 * @param cp
	 * @param client
	 */
	private void handleAccountHolderCreationRequest(ClientProtocol cp, ConnectionToClient client)
	{
		//only tellers can create account holders
		if (client.getInfo("LoginType") == LoginType.TELLER)
		{
			//gets the account holder info of the created account
			AccountHolderInfo info = bc.createAccountHolder(cp.GetParameters().get(0), cp.GetParameters().get(1), cp.GetParameters().get(2), (String)client.getInfo("AccNum"));	
			
			//sets the status based on the creation result of the account holder
			MessageStatus status = info.getHasInfo() ? MessageStatus.SUCCESS : MessageStatus.FAIL;
			
			ServerProtocol sp = new ServerProtocol(status, Datatype.ACCOUNT_HOLDER_CREATION_RESULT);
			
			try
			{
				sp.AddData(info.email, info.cardNumber, info.pin);
			}
			catch (ParameterException e1)
			{
				e1.printStackTrace();
			}
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}	
		}
		else
		{
			try
			{
				client.sendToClient(new ServerProtocol(MessageStatus.FAIL, Datatype.ACCOUNT_HOLDER_CREATION_RESULT));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * handles an account holder deletion request and dispatches it to the rest of the server
	 * @param cp
	 * @param client
	 */
	private void handleAccountHolderDeletionRequest(ClientProtocol cp, ConnectionToClient client)
	{
		//only tellers can delete account holders
		if (client.getInfo("LoginType") == LoginType.TELLER)
		{
			//success if the account creation was successful, fail if not
			MessageStatus status = bc.deleteAccountHolder(cp.GetParameters().get(0), (String)client.getInfo("AccNum")) ? MessageStatus.SUCCESS : MessageStatus.FAIL;	
			ServerProtocol sp = new ServerProtocol(status, Datatype.ACCOUNT_HOLDER_DELETION_RESULT);
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			//send a fail request as the client is not a teller
			ServerProtocol sp = new ServerProtocol(MessageStatus.FAIL, Datatype.ACCOUNT_HOLDER_DELETION_RESULT);
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * handles a person creation request and dispatches it to the rest of the server
	 * @param cp
	 * @param client
	 */
	private void handlePersonCreationRequest(ClientProtocol cp, ConnectionToClient client)
	{
		//only tellers can create a person
		if (client.getInfo("LoginType") == LoginType.TELLER)
		{
			//sets the status based on the creation result of the account holder
			MessageStatus status = bc.createPerson(cp.GetParameters().get(0), cp.GetParameters().get(1), cp.GetParameters().get(2), cp.GetParameters().get(3)) ? MessageStatus.SUCCESS : MessageStatus.FAIL;		
			ServerProtocol sp = new ServerProtocol(status, Datatype.PERSON_CREATION_RESULT);
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}	
		}
		else
		{
			try
			{
				client.sendToClient(new ServerProtocol(MessageStatus.FAIL, Datatype.PERSON_CREATION_RESULT));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * handles a person deletion request and dispatches it to the rest of the server
	 * @param cp
	 * @param client
	 */
	private void handlePersonDeletionRequest(ClientProtocol cp, ConnectionToClient client)
	{
		//only tellers can delete a person
		if (client.getInfo("LoginType") == LoginType.TELLER)
		{
			//success if the account creation was successful, fail if not
			MessageStatus status = bc.deletePerson(cp.GetParameters().get(0)) ? MessageStatus.SUCCESS : MessageStatus.FAIL;	
			ServerProtocol sp = new ServerProtocol(status, Datatype.PERSON_DELETION_RESULT);
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			//send a fail request as the client is not a teller
			ServerProtocol sp = new ServerProtocol(MessageStatus.FAIL, Datatype.PERSON_DELETION_RESULT);
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * dispatches adding an address from a person to the server
	 * @param cp
	 * @param client
	 */
	private void handleAddressAdditionRequest(ClientProtocol cp, ConnectionToClient client)
	{
		//only tellers can add addresses
		if (client.getInfo("LoginType") == LoginType.TELLER)
		{
			//success if the the address was added, fail if not
			MessageStatus status = bc.addAddress(cp.GetParameters().get(0), cp.GetParameters().get(1), cp.GetParameters().get(2), cp.GetParameters().get(3), cp.GetParameters().get(4), cp.GetParameters().get(5)) ? MessageStatus.SUCCESS : MessageStatus.FAIL;	
			ServerProtocol sp = new ServerProtocol(status, Datatype.ADDRESS_ADDITION_RESULT);
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			//send a fail request as the client is not a teller
			ServerProtocol sp = new ServerProtocol(MessageStatus.FAIL, Datatype.ADDRESS_ADDITION_RESULT);
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * dispatches removing an address from a person to the server 
	 * @param cp
	 * @param client
	 */
	private void handleAddressRemovalRequest(ClientProtocol cp, ConnectionToClient client)
	{
		//only tellers can remove addresses from a person
		if (client.getInfo("LoginType") == LoginType.TELLER)
		{
			//success if the the address was removed, fail if not
			MessageStatus status = bc.removeAddress(cp.GetParameters().get(0), cp.GetParameters().get(1)) ? MessageStatus.SUCCESS : MessageStatus.FAIL;	
			ServerProtocol sp = new ServerProtocol(status, Datatype.ADDRESS_REMOVAL_RESULT);
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			//send a fail request as the client is not a teller
			ServerProtocol sp = new ServerProtocol(MessageStatus.FAIL, Datatype.ADDRESS_REMOVAL_RESULT);
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * dispatches adding an account holder role to a person
	 * @param cp
	 * @param client
	 */
	private void handleAccountHolderToPersonRequest(ClientProtocol cp, ConnectionToClient client)
	{
		//only tellers can associate an account holder with a person
		if (client.getInfo("LoginType") == LoginType.TELLER)
		{
			//success if the the association was added, false if not
			MessageStatus status = bc.addAccountHolderToPerson(cp.GetParameters().get(0), cp.GetParameters().get(1)) ? MessageStatus.SUCCESS : MessageStatus.FAIL;	
			ServerProtocol sp = new ServerProtocol(status, Datatype.ACCOUNTHOLDER_ROLE_ASSOCIATION_RESULT);
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			//send a fail request as the client is not a teller
			ServerProtocol sp = new ServerProtocol(MessageStatus.FAIL, Datatype.ACCOUNTHOLDER_ROLE_ASSOCIATION_RESULT);
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Requests the server to create an account
	 * @param sp
	 */
	private void handleAccountCreationRequest(ClientProtocol cp, ConnectionToClient client)
	{
		//only tellers can add accounts to an account holder
		if (client.getInfo("LoginType") == LoginType.TELLER)
		{
			//success if the the account was created, false if not
			MessageStatus status = bc.createAccount(AccountType.valueOf(cp.GetParameters().get(0)), cp.GetParameters().get(1)) ? MessageStatus.SUCCESS : MessageStatus.FAIL;	
			ServerProtocol sp = new ServerProtocol(status, Datatype.ACCOUNT_CREATION_RESULT);
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			//send a fail request as the client is not a teller
			ServerProtocol sp = new ServerProtocol(MessageStatus.FAIL, Datatype.ACCOUNT_CREATION_RESULT);
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Requests the server to delete an account
	 * @param sp
	 */
	private void handleAccountDeletionRequest(ClientProtocol cp, ConnectionToClient client)
	{
		//only tellers can add accounts to an account holder
		if (client.getInfo("LoginType") == LoginType.TELLER)
		{
			//success if the the account was deleted, false if not
			MessageStatus status = bc.deleteAccount(AccountType.valueOf(cp.GetParameters().get(0)), cp.GetParameters().get(1)) ? MessageStatus.SUCCESS : MessageStatus.FAIL;	
			ServerProtocol sp = new ServerProtocol(status, Datatype.ACCOUNT_DELETION_RESULT);
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			//send a fail request as the client is not a teller
			ServerProtocol sp = new ServerProtocol(MessageStatus.FAIL, Datatype.ACCOUNT_DELETION_RESULT);
			
			try
			{
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Returns information about a specific account from a specific account holder
	 * @param accountType type of account (chequing, savings, etc.)
	 * @param cardNumber the account holder's card number
	 * @return
	 */
	private void handleAccountGetRequest(ClientProtocol cp, ConnectionToClient client)
	{
		//gets all the transactions from the banking server
		ArrayList<AccountInfo> info = bc.getAccounts(cp.GetParameters().get(0));
			
		ServerProtocol sp = new ServerProtocol(MessageStatus.SUCCESS, Datatype.ACCOUNT);
			
		//adds each parameter of each account one by one
		for (int i = 0; i < info.size(); i++)
		{
			try
			{
				sp.AddData(info.get(i).accountType.toString(), info.get(i).balance, info.get(i).accountNumber);
			}
			catch (ParameterException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try
		{
			//sends the info to the client
			client.sendToClient(sp);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns information about a specific account from a specific account holder
	 * @param accountType type of account (chequing, savings, etc.)
	 * @param cardNumber the account holder's card number
	 * @return
	 */
	private void handleTransferRequest(ClientProtocol cp, ConnectionToClient client)
	{
		ServerProtocol sp;
		
		//only account holders can transfer funds from one account to another
		if (client.getInfo("LoginType") == LoginType.ACCOUNTHOLDER)
		{

			//gets the new balance from the bank controller
			String newBalance = bc.transfer(TransferType.valueOf(cp.GetParameters().get(0)), cp.GetParameters().get(1), cp.GetParameters().get(2), cp.GetParameters().get(3));	
			
			//sends success if the new balance is different from the old balance
			if (!newBalance.equals(null))
			{
				sp = new ServerProtocol(MessageStatus.SUCCESS, Datatype.TRANSFER_BALANCE);
				
				try
				{
					//adds data to the server protocol
					sp.AddData(newBalance);
				}
				catch (ParameterException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				//fail message with no data
				sp = new ServerProtocol(MessageStatus.FAIL, Datatype.TRANSFER_BALANCE);
			}
		}
		else
		{
			//fail message with no data if the account type is a teller
			sp = new ServerProtocol(MessageStatus.FAIL, Datatype.TRANSFER_BALANCE);
		}
		
		try
		{
			//sends the info to the client
			client.sendToClient(sp);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * sends a message to the server to retrieve the transaction history of an account
	 * @param cp
	 * @param client
	 */
	private void handleGetTransactionHistoryRequest(ClientProtocol cp, ConnectionToClient client)
	{
		//only account holders can get the transactions from the server
		if (client.getInfo("LoginType") == LoginType.ACCOUNTHOLDER)
		{
			//gets all the transactions from the banking server
			ArrayList<TransactionInfo> info = bc.getTransactionHistory((String)client.getInfo("AccNum"), AccountType.valueOf(cp.GetParameters().get(0)));
			
			ServerProtocol sp = new ServerProtocol(MessageStatus.SUCCESS, Datatype.TRANSACTION);
			
			//adds each parameter of each transaction one by one
			for (int i = 0; i < info.size(); i++)
			{
				try
				{
					sp.AddData(info.get(i).date, info.get(i).recipient, info.get(i).transactionType, info.get(i).amount);
				}
				catch (ParameterException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try
			{
				//sends the info to the client
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
		
	/**
	 * sends a message to the server to retrieve the account records on the server
	 * @param cp
	 * @param client
	 */
	private void handleGetAccountRecordsRequest(ClientProtocol cp, ConnectionToClient client)
	{
		//only tellers can get the account records from the server
		if (client.getInfo("LoginType") == LoginType.TELLER)
		{
			//gets all the account records from the banking server
			ArrayList<RecordInfo> info = bc.getAccountRecords();
			
			ServerProtocol sp = new ServerProtocol(MessageStatus.SUCCESS, Datatype.ACCOUNT_RECORD);
			
			//adds each parameter of each account record one by one
			for (int i = 0; i < info.size(); i++)
			{
				try
				{
					sp.AddData(info.get(i).recordDate, info.get(i).tellerEmpID, info.get(i).accountNumber, info.get(i).recordType);
				}
				catch (ParameterException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try
			{
				//sends the info to the client
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
		
	/**
	 * sends a message to the server to retrieve the customer records on the server
	 * @param cp
	 * @param client
	 */
	private void handleGetCustomerRecordsRequest(ClientProtocol cp, ConnectionToClient client)
	{
		//only tellers can get the customer records from the server
		if (client.getInfo("LoginType") == LoginType.TELLER)
		{
			//gets all the customer records from the banking server
			ArrayList<RecordInfo> info = bc.getCustomerRecords();
			
			ServerProtocol sp = new ServerProtocol(MessageStatus.SUCCESS, Datatype.CUSTOMER_RECORD);
			
			//adds each parameter of each customer record one by one
			for (int i = 0; i < info.size(); i++)
			{
				try
				{
					sp.AddData(info.get(i).recordDate, info.get(i).tellerEmpID, info.get(i).accountNumber, info.get(i).recordType);
				}
				catch (ParameterException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try
			{
				//sends the info to the client
				client.sendToClient(sp);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
