package net.netosoft.oauth.monolite;

import net.netosoft.oauth.monolite.crypto.DataCipher;
import net.netosoft.oauth.monolite.crypto.DummyDataCipher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author ernesto
 */
@SpringBootApplication
public class MonoliteApp{
	public static void main(String[] args){
		SpringApplication.run(MonoliteApp.class, args);
	}
	
	@Bean
	public DataCipher dataCipher(){
		return new DummyDataCipher();
	}
}
