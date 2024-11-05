package service.spirit.dto.response;

import ch.qos.logback.core.joran.event.BodyEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import service.spirit.entity.Emotion;
import service.spirit.entity.SpiritType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
        private String spiritName;
        private String imageUrl;
        private Long routineId;
        private Boolean monday;
        private Boolean tuesday;
        private Boolean wednesday;
        private Boolean thursday;
        private Boolean friday;
        private Boolean saturday;
        private Boolean sunday;
        private Boolean status;
        private LocalTime startTime;
        private LocalTime endTime;
        private String explanation;}

    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class DiaryDateDto{
        private LocalDate date;
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
        private Long letterId;
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
        private String Name;
        private String imageUrl;
        private Long routineId;
        private Boolean monthday;
        private Boolean tuesday;
        private Boolean wednesday;
        private Boolean thursday;
        private Boolean friday;
        private Boolean saturday;
        private Boolean sunday;
        private Boolean status;
        private LocalTime startTime;
        private LocalTime endTime;
        private String explanation;
        private SpiritType basicService;

        private Long recordId;
        private Boolean complete;

    }

    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class DiaryTrackerDto{
        private Long id;
        private LocalDate routineDate;
    }

    @NoArgsConstructor
    @Builder
    @Getter
    @AllArgsConstructor
    public static class AiLetterDto{
        private String content;
    }
}
