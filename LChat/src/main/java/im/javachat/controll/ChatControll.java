package im.javachat.controll;

import im.javachat.service.GlobalVar.GlobalVar;
import im.javachat.service.command.InputCommand;
import im.javachat.service.opera.ChatService;
import im.javachat.tool.DateTool;
import im.javachat.tool.StringTool;

import org.apache.commons.lang.StringUtils;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.DefaultParticipantStatusListener;
import org.jivesoftware.smackx.muc.MultiUserChat;


/**
 * 聊天业务处理
 * 创建聊天过程后，进行消息发送
 * */
public class ChatControll {
	
	/**
	 * 发送聊天消息
	 * */
	public void createchat(){
		String friendjid = GlobalVar.directory.getDirecStack().lastElement();
		ChatService.chat(friendjid);
	}
	
	/**
	 * 发送聊天消息给指定用户
	 * @param friendjid 完整的jid信息
	 * */
	public void sendMessage(String ...comm){
		if(comm.length!=3){
			InputCommand.printerrorcommand();
			return;
		}
		if(!StringTool.verifyJid(comm[2])){
			InputCommand.printerrorjid();
			return;
		}
		try {
			Message messagep = new Message(comm[1],Type.chat);
			messagep.setFrom(GlobalVar.userjid);
			messagep.setBody(comm[2]);
			GlobalVar.connection.sendPacket(messagep);
		} catch (NotConnectedException e) {
			System.out.println("Connection error");
		} catch (Exception e) {
			System.out.println("system error");
		}
	}
	
}
