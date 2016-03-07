package currency.converter.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import currency.converter.entity.User;
import currency.converter.entity.UserAuthority;
import currency.converter.entity.UserRole;
import currency.converter.entity.repository.UserAuthorityRepository;
import currency.converter.entity.repository.UserRepository;

/**
 * Service for creating user.
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
@Component
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthorityRepository userAuthorityRespository;

    /**
     * Creates a new user when username does not exist in the system.
     * 
     * @param user User to be created
     * 
     * @return Saved {@link User}
     * 
     * @throws UserExistException when username exists
     */
    public User createUser(User user) throws UserExistException {

        // check if user exists
        if (userRepository.findOne(user.getUsername()) != null) {
            throw new UserExistException(user.getUsername() + " exists in the system. Please choose another username!");
        }

        // Create the user
        user.setEnable(true);
        User savedUser = userRepository.save(user);

        userAuthorityRespository.save(new UserAuthority(savedUser.getUsername(), UserRole.ROLE_USER.name()));

        return savedUser;
    }
}
