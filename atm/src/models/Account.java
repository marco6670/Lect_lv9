package models;

public class Account {

public static int MAX = 3;
	
	// 계좌번호, 사용자코드, 잔액
	private int accNum;
	private int userCode;
	private int money;
	
	// 생성자 
	public Account(int accNum, int userCode) {
		this.accNum = accNum;
		this.userCode = userCode;
		this.money = 5000; // welcome money 
	}
	
	public Account(int accNum, int userCode, int money) {
		this.accNum = accNum;
		this.userCode = userCode;
		this.money = money;
	}
	
	
	public int getAccNum() {
		return this.accNum;
	}
	
	public int getUserCode() {
		return this.userCode;
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
	
	@Override
	public String toString() {
		return this.accNum + "(" + this.userCode + ") : " 
				+ this.money + "원";
	}
}
