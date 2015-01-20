package im.javachat.service.opera;

import im.javachat.entity.Friend;
import im.javachat.service.GlobalVar.GlobalVar;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.SmackException.NotLoggedInException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;

/**
 * 处理好友的service层
 * */
public class FriendService {
	
	private Scanner scanner;

	/**
	 * 初始化好友列表
	 * 1、先从tigase中获取好友jid
	 * 2、再以HTTP方式向UserCenter获取具体信息,返回JSON对象
	 * 
	 * */
	public static void initHTTP(){
		Roster roster = GlobalVar.connection.getRoster();
		//StringBuffer sb = new StringBuffer();
		for(RosterEntry entity:roster.getEntries()){
			GlobalVar.friends.put(entity.getUser(), entity);
		}
		//--------------2
		
		/*String result = HttpService.sendGet(GlobalVar.url.getProperty("getfriends"), sb.toString());
		//好友列表的JSON
		JSONObject json = JSONObject.fromObject(result);*/
		
	}
	
	/**
	 * 先初始化分组，在由组名获取组下的好友列表
	 * */
	public static void init(){
		Roster roster = GlobalVar.connection.getRoster();
		Iterator<RosterGroup> groups = roster.getGroups().iterator();
		//friend下的子目录
		TreeSet<String> frienddir = new TreeSet<String>();
		//遍历每个组
		while(groups.hasNext()){
			RosterGroup group = groups.next();
			//获取组下的好友列表
			Iterator<RosterEntry> friendlist = group.getEntries().iterator();
			TreeSet<String> friend = new TreeSet<String>();
			while(friendlist.hasNext()){
				RosterEntry entity = friendlist.next();
				GlobalVar.friends.put(entity.getUser(), entity);
				friend.add(entity.getUser().split("/")[0]);
			}
			GlobalVar.directory.getHashfile().put(group.getName(), friend);
			frienddir.add(group.getName());
		}
		
		GlobalVar.directory.getHashfile().put("friend", frienddir);
	}
	
	/**
	 * 添加好友,将好友实体放到HashMap中，等对方同意后才建立好友关系
	 * 
	 * @throws NotConnectedException 
	 * @throws XMPPErrorException 
	 * @throws NoResponseException 
	 * @throws NotLoggedInException 
	 * */
	public static void addfriend(String friendjid,String remarkNick,String group) throws NotLoggedInException, NoResponseException, XMPPErrorException, NotConnectedException{
	   //备注昵称为空，则以jid替代
	   if(StringUtils.isEmpty(remarkNick)){
		   remarkNick = friendjid;
	   }
		
		Presence presence = new Presence(Type.subscribe);
		presence.setTo(friendjid);
		presence.setFrom(GlobalVar.userjid);
		//发送请求添加的包
		GlobalVar.connection.sendPacket(presence);
		GlobalVar.peQueue.addFriend(friendjid, new Friend(remarkNick, friendjid, group));
	}
	
	/**
	 * 删除好友，删除后需要更改本地目录
	 * @throws NotConnectedException 
	 * @throws XMPPErrorException 
	 * @throws NoResponseException 
	 * @throws NotLoggedInException 
	 * */
	 public void removefriend(String jid) throws NotLoggedInException, NoResponseException, XMPPErrorException, NotConnectedException{
		
		 GlobalVar.connection.getRoster().removeEntry(GlobalVar.friends.get(jid));
		 GlobalVar.friends.remove(jid);
		 
		 //删除好友分组下的好友
		Set<String> groupname = GlobalVar.directory.getHashfile().keySet();
		Iterator<String> it = groupname.iterator();
		while(it.hasNext()){
			String nowgroup = it.next();
			TreeSet<String> file = GlobalVar.directory.getHashfile().get(nowgroup);
			if(file!=null&&file.contains(jid)){
				file.remove(jid);
				//删除空白组名
				if(GlobalVar.directory.getHashfile().get(nowgroup).isEmpty()){
					GlobalVar.directory.getHashfile().remove(nowgroup);
				}
				return;
			}
		}
	 }
	 
	 /**
	  * 处理好友添加请求
	  * jid 对方的jid
	  * type 拒绝或同意
	  * */
	 public void dealPresence(String jid,String type){
		 Presence presence = new Presence(Type.subscribed);
		 if(type.equals("agree")){
		 }else if(type.equals("refuse")){
			 presence.setType(Type.unsubscribed);
		 }else{
			 return;
		 }
		 presence.setFrom(GlobalVar.userjid);
		 presence.setTo(jid);
		 try {
			GlobalVar.connection.sendPacket(presence);
			//处理后从presence目录中移除
			GlobalVar.directory.getHashfile().get(GlobalVar.PRESENCEDIR).remove(jid);
		//添加对方为好友	，（先判断是否已经有此好友）
			if (!type.equals("refuse")&&!queryFriend(jid)) {
				System.out.print("是否添加对方为好友？ Y(y)/N(n) ");
				scanner = new Scanner(System.in);
				String answer = scanner.nextLine();
				if (answer.equals("y") || answer.equals("Y")) {
					Presence addpresence = new Presence(Type.subscribe);
					addpresence.setFrom(GlobalVar.userjid);
					addpresence.setTo(jid);
					GlobalVar.connection.sendPacket(addpresence);
				}
			}
		} catch (NotConnectedException e) {
			e.printStackTrace();
		}
		
	 }
	 
	 /**
	  * 查找是否已有的好友
	  * @param jid 要查询的jid
	  * */
	 public boolean queryFriend(String jid){
		Iterator<TreeSet<String>> i = GlobalVar.directory.getHashfile().values().iterator();
		while(i.hasNext()){
			TreeSet<String> tree = i.next();
			if(tree.contains(jid)){
				return true;
			}
		}
		 return false;
	 }
}
