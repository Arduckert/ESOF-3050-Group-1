package src.tests.ocsf;

import java.util.ArrayList;

import src.program.structs.*;

public final class TestVariables
{
	//TEST MESSAGE TEST
	public static final String testMessage_Expected = "testing123";
	
	//ACCOUNT HOLDER TEST
	public static final String availableAccountHolderNumber = "1234567";
	public static final String availableAccountHolderPin = "1234";
	
	public static final String unavailableAccountHolderNumber = "2345678";
	public static final String unavailableAccountHolderPin = "2345";
	
	//TELLER
	public static final String availableTellerID = "987654";
	public static final String availableTellerPassword = "password123";
	
	public static final String unavailableTellerID = "345678";
	public static final String unavailableTellerPassword = "password234";
	
	//FIND ACCOUNT HOLDER BY EMAIL
	public static final String availableAccountHolderFindEmail = "test123@example.com";
	public static final String unavailableAccountHolderFindEmail = "tset123@example.com";
	
	public static final String availableAccountHolderFindName = "John Smith";
	public static final String availableAccountHolderFindNumber = "1234567";
	public static final String availableAccountHolderFindPin = "1234";
	
	//CREATE ACCOUNT HOLDER
	public static final String availableCreateAccountHolderEmail = "tset321@example.com";
	public static final String unavailableCreateAccountHolderEmail = "test321@example.com";
	public static final String createAccountHolderPin = "5678";
	public static final String createAccountHolderSin = "5153318";
	public static final String createAccountHolderNumber = "4333821";
	
	//DELETE ACCOUNT HOLDER
	public static final String availableDeleteAccountHolderNumber = "9876543";
	public static final String unavailableDeleteAccountHolderNumber = "4567890";
	public static final String deleteAccountHolderTellerID = "654321";
	public static final String deleteAccountHolderPin = "7654";
	
	//CREATE AND DELETE PERSON
	public static final String availablePersonFirstName = "John";
	public static final String unavailablePersonFirstName = "Jhon";
	
	public static final String personLastName = "Smith";
	
	public static final String availablePersonSIN = "123456789";
	public static final String unavailablePersonSIN = "987654321";
	
	public static final String personDOB = "12/25/1990";
	
	//ADD AND REMOVE ADDRESSES
	public static final String availablePostalCode = "63146";
	public static final String unavailablePostalCode = "52437";
	
	public static final String addressStreetNumber = "123";
	public static final String addressStreetName = "Example St.";
	public static final String addressProvince = "Ontario";
	public static final String addressCountry = "Canada";
	
	//ADD ACCOUNT HOLDER ROLE TO PERSON
	public static final String availableRoleEmail = "testing@example.com";
	public static final String unavailableRoleEmail = "tseting@example.com";
	
	//ADD AND DELETE ACCOUNT
	public static final AccountType accountType = AccountType.CHEQUING;
	public static final String accountCardNumber = "32547698";
	
	//GET ACCOUNT
	public static final AccountInfo account1 = new AccountInfo(AccountType.CHEQUING, "1", "2");
	public static final AccountInfo account2 = new AccountInfo(AccountType.SAVINGS, "3", "4");
	public static final AccountInfo account3 = new AccountInfo(AccountType.MORTGAGE, "5", "6");
	
	//TRANSFER
	public static final TransferType transferType = TransferType.DEPOSIT;
	public static final String sendingAccountNum = "23456256";
	public static final String availableReceivingAccountNum = "65643832";
	public static final String unavailableReceivingAccountNum = "65623222";
	public static final String transferAmount = "50.00";
	
	//GET TRANSACTIONS
	public static final TransactionInfo transaction1 = new TransactionInfo("1", "2", "3", "4");
	public static final TransactionInfo transaction2 = new TransactionInfo("5", "6", "7", "8");
	public static final TransactionInfo transaction3 = new TransactionInfo("9", "10", "1", "12");
	
	//GET ACCOUNT RECORDS
	public static final RecordInfo accountRecord1 = new RecordInfo("13", "14", "15", "16");
	public static final RecordInfo accountRecord2 = new RecordInfo("17", "18", "19", "20");
	public static final RecordInfo accountRecord3 = new RecordInfo("21", "22", "23", "24");
	
	//GET CUSTOMER RECORDS
	public static final RecordInfo customerRecord1 = new RecordInfo("25", "26", "27", "28");
	public static final RecordInfo customerRecord2 = new RecordInfo("29", "30", "31", "32");
	public static final RecordInfo customerRecord3 = new RecordInfo("33", "34", "35", "36");
	
	//MANAGE BILL
	public static final BillAction billAction = BillAction.CREATE_BILL;
	public static final String availableLOCAccountNumber = "23235333";
	public static final String unavailableLOCAccountNumber = "42602331";
}
