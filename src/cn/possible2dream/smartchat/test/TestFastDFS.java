package cn.possible2dream.smartchat.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import cn.possible2dream.smartchat.util.FastDFSClient;

public class TestFastDFS {
	public static String name;
	public static Integer staffId;
	@Test
    public void testStaticVar() {
		System.out.println(name);
		System.out.println(staffId);
    }
	
	
	@Test
    public void testEncode() {
        try {
			System.out.println(URLEncoder.encode("Yu.Chen", "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }
	/**
     * 文件下载测试
     */
    @Test
    public void testDownload() {
        int r = FastDFSClient.downloadFile("group1/M00/00/00/aksgpl6AlFeAckz8AASfGtpkmDQ168.jpg", new File("C:\\Users\\asus\\Desktop\\下载到这张图片.jpg"));
        System.out.println(r == 0 ? "下载成功" : "下载失败");
    }
    
    /**
     * 文件删除测试
     */
    @Test
    public void testDelete() {
        int r = FastDFSClient.deleteFile("group1/M00/00/00/aksgpl6AlFeAckz8AASfGtpkmDQ168.jpg");
        System.out.println(r == 0 ? "删除成功" : "删除失败");
    }
    
    /**
     * 文件上传测试
     */
    @Test
    public void testUpload() {
        File file = new File("C:\\Users\\asus\\Desktop\\AApUHAk.jpg");
        Map<String,String> metaList = new HashMap<String, String>();
        metaList.put("width","1024");
        metaList.put("height","768");
        metaList.put("author","杨信");
        metaList.put("date","20161018");
      
        //获取文件的后缀名 .jpg
        String originalFilename = file.getPath();
        int lastIndexOf = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(lastIndexOf+1);

		try {
			InputStream is = new FileInputStream(file);
			String fid = FastDFSClient.uploadFile(is,suffix,metaList);
	        System.out.println("upload local file " + file.getPath() + " ok, fileid=" + fid);
	        //上传成功返回的文件ID： group1/M00/00/00/wKgAyVgFk9aAB8hwAA-8Q6_7tHw351.jpg
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 获取文件元数据测试
     */
    @Test
    public void testGetFileMetadata() {
        Map<String,String> metaList = FastDFSClient.getFileMetadata("group1/M00/00/00/aksgpl6AlFeAckz8AASfGtpkmDQ168.jpg");
        for (Iterator<Map.Entry<String,String>>  iterator = metaList.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<String,String> entry = iterator.next();
            String name = entry.getKey();
            String value = entry.getValue();
            System.out.println(name + " = " + value );
        }
    }
}
