package im.javachat.service.GlobalVar;

import im.javachat.entity.Friend;

import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.jivesoftware.smack.packet.Presence;

/**
 * 请求信息的队列
 * */
public class PresenceQueue {
	
	/**
	 * 保存好友请求Presence
	 * */
	private Queue<Presence> queue = new LinkedBlockingQueue<Presence>();
	
	/**
	 *保存自己添加好友时的信息，如昵称，jid，所属的组 
	 * */
	private HashMap<String,Friend> addfriendhash = new HashMap<String, Friend>();
	
	/**
	 *取出presence信息 
	 * */
	public Presence poll(){
		return queue.poll();
	}
	
	/**
	 * 有请求信息时，将请求放入到队列中
	 * */
	public void addPresence(Presence presence){ 
		queue.add(presence);
	}
	
	public Queue<Presence> getQueue() {
		return queue;
	}
	
	public Friend getFriend(String jid){
		return addfriendhash.get(jid);
	}
	
	public void addFriend(String jid,Friend friend){
		addfriendhash.put(jid, friend);
	}
}
