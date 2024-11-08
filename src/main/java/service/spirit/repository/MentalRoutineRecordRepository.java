package service.spirit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import service.spirit.dto.response.ResponseMentalDto;
import service.spirit.entity.MentalRoutine;
import service.spirit.entity.MentalRoutineRecord;
import service.spirit.entity.SpiritType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MentalRoutineRecordRepository extends JpaRepository<MentalRoutineRecord, Long> {
    Optional<MentalRoutineRecord> findByMentalRoutineAndRoutineDateAndUserId(
            MentalRoutine mentalRoutine,
            LocalDate routineDate,
            Long userId
    );

    @Query("select new service.spirit.dto.response.ResponseMentalDto$DayRoutineDto(mr.mentalRoutine.mentalRoutineName,mr.mentalRoutine.imageUrl,mr.mentalRoutine.Id, mr.mentalRoutine.monday, " +
            "mr.mentalRoutine.tuesday, mr.mentalRoutine.wednesday, mr.mentalRoutine.thursday, mr.mentalRoutine.friday,mr.mentalRoutine.saturday, mr.mentalRoutine.sunday," +
            "mr.mentalRoutine.status, mr.mentalRoutine.startTime, mr.mentalRoutine.endTime, mr.mentalRoutine.explanation,mr.mentalRoutine.basicService,mr.id, mr.complete)"+
            "from MentalRoutineRecord mr where mr.routineDate =:date and mr.status =:status and mr.userId =:userId")
    List<ResponseMentalDto.DayRoutineDto> getDayRoutine(LocalDate date, Long userId ,Boolean status);


    @Query("SELECT new service.spirit.dto.response.ResponseMentalDto$DayCompleteRoutine(m.routineDate) " +
            "FROM MentalRoutineRecord m " +
            "WHERE m.userId = :userId " +
            "AND m.routineDate BETWEEN :startDate AND :endDate " +
            "AND m.complete = true " +
            "GROUP BY m.routineDate " +
            "ORDER BY m.routineDate")
    List<ResponseMentalDto.DayCompleteRoutine> findCompletedDatesByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );


    @Query("SELECT new service.spirit.dto.response.ResponseMentalDto$DiaryDateDto(" +
            "mr.routineDate, " +
            "mr.emotionalDiary.emotion, " +
            "mr.emotionalDiary.id, " +
            "mr.id) " +  // mentalRoutineRecord.id를 직접 전달
            "FROM MentalRoutineRecord mr " +
            "WHERE YEAR(mr.routineDate) = :year " +
            "AND MONTH(mr.routineDate) = :month " +
            "AND mr.userId = :userId " +
            "AND mr.mentalRoutine.basicService = :spiritType AND mr.status = true")
    List<ResponseMentalDto.DiaryDateDto> getRecordListByDate(
            @Param("year") int year,
            @Param("month") int month,
            @Param("userId") Long userId,
            @Param("spiritType") SpiritType spiritType);
}

