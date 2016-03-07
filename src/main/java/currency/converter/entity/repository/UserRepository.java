package currency.converter.entity.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import currency.converter.entity.User;

/**
 * UserRepository with CRUD operations on User entity
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since  06.03.2016
 */
public interface UserRepository extends CrudRepository<User, String> {
	
	/**
	 * Finds a list of users for the given Email address.
	 * 
	 * @param email Email address of the user
	 * s
	 * @return {@code Page<User>}
	 */
    List<User> findByEmail(String email);
}
