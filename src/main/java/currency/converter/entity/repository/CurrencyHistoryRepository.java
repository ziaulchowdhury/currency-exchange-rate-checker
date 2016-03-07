package currency.converter.entity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import currency.converter.entity.CurrencyHistory;

/**
 * CurrencyHistoryRepository with CRUD operations on CurrencyHistory entity
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
public interface CurrencyHistoryRepository extends PagingAndSortingRepository<CurrencyHistory, Long> {

	/**
	 * Finds a list of exchange histories based on the given page
	 * 
	 * @param userName User name
	 * @param page Contains limit and sorting options
	 * 
	 * @return {@code Page<CurrencyHistory>}
	 */
	Page<CurrencyHistory> findByUserName(String userName, Pageable page);
}