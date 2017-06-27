package Test;

public class StringTest {

	public static void main(String [] args){
		String s = "sadas;ttt";
		System.out.println(s.split(";")[0]);
		System.out.println(s.split(";")[1]);
		System.out.println(s.split(";")[0]+s.split(";")[1]);
	}
	
}
