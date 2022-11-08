package src.program.server;
import java.lang.String;

public class Address {
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
