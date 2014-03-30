/**
 * 
 */
package com.monitor.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ibm
 *
 */
public class DateUtil {

	private static final String DEFAULT_DATE_PATTERN = "MM/dd/yyyy";
	
	public static String formatDate(Date d , String pattern){
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		
		String dateStr = "";
		try{
			dateStr = sdf.format(d);
		}catch(Exception e){
		}
		
		return dateStr;
	}
	
	public static String formatDate(Date d){
		return formatDate(d, DEFAULT_DATE_PATTERN);
	}
	
	
}
