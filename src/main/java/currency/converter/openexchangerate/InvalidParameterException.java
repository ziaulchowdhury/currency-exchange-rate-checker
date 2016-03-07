package currency.converter.openexchangerate;

/**
 * Exception for invalid argument for getting currency exchange rate values.
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
public class InvalidParameterException extends Exception {

    private static final long serialVersionUID = 1904741860179449092L;

    @SuppressWarnings("unused")
    private InvalidParameterException() {
        // exception must have a message
    };

    public InvalidParameterException(String message) {
        super(message);
    }
}
