package im.javachat.tool;

import java.io.StringReader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;



/**
 * 解析XML信息
 * */
public class XmlTool {
	
	/**
	 * 解析聊天是的邀请信息 
	 * */
	public static String[] inviteXml(String xml){
		//invite[0]邀请人 invite[1]邀请理由
		String[] invite = new String[2];
		Document document;
		try {
			document = DocumentHelper.parseText(xml);
			Element root =  document.getRootElement();
		
			invite[0] = root.element("invite").attributeValue("from").split("/")[0];
			invite[1] = root.element("invite").getStringValue();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return invite;
	}
	
	public static void pre(String xml){
		try {
			Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			//System.out.println(root.gete);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String xml = "<message id='r0VV4-4' to='test@localhost.localdomain' from='111@localhost.localdomain/Smack' type='chat'>"
				+ "<body>nihao</body> <thread>cc10087f-2947-4df4-b9e7-2cdadcf69ecb</thread><delay xmlns='urn:xmpp:delay' "
				+ "stamp='2015-01-19T16:35:11.999Z' from='localhost.localdomain'>Offline Storage - localhost</delay></message>";
		XmlTool.pre(xml);
	}
}
