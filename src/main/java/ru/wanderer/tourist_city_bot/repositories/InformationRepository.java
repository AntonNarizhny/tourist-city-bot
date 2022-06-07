package ru.wanderer.tourist_city_bot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.wanderer.tourist_city_bot.domain.entity.Information;

import java.util.Optional;

@Repository
public interface InformationRepository extends JpaRepository<Information,Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT id, " +
                    "       text " +
                    "FROM information " +
                    "WHERE id = :id"
    )
    Optional<Information> findInformationById(@Param("id") Long id);
}
