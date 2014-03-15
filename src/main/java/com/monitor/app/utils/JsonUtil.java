package com.monitor.app.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.monitor.app.result.ServiceResult;


public class JsonUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String buildJosn(List<Map> resultMap,ServiceResult result,int sEcho) {
		Map map = new HashMap();// jquery datatable 需要的数据类型封装
		map.put("aaData", resultMap);// 数据集
		map.put("iTotalRecords", result.getModule());// 总条数
		map.put("iTotalDisplayRecords", result.getModule());// 过滤之后显示的实际条数
		map.put("sEcho", sEcho);// 来自客户端 sEcho 的没有变化的复制品
		logger.warn(">>>userInfo=" + JSONObject.toJSONString(map));
		return JSONObject.toJSONString(map);
	}

}
