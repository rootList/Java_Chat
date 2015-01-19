package im.javachat.service.command;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;

/**
 * 监听键盘上的特殊命令
 * */
public class KeyboardMonitor implements AWTEventListener {

	@Override
	public void eventDispatched(AWTEvent event) {
		// TODO Auto-generated method stub
		if (event.getClass() == KeyEvent.class) {
			// 被处理的事件是键盘事件.
			KeyEvent keyEvent = (KeyEvent) event;
			if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {
				// 按下时你要做的事情
				switch (keyEvent.getKeyCode()) {
				case KeyEvent.VK_TAB:
					System.out.println("tab");
					break;
				default:
					break;
				}
			} else if (keyEvent.getID() == KeyEvent.KEY_RELEASED) {
			}

		}
	}

}
