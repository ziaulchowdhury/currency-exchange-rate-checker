package currency.converter.entity.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import currency.converter.entity.CurrencyRate;

/**
 * CurrencyRateRepository with CRUD operations on CurrencyRate entity
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
public interface CurrencyRateRepository extends CrudRepository<CurrencyRate, Long> {

    /**
     * Finds a list of currency rates for the given historyId
     * 
     * @param historyId Currency history id
     * 
     * @return {@code Page<CurrencyHistory>}
     */
    List<CurrencyRate> findByHistoryId(long historyId);
}
