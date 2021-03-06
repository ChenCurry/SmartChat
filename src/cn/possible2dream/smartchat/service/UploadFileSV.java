package cn.possible2dream.smartchat.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import cn.possible2dream.smartchat.dao.SharedFileDao;
import cn.possible2dream.smartchat.dao.StaffDao;
import cn.possible2dream.smartchat.entity.SharedFile;
import cn.possible2dream.smartchat.entity.Staff;
import cn.possible2dream.smartchat.util.Constant;
import cn.possible2dream.smartchat.util.FastDFSClient;
import cn.possible2dream.smartchat.util.LogManager;

import com.google.gson.Gson;

public class UploadFileSV {

	SharedFileDao dao = new SharedFileDao();
	StaffDao staffDao = new StaffDao();

	String uploadFilePath = Constant.SHAREFLRE_LOCATION;

	String imagePath = Constant.IMG_PATH;

	// String imagePath =
	// "D:\\JAVA\\apache-tomcat-7.0.67\\webapps\\SmartChat\\";
	/**
	 * 收到上传文件请求时的处理方法
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @return false:上传失败 true:上传成功
	 */
	public boolean uploadFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		InputStream is = null;
		FileItem fileItem = getFileItemFromReqest(request, response);
		if (null == fileItem) {
			return false;
		}
		String fileName = fileItem.getName();
		fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
		// 判断是否有文件
		if (fileName == null || fileName.equals("")) {
			response.getWriter().write("{\"res\": \"请选择要上传的文件！\"}");
			return false;
		}

		try {
			is = fileItem.getInputStream();
		} catch (IOException e) {
			System.out.println("failed to get inputStream from fileItem");
			// e.printStackTrace();
			LogManager.getLogger(getClass()).log(Level.WARNING,
					"failed to get inputStream from fileItem", e);
		}
		// 获取文件类型
		String fileType = fileName.substring(fileName.lastIndexOf("."));
//		// 获取上传者的姓名
		String staff_name = URLDecoder.decode(request.getParameter("staff_name"), "utf-8");
//		// 获取当前时间的md5码，作为文件名
//		SimpleDateFormat formatBuilder = new SimpleDateFormat(
//				"yyyy/MM/dd HH:mm:ss");
//		String simpleDateFormat = formatBuilder.format(new Date());
//		String md5 = MD5Util.getMD5String(simpleDateFormat);
//		// 上传文件到指定路径
//		String relativePath = staff_name + md5 + fileType;
//		String filePath = uploadFilePath + relativePath;
//		File file = new File(filePath);
//		if (saveFile(is, file)) {
//			SharedFile sharedFile = new SharedFile(staff_name, new Date(),
//					fileType, fileName, relativePath);
//			dao.insertRecord(sharedFile);
//		}
//		response.getWriter().write(
//				"{\"res\": \"上传成功！\",\"file_name\":\"" + fileName + "\"}");
//		return true;
		
		// ===============上面是原来的处理方式=========

		// ===============下面是用文件服务器的改造=======

		// 获取文件扩展名
		String file_ext_name = fileName.substring(fileName.lastIndexOf(".")+1);

