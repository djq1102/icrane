/**
 * 
 */
package com.monitor.app.controller.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monitor.app.controller.AbstractController;
import com.monitor.app.dataobject.Device;
import com.monitor.app.dataobject.ModelVar;
import com.monitor.app.dataobject.PlcModel;
import com.monitor.app.exception.ManagerException;
import com.monitor.app.query.PlcModelQuery;
import com.monitor.app.query.PlcModelVarQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.DeviceService;
import com.monitor.app.service.ModelService;
import com.monitor.app.service.VariableService;

/**
 * @author ibm
 * 
 */
@Controller
public class VariableController extends AbstractController {

	@Resource
	private VariableService variableService;

	@Resource
	private ModelService modelService;

	@Resource
	private DeviceService deviceService;

	@RequestMapping(value = "/plcvar/index")
	public String index(PlcModel plcModel, Model model) throws Exception {
		model.addAttribute("modelId", plcModel.getModelId());
		model.addAttribute("modelName", plcModel.getModelName());
		return "plcvar/plcvar";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/plcvar/queryModel", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryModel(@RequestParam("modelId") int modelId,
			@RequestParam("modelName") String modelName,
			@RequestParam("iDisplayStart") int start,
			@RequestParam("iDisplayLength") int pagesize,
			@RequestParam("sEcho") int sEcho, Model model) throws Exception {
		PlcModelQuery query = new PlcModelQuery();
		query.setModelId(modelId);
		query.setModelName(modelName);
		query.setBegin(start);
		query.setEnd(start + pagesize);

		ServiceResult dataResult = modelService.queryModels(query);
		ServiceResult countResult = modelService.totalCount(query);
		if (dataResult.isSuccess() && dataResult.getModule() != null
				&& countResult.isSuccess()) {
			List<PlcModel> plcModels = (List<PlcModel>) dataResult.getModule();
			List<Map> resultMap = buildList(plcModels);
			long total = Long.parseLong(countResult.getModule().toString());
			return outputJsonAsResponse(resultMap, total, sEcho);
		}

		return outputJsonAsResponse(Collections.EMPTY_LIST, 0, sEcho);
	}

	@RequestMapping(value = "/plcvar/updateModelVar")
	public String updateModelVar(@RequestParam("modelId") int modelId,
			Model model) throws Exception {

		variableService.updates(modelId);

		return "plcvar/showvar";
	}

	@RequestMapping(value = "/plcvar/queryModelVarIndex")
	public String queryModelVarIndex(ModelVar modelVar, Model model)
			throws Exception {
		model.addAttribute("modelId", modelVar.getModelId());
		model.addAttribute("varName", modelVar.getVarName());
		model.addAttribute("varId", modelVar.getVarId());
		return "plcvar/showvar";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/plcvar/queryModelVar", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryModelVar(@RequestParam("modelId") int modelId,
			@RequestParam("varId") int varId,
			@RequestParam("varName") String varName,
			@RequestParam("iDisplayStart") int start,
			@RequestParam("iDisplayLength") int pagesize,
			@RequestParam("sEcho") int sEcho, Model model) throws Exception {
		PlcModelVarQuery query = new PlcModelVarQuery();
		query.setModelId(modelId);
		query.setVarId(varId);
		query.setVarName(varName);
		query.setBegin(start);
		query.setEnd(start + pagesize);
		ServiceResult dataResult = variableService.queryPlcModelVar(query);
		ServiceResult countResult = variableService.totalCount(query);
		if (dataResult.isSuccess() && dataResult.getModule() != null
				&& countResult.isSuccess()) {
			List<ModelVar> modelVarList = (List<ModelVar>) dataResult
					.getModule();
			List<Map> resultMap = buildListVar(modelVarList);
			long total = Long.parseLong(countResult.getModule().toString());
			return outputJsonAsResponse(resultMap, total, sEcho);
		}

		return outputJsonAsResponse(Collections.EMPTY_LIST, 0, sEcho);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> buildList(List<PlcModel> modelList) {
		List<Map> result = new ArrayList<Map>();
		for (PlcModel plc : modelList) {
			Map map = new HashMap();
			map.put("0", plc.getModelId());
			map.put("1", plc.getModelName());
			map.put("2", plc.getSensorType());
			map.put("3", plc.getIoType());
			map.put("4", plc.getModelId());
			result.add(map);
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> buildListVar(List<ModelVar> modelVarList) {
		List<Map> result = new ArrayList<Map>();
		for (ModelVar modelVar : modelVarList) {
			Map map = new HashMap();
			map.put("0", modelVar.getModelId());
			map.put("1", modelVar.getVarId());
			map.put("2", modelVar.getVarName());
			map.put("3", modelVar.getType());
			map.put("4", modelVar.getUnit());
			result.add(map);
		}
		return result;
	}

	@RequestMapping(value = "/device/supervisory")
	public String supervisory(@RequestParam("deviceId") long deviceId,
			Model model) throws ManagerException {
		model.addAttribute("deviceId", deviceId);
		return "front/monitor/index";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/device/supervisoryValue", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public String supervisoryVaue(@RequestParam("deviceId") long deviceId,
			@RequestParam("showType") long showType, Model model)
			throws ManagerException {
		// TODO
		ServiceResult result = deviceService.queryByDeviceId(deviceId);
		if (result.isSuccess()) {
			Device device = (Device) result.getModule();
			Long modelId = device.getModelId();
			PlcModelVarQuery query = new PlcModelVarQuery();
			query.setModelId(modelId);
			ServiceResult dataResult = variableService.queryPlcModelVar(query);
			if (dataResult.isSuccess() && dataResult.getModule() != null) {
				List<ModelVar> modelVarList = (List<ModelVar>) dataResult
						.getModule();
				model.addAttribute("modelVarList", modelVarList);
			} else {
				model.addAttribute("msg", result.getMsg());
				return "error";
			}
		} else {
			model.addAttribute("msg", result.getMsg());
			return "error";
		}

		model.addAttribute("deviceId", deviceId);

		if (1 == showType) {
			return "front/monitor/monitor_1";
		} else if (2 == showType) {
			return "front/monitor/monitor_2";
		}
		return "front/monitor/index";
	}
}
