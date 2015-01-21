package im.javachat.service.opera;

import im.javachat.service.GlobalVar.GlobalVar;
import im.javachat.service.command.InputCommand;

import java.util.TreeSet;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.RosterPacket;

/**
 * 监听Presence包，用以处理好友添加信息。重新自定义Type属性。
 * 当有请求时，将请求放入到队列中，等待处理。
 * Presence.Type.subscribe   表示添加好友
 * Presence.Type.subscribed  拒绝添加
 * Presence.Type.unsubscribe 删除好友
 * */
public class PresenceService {
	 /**
	  * 监听好友请求的信息
	  */
		public static void listeningPresence() {
			// 设置为手动处理好友请求
			GlobalVar.connection.getRoster().setSubscriptionMode(Roster.SubscriptionMode.manual);
			GlobalVar.connection.addPacketListener(new IMPacketListener(), new IMPacketFilter());
		}
		
		/**
		 * 处理信息包
		 * */
	private static class IMPacketListener implements PacketListener {
		@Override
		public void processPacket(Packet paramPacket)
				throws NotConnectedException {
			if (paramPacket instanceof Presence) {
				Presence presence = (Presence) paramPacket;
				XMPPConnection connection = GlobalVar.connection;
				// 对方发出添加好友请求
				if (presence.getType().equals(Presence.Type.subscribe)) {
					InputCommand.print("\n"+presence.getFrom() + ":添加你为好友,请到/presence下进行处理");
					// 将请求加入到队列中，等待处理
					GlobalVar.peQueue.addPresence(presence);
					//更改目录presence下的文件
					TreeSet<String> prese = new TreeSet<String>();
					prese.add(presence.getFrom());
					GlobalVar.directory.getHashfile().put(GlobalVar.PRESENCEDIR, prese);
					/*Presence newp = new Presence(Presence.Type.subscribed);
					newp.setMode(Presence.Mode.available);
					newp.setPriority(24);
					newp.setTo(presence.getFrom());
					newp.setFrom(presence.getTo());
					connection.sendPacket(newp);*/
					
				// 对方同意你的添加请求,将好友添加到自己的联系人中
				} else if (presence.getType().equals(Presence.Type.subscribed)) {

					System.out.println("\n"+presence.getFrom() + ":同意的你添加请求");

					RosterPacket rosterPacket = new RosterPacket();
					rosterPacket.setType(IQ.Type.SET);
					RosterPacket.Item item = new RosterPacket.Item(presence.getFrom(), presence.getFrom());
					
					/**
					 * 当groupname不为空时，表明是自己添加好友
					 * 为空时，自己的添加好友请求没有设置group和昵称等,添加默认组
					 * */
					String groupName = "autoGroup";
					if (GlobalVar.peQueue.getFriend(presence.getFrom()) != null) {
						 groupName = GlobalVar.peQueue.getFriend(presence.getFrom()).getGroupName();
					}
						String[] groups = {groupName};
						if (groups != null) {
							for (String group : groups) {
								if (group != null && group.trim().length() > 0) {
									item.addGroupName(group);
								}
							}
						}
						
						rosterPacket.addRosterItem(item);
						try {
							connection.createPacketCollectorAndSend(rosterPacket).nextResultOrThrow();
							
						} catch (NoResponseException e) {
							e.printStackTrace();
						} catch (XMPPErrorException e) {
							e.printStackTrace();
						}
						//修改本地目录
						RosterEntry rosterEntry = GlobalVar.connection.getRoster().getEntry(presence.getFrom());
						GlobalVar.friends.put(presence.getFrom(), rosterEntry);
						//若已经存在该目录，则直接将好友添加进该分组，不存在则新建
						if(GlobalVar.directory.getHashfile().get(groupName)!=null){
							GlobalVar.directory.getHashfile().get(groupName).add(presence.getFrom());
							
						}else{
							TreeSet<String> newgroup = new TreeSet<String>();
							newgroup.add(presence.getFrom());
							GlobalVar.directory.getHashfile().put(groupName, newgroup);
							GlobalVar.directory.getHashfile().get(GlobalVar.FRIENDDIR).add(groupName);
						}
				
					
				}else if (presence.getType().equals(Presence.Type.unsubscribe)) {
					System.out.println(presence.getFrom() + ":删除了你");
				}
				// 被对方拒绝添加为好友
				else if (presence.getType().equals(Presence.Type.unsubscribed)) {
					System.out.println(presence.getFrom() +":拒绝你添加他为好友！");
				}
				//处理IQ包
			}else if(paramPacket instanceof IQ){
			//	IQ iq = (IQ)paramPacket;
			//	System.out.println(iq.getXmlns());
			}
		}

		
	}
		
	/**
	 * 过滤 Presence包，只处理好友添加信息
	 * */
	private static class IMPacketFilter implements PacketFilter {
		@Override
		public boolean accept(Packet packet) {
			if (packet instanceof Presence) {
				// 对方添加好友申请
				if (((Presence) packet).getType().equals(
						Presence.Type.subscribe)) {
					return true;
				}
				//对方同意你的添加请求
				else if(((Presence) packet).getType().equals(Presence.Type.subscribed)){
					return true;
				}
				// 被对方删除
				else if (((Presence) packet).getType().equals(
						Presence.Type.unsubscribe)) {
					return true;
				}
				// 被对方拒绝添加为好友
				else if (((Presence) packet).getType().equals(
						Presence.Type.unsubscribed)) {
					return true;
				}
				return false;
				
			//过滤IQ包
			}else if(packet instanceof IQ){
				if(((IQ)packet).getType().equals(IQ.Type.RESULT)){
					return true;
				}
			}
			return false;
		} 

	}
}

