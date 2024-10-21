package service.spirit.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
    }
}
