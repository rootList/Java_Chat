package im.javachat.entity;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.util.XmlStringBuilder;

public class MyMessage extends Message{
	private String sendtime;
	@Override
	public XmlStringBuilder toXML() {
		 XmlStringBuilder buf = new XmlStringBuilder();
		String message = "<message to='test@localhost.localdomain' from='111@localhost.localdomain/Spark 2.6.3'"
				+ " type='chat' sendtime='2015-01-21'><body>阿斯顿</body>  <thread>jY0r1g</thread><x xmlns='jabber:x:event'>"
				+ "<offline/><composing/></x></message>";
		buf.append(message);
		return buf;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}

}
