package service.spirit.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import service.spirit.base.BaseEntity;
import service.spirit.dto.request.MentalDto;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "mental_routine")
public class MentalRoutine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String mentalRoutineName;

    private Long userId;

    private LocalTime startTime;
    private LocalTime endTime;

    private Boolean monday;
    private Boolean tuesday;
    private Boolean wednesday;
    private Boolean thursday;
    private Boolean friday;
    private Boolean saturday;
    private Boolean sunday;

    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    private Boolean status;

    @Enumerated(EnumType.STRING)
    @Column(name = "basic_service")
    private SpiritType basicService;

    @OneToMany(mappedBy = "mentalRoutine", cascade = CascadeType.ALL)
    private List<MentalRoutineRecord> mentalRoutineRecordList = new ArrayList<>();


    @Builder
    public MentalRoutine(String metalRoutineName, String imageUrl,Long userId, List<MentalRoutineRecord> mentalRoutineRecordList, SpiritType basicService, String explanation, Boolean status, Boolean sunday, Boolean saturday, Boolean thursday, Boolean friday, Boolean wednesday, Boolean tuesday, Boolean monday, LocalTime endTime, LocalTime startTime) {
        this.mentalRoutineName = metalRoutineName;
        this.userId = userId;
        this.mentalRoutineRecordList = mentalRoutineRecordList;
        this.basicService = basicService;
        this.explanation = explanation;
        this.status = status;
        this.sunday = sunday;
        this.saturday = saturday;
        this.thursday = thursday;
        this.friday = friday;
        this.wednesday = wednesday;
        this.tuesday = tuesday;
        this.monday = monday;
        this.endTime = endTime;
        this.startTime = startTime;
        this.imageUrl = imageUrl;
    }

    public void update(MentalDto.mentalRoutineUpdateDto mentalRoutineUpdateDto) {
        this.mentalRoutineName = mentalRoutineUpdateDto.getSpiritName();
        this.explanation = mentalRoutineUpdateDto.getExplanation();
        this.sunday = mentalRoutineUpdateDto.getSunday();
        this.saturday = mentalRoutineUpdateDto.getSaturday();
        this.thursday = mentalRoutineUpdateDto.getThursday();
        this.friday = mentalRoutineUpdateDto.getFriday();
        this.wednesday = mentalRoutineUpdateDto.getWednesday();
        this.tuesday = mentalRoutineUpdateDto.getTuesday();
        this.monday = mentalRoutineUpdateDto.getMonday();
        this.endTime = mentalRoutineUpdateDto.getEndTime();
        this.startTime = mentalRoutineUpdateDto.getStartTime();
        this.imageUrl = mentalRoutineUpdateDto.getImageUrl();
    }

    public void onMentalRoutine(){
        this.status = true;
    }

    public void updateStatus(Boolean status){
        this.status = status;
    }
}
