package Fundamentals;

public class HelloWorld {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		Double a = 0.0;
		Double b = 1.0;
		Double c = 1.0;
		
		System.out.println(b == c);
		System.out.println(a + b == b);
		System.out.println(a + b == c);
		
		System.out.println(b.equals(c));
		System.out.println(b.equals((a + b)));
		System.out.println(c.equals((a + b)));
	}

}
