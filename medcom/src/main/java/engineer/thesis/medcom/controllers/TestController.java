package engineer.thesis.medcom.controllers;

import engineer.thesis.core.model.dto.ShortPatientDTO;
import engineer.thesis.core.service.Interface.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author MKlaman
 * @since 11.06.2017
 */
@RestController
public class TestController {

    private final IPatientService patientService;

    @Autowired
    public TestController(IPatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/medcom/patients")
    public List<ShortPatientDTO> getAllPatients() {
        return patientService.findAllPatientsShort();
    }
}
