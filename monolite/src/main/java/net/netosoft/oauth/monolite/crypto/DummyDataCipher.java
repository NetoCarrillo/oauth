package net.netosoft.oauth.monolite.crypto;

/**
 *
 * @author ernesto
 */
public class DummyDataCipher implements DataCipher{

	@Override
	public String encrypt(String word){
		return word;
	}

	@Override
	public String decrypt(String word){
		return word;
	}
	
}
