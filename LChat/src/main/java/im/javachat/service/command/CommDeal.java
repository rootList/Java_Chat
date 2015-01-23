package im.javachat.service.command;

import im.javachat.controll.ChatControll;
import im.javachat.controll.ChatRoomControll;
import im.javachat.controll.DirectoryControll;
import im.javachat.controll.FriendControll;
import im.javachat.controll.HelpControll;
import im.javachat.service.opera.ChatRoomService;
import im.javachat.service.opera.ChatService;
import im.javachat.service.opera.LoginService;

/**
 * 解析用户命令
 * */
public class CommDeal {

	/**
	 * 解析用户命令,一行命令内部用空格分开(暂时使用单命令模式)
	 * 
	 * @param 用户输入的命令
	 * 
	 * */
	public static void ParseComm(String command) {
		String[] comm = command.split(" ");
		switch (comm[0]) {
		// 帮助,打印帮助命令
		case "help":
			HelpControll.show();
			break;
		// 进入目录
		case "cd":
			DirectoryControll.pushDirectory(comm);
			break;
		// 显示当前目录
		case "pwd":
			DirectoryControll.pwdDirectory();
			break;
		// 显示目录下的列表
		case "ls":
			DirectoryControll.lsList();
			break;
		//添加好友	
		case "add":
			FriendControll.addFriend(comm);
			break;
		case "rm":
		case "remove":
			FriendControll.removeFriend(comm);
			break;
		// 发送消息
		case "send":
			new ChatControll().sendMessage(comm);
			break;
		case "revoke":
			new ChatRoomControll().revokevoice(comm);
			break;
		case "grant":
			new ChatRoomControll().grantvoice(comm);
			break;
		case "exit":
			new LoginService().loginout();
			System.exit(0);
			break;
		//加入聊天室	
		case "join":
			if(comm.length == 2)
				new ChatRoomService().joinRoom(comm[1]);
			break;
		//创建聊天室
		case "create":
				new ChatRoomControll().createRoom(comm);
			break;
		//将指定用户踢出聊天室	
		case "ban":
				new ChatRoomControll().banUser(comm);
			break;
		//邀请好友
		case "invite":
				new ChatRoomControll().inviteUser(comm);
			break;
		//处理好友添加请求	
		case "deal":
				new FriendControll().dealPresence(comm);
			break;
		case "leave":
			if(comm.length==2)
				new ChatRoomControll().leaveRoom(comm);
			break;
		case "test":
			new ChatService().test();
			break;
		// 错误输入
		default:
			System.out.println("input error,no " + command);
			break;
		}
	}
}
