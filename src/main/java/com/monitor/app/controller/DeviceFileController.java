/**
 * 
 */
package com.monitor.app.controller;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.monitor.app.dataobject.DeviceDoc;
import com.monitor.app.query.DeviceDocQuery;
import com.monitor.app.result.ServiceResult;
import com.monitor.app.service.AccessoryService;
import com.monitor.app.service.DeviceDocService;

/**
 * @author ibm
 *
 */
@Controller
public class DeviceFileController extends AbstractController implements ServletContextAware{

	
	private static final String uploadPath="WEB-INF/upload/doc";
	private static final String SEP = System.getProperty("file.separator");
	
	private ServletContext servletContext; 
	
	@Resource
	private AccessoryService accessoryService;
	
	@Resource
	private DeviceDocService deviceDocService;
	
	@RequestMapping(value = "/doc/{deviceId}")
	public String index( @PathVariable("deviceId") long deviceId,Model model) throws Exception{
		
		DeviceDocQuery query = new DeviceDocQuery();
		query.setDeviceId(deviceId);
		ServiceResult result = deviceDocService.queryDocs(query);
		if(!result.isSuccess()){
			model.addAttribute("msg", result.getMsg());
			return "error";
		}
		
		//1.查询所有文档。。
		model.addAttribute("deviceId", deviceId);
		model.addAttribute("deviceDocs", result.getModule());
		
		return "devicefile/device_file";
	}
	
	@RequestMapping(value = "/doc/upload/{deviceId}", method = RequestMethod.POST)
	public String upload(@PathVariable("deviceId") long deviceId, 
			 @RequestParam("file") CommonsMultipartFile mFile,
			 HttpServletRequest req, Model model) throws Exception{
		//1.上传
		FileItem fi = mFile.getFileItem();
		String fileName = fi.getName();
		String rootPath = this.servletContext.getRealPath("/");
		String filePath = rootPath + SEP + uploadPath+ SEP+deviceId+SEP;
		File targetFile = new File(filePath, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
		//2.保存 file
        try {  
        	mFile.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
		
        //3.save information to db
        DeviceDoc doc = new DeviceDoc();
        doc.setDocName(fileName);
        doc.setStatus((byte)0);
        doc.setDeviceId(deviceId);
        ServiceResult result = deviceDocService.saveDoc(doc);
        if(!result.isSuccess()){
			model.addAttribute("msg", result.getMsg());
			return "error";
		}
        
		return "redirect:/doc/"+deviceId;
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@RequestMapping(value = "/doc/del/{deviceId}/{docId}")
	public String del(@PathVariable("deviceId") long deviceId, 
			@PathVariable("docId") int docId, Model model) throws Exception{
		ServiceResult result = deviceDocService.delDoc(docId);
		if(!result.isSuccess()){
			model.addAttribute("msg", result.getMsg());
			return "error";
		}
		
		return "redirect:/doc/"+deviceId;
	}
	
}
