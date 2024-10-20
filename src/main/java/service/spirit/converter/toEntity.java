package service.spirit.converter;

import service.spirit.dto.response.MentalDto;
import service.spirit.entity.MentalRoutine;

public class toEntity {
    public static MentalRoutine toMentalRoutine(MentalDto.mentalRoutineDto mentalRoutineDto){
        return MentalRoutine.builder()
                .metalRoutineName(mentalRoutineDto.getRoutineName())
                .userId(mentalRoutineDto.getUserId())
                .monday(mentalRoutineDto.getMonday())
                .tuesday(mentalRoutineDto.getTuesday())
                .wednesday(mentalRoutineDto.getWednesday())
                .thursday(mentalRoutineDto.getThursday())
                .friday(mentalRoutineDto.getFriday())
                .saturday(mentalRoutineDto.getSaturday())
                .sunday(mentalRoutineDto.getSunday())
                .explanation(mentalRoutineDto.getExplanation())
                .startTime(mentalRoutineDto.getStartTime())
                .endTime(mentalRoutineDto.getEndTime())
                .build();
    }

}
