package com.test;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.SimplePayload;


public class Main {
public static void main(String[] args) {
System.out.println(Runtime.getRuntime().availableProcessors());
/*
	ConnectionConfiguration config = new ConnectionConfiguration("localhost.localdomain", 5222);
	config.setSecurityMode(SecurityMode.disabled);
	SASLAuthentication.supportSASLMechanism("PLAIN", 0);
	XMPPConnection	connection = new XMPPTCPConnection(config);
	try {
		connection.connect();
		connection.login("555", "555");
	
		PubSubManager pub = new PubSubManager(connection);
		LeafNode node = pub.getNode("123");
		if(node==null){
			node = pub.createNode("123");
		}
		 String msg = "发布消息";  
         
         SimplePayload payload = new SimplePayload("message","pubsub:test:message", "<message xmlns='pubsub:test:message'><body>"+msg+"</body></message>");  
         PayloadItem<SimplePayload> item = new PayloadItem<SimplePayload>("5", payload);  

		node.publish(item);
	} catch (Exception e) {
		e.printStackTrace();
	}*/
}
}
