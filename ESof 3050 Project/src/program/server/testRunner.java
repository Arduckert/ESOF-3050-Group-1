//--------------------------------------------------------------
//Brief description of this file:
//		This file is solely for the purpose of testing java operations
//Related Documents:
//		Specifications Document
//		Design Document
//
//File created by Aric Duckert
//File approved by Connor McNally and Mathew Camire
//
//----------------------------------------------------------------
package src.program.server;

public class testRunner {

	public static void main(String[] args) {
		Person p = new Person("john","doe",123,"232303");
		Person reference = p;
		reference.setFName("MARK");
		System.out.println(p.toString());
	}

}
