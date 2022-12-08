//--------------------------------------------------------------
//Brief description of this file:
//		this class stores all information about an address
//
//Related Documents:
//		Specifications Document
//		Design Document
//
//File created by Aric Duckert
//File approved by Connor McNally and Mathew Camire
//
//List of Important variable names and their brief description:
//		streetName: the name of the street
//		streetNum: the street number
//		postal code: the postal code
//		province: the province the address is located in
//		country: the country the address is located in
//----------------------------------------------------------------
package src.program.server;
import java.io.Serializable;
import java.lang.String;

public class Address  implements Serializable{
	private String streetName;
	private int streetNum;
	private String postalCode;
	private String province;
	private String country;
	
	//Constructor
	public Address(int sNum, String sName, String pCode, String prov, String country) {
		this.streetName = sName;
		this.streetNum = sNum;
		this.postalCode = pCode;
		this.province = prov;
		this.country = country;
	}
	
	//Setters
	public void setStreetName(String x) {
		this.streetName = x;
	}
	public void setStreetNum(int x) {
		this.streetNum = x;
	}
	public void setPostalCode(String x) {
		this.postalCode = x;
	}
	public void setProvince(String x) {
		this.province = x;
	}
	public void setCountry(String x) {
		this.country = x;
	}
	
	//Getters
	public String getStreetName() {
		return this.streetName;
	}
	public int getStreetNum() {
		return this.streetNum;
	}
	public String getPostalCode() {
		return this.postalCode;
	}
	public String getProvince() {
		return this.province;
	}
	public String getCountry() {
		return this.country;
	}
}
