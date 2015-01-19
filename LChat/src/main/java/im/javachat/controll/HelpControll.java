package im.javachat.controll;

import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import im.javachat.entity.MyEntry;
import im.javachat.service.GlobalVar.GlobalVar;

/**
 * 显示帮助信息
 * */
public class HelpControll {
	public static void show(){
		
		Set<Entry<Object,Object>> s = GlobalVar.help.entrySet();
		TreeSet<MyEntry<Object,Object>> tree=new TreeSet<MyEntry<Object,Object>>();
		
		//将帮助信息放于TreeSet中自动进行排序
		for(Entry<Object, Object> entry:s){
			MyEntry<Object,Object> myEntry = new MyEntry<Object,Object>();
			myEntry.setKey(entry.getKey());
			myEntry.setValue(entry.getValue());
			tree.add(myEntry);
		}
		//输出帮助信息
		for(MyEntry<Object,Object> entry:tree){
			System.out.println(entry.getKey()+"		"+entry.getValue()); 
		}
	}
}
