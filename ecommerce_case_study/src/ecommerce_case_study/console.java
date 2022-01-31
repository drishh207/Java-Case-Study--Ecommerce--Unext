package ecommerce_case_study;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class console {
	Scanner scan = new Scanner(System.in);
	
	public void create_console() {
		try {
			System.out.println("*****************Welcome to DS Mart************************");
			System.out.println();
			System.out.println("Please login as follows: ");
			System.out.println("Press U for User Login");
			System.out.println("Press R for New Registration");
			System.out.println("Press X to Exit application");
			System.out.println("Enter your choice: ");
			String a = scan.nextLine();
			
			
			switch(a.charAt(0)) {			
			case 'U':
			case 'u': System.out.println("Enter username: ");
			String b1 = scan.nextLine();
			System.out.println("Enter password: ");
			String c1 = scan.nextLine();
				user obj1 = new user();
				obj1.login_user(b1,c1);
						break;
						
			case 'R':
			case 'r': user obj2 = new user();
				obj2.create_user();
				break;
				
			case 'X':
			case 'x': System.out.println("****************Thank you for visiting DS Mart***********************"); 
				System.exit(0);
			}
		
		} 
		catch (Exception e) {
			//System.out.println(e.getMessage());
			System.out.println("error");
		}
	
		}
	

	public static void main(String[] args) {
		console c = new console();
		c.create_console();
	}
}
