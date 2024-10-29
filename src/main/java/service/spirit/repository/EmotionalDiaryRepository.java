package service.spirit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import service.spirit.dto.response.ResponseMentalDto;
import service.spirit.entity.Emotion;
import service.spirit.entity.EmotionalDiary;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmotionalDiaryRepository extends JpaRepository<EmotionalDiary,Long> {

    @Query("SELECT new service.spirit.dto.response.ResponseMentalDto$DiaryDateDto(ed.routineDate, ed.emotion,ed.id) " +
            "FROM EmotionalDiary ed " +
            "WHERE YEAR(ed.routineDate) = :year " +
            "AND MONTH(ed.routineDate) = :month " +
            "AND ed.userId = :userId")
    List<ResponseMentalDto.DiaryDateDto> getRecordListByDate(@Param("year") int year,
                                                             @Param("month") int month,
                                                             @Param("userId") Long userId);

    List<EmotionalDiary> findByUserId(Long userId);

    @Query("SELECT new service.spirit.dto.response.ResponseMentalDto$EmotionalDiaryDto(ed.routineDate, ed.emotion,ed.id,ed.goodContent, ed.badContent, ed.scrap, ed.aiLetter.id) " +
            "FROM EmotionalDiary ed " +
            "WHERE ed.userId = :userId AND ed.scrap = :scrap"
    )
    List<ResponseMentalDto.EmotionalDiaryDto> getScrapEmotionalDiary(@Param("userId") Long userId, @Param("scrap") Boolean scarp);


}
