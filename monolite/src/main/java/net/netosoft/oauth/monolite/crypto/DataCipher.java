package net.netosoft.oauth.monolite.crypto;

import net.netosoft.oauth.monolite.exceptions.AppException;

/**
 *
 * @author ernesto
 */
public interface DataCipher{

	/**
	 * @param word message to be encrypted.
	 * @return encrypted message.
	 * @throws AppException
	 */
	String encrypt(String word) throws AppException;

	/**
	 * @param word message to be decrypted.
	 * @return decrypted message.
	 * @throws AppException
	 */
	String decrypt(String word) throws AppException;
}
