package currency.converter.registration;

/**
 * Exception for username exists.
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since  06.03.2016
 */
public class UserExistException extends Exception {

	private static final long serialVersionUID = 5215518033826176259L;
	
	@SuppressWarnings("unused")
	private UserExistException() { 
		// default constructor not allowed!
	}
	
	public UserExistException(String message) {
		super(message);
	}
}
