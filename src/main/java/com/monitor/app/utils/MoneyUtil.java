/**
 * 
 */
package com.monitor.app.utils;

import java.math.BigDecimal;

/**
 * @author ibm
 *
 */
public class MoneyUtil {

	/**
	 * 1000 -> 10.00
	 * @param price
	 * @return
	 */
	public static String formatLong4Money(long cent){
		return BigDecimal.valueOf(cent, 2).toString();
	}
	
	public static long parseCent4Money(String yuan){
		BigDecimal bd = new BigDecimal(yuan);
		
		return bd.multiply(new BigDecimal(100)).longValue();
	}
	
	
	public static void main(String arg[]){
		
		System.out.println(formatLong4Money(123456));
		
		System.out.println(parseCent4Money("12.34"));
	}
}
