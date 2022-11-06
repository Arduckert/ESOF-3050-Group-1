import program.server.Address;
import program.server.Person;
import program.server.personRole;

public class main {

	public static void main(String[] args) {
		//System.out.println("Update November1");
		personRole p1 = new personRole(1);
		Address a1 = new Address(122,"cdscdsc","ded","deed","fefe");
		
		Person person1 = new Person("Aric","Duckert",12345,"may 17",a1,p1);
		System.out.print(person1.toString());
	}

}
