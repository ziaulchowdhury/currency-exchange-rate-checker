package currency.converter.entity.repository;

import org.springframework.data.repository.CrudRepository;

import currency.converter.entity.Country;

/**
 * CountryRepository with CRUD operations on Country entity
 * 
 * @author Ziaul Chowdhury (ziaul.chowdhury@tu-dortmund.de)
 * @since 06.03.2016
 */
public interface CountryRepository extends CrudRepository<Country, Long> {

}