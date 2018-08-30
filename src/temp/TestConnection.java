package temp;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

//import org.nhsdigital.dd.helper.ConfigUtility;

public class TestConnection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("start");
		try{
			
		
		//Properties properties = ConfigUtility.getConfigProperties();
		//String url = properties.getProperty("javax.persistence.jdbc.url");
		//System.out.println(url);
		// Create a variable for the connection string.
		//String connectionUrl = "jdbc:sqlserver://GL-D-DSSSQL-L1.DEV.GREEN.NET\\CI:1433;databaseName=DATA_DICTIONARY_CODES";
			String connectionUrl = "jdbc:sqlserver://ggh-d-dspora-l1:1433;databaseName=dd_dev";
		//String connectionUrl = url;
		// Declare the JDBC objects.
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
        	try {
        		// Establish the connection.
        		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            		//con = DriverManager.getConnection(connectionUrl,"dasm8","1968Tptcom");
        		con = DriverManager.getConnection(connectionUrl,"dd_dev_user","T2qaz678*");
            		//con = DriverManager.getConnection(connectionUrl,"NWR382","%DDC%2018");
            
            		// Create and execute an SQL statement that returns some data
            		String SQL = "SELECT TOP 10 * FROM dbo.Test";
            		stmt = con.createStatement();
            		rs = stmt.executeQuery(SQL);
            
            		// Iterate through the data in the result set and display it.
            		//while (rs.next()) {
            		//	System.out.println(rs.getString(4) + " " + rs.getString(6));
            		//}
            		System.out.println("end");
        	}
        
		// Handle any errors that may have occurred.
		catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (rs != null) try { rs.close(); } catch(Exception e) {}
	    		if (stmt != null) try { stmt.close(); } catch(Exception e) {}
	    		if (con != null) try { con.close(); } catch(Exception e) {}
		}
        	
		}catch(Exception e1){
			e1.printStackTrace();
		}

	}

}
