package service.spirit.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import service.spirit.base.BaseEntity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MentalRoutine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

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

    private String explanation;

    private Boolean status;

    @Enumerated(EnumType.STRING)
    private SpiritType basicService;

    @OneToMany(mappedBy = "mentalRoutine", cascade = CascadeType.ALL)
    private List<MentalRoutineRecord> mentalRoutineRecordList = new ArrayList<>();

    @Builder
    public MentalRoutine(String metalRoutineName, Long userId, List<MentalRoutineRecord> mentalRoutineRecordList, SpiritType basicService, String explanation, Boolean status, Boolean sunday, Boolean saturday, Boolean thursday, Boolean friday, Boolean wednesday, Boolean tuesday, Boolean monday, LocalTime endTime, LocalTime startTime) {
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
    }
}
