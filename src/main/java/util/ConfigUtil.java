package util;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;


public class ConfigUtil {
	public static String getContextParamValueByName(String paramName)
	{	
		try
		{
			XMLConfiguration config=new XMLConfiguration("../web.xml");
			config.setExpressionEngine(new XPathExpressionEngine());
			String value=config.getString("context-param[param-name = '"+paramName+"']/param-value");
			return value;
		}
		catch(Exception ex)
		{
			LogUtil.error("ConfigUtil.getContextParamValueByName", ex);
		}
		return "";
	}
}
