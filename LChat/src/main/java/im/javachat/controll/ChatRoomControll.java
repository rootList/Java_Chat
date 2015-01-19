package im.javachat.controll;

import im.javachat.service.GlobalVar.GlobalVar;
import im.javachat.service.opera.ChatRoomService;

import org.apache.commons.lang.StringUtils;

/**
 * 聊天室入口，在方法中处理非法数据，之后再调用Service
 * */
public class ChatRoomControll {
	
	/**
	 * 从目录结构中获取房间号，再进入聊天室
	 * */
	public void entryChatRoom(){
		String roomjid = GlobalVar.directory.getDirecStack().lastElement();
		if(StringUtils.isEmpty(roomjid)){
			System.out.println("房间号为空");
			return;
		}
		new ChatRoomService().entryRoom(roomjid);
	}
	
	/**
	 * 离开聊天室
	 * */
	public void leaveRoom(String roomjid){
		if(!StringUtils.isEmpty(roomjid))
			new ChatRoomService().exitRoom(roomjid);
	}
	
	/**
	 * 创建聊天室
	 * */
	public void createRoom(String roomjid,String roomNickname){
		new ChatRoomService().createRoom(roomjid, roomNickname);
	}
	
	/**
	 * 踢出聊天室的成员
	 * */
	public void banUser(String userjid,String reason){
		String roomjid = GlobalVar.directory.getDirecStack().lastElement();
		new ChatRoomService().banUser(roomjid, userjid, reason);
	}
	
	/**
	 * 邀请指定人员加入聊天室
	 * */
	public void inviteUser(String userjid,String roomjid,String reason){
		new ChatRoomService().inviteUser(roomjid, userjid, reason);
	}
}
