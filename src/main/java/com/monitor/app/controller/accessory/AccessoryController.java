/**
 * 
 */
package com.monitor.app.controller.accessory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.monitor.app.controller.AbstractController;
import com.monitor.app.dataobject.ModelAccessory;
import com.monitor.app.query.ModelAccessoryQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.AccessoryService;
import com.monitor.app.utils.MoneyUtil;
import com.monitor.app.utils.MsgUtils;

/**
 * @author ibm
 *
 */
@Controller
public class AccessoryController extends AbstractController implements ServletContextAware{

	private static final String SEP = System.getProperty("file.separator");
	private static final String uploadPath="WEB-INF"+SEP+"upload"+SEP+"acc"+SEP;
	
	@Resource
	private AccessoryService accessoryService;
	
	@RequestMapping(value = "/acc/index")
	public String index( ModelAccessory accessory,Model model) throws Exception{
		
		model.addAttribute("accessoryId", accessory.getAccessoryId());
		model.addAttribute("accessoryName", accessory.getAccessoryName());
		return "accessory/accessory";
	}
	
	@RequestMapping(value = "/acc/toAdd")
	public String toAdd(Model model) throws Exception{
		
		return "accessory/add";
	}
	
	@RequestMapping(value = "/acc/add")
	public String add(@RequestParam("accessoryName") String name,@RequestParam("accessoryType") int accessoryType,  @RequestParam("accessoryPrice") String price, 
			@RequestParam("fileName") String fileName, @RequestParam("filePath") String filePath, Model model) throws Exception{
		
		//1.上传图片后修改filename，filepath
		ModelAccessory acc = buildModelAccessory(name, accessoryType, price, fileName, filePath);
		ServiceResult result = accessoryService.addAccessory(acc);
		
		if(result.isSuccess()){
			model.addAttribute("msg", MsgUtils.MSG_SUCCESS);
		}else{
			model.addAttribute("msg",result.getMsg());
		}
		return "accessory/accessory";
	}

	private ModelAccessory buildModelAccessory(String name, int accessoryType, String price,String fileName, String filePath) {
		ModelAccessory acc= new ModelAccessory();
		acc.setAccessoryName(name);
		acc.setAccessoryType(accessoryType);
		acc.setAccessoryPrice(MoneyUtil.parseCent4Money(price));
		acc.setFileName(fileName);
		acc.setFilePath(filePath);
		return acc;
	}

	@RequestMapping(value = "/acc/query",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
	@ResponseBody
	public String query(@RequestParam("accessoryId") int accessoryId, @RequestParam("accessoryName") String accessoryName,
			@RequestParam("iDisplayStart") int start, @RequestParam("iDisplayLength") int pagesize,
			@RequestParam("sEcho") int sEcho, Model model) throws Exception{
		
		ModelAccessoryQuery query = new ModelAccessoryQuery();
		query.setAccessoryId(accessoryId);
		query.setAccessoryName(accessoryName);
		query.setBegin(start);
		query.setEnd(start+pagesize);
		
		ServiceResult dataResult = accessoryService.queryAccessorys(query);
		ServiceResult countResult = accessoryService.totalCount(query);
		if(dataResult.isSuccess() && dataResult.getModule()!=null&& countResult.isSuccess()){
			List<ModelAccessory> accs = (List<ModelAccessory>)dataResult.getModule();
			List<Map> resultMap = buildList(accs);
			long total = Long.parseLong(countResult.getModule().toString());
			return outputJsonAsResponse(resultMap, total , sEcho);
		}
		
		return outputJsonAsResponse(Collections.EMPTY_LIST, 0 , sEcho);
	}
	
	private ServletContext servletContext; 
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@RequestMapping(value = "/acc/upload",method = RequestMethod.POST,produces = "text/html; charset=utf-8")
	@ResponseBody
	public String upload(@RequestParam("selectFile") CommonsMultipartFile mFile) throws Exception{
		
		//1.上传
		FileItem fi = mFile.getFileItem();
		String fileName = fi.getName();
		
		String filePath = buildFilePath();
		File targetPath = new File(filePath);  
        if(!targetPath.exists()){  
        	targetPath.mkdirs();  
        }  
        
		//2.保存 file
        File picFile = new File(filePath, fileName);
        if(picFile.exists()){
        	Random rnd = new Random();
    		int flag = rnd.nextInt(100)+1;
    		
    		int suffixIdx  = fileName.lastIndexOf(".");
    		if(suffixIdx!=-1){
    			String name = fileName.substring(0,suffixIdx);
    			String suffix = fileName.substring(suffixIdx,fileName.length());
    			fileName = name+"_"+ flag+suffix;
    		}else{
    			fileName = fileName+"_"+ flag;
    		}
        }
        
        //3.
        File targetFile = new File(filePath, fileName);  
        try {  
        	mFile.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
		
        String callback="<script type='text/javascript'>window.top.callback('"+fileName+"')</script>";
        
		return callback;
	}
	
	private String buildFilePath() {
		String rootPath = this.servletContext.getRealPath("/");
		String filePath = rootPath + SEP + uploadPath;
		return filePath;
	}
	
	@RequestMapping(value = "/acc/update")
	public String update(@RequestParam("accessoryId") long accessoryId, @RequestParam("accessoryName") String accessoryName,
			@RequestParam("accessoryType") int accessoryType,@RequestParam("accessoryPrice") String accessoryPrice,@RequestParam("fileName") String fileName,
			@RequestParam("filePath") String filePath, 
			Model model) throws Exception{
		//1.判断是否有修改过图片
		ModelAccessory acc = buildModelAccessory(accessoryName,accessoryType,accessoryPrice, fileName, filePath);
		acc.setAccessoryId(accessoryId);
		
		ServiceResult result = accessoryService.updateAccessory(acc);
		if(result.isSuccess()){
			model.addAttribute("msg", MsgUtils.MSG_SUCCESS);
		}else{
			model.addAttribute("msg",MsgUtils.MSG_FAIL);
		}
		return "accessory/accessory";
	}
	
	@RequestMapping(value = "/acc/edit")
	public String edit(@RequestParam("accessoryId") int accessoryId, Model model) throws Exception{
		ServiceResult result = accessoryService.queryAccessory(accessoryId);
		if(!result.isSuccess()){
			model.addAttribute("msg", result.getMsg());
			return "accessory/accessory";
		}
		
		model.addAttribute("accessory", result.getModule());
		return "accessory/edit";
	}
	
	@RequestMapping(value = "/acc/del")
	public String del(@RequestParam("accessoryId") int accessoryId, Model model) throws Exception{
		ServiceResult result = accessoryService.delAccessory(accessoryId);
		if(!result.isSuccess()){
			model.addAttribute("msg", result.getMsg());
		}
		
		return "accessory/accessory";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map> buildList(List<ModelAccessory> accList){
		List<Map> result = new ArrayList<Map>();
		for(ModelAccessory acc:accList){
			Map map = new HashMap();
			map.put("0", acc.getAccessoryId());
			map.put("1", acc.getAccessoryName());
			map.put("2", MoneyUtil.formatLong4Money(acc.getAccessoryPrice()));
			map.put("3", acc.getFileName());
			map.put("4", acc.getFilePath());
			map.put("5", acc.getAccessoryId());
			result.add(map);
		}
		return result;
	}
}
