package controller;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import model.Person;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/test")
@Controller
public class TestController {	
	
	@RequestMapping("/hello")
	public ModelAndView hello()
	{
		ModelAndView mv=new ModelAndView("hello");
		System.out.print("hello2");
		mv.addObject("name", "123");
		return mv;
	}
	
	@RequestMapping("/testapi")
	public ModelAndView testapi() throws UnsupportedOperationException, Exception{
		ModelAndView mv=new ModelAndView("testhbs");		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		//http://openapi.ctrip.com/Hotel/OTA_Ping.asmx
		HttpPost httpPost = new HttpPost("http://openapi.ctrip.com/Hotel/OTA_Ping.asmx");		
		StringBuffer sb=new StringBuffer();
		StringBuffer sbBody=new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><Request xmlns=\"http://ctrip.com/\"><requestXML>");
		sbBody.append("<Request>");
		//		<!--AllianceID:分销商ID;SID:站点ID;TimeStamp:响应时间戳（从1970年到现在的秒数）;RequestType:请求接口的类型;Signature:MD5加密串-->
		sbBody.append("<Header AllianceID=\"1\" SID=\"2\" TimeStamp=\"1450267286\" Signature=\"8E1AC0EA7E64A9D5BD869B61203DA678\" RequestType=\"OTA_Ping\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\" />");				
		sbBody.append("<HotelRequest>");
		sbBody.append("<RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");
		sbBody.append("<ns:OTA_PingRQ>");
		//		<!--测试文本：string类型-->
		sbBody.append("<ns:EchoData>阿什顿</ns:EchoData>");				
		sbBody.append("</ns:OTA_PingRQ>");
		sbBody.append("</RequestBody>");
		sbBody.append("</HotelRequest>");
		sbBody.append("</Request>");
		sb.append(StringEscapeUtils.escapeXml(sbBody.toString()));
		sb.append("</requestXML></Request></soap:Body></soap:Envelope>");
		HttpEntity inputEntity=new StringEntity(sb.toString());		
		httpPost.setEntity(inputEntity);
		RequestConfig requestConfig = RequestConfig.custom()
	            .setSocketTimeout(1000)
	            .setConnectTimeout(1000)
	            .build();		
		httpPost.setConfig(requestConfig);
		httpPost.addHeader("Content-Type", "text/xml; charset=utf-8");
		CloseableHttpResponse response2 = httpclient.execute(httpPost);
		HttpEntity he= response2.getEntity();
		StatusLine sl= response2.getStatusLine();
		String s=InputStreamTOString(he.getContent());

		Person p=new Person();
		p.setName(s);
		p.setAge(34);		
		mv.addObject("p", p);
		return mv;
	}
	
	final static int BUFFER_SIZE = 4096;  
	public static String InputStreamTOString(InputStream in) throws Exception{  
        
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] data = new byte[BUFFER_SIZE];  
        int count = -1;  
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
            outStream.write(data, 0, count);  
          
        data = null;  
        return new String(outStream.toByteArray(),"utf-8");  
    }  
	
	@RequestMapping("/handlebars")
	public ModelAndView handlebars() throws IOException{
		ModelAndView mv=new ModelAndView("testhbs");
		Person p=new Person();
		p.setName("Soros");
		p.setAge(34);		
		mv.addObject("p", p);
		return mv;
	}
}