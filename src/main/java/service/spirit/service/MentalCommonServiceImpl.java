package service.spirit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.spirit.dto.response.MentalDto;
import service.spirit.entity.MentalRoutine;
import service.spirit.repository.MentalRoutineRepository;

import static service.spirit.converter.toEntity.toMentalRoutine;

@Service
@Transactional
@RequiredArgsConstructor
public class MentalCommonServiceImpl implements MentalCommonService {

    private final MentalRoutineRepository mentalRoutineRepository;

    //Todo: 마음 채우기 루틴 설정
    @Override
    public Long addMentalRoutine(MentalDto.mentalRoutineDto mentalRoutineDto) {

        //마음 채우기 루틴 저장
        MentalRoutine mentalRoutine = mentalRoutineRepository.save(toMentalRoutine(mentalRoutineDto));

        //저장된 객체 반환
        return mentalRoutine.getId();
    }

}
