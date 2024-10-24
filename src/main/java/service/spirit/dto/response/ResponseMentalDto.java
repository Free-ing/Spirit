package service.spirit.dto.response;

import ch.qos.logback.core.joran.event.BodyEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import service.spirit.entity.Emotion;
import service.spirit.entity.SpiritType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ResponseMentalDto {

    @Getter
    @AllArgsConstructor
    @Builder
    public static class ResponseAiLetter{
        private String aiLetter;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class MentalRoutineDto{
        private String hobbyName;
        private String imageUrl;
        private Long routineId;
        private Boolean status;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class DiaryDateDto{
        private LocalDateTime date;
        private Emotion emotion;
        private Long diaryId;
    }
    @Getter
    @AllArgsConstructor
    @Builder
    public static class EmotionalDiaryDto{
        private LocalDate date;
        private Emotion emotion;
        private Long diaryId;
        private String wellDone;
        private String hardWork;
        private Boolean scrap;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class RoutineInfoDto{
        private String explanation;
        private String MetalRoutineName;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DayRoutineDto{
        private Long id;
        private Boolean complete;
        private String routineName;
        private SpiritType basicService;
    }
}
