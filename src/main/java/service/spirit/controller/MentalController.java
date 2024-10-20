package service.spirit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import service.spirit.base.BaseResponse;
import service.spirit.dto.request.MentalDto;
import service.spirit.service.MentalCommonService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spirit-service")
public class MentalController {

    private final MentalCommonService mentalCommonService;

    //Todo: 마음채우기 루틴 추가하기
    @PostMapping(value = "/routine/{userId}")
    public BaseResponse<Long> addSpiritRoutine(
            @RequestPart MentalDto.mentalRoutineDto mentalRoutineDto,
            @PathVariable Long userId
            ){
        mentalRoutineDto.setUserId(userId);
        Long routineId = mentalCommonService.addMentalRoutine(mentalRoutineDto);
        return BaseResponse.onSuccess(routineId);
    }

    //Todo: 감정일기 추가하기
    @PostMapping(value = "/routine/{userId}")
    public BaseResponse<Long> saveEmotionalDiary(
            @RequestPart MentalDto.emotionalDiaryDto emotionalDiaryDto,
            @PathVariable Long userId
            //            @RequestHeader("Authorization") String authorizationHeader

    ){
        //        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);

        emotionalDiaryDto.setUserId(userId);
        Long emotionalDiaryId = mentalCommonService.saveEmotionalDiary(emotionalDiaryDto);
        return BaseResponse.onSuccess(emotionalDiaryId);
    }
}
