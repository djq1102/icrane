/**
 * 
 */
package com.monitor.app.controller.model;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.monitor.app.dataobject.ModelVar;

/**
 * @author ibm
 *
 */
@Controller
public class VariableController {

	
	@RequestMapping(value = "/var/add")
	public String add(@RequestParam("name") String name, @RequestParam("alias") String alias, 
			@RequestParam("type") byte type, @RequestParam("unit") String unit,
			@RequestParam("modelId") long modelId, Model model) throws Exception{
		ModelVar modelVar = buildModelVar(name, alias, type, unit, modelId);
		

		return "";
	}

	@RequestMapping(value = "/var/mod")
	public String mod(@RequestParam("varId") long varId, @RequestParam("name") String name,
			@RequestParam("alias") String alias, @RequestParam("type") byte type, @RequestParam("unit") String unit,
			@RequestParam("modelId") long modelId, Model model) throws Exception{
		ModelVar modelVar = buildModelVar(name, alias, type, unit, modelId);
		modelVar.setVarId(varId);
		
		
		return "";
	}
	
	@RequestMapping(value = "/var/del")
	public String del(@RequestParam("varId") String varId, Model model) throws Exception{
		
		
		
		return "";
	}
	
	@RequestMapping(value = "/var/query")
	public String query(@RequestParam("modelName") String modelName, Model model) throws Exception{
		
		
		
		return "";
	}
	
	private ModelVar buildModelVar(String name, String alias, byte type, String unit, long modelId) {
		ModelVar var = new ModelVar();
		var.setVarName(name);
		var.setAlias(alias);
		var.setType(type);
		var.setUnit(unit);
		var.setModelId(modelId);
		return var;
	}
	
}
