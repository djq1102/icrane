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
	
	
	public static void main(String arg[]){
		
		System.out.println(formatLong4Money(123456));
	}
}
