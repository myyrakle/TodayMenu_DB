import static java.lang.System.out;
import java.sql.*;

public class Data_Controller {
	
	public static void all_delete()
	{
		try 
		{
			Statement state = Data_Connector.get_statment();
			state.executeUpdate("delete from student");
			state.executeUpdate("delete from staff");
			state.executeUpdate("delete from dorm");
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void write_student_to_DB(Menu_t menu)
	{
		try 
		{
			PreparedStatement state = Data_Connector.get_connector().prepareStatement("insert into student(breakfast) values(?)");
			
			state.setString(1, menu.get_breakfast());
			state.executeUpdate();
			
			state = Data_Connector.get_connector().prepareStatement("insert into student(lunch) values(?)");
			state.setString(1, menu.get_lunch());
			state.executeUpdate();
			
			state = Data_Connector.get_connector().prepareStatement("insert into student(dinner) values(?)");
			state.setString(1, menu.get_dinner());
			state.executeUpdate();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("write student exception");
		}
	}
	
	public static void write_staff_to_DB(Menu_t menu)
	{
		try 
		{
			PreparedStatement state = Data_Connector.get_connector().prepareStatement("insert into staff(breakfast) values(?)");
			
			state.setString(1, menu.get_breakfast());
			state.executeUpdate();
			
			state = Data_Connector.get_connector().prepareStatement("insert into staff(lunch) values(?)");
			state.setString(1, menu.get_lunch());
			state.executeUpdate();
			
			state = Data_Connector.get_connector().prepareStatement("insert into staff(dinner) values(?)");
			state.setString(1, menu.get_dinner());
			state.executeUpdate();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("write staff exception");
		}
	}
	
	public static void write_dorm_to_DB(Menu_t menu)
	{	
		try 
		{
			PreparedStatement state = Data_Connector.get_connector().prepareStatement("insert into dorm(breakfast) values(?)");
			
			state.setString(1, menu.get_breakfast());
			state.executeUpdate();
			
			state = Data_Connector.get_connector().prepareStatement("insert into dorm(lunch) values(?)");
			state.setString(1, menu.get_lunch());
			state.executeUpdate();
			
			state = Data_Connector.get_connector().prepareStatement("insert into dorm(dinner) values(?)");
			state.setString(1, menu.get_dinner());
			state.executeUpdate();
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			out.println("write dorm exception");
		}
	}
	
	
	public static Menu_t read_student_from_DB()
	{	
		try 
		{
			Statement state = Data_Connector.get_statment();
			String query="SELECT * FROM student";
			ResultSet result = state.executeQuery(query);
			String breakfast="";
			String lunch="";
			String dinner="";
			
			if(result.next())
				breakfast = result.getString("breakfast");
			
			if(result.next())
				lunch = result.getString("lunch");
				
			if(result.next())
				dinner = result.getString("dinner");
		
			result.close();
			return new Menu_t(breakfast, lunch, dinner);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static Menu_t read_staff_from_DB()
	{	
		try 
		{
			Statement state = Data_Connector.get_statment();
			String query="SELECT * FROM staff";
			ResultSet result = state.executeQuery(query);
			String breakfast="";
			String lunch="";
			String dinner="";
			
			if(result.next())
				breakfast = result.getString("breakfast");
			
			if(result.next())
				lunch = result.getString("lunch");
				
			if(result.next())
				dinner = result.getString("dinner");
		
			result.close();
			return new Menu_t(breakfast, lunch, dinner);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static Menu_t read_dorm_from_DB()
	{
		try 
		{
			Statement state = Data_Connector.get_statment();
			String query="SELECT * FROM dorm";
			ResultSet result = state.executeQuery(query);
			String breakfast="";
			String lunch="";
			String dinner="";
			
			if(result.next())
				breakfast = result.getString("breakfast");
			
			if(result.next())
				lunch = result.getString("lunch");
				
			if(result.next())
				dinner = result.getString("dinner");
		
			result.close();
			return new Menu_t(breakfast, lunch, dinner);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
