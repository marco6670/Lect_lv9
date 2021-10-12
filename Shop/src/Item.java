
public class Item {
	String name;
	int price;
	String category; // 카테고리 // 육류 , 과자 , 어류 , 과일 등등

	Item(String na, int pr, String cate) {
		name = na;
		price = pr;
		category = cate;
	}

	void print() {
		System.out.println("[" + name + "]" + "[" + price + "]" + "[" + category + "]");
	}
}
