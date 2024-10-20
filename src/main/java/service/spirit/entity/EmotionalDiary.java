package service.spirit.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class EmotionalDiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void setAiLetter (AiLetter aiLetter){
        this.aiLetter = aiLetter;
    }

    @Builder
    public EmotionalDiary(String goodContent, String badContent, Long userId, Emotion emotion, Boolean getAiLetter) {
        this.goodContent = goodContent;
        this.badContent = badContent;
        this.userId = userId;
        this.emotion = emotion;
        this.getAiLetter = getAiLetter;
    }
}
