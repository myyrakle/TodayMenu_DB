import static java.lang.System.out;

import java.io.IOException;
import java.util.*;
import org.jsoup.*;

public class Menu_Parser{
	
	private static String parsed_text="";
	//private static int type_of_restaurant=-1;
	
	public static void parse()
	{
		try 
		{
			Data_Controller.all_delete();
			parsed_text=Jsoup.connect(Main.student_address).get().text();
			extract_menu_non_dorm(true);
			parsed_text=Jsoup.connect(Main.staff_address).get().text(); 
			extract_menu_non_dorm(false);
			parsed_text=Jsoup.connect(Main.dorm_address).get().html();
			extract_menu_dorm();
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private static void extract_menu_dorm() //기숙식 메뉴 포맷팅
	{
		Calendar cl=Calendar.getInstance();
		
		final String today;
		
		switch(cl.get(Calendar.DAY_OF_WEEK))
		{
		case 1: today="일요일"; break;
		case 2: today="월요일"; break;
		case 3: today="화요일"; break;
		case 4: today="수요일"; break;
		case 5: today="목요일"; break;
		case 6: today="금요일"; break;
		case 7: today="토요일"; break;
		default: out.println("today switch exception"); today="";
		}
		
		//오늘 메뉴만 추출
		StringBuffer unsorted_menu = new StringBuffer( parsed_text.substring(parsed_text.indexOf(today)+12) );
		while(unsorted_menu.charAt(0)=='<' || unsorted_menu.charAt(0)=='>' || unsorted_menu.charAt(0)=='\n' ||
			  unsorted_menu.charAt(0)=='t' || unsorted_menu.charAt(0)=='d' || unsorted_menu.charAt(0)=='/' ||
			  unsorted_menu.charAt(0)==' ')
			unsorted_menu.deleteCharAt(0);
		
		unsorted_menu.delete(unsorted_menu.indexOf("</tr>")-13, unsorted_menu.length()); //뒷부분 뭉텅
		
		final String meal_t_delim="*";
		unsorted_menu.replace(unsorted_menu.indexOf("</td>"), unsorted_menu.indexOf("<td>")+4, meal_t_delim);
		
		unsorted_menu.replace(unsorted_menu.indexOf("</td>"), unsorted_menu.indexOf("<td")+17, meal_t_delim);
		
		for(int i=0; i<2; ++i)
			if(unsorted_menu.indexOf("**")!=-1)
				unsorted_menu.insert(unsorted_menu.indexOf("**")+1, "없어용");
		

		for(int index = unsorted_menu.indexOf("&amp;"); index!=-1; index = unsorted_menu.indexOf("&amp")) 
			unsorted_menu.replace(index, index+5, "&"); //html character &amp -> &
		
		StringTokenizer meals_t_tokenizer=new StringTokenizer(unsorted_menu.toString(),meal_t_delim);
		
		Menu_t result = new Menu_t(true);
		final int buffer_max=50;
		
		//조식
		{
			String unsorted_breakfast=meals_t_tokenizer.nextToken();
			
			StringBuffer buffer=new StringBuffer(buffer_max);
			buffer.append("조식\n");
			
			StringTokenizer tokenizer=new StringTokenizer(unsorted_breakfast," ");
			
			while(tokenizer.hasMoreTokens())
				buffer.append(tokenizer.nextToken()).append('\n');
			
			result.set_breakfast(buffer.toString());
		}
		//조식
		
		//중식
		{
			String unsorted_lunch=meals_t_tokenizer.nextToken();
			
			StringBuffer buffer=new StringBuffer(buffer_max);
			buffer.append("중식\n");
			
			StringTokenizer tokenizer=new StringTokenizer(unsorted_lunch," ");
			
			while(tokenizer.hasMoreTokens())
				buffer.append(tokenizer.nextToken()).append('\n');
			
			result.set_lunch(buffer.toString());
		}
		//중식
		
		//석식
		{
			String unsorted_dinner=meals_t_tokenizer.nextToken();
			
			StringBuffer buffer=new StringBuffer(buffer_max);
			buffer.append("석식\n");
			
			StringTokenizer tokenizer=new StringTokenizer(unsorted_dinner," ");
			
			while(tokenizer.hasMoreTokens())
			buffer.append(tokenizer.nextToken()).append('\n');
			
			result.set_dinner(buffer.toString());
		}
		//석식
		Data_Controller.write_dorm_to_DB(result);
	}
	
	private static void extract_menu_non_dorm(boolean is_student) //학식 메뉴 포맷팅
	{
		if(parsed_text.contains("등록된 식당메뉴 정보가 없습니다."))
		{
			
			if(is_student) 
				Data_Controller.write_student_to_DB(new Menu_t(false));
			else 
				Data_Controller.write_staff_to_DB(new Menu_t(false));
			
			return;
		}
		else 
		{
			Menu_t result = new Menu_t(true);
			
			String unsorted_menu=parsed_text.substring(
														parsed_text.indexOf("조식"), 
														parsed_text.indexOf("담당부서") );
			
			final int buffer_max=50;
			
			//조식
			{
				/*
				String unsorted_breakfast=unsorted_menu.substring(
																	unsorted_menu.indexOf("조식"), 
																	unsorted_menu.indexOf("중식") );
				
				StringBuffer buffer=new StringBuffer(buffer_max);
				StringTokenizer tokenizer=new StringTokenizer(unsorted_breakfast," ");
				
				while(tokenizer.hasMoreTokens())
					buffer.append(tokenizer.nextToken()).append('\n');
				
				retval.set_breakfast(buffer.toString());
				*/ //어차피 안줌
				result.set_breakfast("조식\n없어용\n");
			}
			//조식
			
			//중식
			{
				String unsorted_lunch=unsorted_menu.substring(
																	unsorted_menu.indexOf("중식"), 
																	unsorted_menu.indexOf("석식") );
				
				StringBuffer buffer=new StringBuffer(buffer_max);
				StringTokenizer tokenizer=new StringTokenizer(unsorted_lunch," ");
				
				while(tokenizer.hasMoreTokens())
				buffer.append(tokenizer.nextToken()).append('\n');
				
				result.set_lunch(buffer.toString());
			}
			//중식
			
			//석식
			{
				String unsorted_dinner=unsorted_menu.substring( unsorted_menu.indexOf("석식") );
				
				StringBuffer buffer=new StringBuffer(buffer_max);
				StringTokenizer tokenizer=new StringTokenizer(unsorted_dinner," ");
				
				while(tokenizer.hasMoreTokens())
				buffer.append(tokenizer.nextToken()).append('\n');
				
				result.set_dinner(buffer.toString());
			}
			//석식
			
			if(is_student)
				Data_Controller.write_student_to_DB(result);
			else
				Data_Controller.write_staff_to_DB(result);
		}
	}
}
