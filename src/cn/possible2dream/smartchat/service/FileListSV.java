package cn.possible2dream.smartchat.service;

import java.util.List;

import cn.possible2dream.smartchat.dao.SharedFileDao;
import cn.possible2dream.smartchat.entity.SharedFile;

public class FileListSV {
	private SharedFileDao sharedFileDao = new SharedFileDao();
	
	public List<SharedFile> getFIleList(){
		List<SharedFile> sharedFiles = sharedFileDao.selectAll();
		return sharedFiles;
	}
}
