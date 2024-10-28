package service.spirit.controller;

import groovy.lang.DelegatesTo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import service.spirit.base.BaseResponse;
import service.spirit.dto.request.MentalDto;
import service.spirit.dto.response.ResponseMentalDto;
import service.spirit.service.MentalCommonService;
import service.spirit.service.MentalQueryService;
import service.spirit.service.OpenAiService;
import service.spirit.service.TokenProviderService;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spirit-service")
public class MentalController {

    private final MentalCommonService mentalCommonService;
    private final OpenAiService openAiService;
    private final MentalQueryService mentalQueryService;
    private final TokenProviderService tokenProviderService;

    //Todo: 마음채우기 루틴 추가하기
    @PostMapping(value = "/routine")
    public BaseResponse<Long> addSpiritRoutine(
            @RequestBody MentalDto.mentalRoutineDto mentalRoutineDto,
//            @PathVariable Long userId
                @RequestHeader("Authorization") String authorizationHeader

        ){
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        mentalRoutineDto.setUserId(userId);
        Long routineId = mentalCommonService.addMentalRoutine(mentalRoutineDto);
        return BaseResponse.onSuccess(routineId);
    }

    //Todo: 감정일기 추가하기
    @PostMapping(value = "/emotional-diary")
    public BaseResponse<Long> saveEmotionalDiary(
            @RequestBody MentalDto.emotionalDiaryDto emotionalDiaryDto,
//            @PathVariable Long userId
            @RequestHeader("Authorization") String authorizationHeader

    ){
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);

        emotionalDiaryDto.setUserId(userId);
        Long emotionalDiaryId = mentalCommonService.saveEmotionalDiary(emotionalDiaryDto);

