package net.netosoft.oauth.monolite.exceptions;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Error code and generic description.
 * 
 * The is 3-digit structure, first digit identifies the error kind:
 * <ul>
 * <li><b>1</b>Bussines Logic</li>
 * <li><b>2</b>Technical</li>
 * <li><b>3</b>Networking</li>
 * </ul>
 * 
 * @author ernesto
 */
public enum ErrorCodes{
	
	//Main categories
	BUSSINES_ESPECIFIC_ERROR(
			1,"The current request doesnt follow the bussines rules defined"),
	TECHNICAL_ERROR(
			2,"Technical error"),
	COMMUNICATION_ERROR(
			3,"Comunnication error, please retry later"),
	
	AUTH_WITHOUT_DEVICE_ID(
			137, "Auth request doesn't include device id"),
	CIPHER_ERROR(
			261, "Error ocurred while encryption/decryption");
	
	private static final Map<Integer, ErrorCodes> CODE_MAP;
	static{
		ErrorCodes[] errors = ErrorCodes.values();
		CODE_MAP = new HashMap<>(errors.length);
		
		for(ErrorCodes error : errors){
			CODE_MAP.put(error.code, error);
		}
	}

	public int code;
	public String description;

	private ErrorCodes(int code, String description){
		this.code = code;
		this.description = description;
	}
	
	public static ErrorCodes getByCode(int code){
		ErrorCodes error = CODE_MAP.get(code);
		if(error == null){
			throw new RuntimeException("Unkown error code: " + code);
		}
		return error;
	}
}
