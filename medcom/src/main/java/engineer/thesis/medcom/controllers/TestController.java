package engineer.thesis.medcom.controllers;

import engineer.thesis.core.model.dto.PatientDTO;
import engineer.thesis.core.service.IPatientService;
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
    public List<PatientDTO> getAllPatients() {
        return patientService.getAllPatients();
    }
}
