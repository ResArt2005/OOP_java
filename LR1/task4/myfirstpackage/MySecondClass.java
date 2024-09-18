package myfirstpackage;
public class MySecondClass{
	private int a;
	private int b;
	public MySecondClass(){
	a = 0; b = 0;
	}
	public MySecondClass(int A, int B){
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