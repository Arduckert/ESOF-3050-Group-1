package src.program.client;

/**
 * @author Connor
 * This is the IBankingClientController interface. This is used by the BankingClient class as an
 * indirect way to communicate with the gui of the client. Only the functions that the BankingClient
 * class needs to call should be included in this interface as this is being used as a sort of
 * abstraction design pattern.
 * 
 * This interface was made to make testing the OCSF much easier and to remove a direct
 * dependency between the BankingClient class and the BankingClientController class.
 */
public interface IBankingClientController
{

}
