package im.javachat.controll;

import im.javachat.service.GlobalVar.GlobalVar;
import im.javachat.service.command.InputCommand;
import im.javachat.service.opera.ChatRoomService;
import im.javachat.tool.StringTool;

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
	public void leaveRoom(String ...roomjid){
		if(roomjid.length!=2){
			InputCommand.printerrorcommand();
			return;
		}
			new ChatRoomService().exitRoom(roomjid[1]);
	}
	
	/**
	 * 创建聊天室
	 * */
	public void createRoom(String ...room){
		if(room.length!=3){
			InputCommand.printerrorcommand();
			return;
		}
		new ChatRoomService().createRoom(room[1], room[2]);
	}
	
	/**
	 * 踢出聊天室的成员
	 * */
	public void banUser(String ...ban){
		if(ban.length!=3){
			InputCommand.printerrorcommand();
			return;
		}
		if(!StringTool.verifyJid(ban[1])){
			InputCommand.printerrorjid();
			return;
		}
		String roomjid = GlobalVar.directory.getDirecStack().lastElement();
		new ChatRoomService().banUser(roomjid, ban[1], ban[2]);
	}
	
	/**
	 * 邀请指定人员加入聊天室
	 * @param comm[1] 聊天室jid
	 * @param comm[2] 用户jid
	 * @param comm[3] 邀请理由
	 * */
	public void inviteUser(String ...comm){
		if(comm.length!=4){
			InputCommand.printerrorcommand();
			return;
		}
		if(!StringTool.verifyJid(comm[1])||!StringTool.verifyJid(comm[2])){
			return;
		}
		new ChatRoomService().inviteUser(comm[2], comm[1], comm[3]);
	}
}
