package src.tests.ocsf;

/**
 * @author Connor
 * This class is responsible for initializing a ClientTestDriver and
 * running its tests
 * 
 * Created on November 28, 2022
 */
public class ClientRunner
{
	public static void main(String[] args)
	{
		ClientTestDriver ctd = new ClientTestDriver();
		ctd.RunTests();
	}
}