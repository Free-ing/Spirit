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
@Table(name = "ai_letter")
public class AiLetter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")  // PostgreSQL에서는 TEXT 사용
    private String content;

    @OneToOne(mappedBy = "aiLetter", cascade = CascadeType.ALL)  // EmotionalDiary가 관계의 주인임을 명시
    private EmotionalDiary emotionalDiary;


    @Builder
    public AiLetter(String content, EmotionalDiary emotionalDiary) {
        this.content = content;
        this.emotionalDiary = emotionalDiary;
    }

    protected void setEmotionalDiary(EmotionalDiary emotionalDiary) {
        this.emotionalDiary = emotionalDiary;
    }
}
