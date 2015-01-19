package im.javachat.entity;
/**
 * 好友实体
 * */
public class Friend {
	
	private String nickname;//好友昵称
	private String jid;//好友jid
	private String groupName;//好友所属的组
	
	public Friend() {}
	public Friend(String nickname,String jid,String groupname) {
		this.nickname = nickname;
		this.jid = jid;
		this.groupName = groupname;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getJid() {
		return jid;
	}
	public void setJid(String jid) {
		this.jid = jid;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
}
