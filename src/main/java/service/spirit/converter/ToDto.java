package service.spirit.converter;

import service.spirit.dto.response.ResponseMentalDto;
import service.spirit.entity.EmotionalDiary;

public class ToDto {

    public static ResponseMentalDto.EmotionalDiaryDto toEmotionalDiaryDTO(EmotionalDiary emotionalDiary) {
        return ResponseMentalDto.EmotionalDiaryDto.builder()
                .emotion(emotionalDiary.getEmotion())
                .date(emotionalDiary.getCreatedAt().toLocalDate())
                .wellDone(emotionalDiary.getGoodContent())
                .hardWork((emotionalDiary.getBadContent()))
                .diaryId(emotionalDiary.getId())
                .scrap(emotionalDiary.getScrap())
                .build();
    }
}

