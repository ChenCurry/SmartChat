package cn.possible2dream.smartchat.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.possible2dream.smartchat.entity.MessageToFore;
import cn.possible2dream.smartchat.entity.Staff;
import cn.possible2dream.smartchat.util.Mybatisutil;
import cn.possible2dream.smartchat.util.PropertiesReader;



public class mainEnter {
	public static void main(String[] args) {
		mainEnter me = new mainEnter();
		me.getProperties();
		
	}
	
	public void getProperties(){
		String[] names = {"maxCapacity"};
		PropertiesReader pReader = new PropertiesReader();
		
		try {
			HashMap<String, String> hm = pReader.getProperties("msgCache.properties", names);
			System.out.println(hm.get("maxCapacity"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
