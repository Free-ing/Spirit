package service.spirit.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoutineTrackerDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MentalRoutineTrackerDto {
        private String mentalName;
        private List<MentalRecordDto> records = new ArrayList<>();
        private List<ResponseMentalDto.DiaryTrackerDto> diaryTrackerDtoList;


        public void setDiaryTrackerDtoList(List<ResponseMentalDto.DiaryTrackerDto> diaryTrackerDtoList) {
            this.diaryTrackerDtoList = diaryTrackerDtoList;
        }

        public MentalRoutineTrackerDto(String mentalName) {
        this.mentalName = mentalName;
        }
        public void addRecord(MentalRecordDto record) {
            this.records.add(record);
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MentalRecordDto {
        private Long id;
        private LocalDate createdAt;  // LocalDateTime 대신 LocalDate 사용

        public MentalRecordDto(Long id, LocalDateTime createdAt) {
            this.id = id;
            this.createdAt = createdAt.toLocalDate();  // LocalDate로 변환
        }
    }
}
