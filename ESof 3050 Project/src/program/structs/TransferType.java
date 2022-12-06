package src.program.structs;

/**
 * denotes the transfer type to be used when completing a
 * tranfer of money
 * @author Connor, Created December 6th, 2022
 *
 */
public enum TransferType
{
	/**
	 * deposit money into one of the account holder's accounts
	 */
	DEPOSIT,
	
	/**
	 * withdraw money from one of the account holder's accounts
	 */
	WITHDRAW,
	
	/**
	 * transfer money from one account holder to another. When doing
	 * this method of transfer, place the money in the receiver's 
	 * chequing account
	 */
	TRANSFER
}
