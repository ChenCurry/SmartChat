package cn.possible2dream.smartchat.util;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
 
public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator
{
	//public static HttpSession httpSession0;//来自前台servlet请求中不为空的一次
	//public static String name0;
    @Override
    public void modifyHandshake(ServerEndpointConfig config, 
                                HandshakeRequest request, 
                                HandshakeResponse response)
    {
        HttpSession httpSession = (HttpSession)request.getHttpSession();
        if(null==httpSession){//先不考虑安全问题   &&name0.equals(request.)
        	//留下这个解决问题的方式吧，虽然这个分支没啥用
//        	httpSession = httpSession0;
//        	System.out.println("WS:GetHttpSessionConfigurator获取不到httpSession，先不考虑安全问题，直接给它一个:"+httpSession.toString());
//        	httpSession0 = null;
//        	name0 = null;
        }else{
        	System.out.println("WS:GetHttpSessionConfigurator获取到httpSession，没毛病");
        	if(null!=httpSession.getAttribute("name")){
        		System.out.println("httpSession:"+httpSession.getAttribute("name").toString());
        	}else{
        		System.out.println("httpSession中没有name属性");
        	}
        	if(null!=httpSession.getAttribute("staffId")){
        		System.out.println("httpSession:"+httpSession.getAttribute("staffId").toString());
        	}else{
        		System.out.println("httpSession中没有staffId属性");
        	}
        }
        config.getUserProperties().put(HttpSession.class.getName(),httpSession);
        
    }
}