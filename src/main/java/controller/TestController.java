package controller;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import model.Person;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
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
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<Request>");
		//		<!--AllianceID:分销商ID;SID:站点ID;TimeStamp:响应时间戳（从1970年到现在的秒数）;RequestType:请求接口的类型;Signature:MD5加密串-->
		sb.append("<Header  AllianceID=\"x\" SID=\"xx\" TimeStamp=\"xxxxxx\"  RequestType=\" OTA_Ping \" Signature=\"xxxxxxx\" />");				
		sb.append("<HotelRequest>");
		sb.append("<RequestBody xmlns:ns=\"http://www.opentravel.org/OTA/2003/05\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");
		sb.append("<ns:OTA_PingRQ>");
		//		<!--测试文本：string类型-->
		sb.append("<ns:EchoData>阿什顿</ns:EchoData>");				
		sb.append("</ns:OTA_PingRQ>");
		sb.append("</RequestBody>");
		sb.append("</HotelRequest>");
		sb.append("</Request>");
		HttpEntity inputEntity=new StringEntity(sb.toString());		
		httpPost.setEntity(inputEntity);
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
        return new String(outStream.toByteArray());  
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