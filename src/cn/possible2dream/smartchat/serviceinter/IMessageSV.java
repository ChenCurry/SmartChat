package cn.possible2dream.smartchat.serviceinter;

import java.util.List;

import cn.possible2dream.smartchat.entity.Message;
import cn.possible2dream.smartchat.entity.MessageToFore;

public interface IMessageSV {
	public int insertRecord(Message message);
	public List<MessageToFore> getPublicTop10();
	public List<MessageToFore> selectPublicNormal(int number);
	public List<MessageToFore> selectPrivateNormal(int staffId, int targetId, int number);
	public List<MessageToFore> getRecords();
	public List<MessageToFore> getPrivateTop10(int staffId, int targetId);
	public List<MessageToFore> getPrivateRecords(int staffId, int targetId);
}