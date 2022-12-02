package src.program.server;

public abstract class Record {
	private String recordDate;
	private Teller teller;
	
	public Record(String date, Teller teller) {
		this.recordDate = date;
		this.teller = teller;
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
