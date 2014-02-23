/**
 * 
 */
package com.monitor.app.web.security.util;

/**
 * @author Administrator
 *
 */
public class RoleEnumUtil {

	public static RoleEnum toAuthRole(short userType){
		RoleEnum role = null;
		switch(userType){
			case 1:
				role = RoleEnum.ROLE_SUPERADMIN;
				break;
			case 2:
				role = RoleEnum.ROLE_CUTOMERADMIN;
				break;
			case 3:
				role = RoleEnum.ROLE_GENERALADMIN;
				break;
			default :
				role = RoleEnum.ROLE_GENERALADMIN;
		}
		return role;
	}
}
