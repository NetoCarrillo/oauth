package net.netosoft.oauth.monolite.security;

/**
 *
 * @author ernesto
 */
public final class SecurityConstants{
	private SecurityConstants(){
		super();
	}
	
	public static final String HEADER_IMEI = "X-IMEI";
	public static final String HEADER_AUTH = "Authorization";
	public static final String ALLOW_METHODS =
			"POST, PUT, GET, OPTIONS, DELETE";
	public static final String ALLOW_HEADERS = 
			"Access-Control-Allow-Credentials, " +
			"Access-Control-Allow-Origin, " +
			"Access-Control-Allow-Headers, " +
			"Access-Control-Allow-Methods, " +
			"Access-Control-Max-Age, " +
			"Access-Control-Request-Method, " +
			"Access-Control-Request-Headers, " +
			HEADER_AUTH + ", " +
			"Origin, " +
			"Accept, " +
			"X-Requested-With, " +
			HEADER_IMEI + ", " +
			"Content-Type";
	public static final String TOKEN_MAX_AGE = String.valueOf(Integer.MAX_VALUE);
	public static final String TOKEN_TYPE = "bearer";
	public static final String WEB_APP_CLIENT = "web";
	


}
