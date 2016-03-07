package currency.converter.entity.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import currency.converter.entity.UserAuthority;

/**
 * UserAuthorityRepository with CRUD operations on UserAuthority entity
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
public interface UserAuthorityRepository extends CrudRepository<UserAuthority, Long> {

	/**
	 * Finds a list of authorities of the given user name.
	 * 
	 * @param username Username of the user
	 * 
	 * @return {@code Page<UserAuthority>}
	 */
	List<UserAuthority> findByUsername(String username);
}
