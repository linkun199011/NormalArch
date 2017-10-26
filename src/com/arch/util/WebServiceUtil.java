package com.arch.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.arch.beans.Project;

@SuppressWarnings("serial")
public class WebServiceUtil implements Serializable{
	
	private static WebServiceUtil webservice = null;
	
	private static final String servConf = "server-config.properties";	
	private Properties prop = new Properties();
	private String pmURL = null;
	private String pmNamespace = null;

	/**
	 * 实例化
	 * @throws Exception
	 */
	private WebServiceUtil() throws Exception{
		
		URL url = SSOUtil.class.getClassLoader().getResource(servConf);
		String path = url.getPath();
		
		InputStream in = new BufferedInputStream(new FileInputStream(path));
		prop.load(in);

		LogUtil.info(WebServiceUtil.class, "加载prop,"+prop);
		
		pmURL = prop.getProperty("pmURL");
		pmNamespace = prop.getProperty("pmNamespace");
		
	}
	
	public static WebServiceUtil getInstance() throws Exception{


		LogUtil.info(SSOUtil.class, "WebServiceUtil.gentInstance() called……");
		
		if(webservice == null){
			return new WebServiceUtil();
		}
		return webservice;
	}
	
	public List<Project> getProjectListByUsername(String username) {

		LogUtil.info(WebServiceUtil.class,
				"getProjectListByUsername called...username:" + username);

		try {
			ServiceClient sc = new ServiceClient();
			Options opts = new Options();
			EndpointReference end = new EndpointReference(pmURL);
			opts.setTo(end);
			opts.setTimeOutInMilliSeconds(1000 * 15);// 超时15秒
			// opts.setAction("http://server.webservice.april.com/sayHello");
			sc.setOptions(opts);

			OMFactory fac = OMAbstractFactory.getOMFactory();
			OMNamespace namespace = fac.createOMNamespace(
					pmNamespace,
					"");

			OMElement method = fac.createOMElement("synchProjectInfo",
					namespace);

			OMElement systemIdParam = fac
					.createOMElement("systemId", namespace);
			OMElement systemNameParam = fac.createOMElement("systemName",
					namespace);
			OMElement sendTimeParam = fac
					.createOMElement("sendTime", namespace);
			OMElement loginIdParam = fac.createOMElement("loginId", namespace);
			OMElement md5Param = fac.createOMElement("md5", namespace);

			loginIdParam.addChild(fac.createOMText(loginIdParam, username));

			method.addChild(systemIdParam);
			method.addChild(systemNameParam);
			method.addChild(sendTimeParam);
			method.addChild(loginIdParam);
			method.addChild(md5Param);

			LogUtil.info(WebServiceUtil.class, "PMS webservice called...xml:"
					+ method);
			OMElement res = sc.sendReceive(method);

			List<Project> projLst = projXMLRead(res.getFirstElement().getText());

			LogUtil.info(WebServiceUtil.class,
					"getProjectListByUsername complete...");

			return projLst;
		} catch (Exception e) {
			LogUtil.error(WebServiceUtil.class,
					"getProjectListByUsername failed..."+e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}

	private List<Project> projXMLRead(String xml) {

		LogUtil.info(WebServiceUtil.class, "projXMLRead called...");

		LogUtil.debug(WebServiceUtil.class, "PMS webservice resp...xml:" + xml);
		
//		xml = xml.replace("&", "-");

		List<Project> tmpList = new ArrayList<Project>();
		try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			@SuppressWarnings("unchecked")
			List<Element> projectInfoLst = root.elements();
			LogUtil.info(WebServiceUtil.class, "项目总数：" + projectInfoLst.size());
			for (Element proj : projectInfoLst) {

				Project tmp = new Project();

				String projNo = proj.element("projNo").getTextTrim();
				String projName = proj.element("projName").getTextTrim();
				String incrementName = proj.element("incrementName").getTextTrim();
				
				if(incrementName.contains(projName)){
					// 原有项目，增量名与项目名相同
					projName = incrementName;
				}else{
					projName = projName + "-" + incrementName;
				}
				
				String pmName = proj.element("pmName").getTextTrim();
				String bmName = proj.element("businessName").getTextTrim();
				String techMName = proj.element("developerName").getTextTrim();
				String testMName = proj.element("testName").getTextTrim();
				String currentStatus = proj.element("currentStatus")
						.getTextTrim();

				LogUtil.debug(WebServiceUtil.class, "proj Name:" + projName+", status:"+currentStatus);

					//LogUtil.info(WebServiceUtil.class, "proj Name:" + projName+", status:"+currentStatus);
					tmp.setProjectNo(projNo);
					tmp.setProjectName(projName);
					tmp.setProjectManager(pmName);
					tmp.setTechManager(techMName);
					tmp.setBusiManager(bmName);
					tmp.setTestManager(testMName);

					tmpList.add(tmp);
				

			}

		} catch (Exception e) {
			LogUtil.error(WebServiceUtil.class,
					"projXMLRead failed..."+xml+","+e.getLocalizedMessage());
			e.printStackTrace();
		}

		return tmpList;
	}
	
}
