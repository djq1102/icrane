package com.monitor.app.utils;

import com.monitor.app.dataobject.Device;

public class ToolsUtil {
	
	public static void spiltLocation(Device device){
		String location = "";
		String local = device.getLocation();
		if(local != null && local != ""){
			location = local.replace("(", "").replace(")", "");
		}
	     device.setLocation(location);
	}
}
