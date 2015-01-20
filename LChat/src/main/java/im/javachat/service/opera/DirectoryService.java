package im.javachat.service.opera;

import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import im.javachat.service.GlobalVar.GlobalVar;
import im.javachat.tool.DateTool;

/**
 * 目录服务层
 * */
public class DirectoryService {
	
	/**
	 * 当进入新的目录后，加载该目录下的文件列表，并将文件列表放入TreeSet中
	 * @param Directory 目录名
	 * */
	public static void pushFile(String directory){
		
	}
	
	/**
	 * ls命令
	 * 1、获取当前目录
	 * 2、加载目录列表
	 * */
	public static void lsFile(){
		
		TreeSet<String> tree = GlobalVar.directory.getHashfile().get(GlobalVar.directory.getDirecStack().lastElement());
		if(tree==null){
			System.out.println("no file");
			return;
		}
		String filelist = tree.toString().replace(", ", "\n");
		System.out.println(filelist.substring(1, filelist.length()-1));
	}
	
	/**
	 * pwd命令
	 * */
	public static void pwdpath(){
		String directory = GlobalVar.directory.getDirecStack().toString().replaceAll(", ","/");
		directory = directory.substring(1, directory.length()-1);
		//根目录下多了一个 / ,替换它
		System.out.println(directory.replaceAll("//", "/"));
	}
	
	/**
	 * 初始化目录结构,从/开始，若有下级目录则递归调用
	 * @param hashMap 从配置文件中加载的HashMap结构
	 * @param value 需要搜索的目录
	 * */
	public void initDirectory(HashMap<Object, Object> hashMap,Object value){
		List<Object> list =	DateTool.HashValueToKey(hashMap, value);
		
		if(list!=null){
		TreeSet<String> file = new TreeSet<String>();
			for(Object keyvalue:list){
				file.add((String)keyvalue);
				initDirectory(hashMap,keyvalue);
			}
		//将本级目录添加到目录结构中
			GlobalVar.directory.getHashfile().put((String)value, file);
		}
	}
}
