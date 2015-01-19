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
	
	public static void main(String[] args) {
		String xml = "<x xmlns='http://jabber.org/protocol/muc#user'><invite  from='666@localhost.localdomain/Smack'>"
				+ "<reason>comeon</reason></invite></x>";
		XmlTool.inviteXml(xml);
	}
}
