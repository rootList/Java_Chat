package im.javachat.service.command;


import im.javachat.service.GlobalVar.GlobalVar;

import java.util.Scanner;

/**
 * 输入命令
 * */
public class InputCommand {
	
	private static Scanner scanner = new Scanner(System.in);

	/**
	 *  返回用户输入的命令,同时监听tigase推送的信息，若有需要处理的信息（如对方添加自己为好友），
	 *  则暂停主线程的输入，等待处理信息输入的线程
	 *  
	 * */
	public String scanner() {
		//开启一个监听线程，判断是否有处理信息
		if(GlobalVar.directory.getDirecStack().size()>0){
			System.out.print("["+GlobalVar.userjid+" "+GlobalVar.directory.getDirecStack().lastElement()+"]# ");
			return scanner.nextLine();
		}
		return null;
	}
	
	/**
	 * 聊天时的出入，无提示符
	 * */
	public String chatscanner(){
		return scanner.nextLine();
	}
	
	/** 
	 * 輸出
	 * */
	public void print(String show){
		System.out.println(show);
	}
}
