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
import java.util.Optional;

@Repository
public interface EmotionalDiaryRepository extends JpaRepository<EmotionalDiary,Long> {

    List<EmotionalDiary> findByUserId(Long userId);

    @Query("SELECT new service.spirit.dto.response.ResponseMentalDto$EmotionalDiaryDto(ed.routineDate, ed.emotion,ed.id,ed.goodContent, ed.badContent, ed.scrap, ed.aiLetter.id) " +
            "FROM EmotionalDiary ed " +
            "WHERE ed.userId = :userId AND ed.scrap = :scrap"
    )
    List<ResponseMentalDto.EmotionalDiaryDto> getScrapEmotionalDiary(@Param("userId") Long userId, @Param("scrap") Boolean scarp);


    Optional<EmotionalDiary> findByIdAndUserId(Long id, Long userId);
}
