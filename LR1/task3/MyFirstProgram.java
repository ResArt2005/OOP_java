class MyFirstClass {
	public static void main(String[] args) {
		MySecondClass o = new MySecondClass(10, 20);
		System.out.println(o.option1());
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				o.setA(i);
				o.setB(j);
				System.out.print(o.option1());
				System.out.print(" ");
			}
		System.out.println();
		}
	}
}

class MySecondClass{
	private int a;
	private int b;
	MySecondClass(){
	a = 0; b = 0;
	}
	MySecondClass(int A, int B){
	a = A; b = B;
	}
	public int getA(){
		return a;
	}
	public int getB(){
		return b;
	}
	public void setA(int A){
		a = A;
	}
	public void setB(int B){
		b = B;
	}
	public int option1(){ return a+b;}
	public int option2(){ return a-b;}
	public int option3(){ return a*b;}
	public int option4(){ return a/b;}
	public int option5(){ return a%b;}
	public int option6(){ return a < b ? a:b;}
	public int option7(){ return a > b ? a:b;}
	public int option8(){ return a&b;}
	public int option9(){ return a|b;}
	public int option10(){ return a^b;}
	public int option11(){ return a<<b;}
}