package service.spirit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.spirit.entity.MentalRoutine;
import service.spirit.entity.MentalRoutineRecord;

import java.time.LocalDate;
import java.util.Optional;

public interface MentalRoutineRecordRepository extends JpaRepository<MentalRoutineRecord, Long> {
    Optional<MentalRoutineRecord> findByMentalRoutineAndRoutineDateAndUserId(
            MentalRoutine mentalRoutine,
            LocalDate routineDate,
            Long userId
    );}
