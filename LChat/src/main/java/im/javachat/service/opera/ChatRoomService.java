package im.javachat.service.opera;

import im.javachat.service.GlobalVar.GlobalVar;
import im.javachat.service.command.InputCommand;
import im.javachat.tool.DateTool;
import im.javachat.tool.StringTool;

import java.util.Iterator;
import java.util.TreeSet;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.HostedRoom;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;

/**
 * 聊天室服务类
 * */
public class ChatRoomService {

	/**
	 * 每次进入chatroom目录就更新chatroom的目录结构
	 * */
	public static void showRooms(){
		// TODO 是否每次都需要更新roomjid
		TreeSet<String> rooms = new TreeSet<String>();
		try {
			if (!MultiUserChat.getHostedRooms(GlobalVar.connection,GlobalVar.service.getProperty("servicename")).isEmpty()) {
				for (HostedRoom k : MultiUserChat.getHostedRooms(GlobalVar.connection,GlobalVar.service.getProperty("servicename"))) {
					for (HostedRoom j : MultiUserChat.getHostedRooms(GlobalVar.connection, k.getJid())) {
					//	RoomInfo info2 = MultiUserChat.getRoomInfo(GlobalVar.connection, j.getJid());
					//	System.out.println("名字 	|	"+"地址		" );
						if (j.getJid().indexOf("@") > 0) {
						//	System.out.println(j.getName()+"	| "+j.getJid()); 
							rooms.add(j.getJid());
						}
					}
				}
				GlobalVar.directory.getHashfile().put(GlobalVar.CHATROOM, rooms);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 加入聊天室,可以加入多个聊天室，只是接受该聊天室的消息，不进行消息发送
	 * @param roomName 聊天室名称
	 * */
	public void joinRoom(String roomName){
		try {
			
			MultiUserChat muc = new MultiUserChat(GlobalVar.connection, roomName);
			if(!muc.isJoined()){
				muc.join(GlobalVar.userjid);//用jid作为昵称加入聊天室
				GlobalVar.chatroom.put(roomName, muc);
				listenRoomMessage(muc);
			}
		} catch (NoResponseException e) {
			e.printStackTrace();
		} catch (XMPPErrorException e) {
			e.printStackTrace();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 进入聊天室，可以发言
	 * 1、若已经加入该聊天室，则从GlobalVar中取出MultiUserChat
	 * 2、否则加入聊天室
	 * @param roomjid 聊天室jid
	 * */
	public void entryRoom(String roomjid){
		MultiUserChat muc;
		try {
			//已经加入
		if(GlobalVar.chatroom.get(roomjid)!=null&&GlobalVar.chatroom.get(roomjid).isJoined()){
			muc = GlobalVar.chatroom.get(roomjid);
		}else{
			joinRoom(roomjid);
			muc = GlobalVar.chatroom.get(roomjid);
		}
		
		String message = GlobalVar.input.chatscanner();
		while(message!=null&&!message.equals("quit")){
				muc.sendMessage(StringTool.StrtoUTF8(message));
				message = GlobalVar.input.chatscanner();
		}
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建聊天室,监听消息并修改chatroom目录
	 * @param roomName 聊天室的jid前缀
	 * */
	public void createRoom(String roomName,String roomNickname){
		try {
			MultiUserChat muc = new MultiUserChat(GlobalVar.connection, roomName+"@muc."+GlobalVar.service.getProperty("servicename"));
			
			 muc.create(GlobalVar.userjid);
			 //根据原表单创建需要提交的表单
			 Form form = muc.getConfigurationForm();
			 Form submitForm = form.createAnswerForm();
			for (Iterator<FormField> fields = form.getFields().iterator(); fields.hasNext();) {
				FormField field = (FormField) fields.next();
				if (!FormField.TYPE_HIDDEN.equals(field.getType())&& field.getVariable() != null) { 
					submitForm.setDefaultAnswer(field.getVariable());
				}
			}
			//submitForm.setAnswer("muc#roomconfig_persistentroom", true);
			submitForm.setAnswer("muc#roomconfig_roomname", roomNickname);
			muc.sendConfigurationForm(submitForm); 
			listenRoomMessage(muc);
			GlobalVar.chatroom.put(muc.getRoom(), muc);
		} catch (NoResponseException e) {
			e.printStackTrace();
		} catch (XMPPErrorException e) {
			e.printStackTrace();
		} catch (SmackException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 监听指定聊天室的消息
	 * */
	public void listenRoomMessage(MultiUserChat muc){
		muc.addMessageListener(new PacketListener() {
			@Override
			public void processPacket(Packet packet) throws NotConnectedException {
				if(packet instanceof Message){
					Message m = (Message)packet;
					String[] message = m.getFrom().split("@");
					//过滤自己在聊天室发送的消息
					if(message[1].split("/").length>1){
						if(!message[1].split("/")[1].equals(GlobalVar.userjid.split("@")[0])){
							System.out.println(message[1].split("/")[1]+" "+DateTool.getNowtime()+"\n"+
									StringTool.StrtoUTF8(m.getBody()));
						}
					}
				}
			}
		});
	}
	
	
	/**
	 * 退出聊天室,同时移除MultiUserChat
	 * @param roomjid 聊天室的jid
	 * */
	public void exitRoom(String roomjid){
		try {
			if(GlobalVar.chatroom.get(roomjid)!=null){
				GlobalVar.chatroom.get(roomjid).leave();
				GlobalVar.chatroom.remove(roomjid);
			}
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将聊天室里面的指定用户踢出
	 * @param roomjid 聊天室jid
	 * @param uesrjid 用户jid
	 * @param reason 踢出的原因
	 * */
	public void banUser(String roomjid,String uesrjid,String reason){
		try {
			if(GlobalVar.chatroom.get(roomjid)!=null){
				GlobalVar.chatroom.get(roomjid).banUser(uesrjid, reason);
			}
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (XMPPErrorException e) {
			System.out.println("你没有删除权限");
		} catch (NoResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 邀请好友
	 * @param roomjid 聊天室jid
	 * @param uesrjid 用户jid
	 * @param reason 邀请理由
	 * */
	public void inviteUser(String roomjid,String userjid,String reason){
		try {
			if(GlobalVar.chatroom.get(roomjid)!=null){
				GlobalVar.chatroom.get(roomjid).invite(userjid, reason);
			}
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 监听聊天室动态
	 * */
	public void listenPresence(MultiUserChat muc){
		//muc.addUserStatusListener(listener);
	}
	
}
