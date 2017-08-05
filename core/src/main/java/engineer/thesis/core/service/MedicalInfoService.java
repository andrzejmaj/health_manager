package engineer.thesis.core.service;

import engineer.thesis.core.exception.AlreadyExistsException;
import engineer.thesis.core.model.MedicalInformation;
import engineer.thesis.core.model.dto.MedicalInfoDTO;
import engineer.thesis.core.repository.MedicalInfoRepository;
import engineer.thesis.core.utils.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class MedicalInfoService implements IMedicalInfoService {

    @Autowired
    MedicalInfoRepository medicalInfoRepository;

    @Autowired
    CustomObjectMapper objectMapper;

    @Override
    public MedicalInfoDTO findById(Long Id) throws NoSuchElementException {
        MedicalInformation medicalInformation = medicalInfoRepository.findOne(Id);
        if (medicalInformation == null) {
            throw new NoSuchElementException("Medical info not found");
        }
        return objectMapper.convert(medicalInformation, MedicalInfoDTO.class);
    }

    @Override
    public MedicalInfoDTO save(MedicalInfoDTO medicalInfoDTO) throws AlreadyExistsException {
        if (medicalInfoRepository.findOne(medicalInfoDTO.getId()) != null) {
            throw new AlreadyExistsException("Medical information already exists");
        }
        medicalInfoDTO.setId(null);
        return objectMapper.convert(medicalInfoRepository.save(objectMapper.convert(medicalInfoDTO, MedicalInformation.class)), MedicalInfoDTO.class);
    }

    @Override
    public MedicalInfoDTO update(MedicalInfoDTO medicalInfoDTO) throws NoSuchElementException {
        if (medicalInfoRepository.findOne(medicalInfoDTO.getId()) == null) {
            throw new NoSuchElementException("Medical information doesnt exists");
        }

        return objectMapper.convert(medicalInfoRepository.save(objectMapper.convert(medicalInfoDTO, MedicalInformation.class)), MedicalInfoDTO.class);
    }
}
