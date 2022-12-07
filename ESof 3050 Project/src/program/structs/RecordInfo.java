package src.program.structs;

public class RecordInfo
{
	/**
	 * the date the record was made
	 */
	public String recordDate;
	
	/**
	 * the employee ID of the teller
	 */
	public String tellerEmpID;
	
	/**
	 * the card number of either the account holder for a customer record
	 * or the account number of the account for an account record
	 */
	public String accountNumber;
	
	/**
	 * the record type (customer deletion, account creation, etc.)
	 */
	public String recordType;
	
	/**
	 * constructor
	 * @param _recordDate
	 * @param _tellerEmpID
	 * @param _accountNumber
	 * @param _recordType
	 */
	public RecordInfo(String _recordDate, String _tellerEmpID, String _accountNumber, String _recordType)
	{
		recordDate = _recordDate;
		tellerEmpID = _tellerEmpID;
		accountNumber = _accountNumber;
		recordType = _recordType;
	}
}
