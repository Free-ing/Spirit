package service.spirit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.setting.base.BaseResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spirit-service")
public class SpiritController {

    //Todo: 마음채우기 루틴 추가하기
    @PostMapping("/routine/{userId}")
    public BaseResponse addSpiritRoutine(
            @RequestBody
    ){

    }
}
