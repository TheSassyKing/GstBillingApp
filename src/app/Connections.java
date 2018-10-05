package app;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connections {

	public static final Connection b2csDBConncetion(){
		String url="jdbc:mysql://localhost/testing";
		String userName="root";
		String password="root";
		
		 Connection c = null;
	      
	      try {
	    	  Class.forName("org.sqlite.JDBC").newInstance();
		         c = DriverManager.getConnection("jdbc:sqlite:testing.sqlite");
		             /* Class.forName("com.mysql.jdbc.Driver").newInstance();;
		         c = DriverManager.getConnection(url,userName,password);*/
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Opened database successfully");
		
		
		return c;
		
	}
}
