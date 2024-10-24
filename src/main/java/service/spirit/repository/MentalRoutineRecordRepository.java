package service.spirit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import service.spirit.dto.response.ResponseMentalDto;
import service.spirit.entity.MentalRoutine;
import service.spirit.entity.MentalRoutineRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MentalRoutineRecordRepository extends JpaRepository<MentalRoutineRecord, Long> {
    Optional<MentalRoutineRecord> findByMentalRoutineAndRoutineDateAndUserId(
            MentalRoutine mentalRoutine,
            LocalDate routineDate,
            Long userId
    );

    @Query("select new service.spirit.dto.response.ResponseMentalDto$DayRoutineDto(mr.id, mr.complete, mr.mentalRoutine.mentalRoutineName, mr.mentalRoutine.basicService) " +
            "from MentalRoutineRecord mr where mr.routineDate =:date and mr.status =:status and mr.userId =:userId")
    List<ResponseMentalDto.DayRoutineDto> getDayRoutine(LocalDate date, Long userId ,Boolean status);

}
