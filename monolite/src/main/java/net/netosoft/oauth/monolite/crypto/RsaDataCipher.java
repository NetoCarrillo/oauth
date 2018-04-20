package net.netosoft.oauth.monolite.crypto;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import net.netosoft.oauth.monolite.exceptions.AppException;
import net.netosoft.oauth.monolite.exceptions.ErrorCodes;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author ernesto
 */
public class RsaDataCipher implements DataCipher{
	private static final Charset CHARSET = StandardCharsets.UTF_8;
	
	private final String privateKeyPath;

	private final String publicKeyPath;

	public RsaDataCipher(String privateKeyPath, String publicKeyPath){
		this.privateKeyPath = privateKeyPath;
		this.publicKeyPath = publicKeyPath;
	}

	/**
	 * @param word message to be encrypted.
	 * @return encrypted message.
	 * @throws AppException
	 */
	@Override
	public String encrypt(String word) throws AppException{
		String result;
		try{
			Cipher encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			encryptCipher.init(Cipher.ENCRYPT_MODE, loadPublicKey());
			byte[] cipherText = encryptCipher.doFinal(word.getBytes(CHARSET));
			result = Base64.encodeBase64String(cipherText);
		}catch(IOException | GeneralSecurityException e){
			throw new AppException(ErrorCodes.CIPHER_ERROR, e);
		}

		return result;
	}

	/**
	 * @param word message to be decrypted.
	 * @return decrypted message.
	 * @throws AppException
	 */
	@Override
	public String decrypt(String word) throws AppException{
		String result;
		try{
			Cipher decryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			decryptCipher.init(Cipher.DECRYPT_MODE, loadPrivateKey());
			byte[] encryptedMessage = Base64.decodeBase64(word.getBytes());
			result = new String(decryptCipher.doFinal(encryptedMessage), CHARSET);
		}catch(IOException | GeneralSecurityException e){
			throw new AppException(ErrorCodes.CIPHER_ERROR, e);
		}

		return result;
	}

	private PublicKey loadPublicKey()
			throws IOException, GeneralSecurityException{

		byte[] bytesPublicKey = Files.readAllBytes(Paths.get(publicKeyPath));
		X509EncodedKeySpec spec = new X509EncodedKeySpec(bytesPublicKey);
		KeyFactory kFactory = KeyFactory.getInstance("RSA");
		
		return kFactory.generatePublic(spec);
	}

	private PrivateKey loadPrivateKey()
			throws IOException, GeneralSecurityException{
		
		byte[] bytesPrivateKey = Files.readAllBytes(Paths.get(privateKeyPath));
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytesPrivateKey);
		KeyFactory kFactory = KeyFactory.getInstance("RSA");
		
		return kFactory.generatePrivate(spec);
	}
}
