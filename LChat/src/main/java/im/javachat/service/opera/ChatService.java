package im.javachat.service.opera;


import java.util.Collection;
import java.util.Date;

import im.javachat.service.GlobalVar.GlobalVar;
import im.javachat.tool.DateTool;
import im.javachat.tool.XmlTool;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.PacketExtension;
import org.jivesoftware.smackx.muc.MultiUserChat;


/**
 * 聊天服务类
 * */
public class ChatService {

	/**
	 * 选择聊天对象后，建立一个会话，输出quit则退出该会话
	 * */
	public static void chat(String friendjid) {
		String message = "";
		if (!GlobalVar.hashchat.containsKey(friendjid)) {
			GlobalVar.hashchat.put(friendjid, GlobalVar.chatManager.createChat(
					friendjid, new MessageListener() {
						@Override
						public void processMessage(Chat chat, Message message) {
							String messageBody = message.getBody();
								System.out.println("\n"+message.getFrom().split("/")[0]+" "+ DateTool.getNowtime()+"\n" + messageBody);
						}
					}));
		}
		//输入消息
		Chat chat = GlobalVar.hashchat.get(friendjid);
		message = GlobalVar.input.chatscanner();
		while (!message.equals("quit")) {
			try {
				chat.sendMessage(message);
				message = GlobalVar.input.chatscanner();
			} catch (NotConnectedException e) {
				e.printStackTrace();
			} catch (XMPPException e) {
				e.printStackTrace();
			}
		}
		chat.close();
	}
	
	/**
	 *发送单条信息给指定用户
	 * @throws XMPPException 
	 * @throws NotConnectedException 
	 * */
	
	public static void sendmessage(String friendjid,String message) throws NotConnectedException, XMPPException{
		if (!GlobalVar.hashchat.containsKey(friendjid)) {
			GlobalVar.hashchat.put(friendjid, GlobalVar.chatManager.createChat(
					friendjid, new MessageListener() {
						@Override
						public void processMessage(Chat chat, Message message) {}
					}));
		}
		
		//输入消息
		Chat chat = GlobalVar.hashchat.get(friendjid);
		chat.sendMessage(DateTool.getNowtime()+GlobalVar.MESSAGEDELIMITER+message);
		chat.close();
	}

	/**
	 * 消息监听
	 * */
	public static void listeningMssage() {
		GlobalVar.chatManager.addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat chat, boolean createdLocally) {
				if (!createdLocally) {
					chat.addMessageListener(new MessageListener() {
						@Override
						public void processMessage(Chat chat, Message message) {
							String messageBody = message.getBody();
						//聊天室邀请包	
						if(message.getFrom().split("/")[0].contains("@muc.")){
							Collection<PacketExtension> c =	message.getExtensions();
							String xml =c.iterator().next().toXML().toString();
							String[] invite = XmlTool.inviteXml(xml);
							System.out.println("\n"+invite[0]+"邀请你加入:"+message.getFrom().split("/")[0]+"\n"+"邀请理由:"+invite[1]);
						//普通消息包（//TODO未处理离线消息）
						} else{
							System.out.println("\n" + message.getFrom().split("/")[0] +" "+DateTool.getNowtime()+" \n"+ messageBody);
						}	
				
						}
					});
				}
			}
		}); 
	}

	
	/**
	 * 监听聊天室中人员的进出
	 * */
	public void listenPresence(MultiUserChat muc){
		//muc.addUserStatusListener(listener);
	}
	
}
