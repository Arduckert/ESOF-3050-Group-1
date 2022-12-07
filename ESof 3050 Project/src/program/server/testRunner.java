package src.program.server;

public class testRunner {

	public static void main(String[] args) {
		Person p = new Person("john","doe",123,"232303");
		Person reference = p;
		reference.setFName("MARK");
		System.out.println(p.toString());
	}

}
