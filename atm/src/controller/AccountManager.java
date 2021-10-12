package controller;

import java.util.ArrayList;
import java.util.Random;

import models.Account;
import models.Bank;
import models.User;

public class AccountManager {
	
	private UserManager um = UserManager.instance;
	public static AccountManager instance = new AccountManager();
	
	private ArrayList<Account> accs = new ArrayList<>();
	
	private User getUser(int log) {
		return um.getUser(log);
	}
	
	public void createAcc() {
		// 현재 로그인 중인 회원이 
		// 보유한 계좌의 수가 확인이 되고 
		// 계좌의 수가 Account.MAX 값을 기준으로 
		// 처리가 두 가지로 나뉨 
		
		int userCode = getUser(Bank.log).getUserCode();
		
//		int cnt = 0;
//		for(Account acc : accs) {
//			if(userCode == acc.getUserCode()) {
//				cnt ++;
//			}
//		}
		int cnt = getUser(Bank.log).getAccCnt();
		if(cnt < 3) {
			this.accs.add(new Account(randomCode(), userCode));
			getUser(Bank.log).setAccCnt(++cnt);
			System.out.print("계좌생성 완료!");
		}
		else {
			System.out.println("최대 개설 가능 개수를 초과했습니다.");
		}
	}
	
	public void deleteAcc() {
		printAccs();
		System.out.print("삭제할 계좌 선택 : ");
		
		String input = Bank.sc.next();
		int delIdx = findIndex(input);
		
		int cnt = getUser(Bank.log).getAccCnt();
		
		if(delIdx != -1) {
			this.accs.remove(delIdx);
			getUser(Bank.log).setAccCnt(--cnt); //
			System.out.println("계좌철회 완료");
		}
	}
	
	private int findIndex(String input) { // 로그인 한 사용자에 대해서 
		int index = -1;
		try {
			int userCode = getUser(Bank.log).getUserCode();
			int cnt = getUser(Bank.log).getAccCnt();
			
			int num = Integer.parseInt(input);
			
			if(num >= 0 && num < cnt) {
				int tempCnt = 0;
				for(int i=0; i<this.accs.size(); i++) {
					if(userCode == this.accs.get(i).getUserCode()) {
						index = i;
						tempCnt ++;
						if(tempCnt == num)
							break;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return index;
	}
	
	public void inputMoney() {
		printAccs();
		System.out.print("입금할 계좌 선택 : ");
		
		String input = Bank.sc.next();
		int index = findIndex(input);
		
		if(index != -1) {
			Account temp = this.accs.get(index);
			int balance = temp.getMoney();
			
			System.out.print("입금금액 : ");
			int money = Bank.sc.nextInt();
			
			if(money > 0) {
				balance += money;
				temp.setMoney(balance);
				System.out.printf("출금 완료, 잔액 : %d원", balance);
			}
		}
		
	}
	
	public void outMoney() {
		printAccs();
		System.out.print("출금할 계좌 선택 : ");
		
		String input = Bank.sc.next();
		int index = findIndex(input);
		
		if(index != -1) {
			Account temp = this.accs.get(index);
			int balance = temp.getMoney(); // 실잔액 
			
			System.out.print("출금금액 : ");
			int money = Bank.sc.nextInt();
			
			if(balance >= money) {
				balance -= money;
				temp.setMoney(balance);
				System.out.printf("출금 완료, 잔액 : %d원", balance);
			}
			
		}
	}
	
	public void withdraw() {
		System.out.print("이체할 계좌번호 : ");
		int target = Bank.sc.nextInt(); 
		
		int targetIdx = -1;
		for(int i=0; i<this.accs.size(); i++) {
			if(target == this.accs.get(i).getAccNum())
				targetIdx = i;
		}
		
		printAccs();
		System.out.print("출금할 계좌 선택 : ");
		
		String input = Bank.sc.next();
		int index = findIndex(input);
		
		if(targetIdx != -1 && index != -1) {
			System.out.print("이체할 금액 : ");
			int money = Bank.sc.nextInt();
			
			Account temp = this.accs.get(index);
			int balance = temp.getMoney();
			
			if(balance >= money) {
				balance -= money;
				temp.setMoney(balance);
				
				temp = this.accs.get(targetIdx);
				balance = temp.getMoney();
				balance += money;
				temp.setMoney(balance);
			}
			else {
				System.out.println("잔액이 부족합니다.");
			}
		}
		else {
			System.out.println("존재하지 않는 계좌입니다.");
		}
		
	}
	
	private int randomCode() {
		Random rn = new Random();
		while(true) {
			int ranCode = rn.nextInt(8999) + 1000;
			
			boolean check = false;
			for(Account acc : this.accs) {
				if(ranCode == acc.getAccNum())
					check = true;
			}
			
			if(!check)
				return ranCode;
		}
	}
	
	public void printAccs() {
		int userCode = getUser(Bank.log).getUserCode();
		
		for(int i=0; i<this.accs.size(); i++) {
			if(userCode == accs.get(i).getUserCode()) {
				System.out.print(i+1 + ") ");
				System.out.println(accs.get(i));
			}
		}
	}
	
	public void printAllData() {
		for(Account acc : this.accs)
			System.out.println(acc);
	}

	public Account getAccount(int index) {
		return this.accs.get(index);
	}
	
	public int getAccsSize() {
		return this.accs.size();
	}
	
	public void addAccount(Account acc) {
		this.accs.add(acc);
	}
	
}
