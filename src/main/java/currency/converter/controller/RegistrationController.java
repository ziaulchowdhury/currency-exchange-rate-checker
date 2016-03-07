package currency.converter.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import currency.converter.entity.User;
import currency.converter.registration.RegistrationService;
import currency.converter.registration.UserExistException;

/**
 * Controller for registering new user.
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since  06.03.2016
 */
@Controller
@RequestMapping("/register*")
public class RegistrationController extends ControllerCache {
	
	@Autowired
	private RegistrationService registrationService;
	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
	
	/**
	 * Initializes register page
	 * 
	 * @param model UI model
	 * 
	 * @return register view
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showRegistrationForm(Model model) {
		
		User user = new User();
		model.addAttribute("user", user);
		
		model.addAttribute("countries", getCountries());
		
        return "register";
	}
	
	/**
	 * Make registration of new user when there is no validation error and there is existing username.
	 *  
	 * @param model UI model
	 * @param user {@link User} model with all form inputs
	 * @param bindingResult Binding result for validation
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
    public String registerUser(Model model, @Valid User user, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
		
		model.addAttribute("countries", getCountries());
		
		if (bindingResult.hasErrors()) {
			
			model.addAttribute("user", user);
			return "register";
        }
		
		try {
			User savedUser = registrationService.createUser(user);
			logger.info("User created : " + savedUser.toString());
			
			String successMessage = "User '" + savedUser.getUsername() + "' got created successfully. Please click on the login link to access the system.";
			model.addAttribute("success", successMessage);
			
		} catch (UserExistException uee) {
			model.addAttribute("registrationError", uee.getMessage());
		}
		
		return "register";
    }
}
