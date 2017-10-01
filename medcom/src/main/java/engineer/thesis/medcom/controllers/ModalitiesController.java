package engineer.thesis.medcom.controllers;

import engineer.thesis.medcom.model.MedcomModality;
import engineer.thesis.medcom.model.error.ErrorMessage;
import engineer.thesis.medcom.model.error.InstanceNotFoundException;
import engineer.thesis.medcom.model.error.ModalityNotFoundException;
import engineer.thesis.medcom.services.ModalitiesService;
import org.apache.catalina.valves.rewrite.RewriteMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MKlaman
 * @since 30.09.2017
 */
@RestController
public class ModalitiesController {

    private final static Logger logger = Logger.getLogger(ModalitiesController.class);


    private final ModalitiesService modalitiesService;

    @Autowired
    public ModalitiesController(ModalitiesService modalitiesService) {
        this.modalitiesService = modalitiesService;
    }


    @GetMapping(RequestMappings.MODALITY.GET_ALL)
    public List<MedcomModality> getAllModalities() {
        return modalitiesService.getAllModalities();
    }

    @PutMapping(RequestMappings.MODALITY.UPDATE)
    public MedcomModality updateModality(@RequestBody MedcomModality modality) {
        return modalitiesService.updateModality(modality);
    }

    @DeleteMapping(RequestMappings.MODALITY.DELETE)
    public void deleteModality(@PathVariable String aet) {
        modalitiesService.deleteModality(aet);
    }
    

    @ExceptionHandler(ModalityNotFoundException.class) // TODO global exception handler
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public @ResponseBody
    ErrorMessage handleException(ModalityNotFoundException ex) {
        logger.error(ex);
        return new ErrorMessage(ex.getMessage());
    }
}
