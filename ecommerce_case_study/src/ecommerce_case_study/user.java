package ecommerce_case_study;

 

import java.sql.Connection;
import java.util.*;
import java.io.Console;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

 

public class user {

       //Create Connection with Database

       Connection con;

       Scanner scan = new Scanner(System.in);

       public void connect()

       {

              try {

                     Class.forName("com.mysql.cj.jdbc.Driver"); 

                    

                     con=DriverManager.getConnection("jdbc:mysql://localhost/ecommerce","root","Gosencity@1"); 

              }

              catch(Exception e) {

                     System.out.println(e.getMessage());

              }

       }

      

       public void after_choice(String a, String b) {

              System.out.println("*******************");

              System.out.println("Press A to go back to account");

              System.out.println("Press B to Log out");

              System.out.println("*******************");

              System.out.println("Enter your choice: ");

              String ch = scan.nextLine();

              switch(ch.charAt(0)) {

              case 'A':

              case 'a': login_user(a,b);

              break;

              case 'B':

              case 'b': console obj = new console();

                           obj.create_console();

                           break;

              }

       }

      

       public void create_user() {

              connect();

              System.out.println("Enter required details");

              System.out.println("Enter username: ");

              String username = scan.nextLine();

              System.out.println("Enter Password: ");

              String pwd = scan.nextLine();

              System.out.println("Enter name: ");

              String name = scan.nextLine();

              System.out.println("Enter mobile number");

              String no = scan.nextLine();

             

              //check if number is digit and of length 10

              String pattern = "\\d{10}";

              Pattern pattern1 = Pattern.compile(pattern);

              Matcher matcher = pattern1.matcher(no);

              if(matcher.matches() == false){

                     System.out.println("Enter a valid mobile number:");

                     create_user();

              }

             

              System.out.println("Enter address: ");

              String add = scan.nextLine();

              System.out.println("Enter city: ");

              String city = scan.nextLine();

              System.out.println("Enter state: ");

              String state = scan.nextLine();

              try {

                     Statement stmt4 = con.createStatement();

                     String query4 = "select username from user_login where username = '" + username + "';";

                     ResultSet rs = stmt4.executeQuery(query4);

                     if(rs.next()){

                           System.out.println("Please enter another username.");

                           create_user();

                     }

                    

                     else{

                           Statement stmt = con.createStatement();

                           String query = "insert into user_login values('" + username + "' , ' " + pwd + "');";

                           stmt.executeLargeUpdate(query);

                          

                           String query1 = "insert into users values('" + username + "' , '" + name + "'," + no + ", '" + add + "','" + city + "','" + state + "');";

                           stmt.executeLargeUpdate(query1);

                          

                           System.out.println("Registration Successful. Login to access account");

                          

                           System.out.println("Enter username: ");

                           String b = scan.nextLine();

                           System.out.println("Enter password: ");

                           String c = scan.nextLine();

                           login_user(b,c);

                     }

                    

                    

                    

              } catch (Exception e) {

                     System.out.println(e.getMessage());

              }

             

       }

      

       public void view_products() {

              System.out.println("*********************************************************");

              System.out.println("Following categories are available to order from---");

              try {

                     connect();

                     Statement stmt = con.createStatement();

                     String query = "select category_name from category;";

                     ResultSet rs = stmt.executeQuery(query);

                     int i = 1;

                     System.out.println("------------------------------");

                     while(rs.next()) {

                           System.out.println(i + ") "+ rs.getString(1));

                     }

                     System.out.println("------------------------------");

                     System.out.println("Enter category name that you want to view products from: ");

                     String name = scan.nextLine();

                     name = name.toLowerCase();

                     Statement stmt1 = con.createStatement();

                     String query1 = "select category_id from category where category_name = '" + name + "';";

                    

                     ResultSet rs1 = stmt1.executeQuery(query1);

                     int id = 0;

                     if(rs1.next()) {

                           id = rs1.getInt(1);

                     }

                    

                     Statement stmt2 = con.createStatement();

                     String query2 = "select * from product where category_id = " + id + ";";

                    

                     ResultSet rs2 = stmt2.executeQuery(query2);

                     while(rs2.next()) {

                           System.out.println("==========================================");

                           System.out.println("Product Id: " + rs2.getString(1));

                           System.out.println(rs2.getString(2));

                           System.out.println(rs2.getString(3));

                           System.out.println("price: " + rs2.getInt(6));

                           System.out.println("Manufacturer: " + rs2.getString(4));

                           System.out.println("Seller: " + rs2.getNString(5));

                           System.out.println("==========================================");

                           System.out.println();

                     }

                    

                    

              } catch (Exception e) {

                     e.printStackTrace();

              }

             

       }

      

