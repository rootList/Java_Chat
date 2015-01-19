package im.javachat.controll;

import java.util.TreeSet;

import im.javachat.service.GlobalVar.GlobalVar;
import im.javachat.service.opera.ChatRoomService;
import im.javachat.service.opera.DirectoryService;

/**
 * 目录操作
 * */
public class DirectoryControll {
	
	/**
	 * 进入目录，判断该目录是否存在
	 * 1、处理特殊目录位置 ..
	 * 2、判断该目录是否存在
	 * 3、向栈中添加目录结构
	 * @param directoryname 目录名称
	 * */
	public static boolean pushDirectory(String  directoryname){
		//==========1
		String nowdirectory = GlobalVar.directory.getDirecStack().lastElement();
		TreeSet<String> tree = GlobalVar.directory.getHashfile().get(nowdirectory);
		//==============2
		if(directoryname.equals("..")){
			if(GlobalVar.directory.getDirecStack().size()>1)
				popDirectory();
			else{ 
				System.out.println("It's a last directory");
				return false;
				}
		//===========3	
		}else if(tree==null){
			return false;
		}
		else if(!tree.contains(directoryname)){
			System.out.println("no such a directory is "+directoryname);
		}else{
			GlobalVar.directory.getDirecStack().push(directoryname);
			//进入聊天室目录
			if(directoryname.equals("chatroom")){
				ChatRoomService.showRooms();
			//进入指定的聊天室
			}else if(directoryname.contains("@muc")){
				new ChatRoomControll().entryChatRoom();
			//如果进入好友目录目录
			}else if(directoryname.contains("@")){
				new ChatControll().createchat();
			}
			
			
		}
		return true;
	}
	
	/**
	 * 退出目录
	 * 1、弹出栈中的外层目录
	 * */
	public static void popDirectory(){
		GlobalVar.directory.getDirecStack().pop();
	}
	
	/**
	 * 显示自己当前所在的目录
	 * */
	public static void pwdDirectory(){
		DirectoryService.pwdpath();
	}
	
	/**
	 * ls显示当前目录下的列表
	 * */
	public static void lsList(){
		DirectoryService.lsFile();
	}
}
