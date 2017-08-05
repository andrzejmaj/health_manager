package engineer.thesis.core.service;

import engineer.thesis.core.model.Specialization;
import engineer.thesis.core.model.dto.SpecializationDTO;
import engineer.thesis.core.repository.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpecializationService implements ISpecializationService {
    @Autowired
    SpecializationRepository specializationRepository;

    @Override
    public SpecializationDTO findExistingOrSaveNewByDescription(String description) {
        return Optional.ofNullable(specializationRepository.findByDescription(description))
                .map(spec -> new SpecializationDTO(spec.getId(), spec.getDescription()))
                .orElseGet(() -> {
                    Specialization spec = specializationRepository.save(new Specialization(description));
                    return new SpecializationDTO(spec.getId(), spec.getDescription());
                });
    }
}
