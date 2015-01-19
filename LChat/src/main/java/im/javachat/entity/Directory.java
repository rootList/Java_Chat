package im.javachat.entity;

import java.util.HashMap;
import java.util.Stack;
import java.util.TreeSet;

/**
 * 目录树
 * */
public class Directory {
	
	/**
	 * 目录所处的位置，通过进栈或出栈来操作目录
	 * */
	private Stack<String> direcStack = new Stack<String>();
	
	/**
	 * 每级目录下的文件列表
	 * Key保存目录direcStack
	 * Value保存目录下的文件
	 * */
	private HashMap<String, TreeSet<String>> hashfile = new HashMap<String, TreeSet<String>>();
	
	
	public Stack<String> getDirecStack() {
		return direcStack;
	}
	public void setDirecStack(Stack<String> direcStack) {
		this.direcStack = direcStack;
	}
	public HashMap<String, TreeSet<String>> getHashfile() {
		return hashfile;
	}
	public void setHashfile(HashMap<String, TreeSet<String>> hashfile) {
		this.hashfile = hashfile;
	}
	
}
