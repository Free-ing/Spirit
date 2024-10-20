package service.spirit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import service.spirit.base.BaseEntity;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class MentalRoutineRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mentalRoutineName;
    private Long userId;
    private LocalDate completeDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mental_routine_id")
    private MentalRoutine mentalRoutine;


}
