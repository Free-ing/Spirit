package service.spirit.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

    }

}
