package im.javachat.exception;

/**
 * 参数异常
 * */
public class ParameterException extends Exception{

	//异常消息
	private String exceptionMessage;
	private static final long serialVersionUID = 1L;
	
	public ParameterException(String message) {
		this.exceptionMessage = message;
	}
	
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	
	
}
