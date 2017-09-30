package engineer.thesis.medcom.controllers;

import engineer.thesis.medcom.model.MedcomModality;
import engineer.thesis.medcom.services.ModalitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author MKlaman
 * @since 30.09.2017
 */
@RestController
public class ModalitiesController {

    private final ModalitiesService modalitiesService;

    @Autowired
    public ModalitiesController(ModalitiesService modalitiesService) {
        this.modalitiesService = modalitiesService;
    }


    @GetMapping(RequestMappings.MODALITY.GET_ALL)
    public List<MedcomModality> getAllModalities() {
        return modalitiesService.getAllModalities();
    }
}
