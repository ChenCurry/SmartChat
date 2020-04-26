package cn.possible2dream.smartchat.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.possible2dream.smartchat.entity.MessageToFore;
import cn.possible2dream.smartchat.service.MessageSVImp;
import cn.possible2dream.smartchat.service.UploadFileSV;
import cn.possible2dream.smartchat.serviceinter.IMessageSV;

/**
 * 确认修改头像
 *
 */
@WebServlet("/vcardServlet")
public class VcardServlet extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
	UploadFileSV uploadFileSV = new UploadFileSV();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VcardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        //"id":staffId,
		//"headUrl":head_url_new
        /**
         * "id":staffId,
		   "headUrl":head_url_new
         */
        //String body = request.getParameter("body");
        //System.out.println("update Head");
        if(null != request.getParameter("body")){
        	//xiu gai tou xiang
        	uploadFileSV.updateHead(request, response);
        }else{
        	//xiu gai mi ma 
        	uploadFileSV.updatePassword(request, response);
        }
        
		
        
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	

}
