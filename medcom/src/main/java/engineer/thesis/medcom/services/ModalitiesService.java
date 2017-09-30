package engineer.thesis.medcom.services;

import engineer.thesis.core.repository.medcom.ModalityRepository;
import engineer.thesis.core.utils.CustomObjectMapper;
import engineer.thesis.medcom.model.MedcomModality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MKlaman
 * @since 30.09.2017
 */
@Service
public class ModalitiesService {

    private final ModalityRepository modalityRepository;

    private final CustomObjectMapper objectMapper;

    @Autowired
    public ModalitiesService(ModalityRepository modalityRepository,
                             CustomObjectMapper objectMapper) {
        this.modalityRepository = modalityRepository;
        this.objectMapper = objectMapper;
    }

    public List<MedcomModality> getAllModalities() {
        return modalityRepository.findAll()
                .stream()
                .map(entity -> objectMapper.convert(entity, MedcomModality.class))
                .collect(Collectors.toList());
    }

    public void updateModality(MedcomModality newModality) {
        // TODO merge with exisiting entity and save
    }
}
