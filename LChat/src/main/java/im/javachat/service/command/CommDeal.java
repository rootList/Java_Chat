package im.javachat.service.command;

import im.javachat.controll.ChatControll;
import im.javachat.controll.ChatRoomControll;
import im.javachat.controll.DirectoryControll;
import im.javachat.controll.FriendControll;
import im.javachat.controll.HelpControll;
import im.javachat.service.opera.ChatRoomService;
import im.javachat.service.opera.FriendService;
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
			DirectoryControll.pushDirectory(comm[1]);
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
			if(comm.length == 2){
				FriendControll.addFriend(comm[1],comm[2], comm[3]);
			}else{
				System.out.println("command is error,help check it");
			}
			break;
		case "rm":
		case "remove":
			FriendControll.removeFriend(comm[1]);
			break;
		// 发送消息
		case "send":
			new ChatControll().sendMessage(comm[2],comm[1]);
			break;
		case "exit":
			new LoginService().loginout();
			System.exit(0);
			break;
		//加入聊天室	
		case "join":
			new ChatRoomService().joinRoom(comm[1]);
			break;
		//创建聊天室
		case "create":
			System.out.println(comm.length);
			if(comm.length==3)
				new ChatRoomControll().createRoom(comm[1], comm[2]);
			break;
		//将指定用户踢出聊天室	
		case "ban":
			if(comm.length == 2)
				new ChatRoomControll().banUser(comm[1], comm[2]);
			else
				System.out.println("command's parameter is error");
			break;
		//邀请好友
		case "invite":
			if(comm.length>3)
				new ChatRoomControll().inviteUser(comm[1], comm[2],comm[3]);
			break;
		//处理好友添加请求	
		case "deal":
			if(comm.length==2){
				new FriendService().dealPresence(comm[1],comm[2]);
			}else{
				System.out.println("command's parameter is error");
			}
			break;
		case "leave":
			new ChatRoomControll().leaveRoom(comm[1]);
			break;
		// 错误输入
		default:
			System.out.println("input error,no " + command);
			break;
		}
	}
}
