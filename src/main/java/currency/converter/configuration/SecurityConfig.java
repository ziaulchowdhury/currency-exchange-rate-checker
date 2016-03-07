package currency.converter.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

/**
 * Configures security in the application.
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since  06.03.2016
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;
	
	/**
	 * Sets permissions of different URLs in the application. URL's which are not specified requires
	 * authentication to access.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers("/", "/register**").permitAll()
		.antMatchers("/error").permitAll()
		.anyRequest().authenticated()
		.antMatchers("/admin**").hasAuthority("ROLE_ADMIN")
		.and()
		.formLogin().loginPage("/login").defaultSuccessUrl("/userhome").permitAll().and()
		.logout().permitAll();
	}

	/**
	 * Configures JDBC authentication
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication().dataSource(this.dataSource)
			.usersByUsernameQuery("select username, password, enable from users where username =?")
			.authoritiesByUsernameQuery("select username, authority from authorities where username =?");
	}
	
	/**
     *  Enables Spring security dialect for Thymeleaf
     */
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
    	return new SpringSecurityDialect();
    }
}
