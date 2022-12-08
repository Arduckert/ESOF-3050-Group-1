package src.program.structs;

public class TransactionInfo
{
	/**
	 * the date the transaction was done
	 */
	public String date;
	
	/**
	 * the recipient of the transaction (if applicable)
	 */
	public String recipient;
	
	
	public String sender;
	
	/**
	 * transaction type (DEPOSIT, WITHDRAW, TRANSFER)
	 */
	public String transactionType;
	
	/**
	 * the amount that was transferred
	 */
	public String amount;
	
	/**
	 * constructor
	 * @param _date
	 * @param _recipient
	 * @param _transactionType
	 * @param _amount
	 */
	public TransactionInfo(String _date, String _recipient, String send, String _transactionType, String _amount)
	{
		date = _date;
		recipient = _recipient;
		sender = send;
		transactionType = _transactionType;
		amount = _amount;
	}
	
	@Override
	public String toString() {
		return date;
	}
}
