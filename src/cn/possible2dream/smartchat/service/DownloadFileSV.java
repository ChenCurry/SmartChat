package cn.possible2dream.smartchat.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.possible2dream.smartchat.dao.SharedFileDao;
import cn.possible2dream.smartchat.entity.SharedFile;
import cn.possible2dream.smartchat.util.Constant;

public class DownloadFileSV {
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	SharedFileDao sharedFileDao = new SharedFileDao();
	//String DIR_DATAOUT_MAIN = "D:\\dev_workspace\\webSocket\\SmartChat\\WebContent\\files\\";
	
	public DownloadFileSV() {
	}
	public DownloadFileSV(HttpServletRequest request,HttpServletResponse response){
		this.request = request;
		this.response = response;
	}
	
	public boolean downloadFile() throws IOException{
		//获取fileId
		String fileId = request.getParameter("fileId").trim();
		//通过fileId查找文件信息
		SharedFile sharedFile = sharedFileDao.selectOne(Long.parseLong(fileId));
		
		response.sendRedirect(sharedFile.getBackName());//tupian de hua hui xin kai yi ge ye mian 
		
        
        //==============xia mian shi liyuxin yuan lai de dai ma ===================
		/*
		 * //获取响应的输出流
		ServletOutputStream outputStream = response.getOutputStream();
		 //处理请求参数
        File fileToDownload=null;
        fileToDownload=new File(sharedFile.getBackName());
        //响应下载请求
        if (fileToDownload!=null)
        {
        	//ju dui lu jing 
        	//C:\download\eclipse-jee-luna-SR2-win32-x86_64\eclipse\http:\xxx.xxx.xxx.xxx:8888\group1\M00\00\00\aksgpl6Bh0OABl2AAACVnpqq9O8160.jpg
            String fileSavePath=fileToDownload.getAbsolutePath();
            System.out.println("将到这里查找文件:" + fileSavePath);
            
            if (!fileToDownload.exists())
            {
            	outputStream.print("File not found");
                System.out.println("文件不存在!");
            }else {
             // 读取文件流
                FileInputStream fis = new FileInputStream(fileToDownload);
                // 下载文件
                // 设置响应头和下载保存的文件名
                if (fileSavePath != null && fileSavePath.length() > 0)
                {
                    // 设置响应为下载文件
                    response.setContentType("application/x-msdownload");
                    // 设置响应文件名为路径
                    response.setHeader("Content-Disposition","attachment; filename=" + new String(sharedFile.getForeName().getBytes("utf-8"), "ISO-8859-1").trim());
                    if (fis != null)
                    {
                        int filelen = fis.available();
                        // 文件太大时内存不能一次读出,要循环
                        byte a[] = new byte[filelen];
                        fis.read(a);
                        outputStream.write(a);
                    }
                    fis.close();
                    outputStream.close();
                    System.out.println("文件下载成功");
                }
            }
        }*/
        
		return true;
	}
}
