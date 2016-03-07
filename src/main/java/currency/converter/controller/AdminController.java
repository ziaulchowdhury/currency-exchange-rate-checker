package currency.converter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import currency.converter.entity.Currency;
import currency.converter.entity.User;
import currency.converter.entity.UserAuthority;
import currency.converter.entity.repository.CurrencyRespository;
import currency.converter.entity.repository.UserAuthorityRepository;
import currency.converter.entity.repository.UserRepository;
import currency.converter.login.UserService;

/**
 * Controller for users with ADMIN role.
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAuthorityRepository userAuthorityRespository;

	@Autowired
	private CurrencyRespository currencyRepository;

	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	/**
	 * Displays all persisted users, authorities and currencies.
	 * 
	 * @param model UI model
	 * @return showUsers view
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showUsers(Model model) {

		logger.info("Admin page is accessed by : " + userService.getLoggedInUserName());

		if (!userService.hasAdminRole()) {
			return "redirect:userhome";
		}

		Iterable<User> users = userRepository.findAll();
		model.addAttribute("users", users);

		Iterable<Currency> currencies = currencyRepository.findAll();
		model.addAttribute("currencies", currencies);

		Iterable<UserAuthority> authorities = userAuthorityRespository.findAll();
		model.addAttribute("authorities", authorities);

		return "showUsers";
	}
}
