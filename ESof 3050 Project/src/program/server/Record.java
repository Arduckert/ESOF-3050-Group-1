package src.program.server;
import java.time.LocalDate;

public abstract class Record {
	private LocalDate recordDate;
	private Teller teller;
	private String action;
	
	public Record(Teller teller, String a) {
		this.recordDate = LocalDate.now();
		this.teller = teller;
		this.action = a;
	}
	public Teller getTeller() {
		return this.teller;
	}
	
	public String getRecordDate() {
		return String.format("%s",recordDate);
	}
	
	@Override
	public String toString() {
		switch(action) {
		case "created":
			return String.format("%s: Created",recordDate);
		case "removed":
			return String.format("%s: Removed",recordDate);
		default:
			return String.format("");
		}
	}

}