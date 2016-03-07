package currency.converter;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import currency.converter.entity.Country;
import currency.converter.entity.Currency;
import currency.converter.entity.User;
import currency.converter.entity.UserAuthority;
import currency.converter.entity.repository.CountryRepository;
import currency.converter.entity.repository.CurrencyRespository;
import currency.converter.entity.repository.UserAuthorityRepository;
import currency.converter.entity.repository.UserRepository;

/**
 * Main entry point of the application.
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since  06.03.2016
 */
@SpringBootApplication
public class Application {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserAuthorityRepository userAuthorityRepository;
	
	@Autowired
	private CurrencyRespository currencyRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) throws Throwable {
		
		SpringApplication.run(Application.class);
	}
	
	/**
	 * Insert default data to the database
	 */
	@SuppressWarnings("unused")
	@Bean
	public CommandLineRunner insertData() {
		return (args) -> {
			
			// currencies
			log.info("Creating currencies ...........");
			Currency eur = currencyRepository.save(new Currency("EURO", "EUR"));
			Currency usd = currencyRepository.save(new Currency("United States Dollar", "USD"));
			Currency gbp =  currencyRepository.save(new Currency("Pound Sterling", "GBP"));
			Currency nzd =  currencyRepository.save(new Currency("New Zealand Dollarg", "NZD"));
			Currency aud =  currencyRepository.save(new Currency("Australian Dollar", "AUD"));
			Currency jpy =  currencyRepository.save(new Currency("Japanese Yen", "JPY"));
			Currency huf =  currencyRepository.save(new Currency("Hungarian Forint", "HUF"));
			log.info("Currency creation completed!");
			
			// save countries
			log.info("Creating countriess ...........");
			Country germany = countryRepository.save(new Country("Germany", eur.getId()));
			Country usa = countryRepository.save(new Country("USA", usd.getId()));
			Country uk = countryRepository.save(new Country("UK", gbp.getId()));
			Country newZealand = countryRepository.save(new Country("New Zealand", nzd.getId()));
			Country australia = countryRepository.save(new Country("Australia", aud.getId()));
			Country japan = countryRepository.save(new Country("Japan", jpy.getId()));
			Country hungary = countryRepository.save(new Country("Hungary", huf.getId()));
			Country france = countryRepository.save(new Country("France", eur.getId()));
			Country netherlands = countryRepository.save(new Country("Netherlands", eur.getId()));
			Country greece = countryRepository.save(new Country("Greece", eur.getId()));
			Country austria = countryRepository.save(new Country("Austria", eur.getId()));
			Country belgium = countryRepository.save(new Country("Belgium", eur.getId()));
			Country italy = countryRepository.save(new Country("Italy", eur.getId()));
			log.info("Country creation completed!");
			
			// save a couple of customers
			log.info("Creating users & authories ...........");
			Calendar cal = Calendar.getInstance();
			cal.set(1980, 1, 1);
			User johnDoe = userRepository.save(new User("John Doe", "john.doe", "Qweqwe123", "john.doe@email.com", cal.getTime(), "Karlsruhe", "76123", germany.getId(), true));
			
			cal.set(1985, 5, 15);
			User romel = userRepository.save(new User("Romel Chow", "romel", "test123", "romel@email.com", cal.getTime(), "Sydney", "2000", australia.getId(), true));
			
			UserAuthority johnAdmin = userAuthorityRepository.save(new UserAuthority(johnDoe.getUsername(), "ROLE_ADMIN"));
			UserAuthority romelAdmin = userAuthorityRepository.save(new UserAuthority(romel.getUsername(), "ROLE_USER"));
			log.info("User & authority creation completed!");
		};
	}
}
