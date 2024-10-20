package service.spirit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.spirit.entity.EmotionalDiary;

public interface EmotionalDiaryRepository extends JpaRepository<EmotionalDiary,Long> {
}
