package im.javachat.service.GlobalVar;


import im.javachat.controll.LoginControll;
import im.javachat.service.command.Command;
import im.javachat.service.opera.ChatService;
import im.javachat.service.opera.FriendService;
import im.javachat.service.opera.PresenceService;

public class ImServiceMain {
	
	public static void main(String[] args) {
		new LoginControll().login();
		PresenceService.listeningPresence();//开启状态监听线程
		ChatService.listeningMssage();//开启消息监听线程
		FriendService.init();//登陆后初始化好友列表
		new Thread(new Command()).start();
	}
}
	