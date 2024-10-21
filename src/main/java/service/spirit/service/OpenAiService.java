package service.spirit.service;

import service.spirit.dto.request.MentalDto;
import service.spirit.dto.response.ResponseMentalDto;

public interface OpenAiService {

    //Todo:ai 편지 작성
    Long writeAiLetter(Long diaryId);
}
