package service.spirit.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import javax.swing.*;
import java.time.LocalTime;

public class SpiritDto {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class spiritRoutineDto{
        private String routineName;
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

    }

}
