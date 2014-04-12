/**
 * 
 */
package com.monitor.app.controller.device;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.monitor.app.controller.AbstractController;
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
public class FrontDeviceFileController extends AbstractController implements ServletContextAware{

	
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
		String filePath = buildFilePath(deviceId);
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

	private String buildFilePath(long deviceId) {
		String rootPath = this.servletContext.getRealPath("/");
		String filePath = rootPath + SEP + uploadPath+ SEP+deviceId+SEP;
		return filePath;
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
	
	@RequestMapping(value = "/doc/download/{docId}")
	public void download(@PathVariable("docId") int docId,HttpServletResponse response, Model model) throws Exception{
		//1.查询文档对象
		ServiceResult result = deviceDocService.queryDocById(docId);
		if(!result.isSuccess()){
			model.addAttribute("msg","文档不存在");
			return ;
		}
		
		//2.文件路径。。下载
		DeviceDoc doc = (DeviceDoc)result.getModule();
		long deviceId = doc.getDeviceId();
		String fileName =doc.getDocName();
		String filePath = buildFilePath(deviceId);
		File targetFile = new File(filePath, fileName);  
        if(!targetFile.exists()){  
        	model.addAttribute("msg","文档不存在");
			return ;
        }  
		
        response.setCharacterEncoding("utf-8");    
        response.setContentType("multipart/form-data");    
        response.setHeader("Content-Disposition", "attachment;fileName="+fileName);
        try {    
            InputStream inputStream=new FileInputStream(targetFile);    
            OutputStream os=response.getOutputStream();    
            byte[] b=new byte[1024];    
            int length;    
            while((length=inputStream.read(b))>0){    
                os.write(b,0,length);    
            }    
            inputStream.close();    
        } catch (FileNotFoundException e) {    
            e.printStackTrace();    
        } catch (IOException e) {    
            e.printStackTrace();    
        }    
	}
}
