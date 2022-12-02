package src.program.server;
import java.time.LocalDate;

public abstract class Record {
	private LocalDate recordDate;
	private Teller teller;
	private String type;
	private String action;
	
	public Record(Teller teller, String t, String a) {
		this.recordDate = LocalDate.now();
		this.teller = teller;
		this.type = t;
		this.action = a;
	}
	public Teller getTeller() {
		return this.teller;
	}
	/*
	public String getRecordDate() {
		return String.format("%s",recordDate);
	}
	
	public String toString() {
		if(type == "account") {
			switch(action) {
			case "created":
				return String.format("ACCOUNT CREATED\nTeller:%s %s", getRecordDate(),getTeller().getFName(),getTeller().getLName());
			}
		}
		
	}
	*/
}
