package co.edu.colomboamericano.caelassessment.utils;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ProgramsAgeRangesUtils
{
	public String getCourseTeensKids( LocalDate birthday, String course ) throws ParseException
	{
	    LocalDate currentDate = LocalDate.now();
	    
	    int years = currentDate.getYear() - birthday.getYear();

		Map<String, String> programAgeRangeMap = new HashMap<>();
		
		if( years >= 10 && years < 11 && course.equals("Teen 1") ) {
			programAgeRangeMap.put( "program", "Advanced 1" );
			programAgeRangeMap.put( "course", "Teen 1" );
		};
		
		if( years >= 10 && years < 11 && course.equals("Teen 2") ) {
			programAgeRangeMap.put( "program", "Advanced 1" );
			programAgeRangeMap.put( "course", "Teen 2" );
		};
		
		if( years >= 10 && years < 11 && course.equals("Teen 3") ) {
			programAgeRangeMap.put( "program", "Advanced 1" );
			programAgeRangeMap.put( "course", "Teen 3" );
		};
		
		if( years >= 10 && years < 11 && course.equals("Teen 4") ) {
			programAgeRangeMap.put( "program", "Advanced 1" );
			programAgeRangeMap.put( "course", "Teen 5" );
		};
		
		if( years >= 10 && years < 11 && course.equals("Teen 5") ) {
			programAgeRangeMap.put( "program", "Advanced 1" );
			programAgeRangeMap.put( "course", "Teen 6" );
		};
		
		if( years >= 10 && years < 11 && course.equals("Teen 6") ) {
			programAgeRangeMap.put( "program", "Advanced 1" );
			programAgeRangeMap.put( "course", "Teen 7" );
		};
		
		if( years >= 10 && years < 11 && course.equals("Teen 7") ) {
			programAgeRangeMap.put( "program", "Advanced 1" );
			programAgeRangeMap.put( "course", "Teen 8" );
		};
		
		if( years >= 10 && years < 11 && course.equals("Teen 8") ) {
			programAgeRangeMap.put( "program", "Advanced 1" );
			programAgeRangeMap.put( "course", "Junior 1" );
		};

		if( programAgeRangeMap.get("course") == null ) return course;
		
		return programAgeRangeMap.get("course");
	};
}
