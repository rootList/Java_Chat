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
	 *禁用指定用户权限
	 * */
	public void revoke(String ...revoke){
		if(revoke.length!=3){
			InputCommand.printerrorcommand();
			return;
		}
		if(!StringTool.verifyJid(revoke[2])){
			InputCommand.printerrorjid();
			return;
		}
		String roomjid = GlobalVar.directory.getDirecStack().lastElement();
		switch (revoke[1]) {
		case "voice":
			new ChatRoomService().shutupUser(revoke[2], roomjid);
			break;
		case "admin":
			new ChatRoomService().revokeAdmin(revoke[2], roomjid);
			break;
		default:
			break;
		}
		
		
	} 
	
	/**
	 * 赋予指定用户权限
	 * */
	public void grant(String ...revoke){
		if(revoke.length!=3){
			InputCommand.printerrorcommand();
			return;
		}
		if(!StringTool.verifyJid(revoke[2])){
			InputCommand.printerrorjid();
			return;
		}
		String roomjid = GlobalVar.directory.getDirecStack().lastElement();
		switch (revoke[1]) {
		//授予发言权
		case "voice":
			new ChatRoomService().grantVoice(revoke[2], roomjid);
			break;
		//授予管理权	
		case "admin":
			new ChatRoomService().grantAdmin(revoke[2], roomjid);
			break;
		case "entry":
			new ChatRoomService().grantEntry(revoke[2], roomjid); 
			break;
		default:
			break;
		}
		
		
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