       public void order_items(String a, String b) {

              view_products();

              try {

                     connect();

                     System.out.println("Enter Product ID to order item");

                     //check quantity -------------------

                     int pid = scan.nextInt();

                     System.out.println("Enter quantity you want to order: ");

                     int quantity = scan.nextInt();

                    

                     Statement stmt1 = con.createStatement();

                     String querya = "select quantity from product where product_id = " + pid + ";";

                     ResultSet rs1 = stmt1.executeQuery(querya);

                     int qu = 0;

                     if(rs1.next()) {

                           qu = rs1.getInt(1);

                     }

                     if(qu > quantity) {

                           Random rnd = new Random();

                           int oid = 100000 + rnd.nextInt(900000);

                           SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

                           String date = df.format(new Date());

                           LocalDate delivery_date = LocalDate.now();

                           delivery_date = delivery_date.plusDays(7);

                          

                           Statement stmt = con.createStatement();

                           String query = "insert into orders values(" + oid + " , '" + date + "','" + delivery_date + "'," + pid + "," + quantity + ",'" + a +"');";

                           //System.out.println(query);

                           stmt.executeUpdate(query);

                           System.out.println("Items Ordered successfully");

                           System.out.println("Your order will be delivered till " + delivery_date + ". Kindly refer to order id " + oid + " for further communications for this order.");

                          

                           //reduce quantity

                           Statement stmt2 = con.createStatement();

                           String query2 = "update product set quantity = " + (qu - quantity) + " where product_id = " + pid + ";" ;

                           //System.out.println(query2);

                           stmt.executeUpdate(query2);

                           login_user(a,b);

                     }

                     else if (qu == 0){

                           System.out.println("Sorry to inform but this item is sold out. We will inform you once it gets restocked.");

                           order_items(a,b);

                     }

                     else if(qu < quantity){

                           System.out.println("Only " + qu + " items left in stock. Please order accordingly");

                           order_items(a,b);

                     }

                    

                    

              } catch (Exception e) {

                     e.printStackTrace();

              }

             

       }

      

       public void view_order(String a, String b) {

              try {

                     connect();

                     Statement stmt = con.createStatement();

                     String query = "select * from orders where username ='" + a + "' order by order_date;";

                     ResultSet rs = stmt.executeQuery(query);

                     while(rs.next()) {

                           System.out.println("--------------------------------------");

                           System.out.println("Order ID: " + rs.getInt(1));

                           int id = rs.getInt("product_id");

                           Statement stmt1 = con.createStatement();

                           String query1 = "select product_name from product where product_id = "+ id + ";";

                           ResultSet rs1 = stmt1.executeQuery(query1);

                           if(rs1.next()) {

                                  System.out.println("Item Bought: " + rs1.getString(1));

                           }

                          

                           System.out.println("Quantity: " + rs.getInt(5));

                           System.out.println("Order Date: " + rs.getDate(2));

                           System.out.println("--------------------------------------");

                           System.out.println();

                     }

                     after_choice(a,b);

              } catch (Exception e) {

                     System.out.println(e.getMessage());

              }

       }

      

       public void view_profile(String a, String b) {

              try {

                     connect();

                     Statement stmt = con.createStatement();

                     String query = "select * from users where username ='" + a + "';";

                     ResultSet rs = stmt.executeQuery(query);

                     if(rs.next()) {

                           System.out.println("********************");

                           System.out.println("Name: " + rs.getString(2));

                           System.out.println("Mobile number: " + rs.getString(3));

                           System.out.println("Address: " + rs.getString(4) + ", " + rs.getString(5) + ", " + rs.getString(6) + ", India");

                           System.out.println("********************");

                     }

                     after_choice(a,b);

              } catch (Exception e) {

                     System.out.println(e.getMessage());

              }

       }

      

