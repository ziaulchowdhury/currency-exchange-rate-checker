package currency.converter.entity.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import currency.converter.entity.Currency;

/**
 * CurrencyRespository with CRUD operations on Currency entity
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
public interface CurrencyRespository extends CrudRepository<Currency, Long> {

    /**
     * Finds a list of currency by using the given code. Ideally, only one currency should exist for the given code.
     * 
     * @param code Currency codes
     * 
     * @return {@code Page<Currency>}
     */
    List<Currency> findByCode(String code);
}
