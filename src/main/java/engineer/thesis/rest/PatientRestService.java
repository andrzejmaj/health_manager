package engineer.thesis.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamil on 2017-04-07.
 */
@RestController
public class PatientRestService {
    public static final List<String> diseases = new ArrayList<>();

    static {
        diseases.add("cold");
        diseases.add("hot");
        diseases.add("pain");
    }


    @RequestMapping(path = "/diseases" ,  method = RequestMethod.GET)
    public static List<String> getDiseases() {
        return diseases;
    }
}
