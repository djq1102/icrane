/**
 * 
 */
package com.monitor.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * @author ibm
 *
 */
public class AbstractController {

	@SuppressWarnings({"unchecked","rawtypes"})
	public String outputJsonAsResponse(List<Map> data, long total,int echo){
			Map map = new HashMap();// jquery datatable 需要的数据类型封装
			map.put("aaData", data);// 数据集
			map.put("iTotalRecords", total);// 总条数
			map.put("iTotalDisplayRecords", total);// 过滤之后显示的实际条数
			map.put("sEcho", echo);// 来自客户端 sEcho 的没有变化的复制品
			
			return JSONObject.toJSONString(map);
	}
}
