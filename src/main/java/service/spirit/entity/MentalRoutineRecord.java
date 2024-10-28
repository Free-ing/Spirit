package service.spirit.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import service.spirit.base.BaseEntity;

import javax.swing.text.StyledEditorKit;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class MentalRoutineRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mental_routine_record_id")
    private Long id;

//    private String mentalRoutineName;
    private Long userId;
    private LocalDate completeDay;
    private LocalDate routineDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mental_routine_id")
    private MentalRoutine mentalRoutine;

    private Boolean complete;
    private Boolean status;

    @Builder
    public MentalRoutineRecord(Boolean complete ,LocalDate routineDate,Boolean status, Long userId, LocalDate completeDay, MentalRoutine mentalRoutine) {
        this.routineDate = routineDate;
        this.complete = complete;
        this.status =status;
        this.userId = userId;
        this.completeDay = completeDay;
        this.mentalRoutine = mentalRoutine;
    }

    public void updateStatus(Boolean status) {
        this.status = status;
        System.out.println(status);
    }

    public void updateCompleteAndCompleteDate(Boolean complete, LocalDate date){
        this.complete = complete;
        this.completeDay = date;
    }
}
