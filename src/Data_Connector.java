import static java.lang.System.out;
import java.sql.*;

public class Data_Connector {
	
	private static String db_url="jdbc:mysql://localhost:3306/todaymenu?serverTimezone=UTC";
	private static String username="root";
	
	private static Connection connector=null;
	
	public static void sql_open()
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			connector = DriverManager.getConnection(db_url, username, password);
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			out.println("class not found exception");
			e.printStackTrace();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			out.println("SQL exception");
			e.printStackTrace();
		} 		
	}
	public static void sqp_close()
	{
		try 
		{
			if(connector!=null)
				connector.close();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connector=null;
	}
	
	public static Connection get_connector()
	{
		return connector;
	}

	public static Statement get_statment()
	{
		try 
		{
			return connector.createStatement();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private static String password="awesome";
}