        return BaseResponse.onSuccess(emotionalDiaryId);
    }


    //Todo: ai 편지 작성
    @PostMapping("/ai/emotional-record/{diaryId}")
    public BaseResponse getAiLetter(
            @PathVariable Long diaryId,
            @RequestHeader("Authorization") String authorizationHeader

    ){

        Long aiLetterId =openAiService.writeAiLetter(diaryId);

        return BaseResponse.onSuccess(aiLetterId);

    }


    // Todo: 마음 채우기 루틴 리스트 조회
    @GetMapping("/routine-list")
    public BaseResponse<List<ResponseMentalDto.MentalRoutineDto>> getRoutineList(
//            @PathVariable Long userId
            @RequestHeader("Authorization") String authorizationHeader

    ){
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        return BaseResponse.onSuccess(mentalQueryService.getSpiritRoutineList(userId));
    }

    // Todo: 감정일기 존재하는지 리스트 조회
    @GetMapping("/emotional-record-list/{userId}")
    public BaseResponse<List<ResponseMentalDto.DiaryDateDto>> getEmotionalDiaryList(
            @RequestParam int year,
            @RequestParam int month,
//            @PathVariable Long userId
            @RequestHeader("Authorization") String authorizationHeader

    ){
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        return BaseResponse.onSuccess(mentalQueryService.getDiaryDate(year,month,userId));
    }


    //Todo: 감정일기 조회
    @GetMapping("/emotional-record/{diaryId}")
    public BaseResponse<ResponseMentalDto.EmotionalDiaryDto> getEmotionalRecord(
            @PathVariable Long diaryId
//            @RequestHeader("Authorization") String authorizationHeader

    ){
        return BaseResponse.onSuccess(mentalQueryService.getEmotionalDiary(diaryId));
    }


    //Todo : 마음채우기 루틴 설명 상세보기
    @GetMapping("/routine-info/{routineId}")
    public BaseResponse<ResponseMentalDto.RoutineInfoDto> getRoutineInfo(
            @PathVariable Long routineId,
            @RequestHeader("Authorization") String authorizationHeader

    ){
        ResponseMentalDto.RoutineInfoDto routineInfoDto = mentalQueryService.getRoutineInfo(routineId);
        return BaseResponse.onSuccess(routineInfoDto);
    }

    //Todo: 감정일기 스크랩하기
    @PatchMapping("/emotional-records/scrap/{recordId}")
    public BaseResponse<Long> diaryScrap(
            @PathVariable Long recordId,
            @RequestHeader("Authorization") String authorizationHeader

    ){
        Long diaryScrapId = mentalCommonService.emotionalRecordDiaryScrap(recordId);
        return BaseResponse.onSuccess(diaryScrapId);
    }

    //Todo: 감정일기 스크랩 취소하기
    @PatchMapping("/emotional-records/scrap-cancel/{recordId}")
    public BaseResponse<Long> diaryScrapCancel(
            @PathVariable Long recordId,
            @RequestHeader("Authorization") String authorizationHeader

    ){
        Long diaryScrapId = mentalCommonService.emotionalRecordDiaryScrapCancel(recordId);
        return BaseResponse.onSuccess(diaryScrapId);
    }

    //Todo: 마음채우기 루틴 삭제
    @DeleteMapping("/{routineId}")
    public BaseResponse<String> deleteMentalRoutine(
            @PathVariable Long routineId,
            @RequestHeader("Authorization") String authorizationHeader

    ){
        mentalCommonService.deleteMentalRoutine(routineId);
        return BaseResponse.onSuccess("성공적으로 삭제하였습니다.");
    }

    //Todo: 감정일기 삭제
    @DeleteMapping("/emotional-diary/{diaryId}")
    public BaseResponse<String> deleteEmotionalDiary(
            @PathVariable Long diaryId,
            @RequestHeader("Authorization") String authorizationHeader

    ){
        mentalCommonService.deleteEmotionalDiary(diaryId);
        return BaseResponse.onSuccess("성공적으로 삭제하였습니다.");
    }

    //Todo: ai 편지 삭제
    @DeleteMapping("/ai/{letterId}")
    public BaseResponse<String> deleteAiDelete(
            @PathVariable Long letterId,
            @RequestHeader("Authorization") String authorizationHeader

    ){
        mentalCommonService.deleteAiLetter(letterId);
        return BaseResponse.onSuccess("성공적으로 삭제하였습니다.");
    }


    //Todo: 마음채우기 루틴 수정
    @PutMapping("/{routineId}")
    public BaseResponse<Long> updateMentalRoutine(
            @PathVariable Long routineId,
            @RequestBody MentalDto.mentalRoutineUpdateDto mentalRoutineUpdateDto,
            @RequestHeader("Authorization") String authorizationHeader

    ) {
        mentalCommonService.updateMentalRoutine(routineId, mentalRoutineUpdateDto);
        return BaseResponse.onSuccess(routineId);
    }

    //Todo: 마음채우기 루틴 켜기
    @PatchMapping("/{routineId}/on")
    public BaseResponse<String> onMentalRoutine(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @PathVariable Long routineId
    ){
        System.out.println(date);
        System.out.println(routineId);
        System.out.println("실행중");
        mentalCommonService.onMentalRoutine(routineId,date);
        return BaseResponse.onSuccess("성공적으로 루틴 일정을 켰습니다.");
    }
    //Todo: 마음채우기 루틴 끄기
    @PatchMapping("/{routineId}/off")
    public BaseResponse<String> offMentalRoutine(
            @RequestParam LocalDate date,
            @PathVariable Long routineId
    ){

        int year = LocalDate.now().getYear();
        mentalCommonService.offMentalRoutine(routineId,date);
        return BaseResponse.onSuccess("성공적으로 루틴 일정을 껐습니다.");
    }

    //Todo: 루틴 일정 수행 완료
    @PatchMapping("/{routineRecordId}/complete")
    public BaseResponse<String> completeRoutineRecord(
            @PathVariable Long routineRecordId,
            @RequestHeader("Authorization") String authorizationHeader

    ){
        mentalCommonService.completeRoutine(routineRecordId);
        return BaseResponse.onSuccess("성공적으로 루틴을 수행하였습니다.");
    }

    //Todo: 루틴 일정 수행 완료 취소
    @PatchMapping("/{routineRecordId}/cancel")
    public BaseResponse<String> cancelRoutineRecord(
            @PathVariable Long routineRecordId,
            @RequestHeader("Authorization") String authorizationHeader

    ){
        mentalCommonService.cancelRoutine(routineRecordId);
        return BaseResponse.onSuccess("성공적으로 루틴 수행 완료를 취소하였습니다.");
    }

    //Todo: 회원의 모든 마음채우기 데이터 삭제
    @DeleteMapping("/users/{userId}")
    public BaseResponse<String> deleteSpiritData(
            @RequestHeader("Authorization") String authorizationHeader

    ){
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        mentalCommonService.deleteSpiritDate(userId);
        return BaseResponse.onSuccess("성공적으로 회원의 마음채우기 모든 데이터를 삭제하였습니다.");
    }

    //Todo: 일별 일정 조회
    @GetMapping("/{userId}")
    public BaseResponse<List<ResponseMentalDto.DayRoutineDto>> getDayRoutine(
            @RequestParam LocalDate date,
//            @PathVariable Long userId
            @RequestHeader("Authorization") String authorizationHeader

    ){
        Long userId = tokenProviderService.getUserIdFromToken(authorizationHeader);
        return BaseResponse.onSuccess(mentalQueryService.getDayRoutine(date, userId));
    }

}
