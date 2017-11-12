package engineer.thesis.medcom.services;

import engineer.thesis.core.model.entity.medcom.Modality;
import engineer.thesis.core.repository.medcom.ModalityRepository;
import engineer.thesis.core.utils.CustomObjectMapper;
import engineer.thesis.medcom.model.DicomModality;
import engineer.thesis.medcom.model.error.ModalityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public List<DicomModality> getAllModalities() {
        return modalityRepository.findAll()
                .stream()
                .map(entity -> objectMapper.convert(entity, DicomModality.class))
                .collect(Collectors.toList());
    }

    public DicomModality updateModality(DicomModality newModality) {
        // TODO merge with exisiting entity and save and return current modality
        return null;
    }

    public void deleteModality(String modalityAET) {
        Modality modality = Optional.ofNullable(modalityRepository.findOne(modalityAET))
                .orElseThrow(() -> new ModalityNotFoundException(
                        String.format("modality with AE title '%s' not found in the database!", modalityAET)
                ));
        modalityRepository.delete(modality);
    }
}
