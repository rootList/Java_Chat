package im.javachat.controll;


import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;

import im.javachat.service.opera.FriendService;

/**
 * 处理与好友相关的命令
 * 
 * */
public class FriendControll {
	
	/**
	 * 添加好友
	 * @param user 好友的jid
	 * @param name 好友昵称
	 * @param groups 好友所属的组
	 * */
	public static void addFriend(String friendjid,String remarkNick,String group){
		try {
			FriendService.addfriend(friendjid, remarkNick, group);
		} catch (NotLoggedInException e) {
			e.printStackTrace();
		} catch (NoResponseException e) {
			e.printStackTrace();
		} catch (XMPPErrorException e) {
			e.printStackTrace();
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除好友
	 * @param friendjid
	 * */
	public static void removeFriend(String friendjid){
		try {
			new FriendService().removefriend(friendjid);
		} catch (NotLoggedInException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMPPErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			System.out.println("maybe jid is not exist"+e);
		}
	}
}
