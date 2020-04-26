package cn.possible2dream.smartchat.util;

import cn.possible2dream.smartchat.entity.MessageToFore;

public class MsgNode {
	private MessageToFore msg;
	protected MsgNode pre;
	protected MsgNode next;
	
	public MsgNode(){
		
	}
	
	public MsgNode(MessageToFore message){
		this.setMsg(message);
	}

	public MessageToFore getMsg() {
		return msg;
	}

	public void setMsg(MessageToFore msg) {
		this.msg = msg;
	}
}
