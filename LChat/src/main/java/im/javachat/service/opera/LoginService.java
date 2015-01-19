package im.javachat.service.opera;

import java.io.IOException;

import javax.security.sasl.SaslException;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;

import im.javachat.service.GlobalVar.GlobalVar;

/**
 * 处理登录业务
 * */
public class LoginService {
	
	/**
	 * 用户登录
	 * @param username 用户jid(不包括设备名)
	 * @param password 密码
	 * @return 成功返回true
	 * */
	public boolean login (String username,String password) throws SaslException, XMPPException, SmackException, IOException{
		GlobalVar.connection.login(username, password);
		return true;
	}
	public void loginout(){
		try {
			GlobalVar.connection.disconnect();
		} catch (NotConnectedException e) {
			GlobalVar.logger.error("loginout "+e);
		}
	}
}
