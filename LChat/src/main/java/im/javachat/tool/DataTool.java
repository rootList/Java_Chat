package im.javachat.tool;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * 数据结构操作工具 
 * */
public class DataTool {
	
	/**
	 * 通过HashMap的Value获取Key值
	 * */
	public static List<Object> HashValueToKey(HashMap<Object, Object> hashMap,Object value) {
		if(hashMap==null||value==null){
			return null;
		}
		List<Object> list = new ArrayList<Object>();
		
		Set<Object> keyset = hashMap.keySet();
		for(Object key:keyset){
			if(hashMap.get(key).equals(value)){
				list.add(key);
			}
		}
		if(list.size()==0)
			return null;
		
		return list;
	}
	
	/**
	 * 获取系统时间
	 * */
	public static String getNowtime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date());
	}
}
