package calendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class calendar {
	
	
	
	public static void main(String[]args) throws ParseException {

		String Lieferdatum = "20-11-2017 ";
		String Lieferdatum2 = "21-12-2017 ";
		String Lieferdatum3 = "02-01-2018 ";
		String Lieferdatum4 = "21.05.2018";
		String Lieferdatum5 = "15.06.2018";
		
		
		Map<String,String>BestellKalender = new TreeMap<String, String>();
		
		
		DateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
		Date inputDate = format1.parse(Lieferdatum);
		
		System.out.println(format1.format(inputDate)); 
		
		BestellKalender.put(Lieferdatum ,": Lieferung an Pasha \n" );
		BestellKalender.put(Lieferdatum2,": Lieferung an Markus\n");
		BestellKalender.put(Lieferdatum3,": Lieferung an Niemanden\n");
		
		 
		
		System.out.println(BestellKalender);
		
		
	}
}
		
		
		
		
		 
		
		
	


