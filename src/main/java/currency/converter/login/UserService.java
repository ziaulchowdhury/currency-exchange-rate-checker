package currency.converter.login;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import currency.converter.entity.UserRole;

/**
 * Service for getting properties of logged in user.
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
@Component
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	/**
	 * Returns the username of the logged in user.
	 */
	public String getLoggedInUserName() {

		User currentUser = getLoggedInUser();
		String userName = currentUser.getUsername();
		logger.info("Currently logged user : " + userName);

		return userName;
	}

	/**
	 * Checks if currently logged in user has admin role
	 */
	public boolean hasAdminRole() {

		User currentUser = getLoggedInUser();

		Collection<GrantedAuthority> authorities = currentUser.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			if (UserRole.ROLE_ADMIN.name().equals(authority.getAuthority())) {
				logger.info("Currently logged in user has admin role!");
				return true;
			}
		}

		return false;
	}

	private User getLoggedInUser() {

		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) a.getPrincipal();
		return currentUser;
	}
}
