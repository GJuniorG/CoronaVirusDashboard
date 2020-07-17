package de.java.application.Covid19_Tracker.Database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlobalEntityRepository extends JpaRepository<GlobalEntity, Long> {

    GlobalEntity findByCountryAndState(String country, String state);

    List<GlobalEntity> findAll();

//    List<GlobalEntity> findAllByCountry();
//
//    List<GlobalEntity> findAllByState(String state);
//
//    List<GlobalEntity> findAllByLatestTotalCases();
//
//    List<GlobalEntity> findAllByDiffFromPrevDay();
//
//    List<GlobalEntity> findAllByTotalRecovered();
//
//    List<GlobalEntity> findAllByTotalDeaths(String totalDeaths);
//
//    List<GlobalEntity> findAllByDeathFromPreviousDay(String deathFromPreviousDay);
//
//    List<GlobalEntity> findAllByRecoveredFromPreviousDay(String recoveredFromPreviousDay);

}
