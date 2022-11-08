package src.program.server;

public class LoginAttempt {
	int failCount;
	int success;
	AccountHolder accountHolder;
	
	//Constructor
	public LoginAttempt(AccountHolder x) {
		this.failCount = 0;
		this.success = 0;
		this.accountHolder = x;
	}
	//Setters
	public void setSuccess(int x) {
		this.success = x;
	}
	//getters
	public int getFailCount() {
		return this.failCount;
	}
	public int isSuccess() {
		return this.success;
	}
	
	public void increaseFailCount() {
		this.failCount++;
	}
	public void resetFailCount() {
		this.failCount = 0;
	}
}
