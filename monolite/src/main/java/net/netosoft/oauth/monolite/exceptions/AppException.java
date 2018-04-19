package net.netosoft.oauth.monolite.exceptions;

/**
 *
 * @author ernesto
 */
public class AppException extends Exception{

	
	private final int code;
	
	public AppException(ErrorCodes error){
		super(error.description);
		this.code = error.code;
	}
	
	public AppException(ErrorCodes error, Throwable cause){
		super(error.description, cause);
		this.code = error.code;
	}
	
	public AppException(ErrorCodes error, String message){
		super(error.description + ':' + message);
		this.code = error.code;
	}

	public AppException(ErrorCodes error, String message, Throwable cause){
		super(error.description + ':' + message, cause);
		this.code = error.code;
	}

	public int getCode(){
		return code;
	}
}
