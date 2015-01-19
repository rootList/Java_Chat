package im.javachat.service.opera;

import im.javachat.service.GlobalVar.GlobalVar;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.InvitationListener;
import org.jivesoftware.smackx.muc.MultiUserChat;

/**
 * 监听关于聊天室的包
 * */
public class ChatRoomPackService {
	
	/**
	 * 监听聊天室的邀请请求
	 * */
	public static void InvitationListener(){
		MultiUserChat.addInvitationListener(GlobalVar.connection, new InvitationListener() {
			@Override
			public void invitationReceived(XMPPConnection conn, String room,
					String inviter, String reason, String password, Message message) {
			//	System.out.println("%^%&"+room);
			
			}
		});
	}
}