		String place = FastDFSClient.uploadFile(is, file_ext_name);
		if(null != place){
			String filePath = uploadFilePath + place;
			//cha biao
			System.out.println(filePath);
			SharedFile sharedFile = new SharedFile(staff_name, new Date(),fileType, fileName, filePath);
			dao.insertRecord(sharedFile);
			//String ImgUrl = place.replace("\\", "/");
			//String reString = "{\"url\":\"" + filePath + "\"}";
			response.getWriter().write("{\"res\": \"上传成功！\",\"file_name\":\"" + fileName + "\"}");
			return true;
		}
		response.getWriter().write("{\"res\": \"上传faild！\",\"file_name\":\"" + fileName + "\"}");
		return false;
	}

	/**
	 * 收到聊天图片后的处理逻辑
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void sendImage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		FileItem fileItem = getFileItemFromReqest(request, response);
		if (fileItem == null) {
			return;
		}
		String fileName = fileItem.getName();
		try {
			is = fileItem.getInputStream();
		} catch (IOException e) {
			System.out.println("fail to get inputStream from fileItem");
			// e.printStackTrace();
			LogManager.getLogger(getClass()).log(Level.WARNING,
					"failed to get inputStream from fileItem", e);
		}

		// 获取文件类型
		//String fileType = fileName.substring(fileName.lastIndexOf("."));
		// 获取文件扩展名
		String file_ext_name = fileName.substring(fileName.lastIndexOf(".")+1);

		// 将输入流缓存，这样便可以重复读取
		baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = is.read(buffer)) > -1) {
			baos.write(buffer, 0, len);
		}
		baos.flush();

		// 获取md5码
		// InputStream stream1 = new ByteArrayInputStream(baos.toByteArray());
		// String md5 = null;
		// try {
		// md5 = MD5Util.getMD5Byinput(stream1);
		// } catch (NoSuchAlgorithmException e) {
		// // TODO Auto-generated catch block
		// //e.printStackTrace();
		// LogManager.getLogger(getClass()).log(Level.WARNING,"MD5 加密文件失败",e);
		// }finally{
		// stream1.close();
		// }
		//
		// //保存文件
		// InputStream stream2 = new ByteArrayInputStream(baos.toByteArray());
		// String relativePath = "img\\messageImg\\" +md5+ fileType;
		// String filePath = imagePath + relativePath;
		// File file = new File(filePath);
		// saveFile(stream2, file);
		//
		// //将图片url返回给前台
		// //返回数据
		// String ImgUrl = relativePath.replace("\\", "/");
		// String reString = "{\"url\":\""+ImgUrl+"\"}";
		// response.getWriter().write(reString);

		// ===============上面是原来的处理方式=======

		// ===============下面是用文件服务器的改造=======
		InputStream stream1 = new ByteArrayInputStream(baos.toByteArray());
		String place = FastDFSClient.uploadFile(stream1, file_ext_name);
		if(null != place){
			// 将图片url返回给前台
			// 返回数据
			String filePath = imagePath + place;
			//String ImgUrl = place.replace("\\", "/");
			String reString = "{\"url\":\"" + filePath + "\"}";
			response.getWriter().write(reString);
		}
	}

	/**
	 * 上传头像  zan shi mei yong 
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public boolean uploadHead(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		InputStream is = null;
		FileItem fileItem = getFileItemFromReqest(request, response);
		if (null == fileItem) {
			return false;
		}
		String fileName = fileItem.getName();
		fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
		// 判断选中文件
		if (fileName == null || fileName.equals("")) {
			response.getWriter().write("{\"res\": \"请选择要上传的文件！\"}");
			return false;
		}
		try {
			is = fileItem.getInputStream();
		} catch (IOException e) {
			System.out.println("file to get inputStream from fileItem");
			// e.printStackTrace();
			LogManager.getLogger(getClass()).log(Level.WARNING,
					"failed to get inputStream from fileItem", e);
		}
		// 获取文件类型.jpg
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		//扩展名jpg
		String file_ext_name = fileName.substring(fileName.lastIndexOf(".")+1);
		// 获取上传者的姓名
		String staff_name = URLDecoder.decode(request.getParameter("staff_name"), "utf-8");
		String staff_id = URLDecoder.decode(request.getParameter("id"), "utf-8");
		System.out.println("文件扩展名是否正确："+file_ext_name);
		//上传头像
		String place = FastDFSClient.uploadFile(is, file_ext_name);
		if(null != place){
			//将以前的旧头像删去
			//将现有头像更新
			Staff staff = new Staff();
			staff.setId(Integer.parseInt(staff_id));
			staff.setHeadUrl(place);
			staffDao.updateHeadUrlById(staff);
			//把头像也当作个人的文件留存
			SharedFile sharedFile = new SharedFile(staff_name, new Date(),
					fileType, fileName, place);
			dao.insertRecord(sharedFile);
		}

		response.getWriter().write("{\"res\": \"上传成功！\"}");
		return true;
	}

	/**
	 * update head
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public boolean updateHead(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		String body = request.getParameter("body");
		Gson gson = new Gson();
		Staff staff = new Staff();
		staff = gson.fromJson(body, Staff.class);
		//将现有头像更新
		staffDao.updateHeadUrlById(staff);
		response.getWriter().write("{\"res\": \"update head 成功！\"}");
		return true;
	}
	
	/**
	 * xiu gai mi ma 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public boolean updatePassword(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//staffId="+staffId+"&pwd_old="+pwd+"&pwd_new="+pwd1
		int staffId = Integer.valueOf(request.getParameter("staffId"));
		String pwd_old = request.getParameter("pwd_old");
		String pwd_new = request.getParameter("pwd_new");
		//if new password right 
		//update password
		if(pwd_old.equals(staffDao.selectOne(staffId).getPwd())){
			Staff staff = new Staff();
			staff.setId(staffId);
			staff.setPwd(pwd_new);;
			staffDao.updateByPrimaryKey(staff);
			response.getWriter().write("{\"res\": \"update password 成功！\"}");
			return true;
		}
		response.getWriter().write("{\"res\": \"原密码输入错误\"}");
		return false;
	}
	
	/**
	 * 从request从获取文件信息
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private FileItem getFileItemFromReqest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		FileItem fileItem = null;
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		fileUpload.setFileSizeMax(1024 * 1024 * 10);
		try {
			List<FileItem> fileItems = fileUpload
					.parseRequest(new ServletRequestContext(request));
			for (FileItem item : fileItems) {
				fileItem = item;
			}
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			// e.printStackTrace();
			System.out.println("文件过大");
			response.getWriter().write("文件太大！");
			LogManager.getLogger(getClass()).log(Level.WARNING,
					"File is too big", e);
		} catch (FileUploadException e) {
			System.out.println("parse rquest failed");
			// e.printStackTrace();
			LogManager.getLogger(getClass()).log(Level.WARNING,
					"parse rquest failed", e);
		}

		return fileItem;
	}

	/**
	 * 将文件流写入到到磁盘文件
	 * 
	 * @param is
	 * @param file
	 * @return
	 */
	private boolean saveFile(InputStream is, File file) {
		// 检测文件目录目录是否存在,不存在就创建
		String filePath = file.getAbsolutePath();
		String fileDir = filePath.substring(0, filePath.lastIndexOf("\\"));// 获取文件目录
		if (!new File(fileDir).exists()) {
			new File(fileDir).mkdirs();
		}

		// 如果服务器已经存在和上传文件同名的文件
		if (new File(filePath).exists()) {
			System.out.println("文件已存在,不作任何操作,这个概率比1/2^128还要小哦,快去买彩票吧");
			// new File(fileDir).delete();
			return false;
		} else {
			// 开始保存文件
			if (!filePath.equals("")) {
				// 用FileOutputStream打开服务端的上传文件
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(filePath);
					byte[] buffer = new byte[1024]; // 每次读1KB字节
					int count = 0;
					// 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
					while ((count = is.read(buffer)) > 0) {
						fos.write(buffer, 0, count); // 向服务端文件写入字节流
					}
					fos.close(); // 关闭FileOutputStream对象
					is.close(); // InputStream对象
					System.out.println("文件保存成功");
					return true;
				} catch (FileNotFoundException e) {
					// e.printStackTrace();
					System.out.println("找不到文件");
					LogManager.getLogger(getClass()).log(Level.WARNING,
							"找不到文件", e);
					return false;
				} catch (IOException e) {
					// e.printStackTrace();
					System.out.println("无法保存文件");
					LogManager.getLogger(getClass()).log(Level.WARNING,
							"无法保存文件", e);
					return false;
				}
			}
		}
		return false;
	}
}
