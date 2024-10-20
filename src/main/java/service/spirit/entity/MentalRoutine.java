package service.spirit.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import service.setting.SpiritType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MentalRoutine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String metalRoutineName;

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




}
