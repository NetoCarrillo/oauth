package net.netosoft.oauth.jwt.persistence.repositories;

import java.util.Optional;
import net.netosoft.oauth.jwt.persistence.model.ResourceOwner;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author ernesto
 */
public interface ResourceOwnerRepository
				extends CrudRepository<ResourceOwner, Long>{
	
	Optional<ResourceOwner> findByUsername(String username);
}
