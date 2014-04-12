package com.monitor.app.controller.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.monitor.app.controller.AbstractController;
import com.monitor.app.dataobject.Customer;
import com.monitor.app.dataobject.Device;
import com.monitor.app.dataobject.PlcModel;
import com.monitor.app.dataobject.Site;
import com.monitor.app.exception.ManagerException;
import com.monitor.app.query.DeviceQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.AccessoryService;
import com.monitor.app.service.CustomerService;
import com.monitor.app.service.DeviceService;
import com.monitor.app.service.ModelService;
import com.monitor.app.service.SiteService;
import com.monitor.app.service.UserDeviceRelationService;
import com.monitor.app.utils.JsonUtil;
import com.monitor.app.utils.ToolsUtil;
import com.monitor.app.validator.DeviceValidator;


@Controller
public class DeviceController extends AbstractController{
	
	private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);
	@Resource
	private DeviceService deviceService;
	@Resource
	private CustomerService customerService;
	@Resource
	private UserDeviceRelationService userDeviceRelationService;
	@Resource
	private AccessoryService accessoryService;
	@Resource
	private SiteService siteService;
	@Resource
	private ModelService modelService;
	
	@InitBinder  
	public void initBinder(DataBinder binder) {  
	   binder.setValidator(new DeviceValidator());  
	}  
	
	@RequestMapping(value = "/device/addDevice")
	public String addDevice(@Valid @ModelAttribute("device")Device device,BindingResult bingResult,Model model) throws ManagerException {
		if(bingResult.hasFieldErrors()){
			if(bingResult.getFieldError("customerId") != null){
				model.addAttribute("errorCustomerId", bingResult.getFieldError("customerId").getDefaultMessage());
			}
			if(bingResult.getFieldError("siteId") != null){
				model.addAttribute("errorSiteId",bingResult.getFieldError("siteId").getDefaultMessage());
			}
			if(bingResult.getFieldError("deviceIp") != null){
				model.addAttribute("deviceErrorIp", bingResult.getFieldError("deviceIp").getDefaultMessage());
			}
			if(bingResult.getFieldError("deviceName") != null){
				model.addAttribute("deviceErrorName",bingResult.getFieldError("deviceName").getDefaultMessage());
			}
			if(bingResult.getFieldError("serialNumber") != null){
				model.addAttribute("serialErrorNumber",bingResult.getFieldError("serialNumber").getDefaultMessage());
			}
			if(bingResult.getFieldError("userName") != null){
				model.addAttribute("userErrorName", bingResult.getFieldError("userName").getDefaultMessage());
			}
			if(bingResult.getFieldError("userPwd") != null){
				model.addAttribute("userErrorPwd", bingResult.getFieldError("userPwd").getDefaultMessage());
			}
			if(bingResult.getFieldError("location") != null){
				model.addAttribute("errorLocation", bingResult.getFieldError("location").getDefaultMessage());
			}
			return deviceInput(model);
		}
		ToolsUtil.spiltLocation(device);
		ServiceResult result = deviceService.addDevice(device);
		if(!result.isSuccess()){
			logger.error(">>>action=addDevice error" + device.getDeviceName());
			model.addAttribute("msg", result.getMsg());
			return "error";
		}
		return "redirect:index.htm";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/device/queryDeviceList",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String queryDeviceList(@RequestParam("iDisplayStart") int start, @RequestParam("iDisplayLength") int pagesize, @RequestParam("sEcho") int sEcho,
			Device device) throws ManagerException {
		logger.warn(">>>action=queryDeviceList" + device.getDeviceName());
		DeviceQuery query = new DeviceQuery();
		query.setDeviceName(device.getDeviceName());
		query.setSiteId(device.getSiteId());
		query.setCustomerId(device.getCustomerId());
		query.setBegin(start);
		query.setEnd(start+pagesize);
		ServiceResult result = deviceService.queryAllDevice(query);
		ServiceResult numResult = deviceService.totalCount(query);
		List<Device> deviceList  = (List<Device>)result.getModule();
		List<Map> resultMap = buildList(deviceList);
		return JsonUtil.buildJosn(resultMap, numResult, sEcho);
	}
	
	@RequestMapping(value = "/device/index",method = {RequestMethod.POST,RequestMethod.GET})
	public String index(Model model,Device device) throws ManagerException{
		logger.warn(">>>action=index");
		model.addAttribute("customers", queryAllCustomers());
		model.addAttribute("deviceName", device.getDeviceName());
		model.addAttribute("customerId", device.getCustomerId());
		if(device.getCustomerId() != null){
			model.addAttribute("sites", querySitesByCustomerId(device.getCustomerId()));
		}
		model.addAttribute("siteId", device.getSiteId());
		return "device/device";
	}
	
	@RequestMapping(value = "/device/editDevice")
	public String editDevice(@RequestParam("deviceId") long deviceId,Model model) throws Exception{
		ServiceResult result = deviceService.queryByDeviceId(deviceId);
		if(result.isSuccess()){
			Device device  = (Device)result.getModule();
			String location = device.getLocation();
			String lat = "";
			String lng = "";
			if(location != null && location != ""){
				String [] array = location.split(",");
				if(array != null && array.length == 2){
					lat = array[0];
					lng = array[1];
				}
			}
			model.addAttribute("plcModels", queryAllModels());
			model.addAttribute("customers", queryAllCustomers());
			model.addAttribute("sites", querySitesByCustomerId(device.getCustomerId()));
			model.addAttribute("lng", lng);
			model.addAttribute("lat", lat);
			model.addAttribute("device", device);
			return "device/device_edit_input";
		}else{
			model.addAttribute("msg", result.getMsg());
			return "error";
		}
	}
	
	@RequestMapping(value = "/device/deleteDevice")
	public String deleteDevice(@RequestParam("deviceId") long deviceId,Model model) throws Exception{
		ServiceResult result = deviceService.deleteDevice(deviceId);
		if(result.isSuccess()){
			return "redirect:index.htm";
		}else{
			model.addAttribute("msg", result.getMsg());
			return "error";
		}
	}

	@RequestMapping(value = "/device/updateDevice")
	public String updateDevice(@Valid @ModelAttribute("device")Device device,BindingResult bingResult,Model model) throws Exception{
		ServiceResult result = deviceService.queryByDeviceId(device.getDeviceId());
		if(result.isSuccess()){
			if(bingResult.hasFieldErrors()){
				if(bingResult.getFieldError("customerId") != null){
					model.addAttribute("errorCustomerId", bingResult.getFieldError("customerId").getDefaultMessage());
				}
				if(bingResult.getFieldError("siteId") != null){
					model.addAttribute("errorSiteId",bingResult.getFieldError("siteId").getDefaultMessage());
				}
				if(bingResult.getFieldError("deviceIp") != null){
					model.addAttribute("deviceErrorIp", bingResult.getFieldError("deviceIp").getDefaultMessage());
				}
				if(bingResult.getFieldError("deviceName") != null){
					model.addAttribute("deviceErrorName",bingResult.getFieldError("deviceName").getDefaultMessage());
				}
				if(bingResult.getFieldError("serialNumber") != null){
					model.addAttribute("serialErrorNumber",bingResult.getFieldError("serialNumber").getDefaultMessage());
				}
				if(bingResult.getFieldError("userName") != null){
					model.addAttribute("userErrorName", bingResult.getFieldError("userName").getDefaultMessage());
				}
				if(bingResult.getFieldError("userPwd") != null){
					model.addAttribute("userErrorPwd", bingResult.getFieldError("userPwd").getDefaultMessage());
				}
				if(bingResult.getFieldError("location") != null){
					model.addAttribute("errorLocation", bingResult.getFieldError("location").getDefaultMessage());
				}				
				return editDevice(device.getDeviceId(), model);
			}
			ToolsUtil.spiltLocation(device);
			ServiceResult editResult = deviceService.updateDevice(device);
			if(!editResult.isSuccess()){
				logger.error(">>>action=updateDevice error" + device.getDeviceName());
				model.addAttribute("msg", editResult.getMsg());
				return "error";
			}
			return "redirect:index.htm";
		}else{
			logger.error(">>>action=queryByDeviceId error" + device.getDeviceId());
			model.addAttribute("error",result.getMsg() );
			return "error";
		}
	}
	
	@RequestMapping(value = "/device/deviceInput",method = RequestMethod.GET)
	public String deviceInput(Model model) throws ManagerException {
		logger.warn(">>>action=deviceInput");
		model.addAttribute("customers", queryAllCustomers());
		model.addAttribute("plcModels", queryAllModels());
		return "device/device_input";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> buildList(List<Device> deviceList){
		List<Map> result = new ArrayList<Map>();
		for(Device device:deviceList){
			Map map = new HashMap();
			map.put("0", device.getDeviceId());
			map.put("1", device.getCustomerId());
			map.put("2", device.getSiteId());
			map.put("3", device.getDeviceName());
			map.put("4", device.getModelId());
			map.put("5", device.getSerialNumber());
			map.put("6", device.getDeviceId());
			result.add(map);
		}
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Customer> queryAllCustomers() throws ManagerException{
		ServiceResult result = customerService.queryAllCustomers();
		List<Customer> customers = (List)result.getModule();
		return customers;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<PlcModel> queryAllModels(){
		ServiceResult result = modelService.queryAllModels();
		List<PlcModel> models =(List) result.getModule();
		return models;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Site> querySitesByCustomerId(long customerId) throws ManagerException{
		ServiceResult result = siteService.querySiteByCustomerId(customerId);
		List<Site> sites = (List)result.getModule();
		return sites;
	}
}
