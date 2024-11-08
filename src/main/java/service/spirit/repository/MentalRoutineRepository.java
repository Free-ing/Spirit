package service.spirit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import service.spirit.dto.response.ResponseMentalDto;
import service.spirit.entity.MentalRoutine;

import java.util.List;
import java.util.Optional;

@Repository
public interface MentalRoutineRepository extends JpaRepository<MentalRoutine,Long> {
    List<MentalRoutine> findByUserId(Long userId);

    @Query("select new service.spirit.dto.response.ResponseMentalDto$RoutineInfoDto(mr.explanation, mr.mentalRoutineName) " +
            "from MentalRoutine mr where mr.id = :routineId")
    Optional<ResponseMentalDto.RoutineInfoDto> getRoutineInfo(Long routineId);


    @Query("SELECT m FROM MentalRoutine m " +
            "LEFT JOIN FETCH m.mentalRoutineRecordList mr " +
            "WHERE m.userId = :userId " +
            "AND (mr IS NULL OR (YEAR(mr.routineDate) = :year AND MONTH(mr.routineDate) = :month)) AND mr.complete = true")
    List<MentalRoutine> findAllWithRecordsByUserId(
            @Param("userId") Long userId,
            @Param("year") int year,
            @Param("month") int month
    );


    @Query("SELECT mr FROM MentalRoutine mr WHERE mr.status = true")
    List<MentalRoutine> findActiveRoutines();

    Optional<MentalRoutine> findByIdAndUserId(Long id , Long userId);
}
