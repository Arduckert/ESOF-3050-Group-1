package src.tests.ocsf;

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
	public static final String createAccountHolderNumber = "213452";
	public static final String createAccountHolderPin = "5678";
	
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
}
