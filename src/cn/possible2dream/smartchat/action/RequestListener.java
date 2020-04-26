package cn.possible2dream.smartchat.action;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@WebListener
public class RequestListener implements ServletRequestListener {

	//private static IStaffSV iStaffSV = StaffSVImpl.getInstance();
	
	public RequestListener() {
        // TODO Auto-generated constructor stub
    }
	
	/**
	 * 
	 * 我哭了啊，破案了啊，这个类也不需要了！暂且留着，以后再也不走捷径了啊，首先抓住主要的东西！！
	 * 为了一些细枝末节，耽误了多少时间啊！！！！乱去改tomcat下conf里的配置
	 * （为了访问主页直接进入该项目，结果出了些完全莫名其妙的错误！！）各次请求的session  静态变量不能共享！！！！fuck！
	 */
	@Override
	public void requestInitialized(ServletRequestEvent arg0) {
		//这个方法为无数次的被调用
		HttpSession httpSession = ((HttpServletRequest) arg0.getServletRequest()).getSession();
		if(null!=httpSession){
			//GetHttpSessionConfigurator.httpSession0 = httpSession;
			String code = arg0.getServletRequest().getParameter("username");
			//GetHttpSessionConfigurator.name0 = code;//是否ws建立连接请求时code也为空
			System.out.println("本次@WebListener请求,httpSession="+httpSession+",code="+code);
		}else{
			System.out.println("本次@WebListener请求,httpSession="+httpSession);
		}
	}
	
	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {

	}
	
	

}
