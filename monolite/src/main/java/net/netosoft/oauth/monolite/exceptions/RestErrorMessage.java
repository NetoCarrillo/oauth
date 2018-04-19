package net.netosoft.oauth.monolite.exceptions;

public class RestErrorMessage{

	private String message;

	private Integer code;

	public RestErrorMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public Integer getCode(){
		return code;
	}

	public void setCode(Integer code){
		this.code = code;
	}

}
