package mx.anzen.app.bank.clearing.persistence.repositories;

import com.example.persistence.model.User;
import com.example.persistence.repositories.UserRepository;
import mx.anzen.app.bank.clearing.persistence.TestEnvConfig;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ernesto
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestEnvConfig.class})
@TestExecutionListeners({
	DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class})
@Transactional
public class UserRepositoryTest{
	
	@Autowired
	private UserRepository userRepo;
	
	@Test
	@Transactional
	public void create(){
		User user = new User();
		
		User saved = userRepo.save(user);
		
		Assert.assertNotNull("Saved transfer is null", saved);
	}
}
