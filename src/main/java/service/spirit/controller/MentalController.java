package service.spirit.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import service.spirit.base.BaseResponse;
import service.spirit.dto.response.MentalDto;
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
}
