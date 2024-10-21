package service.spirit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import service.spirit.entity.AiLetter;

public interface AiLetterRepository extends JpaRepository<AiLetter, Long> {
}