       public void edit_profile(String a, String b) {

              try {

                     connect();

                     Statement stmt = con.createStatement();

                     System.out.println("Enter A for updating mobile number \n B for updating address \n C for updating city \n D for updating state");

                     String ch = scan.nextLine();

                     String up = "";

                     String parameter = "";

                     switch(ch.charAt(0)) {

                     case 'a':

                     case 'A': System.out.println("Enter new mobile number");

                                  parameter = "mobile_no";

                                  up = scan.nextLine();

                                  break;

                                 

                     case 'b':

                     case 'B': System.out.println("Enter new address");

                                  parameter = "address";

                                  up = scan.nextLine();

                                  break;

                                 

                     case 'c':

                     case 'C': System.out.println("Enter new city");

                                  parameter = "city";

                                  up = scan.nextLine();

                                  break;

                                 

                                 

                     case 'd':

                     case 'D': System.out.println("Enter new state");

                                  parameter = "state";

                                  up = scan.nextLine();

                                  break;

                     }

                     String query = "update users set " + parameter + " = '" + up + "' where username = '" + a +"';";

                     //System.out.println(query);

                     stmt.executeUpdate(query);

                     System.out.println("Profile Updated.\n New Profile is");

                     view_profile(a,b);

                    

              }catch(Exception e) {

                     System.out.println(e.getMessage());

              }

       }

      

       public void cancel_order(String a, String b) {

              try {

                     connect();

                     System.out.println("Enter the order number of the order you wish to cancel");

                     int no = scan.nextInt();

                    

                     //updating quantity

                     int qu = 0;

                     int pid = 0;

                     int qu1 = 0;

                     Statement stmt1 = con.createStatement();

                     String query1 = "select quantity,product_id from orders where order_id = " + no +";";

                     //System.out.println(query1);

                     ResultSet rs = stmt1.executeQuery(query1);

                     while(rs.next()) {

                           qu = rs.getInt(1);

                           pid = rs.getInt(2);

                     }

                     //System.out.println(qu + " " + pid + "test");

                    

                     Statement stmt2 = con.createStatement();

                     String query2 = "select quantity from product where product_id = " + pid +";";

                     //System.out.println(query2);

                     ResultSet rs1 = stmt2.executeQuery(query2);

                     if(rs1.next()) {

                           qu1 = rs1.getInt(1);

                     }

                    

                     Statement stmt3 = con.createStatement();

                     String query3 = "update product set quantity = " + (qu + qu1) + " where product_id = " + pid + ";";

                     //System.out.println(query3);

                     stmt3.executeUpdate(query3);

                    

                    

                     //delete order

                     Statement stmt = con.createStatement();

                     String query = "delete from orders where username = '" + a +"' and order_id = " + no +";";

                     stmt.executeUpdate(query);

                     System.out.println("Order number " + no + " cancelled successfully.");

                     stmt.close();

                    

                    

                    

                     login_user(a,b);

              } catch (Exception e) {

                     System.out.println(e.getMessage());

              }

              after_choice(a,b);

       }

      

       public void console1(String a, String b) {

              System.out.println("----------------------------------------------------");

              System.out.println("Following are the functionalities offered to you");

              System.out.println("Press A for Order Items");

              System.out.println("Press B for View Past Orders");

              System.out.println("Press C for View Profile");

              System.out.println("Press D for Edit Profile");

              System.out.println("Press E for Cancelling Order");

              System.out.println("Press L for Log Out");

              System.out.println("----------------------------------------------------");

              System.out.println("Enter your choice: ");

              String temp1 = scan.nextLine();

              switch(temp1.charAt(0)) {

              case 'A':

              case 'a': order_items(a,b);

                                  break;

              case 'B':

              case 'b': view_order(a,b);

                                  break;

              case 'C':

              case 'c': view_profile(a,b);

                                  break;

             

              case 'D':

              case 'd': edit_profile(a,b);

                                  break;

             

              case 'E':

              case 'e': cancel_order(a,b);

                                  break;

             

                                 

              case 'L':

              case 'l': console obj = new console();

                             obj.create_console();

                             break;

              }

       }

      

      

       public void login_user(String a, String b) {

              try {

                    

                     connect();

                     Statement stmt = con.createStatement();

                     String query = "select username,password from user_login where username = \'" + a +"\' and password = " + b + ";";

                     //System.out.println(query);

                     ResultSet rs= stmt.executeQuery(query);

                          

                     System.out.println();

                     if(rs.next()){

                           user u = new user();

                           u.console1(a,b);

                           System.out.println("Login successful");

                     }

                    

                     else {

                           System.out.println("Please enter correct credentials");

                           console obj = new console();

                           obj.create_console();

                     }

                    

                    

              } catch (Exception e) {

                     System.out.println(e.getMessage());

              }

       }

}

 