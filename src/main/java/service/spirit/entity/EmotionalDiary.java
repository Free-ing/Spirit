package service.spirit.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import service.spirit.base.BaseEntity;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "emotional_diary")  // 테이블명 명시적 지정
public class EmotionalDiary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String goodContent;
    private String badContent;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private Emotion emotion;

    private Boolean getAiLetter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ai_letter_id")
    private AiLetter aiLetter;

    private Boolean scrap;

    private LocalDate routineDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mental_routine_record_id")
    private MentalRoutineRecord mentalRoutineRecord;


    public void setAiLetter (AiLetter aiLetter){
        this.aiLetter = aiLetter;
    }

    @Builder
    public EmotionalDiary(MentalRoutineRecord mentalRoutineRecord, LocalDate routineDate, String goodContent, Boolean scrap, String badContent, Long userId, Emotion emotion, Boolean getAiLetter) {
        this.mentalRoutineRecord = mentalRoutineRecord;
        this.goodContent = goodContent;
        this.badContent = badContent;
        this.userId = userId;
        this.emotion = emotion;
        this.getAiLetter = getAiLetter;
        this.scrap = scrap;
        this.routineDate = routineDate;
    }

    public void changeScrap(Boolean scrap){
        this.scrap = scrap;
    }

    public void addAiLetter(AiLetter aiLetter){
        this.aiLetter = aiLetter;
    }

}
