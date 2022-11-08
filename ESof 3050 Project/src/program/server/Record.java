package src.program.server;

public abstract class Record {
	private String recordDate;
	private int recordType; //1 for account record 0 for person record
	private Teller teller;
	
	public Record(String date, int type, Teller teller) {
		this.recordDate = date;
		this.recordType = type;
		this.teller = teller;
	}
	public int getRecordType() {
		return this.recordType;
	}
	public Teller getTeller() {
		return this.teller;
	}
	public String getRecordDate() {
		return this.recordDate;
	}
	
	//public String toString() {
		//return String.format("Date: %s\nTeller: %s %s\n", getRecordDate(),getTeller().getFName(),getTeller().getLName());
	//}
}
