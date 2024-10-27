package service.spirit.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import service.spirit.entity.Emotion;

import java.time.LocalTime;

public class MentalDto {

    @Getter
    @Builder
    @Setter
    @AllArgsConstructor
    public static class mentalRoutineDto{
        private String routineName;
        private Long userId;
        private String imageUrl;
        private Boolean monday;
        private Boolean tuesday;
        private Boolean wednesday;
        private Boolean thursday;
        private Boolean friday;
        private Boolean saturday;
        private Boolean sunday;
        private LocalTime startTime;
        private LocalTime endTime;
        private String explanation;
        private Boolean status;

    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class emotionalDiaryDto{
        private String wellDone;
        private String hardWork;
        private Boolean getAiLetter;
        private Emotion emotion;
        private Long userId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class AiLetterDto{
        private String wellDone;
        private String hardWork;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class mentalRoutineUpdateDto{
        private String spiritName;
        private String explanation;
        private Boolean sunday;
        private Boolean saturday;
        private Boolean thursday;
        private Boolean friday;
        private Boolean wednesday;
        private Boolean tuesday;
        private Boolean monday;
        private LocalTime endTime;
        private LocalTime startTime;
        private String imageUrl;
    }

}
