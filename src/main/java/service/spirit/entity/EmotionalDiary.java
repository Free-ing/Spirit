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

    @OneToOne(cascade = CascadeType.ALL)  // 주인 쪽에서 cascade 설정
    @JoinColumn(name = "ai_letter_id")    // 주인 쪽에서 외래 키 관리
    private AiLetter aiLetter;

    private Boolean scrap;

    private LocalDate routineDate;

    @OneToOne(mappedBy = "emotionalDiary")  // MentalRoutineRecord의 emotionalDiary 필드가 관계의 주인
    private MentalRoutineRecord mentalRoutineRecord;


    public void setAiLetter(AiLetter aiLetter) {
        // 기존 연관관계 제거
        if (this.aiLetter != null) {
            this.aiLetter.setEmotionalDiary(null);
        }

        this.aiLetter = aiLetter;

        // 새로운 연관관계 설정
        if (aiLetter != null) {
            aiLetter.setEmotionalDiary(this);
        }
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

//    public void addAiLetter(AiLetter aiLetter) {
//        this.aiLetter = aiLetter;
//        if (aiLetter != null) {
//            aiLetter.setEmotionalDiary(this);
//        }
//    }

    public void setMentalRoutineRecord(MentalRoutineRecord mentalRoutineRecord) {
        this.mentalRoutineRecord = mentalRoutineRecord;
        if (mentalRoutineRecord != null) {
            mentalRoutineRecord.setEmotionalDiary(this);
        }
    }

}
