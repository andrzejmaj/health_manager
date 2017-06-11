package engineer.thesis.medcom.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author MKlaman
 * @since 11.06.2017
 */
@RestController
public class DicomInstancesController {


    @GetMapping(RequestMappings.INSTANCES.GET_ALL_INSTANCES)
    public List<?> getDicomInstancesList() {
        //TODO
        return null;
    }

    @GetMapping(RequestMappings.INSTANCES.GET_INSTANCE_DICOM)
    public void getRawDicomInstance() {
        //TODO
    }

}
