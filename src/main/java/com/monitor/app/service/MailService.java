/**
 * 
 */
package com.monitor.app.service;

import java.util.List;

import com.monitor.app.dataobject.helper.AccBuyInfo;
import com.monitor.app.mail.MailSenderInfo;
import com.monitor.app.mail.SimpleMailSender;

/**
 * @author ibm
 *
 */
public class MailService {

	private String mailServer;
	private String mailServerPort;
	private String mailUserName;
	private String mailUserPass;
	private String mailFromAddress;
	private String mailToAddress;
	
	public boolean sendBuyAccMail(String userNick, List<AccBuyInfo> accBuyInfoList){
		
		  MailSenderInfo mailInfo = new MailSenderInfo();    
	      mailInfo.setMailServerHost(mailServer);    
	      mailInfo.setMailServerPort(mailServerPort);    
	      mailInfo.setValidate(true);    
	      mailInfo.setUserName(mailUserName);    
	      mailInfo.setPassword(mailUserPass);//您的邮箱密码    
	      mailInfo.setFromAddress(mailFromAddress);    
	      mailInfo.setToAddress(mailToAddress);    
	      
	      mailInfo.setSubject("申请配件购买列表，来自"+userNick); 
	      
	      StringBuilder content = new StringBuilder();
	      content.append("<html><body><table border='1'>");
	      content.append("<tr><td>配件编号</td><td>配件名称</td></tr>");
	      for(AccBuyInfo acc : accBuyInfoList){
	    	  content.append("<tr><td>");
	    	  content.append(acc.getAccId());
	    	  content.append("</td><td>");
	    	  content.append(acc.getAccName());
	    	  content.append("</td></tr>");
	      }
	      content.append("</table></body></html>");
	      mailInfo.setContent(content.toString());    
	     
	      boolean result = SimpleMailSender.sendHtmlMail(mailInfo);
	      
	      return result;
	}
	
	public static void main(String[] args){
		//sendBuyAccMail();
	}
	
	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}
	
	public void setMailUserName(String mailUserName) {
		this.mailUserName = mailUserName;
	}

	public void setMailUserPass(String mailUserPass) {
		this.mailUserPass = mailUserPass;
	}

	public void setMailFromAddress(String mailFromAddress) {
		this.mailFromAddress = mailFromAddress;
	}

	public void setMailToAddress(String mailToAddress) {
		this.mailToAddress = mailToAddress;
	}

	public void setMailServer(String mailServer) {
		this.mailServer = mailServer;
	}
}
