public class User {
	String id;
	int money;

	User(String id, int mo) {
		this.id = id;
		money = mo;
	}

	void print() {
		System.out.println("[" + id + "] " + "ฑÝพื : " + money);
	}
}