package im.javachat.tool;

import java.io.UnsupportedEncodingException;



public class StringTool {
	//将字符串转成utf-8
	public static String StrtoUTF8(String string){
		
		try {
			String str = new String(string.getBytes(), "GBK");
			return str;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 对jid进行验证
	 * */
	public static boolean verifyJid(String jid){
		if(!jid.contains("@")){
			System.out.println("jid is not legal");
			return false;
		}
		return true;
	}
}
