package net.netosoft.oauth.monolite.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

/**
 *
 * @author ernesto
 */
public class AppExceptionTranslator extends DefaultWebResponseExceptionTranslator{
	
	private static final Logger LOGGER =
			LoggerFactory.getLogger(AppExceptionTranslator.class);

	@Override
	public ResponseEntity<OAuth2Exception> translate(Exception exception)
			throws Exception{

		ResponseEntity<OAuth2Exception> response;
		if(exception.getCause() instanceof AppException){
			response = handleDomainException(exception);
		}else{
			LOGGER.debug("Delegate exception translate to super ({})",
					exception.getClass().getName());
			LOGGER.error("Delegated exception trace", exception);
			response = super.translate(exception);
		}
		
		return response;
	}
	
	private ResponseEntity<OAuth2Exception> handleDomainException(Exception ex){
		AppException es = (AppException)ex.getCause();
		LOGGER.debug("Handling EspaciaException code:{}", es.getCode());

		HttpStatus status;
		if(es.getCode() == ErrorCodes.AUTH_WITHOUT_DEVICE_ID.code){
			status = HttpStatus.FORBIDDEN;
		}else{
			status = HttpStatus.CONFLICT;
		}

		return new ResponseEntity<>(
				new OAuth2Exception(ex.getMessage(), es),status);
	}
}