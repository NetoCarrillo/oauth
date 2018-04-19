package net.netosoft.oauth.monolite.security.services;

import javax.servlet.http.HttpServletRequest;
import net.netosoft.oauth.monolite.crypto.DataCipher;
import net.netosoft.oauth.monolite.exceptions.AppException;
import net.netosoft.oauth.monolite.exceptions.ErrorCodes;
import net.netosoft.oauth.monolite.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author ernesto
 */
@Component
public class SecurityUtils{
	
	@Autowired
	private DataCipher cipher;
	
	public String getImei() throws AppException{
		HttpServletRequest request = getCurrentRequest();
		
		String dheader = request.getHeader(SecurityConstants.HEADER_IMEI);
		if(StringUtils.isEmpty(dheader)){
			throw new AppException(ErrorCodes.AUTH_WITHOUT_DEVICE_ID);
		}

		String deviceId = cipher.decrypt(dheader);
		if(!StringUtils.hasText(deviceId)){
			throw new AppException(ErrorCodes.AUTH_WITHOUT_DEVICE_ID);
		}
		
		return deviceId;
	}
	
	public HttpServletRequest getCurrentRequest(){
		return ((ServletRequestAttributes)RequestContextHolder
					.currentRequestAttributes()).getRequest();
	}
	
}
