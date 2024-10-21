package service.spirit.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import service.spirit.base.BaseEntity;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
public class AiLetter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @OneToOne(mappedBy = "aiLetter")
    private EmotionalDiary emotionalDiary;

    @Builder
    public AiLetter(String content, EmotionalDiary emotionalDiary) {
        this.content = content;
        this.emotionalDiary = emotionalDiary;
    }
}
