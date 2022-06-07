package ru.wanderer.tourist_city_bot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.wanderer.tourist_city_bot.domain.entity.City;
import ru.wanderer.tourist_city_bot.domain.projections.CityReadDto;
import ru.wanderer.tourist_city_bot.domain.projections.CityWithInformationReadDto;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT name " +
                    "FROM city"
    )
    List<CityReadDto> findAllCities();

    @Query(
            nativeQuery = true,
            value = "SELECT * " +
                    "FROM city " +
                    "WHERE name ILIKE :name"
    )
    Optional<City> findByName(@Param("name") String name);

    @Query(
            nativeQuery = true,
            value = "SELECT c.name AS name, " +
                    "       i.id AS id, " +
                    "       i.text AS text " +
                    "FROM city c " +
                    "JOIN information i on c.id = i.city_id " +
                    "WHERE c.name ILIKE :name"
    )
    List<CityWithInformationReadDto> findInformationByCityName(@Param("name") String name);
}
