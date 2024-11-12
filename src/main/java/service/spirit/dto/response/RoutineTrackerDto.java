package service.spirit.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoutineTrackerDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MentalRoutineTrackerDto {
        private String mentalName;
        private String imageUrl;
        private List<MentalRecordDto> records = new ArrayList<>();


        public MentalRoutineTrackerDto(String mentalName) {
            this.mentalName = mentalName;
        }
        public void setImageUrl(String imageUrl){
            this.imageUrl = imageUrl;
        }
        public void addRecord(MentalRecordDto record) {
            this.records.add(record);
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MentalRecordDto {
        private Long id;
        private LocalDate routineDate;  // LocalDateTime 대신 LocalDate 사용

        public MentalRecordDto(Long id, LocalDate routineDate) {
            this.id = id;
            this.routineDate = routineDate;
        }
    }
}
