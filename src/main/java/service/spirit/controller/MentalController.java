package service.spirit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;
import service.spirit.base.BaseResponse;
import service.spirit.dto.request.MentalDto;
import service.spirit.dto.response.ResponseMentalDto;
import service.spirit.service.MentalCommonService;
import service.spirit.service.MentalQueryService;
import service.spirit.service.OpenAiService;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spirit-service")
public class MentalController {

    private final MentalCommonService mentalCommonService;
    private final OpenAiService openAiService;
    private final MentalQueryService mentalQueryService;

    //Todo: 마음채우기 루틴 추가하기
    @PostMapping(value = "/routine/{userId}")
    public BaseResponse<Long> addSpiritRoutine(
            @RequestBody MentalDto.mentalRoutineDto mentalRoutineDto,
            @PathVariable Long userId
            ){
        mentalRoutineDto.setUserId(userId);
        Long routineId = mentalCommonService.addMentalRoutine(mentalRoutineDto);
        return BaseResponse.onSuccess(routineId);
    }

    //Todo: 감정일기 추가하기
    @PostMapping(value = "/emotional-diary/{userId}")
    public BaseResponse<Long> saveEmotionalDiary(
            @RequestBody MentalDto.emotionalDiaryDto emotionalDiaryDto,
            @PathVariable Long userId
            //            @RequestHeader("Authorization") String authorizationHeader

    ){
        //        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);

        emotionalDiaryDto.setUserId(userId);
        Long emotionalDiaryId = mentalCommonService.saveEmotionalDiary(emotionalDiaryDto);

        return BaseResponse.onSuccess(emotionalDiaryId);
    }


    //Todo: ai 편지 작성
    @PostMapping("/ai/emotional-record/{diaryId}")
    public BaseResponse getAiLetter(
            @PathVariable Long diaryId){

        Long aiLetterId =openAiService.writeAiLetter(diaryId);

        return BaseResponse.onSuccess(aiLetterId);

    }


    // Todo: 마음 채우기 루틴 리스트 조회
    @GetMapping("/routine-list/{userId}")
    public BaseResponse<List<ResponseMentalDto.MentalRoutineDto>> getRoutineList(
            @PathVariable Long userId
    ){

        return BaseResponse.onSuccess(mentalQueryService.getSpiritRoutineList(userId));
    }

    // Todo: 마음 채우기 루틴 리스트 조회
    @GetMapping("/emotional-record-list/{userId}")
    public BaseResponse<List<ResponseMentalDto.DiaryDateDto>> getEmotionalDiaryList(
            @RequestParam int year,
            @RequestParam int month,
            @PathVariable Long userId
    ){
        System.out.println(year);
        System.out.println(month);

        return BaseResponse.onSuccess(mentalQueryService.getDiaryDate(year,month,userId));
    }



}
