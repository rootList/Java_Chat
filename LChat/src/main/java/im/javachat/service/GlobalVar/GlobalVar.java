package im.javachat.service.GlobalVar;

import im.javachat.entity.Directory;
import im.javachat.service.command.InputCommand;
import im.javachat.service.opera.DirectoryService;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.muc.MultiUserChat;

/**
 * 保存全局变量，其他部分可以使用该类里面的数据
 * */
public class GlobalVar {

	public static Logger logger = Logger.getLogger(GlobalVar.class);
	public static final String FRIENDDIR="friend";
	public static final String PRESENCEDIR="presence";
	public static final String CHATROOM="chatroom";
	public static final String MESSAGEDELIMITER="##";//消息分隔符
	
	/**
	 * 好友列表
	 * */
	/*public static HashMap<String, Friend> friends = new HashMap<String, Friend>();*/
	public static HashMap<String, RosterEntry> friends = new HashMap<String, RosterEntry>();
	
	/**
	 * 聊天室记录 <br>
	 * key 聊天室jid <br>
	 * value MultiUserChat
	 * */
	public static HashMap<String, MultiUserChat> chatroom = new HashMap<String, MultiUserChat>(); 
	
	public static HashMap<String, Chat> hashchat = new HashMap<String, Chat>();//保存接受者的Chat
	public static PresenceQueue peQueue = new PresenceQueue();
	public static ChatManager chatManager;
	
	/**
	 * 配置文件
	 * */
	public static Properties help = new Properties(); 
	public static Properties url = new Properties();
	public static Properties directorypro = new Properties();
	public static Properties service = new Properties();
	
	public static InputCommand input = new InputCommand();//全局的输入
	public static XMPPConnection connection;
	public static String userjid;//用户的jid
	
	/**
	 * 使用类似于Linux的目录格式进行操作，如进行聊天则进入 /chat/好友jid
	 * */
	public static Directory directory = new Directory();
	
	//从配置文件中初始化数据
	public GlobalVar() {
		try {
			InputStream stream = GlobalVar.class.getResourceAsStream("/help.properties");
			help.load(stream);
			InputStream streamurl = GlobalVar.class.getResourceAsStream("/url.properties");
			url.load(streamurl);
			InputStream streamdirectory = GlobalVar.class.getResourceAsStream("/directory.properties");
			directorypro.load(streamdirectory);
			InputStream streamservice = GlobalVar.class.getResourceAsStream("/service.properties");
			service.load(streamservice);
			
			//初始化目录
			initDirectory(directorypro);
			userjid = "null@"+service.getProperty("servicename");
		} catch (IOException e) {
			logger.error("init error!");
			System.exit(0);
		}
	}
	
	/**
	 * 初始化连接服务器
	 * */
	public void init(){
		try {
			ConnectionConfiguration config = new ConnectionConfiguration(service.getProperty("servicename"),
					Integer.parseInt(service.getProperty("port")));
			config.setSecurityMode(SecurityMode.disabled);
			SASLAuthentication.supportSASLMechanism("PLAIN", 0);
			connection = new XMPPTCPConnection(config);
			connection.connect();
			
			chatManager = ChatManager.getInstanceFor(GlobalVar.connection);
		} catch (SmackException e) {
			logger.error("Service connection fail! ");
			System.exit(0);
		} catch (IOException e) {
			logger.error("IO fail!"+e);
		} catch (XMPPException e) {
			logger.error("system error from service!"+e);
		}
	}
	
	/**
	 * 初始化目录
	 * 1、将Properties转为HashMap
	 * 2、将HashMap中的目录信息初始化
	 * */
	public void initDirectory(Properties properties){
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>(properties);
	
		/**
		 * 初始化根目录
		 * */
		directory.getDirecStack().push("/");
		new DirectoryService().initDirectory(hashMap, "/");
	}
	
}
