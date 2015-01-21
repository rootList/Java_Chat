package im.javachat.controll;


import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;

import im.javachat.service.command.InputCommand;
import im.javachat.service.opera.FriendService;
import im.javachat.tool.StringTool;

/**
 * 处理与好友相关的命令
 * 
 * */
public class FriendControll {
	
	/**
	 * 添加好友
	 * @param comm[1] 好友的jid
	 * @param comm[2] 好友昵称
	 * @param comm[3] 好友所属的组
	 * */
	public static void addFriend(String ...comm){
		try {
			if(comm.length!=4){
				InputCommand.printerrorcommand();
				return;
			}
			if(!StringTool.verifyJid(comm[1])){
				   System.out.println("jid is not true");
				   return;
			   }
			FriendService.addfriend(comm[1], comm[2], comm[3]);
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
	public static void removeFriend(String ...friendjid){
		try {
			if(friendjid.length!=2){
				InputCommand.printerrorcommand();
				return;
			}
			if(!StringTool.verifyJid(friendjid[1])){
				return;
			}
			new FriendService().removefriend(friendjid[1]);
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
	
	/**
	 * 处理好友的添加请求
	 * @param comm[1] userjid
	 * @param comm[2] agree/refuse
	 * */
	public void dealPresence(String ...comm){
		if(comm.length!=3){
			System.out.println("command's parameter is error");
			return;
		}
		//验证jid的合法性
		if(!StringTool.verifyJid(comm[1])){
			return;
		}
		new FriendService().dealPresence(comm[1], comm[2]);
	}
}
