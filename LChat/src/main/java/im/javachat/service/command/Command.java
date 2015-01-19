package im.javachat.service.command;


import im.javachat.service.GlobalVar.GlobalVar;


/**
 * 处理用户输入的线程，读取一行（命令之间用空格分开），解析命令
 * */

public class Command implements Runnable{
	String command;
	
	/**
	 * 等待用户输入，输入命令后交给命令解析对象处理
	 * TODO(增加键盘监听，处理Tab键等提示命令)
	 * */
	public void run() {
		while (true) {
			command = GlobalVar.input.scanner();
			//分析用户输入的命令
			CommDeal.ParseComm(command);
		}
	}
}
