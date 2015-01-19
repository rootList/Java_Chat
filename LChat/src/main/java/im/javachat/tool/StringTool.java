package im.javachat.tool;

import java.io.UnsupportedEncodingException;


public class StringTool {
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
}
