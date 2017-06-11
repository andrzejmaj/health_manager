package engineer.thesis.core.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DoctorRestService {
    public static final List<String>  patients = new ArrayList<>();

    static {
        patients.add("patient1");
        patients.add("patient2");
        patients.add("patient3");
    }

    @RequestMapping(path = "/sthelse" ,  method = RequestMethod.GET)
    public static List<String> getPatients() {
        return patients;
    }
}
