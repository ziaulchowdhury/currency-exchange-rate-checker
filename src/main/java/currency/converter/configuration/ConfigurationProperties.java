package currency.converter.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration contains properties from config.properties file.
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
@Configuration
@PropertySource("classpath:/config.properties")
public class ConfigurationProperties {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationProperties.class);

    @Value("${openexchange.api.key}")
    private String openExchangeApiKey;

    @Value("${openexchange.commercial.license}")
    private boolean openExchangeCommercialLicense;

    public String getOpenExchangeApiKey() {
        logger.info("OpenExchange API KEY from property file >>>>>>>> " + openExchangeApiKey);
        return openExchangeApiKey;
    }

    public boolean isOpenExchangeCommercialLicense() {
        logger.info("OpenExchange commercial license >>>>>>>> " + openExchangeCommercialLicense);
        return openExchangeCommercialLicense;
    }
}
