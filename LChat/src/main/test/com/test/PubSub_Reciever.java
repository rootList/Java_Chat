package com.test;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.LeafNode;
import org.jivesoftware.smackx.pubsub.Node;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.PubSubManager;
import org.jivesoftware.smackx.pubsub.SimplePayload;
import org.jivesoftware.smackx.pubsub.listener.ItemEventListener;

public class PubSub_Reciever {
public static void main(String[] args) {

	ConnectionConfiguration config = new ConnectionConfiguration("localhost.localdomain", 5222);
	config.setSecurityMode(SecurityMode.disabled);
	SASLAuthentication.supportSASLMechanism("PLAIN", 0);
	XMPPConnection	connection = new XMPPTCPConnection(config);
	try {
		connection.connect();
		connection.login("333", "333");
		PubSubManager manager = new PubSubManager(connection);
		 Node eventNode = manager.getNode("123");  
	        eventNode.addItemEventListener(new ItemEventListener<PayloadItem>() {  
	            public void handlePublishedItems(ItemPublishEvent evt) {  
	                for (Object obj : evt.getItems()) {  
	                    PayloadItem item = (PayloadItem) obj;  
	                    
	                    System.out.println("\n"+item.getPayload().toXML());  
	                }  
	            }  
	        });  
	        eventNode.subscribe(connection.getUser());  
	        while(true);  
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
