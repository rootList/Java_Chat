package im.javachat.controll;

import im.javachat.service.GlobalVar.GlobalVar;
import im.javachat.service.opera.LoginService;

import java.io.Console;
import java.io.IOException;

import javax.security.sasl.SaslException;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

/**
 * 用户登录
 * */
public class LoginControll {
	String username;
	String password;
	/**
	 * 输入用户名和密码
	 * */
	public void login(){
		boolean bool = false;
		GlobalVar init = new GlobalVar();
		while(!bool){
			//当密码输入错误后需要重新初始化
			init.init();
			
			username = "";
			password = "";
			System.out.print("input you name :");
			username = GlobalVar.input.scanner();
			GlobalVar.userjid = username+"@localhost.localdomain";
			System.out.print("input you password :");
			Console console = System.console();
			if(console!=null){
				System.out.print("["+GlobalVar.userjid+" "+GlobalVar.directory.getDirecStack().lastElement()+"]# ");
				password = new String(console.readPassword());
			}else{
				password = GlobalVar.input.scanner();
			}
			try {
				bool = new LoginService().login(username, password);
			} catch (SaslException e) {
				GlobalVar.logger.info(e);
			} catch (XMPPException e) {
				System.out.println("userjid or password error");
			} catch (SmackException e) {
				GlobalVar.logger.info(e);
			} catch (IOException e) {
				e.printStackTrace();
			}catch (Exception e) {
			}
		}
		
		System.out.println("login success:");
	}
}
