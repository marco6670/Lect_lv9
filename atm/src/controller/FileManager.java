package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import models.Account;
import models.User;

public class FileManager {
	
	private File file;
	private FileWriter fw;
	private FileReader fr;
	private BufferedReader br;
	
	private String fileUsersName = "users.txt";
	private String fileAccsName = "accs.txt";
	
	private UserManager um = UserManager.instance;
	private AccountManager am = AccountManager.instance;
	
	public static FileManager instance = new FileManager();
	
	// save 
	public void save() {
		// um.users
		// code/id/pw/name/acccnt
		String data = makeUsersData();
		try {
			file = new File(fileUsersName);
			fw = new FileWriter(file);
			fw.write(data);
			fw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// am.accs 
		// accNum/code/money
		data = makeAccsData();
		try {
			file = new File(fileAccsName);
			fw = new FileWriter(file);
			fw.write(data);
			fw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private String makeUsersData() {
		String data = "";
		
		for(int i=0; i<um.getUsersSize(); i++) {
			data += um.getUser(i).getUserCode() + "/";
			data += um.getUser(i).getId() + "/";
			data += um.getUser(i).getPw() + "/";
			data += um.getUser(i).getName() + "/";
			data += um.getUser(i).getAccCnt() + "\n";
		}
		return data;
	}
	
	private String makeAccsData() {
		String data = "";
		
		for(int i=0; i<am.getAccsSize(); i++) {
			data += am.getAccount(i).getAccNum() + "/";
			data += am.getAccount(i).getUserCode() + "/";
			data += am.getAccount(i).getMoney() + "\n";
		}
		return data;
	}
	
	// load 
	public void load() {
		// um.users
		// code/id/pw/name/acccnt
		try {
			file = new File(fileUsersName);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			String data = br.readLine();
			while(data != null) {
				String info[] = data.split("/");
				
				int code = Integer.parseInt(info[0]);
				String id = info[1];
				String pw = info[2];
				String name = info[3];
				int cnt = Integer.parseInt(info[4]);
				User user = new User(code, id, pw, name, cnt);
				
				um.addUser(user);
				
				data = br.readLine();
			}
			
			fr.close();
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// am.accs 
		// accNum/code/money
		try {
			file = new File(fileAccsName);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			String data = br.readLine();
			while(data != null) {
				String info[] = data.split("/");
				
				int num = Integer.parseInt(info[0]);
				int code = Integer.parseInt(info[1]);
				int money = Integer.parseInt(info[2]);
				
				Account acc = new Account(num, code, money);
				am.addAccount(acc);
				
				data = br.readLine();
			}
			
			fr.close();
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(um.getUsersSize() == 0) {
			um.setAdmin();
		}
	}
}